/*
 * This file is part of the MASTODONFX-RESTAPI project.
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * @authors - Geru-Scotland (Basajaun) | Github: https://github.com/geru-scotland
 *          - Unai Salaberria          | Github: https://github.com/unaisala
 *          - Martin Jimenez           | Github: https://github.com/Matx1n3
 *          - Iñaki Azpiroz            | Github: https://github.com/iazpiroz15
 *          - Diego Forniés            | Github: https://github.com/DiegoFornies
 *
 */

package eus.ehu.bum4_restapi.controllers.user;

import eus.ehu.bum4_restapi.api.MastodonAPI;
import eus.ehu.bum4_restapi.api.RestAPI;
import eus.ehu.bum4_restapi.model.Toot;
import eus.ehu.bum4_restapi.utils.Constants;
import eus.ehu.bum4_restapi.utils.HyperLinkRedirectListener;
import eus.ehu.bum4_restapi.utils.PropertyManager;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;

public abstract class TootListTemplateController {
    /**
     * FXML members
     */
    @FXML
    private RadioButton boosted;

    @FXML
    private Label tootCount;

    @FXML
    private TextField author;

    @FXML
    private TextField date;

    @FXML
    private Button next;

    @FXML
    private Button previous;

    @FXML
    private WebView webArea;

    @FXML
    private WebEngine webEngine;

    @FXML
    private Hyperlink tootLink;

    @FXML
    private Label authText;

    @FXML
    private Label dateText;

    @FXML
    private RadioButton favButton;

    /**
     * Specific MainController class members.
     */
    protected RestAPI<?, ?> restAPI;
    private int currentToot;
    private int totalToots;

    private Toot finalToot;

    @FXML
    private ImageView loadingImage;

    @FXML
    public void initialize(){

        //  Start timer
        Instant start = Instant.now();

        showAll(false);
        loadingImage.setVisible(true);

        finalToot = null;

        try{
            int propCurr;
            boosted.setDisable(true);
            boosted.setStyle("-fx-opacity: 1;");

            propCurr = Integer.parseInt(PropertyManager.getProperty(Constants.CURRENT_TOOT));

            restAPI = new MastodonAPI();
            webEngine = webArea.getEngine();

            webArea.getEngine().getLoadWorker().stateProperty().addListener(new HyperLinkRedirectListener(webArea));

            getData();

            currentToot = (propCurr != -1)&&(propCurr <= totalToots) ? propCurr -1 : 0;

            CompletableFuture<Toot> toot = (CompletableFuture<Toot>) restAPI.getObjectFromListAsync(currentToot);
            toot.thenAcceptAsync(toot1 -> {
                try {
                    showTootData((Toot) toot1);
                    totalToots = restAPI.getObjectListSize();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                loadingImage.setVisible(false);
                showAll(true);
            });

        } catch(Exception e){
            System.out.println("[EXCEPTION] " + e.getMessage());
        }

        //  End timer and print taken time
        Instant end = Instant.now();
        System.out.println("Time taken to load toots: " + java.time.Duration.between(start, end).toMillis() + "ms");
    }

    abstract void getData() throws IOException;
    @FXML
    private void loadNextToot(ActionEvent event) {
        if(currentToot < totalToots - 1)
        {
            currentToot++;
            try{
                showTootData((Toot) restAPI.getObjectFromList(currentToot));
            } catch (Exception e){
                System.out.println("[EXCEPTION] " + e.getMessage());
            }
        }
    }

    @FXML
    private void loadPreviousToot(ActionEvent event) {
        if(currentToot > 0)
        {
            currentToot--;
            try {
                showTootData((Toot) restAPI.getObjectFromList(currentToot));
            } catch (Exception e){
                System.out.println("[EXCEPTION] " + e.getMessage());
            }
        }
    }

    /**
     * Helper, in order to show the toot.
     */
    private void showTootData(Toot toot) throws IOException {
        boosted.setSelected(false);

        if(toot.isReblog()){
            toot = toot.getReblog();
            boosted.setSelected(true);
        }

        author.setText(toot.getUsername());
        date.setText(toot.getCreatedAt());
        favButton.setSelected(toot.isFavourited());

        /*
        TODO: Change all this kind of thread use for CompletableFuture
         */
        class MyThread extends Thread{
            public void run(Toot toot){
                Platform.runLater(() -> {
                    webEngine.loadContent("<body><div id='toot-area'>" + toot.getContent() + "</div></body>");
                    tootCount.setText((currentToot + 1) + "/" + totalToots);
                });
            }
        }

        MyThread thread = new MyThread();
        thread.run(toot);
        finalToot = toot;

        PropertyManager.setProperty(Constants.CURRENT_TOOT.getKey(), String.valueOf(currentToot + 1));
    }

    @FXML
    void goToLink(MouseEvent event) {
        if (finalToot != null) {
            new Thread(() -> {
                try {
                    if (Desktop.isDesktopSupported()) {
                        Desktop.getDesktop().browse(new URI(finalToot.getUri()));
                    }
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    @FXML
    void favClicked(MouseEvent event){
        if (finalToot.isFavourited()){
            new Thread(() -> {
                restAPI.getRequest(Constants.ENDPOINT_STATUSES + "/" + finalToot.getId() + Constants.ENDPOINT_MARK_TOOT_AS_NOT_FAV);
            }).start();
            finalToot.setFavourited(false);
            favButton.setSelected(false);
        }
        else {
            new Thread(() -> {
                restAPI.getRequest(Constants.ENDPOINT_STATUSES + "/" + finalToot.getId() + Constants.ENDPOINT_MARK_TOOT_AS_FAV);
            }).start();
            finalToot.setFavourited(true);
            favButton.setSelected(true);
        }
    }

    private void showAll(boolean show){
        favButton.setVisible(show);
        boosted.setVisible(show);
        tootCount.setVisible(show);
        author.setVisible(show);
        date.setVisible(show);
        next.setVisible(show);
        previous.setVisible(show);
        webArea.setVisible(show);
        tootLink.setVisible(show);
        authText.setVisible(show);
        dateText.setVisible(show);
    }
}
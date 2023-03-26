/*
 * This file is part of the BUM4_REST-API project.
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
import eus.ehu.bum4_restapi.utils.PropertyManager;
import eus.ehu.bum4_restapi.utils.Constants;
import eus.ehu.bum4_restapi.utils.HyperLinkRedirectListener;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

public class TootsController {
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

    /**
     * Specific MainController class members.
     */
    RestAPI<?, ?> restAPI;
    int currentToot;
    int totalToots;

    @FXML
    public void initialize(){
        try{
            int propCurr;
            boosted.setDisable(true);
            boosted.setStyle("-fx-opacity: 1;");

            propCurr = Integer.parseInt(PropertyManager.getProperty(Constants.CURRENT_TOOT));

            restAPI = new MastodonAPI();
            webEngine = webArea.getEngine();

            webArea.getEngine().getLoadWorker().stateProperty().addListener(new HyperLinkRedirectListener(webArea));

            restAPI.setJSONtoList();
            totalToots = restAPI.getObjectListSize();

            currentToot = (propCurr != -1)&&(propCurr <= totalToots) ? propCurr -1 : 0;

            showTootData((Toot) restAPI.getObjectFromList(currentToot));

        } catch(Exception e){
            System.out.println("[EXCEPTION] " + e.getMessage());
        }
    }

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

        webEngine.loadContent("<body><div id='toot-area'>" + toot.getContent() + "</div></body>");
        tootCount.setText((currentToot + 1) + "/" + totalToots);

        Toot finalToot = toot;
        tootLink.setOnAction(event -> {
            try {
                if (Desktop.isDesktopSupported())
                    Desktop.getDesktop().browse(new URI(finalToot.getUri()));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        });

        /*
         * Input stream needs to be loaded, in order to preserve other variables.
         * TODO: Adapt de the new PropertyManager class to make it work here.
         */
        Properties prop = new Properties();
        InputStream input = new FileInputStream("config.properties");
        prop.load(input);

        OutputStream output = new FileOutputStream("config.properties");

        prop.setProperty("currenttoot", String.valueOf(currentToot + 1));
        prop.store(output, null);
    }
}
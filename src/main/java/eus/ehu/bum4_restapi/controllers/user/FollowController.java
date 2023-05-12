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
import eus.ehu.bum4_restapi.model.Account;
import eus.ehu.bum4_restapi.utils.Constants;
import eus.ehu.bum4_restapi.utils.VboxUtils;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.time.Instant;
import java.util.List;


public abstract class FollowController {

    @FXML
    protected VBox accountsListView;

    @FXML
    protected ImageView loadingImage;

    @FXML
    protected ScrollPane scrollPane;
    protected RestAPI<?, ?> restAPI;

    protected String API_endpoint;

    @FXML
    public void initialize(){

        //  Start timer
        Instant start = Instant.now();

        scrollPane.setVisible(false);
        loadingImage.setVisible(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        restAPI = new MastodonAPI();

        setAPI_endpoint();

        new Thread(() -> {
            List<Account> list = (List<Account>) restAPI.getObjectList(API_endpoint);
            Platform.runLater(() -> {
                initialize(list, accountsListView);
                scrollPane.setVisible(true);
                loadingImage.setVisible(false);
            });
        }).start();

        Instant end = Instant.now();
        System.out.println("Time taken to load followers: " + java.time.Duration.between(start, end).toMillis() + "ms");
    }

    @FXML
    public void initialize(List<Account> list, VBox view) {

        ObservableList<Account> items = FXCollections.observableList(list);

        if(view != null){
            VboxUtils.mapByValue(items, view.getChildren(), account -> new UserFrameController(account).getAnchorPane());
        }
    }

    abstract void setAPI_endpoint();
}

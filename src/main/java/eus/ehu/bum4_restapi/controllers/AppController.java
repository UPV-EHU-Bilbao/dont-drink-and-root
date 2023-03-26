/*
 * This file is part of the Project-MastodonFX project.
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

package eus.ehu.bum4_restapi.controllers;

import eus.ehu.bum4_restapi.utils.Constants;
import eus.ehu.bum4_restapi.utils.PropertyManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class AppController {

    /**
     * Class members
     */

    /**
     * JavaFX members
     */
    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Button userToots;

    @FXML
    private Button userFollowers;

    @FXML
    private Button userFollowing;

    @FXML
    public void initialize() throws IOException {
    }

    /**
     * @param view String con el nombre de la vista a.
     */
    public void loadViewOnCenter(String view) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(view));
            AnchorPane userTootAnchor = loader.load();
            mainBorderPane.setCenter(userTootAnchor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleLoadUserToots(ActionEvent event) throws IOException {
        System.out.println("Loading User Toot view...");
        loadViewOnCenter(PropertyManager.getProperty(Constants.USER_TOOTS_VIEW));
    }

    @FXML
    void handleLoadUserFollowers(ActionEvent event) throws IOException {
        System.out.println("Loading User Followers...");
        loadViewOnCenter(PropertyManager.getProperty(Constants.USER_FOLLOWERS_VIEW));
    }

    @FXML
    void handleLoadUserFollowing(ActionEvent event) throws IOException {
        System.out.println("Loading User Following...");
        loadViewOnCenter(PropertyManager.getProperty(Constants.USER_FOLLOWING_VIEW));
    }
}
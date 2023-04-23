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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    private ImageView TitleImg;
    @FXML
    private Label TitleLabel;

    @FXML
    private TextArea TootText;
    @FXML
    private Button postButton;

    @FXML
    private Label PostedTootLabel;



    @FXML
    public void initialize() throws IOException {
        handleLoadUserToots();
        TitleLabel.setText("User Toots");
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

    void selectButton(String button){

        String selected = "-fx-background-color: rgba(135, 206, 250, 0.5);";

        switch (button) {
            case "toots" -> {
                userToots.setStyle(selected);
                userFollowers.setStyle("-fx-background-color: white;");
                userFollowing.setStyle("-fx-background-color: white;");
                TitleLabel.setText("User toots");
              Image image = new Image(getClass().getResourceAsStream("/mailIcon.png"));
              TitleImg.setImage(image);
            }
            case "followers" -> {
                userToots.setStyle("-fx-background-color: white;");
                userFollowers.setStyle(selected);
                userFollowing.setStyle("-fx-background-color: white;");
                TitleLabel.setText("Followers");
                Image image = new Image(getClass().getResourceAsStream("/followersIcon.png"));
                TitleImg.setImage(image);

            }
            case "following" -> {
                userToots.setStyle("-fx-background-color: white;");
                userFollowers.setStyle("-fx-background-color: white;");
                userFollowing.setStyle(selected);
                TitleLabel.setText("Following");
                Image image = new Image(getClass().getResourceAsStream("/following.png"));
                TitleImg.setImage(image);

            }
        }
    }
    @FXML
    void handleLoadUserToots() throws IOException {
        System.out.println("Loading User Toot view...");
        loadViewOnCenter(PropertyManager.getProperty(Constants.USER_TOOTS_VIEW));
        selectButton("toots");

    }

    @FXML
    void handleLoadUserFollowers() throws IOException {
        System.out.println("Loading User Followers...");
        loadViewOnCenter(PropertyManager.getProperty(Constants.USER_FOLLOWERS_VIEW));
        selectButton("followers");
    }

    @FXML
    void handleLoadUserFollowing() throws IOException {
        System.out.println("Loading User Following...");
        loadViewOnCenter(PropertyManager.getProperty(Constants.USER_FOLLOWING_VIEW));
        selectButton("following");
    }

    @FXML
    void publishToot(ActionEvent event) {
        if (TootText.getText().length() > 0) {
            PostedTootLabel.setVisible(true);
            PostedTootLabel.setStyle("-fx-background-color:  #7fff00;");
            PostedTootLabel.setText("Your toot has been successfully posted!");
            TootText.setText("");
        }
        else{
            PostedTootLabel.setVisible(true);
            PostedTootLabel.setText("Type something to post a toot!");
            PostedTootLabel.setStyle("-fx-background-color:  #ff4500;");
        }
    }

}
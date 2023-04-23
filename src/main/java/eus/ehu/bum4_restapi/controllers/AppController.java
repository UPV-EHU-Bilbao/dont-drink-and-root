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

import eus.ehu.bum4_restapi.App;
import eus.ehu.bum4_restapi.api.MastodonAPI;
import eus.ehu.bum4_restapi.api.RestAPI;
import eus.ehu.bum4_restapi.controllers.user.FavTootsController;
import eus.ehu.bum4_restapi.controllers.user.TootsController;
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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

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
    private App main;

    @FXML
    private TextArea TootText;
    @FXML
    private Button postButton;

    @FXML
    private Label PostedTootLabel;

    @FXML
    private Button favouritesButton;

    RestAPI<?, ?> restAPI;
    
    @FXML
    public void initialize(){}

    public void onScene() throws IOException{
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

    /**
     * @param view String con el nombre de la vista a.
     * @param controllerClass String con el nombre de la clase controller.
     */
    public <T> void loadViewOnCenter(String view, Class<T> controllerClass) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(view));
            Constructor<T> constructor = controllerClass.getDeclaredConstructor();
            T controller = constructor.newInstance();
            loader.setController(controller);
            AnchorPane userTootAnchor = loader.load();
            mainBorderPane.setCenter(userTootAnchor);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    void selectButton(String button){

        String selected = "-fx-background-color: rgba(135, 206, 250, 0.5);";
        String not_selected = "-fx-background-color: white;";

        switch (button) {
            case "toots" -> {
                userToots.setStyle(selected);
                userFollowers.setStyle(not_selected);
                userFollowing.setStyle(not_selected);
                favouritesButton.setStyle(not_selected);
                TitleLabel.setText("User toots");
              Image image = new Image(getClass().getResourceAsStream("/mailIcon.png"));
              TitleImg.setImage(image);
            }
            case "followers" -> {
                userToots.setStyle(not_selected);
                userFollowers.setStyle(selected);
                userFollowing.setStyle(not_selected);
                favouritesButton.setStyle(not_selected);
                TitleLabel.setText("Followers");
                Image image = new Image(getClass().getResourceAsStream("/followersIcon.png"));
                TitleImg.setImage(image);

            }
            case "following" -> {
                userToots.setStyle(not_selected);
                userFollowers.setStyle(not_selected);
                userFollowing.setStyle(selected);
                favouritesButton.setStyle(not_selected);
                TitleLabel.setText("Following");
                Image image = new Image(getClass().getResourceAsStream("/following.png"));
                TitleImg.setImage(image);
            }
            case "favourite-toots" -> {
                userToots.setStyle(not_selected);
                userFollowers.setStyle(not_selected);
                userFollowing.setStyle(not_selected);
                favouritesButton.setStyle(selected);
                TitleLabel.setText("Favourite Toots");
                Image image = new Image(getClass().getResourceAsStream("/following.png"));  //  Change picture
                TitleImg.setImage(image);
            }
        }
    }
    @FXML
    void handleLoadUserToots() throws IOException {
        System.out.println("Loading User Toot view...");
        loadViewOnCenter(PropertyManager.getProperty(Constants.USER_TOOTS_VIEW), TootsController.class);
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
    void handleFavourites() throws IOException {
        System.out.println("Loading favourite toots...");
        loadViewOnCenter(PropertyManager.getProperty(Constants.USER_TOOTS_VIEW), FavTootsController.class);
        selectButton("favourite-toots");
    }

    @FXML
    void handleLogOut() throws IOException {
        main.show("Login");
    }

    public void setMain(App a){
        this.main = a;
    }
   
    @FXML
    void publishToot(ActionEvent event) {
        if (!TootText.getText().equals("")) {
            restAPI = new MastodonAPI();


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
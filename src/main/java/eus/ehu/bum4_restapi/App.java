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

package eus.ehu.bum4_restapi;

import eus.ehu.bum4_restapi.controllers.AppController;
import eus.ehu.bum4_restapi.controllers.LoginController;
import eus.ehu.bum4_restapi.utils.Constants;
import eus.ehu.bum4_restapi.utils.PropertyManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    Scene loginScene;
    LoginController loginController;
    Scene menuScene;
    AppController appController;
    Stage appStage;
    @Override
    public void start(Stage stage) throws IOException {
        appStage = stage;

        FXMLLoader loginLoader = new FXMLLoader(App.class.getResource("login-view.fxml"));
        Scene loginScene = new Scene(loginLoader.load(), 600, 400);
        LoginController loginController = loginLoader.getController();
        this.loginController = loginController;
        loginController.setMain(this);

        FXMLLoader menuLoader = new FXMLLoader(App.class.getResource(PropertyManager.getProperty(Constants.APP_VIEW)));
        Scene menuScene = new Scene(menuLoader.load());
        AppController appController = menuLoader.getController();
        this.appController = appController;
        appController.setMain(this);

        this.loginScene = loginScene;
        this.menuScene = menuScene;

        stage.setTitle("Login");
        stage.setScene(loginScene);
        stage.show();
        stage.centerOnScreen();
    }

    public void show(String title) throws IOException{
        switch (title){
            case "Login":
                appStage.setTitle("Login");
                appStage.setScene(loginScene);
                appStage.show();
                appStage.centerOnScreen();
                loginController.initialize();
                break;
            case "Menu":
                appController.onScene();
                appStage.setTitle("Rest API Mastodon");
                appStage.setScene(menuScene);
                appStage.show();
                appStage.centerOnScreen();
                break;
        }
    }


    public static void main(String[] args) {
        launch();
    }
}
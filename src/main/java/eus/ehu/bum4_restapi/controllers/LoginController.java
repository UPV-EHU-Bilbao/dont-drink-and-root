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
import eus.ehu.bum4_restapi.database.DbAccessManager;
import eus.ehu.bum4_restapi.utils.Constants;
import eus.ehu.bum4_restapi.utils.PropertyManager;
import eus.ehu.bum4_restapi.utils.Shared;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    private App main;

    @FXML
    private TextField apiKeyField;

    @FXML
    private Button loginButton;

    @FXML
    private Button savedAccButton;

    @FXML
    private CheckBox savedCB;

    @FXML
    private Label infoLabel;

    @FXML
    public Label apiLabel;

    private static DbAccessManager db;
    private RestAPI<?, ?> restAPI;

    @FXML
    public void initialize() throws IOException {
        db = DbAccessManager.getInstance();
        restAPI = new MastodonAPI();
        apiKeyField.setVisible(false);
        apiLabel.setVisible(false);
        apiKeyField.setText("");
        usernameField.setText(PropertyManager.getProperty(Constants.CURRENT_USERNAME));
        infoLabel.setText("");
        savedCB.setVisible(false);
    }

    public void loginClicked() throws IOException {
        String username = usernameField.getText();
        String apiKey = apiKeyField.getText();
        String apiKeyDB = db.getApiKeyFromUsername(username);
        restAPI = new MastodonAPI();

        if(username.equals("")){
            infoLabel.setText("Please, introduce a username");
        }
        else if(apiKeyDB != null){
            if(!validateInfo(username, apiKeyDB, db)){
                apiKeyField.setVisible(true);
                apiLabel.setVisible(true);
            }
        } else {
            if(apiKeyField.isVisible()){
                validateInfo(username, apiKey, db);
            } else {
                infoLabel.setText("API Key not existant for that account, please introduce one.");
                apiKeyField.setVisible(true);
                apiLabel.setVisible(true);
            }
        }
    }

    private boolean validateInfo(String username, String apiKey, DbAccessManager db) throws IOException {
        if(apiKey.equals("")){
            infoLabel.setText("Please introduce your Mastodon's API key");
        }
        if (restAPI.validateCredentials(username, apiKey)){
            db.addUser(username, apiKey, Shared.accID);
            PropertyManager.setProperty(Constants.CURRENT_USERNAME.getKey(), username);
            main.show("Menu");
            return true;
        }
        else
            infoLabel.setText("Incorrect credentials");

        return false;
    }

    public void setMain(App a){
        main = a;
    }
}

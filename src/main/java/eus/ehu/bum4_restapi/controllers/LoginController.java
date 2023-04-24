package eus.ehu.bum4_restapi.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import eus.ehu.bum4_restapi.App;
import eus.ehu.bum4_restapi.api.MastodonAPI;
import eus.ehu.bum4_restapi.api.RestAPI;
import eus.ehu.bum4_restapi.controllers.user.FollowController;
import eus.ehu.bum4_restapi.database.DbAccessManager;
import eus.ehu.bum4_restapi.model.Account;
import eus.ehu.bum4_restapi.utils.Constants;
import eus.ehu.bum4_restapi.utils.PropertyManager;
import eus.ehu.bum4_restapi.utils.Shared;
import eus.ehu.bum4_restapi.utils.VboxUtils;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

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
    RestAPI<?, ?> restAPI;

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
        String username = usernameField.getText().toString();
        String apiKey = apiKeyField.getText().toString();
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

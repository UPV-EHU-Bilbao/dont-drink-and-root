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

    private DbAccessManager db;
    RestAPI<?, ?> restAPI;

    @FXML
    public void initialize() throws IOException {
        db = DbAccessManager.getInstance();
        restAPI = new MastodonAPI();
        db.deleteCurrentAccount();
        apiKeyField.setText("");
        usernameField.setText("");
        infoLabel.setText("");
        savedCB.setSelected(false);
        db.deleteAccounts();
    }

    public void loginClicked() throws IOException{
        String username = usernameField.getText().toString();
        String apiKey = apiKeyField.getText().toString();
        boolean save = savedCB.isSelected();

        restAPI = new MastodonAPI();
        Gson gson = new Gson();

        PropertyManager.setProperty(String.valueOf(Constants.CURRENT_USER_API_KEY), apiKey);
        String rq = restAPI.getRequest(Constants.ACCOUNTS + "verify_credentials");
        JsonObject account = gson.fromJson(rq, JsonObject.class);

        String u = account.get("username").getAsString();
        String accountId = account.get("id").getAsString();

        if (u.equals(username)){
            PropertyManager.setProperty(String.valueOf(Constants.CURRENT_USERNAME), username);
            PropertyManager.setProperty(String.valueOf(Constants.CURRENT_USER_ID), accountId);
            main.show("Menu");
        }
        else {
            infoLabel.setText("User or api key incorrect. Please check credentials.");
        }
    }

    public void setMain(App a){
        main = a;
    }
}

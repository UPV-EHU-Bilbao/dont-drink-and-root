package eus.ehu.bum4_restapi.controllers;

import eus.ehu.bum4_restapi.App;
import eus.ehu.bum4_restapi.api.MastodonAPI;
import eus.ehu.bum4_restapi.controllers.user.FollowController;
import eus.ehu.bum4_restapi.database.DbAccessManager;
import eus.ehu.bum4_restapi.model.Account;
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
    private MastodonAPI restAPI;

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
        if(restAPI.login(username, apiKey, save)){
            main.show("Menu");
        } else {
            infoLabel.setText("User or api key incorrect. Please check credentials.");
        }

    }

    public void setMain(App a){
        main = a;
    }
}

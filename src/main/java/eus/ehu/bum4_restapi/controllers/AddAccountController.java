package eus.ehu.bum4_restapi.controllers;

import eus.ehu.bum4_restapi.App;
import eus.ehu.bum4_restapi.api.MastodonAPI;
import eus.ehu.bum4_restapi.api.RestAPI;
import eus.ehu.bum4_restapi.database.DbAccessManager;
import eus.ehu.bum4_restapi.utils.Constants;
import eus.ehu.bum4_restapi.utils.PropertyManager;
import eus.ehu.bum4_restapi.utils.Shared;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class AddAccountController {
    @FXML
    private TextField apiKeyTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private Button addButton;

    @FXML
    private Button goBack;

    @FXML
    private Text infoText;

    private App main;
    private RestAPI<?, ?> restAPI;

    private DbAccessManager db;

    public void setMain(App app) {
        main = app;
    }
    @FXML
    void addClicked(ActionEvent event) throws IOException {
        restAPI = new MastodonAPI();
        if (usernameTextField.getText().equals("") || apiKeyTextField.getText().equals("")){
            infoText.setText("Please, enter the credentials");
        }
        else {
            if (restAPI.validateCredentials(usernameTextField.getText(), apiKeyTextField.getText())){
                db = DbAccessManager.getInstance();
                db.addUser(usernameTextField.getText(), apiKeyTextField.getText(), Shared.accID);
                PropertyManager.setProperty(Constants.CURRENT_USERNAME.getKey(), usernameTextField.getText());
                main.show("Menu");
            }
            else {
                infoText.setText("The provided credentials are wrong, please check and try again");
            }
        }

    }

    @FXML
    void goBackClicked(ActionEvent event) throws IOException {
        main.show("Login");
    }
}

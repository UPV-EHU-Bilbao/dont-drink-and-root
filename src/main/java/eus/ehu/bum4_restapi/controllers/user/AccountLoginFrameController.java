package eus.ehu.bum4_restapi.controllers.user;

import eus.ehu.bum4_restapi.App;
import eus.ehu.bum4_restapi.api.MastodonAPI;
import eus.ehu.bum4_restapi.api.RestAPI;
import eus.ehu.bum4_restapi.database.DbAccessManager;
import eus.ehu.bum4_restapi.model.SimpleAccount;
import eus.ehu.bum4_restapi.utils.Constants;
import eus.ehu.bum4_restapi.utils.PropertyManager;
import eus.ehu.bum4_restapi.utils.Shared;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.Objects;
import java.io.File;

public class AccountLoginFrameController {

    @FXML
    private ImageView accountImage;

    @FXML
    private Button loginButton;

    @FXML
    private Label username_label;
    private App main;

    @FXML
    private AnchorPane accForLoginAnchorPane;

    @FXML
    void loginButtonClicked(ActionEvent event) {}

    @FXML
    private Button removeButton;


    private RestAPI<?, ?> restAPI;

    public void initialize(){};

    public AccountLoginFrameController(SimpleAccount account, App app){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PropertyManager.getProperty(Constants.ACCOUNT_LOGIN_FRAME_VIEW)));
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        restAPI = new MastodonAPI();
        main = app;

        if (!Objects.equals(account.getId(), "login")) {
            username_label.setText(account.getUsername());
            loginButton.setOnAction(actionEvent -> {
                if (restAPI.validateCredentials(account.getUsername(), account.getApikey())){
                    Shared.username = account.getUsername();
                    Shared.apiKey = account.getApikey();
                    Shared.accID = account.getId();
                    try {
                        main.show("Menu");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
            });
            removeButton.setOnAction(actionEvent -> {
                DbAccessManager db = DbAccessManager.getInstance();
                db.deleteAccount(account.getId());
                try {
                    main.show("Login");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            Image image;

            File file = new File("src/main/resources/profile_pictures/" + account.getId() + ".jpeg");
            if (!file.exists()){
                file = new File("src/main/resources/profile_pictures/default-profile.jpeg");
            }
            image = new Image(file.toURI().toString());
            accountImage.setImage(image);

        }
        else {
            username_label.setText("Add new account: ");
            loginButton.setText("Add!");
            loginButton.setOnAction(actionEvent -> {
                try {
                    main.show("AddAccount");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            removeButton.setVisible(false);
        }
    }

    public AnchorPane getAnchorPane() {
        return accForLoginAnchorPane;
    }

    public void setMain(App app){
        main = app;
    }
}

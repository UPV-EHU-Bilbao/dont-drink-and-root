package eus.ehu.bum4_restapi.controllers.user;

import eus.ehu.bum4_restapi.model.Account;
import eus.ehu.bum4_restapi.utils.Constants;
import eus.ehu.bum4_restapi.utils.PropertyManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class UserFrameController {

    private FXMLLoader fxmlLoader;
    @FXML
    private ImageView avatar;

    @FXML
    private TextField displayName;

    @FXML
    private TextField followers;

    @FXML
    private TextField following;

    @FXML
    private AnchorPane listItem;

    @FXML
    private TextField username;


    public UserFrameController(Account item) {

        if (fxmlLoader == null) {
            try {
                fxmlLoader = new FXMLLoader(getClass().getResource(PropertyManager.getProperty(Constants.USER_FRAME_VIEW)));
                fxmlLoader.setController(this);
                fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        displayName.setText(item.getDisplay_name());
        username.setText(item.getUsername());
        followers.setText("" + item.getFollowersCount());
        following.setText("" + item.getFollowingCount());
        avatar.setImage(new Image(item.getAvatar()));
    }

    public AnchorPane getAnchorPane(){
        return listItem;
    }

}

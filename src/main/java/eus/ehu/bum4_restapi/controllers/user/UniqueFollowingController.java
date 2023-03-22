package eus.ehu.bum4_restapi.controllers.user;

import eus.ehu.bum4_restapi.model.Account;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class UniqueFollowingController extends ListCell<Account> {

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


    protected void updateItem(Account item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
            setText(null);

            return;
        }

        if (fxmlLoader == null) {
            fxmlLoader = new FXMLLoader(getClass().getResource("/eus/ehu/bum4_restapi/user-uniquefollowing.fxml"));
            fxmlLoader.setController(this);
            try {
                fxmlLoader.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        displayName.setText(item.getDisplay_name());
        username.setText(item.getUsername());
        followers.setText(""+item.getFollowers_count());
        following.setText(""+item.getFollowing_count());
        avatar.setImage(new Image(item.getAvatar()));

        setText(null);
        setGraphic(listItem);
    }

}

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

package eus.ehu.bum4_restapi.controllers.user;

import eus.ehu.bum4_restapi.model.Account;
import eus.ehu.bum4_restapi.utils.Constants;
import eus.ehu.bum4_restapi.utils.PropertyManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class UserFrameController {

    @FXML
    private ImageView avatar;

    @FXML
    private Label displayName;

    @FXML
    private Label followers;

    @FXML
    private Label following;

    @FXML
    private AnchorPane listItem;

    @FXML
    private Label username;


    public UserFrameController(Account item) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(PropertyManager.getProperty(Constants.USER_FRAME_VIEW)));
            fxmlLoader.setController(this);
            fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        displayName.setText(item.getDisplay_name());
        username.setText(item.getUsername());
        followers.setText("" + item.getFollowersCount());
        following.setText("" + item.getFollowingCount());
        new Thread(() -> {
            avatar.setImage(new Image(item.getAvatar()));
        }).start();
    }

    public AnchorPane getAnchorPane(){
        return listItem;
    }

}

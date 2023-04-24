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

package eus.ehu.bum4_restapi.controllers.user;

import eus.ehu.bum4_restapi.api.MastodonAPI;

import eus.ehu.bum4_restapi.model.Account;
import eus.ehu.bum4_restapi.utils.Constants;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import javafx.scene.image.ImageView;

public class FollowersController extends FollowController {

    @FXML
    private VBox followersView;

    @FXML
    private ImageView loadingImage;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    public void initialize() throws IOException {

        //  Start timer
        Instant start = Instant.now();

        scrollPane.setVisible(false);
        loadingImage.setVisible(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        restAPI = new MastodonAPI();

        new Thread(() -> {
            List<Account> list = (List<Account>) restAPI.getObjectList(Constants.ENDPOINT_FOLLOWERS.getKey());
            Platform.runLater(() -> {
                super.initialize(list, followersView);
                scrollPane.setVisible(true);
                loadingImage.setVisible(false);
            });
        }).start();

        Instant end = Instant.now();
        System.out.println("Time taken to load followers: " + java.time.Duration.between(start, end).toMillis() + "ms");
    }
}
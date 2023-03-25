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
import eus.ehu.bum4_restapi.api.RestAPI;
import eus.ehu.bum4_restapi.model.Account;
import eus.ehu.bum4_restapi.utils.Constants;
import eus.ehu.bum4_restapi.utils.PropertyManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * TODO: This needs to use MastodonAPI
 */

public class FollowersController {


    @FXML
    private ListView<Account> followersView;
    java.util.List<Account> accounts;
    @FXML
    private TextField noFollowerText;
    @FXML
    private ImageView pepoImg;
    RestAPI<?, ?> restAPI;

    public java.util.List<Account> getFollowers(){
        String id = "109842111446764244";


        String body = restAPI.sendRequest("accounts/"+id+"/followers");

        Gson gson = new Gson();
        JsonArray jsonArray = gson.fromJson(body, JsonArray.class);

        Type accountList = new TypeToken<ArrayList<Account>>() {}.getType();
        List<Account> accounts = gson.fromJson(jsonArray.getAsJsonArray(), accountList);

        return accounts;
    }

    @FXML
    public void initialize() throws IOException {
        restAPI = new MastodonAPI(PropertyManager.getProperty(Constants.USER_JUANAN));
        accounts = getFollowers();

        ObservableList<Account> items = FXCollections.observableList(accounts);

        if(items.size()<1){
        noFollowerText.setVisible(true);
        noFollowerText.setText("You don't have any follower yet :(");
        pepoImg.setVisible(true);

        }else {
            followersView.setVisible(true);
            if (followersView != null) {
                followersView.setItems(items);
                followersView.setCellFactory(param -> {
                    var cell = new UniqueFollowerController();
                    cell.setOnMouseClicked((evt) -> {
                        Account account = cell.getItem();
                        if (account != null) {
                            System.out.println(account.getDisplay_name());
                        }
                    });
                    return cell;
                });
            }
        }
    }
}
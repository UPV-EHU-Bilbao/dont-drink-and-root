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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

/**
 * TODO: This needs to use MastodonAPI
 */
public class FollowingController {

    @FXML
    private ListView<Account> followersView;
    List<Account> accounts;
    RestAPI<?, ?> restAPI;


    @FXML
    public void initialize(){
        try {
            restAPI = new MastodonAPI(PropertyManager.getProperty(Constants.USER_JUANAN));

            accounts = restAPI.convertJSONtoFollowingList();

            ObservableList<Account> items = FXCollections.observableList(accounts);

            if(followersView != null){
                followersView.setItems(items);
                followersView.setCellFactory(param -> {
                    var cell = new UniqueFollowingController();
                    cell.setOnMouseClicked((evt) -> {
                        Account account = cell.getItem();
                        if(account!=null) {
                            System.out.println(account.getDisplay_name());
                        }
                    });
                    return cell;
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

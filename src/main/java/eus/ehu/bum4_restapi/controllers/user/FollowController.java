/*
 * This file is part of the BUM4_REST-API project.
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

import eus.ehu.bum4_restapi.api.RestAPI;
import eus.ehu.bum4_restapi.model.Account;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.List;


public class FollowController {

    protected RestAPI<?, ?> restAPI;

    @FXML
    public void initialize(List<Account> list, ListView<Account> view)  {

        ObservableList<Account> items = FXCollections.observableList(list);

        if(view != null){
            view.setItems(items);
            view.setCellFactory(param -> {
                var cell = new UserFrameController();
                cell.setOnMouseClicked((evt) -> {
                    Account account = cell.getItem();
                    if(account!=null) {
                        System.out.println(account.getDisplay_name());
                    }
                });
                return cell;
            });
        }
    }
}

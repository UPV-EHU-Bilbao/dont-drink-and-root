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

package eus.ehu.bum4_restapi.api;

import com.google.gson.JsonArray;
import eus.ehu.bum4_restapi.model.Account;

import java.util.List;

public interface RestAPI<T, V> {
    public String sendRequest(String endpoint);
    public void convertJSONtoList();
    public JsonArray getPreviousJSONObject();
    public JsonArray getNextJSONObject();
    public T getObjectFromList(int index);
    public List<V> getObjectList(String endpoint);
    public int getObjectListSize();

    List<Account> convertJSONtoFollowersList();

    List<Account> convertJSONtoFollowingList();
}

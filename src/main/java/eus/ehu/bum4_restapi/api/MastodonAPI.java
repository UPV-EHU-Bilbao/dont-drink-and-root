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

package eus.ehu.bum4_restapi.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import eus.ehu.bum4_restapi.model.Account;
import eus.ehu.bum4_restapi.model.Toot;

import eus.ehu.bum4_restapi.utils.Constants;
import eus.ehu.bum4_restapi.utils.PropertyManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MastodonAPI implements RestAPI<Toot, Account> {
    List<Toot> tootList;
    String accountId;
    List<Account> followersList;
    List<Account> followingList;

    public MastodonAPI() throws IOException {
        accountId = PropertyManager.getProperty(Constants.USER_GERU);
        resetTootList();
    }

    private String request(String endpoint){
        String result = "";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(Constants.API_BASE + endpoint)
                .get()
                .addHeader("Authorization", "Bearer " + System.getenv("TOKEN-MFX"))
                .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.code() == 200) {
                result = response.body().string();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<Toot> getToots(String endpoint){
        Gson gson = new Gson();
        String rq = request(endpoint);
        JsonArray jsonArray = gson.fromJson(rq, JsonArray.class);

        Type statusList = new TypeToken<ArrayList<Toot>>() {}.getType();
        return gson.fromJson(jsonArray.getAsJsonArray(), statusList);
    }

    public List<Account> getFollowers(String endpoint){
        Gson gson = new Gson();
        String rq = request(endpoint);
        JsonArray jsonArray = gson.fromJson(rq, JsonArray.class);

        Type accountList = new TypeToken<ArrayList<Account>>() {}.getType();
        return gson.fromJson(jsonArray.getAsJsonArray(), accountList);
    }

    public void setTootList(List<Toot> list){
        tootList = list;
    }

    public void resetTootList(){
        tootList = new ArrayList<>();
    }

    public int getTootListSize(){ return tootList.size(); }

    public void setFollowersList(List<Account> list){
        followersList = list;
    }

    public void setFollowingList(List<Account> list){
        followingList = list;
    }

    @Override
    public String sendRequest(String endpoint){
        return request(endpoint);
    }

    @Override
    public void setJSONtoList(){
        List<Toot> list = getToots(Constants.ACCOUNTS + accountId + Constants.ENDPOINT_STATUSES);
        setTootList(list);
    }

    @Override
    public JsonArray getPreviousJSONObject() {
        return null;
    }

    @Override
    public JsonArray getNextJSONObject() {
        return null;
    }

    @Override
    public Toot getObjectFromList(int index) throws ArrayIndexOutOfBoundsException {
        if(index < 0 || index >= tootList.size())
            throw new ArrayIndexOutOfBoundsException();

        return tootList.get(index);
    }

    @Override
    public List<Account> getObjectList(String endpoint) {
        return getFollowers(Constants.ACCOUNTS + accountId + endpoint);
    }

    @Override
    public int getObjectListSize() {
        return getTootListSize();
    }
}

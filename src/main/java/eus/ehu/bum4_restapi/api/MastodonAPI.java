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

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import eus.ehu.bum4_restapi.model.Account;
import eus.ehu.bum4_restapi.model.Toot;

import eus.ehu.bum4_restapi.utils.Constants;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MastodonAPI implements RestAPI<Toot> {
    List<Toot> tootList;
    String accountId;
    List<Account> followersList;
    List<Account> followingList;


    private static MastodonAPI instance;

    public MastodonAPI(String userId) {
        accountId = userId;
        resetTootList();
    }

    public static MastodonAPI getAPIHandler(String userId){
        if (instance == null){
            instance = new MastodonAPI(userId);
        }
        if (!instance.accountId.equals(userId)){
            instance = new MastodonAPI(userId);
        }
        return instance;
    }

    private String request(String endpoint){
        String result = "";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://mastodon.social/api/v1/" + endpoint)
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
    public void convertJSONtoList(){
        List<Toot> list = getToots("accounts/" + accountId + "/statuses");
        setTootList(list);
    }

    public void convertJSONtoFollowersList(){
        List<Account> list = getFollowers("accounts/" + accountId + "/followers");
        setFollowersList(list);
    }

    public void convertJSONtoFollowingList(){
        List<Account> list = getFollowers("accounts/" + accountId + "/following");
        setFollowingList(list);
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
    public int getObjectListSize() {
        return getTootListSize();
    }
}

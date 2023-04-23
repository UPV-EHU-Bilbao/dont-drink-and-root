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
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import eus.ehu.bum4_restapi.database.DbAccessManager;
import eus.ehu.bum4_restapi.model.Account;
import eus.ehu.bum4_restapi.model.SimpleAccount;
import eus.ehu.bum4_restapi.model.Toot;

import eus.ehu.bum4_restapi.utils.Constants;
import eus.ehu.bum4_restapi.utils.PropertyManager;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class MastodonAPI implements RestAPI<Toot, Account> {
    private List<Toot> tootList;
    private SimpleAccount currentAccount;
    private List<Account> followersList;
    private List<Account> followingList;
    private DbAccessManager db;
    private boolean tootListFilled;
    private String apiKey;
    private static MastodonAPI instance;

    private MastodonAPI() {
        tootListFilled = false;
        resetTootList();
        db = DbAccessManager.getInstance();
        updateCurrentAccount();
    }

    public static MastodonAPI getInstance(){
        if (instance == null)
            instance = new MastodonAPI();
        return instance;
    }

    public void updateCurrentAccount(){
        currentAccount = db.getCurrentAccount();
    }

    private String request(String endpoint){
        String result = "";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constants.API_BASE + endpoint)
                .get()
                .addHeader("Authorization", "Bearer " + apiKey)
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

    private CompletableFuture<List<Toot>> getToots(String endpoint){
        return CompletableFuture.supplyAsync(() -> {
            Gson gson = new Gson();
            String rq = request(endpoint);
            JsonArray jsonArray = gson.fromJson(rq, JsonArray.class);
            Type statusList = new TypeToken<ArrayList<Toot>>() {}.getType();
            return gson.fromJson(jsonArray.getAsJsonArray(), statusList);
        });
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
        tootListFilled = true;
    }

    public void resetTootList(){
        tootList = new ArrayList<>();
        tootListFilled = false;
    }

    public int getTootListSize(){ return tootList.size(); }

    /*
      Esto no puede estar aqui, el API tiene que abstraerse de la DB, cuenta y demás
     */
    public boolean login(String username, String apiKey, boolean save){

        try {
            Gson gson = new Gson();
            this.apiKey = apiKey;
            String rq = request(Constants.ACCOUNTS + "verify_credentials");
            JsonObject account = gson.fromJson(rq, JsonObject.class);
            String u = account.get("username").getAsString();
            String accountId = account.get("id").getAsString();
            if(u.equals(username)){
                if(save) {
                    db.addUser(username, apiKey, accountId);
                }
                db.addCurrentUser(username, apiKey, accountId);
                updateCurrentAccount();
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void setFollowersList(List<Account> list){
        followersList = list;
    }

    public void setFollowingList(List<Account> list){
        followingList = list;
    }

    //ENDPOINT for toots: Constants.ACCOUNTS + accountId + Constants.ENDPOINT_STATUSES
    //ENDPOINT for fav toots: String.valueOf(Constants.ENDPOINT_FAVOURITES
    @Override
    public String sendRequest(String endpoint){
        return request(endpoint);
    }


    //ENDPOINT: Constants.ACCOUNTS + accountId + Constants.ENDPOINT_STATUSES
    @Override
    public void setJSONtoList(String endpoint){
        CompletableFuture<List<Toot>> future = getToots(endpoint.replace(Constants.PLACEHOLDER_ACCOUNT.getKey(), currentAccount.getId()));
        future.thenAcceptAsync(this::setTootList);
    }

    @Override
    public JsonArray getPreviousJSONObject() {return null;}

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

    public CompletableFuture<Toot> getObjectFromListAsync(int index) throws ArrayIndexOutOfBoundsException {
        return CompletableFuture.supplyAsync(() -> {
            while (!tootListFilled){
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return getObjectFromList(index);
        });
    }

    @Override
    public List<Account> getObjectList(String endpoint) {
        return getFollowers(Constants.ACCOUNTS + currentAccount.getId() + endpoint);
    }

    @Override
    public int getObjectListSize() {
        return getTootListSize();
    }


}

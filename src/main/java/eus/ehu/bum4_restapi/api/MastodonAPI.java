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
import eus.ehu.bum4_restapi.model.Account;
import eus.ehu.bum4_restapi.model.Toot;

import eus.ehu.bum4_restapi.utils.Constants;
import eus.ehu.bum4_restapi.utils.Shared;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class MastodonAPI implements RestAPI<Toot, Account> {
    List<Toot> tootList;
    List<Account> followersList;
    List<Account> followingList;
    private boolean tootListFilled;

    public MastodonAPI() {
        tootListFilled = false;
        resetTootList();
    }

    public String getRequest(String endpoint) {
        String result = "";
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(Constants.API_BASE + endpoint)
                .get()
                .addHeader("Authorization", "Bearer " + Shared.apiKey)
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

    public String postRequest(String endpoint, Map<String, String> params) {

        String result = "";
        OkHttpClient client = new OkHttpClient();

        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()){
            builder.add(entry.getKey(), entry.getValue());
        }
        RequestBody body = builder.build();

        Request request = new Request.Builder()
                .url(Constants.API_BASE + endpoint)
                .post(body)
                .addHeader("Authorization", "Bearer " + Shared.apiKey)
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());
            result = String.valueOf(response.code());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    private CompletableFuture<List<Toot>> getToots(String endpoint){
        return CompletableFuture.supplyAsync(() -> {
            Gson gson = new Gson();
            String rq = getRequest(endpoint);
            JsonArray jsonArray = gson.fromJson(rq, JsonArray.class);
            Type statusList = new TypeToken<ArrayList<Toot>>() {}.getType();
            return gson.fromJson(jsonArray.getAsJsonArray(), statusList);
        });
    }

    public List<Account> getFollowers(String endpoint) throws IOException {
        Gson gson = new Gson();
        String rq = getRequest(endpoint);
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
    }

    public int getTootListSize(){ return tootList.size(); }


    public void setFollowersList(List<Account> list){
        followersList = list;
    }

    public void setFollowingList(List<Account> list){
        followingList = list;
    }

    //ENDPOINT: Constants.ACCOUNTS + accountId + Constants.ENDPOINT_STATUSES
    @Override
    public void setJSONtoList(String endpoint){
        CompletableFuture<List<Toot>> future = getToots(endpoint.replace(Constants.PLACEHOLDER_ACCOUNT.getKey(), Shared.accID));
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
    public List<Account> getObjectList(String endpoint){
        try {
            return getFollowers(Constants.ACCOUNTS + Shared.accID + endpoint);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getObjectListSize() {
        return getTootListSize();
    }

    @Override
    public boolean validateCredentials(String username, String apiKey) {
        try{
            Shared.apiKey = apiKey;
            Gson gson = new Gson();
            String rq = getRequest(Constants.ACCOUNTS + Constants.ENDPOINT_VERIFY_CREDENTIALS.getKey());
            JsonObject jsonData = gson.fromJson(rq, JsonObject.class);
            String user = jsonData.get("username").getAsString();
            Shared.accID = jsonData.get("id").getAsString();
            return username.equals(user);
        } catch(Exception e){
            System.out.println("[EXCEPTION][API-CREDENTIALS] Something went wrong with the validation.");
        }
        return false;
    }

}

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

package eus.ehu.bum4_restapi.database;

import eus.ehu.bum4_restapi.model.SimpleAccount;

import java.sql.*;
import java.util.ArrayList;

public class DbAccessManager {

    private Connection conn = null;
    private String dbpath;

    public void open() {
        try {
            String url = "jdbc:sqlite:" + dbpath;
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.err.println("Cannot connect to database server " + e.getMessage());
        }
    }

    public void close() {
        if (conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static DbAccessManager instance = new DbAccessManager();

    private DbAccessManager() {
        dbpath = "accounts.db";
    }

    public static DbAccessManager getInstance() {
        return instance;
    }

    public String getApiKeyFromUsername(String username) {
        this.open();

        try {
            String query = "SELECT api_key FROM accounts WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            return rs.getString("api_key");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.close();
        return null;
    }

    public void addUser(String username, String apiKey, String id){
        this.open();
        String sql = "INSERT INTO accounts (username, api_key, id) VALUES(?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, username);
            pstmt.setString(2, apiKey);
            pstmt.setString(3, id);
            pstmt.executeUpdate();
        } catch (SQLException ignored) {
        }

        this.close();
    }

    public ArrayList<SimpleAccount> getAccounts (){
        var accounts  = new ArrayList<SimpleAccount>();
        this.open();

        try {
            String query = "SELECT * FROM accounts";
            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                accounts.add(new SimpleAccount(rs.getString("username"), rs.getString("api_key"), rs.getString("id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.close();
        return accounts;

    }

    public void deleteCurrentAccount(){
        this.open();
        String sql = "DELETE FROM current_account";

        try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }

        this.close();
    }

    public void addCurrentUser(String username, String apikey, String accountId){
        this.open();
        String sql = "INSERT INTO current_account (username, api_key, id) VALUES(?,?,?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, apikey);
            pstmt.setString(3, accountId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.close();
    }

    public SimpleAccount getCurrentAccount(){
        this.open();
        SimpleAccount account = null;

        try {
            String query = "SELECT * FROM current_account";
            ResultSet rs = conn.createStatement().executeQuery(query);
            String username = rs.getString("username");
            String apikey = rs.getString("api_key");
            String accountId = rs.getString("id");
            account = new SimpleAccount(username, apikey, accountId);
            this.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.close();
        return account;
    }

    public void deleteAccounts(){
        this.open();
        String sql = "DELETE FROM accounts";
        try (PreparedStatement pstmt  = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        this.close();
    }
}

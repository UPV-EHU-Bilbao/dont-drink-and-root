package eus.ehu.bum4_restapi.database;

import eus.ehu.bum4_restapi.model.SimpleAccount;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DbAccessManager {

    Connection conn = null;
    String dbpath;

    public void open() {
        try {
            String url = "jdbc:sqlite:" + dbpath;
            conn = DriverManager.getConnection(url);

            System.out.println("Database connection established");
        } catch (Exception e) {
            System.err.println("Cannot connect to database server " + e);
        }
    }

    public void close() {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        System.out.println("Database connection closed");
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

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, apiKey);
            pstmt.setString(3, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
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
}

package eus.ehu.bum4_restapi.model;

public class SimpleAccount {
    private String username;
    private String apikey;
    private String id;

    public SimpleAccount(String username, String apikey, String id){
        this.username = username;
        this.apikey = apikey;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public String getApikey() {
        return apikey;
    }

    public String getId() {
        return id;
    }
}

package eus.ehu.bum4_restapi.controllers;

import eus.ehu.bum4_restapi.api.MastodonAPI;
import eus.ehu.bum4_restapi.database.DbAccessManager;
import eus.ehu.bum4_restapi.model.SimpleAccount;
import javafx.fxml.FXML;

import java.io.IOException;
import java.util.ArrayList;

public class SelectAccountController {
    private DbAccessManager db;
    private MastodonAPI restAPI;
    private ArrayList<SimpleAccount> accounts;
    @FXML
    public void initialize() throws IOException {
        db = DbAccessManager.getInstance();
        restAPI = new MastodonAPI();
        accounts = db.getAccounts();
    }

}

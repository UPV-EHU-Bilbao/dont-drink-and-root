package eus.ehu.bum4_restapi.controllers;

import eus.ehu.bum4_restapi.App;
import eus.ehu.bum4_restapi.controllers.user.AccountLoginFrameController;
import eus.ehu.bum4_restapi.database.DbAccessManager;
import eus.ehu.bum4_restapi.model.SimpleAccount;
import eus.ehu.bum4_restapi.utils.VboxUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class LoginController {
    @FXML
    private VBox accounts_vbox;

    private DbAccessManager db;

    private App main;

    public void init(){
        db = DbAccessManager.getInstance();
        ObservableList<SimpleAccount> storedAccounts = FXCollections.observableList(db.getAccounts());
        storedAccounts.add(new SimpleAccount());
        VboxUtils.mapByValue(storedAccounts, accounts_vbox.getChildren(), account -> new AccountLoginFrameController(account, main).getAnchorPane());
    }

    public void setMain(App app) {
        main = app;
    }
}

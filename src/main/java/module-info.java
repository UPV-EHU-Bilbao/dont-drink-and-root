module eus.ehu.bum4_restapi {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.kordamp.bootstrapfx.core;
    requires okhttp3;
    requires com.google.gson;
    requires java.desktop;
    requires jdk.xml.dom;

    opens eus.ehu.bum4_restapi to javafx.fxml;
    opens eus.ehu.bum4_restapi.api to javafx.fxml;
    opens eus.ehu.bum4_restapi.controllers to javafx.fxml;
    opens eus.ehu.bum4_restapi.model to com.google.gson;

    exports eus.ehu.bum4_restapi;
    exports eus.ehu.bum4_restapi.controllers;
    exports eus.ehu.bum4_restapi.api;
    exports eus.ehu.bum4_restapi.model to com.google.gson;
    exports eus.ehu.bum4_restapi.controllers.user;
    exports eus.ehu.bum4_restapi.utils;
    opens eus.ehu.bum4_restapi.controllers.user to javafx.fxml;
}
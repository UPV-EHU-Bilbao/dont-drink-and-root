package eus.ehu.bum4_restapi.controllers.user;

import eus.ehu.bum4_restapi.utils.Constants;

import java.io.IOException;

public class HomeController extends TootListTemplateController{

    @Override
    void getData() throws IOException {
        restAPI.setJSONtoList(String.valueOf(Constants.ENDPOINT_HOME));
    }
}

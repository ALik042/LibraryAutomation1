package com.library_practice.step_definitions;

import com.library_practice.utilities.ConfigurationReader;
import com.library_practice.utilities.DB_Util;
import com.library_practice.utilities.Driver;
import io.cucumber.java.After;
import io.cucumber.java.Before;


public class Hooks {

    @Before
    public  void createConnection(){


        String url = ConfigurationReader.getProperty("url_JDBC");
        String username = ConfigurationReader.getProperty("usernameDB");
        String password = ConfigurationReader.getProperty("passwordDB");

        DB_Util.createConnection(url, username, password);
    }

    @After
    public  void destroy(){
        DB_Util.destroy();

    }
}

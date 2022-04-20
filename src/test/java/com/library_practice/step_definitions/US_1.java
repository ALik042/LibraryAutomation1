package com.library_practice.step_definitions;

import com.library_practice.utilities.DB_Util;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;

public class US_1 {
    @Given("Establish the database connection")
    public void establish_the_database_connection() {


    }

        int totalId;
        int totalUniqueId;
    @When("Execute query to get all IDs from users")
    public void execute_query_to_get_all_i_ds_from_users() {

        DB_Util.runQuery("select id from users");
        totalId =DB_Util.getRowCount();
        System.out.println("Total users ID :"+totalId);

        DB_Util.runQuery("select distinct id from users");
        totalUniqueId = DB_Util.getRowCount();
        System.out.println("Total unique users ID :"+totalUniqueId);




    }

    @Then("verify all users has unique ID")
    public void verify_all_users_has_unique_id() {
        Assert.assertEquals(totalId,totalUniqueId);
    }


    List<String> listNames;
    @When("Execute query to get all columns")
    public void execute_query_to_get_all_columns() {

        DB_Util.runQuery("select * from users");
        listNames = DB_Util.getAllColumnNamesAsList();
        System.out.println("Expected result = "+listNames);


    }

    @Then("verify the below columns are listed in result")
    public void verify_the_below_columns_are_listed_in_result(List<String> dataTable) {
        Assert.assertEquals(dataTable,listNames);
        System.out.println("Actual result = "+dataTable);

    }


}

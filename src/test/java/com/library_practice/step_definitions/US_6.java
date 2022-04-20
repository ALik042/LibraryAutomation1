package com.library_practice.step_definitions;

import com.library_practice.page.LoginPage;
import com.library_practice.utilities.DB_Util;
import com.library_practice.utilities.Driver;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class US_6 {
    LoginPage loginPage = new LoginPage();
    List<String> actualData =new ArrayList<>();
    @When("I take all book categories in UI")
    public void i_take_all_book_categories_in_ui() {
        Select select = new Select(loginPage.dropDownBookCategories);
        List<WebElement> allCategories =select.getOptions();

        for (WebElement each : allCategories) {
            if(each.getText().equals("ALL")){
                continue;
            }
            actualData.add(each.getText());
        }
        System.out.println(actualData);
    }
    List<String> expectedData = new ArrayList<>();
    @When("I execute a query to get book categories")
    public void i_execute_a_query_to_get_book_categories() {
        DB_Util.runQuery("select name from book_categories");
        int countColumn = DB_Util.getRowCount();
       for (int i =1; i<=countColumn;i++){
           expectedData.add(DB_Util.getCellValue(i,1));
       }


        System.out.println(expectedData);


    }
    @Then("verify book categories must match the book_categories table from DB.")
    public void verify_book_categories_must_match_the_book_categories_table_from_db() {
        Assert.assertEquals(expectedData,actualData);
        Driver.quitDriver();
    }
}

package com.library_practice.step_definitions;

import com.library_practice.page.BooksPage;
import com.library_practice.page.LoginPage;
import com.library_practice.utilities.BrowserUtils;
import com.library_practice.utilities.ConfigurationReader;
import com.library_practice.utilities.DB_Util;
import com.library_practice.utilities.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class US_5 {
    LoginPage loginPage = new LoginPage();
    BooksPage booksPage = new BooksPage();
    @Given("I login as a librarian")
    public void i_login_as_a_librarian() {
        Driver.getDriver().get(ConfigurationReader.getProperty("URL"));
        loginPage.LibrarianLogin();
    }
    @Given("I navigate to {string} page")
    public void i_navigate_to_page(String string) {
        booksPage.booksModule.click();


    }
    String name;
    @When("I open book {string}")
    public void i_open_book(String string) {
       booksPage.searchBox.sendKeys(string);
       name=string;
        BrowserUtils.waitFor(2);


    }
    @Then("book information must match the Database")
    public void book_information_must_match_the_database() {
        List <String> actualData= new ArrayList<>();
        actualData.add(booksPage.bookName.getText());
        actualData.add(booksPage.authorName.getText());
        actualData.add(booksPage.year.getText());
        System.out.println("Actual data : "+actualData);
        DB_Util.runQuery("select name, author,year from books where name='"+name+"'");
        List<String> expectedData= DB_Util.getRowDataAsList(1);

        System.out.println("Expected data : "+expectedData);
        Assert.assertEquals(expectedData,actualData);
        Driver.quitDriver();
    }
}

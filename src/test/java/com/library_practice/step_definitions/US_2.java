package com.library_practice.step_definitions;

import com.library_practice.page.LoginPage;
import com.library_practice.utilities.BrowserUtils;
import com.library_practice.utilities.ConfigurationReader;
import com.library_practice.utilities.DB_Util;
import com.library_practice.utilities.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class US_2 {
    LoginPage loginPage = new LoginPage();
    @Given("I am in the homepage of the library app")
    public void iAmInTheHomepageOfTheLibraryApp() {
        Driver.getDriver().get(ConfigurationReader.getProperty("URL"));
        loginPage.LibrarianLogin();


    }
       String actualBorrowedBooks;
       String expectedBorrowedBooks;

       @When("I take borrowed books number")
       public void iTakeBorrowedBooksNumber() {


           actualBorrowedBooks =loginPage.borrowedBooks.getText();
           System.out.println("Actual Borrowed Books : "+actualBorrowedBooks);
    }

    @Then("borrowed books number information must match with DB")
    public void borrowedBooksNumberInformationMustMatchWithDB() {
     DB_Util.runQuery("select COUNT(book_id) from book_borrow where returned_date is null");
        expectedBorrowedBooks = DB_Util.getFirstRowFirstColumn();
        System.out.println("Expected Borrowed Books : "+expectedBorrowedBooks);
        Assert.assertEquals(actualBorrowedBooks,expectedBorrowedBooks);
        Driver.quitDriver();
    }


}

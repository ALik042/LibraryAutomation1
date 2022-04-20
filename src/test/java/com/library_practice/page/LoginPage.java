package com.library_practice.page;

import com.library_practice.utilities.ConfigurationReader;
import com.library_practice.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
    public LoginPage(){
        PageFactory.initElements(Driver.getDriver(),this);}

    @FindBy(xpath = "//input[@id=\"inputEmail\"]")
    public WebElement usernameInputEmail;

    @FindBy(xpath = "//input[@id=\"inputPassword\"]")
    public WebElement passwordInput;

    @FindBy(xpath = "//button[@type=\"submit\"]")
    public WebElement signInBtn;

    @FindBy(xpath = "//h2[@id='borrowed_books']")
    public WebElement borrowedBooks;

    @FindBy(xpath = "//select[@id='book_categories']")
    public WebElement dropDownBookCategories;
    public void StudentLogin(){

        usernameInputEmail.sendKeys(ConfigurationReader.getProperty("usernameS"));
        passwordInput.sendKeys(ConfigurationReader.getProperty("passwordS"));
        signInBtn.click();
    }

    public void LibrarianLogin(){

        usernameInputEmail.sendKeys(ConfigurationReader.getProperty("usernameL"));
        passwordInput.sendKeys(ConfigurationReader.getProperty("passwordL"));
        signInBtn.click();
    }




}

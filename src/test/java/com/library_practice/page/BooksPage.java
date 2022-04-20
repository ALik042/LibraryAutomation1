package com.library_practice.page;

import com.library_practice.utilities.Driver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BooksPage {

    public  BooksPage(){
        PageFactory.initElements(Driver.getDriver(),this);
    }

    @FindBy(xpath = "(//a[@class='nav-link'])[3]")
    public WebElement booksModule;

    @FindBy(xpath = "//input[@type='search']")
    public WebElement searchBox;

    @FindBy(xpath = "//td[3]")
    public WebElement bookName;

    @FindBy(xpath = "//td[4]")
    public WebElement authorName;

    @FindBy(xpath = "//td[6]")
    public WebElement year;






}

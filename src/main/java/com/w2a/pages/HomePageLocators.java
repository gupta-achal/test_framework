package com.w2a.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePageLocators {

    @FindAll(
            @FindBy(xpath = "//a[text()='Sign In']")
    )
    public WebElement signIn;

//    public HomePageLocators(WebDriver driver){
//        PageFactory.initElements(driver, this);
//    }
}

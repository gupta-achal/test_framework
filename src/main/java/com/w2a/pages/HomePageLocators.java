package com.w2a.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class HomePageLocators {

    @FindAll(
            @FindBy(xpath = "//a[text()='Sign In']")
    )
    public WebElement signIn;
    @FindAll({
            @FindBy(xpath = "//input[@placeholder='Email address or mobile number']"),
            @FindBy(css = "input[placeholder='Email address or mobile number']"),
            @FindBy(id = " .. "),
            @FindBy(linkText = "email address or mobile number")
    })
    public WebElement emailTextBox;

}

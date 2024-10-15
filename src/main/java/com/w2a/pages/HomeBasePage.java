package com.w2a.pages;

import com.w2a.base.BasePage;
import com.w2a.errorController.ActionLib;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

public class HomeBasePage extends BasePage {

    private HomePageLocators homePageLocators;

    public HomeBasePage(){
        this.homePageLocators = new HomePageLocators();
        PageFactory.initElements(factory,homePageLocators);

    }

    public void gotoLogin(){

        ActionLib.click(homePageLocators.signIn, "");

    }
    public void login() {
        driver.findElement(By.xpath("//input[@placeholder='Email address or mobile number']")).sendKeys("guptaachal23@gmail.com");
        driver.findElement(By.xpath("//*[@id=\"nextbtn\"]")).click();
        driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("Aez@Km1?");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        driver.findElement(By.xpath("//*[@id=\"nextbtn\"]")).click();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

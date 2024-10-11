package com.w2a.pages;

import com.w2a.base.Page;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class HomePage extends Page {

    private HomePageLocators homePageLocators;

    public HomePage(){
        this.homePageLocators = new HomePageLocators();
       // AjaxElementLocatorFactoTry factory = new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(factory,homePageLocators);
    }

    public void gotoLogin(){
        //driver.findElement(By.xpath("//a[text()='Sign In']")).click();
        homePageLocators.signIn.click();

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

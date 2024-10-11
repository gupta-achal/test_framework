package com.w2a.pages;

import com.w2a.base.Page;
import org.openqa.selenium.By;

public class LoginPage extends Page {


    public void gotoLogin(){
        driver.findElement(By.xpath("//a[text()='Sign In']")).click();

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

package com.w2a.pages;

import com.w2a.base.Page;
import org.openqa.selenium.By;

public class ZohoAppPage extends Page {

    public void gotoAppPage(){
        driver.findElement(By.xpath("//*[@id=\"all-apps\"]")).click();
    }
}

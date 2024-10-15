package com.w2a.pages;

import com.w2a.base.BasePage;
import org.openqa.selenium.By;

public class ZohoAppBasePage extends BasePage {

    public void gotoAppPage(){
        driver.findElement(By.xpath("//*[@id=\"all-apps\"]")).click();
    }
}

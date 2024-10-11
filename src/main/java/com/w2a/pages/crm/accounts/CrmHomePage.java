package com.w2a.pages.crm.accounts;

import com.w2a.base.Page;
import org.openqa.selenium.By;

public class CrmHomePage extends Page {

    public void gotoCrm(){
        driver.findElement(By.xpath("//*[@id=\"zl-category-1\"]/div/ul/li[1]/div/a/p")).click();
        driver.navigate().to("https://www.zoho.com/all-products.html");
        try {
            Thread.sleep(2000);
        }catch (InterruptedException e){
            throw new RuntimeException();
        }
    }
}

package com.w2a.pages.crm.accounts;

import com.w2a.base.BasePage;
import org.openqa.selenium.By;

public class CrmHomeBasePage extends BasePage {

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

package com.w2a.testcases;

import com.w2a.pages.LoginPage;
import com.w2a.pages.ZohoAppPage;
import com.w2a.pages.crm.accounts.CrmHomePage;
import org.testng.annotations.Test;

public class CrmHomePageTest {

    @Test
    //dataProviderClass = TestUtil.class, dataProvider = "dp"  --  use it when the setup for the data provider is complete
    //inside the @Test annotation.

    public void crmHomePageTest(){
        CrmHomePage homePage = new CrmHomePage();
        LoginPage loginPage = new LoginPage();
        ZohoAppPage appPage = new ZohoAppPage();
        loginPage.gotoLogin();
        loginPage.login();
        appPage.gotoAppPage();
        homePage.gotoCrm();
    }

}

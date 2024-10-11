package com.w2a.testcases;

import com.w2a.pages.LoginPage;
import com.w2a.pages.ZohoAppPage;
import com.w2a.pages.crm.accounts.CrmHomePage;
import com.w2a.utilities.TestUtil;
import org.testng.annotations.Test;

public class CrmHomePageTest {

    @Test(dataProviderClass = TestUtil.class, dataProvider = "dp")
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

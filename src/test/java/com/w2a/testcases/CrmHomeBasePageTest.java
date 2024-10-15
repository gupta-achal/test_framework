package com.w2a.testcases;

import com.w2a.pages.LoginBasePage;
import com.w2a.pages.ZohoAppBasePage;
import com.w2a.pages.crm.accounts.CrmHomeBasePage;
import org.testng.annotations.Test;

public class CrmHomeBasePageTest extends BaseTest {

    @Test
    //dataProviderClass = TestUtil.class, dataProvider = "dp"  --  use it when the setup for the data provider is complete
    //inside the @Test annotation.
    public void crmHomePageTest(){
        CrmHomeBasePage homePage = new CrmHomeBasePage();
        LoginBasePage loginPage = new LoginBasePage();
        ZohoAppBasePage appPage = new ZohoAppBasePage();
        loginPage.gotoLogin();
        loginPage.login();
        appPage.gotoAppPage();
        homePage.gotoCrm();
    }

}

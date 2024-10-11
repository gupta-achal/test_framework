package com.w2a.pages;

import com.w2a.base.Page;
import com.w2a.pages.crm.accounts.CrmHomePage;

public class App extends Page {

    public static  LoginPage loginPage;
    public static HomePage homePage;
    public static ZohoAppPage zohoAppPage;
    public static CrmHomePage crmHomePage;

    public App(){

        this.loginPage = new LoginPage();
        this.homePage = new HomePage();
        this.zohoAppPage = new ZohoAppPage();
        this.crmHomePage = new CrmHomePage();
    }
}

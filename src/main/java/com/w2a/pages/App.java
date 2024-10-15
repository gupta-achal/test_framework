package com.w2a.pages;

import com.w2a.pages.crm.accounts.CrmHomeBasePage;

public class App {

    public static LoginBasePage loginPage;
    public static HomeBasePage homePage;
    public static ZohoAppBasePage zohoAppPage;
    public static CrmHomeBasePage crmHomePage;

    public App(){

        this.loginPage = new LoginBasePage();
        this.homePage = new HomeBasePage();
        this.zohoAppPage = new ZohoAppBasePage();
        this.crmHomePage = new CrmHomeBasePage();
    }
}

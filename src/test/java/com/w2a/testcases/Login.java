package com.w2a.testcases;

import com.w2a.annotation.Environment;
import com.w2a.pages.App;
import org.testng.annotations.Test;

public class Login {
    @Test(priority = 1)
    @Environment(Environment.Level.CAE)
    public void login(){
        App.homePage.gotoLogin();
        App.loginPage.login();
    }
}
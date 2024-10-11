package com.w2a.testcases;

import com.w2a.annotation.Production;
import com.w2a.pages.App;
import com.w2a.utilities.TestUtil;
import org.testng.annotations.Test;


public class Login extends BaseTest {

    @Test
    public void login(){
        App.homePage.gotoLogin();
        App.loginPage.login();

    }
}

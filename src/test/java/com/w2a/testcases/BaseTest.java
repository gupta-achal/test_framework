package com.w2a.testcases;

import com.w2a.base.Page;
import com.w2a.pages.App;

public class BaseTest{
    public App app;

    public BaseTest() {

        this.app = new App(); // Only initialize App, not WebDriver
    }
}


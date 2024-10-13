package com.w2a.testcases;

import com.w2a.pages.App;

public class BaseTest{
    protected App app;

 //   @BeforeMethod
    public void setup() {
        this.app = new App();

    }
}


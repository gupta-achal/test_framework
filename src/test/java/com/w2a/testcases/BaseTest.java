package com.w2a.testcases;

import com.w2a.base.Page;
import com.w2a.pages.App;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;

public class  BaseTest extends Page  {

    public static App app;
    public BaseTest(){
        start();
        this.app = new App();

    }



//    @BeforeTest
//    public void start(){
//        Page.start();
//    }

}

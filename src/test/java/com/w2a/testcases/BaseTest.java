package com.w2a.testcases;

import com.w2a.pages.App;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    protected App app;

    @BeforeClass // This will run before any test in the class
    public void setUp() {
        app = new App(); // Initialize the App instance
    }

    @AfterClass // This will run after all tests in the class
    public void tearDown() {
        // Any cleanup code can go here
        app = null; // Clean up the instance
    }
}


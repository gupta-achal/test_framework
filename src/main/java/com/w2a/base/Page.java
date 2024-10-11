package com.w2a.base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.w2a.utilities.ExcelReader;
import com.w2a.utilities.ExtentManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

public class Page {

    public static WebDriver driver;
    public static Properties config = new Properties();
    public static Properties OR = new Properties();
    public static FileInputStream fis;
    public static Logger log = Logger.getLogger("devpinoyLogger");
    public static WebDriverWait wait;
    public static String browser;
    public static ExcelReader excelReader;
    public static ExtentReports rep = ExtentManager.getInstance();
    public static ExtentTest test;
    public static AjaxElementLocatorFactory factory;

    public Page() {
        //start();
    }


    @BeforeTest
    public static void start(){
        initializeProperties();
        initializeWebDriver();
    }


    private static void initializeProperties() {
        // Load properties from configuration files
        try {
            log.info("Loading configuration properties...");
            fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\com\\w2a\\properties\\Config.properties");
            config.load(fis);
            log.info("Config file loaded successfully.");
        } catch (IOException e) {
            log.severe("Failed to load config file: " + e.getMessage());
            throw new RuntimeException(e);
        }

        // Load Object Repository properties
        try {
            log.info("Loading OR properties...");
            fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\com\\w2a\\properties\\OR.properties");
            OR.load(fis);
            log.info("OR file loaded successfully.");
        } catch (IOException e) {
            log.severe("Failed to load OR file: " + e.getMessage());
            throw new RuntimeException(e);
        }

        // Determine which browser to use
        browser = System.getenv("browser") != null && !System.getenv("browser").isEmpty() ?
                System.getenv("browser") : config.getProperty("browser");
        log.info("Browser set to: " + browser);

    }

    protected static void initializeWebDriver() {
        if (driver == null) {
            synchronized (Page.class) {
                if (driver == null) {
                    log.info("Initializing WebDriver...");
                    switch (browser.toLowerCase()) {
                        case "firefox":
                            log.info("Initializing Firefox driver...");
                             WebDriverManager.firefoxdriver().setup(); // Automatically download and setup FirefoxDriver
                            driver = new FirefoxDriver();
                            break;

                        case "chrome":
                            log.info("Initializing Chrome driver...");
                            WebDriverManager.chromedriver().clearDriverCache().setup(); // Automatically download and setup ChromeDriver
                            driver = createChromeDriver();
                            break;

                        case "ie":
                            log.info("Initializing Internet Explorer driver...");
                            WebDriverManager.iedriver().setup(); // Automatically download and setup IEDriver
                            driver = new InternetExplorerDriver();
                            break;

                        default:
                            log.severe("Unsupported browser: " + browser);
                            throw new IllegalArgumentException("Browser not supported: " + browser);
                    }

                }
            }
        }
        factory = new AjaxElementLocatorFactory(driver,3);
    }

    public static void configureDriver() {
        log.info("Navigating to test site: " + config.getProperty("testsiteurl"));
        driver.get(config.getProperty("testsiteurl"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        log.info("Driver setup completed successfully.");
    }

    private static WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        // Set Chrome preferences
        prefs.put("profile.default_content_setting_values.notifications", 2);
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--disable-extensions", "--disable-infobars", "--disable-gpu");

        // Headless mode
        if (Boolean.parseBoolean(config.getProperty("headless"))) {
            options.addArguments("--headless");
            log.info("Running Chrome in headless mode.");
        }

        return new ChromeDriver(options);
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static void click(WebElement element){
        try{
            element.click();
            test.log(LogStatus.INFO,"Clicking on the element: "+element.getText());
        }catch (ElementClickInterceptedException ex){
            ex.printStackTrace();
        }
        test.log(LogStatus.INFO, "Clicking on: "+element.getText());
    }

    public static void type(WebElement element, String message){
        try {
            element.sendKeys(message);
        }catch (ElementClickInterceptedException e){
            e.printStackTrace();
        }
        test.log(LogStatus.INFO, "Entering the text: "+message+" in the element: "+element.getText());
    }


    @AfterTest
    public static void quit() {
        log.info("Closing the browser...");
        if (driver != null) {
            driver.quit();
            log.info("Browser closed.");
            driver = null; // Reset driver to null to allow reinitialization
        }
    }



    public static ExcelReader getExcel(String path){
        excelReader = new ExcelReader(path);
        return excelReader;
    }
    public static ExcelReader getExcel(){
        excelReader = new ExcelReader(
                System.getProperty("user.dir") + "\\src\\test\\resources\\com\\w2a\\excel\\testdata.xlsx");
        return excelReader;
    }



}

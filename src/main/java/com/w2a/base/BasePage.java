package com.w2a.base;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.w2a.utilities.ExcelReader;
import com.w2a.utilities.ExtentManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

public class BasePage {

    public static WebDriver driver;
    protected static Properties config = new Properties();
    protected static Properties OR = new Properties();
    public static InputStream fis;
    public static Logger log = Logger.getLogger("devpinoyLogger");
    protected static WebDriverWait wait;
    public static String browser;
    public static ExcelReader excelReader;
    public static ExtentReports rep = ExtentManager.getInstance();
    public static ExtentTest test;
    public static AjaxElementLocatorFactory factory;
    public static Actions actions;
    public static Boolean runOnGrid;

    public BasePage() {
    }

    @BeforeTest
    public static void start() {
        initializeProperties();
        initializeWebDriver(); // Download the WebDriver without launching the browser
    }

    private static void initializeProperties() {
        // Load properties from configuration files
        try {
            log.info("Loading configuration properties...");
           // fis = new FileInputStream(System.getProperty("user.dir") + "/src/test/resources/com/w2a/properties/Config.properties");
            fis = BasePage.class.getClassLoader().getResourceAsStream("com/w2a/properties/Config.properties");
            config.load(fis);
            log.info("Config file loaded successfully.");
        } catch (IOException e) {
            log.severe("Failed to load config file: " + e.getMessage());
            throw new RuntimeException(e);
        }

        // Load Object Repository properties
        try {
            log.info("Loading OR properties...");
           // fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\com\\w2a\\properties\\OR.properties");
            fis = BasePage.class.getClassLoader().getResourceAsStream("com/w2a/properties/OR.properties");

            OR.load(fis);
            log.info("OR file loaded successfully.");
        } catch (IOException e) {
            log.severe("Failed to load OR file: " + e.getMessage());
            throw new RuntimeException(e);
        }

        // Determine which browser to use
        browser = System.getenv("browser") != null && !System.getenv("browser").isEmpty() ?
                System.getenv("browser") : config.getProperty("browser");
        runOnGrid = Boolean.parseBoolean(config.getProperty("runOnGrid", "false")); // Set flag for Grid execution
        String gridEnabled = System.getProperty("selenium.grid.enabled");
        System.out.println("**********************................"+System.getProperty("selenium.grid.enabled"));

        runOnGrid = Boolean.parseBoolean(gridEnabled);
        System.out.println("**********************"+runOnGrid);
        if(runOnGrid){
            log.info("Running the test cases on the grid.....................");
        }else {
            log.info("Running the test cases on no to the grid......");
        }
        log.info("Browser set to: " + browser);
    }

    // java -cp libs/* org.testng.TestNG com/w2a/runner/testng.xml

    protected static void initializeWebDriver() {
        log.info("Initializing the WebDriver.");
        if (runOnGrid) {
            setupRemoteDriver();
        } else {
            setupLocalDriver();
        }
    }

    protected static void setupLocalDriver() {
        log.info("Setting up WebDriver manager...");
        switch (browser.toLowerCase()) {
            case "firefox":
                log.info("Setting up Firefox driver...");
                WebDriverManager.firefoxdriver().clearDriverCache().setup(); // Download and setup FirefoxDriver
                break;

            case "chrome":
                log.info("Setting up Chrome driver...");
                WebDriverManager.chromedriver().clearDriverCache().setup(); // Download and setup ChromeDriver
                break;

            case "ie":
                log.info("Setting up Internet Explorer driver...");
                WebDriverManager.iedriver().clearDriverCache().setup(); // Download and setup IEDriver
                break;
            case "edge":
                log.info("Setting up Edge browser driver");
                WebDriverManager.edgedriver().clearDriverCache().setup();

            default:
                log.severe("Unsupported browser: " + browser);
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }
    }


    protected static void setupRemoteDriver() {
        log.info("Setting up WebDriver for Selenium Grid...");

        // Define WebDriver options for different browsers
        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--headless", "--disable-dev-shm-usage", "--no-sandbox", "--disable-gpu");
                chromeOptions.setExperimentalOption("prefs", getBrowserPreferences());
                log.info("Setting up Chrome driver for Grid...");
                driver = initializeRemoteWebDriver(chromeOptions);
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--headless");  // Add headless mode for Firefox
                log.info("Setting up Firefox driver for Grid...");
                driver = initializeRemoteWebDriver(firefoxOptions);
                break;

            case "ie":
                InternetExplorerOptions ieOptions = new InternetExplorerOptions();
                log.info("Setting up Internet Explorer driver for Grid...");
                driver = initializeRemoteWebDriver(ieOptions);
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--headless"); // Optional: Headless mode for Edge, if supported
                log.info("Setting up Edge driver for Grid...");
                driver = initializeRemoteWebDriver(edgeOptions);
                break;

            default:
                log.severe("Unsupported browser: " + browser);
                throw new IllegalArgumentException("Browser not supported: " + browser);
        }
    }


    private static WebDriver initializeRemoteWebDriver(Capabilities options) {
        try {
            return new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);
        } catch (MalformedURLException e) {
            log.severe("Grid Hub URL is incorrect: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static Map<String, Object> getBrowserPreferences() {
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        return prefs;
    }

    public static void configureDriver() {
        if (driver == null) {
            synchronized (BasePage.class) {
                if (driver == null) {
                    log.info("Initializing WebDriver...");
                    switch (browser.toLowerCase()) {
                        case "firefox":
                            driver = new FirefoxDriver();
                            break;

                        case "edge":
                            driver = new EdgeDriver();
                            break;

                        case "chrome":
                            driver = createChromeDriver();
                            break;

                        case "ie":
                            driver = new InternetExplorerDriver();
                            break;
                    }
                }
            }
        }

        factory = new AjaxElementLocatorFactory(driver, 15); // explicit wait....
        actions = new Actions(driver);

        log.info("Navigating to test site: " + config.getProperty("testsiteurl"));
        driver.get(config.getProperty("testsiteurl"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(runOnGrid ? 10 : 5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(runOnGrid ? 15 : 10));
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



    @AfterTest
    public static void quit() {
        log.info("Closing the browser...");
        if (driver != null) {
            try {
                driver.quit();
                log.info("Browser closed.");
            } catch (org.openqa.selenium.WebDriverException e) {
                log.warning("Session already closed or invalid session ID: " + e.getMessage());
            } finally {
                driver = null; // Reset driver to null to allow reinitialization
            }
        }
    }

    public static ExcelReader getExcel(String path) {
        excelReader = new ExcelReader(path);
        return excelReader;
    }

    public static ExcelReader getExcel() {
        String filePath = "com/w2a/excel/testdata.xlsx";
        InputStream file = ExcelReader.class.getClassLoader().getResourceAsStream(filePath);
//        excelReader = new ExcelReader(
//                System.getProperty("user.dir") + "\\src\\test\\resources\\com\\w2a\\excel\\testdata.xlsx");
        excelReader = new ExcelReader(filePath);
        return excelReader;
    }

//    public static ExcelReader getExcel() {
//        String filePath = "com/w2a/excel/testdata.xlsx";
//        InputStream file = ExcelReader.class.getClassLoader().getResourceAsStream(filePath);
//
//        if (file == null) {
//            throw new RuntimeException("File not found at path: " + filePath);
//        }
//
//        excelReader = new ExcelReader(file);
//        return excelReader;
//    }

}
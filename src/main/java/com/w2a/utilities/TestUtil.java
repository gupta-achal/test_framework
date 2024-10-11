package com.w2a.utilities;

import com.w2a.base.Page;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Hashtable;

public class TestUtil extends Page {

    private static ExcelReader excelReader = getExcel();
    public static String scrName;

    @DataProvider(name = "dp")
    public Object[][] getData(Method method){
        String sheetName = method.getName();
        int rows = excelReader.getRowCount(sheetName);
        int cols = excelReader.getColumnCount(sheetName);
        Object[][] data = new Object[rows-1][cols];
        Hashtable<String,String> hashtable = null;
        for(int r = 2; r<= rows; r++){
            hashtable = new Hashtable<>();
            for(int c= 0; c<cols;c++){
                hashtable.put(excelReader.getCellData(sheetName, c,1), excelReader.getCellData(sheetName, c,r));
            }
        }return data;
    }

    public static void captureScreen(){
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        Date d = new Date();
        scrName =
                d.toString().replace(":", "_").replace(" ", "_") + ".jpg";
        try {
            FileUtils.copyFile(scrFile, new File(System.getProperty("user.dr")+"\\target\\surefire-reports\\html\\"+scrName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isTestRunnable(String testName, ExcelReader excel){

        String sheetName="test_suite";
        int rows = excel.getRowCount(sheetName);


        for(int rNum=2; rNum<=rows; rNum++){

            String testCase = excel.getCellData(sheetName, "TCID", rNum);

            if(testCase.equalsIgnoreCase(testName)){

                String runmode = excel.getCellData(sheetName, "Runmode", rNum);

                if(runmode.equalsIgnoreCase("Y"))
                    return true;
                else
                    return false;
            }
        }
        return false;
    }
}

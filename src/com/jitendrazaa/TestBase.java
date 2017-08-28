package com.jitendrazaa;

import static org.testng.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

public class TestBase {
	 
	protected String firefox_driverPath = "\\webdrivers\\geckodriver.exe";
	protected String chrome_driverPath = "\\webdrivers\\chromedriver.exe";   
    StringBuffer verificationErrors = new StringBuffer();
    
    @BeforeClass(alwaysRun = true)
    public void setUp() throws Exception {
    	String rootPath = Paths.get("").toAbsolutePath().toString(); 
    	System.out.printf("Setup Thread Id : %s%n", Thread.currentThread().getId());
        System.setProperty("webdriver.chrome.driver", rootPath+chrome_driverPath); 
    }
     
    @AfterClass(alwaysRun = true)
    public void tearDown() throws Exception {  
      String verificationErrorString = verificationErrors.toString();
      if (!"".equals(verificationErrorString)) {
        fail(verificationErrorString);
      }
    }
    
    protected void wait(int seconds){
    	try{ 
    		for(int i=0;i<seconds;i++){
    			Thread.sleep(1000);
    			int pauseLeft = seconds - (i+1) ;
    			System.out.printf("pause for 1 seconds... "+pauseLeft+"sec left in Thread - "+Thread.currentThread().getId()+"\n");
    		}
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	} 
    }

    @AfterMethod
	public void takeScreenShotOnFailure(ITestResult testResult) throws IOException {
     
		if (testResult.getStatus() == ITestResult.FAILURE) { 
			File scrFile = ((TakesScreenshot)DriverFactory.getInstance().getDriver()).getScreenshotAs(OutputType.FILE);
			FileUtils.copyFile(scrFile, new File("errorScreenshots\\" + testResult.getName() + "-" 
					+ System.currentTimeMillis()  +  ".jpg"));
	   }     
		DriverFactory.getInstance().removeDriver();
	}

}

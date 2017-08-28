package com.jitendrazaa;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;
	
public class ConcurrentApexError extends TestBase{
	
	 		    
	    @Test(invocationCount = 20,threadPoolSize = 20)
	    public void verifyLonGRunningProcess() {  
	    	System.out.printf("Thread Id : %s%n", Thread.currentThread().getId());
	    	WebDriver driver = DriverFactory.getInstance().getDriver();
	    	DriverFactory.getInstance().getDriver().manage().timeouts().pageLoadTimeout(300, TimeUnit.SECONDS);
	    	DriverFactory.getInstance().getDriver().manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	    	
	    	driver.get("https://login.salesforce.com/");
	        driver.findElement(By.id("username")).clear();
	        driver.findElement(By.id("username")).sendKeys("SalesforceUserName"); 
	        
	        driver.findElement(By.id("password")).clear();
	        driver.findElement(By.id("password")).sendKeys("SomePassword");  
	        
	        driver.findElement(By.id("Login")).click();
	        wait(10);
	        driver.get("https://zaa-dev-ed.my.salesforce.com/apex/LongRunningProcesses");
	        for(int i=1,j=5,k=3;i<=100;i++,j=j+7,k=k+7){
	        	System.out.printf("Thread - "+Thread.currentThread().getId()+" - j_id0:timer"+i+":j_id"+k+":frm1:j_id"+j+"\n");
	        	driver.findElement(By.id("j_id0:timer"+i+":j_id"+k+":frm1:j_id"+j)).click();
	        }
	        wait(60*2); 
	        AssertJUnit.assertEquals(driver.findElement(By.id("j_id0:timer1:j_id3:frm1:rsltPanel")).getText(),"Process completed in 110 sec");
	         
	    }
	    
	   	    
	   
}

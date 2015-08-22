package suiteB;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.TestUtil;


public class TestCase_B3 extends TestBase{
	static int status=1;
	
//	Following is the list of status:
//		1--->PASS
//		2--->FAIL
//      3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest(){
		
		test = extent.startTest(this.getClass().getSimpleName(), "To verify that MUST NOT OCCUR rule is working correctly").assignCategory("Suite B");
		
	}
	
	@Test
	public void testcaseB1() throws Exception{
		
		boolean suiteRunmode=TestUtil.isSuiteRunnable(suiteXls, "B Suite");
		boolean testRunmode=TestUtil.isTestCaseRunnable(suiteBxls,this.getClass().getSimpleName());
		boolean master_condition=suiteRunmode && testRunmode;
		
		if(!master_condition){
			
			status=3;//excel
			test.log(LogStatus.SKIP, "Skipping test case "+this.getClass().getSimpleName()+" as the run mode is set to NO");
			throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
		
		}
		
		test.log(LogStatus.INFO,this.getClass().getSimpleName()+" execution starts--->");
		try{
		
		
			
			String search_query="cat -dog goat";
			
			openBrowser();
			clearCookies();
			maximizeWindow();
			
			ob.navigate().to(CONFIG.getProperty("testSiteName"));
			Thread.sleep(8000);
			
			//login using TR credentials
			login();
			Thread.sleep(15000);
			
			//Type into the search box and get search results
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(search_query);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			Thread.sleep(4000);
			
			//Put the urls of all the search results documents in a list and test whether documents contain searched keyword or not
			List<WebElement> searchResults=ob.findElements(By.xpath(OR.getProperty("searchResults_links")));
			ArrayList<String> urls=new ArrayList<String>();
			for(int i=0;i<searchResults.size();i++){
				
				urls.add(searchResults.get(i).getAttribute("href"));
			}
			boolean condition1;
			String pageText;
			ArrayList<Integer> error_list=new ArrayList<Integer>();
			int count=0;
			for(int i=0;i<urls.size();i++){
				
				ob.navigate().to(urls.get(i));
				Thread.sleep(5000);
				ob.findElement(By.xpath(OR.getProperty("details_link"))).click();
				Thread.sleep(15000);
				pageText=ob.getPageSource().toLowerCase();
				condition1=!(pageText.contains("dog"));
				System.out.println(condition1);
				if(condition1){
					
					count++;
				}
				else
				{
					
					error_list.add(i+1);
				}
				
				
				
			}
			String message="";
			for(int i=0;i<error_list.size();i++){
				
				message=message+error_list.get(i)+",";
				
			}
			
			
			if(!compareNumbers(urls.size(),count)){
				
				test.log(LogStatus.FAIL, "MUST NOT OCCUR rule not working correctly");//extent reports
				status=2;//excel
				test.log(LogStatus.INFO,"Issues are in the following documents:"+message);//extent reports
			}
			
			
			closeBrowser();
		}
		catch(Throwable t){
			test.log(LogStatus.FAIL,"Error:"+t);//extent reports
			ErrorUtil.addVerificationFailure(t);//testng
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_something_unexpected_happened")));//screenshot
			closeBrowser();
		}
		
		test.log(LogStatus.INFO,this.getClass().getSimpleName()+" execution ends--->");
	}
	

	@AfterTest
	public void reportTestResult(){
		extent.endTest(test);
		
		if(status==1)
			TestUtil.reportDataSetResult(suiteBxls, "Test Cases", TestUtil.getRowNum(suiteBxls,this.getClass().getSimpleName()), "PASS");
		else if(status==2)
			TestUtil.reportDataSetResult(suiteBxls, "Test Cases", TestUtil.getRowNum(suiteBxls,this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteBxls, "Test Cases", TestUtil.getRowNum(suiteBxls,this.getClass().getSimpleName()), "SKIP");
	
	}
	

	
	

}

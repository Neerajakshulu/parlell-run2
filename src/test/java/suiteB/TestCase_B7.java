package suiteB;



import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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


public class TestCase_B7 extends TestBase{
	static int status=1;
	
//	Following is the list of status:
//		1--->PASS
//		2--->FAIL
//      3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception{
		
		String var=xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),Integer.parseInt(this.getClass().getSimpleName().substring(10)+""),1);
		test = extent.startTest(var, "Verify that no search results get displayed if search engine doesn't interpret the query and that a proper message gets displayed regarding that").assignCategory("Suite B");
		
	}
	
	@Test
	public void testcaseB7() throws Exception{
		
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
		
		
			
			String search_query="ghdscjgwedkjdgcdjkdw";
			
			openBrowser();
			clearCookies();
			maximizeWindow();
			
			ob.navigate().to(host);
//			ob.navigate().to(CONFIG.getProperty("testSiteName"));
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
			System.out.println(searchResults.size());
			
			if(!compareNumbers(0,searchResults.size())){
				
				test.log(LogStatus.FAIL, "Search resuls getting displayed even when user searches for an absurd keyword");//extent reports
				status=2;//excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_search_results_getting_displayed_even_when_user_searches_absurd_keyword")));//screenshot	
				
				
			}
			
			
			closeBrowser();
		}
		catch(Throwable t){
			test.log(LogStatus.FAIL,"Something unexpected happened");//extent reports
			//next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO,errors.toString());//extent reports
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

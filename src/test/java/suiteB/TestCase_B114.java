package suiteB;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ErrorUtil;
import util.TestUtil;

public class TestCase_B114 extends TestBase {
static int status=1;
	
//	Following is the list of status:
//		1--->PASS
//		2--->FAIL
//      3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception{
		
		String var=xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),Integer.parseInt(this.getClass().getSimpleName().substring(10)+""),1);
		test = extent.startTest(var, "Verify that left navigation pane content type is retained when user navigates back to PATENTS search results page from record view page").assignCategory("Suite B");
		
	}
	
	@Test
	public void testcaseB114() throws Exception{
		
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
		
		
			
			String search_query="cat dog";
			
			openBrowser();
			clearCookies();
			maximizeWindow();
			
			//ob.navigate().to(CONFIG.getProperty("testSiteName"));
			ob.navigate().to(host);
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);
			
			//login using TR credentials
			login();
//			
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_button")), 30);
			
			//Type into the search box and get search results
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(search_query);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
//			
			waitForAjax(ob);
			List<WebElement> content_type_tiles=ob.findElements(By.xpath("//*[contains(@class,'content-type-selector ng-scope')]"));
			content_type_tiles.get(2).click();
			waitForAjax(ob);			
			List<WebElement> searchResults=ob.findElements(By.xpath(OR.getProperty("searchResults_links")));
			jsClick(ob, searchResults.get(8));
			waitForPageLoad(ob);
			
			
//			ob.navigate().back();
			JavascriptExecutor js = (JavascriptExecutor)ob;
			js.executeScript("window.history.back();");
			waitForPageLoad(ob);
			waitForAjax(ob);
			
			
			
			//System.out.println("list2-->"+al2);
			
			try{
				content_type_tiles=ob.findElements(By.xpath("//*[contains(@class,'content-type-selector ng-scope')]"));
				
				Assert.assertTrue(content_type_tiles.get(2).getAttribute("class").contains("active"));
				test.log(LogStatus.PASS, "Left navigation panel content type is retained in patent search result page after page navigation");
				}
				catch(Throwable t){
					t.printStackTrace();
					test.log(LogStatus.FAIL, "Left navigation panel content type is not retained in patent search result page after page navigation");//extent reports
					test.log(LogStatus.INFO, "Error--->"+t);
					ErrorUtil.addVerificationFailure(t);
					status=2;//excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_incorrect_documents_getting_displayed")));//screenshot	
					
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

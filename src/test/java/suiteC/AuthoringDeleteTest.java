package suiteC;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
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

public class AuthoringDeleteTest extends TestBase {
	
	String runmodes[]=null;
	static int count=-1;
	static int totalCommentsBeforeDeletion=0;
	static int totalCommentsAfterDeletion=0;
	static int time=15;
	
	static boolean fail=false;
	static boolean skip=false;
	static int status=1;
	
	// Checking whether this test case should be skipped or not
		@BeforeTest
		public void beforeTest() {
			test = extent.startTest("AuthoringTest", "Validate Authoring Delete Comments").assignCategory("Suite C");
//			test.log(LogStatus.INFO, "****************************");
			//load the runmodes of the tests			
			runmodes=TestUtil.getDataSetRunmodes(suiteCxls, "AuthoringTest");
			System.out.println("Run modes-->"+runmodes.length);
		}
	
			
	@Test(dataProvider="getTestData")
	public void testLoginTRAccount(String username,
			String password,
			String article,
			String completeArticle, String addComments) throws Exception  {
		
		try {
			boolean suiteRunmode=TestUtil.isSuiteRunnable(suiteXls, "C Suite");
			boolean testRunmode=TestUtil.isTestCaseRunnable(suiteCxls,"AuthoringTest");
			boolean master_condition=suiteRunmode && testRunmode;
			
			if(!master_condition) {
				status=3;
//			TestUtil.reportDataSetResult(suiteCxls, "Test Cases", TestUtil.getRowNum(suiteCxls,"AuthoringTest"), "SKIP");
				test.log(LogStatus.SKIP, "Skipping test case "+"AuthoringTest"+" as the run mode is set to NO");
				throw new SkipException("Skipping Test Case"+"AuthoringTest"+" as runmode set to NO");//reports
			}
			
			
			// test the runmode of current dataset
			count++;
			if(!runmodes[count].equalsIgnoreCase("Y")) {
				test.log(LogStatus.INFO, "Runmode for test set data set to no "+count);
				skip=true;
//			TestUtil.reportDataSetResult(suiteCxls, "AuthoringTest", count+2, "SKIP");
				throw new SkipException("Runmode for test set data set to no "+count);
			}
			test.log(LogStatus.INFO,"AuthoringTest"+" execution starts for data set #"+ count+"--->");
			//test.log(LogStatus.INFO,searchKey);
			
					//selenium code
					openBrowser();
					clearCookies();
					maximizeWindow();
					ob.get(CONFIG.getProperty("devStable_url"));
					ob.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
					ob.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
					LoginTR.waitForTRHomePage();
					performAuthoringCommentOperations(username, password, article, completeArticle, addComments);
					
		} catch (Throwable t) {
			test.log(LogStatus.FAIL,"Error:"+t);//extent reports
			ErrorUtil.addVerificationFailure(t);//testng
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_something_unexpected_happened")));//screenshot
			closeBrowser();
		}
	}
	
	//@Test(dependsOnMethods="testLoginTRAccount",dataProvider="getTestData")
	public void performAuthoringCommentOperations(String username,
			String password,
			String article,
			String completeArticle, String addComments) throws Exception  {
		LoginTR.waitForTRHomePage();
		LoginTR.enterTRCredentials(username, password);
		LoginTR.clickLogin();
		LoginTR.searchArticle(article);
		LoginTR.chooseArticle(completeArticle);
		deleteComments(); 
	}
	
	
	
	//@Test(dependsOnMethods="performOperations")
	@AfterMethod
	public void reportDataSetResult() {
		if(skip)
			TestUtil.reportDataSetResult(suiteCxls, "AuthoringTest", count+2, "SKIP");
		
		else if(fail) {
			
			status=2;
			TestUtil.reportDataSetResult(suiteCxls, "AuthoringTest", count+2, "FAIL");
		}
		else
			TestUtil.reportDataSetResult(suiteCxls, "AuthoringTest", count+2, "PASS");
		
		
		skip=false;
		fail=false;
		

	}
	
	@AfterTest
	public void reportTestResult() {
		
		extent.endTest(test);
		
		if(status==1)
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases", TestUtil.getRowNum(suiteCxls,this.getClass().getSimpleName()), "PASS");
		else if(status==2)
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases", TestUtil.getRowNum(suiteCxls,this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases", TestUtil.getRowNum(suiteCxls,this.getClass().getSimpleName()), "SKIP");
		
		closeBrowser();
	}
	

	@DataProvider
	public Object[][] getTestData() {
		return TestUtil.getData(suiteCxls, "AuthoringTest") ;
	}
	
	public void deleteComments() throws Exception {
		AuthoringAppreciateTest.scrollingToElementofAPage();
		totalCommentsBeforeDeletion=getTotalComments();
		System.out.println("Before Deletion count --->"+totalCommentsBeforeDeletion);
		ob.findElement(By.cssSelector("button[class='webui-icon webui-icon-trash edit-comment-icon'][ng-click='deleteThis(comment.id)']")).click();
		waitUntilText("Confirmation to Delete Comment");
		IsElementPresent(OR.getProperty("tr_authoring_delete_confirmation_ok_button_css"));
		ob.findElement(By.cssSelector(OR.getProperty("tr_authoring_delete_confirmation_ok_button_css"))).click();
		totalCommentsAfterDeletion=getTotalComments();
		System.out.println("TOTAL COMMENTS AFTER DELETION --->"+totalCommentsAfterDeletion);
		
		if(!(totalCommentsBeforeDeletion>totalCommentsAfterDeletion)){
			status=2;
			throw new Exception("Comment Deletion not happended");
		}
		
	}
	
	public int getTotalComments()  {
		return Integer.parseInt(ob.findElement(By.cssSelector(TestBase.OR.getProperty("tr_authoring_commentCount_css"))).getText());
	}
	
	/**
	 * wait for until expected text present
	 * @param text
	 */
	public  void waitUntilText(final String text) {
		try {
			(new WebDriverWait(ob, time))
					.until(new ExpectedCondition<Boolean>() {
						public Boolean apply(WebDriver d) {
							try {
								return Boolean.valueOf(d.getPageSource()
										.contains(text));
							} catch (Exception e) {
								return Boolean.valueOf(false);
							}
						}
					});
		} catch (TimeoutException te) {
			throw new TimeoutException("Failed to find text: " + text
					+ ", after waiting for " + time + "ms");
		}
	}
	
	/**
	 * Method for wait until Element is Present
	 * @param locator
	 * @throws Exception, When NoSuchElement Present
	 */
	public  void IsElementPresent(String locator) throws Exception {
		try{
		 count = ob.findElements(By.cssSelector(locator)).size();
		} catch (NoSuchElementException ne) {
			throw new NoSuchElementException("Failed to find element [Locator = {"
					+ locator+ "}]");
		}
		
		if(!(count > 0)){
			throw new Exception("Unable to find Element...Element is not present");
		}
	}
}

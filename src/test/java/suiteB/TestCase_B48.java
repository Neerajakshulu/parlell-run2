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

public class TestCase_B48 extends TestBase {
	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {

		String var = xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),
				Integer.parseInt(this.getClass().getSimpleName().substring(10) + ""), 1);
		test = extent
				.startTest(var,
						"Verify that ALL option is selected in the left navigation pane by default when user searches using ALL option in the search drop down")
				.assignCategory("Suite B");

	}

	@Test
	public void testcaseB48() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "B Suite");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteBxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {

			

			openBrowser();
			clearCookies();
			maximizeWindow();

			// Navigating to the NEON login page
			ob.navigate().to(host);
			//ob.navigate().to(CONFIG.getProperty("testSiteName"));
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);
			// login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("search_button")), 30);
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys("bio");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			waitForElementTobeVisible(ob, By.xpath("//li[@class='content-type-selector ng-scope active']"), 30);
			String text=ob.findElement(By.xpath("//li[@class='content-type-selector ng-scope active']")).getText();
			System.out.println(text);
			
			if(!StringContains(text,"All")){
				
				test.log(LogStatus.FAIL, "ALL option not selected in the left navigation pane by default when user searches using ALL option in the search drop down");// extent report
            	status = 2;// excel
            	test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
            			captureScreenshot(this.getClass().getSimpleName() + "_ALL_option_not_selected_in_the_left_navigation_pane_by_default_when_user_searches_using_ALL_option_in_the_search_drop_down")));// screenshot

				
			}
			
			closeBrowser();

		} 
		
		catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
																		// reports
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}


	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		if (status == 1)
			TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
					TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "PASS");
		else if (status == 2)
			TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
					TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteBxls, "Test Cases",
					TestUtil.getRowNum(suiteBxls, this.getClass().getSimpleName()), "SKIP");

	}

}

package suiteB;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.ErrorUtil;
import util.TestUtil;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;

public class TestCase_B93 extends TestBase {
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
						"Verify that profile page of a person gets displayed when clicks on any PEOPLE search result in ALL search results page")
						.assignCategory("Suite B");

	}

	@Test
	public void testcaseB92() throws Exception {

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
			String userName="STQABLR";

			openBrowser();
			clearCookies();
			maximizeWindow();

			// Navigating to the NEON login page
			ob.navigate().to(host);
			Thread.sleep(8000);

			// login using TR credentials
			login();
			Thread.sleep(15000);
			// Searching for patents
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(userName);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			Thread.sleep(8000);
			
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("tr_search_people_profilename_link_xpath")),8);
			ob.findElement(By.xpath(OR.getProperty("tr_search_people_profilename_link_xpath"))).click();
			boolean isPresent=ob.findElement(By.xpath("//h2[contains(text(),'Interests')]")).isDisplayed();
			if (isPresent) {
				test.log(LogStatus.PASS, "Profile page of a person is displayed as expected");
			} else {
				status = 2;
				test.log(LogStatus.FAIL, "Profile page of a person is not displayed as expected");
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_something_wrong_happened")));// screenshot
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

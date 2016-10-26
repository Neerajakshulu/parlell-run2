package iam;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;

public class IAM027 extends TestBase {

	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("IAM");
	}

	@Test
	public void testcaseA27() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
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
			try {
				maximizeWindow();
			} catch (Throwable t) {

				System.out.println("maximize() command not supported in Selendroid");
			}
			clearCookies();

			// Navigate to deep link account page
			ob.navigate().to(CONFIG.getProperty("helpLink"));
			login();
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.NEON_HELP_AND_FEEDBACK_PAGE_SEND_FEEDBACK_LINK_CSS);
			String str = ob.findElement(By.cssSelector(OnePObjectMap.NEON_HELP_AND_FEEDBACK_PAGE_SEND_FEEDBACK_LINK_CSS.toString())).getText();
			logger.info("Title : " + str);
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.NEON_HELP_AND_FEEDBACK_PAGE_SUBMIT_SUPPORT_LINK_CSS);
			String feedBack = ob.findElement(By.cssSelector(OnePObjectMap.NEON_HELP_AND_FEEDBACK_PAGE_SUBMIT_SUPPORT_LINK_CSS.toString())).getText();
			logger.info("Emai Text : " + feedBack);
			BrowserWaits.waitTime(2);
			try {
				Assert.assertTrue(str.contains("Send feedback to the Project Neon team")
						&& feedBack.contains("Report a problem or submit a support request"));
				test.log(LogStatus.PASS, "Deep link is working correctly for help page");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Deep link is not working correctly for help page" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString()); // reports
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "Deep_link_is_not_working_correctly_ for_help_page")));// screenshot
			}

			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.HOME_ONEP_APPS_CSS);
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_IMAGE_CSS);
			/*
			if (!checkElementPresence("ul_name")) {

				test.log(LogStatus.FAIL, "Existing Neon user credentials are not working fine");// extent
																								// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_existing_Neon_User_credentials_not_working_fine")));// screenshot
				closeBrowser();

			}

			// Verify that profile name gets displayed correctly
			if (!checkElementPresence("header_label")) {

				test.log(LogStatus.FAIL, "Incorrect profile name getting displayed");// extent
																						// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_incorrect_profile_name_getting_displayed")));// screenshot
				closeBrowser();

			}
*/
			logout();
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.NEON_LANDING_PAGE_LOGGIN_BANNER_CSS);
			/*waitForElementTobeVisible(ob, By.xpath(OR.getProperty("login_banner")), 8);
			if (!checkElementPresence("login_banner")) {

				test.log(LogStatus.FAIL, "User not able to logout successfully");// extent
																					// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_user_unable_to_logout_successfully")));// screenshot
				closeBrowser();

			}*/

			closeBrowser();

		}

		catch (Throwable t) {

			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
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

		/*
		 * if(status==1) TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "PASS"); else if(status==2)
		 * TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "SKIP");
		 */
	}
}

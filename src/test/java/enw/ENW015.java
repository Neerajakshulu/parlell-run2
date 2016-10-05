package enw;

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
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;

public class ENW015 extends TestBase {

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
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("ENW");
	}

	@Test
	public void testcaseENW015() throws Exception {
		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {

			openBrowser();
			maximizeWindow();
			clearCookies();

			ob.get(host + CONFIG.getProperty("appendENWAppUrl"));
			loginAs("NONMARKETUSEREMAIL", "NONMARKETUSERPASSWORD");
			if (ob.findElement(By.xpath(OnePObjectMap.ENW_CONTINUE_DIALOG_BOX_XPATH.toString())).isEnabled()) {
				// ob.findElement(By.xpath(OnePObjectMap.ENW_CONTINUE_BUTTON.toString())).click();
				ob.findElement(By.xpath(OR.getProperty("ENW_CONTINUE_BUTTON"))).click();
			}
			ob.findElement(By.xpath(OnePObjectMap.ENW_PROFILE_USER_ICON_XPATH.toString())).click();
			ob.findElement(By.linkText(OnePObjectMap.ENW_HOME_PROFILE_FLYOUT_FEEDBACK_LINK.toString())).click();
			// ob.findElement(By.xpath(OR.getProperty("ENW_Feedback"))).click();

			String newWindow = switchToNewWindow(ob);
			if (newWindow != null) {
				if (ob.getCurrentUrl().contains("app.qc.endnote.com/EndNoteWeb.html?func=feedBack")) {

					logger.info(
							"feedBack is opened in the new Window and it takes user to the existing (BAU) version of "
									+ "the Endnote Feedback");
				}
			} else {
				test.log(LogStatus.FAIL, "New window is not displayed and content is not matching");
				Assert.assertEquals(true, false);
			}
			try {

				// Assert.assertTrue();
				test.log(LogStatus.PASS,
						"feedBack is opened in the new Window it takes user to the existing (BAU) version of "
								+ "the Endnote Feedback");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Feedback New window is not displayed and content is not matching");// extent
				ErrorUtil.addVerificationFailure(t); // reports
				status = 2;// excel
				test.log(LogStatus.INFO,
						"Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
								+ "Feedback New window is not displayed and content is not matching")));// screenshot
			}
		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
																		// reports
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			ErrorUtil.addVerificationFailure(t);// testng
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
					captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);
		closeBrowser();
	}
}

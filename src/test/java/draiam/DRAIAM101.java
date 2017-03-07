package draiam;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.StringUtils;
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
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;

public class DRAIAM101 extends TestBase {

	static int status = 1;
	static boolean fail = false;

	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("DRAIAM");

	}
	@Test
	public void testcaseDRAIAM101() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;
		// static boolean fail = false;

		if (!master_condition) {
			status = 3;
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		try {
			openBrowser();
			maximizeWindow();
			clearCookies();
			ob.navigate().to(host + CONFIG.getProperty("appendDRAAppUrl"));
			test.log(LogStatus.PASS, "User is succeccfully sent to the DRA landing page. ");
			//pf.getDraPageInstance(ob).validateInvalidCredentialsErrorMsg(test);	
		//	ClarivateAnalyticslogo();
			BrowserWaits.waitTime(6);
			JavascriptExecutor jse = (JavascriptExecutor)ob;
			jse.executeScript("window.scrollBy(0,250)", "");
			BrowserWaits.waitTime(5);
			WebElement ImageFile = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.LOGIN_PAGE_LOGO_IMG_XPATH);
			Boolean ImagePresent = (Boolean) ((JavascriptExecutor) ob).executeScript(
					"return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0",
					ImageFile);
			try {
				Assert.assertTrue(ImagePresent);
				if (!ImagePresent)
		        {
		        	
		        	test.log(LogStatus.FAIL, "DRA Landing page the Clarivate Logo is not diplayed");
		        }
		        else
		        {
		        	test.log(LogStatus.PASS, "DRA Landing page displays the Clarivate Logo");
		        }

				test.log(LogStatus.PASS, " Image is present and User name is not hyper linked");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, " Image is not present and User is hyperlinked");// extent
				ErrorUtil.addVerificationFailure(t); // reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "mage is not present and User is hyperlinked")));// screenshot
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
			ErrorUtil.addVerificationFailure(t);
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

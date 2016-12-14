package ipa;

import java.util.Random;

import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.BrowserWaits;
import util.ExtentManager;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class IPA112 extends TestBase {

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
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("IPA");
	}

	@Test
	public void updateTitleInfo() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP, "Skipping test case " + this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {
			// String dtitle = this.getClass().getSimpleName() + "_Save_Title" + "_" + getCurrentTimeStamp();
			// String ddesc = this.getClass().getSimpleName() + "_Save_Desc_" +
			// RandomStringUtils.randomAlphanumeric(100);
			String newtitle = this.getClass().getSimpleName() + "_Updated_Save_Title" + "_" + getCurrentTimeStamp();
			openBrowser();
			maximizeWindow();
			clearCookies();
			ob.navigate().to(host + CONFIG.getProperty("appendIPAAppUrl"));
			pf.getIpaPage(ob).loginToIPA("ipauser1@tr.com", "Neon@123");
			pf.getIpaSavedSearchpage(ob).clickOnSavedWork();
			test.log(LogStatus.PASS, "navigated to saved data page");
			BrowserWaits.waitTime(3);
			Random rand = new Random();
			int value = rand.nextInt(5);
			pf.getIpaSavedSearchpage(ob).randomUpdate(value);
			pf.getIpaPage(ob).enterSavedatatitle(newtitle);
			BrowserWaits.waitTime(4);
			pf.getIpaSavedSearchpage(ob).clickOnSaveButtonInTile();
			test.log(LogStatus.PASS, "Title is updated with new title");
			BrowserWaits.waitTime(3);
			closeBrowser();

		} catch (Exception e) {
			logFailureDetails(test, "User is not able tpo login", "Screenshot for login");
			closeBrowser();
		}

	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);
	}
}

package suiteC;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.HeaderFooterLinksPage;
import pages.ProfilePage;
import util.ErrorUtil;
import util.TestUtil;

public class DeleteDraftPostFromProfile extends TestBase{

	static int status = 1;

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		String var=xlRead2(returnExcelPath('C'),this.getClass().getSimpleName(),1);
		test = extent.startTest(var, "Verfiy that user is able to delete the draft post from the list in their profile")
				.assignCategory("Suite C");

	}

	@Test
	public void deleteDraftPostFromProfile() throws Exception {
		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "C Suite");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteCxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		try {
			String postString="DraftPostCreationTest"+RandomStringUtils.randomNumeric(10);
			openBrowser();
			maximizeWindow();
			clearCookies();

			// Navigate to TR login page and login with valid TR credentials
			ob.navigate().to(host);
			//ob.get(CONFIG.getProperty("testSiteName"));
			loginAs("USERNAME1","PASSWORD1");
			test.log(LogStatus.INFO, "Logged in to NEON");
			HeaderFooterLinksPage.clickOnProfileLink();
			test.log(LogStatus.INFO, "Navigated to Profile Page");
			ProfilePage.clickOnPublishPostButton();
			ProfilePage.enterPostTitle(postString);
			test.log(LogStatus.INFO, "Entered Post Title");
			ProfilePage.enterPostContent(postString);
			test.log(LogStatus.INFO, "Entered Post Content");
			ProfilePage.clickOnPostCancelButton();
			ProfilePage.clickOnPostCancelKeepDraftButton();
			test.log(LogStatus.INFO, "Saved the draft post");
			ProfilePage.clickOnDraftPostsTab();
			int postCountBefore=ProfilePage.getDraftPostsCount();
			test.log(LogStatus.INFO, "Draft Post count:"+postCountBefore);
			ProfilePage.deleteDraftPost(postString);
			int postCountAfter=ProfilePage.getDraftPostsCount();
			test.log(LogStatus.INFO, "Draft Post count:"+postCountAfter);
			
			try {
				Assert.assertEquals(postCountBefore-1, postCountAfter);
				test.log(LogStatus.PASS, "Post count is decremented after the post deletion");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Post count is not decremented after the post deletion");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "Post_count_validation_failed")));// screenshot

			}
			
			try {
				Assert.assertTrue(!ProfilePage.getAllDraftPostTitle().contains(postString) );
				test.log(LogStatus.PASS, "Deleted post is not displayed under posts tab in profile");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Deleted post is displayed under posts tab in profile");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "Post_creation_validation_failed")));// screenshot

			}
		
			logout();
			closeBrowser();
		} catch (Throwable t) {
			t.printStackTrace();
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent
																		// reports
			// next 3 lines to print whole testng error in report
			status = 2;// excel
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

		if (status == 1)
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases",
					TestUtil.getRowNum(suiteCxls, this.getClass().getSimpleName()), "PASS");
		else if (status == 2)
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases",
					TestUtil.getRowNum(suiteCxls, this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteCxls, "Test Cases",
					TestUtil.getRowNum(suiteCxls, this.getClass().getSimpleName()), "SKIP");

	}

	
}

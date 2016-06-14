package Authoring;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pages.PageFactory;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class Authoring24 extends TestBase {

	
	static int status = 1;
	PageFactory pf = new PageFactory();

	// Following is the list of status:
	// 1--->PASS
	// 2--->FAIL
	// 3--->SKIP
	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead2(returnExcelPath('C'), this.getClass().getSimpleName(), 1);
		test = extent
				.startTest(var, "Verify that user is not able to edit and delete the comment added by other users")
				.assignCategory("Authoring");

	}

	@Test
	public void testEditOtherUserComments() throws Exception {
		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "Authoring");
		boolean testRunmode = TestUtil.isTestCaseRunnable(authoringxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP, "Skipping test case " + this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

		try {

			openBrowser();
			maximizeWindow();
			clearCookies();

			// Navigate to TR login page and login with valid TR credentials
			// ob.navigate().to(host);
			ob.get(CONFIG.getProperty("testSiteName"));
			loginAs("USERNAME16","PASSWORD16");
			String PROFILE_NAME = LOGIN.getProperty("PROFILE16");
			pf.getHFPageInstance(ob).searchForText("Biology");
			pf.getSearchResultsPageInstance(ob).searchForArticleWithComments();

			waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("tr_authoring_comments_xpath")), 80);
			List<WebElement> commentsList = ob.findElements(By.xpath(OR.getProperty("tr_authoring_comments_xpath")));
			System.out.println(commentsList.size());
			String commentText;

			for (int i = 0; i < commentsList.size(); i++) {
				commentText = commentsList.get(i).getText();
				if (!commentText.contains(PROFILE_NAME) && !commentText.contains("Comment deleted")) {
					try {
						ob.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						boolean isPresent = commentsList.get(i)
								.findElement(By.xpath(OR.getProperty("tr_authoring_comments_Edit_css"))).isDisplayed();
						Assert.assertTrue(isPresent);

						test.log(LogStatus.FAIL, "Uesr is able to edit the comments added by others");
						status = 2;
						test.log(
								LogStatus.INFO,
								"Snapshot below: "
										+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
												+ "Edit_comment_validation_failed")));// screenshot

					} catch (Throwable t) {

						test.log(LogStatus.PASS, "Uesr is not able to edit the comments added by others");
					}
					break;
				}

			}
			for (int i = 0; i < commentsList.size(); i++) {
				commentText = commentsList.get(i).getText();
				if (!commentText.contains(PROFILE_NAME) && !commentText.contains("Comment deleted")) {
					try {
						ob.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
						boolean isPresent = commentsList.get(i)
								.findElement(By.xpath(OR.getProperty("tr_authoring_comments_delete_css")))
								.isDisplayed();
						Assert.assertTrue(isPresent);

						test.log(LogStatus.FAIL, "Uesr is able to delete the comments added by others");
						status = 2;
						test.log(
								LogStatus.INFO,
								"Snapshot below: "
										+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
												+ "Delete_comment_validation_failed")));// screenshot

					} catch (Throwable t) {

						test.log(LogStatus.PASS, "Uesr is not able to delete the comments added by others");
					}
					break;
				}

			}

			pf.getLoginTRInstance(ob).logOutApp();
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

			test.log(
					LogStatus.INFO,
					"Snapshot below: "
							+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
									+ "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}
		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		/*
		 * if (status == 1) TestUtil.reportDataSetResult(authoringxls, "Test Cases", TestUtil.getRowNum(authoringxls,
		 * this.getClass().getSimpleName()), "PASS"); else if (status == 2) TestUtil.reportDataSetResult(authoringxls,
		 * "Test Cases", TestUtil.getRowNum(authoringxls, this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(authoringxls, "Test Cases", TestUtil.getRowNum(authoringxls,
		 * this.getClass().getSimpleName()), "SKIP");
		 */

	}

}

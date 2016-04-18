package Authoring;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

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

public class Authoring15 extends TestBase {

	
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
		test = extent.startTest(var, "Verify that user is able to cancel the remove flag action").assignCategory(
				"Authoring");

	}

	@Test
	public void testFlagInUserComments() throws Exception {
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
			ob.navigate().to(host);
			login();
			String PROFILE_NAME = LOGIN.getProperty("PROFILE1");;
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 20);
			ob.findElement(By.cssSelector(OR.getProperty("tr_search_box_css"))).sendKeys("biology");
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			pf.getpostRVPageInstance(ob).searchForArticleWithComments();
			pf.getpostRVPageInstance(ob).loadComments();
			// pf.getLoginTRInstance(ob).logOutApp();
			waitForAllElementsToBePresent(ob, By.xpath(OR.getProperty("tr_authoring_comments_xpath")), 40);
			List<WebElement> commentsList = ob.findElements(By.xpath(OR.getProperty("tr_authoring_comments_xpath")));
			String commentText;
			int commentsCount = 0;
			WebElement flagWe;
			for (int i = 0; i < commentsList.size(); i++) {
				commentText = commentsList.get(i).getText();

				if (!commentText.contains(PROFILE_NAME) && !commentText.contains("Comment deleted")) {
					flagWe = commentsList.get(i).findElement(
							By.xpath(OR.getProperty("tr_authoring_comments_flag_dynamic_xpath")));
					if (flagWe.getAttribute("class").contains("flag-inactive")) {
						jsClick(ob, flagWe);
						commentsCount = i;
						break;
					}
				}
			}
			waitForElementTobeVisible(ob,
					By.cssSelector(OR.getProperty("tr_authoring_comments_flag_reason_modal_css")), 180);
			jsClick(ob, ob.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_flag_reason_chkbox_css"))));
			jsClick(ob, ob.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_flag_button_modal_css"))));
			// waitForAllElementsToBePresent(ob,
			// By.xpath(OR.getProperty("tr_authoring_comments_flag_xpath")),
			// 40);
			try {
				Thread.sleep(6000);// Wait for flag modal to disappear
				boolean IsFlagged = commentsList.get(commentsCount)
						.findElement(By.xpath(OR.getProperty("tr_authoring_comments_flag_dynamic_xpath")))
						.getAttribute("class").contains("flag-active");
				Assert.assertTrue(IsFlagged);
				test.log(LogStatus.PASS, "User comment is flagged");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "User comment is not flagged");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "Flag_validation_for_comments_failed")));// screenshot

			}
			commentsList = ob.findElements(By.xpath(OR.getProperty("tr_authoring_comments_xpath")));

			jsClick(ob,
					commentsList.get(commentsCount).findElement(
							By.xpath(OR.getProperty("tr_authoring_comments_flag_dynamic_xpath"))));

			waitForElementTobeVisible(ob,
					By.cssSelector(OR.getProperty("tr_authoring_comments_flag_reason_modal_css")), 40);

			jsClick(ob, ob.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_cancel_button_modal_css"))));
			try {
				boolean isFlagged = commentsList.get(commentsCount)
						.findElement(By.xpath(OR.getProperty("tr_authoring_comments_flag_dynamic_xpath")))
						.getAttribute("class").contains("flag-active");
				Assert.assertTrue(isFlagged);
				test.log(LogStatus.PASS, "Cancel unflag action without reason selected is working fine for comments");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Cancel unflag action without reason selected verification failed");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "Cancel_Flag_validation_for_comments_failed")));// screenshot

			}

			commentsList = ob.findElements(By.xpath(OR.getProperty("tr_authoring_comments_xpath")));

			jsClick(ob,
					commentsList.get(commentsCount).findElement(
							By.xpath(OR.getProperty("tr_authoring_comments_flag_dynamic_xpath"))));

			waitForElementTobeVisible(ob,
					By.cssSelector(OR.getProperty("tr_authoring_comments_flag_reason_modal_css")), 40);
			jsClick(ob, ob.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_flag_reason_chkbox_css"))));
			jsClick(ob, ob.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_cancel_button_modal_css"))));
			try {
				boolean isFlagged = commentsList.get(commentsCount)
						.findElement(By.xpath(OR.getProperty("tr_authoring_comments_flag_dynamic_xpath")))
						.getAttribute("class").contains("flag-active");
				Assert.assertTrue(isFlagged);
				test.log(LogStatus.PASS, "Cancel unflag action with reason selected is working fine for comments");

			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Cancel unflag action verification failed");
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;
				test.log(
						LogStatus.INFO,
						"Snapshot below: "
								+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
										+ "Cancel_Flag_validation_for_comments_failed")));// screenshot

			}
			commentsList = ob.findElements(By.xpath(OR.getProperty("tr_authoring_comments_xpath")));

			jsClick(ob,
					commentsList.get(commentsCount).findElement(
							By.xpath(OR.getProperty("tr_authoring_comments_flag_dynamic_xpath"))));
			List<WebElement> chkBoxList = ob.findElements(By.cssSelector(OR
					.getProperty("tr_authoring_comments_flag_reason_chkbox_css")));
			for (WebElement we : chkBoxList) {
				if (!we.isSelected()) {
					jsClick(ob, we);
					break;
				}
			}
			Thread.sleep(5000);// wait for flag modal to appear
			waitForElementTobeVisible(ob,
					By.cssSelector(OR.getProperty("tr_authoring_comments_flag_button_modal_css")), 40);
			ob.findElement(By.cssSelector(OR.getProperty("tr_authoring_comments_flag_button_modal_css"))).click();
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

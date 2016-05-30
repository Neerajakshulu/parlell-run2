package notifications;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import pages.PageFactory;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;

public class Notifications0010 extends NotificationsTestBase {

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
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription())
				.assignCategory("Notifications");

	}

	@Test
	public void testcaseF10() throws Exception {
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
			maximizeWindow();
			clearCookies();
			ob.navigate().to(host);
			// Logging in with User2
			pf.getLoginTRInstance(ob).enterTRCredentials(CONFIG.getProperty("defaultUsername"),
					CONFIG.getProperty("defaultPassword"));
			pf.getLoginTRInstance(ob).clickLogin();
			BrowserWaits.waitTime(8);
			jsClick(ob, ob.findElement(
					By.xpath(OR.getProperty("trending_now_menu_links").replaceAll("FILTER_TYPE", "Posts"))));
			BrowserWaits.waitTime(6);
			List<WebElement> listOfPosts = ob
					.findElements(By.xpath(OR.getProperty("trending_categories_list_of_links")));
			BrowserWaits.waitTime(3);
			jsClick(ob, ob.findElement(
					By.xpath(OR.getProperty("trending_now_menu_links").replaceAll("FILTER_TYPE", "Articles"))));
			BrowserWaits.waitTime(6);
			List<WebElement> listOfArticles = ob
					.findElements(By.xpath(OR.getProperty("trending_categories_list_of_links")));
			BrowserWaits.waitTime(3);
			jsClick(ob, ob.findElement(
					By.xpath(OR.getProperty("trending_now_menu_links").replaceAll("FILTER_TYPE", "Topics"))));
			BrowserWaits.waitTime(6);
			List<WebElement> listOfTopics = ob
					.findElements(By.xpath(OR.getProperty("trending_categories_list_of_links")));
			BrowserWaits.waitTime(3);

			try {
				Assert.assertTrue(listOfPosts.size() <= 10 && listOfArticles.size() <= 10 && listOfTopics.size() <= 10);
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Count is more than expected 10");// extent
				// reports
				test.log(LogStatus.INFO, "Error--->" + t);
				ErrorUtil.addVerificationFailure(t);
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_Title selected is not same in search text box")));// screenshot
				closeBrowser();
			}

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something happened");// extent
			// reports
			test.log(LogStatus.INFO, "Error--->" + t);
			ErrorUtil.addVerificationFailure(t);
			status = 2;// excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
					this.getClass().getSimpleName() + "_Title selected is not same in search text box")));// screenshot
			closeBrowser();
		}
		closeBrowser();
	}

	@AfterTest
	public void reportTestResult() {
		extent.endTest(test);

		/*
		 * if (status == 1) TestUtil.reportDataSetResult(notificationxls, "Test Cases",
		 * TestUtil.getRowNum(notificationxls, this.getClass().getSimpleName()), "PASS"); else if (status == 2)
		 * TestUtil.reportDataSetResult(notificationxls, "Test Cases", TestUtil.getRowNum(notificationxls,
		 * this.getClass().getSimpleName()), "FAIL"); else TestUtil.reportDataSetResult(notificationxls, "Test Cases",
		 * TestUtil.getRowNum(notificationxls, this.getClass().getSimpleName()), "SKIP");
		 */
	}
}
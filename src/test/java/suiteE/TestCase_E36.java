package suiteE;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import suiteC.LoginTR;
import util.ErrorUtil;
import util.TestUtil;

public class TestCase_E36 extends TestBase {
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
		test = extent.startTest(var, "Verify that user is able to see the watchlist items by content type")
				.assignCategory("Suite E");

	}

	@Test
	public void testWatchlistItemsDisplayedByContentType() throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "E Suite");
		boolean testRunmode = TestUtil.isTestCaseRunnable(suiteExls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;// excel
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
		try {

			String search_query = "hello";

			// Making a new user
			openBrowser();
			try {
				maximizeWindow();
			} catch (Throwable t) {

				System.out.println("maximize() command not supported in Selendroid");
			}
			clearCookies();

			createNewUser("mask", "man");
			// ob.navigate().to(host);
			// LoginTR.enterTRCredentials("Prasenjit.Patra@thomsonreuters.com",
			// "Techm@2015");
			// LoginTR.clickLogin();
			// Thread.sleep(15000);

			// Searching for article
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(search_query);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			Thread.sleep(8000);

			// Watching an article to a particular watch list
			ob.findElement(By.xpath(OR.getProperty("tab_articles_result"))).click();
			Thread.sleep(6000);
			WebElement watchButton = ob.findElement(By.xpath(OR.getProperty("search_watchlist_image")));
			String selectedWatchlistName = watchOrUnwatchItemToAParticularWatchlist(watchButton);

			// Watching a patents to a particular watch list
			ob.findElement(By.xpath(OR.getProperty("tab_patents_result"))).click();
			Thread.sleep(6000);
			watchButton = ob.findElement(By.xpath(OR.getProperty("search_watchlist_image")));
			selectedWatchlistName = watchOrUnwatchItemToAParticularWatchlist(watchButton);
			// Watching a posts to a particular watch list
			ob.findElement(By.xpath(OR.getProperty("tab_posts_result"))).click();
			Thread.sleep(6000);
			watchButton = ob.findElement(By.xpath(OR.getProperty("search_watchlist_image")));
			selectedWatchlistName = watchOrUnwatchItemToAParticularWatchlist(watchButton);

			// Navigate to a particular watch list page
			navigateToParticularWatchlistPage(selectedWatchlistName);

			List<WebElement> watchedItems = ob.findElements(By.xpath(OR.getProperty("searchResults_links")));
			List<WebElement> labels = ob.findElements(By.xpath(OR.getProperty("item_label")));

			if (!compareNumbers(watchedItems.size(), labels.size())) {

				test.log(LogStatus.FAIL, "Watchlist items are not displayed by content type");// extent
				// reports
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(
						this.getClass().getSimpleName() + "_watchlist_items_are_not_displayed_by_content_type")));// screenshot

			} else {
				test.log(LogStatus.PASS, "Watchlist items are displayed by content type");
			}

			closeBrowser();

		} catch (Throwable t) {
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
			TestUtil.reportDataSetResult(suiteExls, "Test Cases",
					TestUtil.getRowNum(suiteExls, this.getClass().getSimpleName()), "PASS");
		else if (status == 2)
			TestUtil.reportDataSetResult(suiteExls, "Test Cases",
					TestUtil.getRowNum(suiteExls, this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteExls, "Test Cases",
					TestUtil.getRowNum(suiteExls, this.getClass().getSimpleName()), "SKIP");

	}

}

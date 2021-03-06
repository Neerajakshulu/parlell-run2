package suiteB;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserWaits;
import util.ErrorUtil;
import util.TestUtil;

public class TestCase_B95 extends TestBase {
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
						"Verify that following fields get displayed correctly for a post in POSTS search results page: a)Title b)Creation date and time c)Author d)Author details e)Likes count f)Comments count")
					.assignCategory("Suite B");

		}

		@Test
		public void testcaseB84() throws Exception {

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

				

				openBrowser();
				clearCookies();
				maximizeWindow();

				// Navigating to the NEON login page
				//ob.navigate().to(host);
				ob.navigate().to(CONFIG.getProperty("testSiteName"));
				waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_home_signInwith_projectNeon_css")), 120);
				waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_home_signInwith_projectNeon_css")), 120);
				BrowserWaits.waitUntilText("Sign in with Project Neon");

				// login using TR credentials
				login();
				waitForElementTobeVisible(ob, By.cssSelector("i[class='webui-icon webui-icon-search']"), 120);
				waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 120);
				
				String postToSearch = "Post for Testing bJ38z9";
				
				ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(postToSearch);
				ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
				waitForAjax(ob);
				ob.findElement(By.xpath(OR.getProperty("tab_posts_result"))).click();
				waitForAjax(ob);
				
				String postTitle=ob.findElement(By.cssSelector(OR.getProperty("tr_search_results_item_title_css"))).getText();
				String postAuthor=ob.findElement(By.cssSelector("h3[class*='ne-profile-object-title-wrapper']")).getText();
				String postCreationDate=ob.findElement(By.cssSelector("h6[class*='ng-binding']")).getText();
				String profileMetaData=ob.findElements(By.cssSelector("h6[class*='ng-binding']")).get(1).getText();
				String postLikeCount=ob.findElements(By.cssSelector(OR.getProperty("tr_search_results_item_comments_count_css"))).get(0).getText();
				String postLikeLabel=ob.findElements(By.cssSelector(OR.getProperty("tr_search_results_item_comments_count_css"))).get(1).getText();
				
				String postCommentCount=ob.findElements(By.cssSelector(OR.getProperty("tr_search_results_item_comments_count_css"))).get(2).getText();
				String postCommentLabel=ob.findElements(By.cssSelector(OR.getProperty("tr_search_results_item_comments_count_css"))).get(3).getText();
				
				

				boolean isPostTitleAvailable=StringUtils.containsIgnoreCase(postTitle,postToSearch);
				boolean isPostedByAuthor=postAuthor.isEmpty();
				boolean ispostCreationDateAndTimeAvailable=StringUtils.containsIgnoreCase(postCreationDate,"2016") && (StringUtils.containsIgnoreCase(postCreationDate,"AM")||StringUtils.containsIgnoreCase(postCreationDate,"PM"));
				boolean ispostAuthorMetadataAvailable=profileMetaData.isEmpty();
				boolean isPostLikeCountAvailable=Integer.parseInt(postLikeCount)>=0 && postLikeLabel.equalsIgnoreCase("Likes");
				boolean isPostCommentCountAvailable=Integer.parseInt(postCommentCount)>=0 && postCommentLabel.equalsIgnoreCase("Comments");
				
				
				//System.out.println("status-->"+isPostTitleAvailable+"2."+isPostedByAuthor+"3."+ispostCreationDateAndTimeAvailable+"4."+ispostAuthorMetadataAvailable+"5."+isPostLikeCountAvailable+"6."+isPostCommentCountAvailable);
				
			if (!(isPostTitleAvailable && (!isPostedByAuthor) && ispostCreationDateAndTimeAvailable
					&& (!ispostAuthorMetadataAvailable) && isPostLikeCountAvailable && isPostCommentCountAvailable)) {
					throw new Exception("Post all fiedls are not getting displayed in search results ALL page");
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
						captureScreenshot(this.getClass().getSimpleName() + "_patent_metadata_failed")));// screenshot
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

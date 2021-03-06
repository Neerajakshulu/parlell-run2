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

public class TestCase_B84 extends TestBase {
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
						"Verify that following fields get displayed correctly for a patent in ALL search results page:a)Title b)Inventors c)Assignees d)Patent number e)Publication date f)Times cited count g)Comments count")
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
			ob.navigate().to(host);
			//ob.navigate().to(CONFIG.getProperty("testSiteName"));
			waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_home_signInwith_projectNeon_css")), 120);
			waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("tr_home_signInwith_projectNeon_css")), 120);
			BrowserWaits.waitUntilText("Sign in with Project Neon");

			// login using TR credentials
			login();
			waitForElementTobeVisible(ob, By.cssSelector("i[class='webui-icon webui-icon-search']"), 120);
			waitForElementTobeClickable(ob, By.cssSelector(OR.getProperty("tr_search_box_css")), 120);
			
			String patent = "Projection exposure apparatus and method which uses multiple diffraction gratings in order to produce a solid state device with fine patterns";
			
			ob.findElement(By.xpath(OR.getProperty("searchBox_textBox"))).sendKeys(patent);
			ob.findElement(By.xpath(OR.getProperty("search_button"))).click();
			waitForAjax(ob);
			
			String patentTitle=ob.findElement(By.cssSelector(OR.getProperty("tr_search_results_item_title_css"))).getText();
			String inventor=ob.findElement(By.cssSelector(OR.getProperty("tr_search_results_patent_title_css"))).getText();
			String assignee=ob.findElements(By.cssSelector(OR.getProperty("tr_search_results_patent_title_css"))).get(1).getText();
			String patentNumberAndDate=ob.findElements(By.cssSelector(OR.getProperty("tr_search_results_patent_title_css"))).get(2).getText();
			String timesCitedCount=ob.findElement(By.cssSelector(OR.getProperty("tr_search_results_patent_metadata_css"))).getText();
			String commentCount=ob.findElements(By.cssSelector(OR.getProperty("tr_search_results_patent_metadata_css"))).get(1).getText();
			String numberDate[]=patentNumberAndDate.split(" ");
			String timeCitedCount[]=timesCitedCount.split(" ");
			String citedCount=timeCitedCount[0].replace(",", "");
			String commentTotCount[]=commentCount.split(" ");
			
			String regEx="[0-9]+";
			
			boolean patenMetaData=StringUtils.containsIgnoreCase(patentTitle,patent) && inventor.contains("By:") && assignee.contains("Assignee:");
			
			boolean patentNumberDateStatus=numberDate[0].length()>0 && patentNumberAndDate.contains("Publication date:");
			boolean timesCitedStatus=citedCount.matches(regEx) && timesCitedCount.contains("Times Cited");
			boolean commentTotCountStauts=commentTotCount[0].matches(regEx) && commentCount.contains("Comments");
			
			if(!(patenMetaData&&patentNumberDateStatus&&timesCitedStatus&&commentTotCountStauts)){
				throw new Exception("Patent meta data not displayed in Search results page");
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

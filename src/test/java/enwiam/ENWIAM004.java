package enwiam;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class ENWIAM004 extends TestBase {

	String runmodes[] = null;
	static int count = -1;

	static boolean fail = false;
	static boolean skip = false;
	static int status = 1;

	// Checking whether this test case should be skipped or not
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		String var = xlRead2(returnExcelPath('G'), this.getClass().getSimpleName(), 1);
		//test = extent.startTest(var, "Verify FIRST NAME field in new TR user registration page").assignCategory("ENWIAM");
		// test.log(LogStatus.INFO, "****************************");
		// load the runmodes of the tests
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("ENWIAM");
		runmodes = TestUtil.getDataSetRunmodes(enwiamxls, this.getClass().getSimpleName());
	}

	@Test(dataProvider = "getTestData")
	public void testcaseA5(String charLength,
			String validity) throws Exception {

		boolean suiteRunmode = TestUtil.isSuiteRunnable(suiteXls, "ENWIAM");
		boolean testRunmode = TestUtil.isTestCaseRunnable(enwiamxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {

			status = 3;
			// TestUtil.reportDataSetResult(iamxls, "Test Cases",
			// TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "SKIP");
			test.log(LogStatus.SKIP, "Skipping test case " + this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

		}

		// test the runmode of current dataset
		count++;
		if (!runmodes[count].equalsIgnoreCase("Y")) {

			test.log(LogStatus.INFO, "Runmode for test set data set to no " + (count + 1));
			skip = true;
			// TestUtil.reportDataSetResult(iamxls, this.getClass().getSimpleName(), count+2, "SKIP");
			throw new SkipException("Runmode for test set data set to no " + (count + 1));
		}

		try {

			String characterLength = charLength.substring(0, 2);
			Double d=new Double(Double.parseDouble(characterLength));
			int i=d.intValue();
			test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts for data set #" + (count + 1)
					+ "--->");
			test.log(LogStatus.INFO, characterLength + " -- " + validity);

			logger.info("Char length : "+characterLength);
			//System.out.println(Integer.parseInt(characterLength));
			String first_name = generateRandomName(i);
			logger.info("First Name : "+first_name);

			// selenium code
			openBrowser();
			//
			try {
				maximizeWindow();
			} catch (Throwable t) {

				System.out.println("maximize() command not supported in Selendroid");
			}
			clearCookies();

			// Navigate to TR login page
//			ob.navigate().to(CONFIG.getProperty("enwUrl"));
			ob.get(host+CONFIG.getProperty("appendENWAppUrl"));
			waitForElementTobeVisible(ob, By.xpath(OR.getProperty("signup_link")), 30);
			ob.findElement(By.xpath(OR.getProperty("signup_link"))).click();
			BrowserWaits.waitTime(3);
			waitForElementTobeVisible(ob, By.name(OR.getProperty("signup_firstName_textbox")), 30);
			ob.findElement(By.name(OR.getProperty("signup_firstName_textbox"))).clear();
			ob.findElement(By.name(OR.getProperty("signup_firstName_textbox"))).sendKeys(first_name);
			BrowserWaits.waitTime(3);
			ob.findElement(By.name(OR.getProperty("signup_lastName_textbox"))).click();

			BrowserWaits.waitTime(2);
			List<WebElement> errorList = ob.findElements(By.xpath(OR.getProperty("reg_error_label")));
			logger.info("Errors Count : "+errorList.size());

			if (validity.equalsIgnoreCase("YES")) {

				for(WebElement text : errorList){
					
				// verifying that error message is not getting displayed
				if (text.getText().equals("First name is too long.")) {

					fail = true;// excel
					test.log(LogStatus.FAIL, "Error message getting displayed unnecessarily");// extent report
					test.log(
							LogStatus.INFO,
							"Snapshot below: "
									+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
											+ "_error_message_getting_displayed_unnecessarily_" + (count + 1))));
					closeBrowser();
					return;
				}
			}

			}

			else {
				for(WebElement text : errorList){
				if (!text.getText().equals("First name is too long.")) {

					fail = true;// excel
					test.log(LogStatus.FAIL, "Error message not getting displayed");// extent report
					test.log(
							LogStatus.INFO,
							"Snapshot below: "
									+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
											+ "_error_message_not_getting_displayed_" + (count + 1))));
					closeBrowser();
					return;
				}

			}
				/*BrowserWaits.waitTime(3);
				String errorText = ob.findElement(By.xpath(OR.getProperty("reg_error_label"))).getText();
				logger.info("Error Text  : "+errorText);
				if (!compareStrings("First name is too long.", errorText)) {

					fail = true;// excel
					test.log(LogStatus.FAIL, "Error text is incorrect");// extent report
					test.log(
							LogStatus.INFO,
							"Snapshot below: "
									+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
											+ "_incorrect_error_text_" + (count + 1))));
					closeBrowser();
					return;

				}*/

			}

			closeBrowser();

		}

		catch (Throwable t) {

			status = 2;// excel-main testcase
			fail = true;// excel-dataset
			ErrorUtil.addVerificationFailure(t);// testng
			test.log(LogStatus.FAIL, "Something unexpected happened");// extent reports
			// next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO, errors.toString());// extent reports
			test.log(
					LogStatus.INFO,
					"Snapshot below: "
							+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
									+ "_something_unexpected_happened")));// screenshot
			closeBrowser();
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends for data set #" + (count + 1)
				+ "--->");
	}

	@AfterMethod
	public void reportDataSetResult() {
		if (skip)
			TestUtil.reportDataSetResult(enwiamxls, this.getClass().getSimpleName(), count + 2, "SKIP");

		else if (fail) {

			status = 2;
			TestUtil.reportDataSetResult(enwiamxls, this.getClass().getSimpleName(), count + 2, "FAIL");
		} else
			TestUtil.reportDataSetResult(enwiamxls, this.getClass().getSimpleName(), count + 2, "PASS");

		skip = false;
		fail = false;

	}

	@AfterTest
	public void reportTestResult() {

		extent.endTest(test);

		/*
		 * if(status==1) TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "PASS"); else if(status==2)
		 * TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(iamxls, "Test Cases",
		 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "SKIP");
		 */

	}

	@DataProvider
	public Object[][] getTestData() {
		return TestUtil.getData(enwiamxls, this.getClass().getSimpleName());
	}

}
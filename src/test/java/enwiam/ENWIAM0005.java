package enwiam;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.PageFactory;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;

//OPQA-2399 Verify that,the user should not be able to exit the STeAM account linking process through 
//clicking anywhere on the page.






public class ENWIAM0005 extends TestBase {
	
	// Following is the list of status:
		// 1--->PASS
		// 2--->FAIL
		// 3--->SKIP
		// Checking whether this test case should be skipped or not
		static boolean fail = false;
		static boolean skip = false;
		static int status = 1;

		static int time = 30;
		PageFactory pf=new PageFactory();
		
		@BeforeTest
		public void beforeTest() throws Exception {
			extent = ExtentManager.getReporter(filePath);
			rowData = testcase.get(this.getClass().getSimpleName());
			test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("ENWIAM");

		}
		
		
		@Test
		public void testLogin() throws Exception {
			
			boolean testRunmode = TestUtil.isTestCaseRunnable(enwiamxls, this.getClass().getSimpleName());
			boolean master_condition = suiteRunmode && testRunmode;

			if (!master_condition) {

				test.log(LogStatus.SKIP,
						"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
				throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

			}

			
			test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");
			try {
				String statuCode = deleteUserAccounts(LOGIN.getProperty("UserName18"));
				Assert.assertTrue(statuCode.equalsIgnoreCase("200"));
							
			} catch (Throwable t) {
				test.log(LogStatus.INFO, "Delete accounts api call failed");// extent
				ErrorUtil.addVerificationFailure(t);
			}
			
			try {
				
				openBrowser();
				maximizeWindow();
				clearCookies();
				loginTofb();
			    closeBrowser();
				pf.clearAllPageObjects();
				
			} 
			catch (Throwable t) {
				test.log(LogStatus.INFO, "Unexpected error");// extent
				ErrorUtil.addVerificationFailure(t);
			}
			test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
		}
		private void loginTofb() throws Exception {
			

			// Navigate to TR login page and login with valid TR credentials
			ob.navigate().to(host+CONFIG.getProperty("appendENWAppUrl"));	
		//	pf.getEnwReferenceInstance(ob).loginWithFBCredentialsENW(ob,"arvindkandaswamy@gmail.com","darshiniyogi@123");
			pf.getENWReferencePageInstance(ob).loginWithENWFBCredentials(LOGIN.getProperty("UserName18"), LOGIN.getProperty("Password18"));
			BrowserWaits.waitTime(2);        
			new Actions(ob).moveByOffset(200, 200).click().build().perform();
			BrowserWaits.waitTime(2);
			ob.findElement(By.cssSelector("i[class='fa fa-close']")).click();
			BrowserWaits.waitTime(2);
			}
		
		
			@AfterTest
			public void reportTestResult() {
				extent.endTest(test);

				/*
				 * if(status==1) TestUtil.reportDataSetResult(iamxls, "Test Cases",
				 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "PASS");
				 * else if(status==2) TestUtil.reportDataSetResult(iamxls, "Test Cases",
				 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "FAIL");
				 * else TestUtil.reportDataSetResult(iamxls, "Test Cases",
				 * TestUtil.getRowNum(iamxls,this.getClass().getSimpleName()), "SKIP");
				 */
			}

}



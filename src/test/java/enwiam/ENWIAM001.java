package enwiam;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
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
import util.OnePObjectMap;
import util.TestUtil;

public class ENWIAM001 extends TestBase {

	static int status = 1;
	String document_title = null;
	String document_url = null;

	PageFactory pf = new PageFactory();
	String postString = null;
	public int screen = 0;

	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("ENWIAM");
	}

	@Test
	public void testcaseF20() throws Exception {
		boolean testRunmode = TestUtil.isTestCaseRunnable(enwiamxls, this.getClass().getSimpleName());
		boolean master_condition = suiteRunmode && testRunmode;
		
		logger.info("Test --" + suiteRunmode + "--" + testRunmode);
		if (!master_condition) {
			status = 3;// excel
			extent = ExtentManager.getReporter(filePath);
			String var = rowData.getTestcaseId();
			String dec = rowData.getTestcaseDescription();
			String[] tests = StringUtils.split(var, TOKENIZER_DOUBLE_PIPE);
			String[] tests_dec = StringUtils.split(dec, TOKENIZER_DOUBLE_PIPE);
			logger.info(rowData.getTestcaseId());
			for (int i = 0; i < tests.length; i++) {
				logger.info(tests_dec[i]);
				test = extent.startTest(tests[i], tests_dec[i]).assignCategory("ENWIAM");
				test.log(LogStatus.SKIP,
						"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
				extent.endTest(test);
			}
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}
		try {
			openBrowser();
			maximizeWindow();
			clearCookies();
			try {
				
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-1719",
								"Verify that ENW registration screen should be displayed and User should be able to enter email address (required), name (required), and password (required).")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO,this.getClass().getSimpleName() + " execution start");
			
					ob.get("https://www.guerrillamail.com");
					BrowserWaits.waitTime(2);
					if (CONFIG.getProperty("browserType").equals("IE")) {
						Runtime.getRuntime().exec("C:/Users/uc204155/Desktop/IEScript.exe");
						BrowserWaits.waitTime(4);
					}
					email = ob.findElement(By.id(OR.getProperty("email_textBox"))).getText();
					ob.get(host+CONFIG.getProperty("appendENWAppUrl"));
					waitForElementTobeVisible(ob, By.xpath(OR.getProperty("signup_link")), 30);
					ob.findElement(By.xpath(OR.getProperty("signup_link"))).click();
					waitForElementTobeVisible(ob, By.name(OR.getProperty("signup_email_texbox")), 30);
					ob.findElement(By.name(OR.getProperty("signup_email_texbox"))).clear();
					ob.findElement(By.name(OR.getProperty("signup_email_texbox"))).sendKeys(email);
					ob.findElement(By.name(OR.getProperty("signup_password_textbox"))).clear();
					ob.findElement(By.name(OR.getProperty("signup_password_textbox")))
							.sendKeys(CONFIG.getProperty("defaultPassword"));
					ob.findElement(By.name(OR.getProperty("signup_firstName_textbox"))).clear();
					ob.findElement(By.name(OR.getProperty("signup_firstName_textbox"))).sendKeys("Duster");
					ob.findElement(By.name(OR.getProperty("signup_lastName_textbox"))).clear();
					ob.findElement(By.name(OR.getProperty("signup_lastName_textbox"))).sendKeys("man");
					BrowserWaits.waitTime(2);
					test.log(LogStatus.PASS, "Required fields are enter properly");
				
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Required fields are enter properly");
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
			} finally {
				test.log(LogStatus.INFO,this.getClass().getSimpleName() + " execution end");
				extent.endTest(test);
			}

			try {
				
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-1676",
								"Verify that 'Sign up' link should be displayed on ENW registration page .")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO,this.getClass().getSimpleName() + " execution start");
				String signupStatus = ob
						.findElement(By.cssSelector(
								"button[class='wui-btn wui-btn--primary login-button button-color-primary']"))
						.getAttribute("ng-disabled");
				logger.info("SingUp Status : " + signupStatus);
				Assert.assertTrue(signupStatus.contains("userRegister.$invalid"));
				test.log(LogStatus.PASS, "Sign up linke is enabled");
			} catch (Throwable t) {
				test.log(LogStatus.FAIL, "Sign up linke is not  enabled" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
			} finally {
				test.log(LogStatus.INFO,this.getClass().getSimpleName() + " execution end");
				extent.endTest(test);
			}
			
			try{
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-1744",
								"Verify that the user should be able click on 'Sign up' button after filling the above fields correctly.")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO,this.getClass().getSimpleName() + " execution start");
				//ob.findElement(By.xpath(OR.getProperty("signup_button"))).click();
				ob.findElement(By.cssSelector("button[class='wui-btn wui-btn--primary login-button button-color-primary']")).click();
				BrowserWaits.waitTime(4);
				waitForElementTobeVisible(ob, By.cssSelector(OR.getProperty("signup_confom_sent_mail")), 30);
				String text = ob.findElement(By.cssSelector(OR.getProperty("signup_confom_sent_mail"))).getText();

				
				if (!StringContains(text, email)) {
					if (test != null) {
						test.log(LogStatus.FAIL, "Account activation email not sent");// extent
																						// reports
						test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
								captureScreenshot(this.getClass().getSimpleName() + "_account_activation_email_not_sent")));// screenshot
					}
				}

				
				test.log(LogStatus.PASS, "Able to click on 'Sign up' button after filling the required fields");
			}catch (Throwable t) {
				test.log(LogStatus.FAIL, "Not Able to click on 'Sign up' button after filling the required fields" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
			} finally {
				test.log(LogStatus.INFO,this.getClass().getSimpleName() + " execution end");
				extent.endTest(test);
			}
			try{
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-1760",
								"Verify that user should get an Email verification Link on the registered Email Id .")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO,this.getClass().getSimpleName() + " execution start");
				ob.findElement(By.xpath(OR.getProperty("signup_conformatin_button"))).click();
				BrowserWaits.waitTime(3);
				ob.get("https://www.guerrillamail.com");
				if (CONFIG.getProperty("browserType").equals("IE")) {
					Runtime.getRuntime().exec("C:/Users/uc204155/Desktop/IEScript.exe");
					BrowserWaits.waitTime(4);
				}
				BrowserWaits.waitTime(14);
				List<WebElement> email_list = ob.findElements(By.xpath(OR.getProperty("email_list")));
				WebElement myE = email_list.get(0);
				JavascriptExecutor executor = (JavascriptExecutor) ob;
				executor.executeScript("arguments[0].click();", myE);
				BrowserWaits.waitTime(3);
				if(ob.findElement(By.cssSelector("h3[class='email_subject']")).getText().equalsIgnoreCase("Welcome to Guerrilla Mail")){
					ob.get("https://www.guerrillamail.com");
					BrowserWaits.waitTime(14);
					List<WebElement> email_list1 = ob.findElements(By.xpath(OR.getProperty("email_list")));
					WebElement myE1 = email_list1.get(0);
					JavascriptExecutor executor1 = (JavascriptExecutor) ob;
					executor1.executeScript("arguments[0].click();", myE1);
				}
				BrowserWaits.waitTime(2);
				String text=ob.findElement(By.cssSelector("h3[class='email_subject']")).getText();
				Assert.assertEquals(text,"Please activate your EndNote account");
				test.log(LogStatus.PASS, "User get an Email verification Link on the registered Email Id");
			}catch (Throwable t) {
				test.log(LogStatus.FAIL, "User not get an Email verification Link on the registered Email Id" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
			} finally {
				test.log(LogStatus.INFO,this.getClass().getSimpleName() + " execution end");
				extent.endTest(test);
			}
			
			try{
				
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-1763",
								"Verify that after clicking verification link user should get the message as' Success!You have successfully activated your account. Please sign in.'")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO,this.getClass().getSimpleName() + " execution start");
				
				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("email_body")), 30);
				WebElement email_body = ob.findElement(By.xpath(OR.getProperty("email_body")));
				List<WebElement> links = email_body.findElements(By.tagName("a"));

				ob.get(links.get(0).getAttribute("href"));
				BrowserWaits.waitTime(3);
				String confomMessage=ob.findElement(By.cssSelector("h2[class='login-title']")).getText();
				
				Assert.assertEquals(confomMessage,"Success!");
				test.log(LogStatus.PASS, "After clicking verification link user get the message as Success! Message");
				
			}catch (Throwable t) {
				test.log(LogStatus.FAIL, "After clicking verification link user not get the message as Success! Message" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
			} finally {
				test.log(LogStatus.INFO,this.getClass().getSimpleName() + " execution end");
				extent.endTest(test);
			}
			
			
			try{
				extent = ExtentManager.getReporter(filePath);
				test = extent
						.startTest("OPQA-1766",
								"Verify that after completion of verification process,user should be able to sign into ENW")
						.assignCategory("ENWIAM");
				test.log(LogStatus.INFO,this.getClass().getSimpleName() + " execution start");
				ob.findElement(By.xpath(OR.getProperty("signup_conformatin_button"))).click();
				BrowserWaits.waitTime(4);
				waitForElementTobeVisible(ob, By.name(OR.getProperty("TR_email_textBox")), 30);
				ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).clear();
				ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).sendKeys(email);
				ob.findElement(By.name(OR.getProperty("TR_password_textBox")))
						.sendKeys(CONFIG.getProperty("defaultPassword"));
				ob.findElement(By.cssSelector(OR.getProperty("login_button"))).click();
				BrowserWaits.waitTime(10);
				waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_AGREE_BUTTON_CSS.toString()), 30);
				ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_AGREE_BUTTON_CSS.toString())).click();
				
				waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS.toString()), 30);
				String text=ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS.toString())).getText();
				if(text.equalsIgnoreCase("Continue")){
					ob.findElement(By.cssSelector(OnePObjectMap.ENDNOTE_LOGIN_CONTINUE_BUTTON_CSS.toString())).click();
				}
				test.log(LogStatus.PASS, "After completion of verification process,user successfylly login to ENW application");
			}catch (Throwable t) {
				test.log(LogStatus.FAIL, "After completion of verification process,user successfylly login to ENW application" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));
			} finally {
				test.log(LogStatus.INFO,this.getClass().getSimpleName() + " execution end");
				extent.endTest(test);
			}
			
		logoutEnw();	

		} catch (Throwable t) {
			if (test == null) {
				extent = ExtentManager.getReporter(filePath);
				String var = rowData.getTestcaseId();
				String dec = rowData.getTestcaseDescription();
				String[] tests = StringUtils.split(var, TOKENIZER_DOUBLE_PIPE);
				String[] tests_dec = StringUtils.split(dec, TOKENIZER_DOUBLE_PIPE);
				for (int i = 0; i < tests.length; i++) {
					test = extent.startTest(tests[i], tests_dec[i]).assignCategory("Notifications");
					test.log(LogStatus.FAIL, "FAIL - " + t.getMessage());
					extent.endTest(test);
				}
			} else {
				test.log(LogStatus.FAIL, "Something unexpected happened" + t);// extent
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				test.log(LogStatus.INFO, errors.toString());// extent reports
				ErrorUtil.addVerificationFailure(t);// testng
				status = 2;// excel
				test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
						captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
			}
		} finally {
			closeBrowser();
		}
	}

	@AfterTest
	public void reportTestResult() {
		// extent.endTest(test);

		/*
		 * if (status == 1) TestUtil.reportDataSetResult(notificationxls,
		 * "Test Cases", TestUtil.getRowNum(notificationxls,
		 * this.getClass().getSimpleName()), "PASS"); else if (status == 2)
		 * TestUtil.reportDataSetResult(notificationxls, "Test Cases",
		 * TestUtil.getRowNum(notificationxls, this.getClass().getSimpleName()),
		 * "FAIL"); else TestUtil.reportDataSetResult(notificationxls,
		 * "Test Cases", TestUtil.getRowNum(notificationxls,
		 * this.getClass().getSimpleName()), "SKIP");
		 */
	}

}

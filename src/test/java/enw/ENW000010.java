package enw;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;

public class ENW000010 extends TestBase {

    static int status = 1;

    @BeforeTest
    public void beforeTest() throws Exception {
	extent = ExtentManager.getReporter(filePath);
	rowData = testcase.get(this.getClass().getSimpleName());
	test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("ENW");

    }

    @Test
    public void testcaseENW000010() throws Exception {

	boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
	boolean master_condition = suiteRunmode && testRunmode;

	if (!master_condition) {

	    test.log(LogStatus.SKIP,
		    "Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
	    throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports

	}

	test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts--->");

	try {

	    String statuCode = deleteUserAccounts(LOGIN.getProperty("UserENW000010"));
	    logger.info("User Status : " + statuCode);
	    if (statuCode.equalsIgnoreCase("200")) {
		logger.info("User Deleted Successfully");
	    }

	    String statuCode1 = deleteUserAccounts(LOGIN.getProperty("UserENWsteam000010"));
	    logger.info("User Status : " + statuCode1);
	    if (statuCode1.equalsIgnoreCase("200")) {
		logger.info("User Deleted Successfully");
	    }

	    openBrowser();
	    clearCookies();
	    maximizeWindow();
	    ob.navigate().to(host);

	    try {
		waitForElementTobeVisible(ob, By.name(OR.getProperty("TR_email_textBox")), 30);
		ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).clear();
		ob.findElement(By.name(OR.getProperty("TR_email_textBox")))
			.sendKeys(LOGIN.getProperty("UserENWsteam000010"));
		ob.findElement(By.name(OR.getProperty("TR_password_textBox")))
			.sendKeys(LOGIN.getProperty("PWDuserENW000010"));
		ob.findElement(By.cssSelector(OR.getProperty("login_button"))).click();
		ob.navigate().refresh();
		ob.findElement(By.name(OR.getProperty("TR_email_textBox"))).clear();
		ob.findElement(By.name(OR.getProperty("TR_email_textBox")))
			.sendKeys(LOGIN.getProperty("UserENWsteam000010"));
		ob.findElement(By.name(OR.getProperty("TR_password_textBox")))
			.sendKeys(LOGIN.getProperty("PWDuserENW000010"));
		ob.findElement(By.cssSelector(OR.getProperty("login_button"))).click();

		ob.findElement(By.xpath(OR.getProperty("signup_conformatin_button"))).click();

		ob.findElement(By.xpath(OR.getProperty("signup_done_button"))).click();

	    } catch (Throwable t) {
		t.printStackTrace();
		test.log(LogStatus.INFO, "Snapshot below: " + test
			.addScreenCapture(captureScreenshot(this.getClass().getSimpleName() + "_user_not_registered")));// screenshot
		closeBrowser();
	    }

	    logout();

	    pf.getLoginTRInstance(ob).loginWithFBCredentials(LOGIN.getProperty("UserENW000010"),
		    LOGIN.getProperty("PWDuserENW000010"));

	    waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.HEADER_LABEL_XPATH.toString()), 30);
	    ob.findElement(By.xpath(OnePObjectMap.HEADER_LABEL_XPATH.toString())).click();

	    waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.ACCOUNT_LINK_XPATH.toString()), 30);
	    ob.findElement(By.xpath(OnePObjectMap.ACCOUNT_LINK_XPATH.toString())).click();

	    // OPQA-1905
	    String actualEmail = ob.findElement(By.xpath(OnePObjectMap.ACCOUNT_ACTUAL_EMAIL_XPATH.toString()))
		    .getText();
	    System.out.println(actualEmail);

	    try {
		Assert.assertEquals(LOGIN.getProperty("UserENW000010"), actualEmail);
		test.log(LogStatus.PASS, " Email id getting displayed in Account Setting page is correct");
	    }

	    catch (Throwable t) {

		test.log(LogStatus.FAIL, "Email id getting displayed in Account Setting page is incorrect");// extent
		// reports
		status = 2;// excel
		test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass()
			.getSimpleName()
			+ "_more_search_results_do_not_get_displayed_when_user_scrolls_down_in_ALL_search_results_page")));// screenshot
		ErrorUtil.addVerificationFailure(t);

	    }
	    // OPQA-1906

	    String passwordText = ob.findElement(By.xpath(OnePObjectMap.ACCOUNT_TEXT_COMPARE_PASSWORD_XPATH.toString()))
		    .getText();
	    System.out.println(passwordText);
	    try {
		Assert.assertEquals(passwordText, "Password is associated with your Facebook account.");
		test.log(LogStatus.PASS,
			"Message 'Password is associated with your Facebook account.' is dispalyed correctly in account setting page");
	    }

	    catch (Throwable t) {

		test.log(LogStatus.FAIL,
			"Message 'Password is associated with your Facebook account.' is not displayed correctly in account setting page");// extent
		// reports
		status = 2;// excel
		test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass()
			.getSimpleName()
			+ "_more_search_results_do_not_get_displayed_when_user_scrolls_down_in_ALL_search_results_page")));// screenshot
		ErrorUtil.addVerificationFailure(t);

	    }
	    String LInkText = ob.findElement(By.xpath(OnePObjectMap.ACCOUNT_LINK_BUTTON_XPATH.toString())).getText();
	    System.out.println(LInkText);
	    try {
		Assert.assertEquals(LInkText, "Link accounts");
		test.log(LogStatus.PASS, "Link button is displayed in Account setting page.");
	    }

	    catch (Throwable t) {

		test.log(LogStatus.FAIL, "Link button is not displayed in Account setting page.");// extent
		// reports
		status = 2;// excel
		test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass()
			.getSimpleName()
			+ "_more_search_results_do_not_get_displayed_when_user_scrolls_down_in_ALL_search_results_page")));// screenshot
		ErrorUtil.addVerificationFailure(t);

	    }
	    ob.findElement(By.xpath(OnePObjectMap.ACCOUNT_LINK_BUTTON_XPATH.toString())).click();

	    waitForElementTobeVisible(ob, By.name(OnePObjectMap.LINK_LOGIN_NAME.toString()), 30);
	    Thread.sleep(30);
	    ob.findElement(By.name("email")).sendKeys(LOGIN.getProperty("UserENWsteam000010"));
	    ob.findElement(By.name("password")).sendKeys(LOGIN.getProperty("PWDuserENW000010"));

	    ob.findElement(By.xpath(OnePObjectMap.DONE_BUTTON_CLICK_XPATH.toString())).click();

	    String actualEmail1 = ob.findElement(By.xpath(OnePObjectMap.ACCOUNT_ACTUAL_EMAIL1_XPATH.toString()))
		    .getText();

	    System.out.println(actualEmail1);
	    try {
		Assert.assertEquals(LOGIN.getProperty("UserENWsteam000010"), actualEmail1);
		test.log(LogStatus.PASS, " Email id getting displayed in Account Setting page is correct");

	    } catch (Throwable t) {
		test.log(LogStatus.FAIL, "Email id getting displayed in Account Setting page is incorrect");
		test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass()
			.getSimpleName()
			+ "_more_search_results_do_not_get_displayed_when_user_scrolls_down_in_ALL_search_results_page")));// screenshot
		ErrorUtil.addVerificationFailure(t);

	    }

	    // OPQA-1904
	    waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.ACCOUNT_fACEBOOK_ICON_XPATH.toString()), 8);
	    waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.ACCOUNT_STEAM_ICON_XPATH.toString()), 8);

	    try {
		if (checkElementPresence("Facebook_icon") && checkElementPresence("Steam_icon")) {
		    test.log(LogStatus.PASS, "Facebook authentication account is linked to the Neon account");
		    System.out.println("pass");
		}

	    } catch (Throwable t) {
		test.log(LogStatus.FAIL, "Facebook authentication account is not linked to the Neon account");
		test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass()
			.getSimpleName()
			+ "_more_search_results_do_not_get_displayed_when_user_scrolls_down_in_ALL_search_results_page")));// screenshot
		ErrorUtil.addVerificationFailure(t);
		System.out.println("fail");

	    }
	    BrowserWaits.waitTime(3);
	    waitForElementTobeVisible(ob, By.xpath(OnePObjectMap.ACCOUNT_RADIOBUTTON1_XPATH.toString()), 30);
	    ob.findElement(By.xpath(OnePObjectMap.ACCOUNT_RADIOBUTTON1_XPATH.toString())).click();

	    String TextcompareAfterLink = ob
		    .findElement(By.xpath(OnePObjectMap.ACCOUNT_TEXT_COMPARE_AFTERLINK_XPATH.toString())).getText();

	    System.out.println(TextcompareAfterLink);
	    try {
		Assert.assertEquals(TextcompareAfterLink,
			"Project Neon has linked your accounts. You can sign in with any of the accounts you already use.");
		test.log(LogStatus.PASS,
			"Message 'Project Neon has linked your accounts.' is displayed correctly after linking from account setting page ");

	    } catch (Throwable t) {
		test.log(LogStatus.FAIL,
			"Message 'Project Neon has linked your accounts.' is not displayed after linking from account setting page ");
		test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass()
			.getSimpleName()
			+ "_more_search_results_do_not_get_displayed_when_user_scrolls_down_in_ALL_search_results_page")));// screenshot
		ErrorUtil.addVerificationFailure(t);

	    }
	    logout();
	    ob.close();

	} catch (Throwable t) {
	    test.log(LogStatus.FAIL, "Something unexpected happened");// extent
								      // reports
	    // next 3 lines to print whole testng error in report
	    StringWriter errors = new StringWriter();
	    t.printStackTrace(new PrintWriter(errors));
	    test.log(LogStatus.INFO, errors.toString());// extent reports
	    ErrorUtil.addVerificationFailure(t);// testng
	    test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(
		    captureScreenshot(this.getClass().getSimpleName() + "_something_unexpected_happened")));// screenshot
	    ErrorUtil.addVerificationFailure(t);

	    closeBrowser();
	}

	test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution ends--->");
    }

    @AfterTest
    public void reportTestResult() {
	extent.endTest(test);

    }
}

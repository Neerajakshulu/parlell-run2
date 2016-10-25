package rcc;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import util.BrowserWaits;
import util.ErrorUtil;
import util.ExtentManager;
import util.OnePObjectMap;
import base.TestBase;

import com.relevantcodes.extentreports.LogStatus;

public class RCC010 extends TestBase {

	static int status = 1;

	/**
	 * Method for displaying JIRA ID's for test case in specified path of Extent Reports
	 * 
	 * @throws Exception, When Something unexpected
	 */
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("RCC");
	}

	/**
	 * Method for wait TR Login Screen
	 * 
	 * @throws Exception, When TR Login screen not displayed
	 */
	@Test
	public void verifyingInvitations() throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;

		if (!master_condition) {
			status = 3;
			test.log(LogStatus.SKIP, "Skipping test case " + this.getClass().getSimpleName()
					+ " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts ");

		try {
			String title = this.getClass().getSimpleName() + "_Group_" + "_" + getCurrentTimeStamp();
			String desc = this.getClass().getSimpleName() + "_Group_" + RandomStringUtils.randomAlphanumeric(100);
			openBrowser();
			clearCookies();
			maximizeWindow();
			ob.navigate().to(host);
			loginAs("GPUSERNAME11", "GPUSERPASSWORD11");
			pf.getGroupsPage(ob).clickOnGroupsTab();
			pf.getGroupsPage(ob).clickOnCreateNewGroupButton();
			pf.getGroupsListPage(ob).createGroup(title, desc);
			test.log(LogStatus.PASS, "Group is created by the owner ");
			boolean result = pf.getGroupDetailsPage(ob).inviteMembers("Jyothi Sree");
			if (result)
				test.log(LogStatus.PASS, "Invitation has been send to the Neon user");
			else
				test.log(LogStatus.FAIL, "Sending Invitation is failed due to user does not exist");
			pf.getLoginTRInstance(ob).logOutApp();
			closeBrowser();
			pf.clearAllPageObjects();

			openBrowser();
			clearCookies();
			maximizeWindow();
			ob.navigate().to(host);
			loginAs("INVITEUSER01", "INVITEUSERPWD");
			pf.getGroupsPage(ob).clickOnGroupsTab();
			waitForAjax(ob);
			result = pf.getGroupInvitationPage(ob).verifyingInvitations(title);
			if (result)
				test.log(LogStatus.PASS, "Member has recieved the invitation from group owner");
			else
				test.log(LogStatus.FAIL, "Member has not recieved the invitation from group owner");

			pf.getLoginTRInstance(ob).logOutApp();
			closeBrowser();
			pf.clearAllPageObjects();

			openBrowser();
			clearCookies();
			maximizeWindow();
			ob.navigate().to(host);
			loginAs("GPUSERNAME11", "GPUSERPASSWORD11");
			test.log(LogStatus.PASS, "Owner has logged into appilication");
			pf.getGroupsPage(ob).clickOnGroupsTab();
			// pf.getBrowserWaitsInstance(ob).waitUntilElementIsNotDisplayed(OnePObjectMap.NEON_TO_ENW_BACKTOENDNOTE_PAGELOAD_CSS);
			waitForAjax(ob);
			pf.getGroupsListPage(ob).clickOnGroupTitle(title);
			test.log(LogStatus.PASS, "Owner navigate to groups details page");
			BrowserWaits.waitTime(6);
			pf.getGroupDetailsPage(ob).clickOnInviteOthersButton();
			pf.getGroupDetailsPage(ob).cancelPendingInvitations("Jyothi Sree");
			test.log(LogStatus.PASS, "Cancelation button is clicked");
			
			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUPS_DETAILS_CANCEL_INVITATION_MODAL_CANCEL_BUTTON_CSS.toString()), 30);
			ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPS_DETAILS_CANCEL_INVITATION_MODAL_CANCEL_BUTTON_CSS.toString())).click();
			
			/*pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.RCC_GROUPS_DETAILS_CANCEL_INVITATION_MODAL_CANCEL_BUTTON_CSS);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.RCC_GROUPS_DETAILS_CANCEL_INVITATION_MODAL_CANCEL_BUTTON_CSS);*/
			test.log(LogStatus.PASS, "Cancel button is working fine for closing modal");
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsNotDisplayed(OnePObjectMap.RCC_GROUPS_DETAILS_CANCEL_INVITATION_MODAL_CSS);
			pf.getGroupDetailsPage(ob).cancelPendingInvitations("Jyothi Sree");
			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUPS_DETAILS_CANCEL_INVITATION_MODAL_CLOSE_BUTTON_CSS.toString()), 30);
			ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPS_DETAILS_CANCEL_INVITATION_MODAL_CLOSE_BUTTON_CSS.toString())).click();
			
			/*pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.RCC_GROUPS_DETAILS_CANCEL_INVITATION_MODAL_CLOSE_BUTTON_CSS);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.RCC_GROUPS_DETAILS_CANCEL_INVITATION_MODAL_CLOSE_BUTTON_CSS);*/
			test.log(LogStatus.PASS, "X button is working fine for closing model");
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsNotDisplayed(OnePObjectMap.RCC_GROUPS_DETAILS_CANCEL_INVITATION_MODAL_CSS);
			pf.getGroupDetailsPage(ob).cancelPendingInvitations("Jyothi Sree");
			waitForElementTobeVisible(ob, By.cssSelector(OnePObjectMap.RCC_GROUPS_DETAILS_CANCEL_INVITATION_MODAL_SUBMIT_BUTTON_CSS.toString()), 30);
			ob.findElement(By.cssSelector(OnePObjectMap.RCC_GROUPS_DETAILS_CANCEL_INVITATION_MODAL_SUBMIT_BUTTON_CSS.toString())).click();
			
			/*pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.RCC_GROUPS_DETAILS_CANCEL_INVITATION_MODAL_SUBMIT_BUTTON_CSS);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.RCC_GROUPS_DETAILS_CANCEL_INVITATION_MODAL_SUBMIT_BUTTON_CSS);*/
			pf.getLoginTRInstance(ob).logOutApp();
			closeBrowser();
			pf.clearAllPageObjects();
			openBrowser();
			clearCookies();
			maximizeWindow();
			ob.navigate().to(host);
			loginAs("INVITEUSER01", "INVITEUSERPWD");
			pf.getGroupsPage(ob).clickOnGroupsTab();
			waitForAjax(ob);
			waitForElementTobeVisible(ob, By.xpath("//a[contains(text(),'Invitations')]"), 30);
			ob.findElement(By.xpath("//a[contains(text(),'Invitations')]")).click();
			
			boolean isInvitationRemoved=pf.getGroupInvitationPage(ob).isInvitationRemoved(title);
			try {
				if (!isInvitationRemoved) {
					throw new Exception("Invitation is not removed from Invitation tab of Invitee");
				}

			} catch (Exception e) {
				test.log(LogStatus.FAIL, "Invitation is not removed from Invitation tab of Invitee");
				ErrorUtil.addVerificationFailure(e);
			}

			pf.getLoginTRInstance(ob).logOutApp();
			closeBrowser();
			pf.clearAllPageObjects();

		} catch (Throwable t) {
			test.log(LogStatus.FAIL, "Something went wrong");
			// print full stack trace
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.FAIL, errors.toString());
			ErrorUtil.addVerificationFailure(t);
			status = 2;// excel
			test.log(
					LogStatus.FAIL,
					"Snapshot below: "
							+ test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()
									+ "_login_not_done")));// screenshot
			closeBrowser();
		}
	}

	/**
	 * updating Extent Report with test case status whether it is PASS or FAIL or SKIP
	 * 
	 * @throws Exception
	 */
	@AfterTest
	public void reportTestResult() throws Exception {
		extent.endTest(test);
		/*
		 * if(status==1) TestUtil.reportDataSetResult(profilexls, "Test Cases",
		 * TestUtil.getRowNum(profilexls,this.getClass().getSimpleName()), "PASS"); else if(status==2)
		 * TestUtil.reportDataSetResult(profilexls, "Test Cases",
		 * TestUtil.getRowNum(profilexls,this.getClass().getSimpleName()), "FAIL"); else
		 * TestUtil.reportDataSetResult(profilexls, "Test Cases",
		 * TestUtil.getRowNum(profilexls,this.getClass().getSimpleName()), "SKIP");
		 */
	}

}

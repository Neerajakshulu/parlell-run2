package suiteD;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.PageFactory;
import util.ErrorUtil;
import util.ExtentManager;
import util.TestUtil;

/**
 * Class for find and follow others profile
 * @author UC202376
 *
 */
public class Profile3 extends TestBase {
	
	String runmodes[]=null;
	static int count=-1;
	
	static boolean fail=false;
	static boolean skip=false;
	static int status=1;
	static String followBefore=null;
	static String followAfter=null;
	static boolean isFollowEnable=false;
	static boolean isFollowDisable=false;
	PageFactory pf;
	
	
	@BeforeTest
	public void beforeTest() throws Exception{ extent = ExtentManager.getReporter(filePath);
		String var=xlRead2(returnExcelPath('D'),this.getClass().getSimpleName(),1);
		test = extent
				.startTest(var,
						"Verify that user should not able to  Edit others profile").assignCategory("Profile");
		runmodes=TestUtil.getDataSetRunmodes(suiteDxls, this.getClass().getSimpleName());
	}
			
	@Test
	@Parameters({"username","password"})
	public void testLoginTRAccount(String username,String password) throws Exception  {
		
		boolean suiteRunmode=TestUtil.isSuiteRunnable(suiteXls, "D Suite");
		boolean testRunmode=TestUtil.isTestCaseRunnable(suiteDxls,this.getClass().getSimpleName());
		boolean master_condition=suiteRunmode && testRunmode;
		
		if(!master_condition) {
			status=3;
			test.log(LogStatus.SKIP, "Skipping test case "+this.getClass().getSimpleName()+" as the run mode is set to NO");
			throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
		}
		
		
		// test the runmode of current dataset
		count++;
		if(!runmodes[count].equalsIgnoreCase("Y")) {
			test.log(LogStatus.INFO, "Runmode for test set data set to no "+count);
			skip=true;
			throw new SkipException("Runmode for test set data set to no "+count);
		}
		test.log(LogStatus.INFO,this.getClass().getSimpleName()+" execution starts ");
		
				try {
					openBrowser();
					clearCookies();
					maximizeWindow();
					ob.navigate().to(System.getProperty("host"));
					
					/*loginTR= new LoginTR(ob);
					searchProfile=new SearchProfile(ob);
					profilePage=new ProfilePage(ob);
					
					
					loginTR.waitForTRHomePage();
					loginTR.enterTRCredentials(username, password);
					loginTR.clickLogin();*/
					pf=new PageFactory();
					
					pf.getLoginTRInstance(ob).waitForTRHomePage();
					pf.getLoginTRInstance(ob).enterTRCredentials(username, password);
					pf.getLoginTRInstance(ob).clickLogin();
					
				} catch (Throwable e) {
					test.log(LogStatus.FAIL,"Error:"+e);
					ErrorUtil.addVerificationFailure(e);
					status=2;//excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_unable_to_find_others_profile")));
					closeBrowser();
				}
				
	}
	
	/**
	 * Method for find and follow others profile
	 * @throws Exception
	 */
	@Test(dependsOnMethods="testLoginTRAccount")
	public void OtherProfileEditDetails() throws Exception  {
				try {
					test.log(LogStatus.INFO,"validate logged user should have previliges to edit others profile");
					pf.getSearchProfilePageInstance(ob).enterSearchKeyAndClick("hao");
					pf.getSearchProfilePageInstance(ob).clickPeople();
					pf.getProfilePageInstance(ob).clickProfile();
					pf.getProfilePageInstance(ob).validateOtherProfileEdit();
					pf.getLoginTRInstance(ob).logOutApp();
					
					/*searchProfile.enterSearchKeyAndClick("hao");
					searchProfile.clickPeople();
					profilePage.clickProfile();
					profilePage.validateOtherProfileEdit();
					
					loginTR.logOutApp();*/
					
					test.log(LogStatus.INFO,this.getClass().getSimpleName()+" execution ends ");
					closeBrowser();

				} catch (Throwable t) {
					test.log(LogStatus.FAIL,"Something Unexpected");
					//print full stack trace
					StringWriter errors = new StringWriter();
					t.printStackTrace(new PrintWriter(errors));
					test.log(LogStatus.INFO,errors.toString());
					ErrorUtil.addVerificationFailure(t);
					status=2;//excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_unable_get_others_profile_details")));
					closeBrowser();
				}
	}
	
	@AfterTest
	public void reportTestResult() {
		
		extent.endTest(test);
		
		/*if(status==1)
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases", TestUtil.getRowNum(suiteDxls,this.getClass().getSimpleName()), "PASS");
		else if(status==2)
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases", TestUtil.getRowNum(suiteDxls,this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases", TestUtil.getRowNum(suiteDxls,this.getClass().getSimpleName()), "SKIP");*/
	}
	
}
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
import pages.ProfilePage;
import pages.SearchProfile;
import suiteC.LoginTR;
import util.ErrorUtil;
import util.TestUtil;

/**
 * Class for profile search with Role
 * @author UC202376
 *
 */
public class FindProfileWithRoleTest extends TestBase {
	
	String runmodes[]=null;
	static int count=-1;
	
	static boolean fail=false;
	static boolean skip=false;
	static int status=1;
	
	
	
	@BeforeTest
	public void beforeTest() throws Exception {
		String var=xlRead2(returnExcelPath('D'),this.getClass().getSimpleName(),1);
		test = extent.startTest(var, "Verify Profile Search with Title/Role").assignCategory("Suite D");
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
		test.log(LogStatus.INFO,this.getClass().getSimpleName()+" execution starts for data set #"+ count+"--->");
		
				try {
					openBrowser();
					clearCookies();
					maximizeWindow();
					ob.navigate().to(System.getProperty("host"));
					LoginTR.waitForTRHomePage();
					LoginTR.enterTRCredentials(username, password);
					LoginTR.clickLogin();
				} catch (Throwable e) {
					test.log(LogStatus.FAIL,"Error:"+e);
					ErrorUtil.addVerificationFailure(e);
					status=2;//excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_unable_to_find_others_profile")));
					closeBrowser();
				}
				
	}
	
	/**
	 * Method for find other profile with their Role
	 * @throws Exception
	 */
	@Test(dependsOnMethods="testLoginTRAccount")
	@Parameters("role")
	public void findOthersProfileWithRole(String role) throws Exception  {
				try {
					SearchProfile.enterSearchKeyAndClick(role);
					if(SearchProfile.getPeopleCount()>0) {
						SearchProfile.clickPeople();
						test.log(LogStatus.INFO, "validate populated search profile results having provided role");
						ProfilePage.validateProfileMetaData(role);
					}
					LoginTR.logOutApp();
					closeBrowser();
				} catch (Throwable t) {
					test.log(LogStatus.FAIL,"Something Unexpected");
					//print full stack trace
					StringWriter errors = new StringWriter();
					t.printStackTrace(new PrintWriter(errors));
					test.log(LogStatus.INFO,errors.toString());
					ErrorUtil.addVerificationFailure(t);
					status=2;//excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_unable_to_find_others_profile_with_their_role")));
					closeBrowser();
				}
	}
	
	@AfterTest
	public void reportTestResult() {
		
		extent.endTest(test);
		
		if(status==1)
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases", TestUtil.getRowNum(suiteDxls,this.getClass().getSimpleName()), "PASS");
		else if(status==2)
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases", TestUtil.getRowNum(suiteDxls,this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteDxls, "Test Cases", TestUtil.getRowNum(suiteDxls,this.getClass().getSimpleName()), "SKIP");
		//closeBrowser();
	}
	
}

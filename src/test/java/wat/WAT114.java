package wat;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.ExtentManager;
import util.OnePObjectMap;

/**
 * Class for Verify that during an author search, if the system determines that there are >50 search results then country dropdown should be displayed
 * 
 * @author UC225218
 *
 */

public class WAT114 extends TestBase {

	static int status = 1;
	static String wos_title = "Web of Science: Author search";
	static String sar_labs_text = "SaR Labs";
	static String Suggestion_text = "We've found a large number of records matching your search.\n"
			+ "For the most relevant results, please select at least one location where this author has published, or select Find to view all results.";
		
	/**
	 * Method for displaying JIRA ID's for test case in specified path of Extent
	 * Reports
	 * 
	 * @throws Exception,
	 *             When Something unexpected
	 */
	@BeforeTest
	public void beforeTest() throws Exception {
		extent = ExtentManager.getReporter(filePath);
		rowData = testcase.get(this.getClass().getSimpleName());
		test = extent.startTest(rowData.getTestcaseId(), rowData.getTestcaseDescription()).assignCategory("WAT");
	}

	/**
	 * Method for login into WAT application using Steam ID
	 * 
	 * @throws Exception,
	 *             When WAT Login is not done
	 */
	@Test
	@Parameters({ "username", "password" })
	public void testLoginWATApp(String username, String password) throws Exception {

		boolean testRunmode = getTestRunMode(rowData.getTestcaseRunmode());
		boolean master_condition = suiteRunmode && testRunmode;
		logger.info("checking master condition status-->" + this.getClass().getSimpleName() + "-->" + master_condition);

		if (!master_condition) {
			status = 3;
			test.log(LogStatus.SKIP,
					"Skipping test case " + this.getClass().getSimpleName() + " as the run mode is set to NO");
			throw new SkipException("Skipping Test Case" + this.getClass().getSimpleName() + " as runmode set to NO");// reports
		}

		test.log(LogStatus.INFO, this.getClass().getSimpleName() + " execution starts.... ");

		try {
			openBrowser();
			clearCookies();
			maximizeWindow();
			test.log(LogStatus.INFO, "Login to WAT Applicaton using valid WAT Entitled user ");
			ob.navigate().to(host + CONFIG.getProperty("appendWATAppUrl"));
			pf.getLoginTRInstance(ob).loginToWAT(username, password, test);

		} catch (Throwable t) {
			logFailureDetails(test, t, "Login Fail", "login_fail");
			pf.getBrowserActionInstance(ob).closeBrowser();
		}

	}

	/**
	 * Method to Verify that during an author search, if the system determines that there are >50 search results then country dropdown should be displayed
	 * 
	 * @throws Exception,
	 *             When Something unexpected
	 * 
	 */
	@SuppressWarnings("static-access")
	@Test(dependsOnMethods = { "testLoginWATApp" })
	@Parameters({ "LastName", "CountryName1", "CountryName2","OrgName1","OrgName2" })
	public void testCountryDropdownFunctionality(String LastName, String CountryName1,String CountryName2, String OrgName1,String OrgName2)
			throws Exception {
			try {
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH);
				pf.getSearchAuthClusterPage(ob).enterAuthorLastName(LastName, test);
				pf.getSearchAuthClusterPage(ob).cliclFindBtn(test);
				pf.getBrowserWaitsInstance(ob).waitTime(4);
				Assert.assertTrue(pf.getBrowserActionInstance(ob)
						.getElement(OnePObjectMap.WAT_AUTHOR_SEARCH_DRILL_DOWN_SUGGESTION_TEXT_XPATH).isDisplayed(),
						"AUTHOR SEARCH DRILL DOWN SUGGESTION TEXT IS DISPLAYED");
				Assert.assertEquals(pf.getBrowserActionInstance(ob)
						.getElement(OnePObjectMap.WAT_AUTHOR_SEARCH_DRILL_DOWN_SUGGESTION_TEXT_XPATH).getText(), Suggestion_text,
						"AUTHOR SEARCH DRILL DOWN SUGGESTION TEXT IS NOT MATCHING");
				test.log(LogStatus.INFO, "AUTHOR SEARCH DRILL DOWN SUGGESTION TEXT IS DISPLAYED AND MATCHING");
				List<WebElement> ele = pf.getBrowserActionInstance(ob)
						.getElements(OnePObjectMap.WAT_AUTHOR_COUNTRY_DROPDOWN_XPATH);
				if (ele.size() != 0) 
				{
					test.log(LogStatus.PASS, "Country Dropdown is disaplayed when search results are more than 50 clusters");
					pf.getBrowserActionInstance(ob).closeBrowser();
				}
				else throw new Exception("Country Dropdown is not disaplayed when search results are more than 50 clusters");
			} catch (Throwable t) {
				logFailureDetails(test, t, "Authorcluster search with only last name failed", "search_fail");
				pf.getBrowserActionInstance(ob).closeBrowser();
			}

		}

	/**
	 * updating Extent Report with test case status whether it is PASS or FAIL
	 * or SKIP
	 */
	@AfterTest
	public void reportTestResult() {

		extent.endTest(test);

		/*
		 * if (status == 1) TestUtil.reportDataSetResult(profilexls,
		 * "Test Cases", TestUtil.getRowNum(profilexls,
		 * this.getClass().getSimpleName()), "PASS"); else if (status == 2)
		 * TestUtil.reportDataSetResult(profilexls, "Test Cases",
		 * TestUtil.getRowNum(profilexls, this.getClass().getSimpleName()),
		 * "FAIL"); else TestUtil.reportDataSetResult(profilexls, "Test Cases",
		 * TestUtil.getRowNum(profilexls, this.getClass().getSimpleName()),
		 * "SKIP");
		 */

	}
}

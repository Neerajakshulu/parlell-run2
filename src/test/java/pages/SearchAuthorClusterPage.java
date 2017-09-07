package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import util.BrowserWaits;
import util.OnePObjectMap;

/**
 * Class for Perform Browser Actions
 * 
 * @author UC225218
 *
 */
public class SearchAuthorClusterPage extends TestBase {

	int k = 1;
	static String wos_title = "Web of Science: Author search";

	public SearchAuthorClusterPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}

	/**
	 * Common method to search for an author cluster with only LastName
	 * 
	 * @param LastName
	 * @author UC225218
	 * @throws Exception
	 * 
	 */
	public void SearchAuthorCluster(String LastName, String Country, String org, ExtentTest test) throws Exception {
		try {
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH);
			enterAuthorLastName(LastName, test);
			pf.getBrowserWaitsInstance(ob)
					.waitUntilElementIsDisplayed(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH);
			test.log(LogStatus.INFO, "Clicking find button... ");
			BrowserWaits.waitTime(2);
			if (pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH)
					.isEnabled()) {
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH);
			} else {
				throw new Exception("FIND button not clicked");
			}
			List<WebElement> ele = pf.getBrowserActionInstance(ob)
					.getElements(OnePObjectMap.WAT_AUTHOR_COUNTRY_DROPDOWN_XPATH);
			if (ele.size() != 0) {
				selectCountryofAuthor(Country, test);
				pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.WAT_AUTHOR_ORG_DROPDOWN_XPATH);
				List<WebElement> orgName = pf.getBrowserActionInstance(ob)
						.getElements(OnePObjectMap.WAT_AUTHOR_ORG_DROPDOWN_XPATH);
				if (orgName.size() != 0) {
					selectOrgofAuthor(org, test);
				}
				test.log(LogStatus.INFO, "Org name selection is not required as the searched user has only one org. ");
			} else {
				test.log(LogStatus.INFO,
						"Country name selection is not required as the searched user resulted in less than 50 clusters... ");
			}
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.WAT_SEARCH_RESULTS_TEXT_XPATH);
			Assert.assertEquals(
					(pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_SEARCH_RESULTS_TEXT_XPATH).getText()),
					"Search Results", "Unable to search for an author and land in Author search result page.");
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Unable to search for an author cluster and land in Author search result page.");
			throw new Exception();
		}
	}

	/**
	 * Common method to search for an author cluster with LastName and FirstName
	 * 
	 * @throws @author
	 *             UC225218
	 * @param LastName
	 *            FirstName
	 * @throws Exception
	 */
	public void SearchAuthorCluster(String LastName, String FirstName, String Country, String org, ExtentTest test)
			throws Exception {
		try {
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH);
			enterAuthorLastName(LastName, test);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH);
			enterAuthorFirstName(FirstName, test);
			pf.getBrowserWaitsInstance(ob)
					.waitUntilElementIsDisplayed(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH);
			test.log(LogStatus.INFO, "Clicking find button... ");

			if (pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH)
					.isEnabled()) {
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH);
			}
			List<WebElement> ele = pf.getBrowserActionInstance(ob)
					.getElements(OnePObjectMap.WAT_AUTHOR_COUNTRY_DROPDOWN_XPATH);
			if (ele.size() != 0) {
				selectCountryofAuthor(Country, test);
				List<WebElement> orgName = pf.getBrowserActionInstance(ob)
						.getElements(OnePObjectMap.WAT_AUTHOR_ORG_DROPDOWN_XPATH);
				if (orgName.size() != 0) {
					selectOrgofAuthor(org, test);
				}
				test.log(LogStatus.INFO, "Org name selection is not required as the searched user has only one org. ");
			} else {
				test.log(LogStatus.INFO,
						"Country name selection is not required as the searched user resulted in less than 50 clusters... ");
			}
			pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.WAT_SEARCH_RESULTS_TEXT_XPATH);
			Assert.assertEquals(
					(pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_SEARCH_RESULTS_TEXT_XPATH).getText()),
					"Search Results", "Unable to search for an author and landed in Author search result page.");
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Unable to search for an author and landed in Author search result page.");
			throw new Exception(e);
		}
	}

	/**
	 * Method to enter the author search name (Last name) character by
	 * character.
	 * 
	 * @throws Exception
	 * @param Name
	 *            element Typeahead
	 * 
	 */
	public void enterAuthorLastName(String LastName, ExtentTest test) throws Exception {
		try {
			pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH).clear();
			for (int i = 0; i < LastName.length(); i++) {
				char c = LastName.charAt(i);
				pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH,
						String.valueOf(c));
				BrowserWaits.waitTime(0.5);
			}
			selectLastNameFromTypeahead(LastName, test);
		} catch (Exception e) {
			e.getMessage();
			test.log(LogStatus.FAIL, "Author Last name not entered");
		}
	}

	/**
	 * Method to enter the author search name (Last name) character by
	 * character. This method is special case for Alternate name typeahead
	 * search scenario.
	 * 
	 * @throws Exception
	 * @param Name
	 *            element Typeahead
	 * 
	 */
	public void enterAuthorLastName(OnePObjectMap path, String LastName, ExtentTest test) throws Exception {
		try {
			pf.getBrowserActionInstance(ob).getElement(path).clear();
			for (int i = 0; i < LastName.length(); i++) {
				char c = LastName.charAt(i);
				String s = new StringBuilder().append(c).toString();
				pf.getBrowserActionInstance(ob).enterFieldValue(path, s);
				BrowserWaits.waitTime(0.5);
			}
			selectLastNameFromTypeahead(LastName, test);
		} catch (Exception e) {
			e.getMessage();
			test.log(LogStatus.FAIL, "Author Last name not entered");
		}
	}

	/**
	 * Method to enter the author search name (First name) character by
	 * character.
	 * 
	 * @throws Exception
	 * @param Name
	 * 
	 */
	public void enterAuthorFirstName(String FirstName, ExtentTest test) throws Exception {

		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH).clear();
		for (int i = 0; i < FirstName.length(); i++) {
			char c = FirstName.charAt(i);
			pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH,
					String.valueOf(c));
			BrowserWaits.waitTime(0.5);
		}
		selectFirstNameFromTypeahead(FirstName, test);
		test.log(LogStatus.INFO, "Author First name entered");
	}

	/**
	 * Method to enter the author search name (First name) character by
	 * character. This method is special case for Alternate name typeahead
	 * search scenario.
	 * 
	 * @throws Exception
	 * @param Name
	 * 
	 */
	public void enterAuthorFirstName(OnePObjectMap path, String FirstName, ExtentTest test) throws Exception {

		pf.getBrowserActionInstance(ob).getElement(path).clear();
		for (int i = 0; i < FirstName.length(); i++) {
			char c = FirstName.charAt(i);
			String s = new StringBuilder().append(c).toString();
			pf.getBrowserActionInstance(ob).enterFieldValue(path, s);
			BrowserWaits.waitTime(0.5);
		}
		selectFirstNameFromTypeahead(FirstName, test);
		test.log(LogStatus.INFO, "Author First name entered");
	}

	/**
	 * Method to select value from typeahead, This will loop for 5 times incase
	 * if typeahead suggestions are not displayed.(Configurable)
	 * 
	 * @throws Exception
	 * 
	 * @param lnTypeahead
	 *            selectionText
	 * 
	 */
	public void selectLastNameFromTypeahead(String selectionText, ExtentTest test) throws Exception {
		while (k < 2) {
			List<WebElement> web = pf.getBrowserActionInstance(ob)
					.getElements(OnePObjectMap.WAT_AUTHOR_LASTNAME_TYPEAHEAD_XPATH);
			List<WebElement> ele = pf.getBrowserActionInstance(ob)
					.getElements(OnePObjectMap.WAT_AUTHOR_NAME_NOT_FOUND_ERROR_XPATH);
			List<WebElement> lastNameSuggestions = pf.getBrowserActionInstance(ob)
					.getElements(OnePObjectMap.WAT_AUTHOR_LASTNAME_TYPEAHEAD_OPTION_XPATH);

			if (web.size() != 0) {
				for (int j = 0; j < lastNameSuggestions.size(); j++) {
					if (lastNameSuggestions.get(j).getText().equals(selectionText)) {
						lastNameSuggestions.get(j).click();
						test.log(LogStatus.INFO, "Typeahead is present for last name");
						test.log(LogStatus.INFO, "Author Last name entered");
						break;
					}
				}
				break;
			} else if (ele.size() == 0 && pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH).isDisplayed()) {
				test.log(LogStatus.INFO, "Author Last name entered");
				test.log(LogStatus.INFO,
						"No Typeahead displayed, Might be due to exact matching of provided last name with name in typeahead suggestion, Hence directly clicking FIND button");
				break;
			} else if (pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_NAME_NOT_FOUND_ERROR_XPATH)
					.isDisplayed()) {
				test.log(LogStatus.FAIL, "Entered name is incorrect, Please enter a valid last name ");
				break;
			} else {
				if (k == 2)
					throw new Exception("No Typeahead suggestion displayed even after 2 attempts");
				k++;
				enterAuthorLastName(selectionText, test);
			}
		}
	}

	/**
	 * Method to select value from typeahead, This will loop for 5 times incase
	 * if typeahead suggestions are not displayed.(Configurable)
	 * 
	 * @throws Exception
	 * 
	 * @param lnTypeahead
	 *            selectionText
	 * 
	 */
	public void selectFirstNameFromTypeahead(String selectionText, ExtentTest test) throws Exception {
		while (k < 2) {
			List<WebElement> web = pf.getBrowserActionInstance(ob)
					.getElements(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_TYPEAHEAD_XPATH);
			List<WebElement> ele = pf.getBrowserActionInstance(ob)
					.getElements(OnePObjectMap.WAT_AUTHOR_NAME_NOT_FOUND_ERROR_XPATH);
			List<WebElement> firstNameSuggestions = pf.getBrowserActionInstance(ob)
					.getElements(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_TYPEAHEAD_OPTION_XPATH);

			if (web.size() != 0) {
				for (int j = 0; j < firstNameSuggestions.size(); j++) {
					if (firstNameSuggestions.get(j).getText().equals(selectionText)) {
						firstNameSuggestions.get(j).click();
						test.log(LogStatus.INFO, "Typeahead is present for first name");
						test.log(LogStatus.INFO, "Author First name entered");
						break;
					}
				}
				break;
			} else if (ele.size() == 0 && pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH).isDisplayed()) {
				test.log(LogStatus.INFO, "Author Last name entered");
				test.log(LogStatus.INFO,
						"No Typeahead displayed, Might be due to exact matching of provided first name with name in typeahead suggestion, Hence directly clicking FIND button");
				break;
			} else if (pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_NAME_NOT_FOUND_ERROR_XPATH)
					.isDisplayed()) {
				test.log(LogStatus.FAIL, "Entered name is incorrect, Please enter a valid First name ");
				break;
			} else {
				if (k == 2)
					throw new Exception("No Typeahead suggestion displayed even after 2 attempts");
				k++;
				enterAuthorLastName(selectionText, test);
			}
		}
	}

	/**
	 * Method to select Country value for further filtering of author cluster.
	 * 
	 * @author UC225218
	 * @throws Exception
	 * 
	 * 
	 */
	public void selectCountryofAuthor(String CountryName, ExtentTest test) throws Exception {
		pf.getBrowserWaitsInstance(ob).waitForElementTobeVisible(ob,
				By.xpath(OnePObjectMap.WAT_AUTHOR_COUNTRY_DROPDOWN_XPATH.toString()), 3,
				"Country dropdown is not present in Author search page");
		try {
			pf.getBrowserActionInstance(ob).moveToElement(OnePObjectMap.WAT_AUTHOR_COUNTRY_DROPDOWN_XPATH);
			test.log(LogStatus.INFO,
					"Country name selected as the searched user resulted in more than 50 clusters... ");
			List<WebElement> ctry = pf.getBrowserActionInstance(ob)
					.getElements(OnePObjectMap.WAT_AUTHOR_COUNTRY_OPTION_XPATH);
			if (ctry.size() != 0) {
				for (int j = 0; j < ctry.size(); j++) {
					if (ctry.get(j).getText().equals(CountryName)) {
						ctry.get(j).click();
						test.log(LogStatus.INFO, "Country name clicked");
						break;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Method to select organization value for further filtering of author
	 * cluster.
	 * 
	 * @author UC225218
	 * @throws Exception
	 * 
	 * 
	 */
	public void selectOrgofAuthor(String orgName, ExtentTest test) throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsDisplayed(OnePObjectMap.WAT_AUTHOR_ORG_DROPDOWN_XPATH, 5);
		try {
			test.log(LogStatus.INFO, "Selecting relavent organization... ");
			pf.getBrowserActionInstance(ob).moveToElement(OnePObjectMap.WAT_AUTHOR_ORG_DROPDOWN_XPATH);
			List<WebElement> org = pf.getBrowserActionInstance(ob)
					.getElements(OnePObjectMap.WAT_AUTHOR_ORG_OPTION_XPATH);
			if (org.size() != 0) {
				for (int j = 0; j < org.size(); j++) {
					if (org.get(j).getText().equals(orgName)) {
						org.get(j).click();
						test.log(LogStatus.INFO, "Org name clicked");
						break;
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param test
	 * @throws Exception
	 */
	public void validateAuthorSearchPage(ExtentTest test) throws Exception {
		Assert.assertTrue(
				pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH).isDisplayed(),
				"Lastname text box not visible");
		test.log(LogStatus.INFO, "User is able to see last name textbox and can be used for author cluster search");

		Assert.assertTrue(
				pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH).isDisplayed(),
				"Firstname text box not visible");
		test.log(LogStatus.INFO, "User is able to see First name textbox and can be used for author cluster search");
	}

	/**
	 * Method for Search Author cluster using last name
	 * 
	 * @param LastName
	 * @param test
	 * @throws Exception,
	 *             When unable to search for author using last name
	 */
	public void SearchAuthorCluster(String LastName, ExtentTest test) throws Exception {
		try {
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH);
			enterAuthorLastName(LastName, test);
			pf.getBrowserWaitsInstance(ob)
					.waitUntilElementIsClickable(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH);
			pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH);
			test.log(LogStatus.INFO, "Find button clicked");
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Unable to search for an author using lastname");
			throw new Exception(e);
		}
	}

	/**
	 * @param LastName
	 * @param FirstName
	 * @param CountryName
	 * @param OrgName
	 * @throws Exception
	 */
	public void searchAuthorClusterLastandFirstname(String LastName, String FirstName, String CountryName,
			String OrgName, ExtentTest test) throws Exception {
		// Verify whether control is in Author Search page
		Assert.assertEquals(
				pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_WOS_AUTHOR_SEARCH_TITLE_XPATH).getText(),
				wos_title, "Control is not in WOS Author Search page");
		test.log(LogStatus.INFO, "Control is in WOS Author Search page");

		// Search for an author cluster
		test.log(LogStatus.INFO, "Entering author name... ");
		pf.getSearchAuthClusterPage(ob).SearchAuthorCluster(LastName, FirstName, CountryName, OrgName, test);
		test.log(LogStatus.PASS, "Successfully searched for an author and landed in Author search result page.");
		pf.getBrowserActionInstance(ob).closeBrowser();
	}

	public void searchAuthorClusterOnlyLastName(String LastName, String CountryName, String OrgName, ExtentTest test)
			throws Exception {
		// Verify whether control is in Author Search page
		Assert.assertEquals(
				pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_WOS_AUTHOR_SEARCH_TITLE_XPATH).getText(),
				wos_title, "Control is not in WOS Author Search page");
		test.log(LogStatus.INFO, "Control is in WOS Author Search page");

		// Search for an author cluster with only Last name
		test.log(LogStatus.INFO, "Entering author name... ");
		pf.getSearchAuthClusterPage(ob).SearchAuthorCluster(LastName, CountryName, OrgName, test);
		test.log(LogStatus.PASS,
				"Successfully searched for an author using only Last name and landed in Author search result page.");
		pf.getBrowserActionInstance(ob).closeBrowser();
	}

	public void searchAuthorClusterWithOnlyFirstName(String FirstName, ExtentTest test)
			throws Exception, InterruptedException {
		// Verify whether control is in Author Search page
		Assert.assertEquals(
				pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_WOS_AUTHOR_SEARCH_TITLE_XPATH).getText(),
				wos_title, "Control is not in WOS Author Search page");
		test.log(LogStatus.INFO, "Control is in WOS Author Search page");

		// Search for an author cluster with only FIRST name
		test.log(LogStatus.INFO, "Entering author First name... ");
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH);
		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH).clear();
		for (int i = 0; i < FirstName.length(); i++) {
			char c = FirstName.charAt(i);
			String s = new StringBuilder().append(c).toString();
			pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH, s);
			BrowserWaits.waitTime(0.5);
		}
		test.log(LogStatus.INFO, "Trying to click find button... ");

		if (pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH)
				.isEnabled()) {
			test.log(LogStatus.FAIL, "Able to search for author cluster with only First name");
		}
		test.log(LogStatus.PASS, "User cant search for Author cluster with only first name");
		pf.getBrowserActionInstance(ob).closeBrowser();
	}

	public void searchAuthorClusterLastNameTypeahead(ExtentTest test) throws Exception {
		// Verify whether control is in Author Search page
		Assert.assertEquals(
				pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_WOS_AUTHOR_SEARCH_TITLE_XPATH).getText(),
				wos_title, "Control is not in WOS Author Search page");
		test.log(LogStatus.INFO, "Control is in WOS Author Search page");
		test.log(LogStatus.INFO, "Entering author name... ");

		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH).clear();
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH, "U");
		if (pf.getBrowserActionInstance(ob).getElement((OnePObjectMap.WAT_AUTHOR_LASTNAME_TYPEAHEAD_XPATH))
				.isDisplayed()) {
			test.log(LogStatus.PASS, "Typeahead displayed for minimum 1 Letter");
		}
		pf.getBrowserActionInstance(ob).closeBrowser();
	}

	public void searchAuthorClusterFirstNameTypeahead(String LastName, ExtentTest test) throws Exception {
		// Verify whether control is in Author Search page
		Assert.assertEquals(
				pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_WOS_AUTHOR_SEARCH_TITLE_XPATH).getText(),
				wos_title, "Control is not in WOS Author Search page");
		test.log(LogStatus.INFO, "Control is in WOS Author Search page");
		test.log(LogStatus.INFO, "Entering author name... ");

		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH).clear();
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH);
		pf.getSearchAuthClusterPage(ob).enterAuthorLastName(LastName, test);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH, "J");
		if (pf.getBrowserActionInstance(ob).getElement((OnePObjectMap.WAT_AUTHOR_FIRSTNAME_TYPEAHEAD_XPATH))
				.isDisplayed()) {
			test.log(LogStatus.PASS, "First name Typeahead displayed for minimum 1 Letter - Firststname");
			pf.getBrowserActionInstance(ob).closeBrowser();
		}
	}

	public void searchAuthorClusterBlankLastName(ExtentTest test) throws Exception {
		// Verify whether control is in Author Search page
		Assert.assertEquals(
				pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_WOS_AUTHOR_SEARCH_TITLE_XPATH).getText(),
				wos_title, "Control is not in WOS Author Search page");
		test.log(LogStatus.INFO, "Control is in WOS Author Search page");

		test.log(LogStatus.INFO, "Entering author First name... ");
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH);
		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH).clear();
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH, " ");
		test.log(LogStatus.INFO, "Trying to click find button... ");

		if (pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH)
				.isEnabled()) {
			test.log(LogStatus.FAIL, "Able to search for author cluster with blank last name");
			throw new Exception("User is able to search for author cluster with blank last name");
		}
		test.log(LogStatus.PASS, "User cant search for Author cluster with blank last name");
		pf.getBrowserActionInstance(ob).closeBrowser();
	}

	public void searchAuthorClusterWithSymbolsLastName(String Symbols, ExtentTest test) throws Exception {
		// Verify whether control is in Author Search page
		Assert.assertEquals(
				pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_WOS_AUTHOR_SEARCH_TITLE_XPATH).getText(),
				wos_title, "Control is not in WOS Author Search page");
		test.log(LogStatus.INFO, "Control is in WOS Author Search page");

		// Search for an author cluster with symbols in last name
		test.log(LogStatus.INFO, "Entering author last name... ");
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH);
		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH).clear();

		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH, Symbols);
		List<WebElement> ele = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.WAT_AUTHOR_NAME_NOT_FOUND_ERROR_XPATH);
		test.log(LogStatus.INFO, "Trying to click find button... ");
		if (ele.size() != 0 && !pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH).isEnabled()) {
			test.log(LogStatus.PASS, "Unable to search for author cluster with special character in Last name");
		} else {
			test.log(LogStatus.FAIL,
					"User is able to search for Author cluster with special character in Last name OR Find button is enabled for Symbol search");
			throw new Exception(
					"User is able to search for Author cluster with special character in Last name OR Find button is enabled for Symbol search");
		}
	}

	public void searchAuthorClusterWithOnlyFirstName(ExtentTest test) throws Exception {
		// Verify whether control is in Author Search page
		Assert.assertEquals(
				pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_WOS_AUTHOR_SEARCH_TITLE_XPATH).getText(),
				wos_title, "Control is not in WOS Author Search page");
		test.log(LogStatus.INFO, "Control is in WOS Author Search page");

		test.log(LogStatus.INFO, "Entering author Last name... ");
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH);
		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH).clear();
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH, "10");
		List<WebElement> ele = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.WAT_AUTHOR_NAME_NOT_FOUND_ERROR_XPATH);
		test.log(LogStatus.INFO, "Trying to click find button... ");

		if (ele.size() != 0 && !pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH).isEnabled()) {
			test.log(LogStatus.PASS, "Unable to search for author cluster with numbers in Last name");
		} else {
			test.log(LogStatus.FAIL,
					"User is able to search for Author cluster with numbers in Last name OR Find button is enabled for number search");
			throw new Exception(
					"User is able to search for Author cluster with numbers in Last name OR Find button is enabled for number search");
		}
	}

	public void searchAuthorClusterWithAlphaneumericLastName(ExtentTest test) throws Exception {
		// Verify whether control is in Author Search page
		Assert.assertEquals(
				pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_WOS_AUTHOR_SEARCH_TITLE_XPATH).getText(),
				wos_title, "Control is not in WOS Author Search page");
		test.log(LogStatus.INFO, "Control is in WOS Author Search page");

		test.log(LogStatus.INFO, "Entering author Last name... ");
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH);
		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH).clear();
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH, "Test007");
		List<WebElement> ele = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.WAT_AUTHOR_NAME_NOT_FOUND_ERROR_XPATH);
		test.log(LogStatus.INFO, "Trying to click find button... ");

		if (ele.size() != 0 && !pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH).isEnabled()) {
			test.log(LogStatus.PASS, "Unable to search for author cluster with alphanumeric characters in Last name");
		} else {
			test.log(LogStatus.FAIL,
					"User is able to search for Author cluster with alphanumeric characters in Last name OR Find button is enabled for alphanumeric characters search");
			throw new Exception(
					"User is able to search for Author cluster with alphanumeric characters in Last name OR Find button is enabled for alphanumeric characters search");
		}
	}

	public void searchAuthorClusterBlankFirstName(String LastName, ExtentTest test) throws Exception {
		// Verify whether control is in Author Search page
		Assert.assertEquals(
				pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_WOS_AUTHOR_SEARCH_TITLE_XPATH).getText(),
				wos_title, "Control is not in WOS Author Search page");
		test.log(LogStatus.INFO, "Control is in WOS Author Search page");
		test.log(LogStatus.INFO, "Entering author Last name... ");
		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH).clear();
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH);
		pf.getSearchAuthClusterPage(ob).enterAuthorLastName(LastName, test);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH);
		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH).clear();
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH, " ");
		test.log(LogStatus.INFO, "Trying to click find button... ");
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH);
		if (pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH)
				.isEnabled()
				&& pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_COUNTRY_DROPDOWN_XPATH)
						.isEnabled()) {
			test.log(LogStatus.PASS, "User is able to search for author cluster with blank First name");
		}
	}

	public void searchAuthorClusterWithSymbolsLastName(String Symbols, String errorMessage, ExtentTest test)
			throws Exception {
		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH).clear();
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH);
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH, Symbols);
		List<WebElement> ele = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.WAT_AUTHOR_NAME_NOT_FOUND_ERROR_XPATH);
		if (ele.size() != 0 && !pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH).isEnabled()) {
			if (pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_NAME_NOT_FOUND_ERROR_XPATH)
					.getText().equals(errorMessage)) {
				test.log(LogStatus.INFO, "Error text matching for symbol -----------    " + Symbols);
				test.log(LogStatus.PASS, "Unable to search for author cluster with special character in First name");
			}
		} else {
			test.log(LogStatus.FAIL,
					"User is able to search for Author cluster with special character in First name OR Find button is enabled for Symbol search");
			throw new Exception(
					"Test User is able to search for Author cluster with special character in First name OR Find button is enabled for Symbol search");
		}
	}

	public void searchAuthorClusterNumberFirstName(String LastName, ExtentTest test) throws Exception {
		// Verify whether control is in Author Search page
		Assert.assertEquals(
				pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_WOS_AUTHOR_SEARCH_TITLE_XPATH).getText(),
				wos_title, "Control is not in WOS Author Search page");
		test.log(LogStatus.INFO, "Control is in WOS Author Search page");

		test.log(LogStatus.INFO, "Entering author Last name... ");
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH);
		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH).clear();
		pf.getSearchAuthClusterPage(ob).enterAuthorLastName(LastName, test);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH);
		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH).clear();
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH, "10");
		List<WebElement> ele = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.WAT_AUTHOR_NAME_NOT_FOUND_ERROR_XPATH);
		test.log(LogStatus.INFO, "Trying to click find button... ");

		if (ele.size() != 0 && !pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH).isEnabled()) {
			test.log(LogStatus.PASS, "Unable to search for author cluster with numbers in Last name");
			pf.getBrowserActionInstance(ob).closeBrowser();
		} else {
			test.log(LogStatus.FAIL,
					"User is able to search for Author cluster with numbers in Last name OR Find button is enabled for number search");
			throw new Exception(
					"User is able to search for Author cluster with numbers in Last name OR Find button is enabled for number search");

		}
	}

	public void searchAuthorClusterAlphanuemricFirstName(String LastName, ExtentTest test) throws Exception {
		// Verify whether control is in Author Search page
		Assert.assertEquals(
				pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_WOS_AUTHOR_SEARCH_TITLE_XPATH).getText(),
				wos_title, "Control is not in WOS Author Search page");
		test.log(LogStatus.INFO, "Control is in WOS Author Search page");

		test.log(LogStatus.INFO, "Entering author Last name... ");
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH);
		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH).clear();
		pf.getSearchAuthClusterPage(ob).enterAuthorLastName(LastName, test);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH);
		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH).clear();
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH, "Test007");
		List<WebElement> ele = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.WAT_AUTHOR_NAME_NOT_FOUND_ERROR_XPATH);
		test.log(LogStatus.INFO, "Trying to click find button... ");

		if (ele.size() != 0 && !pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_SEARCH_BY_NAME_FIND_BTN_XPATH).isEnabled()) {
			test.log(LogStatus.PASS, "Unable to search for author cluster with alphanumeric characters in First name");
		} else {
			test.log(LogStatus.FAIL,
					"User is able to search for Author cluster with alphanumeric characters in First name OR Find button is enabled for alphanumeric characters search");
			throw new Exception(
					"User is able to search for Author cluster with alphanumeric characters in First name OR Find button is enabled for alphanumeric characters search");
		}
	}

	public void typeaheadMultipleAltNameLastName(String LastName, String Lastname2, String Lastname3, ExtentTest test)
			throws Exception {
		// Verify whether control is in Author Search page
		Assert.assertEquals(
				pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_WOS_AUTHOR_SEARCH_TITLE_XPATH).getText(),
				wos_title, "Control is not in WOS Author Search page");
		test.log(LogStatus.INFO, "Control is in WOS Author Search page");

		test.log(LogStatus.INFO, "Entering author Last name... ");
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH);
		pf.getSearchAuthClusterPage(ob).enterAuthorLastName(LastName, test);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_ADD_ALT_NAME_BTN_TEXT_XPATH);

		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME2_XPATH);
		pf.getSearchAuthClusterPage(ob).enterAuthorLastName(OnePObjectMap.WAT_AUTHOR_LASTNAME2_XPATH, Lastname2, test);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_ADD_ALT_NAME_BTN_TEXT_XPATH);

		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME3_XPATH);
		pf.getSearchAuthClusterPage(ob).enterAuthorLastName(OnePObjectMap.WAT_AUTHOR_LASTNAME3_XPATH, Lastname3, test);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH);
		pf.getSearchAuthClusterPage(ob).enterAuthorLastName(LastName, test);
	}

	public void typeaheadMultipleAltNameFirstName(String LastName, String FirstName, String Lastname2, String Lastname3,
			String Firstname2, String Firstname3, ExtentTest test) throws Exception {
		// Verify whether control is in Author Search page
		Assert.assertEquals(
				pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_WOS_AUTHOR_SEARCH_TITLE_XPATH).getText(),
				wos_title, "Control is not in WOS Author Search page");
		test.log(LogStatus.INFO, "Control is in WOS Author Search page");
		test.log(LogStatus.INFO, "Entering first set of Last & First name");
		// Last and first name 1st time
		test.log(LogStatus.INFO, "Entering author Last name... ");
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH);
		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH).clear();
		pf.getSearchAuthClusterPage(ob).enterAuthorLastName(LastName, test);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH);
		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH).clear();
		pf.getSearchAuthClusterPage(ob).enterAuthorFirstName(FirstName, test);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_ADD_ALT_NAME_BTN_TEXT_XPATH);
		test.log(LogStatus.INFO, "Entering second set of Last & First name");
		// Last and first name 2nd time
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME2_XPATH);
		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_LASTNAME2_XPATH).clear();
		pf.getSearchAuthClusterPage(ob).enterAuthorLastName(OnePObjectMap.WAT_AUTHOR_LASTNAME2_XPATH, Lastname2, test);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_FIRSTNAME2_XPATH);
		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_FIRSTNAME2_XPATH).clear();
		pf.getSearchAuthClusterPage(ob).enterAuthorFirstName(OnePObjectMap.WAT_AUTHOR_FIRSTNAME2_XPATH, Firstname2,
				test);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_ADD_ALT_NAME_BTN_TEXT_XPATH);
		test.log(LogStatus.INFO, "Entering third set of Last & First name");
		// Last and first name 3rd time
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_LASTNAME3_XPATH);
		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_LASTNAME3_XPATH).clear();
		pf.getSearchAuthClusterPage(ob).enterAuthorLastName(OnePObjectMap.WAT_AUTHOR_LASTNAME3_XPATH, Lastname3, test);
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_FIRSTNAME3_XPATH);
		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_FIRSTNAME3_XPATH).clear();
		pf.getSearchAuthClusterPage(ob).enterAuthorFirstName(OnePObjectMap.WAT_AUTHOR_FIRSTNAME3_XPATH, Firstname3,
				test);
		test.log(LogStatus.INFO, "Editing first name entered in round 1");
		//// Only First name last time
		pf.getSearchAuthClusterPage(ob).enterAuthorFirstName(FirstName, test);
		test.log(LogStatus.PASS, "Firstname Typeahead displayed during multiple Alternate name search");
	}

	public void altNameLinkGreyedNoLastname(ExtentTest test) throws Exception {
		// Verify whether control is in Author Search page
		Assert.assertEquals(
				pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_WOS_AUTHOR_SEARCH_TITLE_XPATH).getText(),
				wos_title, "Control is not in WOS Author Search page");
		test.log(LogStatus.INFO, "Control is in WOS Author Search page");
		test.log(LogStatus.INFO, "Entering author name... ");

		if (!pf.getBrowserActionInstance(ob).getElement((OnePObjectMap.WAT_ADD_ALT_NAME_BTN_TEXT_XPATH)).isEnabled()) {
			test.log(LogStatus.INFO, "Add alternate name button not enabled when no data entered into Lastname field");
		} else {
			test.log(LogStatus.FAIL, "Add alternate name button is enabled when no data entered into Lastname field");
			throw new Exception("Add alternate name button is enabled when no data entered into Lastname field");
		}
		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH).clear();
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH, "U");
		if (pf.getBrowserActionInstance(ob).getElement((OnePObjectMap.WAT_ADD_ALT_NAME_BTN_TEXT_XPATH)).isEnabled()) {
			test.log(LogStatus.PASS,
					"Add alternate name button enabled after entering a single character into Lastname field");
		} else {
			test.log(LogStatus.FAIL,
					"Add alternate name button is not enabled even after entering a single character into Lastname field");
			throw new Exception(
					"Add alternate name button is not enabled even after entering a single character into Lastname field");
		}
		pf.getBrowserActionInstance(ob).closeBrowser();
	}

	public void altNameLinkGreyedOnlyFirstname(ExtentTest test) throws Exception {
		// Verify whether control is in Author Search page
		Assert.assertEquals(
				pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_WOS_AUTHOR_SEARCH_TITLE_XPATH).getText(),
				wos_title, "Control is not in WOS Author Search page");
		test.log(LogStatus.INFO, "Control is in WOS Author Search page");
		test.log(LogStatus.INFO, "Entering author name... ");

		if (!pf.getBrowserActionInstance(ob).getElement((OnePObjectMap.WAT_ADD_ALT_NAME_BTN_TEXT_XPATH)).isEnabled()) {
			test.log(LogStatus.INFO, "Add alternate name button not enabled when no data entered into Firstname field");
		} else {
			test.log(LogStatus.FAIL, "Add alternate name button is enabled when no data entered into Firstname field");
			throw new Exception("Add alternate name button is enabled when no data entered into Firstname field");
		}
		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH).clear();
		pf.getBrowserActionInstance(ob).enterFieldValue(OnePObjectMap.WAT_AUTHOR_FIRSTNAME_XPATH, "U");
		if (!pf.getBrowserActionInstance(ob).getElement((OnePObjectMap.WAT_ADD_ALT_NAME_BTN_TEXT_XPATH)).isEnabled()) {
			test.log(LogStatus.PASS,
					"Add alternate name button not enabled even after entering a single character into Firstname field");
		} else {
			test.log(LogStatus.FAIL,
					"Add alternate name button is enabled after entering a single character into Firstname field");
			throw new Exception(
					"Add alternate name button is enabled after entering a single character into Firstname field");
		}
		pf.getBrowserActionInstance(ob).closeBrowser();
	}

	public void searchAuthorClusterLastNameTypeahead(String LastName, ExtentTest test) throws Exception {
		// Verify whether control is in Author Search page
		Assert.assertEquals(
				pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_WOS_AUTHOR_SEARCH_TITLE_XPATH).getText(),
				wos_title, "Control is not in WOS Author Search page");
		test.log(LogStatus.INFO, "Control is in WOS Author Search page");
		test.log(LogStatus.INFO, "Entering author name... ");

		if (!pf.getBrowserActionInstance(ob).getElement((OnePObjectMap.WAT_ADD_ALT_NAME_BTN_TEXT_XPATH)).isEnabled()) {
			test.log(LogStatus.INFO, "Add alternate name button not enabled when no data entered into Lastname field");
		} else {
			test.log(LogStatus.FAIL, "Add alternate name button is enabled when no data entered into Lastname field");
			throw new Exception("Add alternate name button is enabled when no data entered into Lastname field");
		}
		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH).clear();
		pf.getSearchAuthClusterPage(ob).enterAuthorLastName(LastName, test);
		if (pf.getBrowserActionInstance(ob).getElement((OnePObjectMap.WAT_ADD_ALT_NAME_BTN_TEXT_XPATH)).isEnabled()) {
			test.log(LogStatus.INFO, "Add alternate name button enabled after entering a value into Lastname field");
			pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_LASTNAME_XPATH).clear();
			test.log(LogStatus.INFO, "Cleared value from Lastname field");
		} else {
			test.log(LogStatus.FAIL,
					"Add alternate name button is not enabled even after entering value into Lastname field");
			throw new Exception(
					"Add alternate name button is not enabled even after entering value into Lastname field");
		}

		if (!pf.getBrowserActionInstance(ob).getElement((OnePObjectMap.WAT_ADD_ALT_NAME_BTN_TEXT_XPATH)).isEnabled()) {
			test.log(LogStatus.PASS, "Add alternate name button not enabled after clearing Lastname field");
		} else {
			test.log(LogStatus.FAIL,
					"Add alternate name button is enabled evenafter clearing data from Lastname field");
			throw new Exception("Add alternate name button is enabled evenafter clearing data from Lastname field");
		}
		pf.getBrowserActionInstance(ob).closeBrowser();
	}
}

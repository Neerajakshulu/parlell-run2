package watpages;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import base.TestBase;
import pages.PageFactory;
import util.OnePObjectMap;

/**
 * Class for Author Record/Profile page related Operations
 * 
 * @author UC202376
 *
 */
public class AuthorRecordPage extends TestBase {
	boolean isDefaultAvatarPresent = false;
	String metaTitle;
	String metaOrg;
	String metaDept;
	String location;
	boolean hilightedTab = false;
	boolean isTabDisabled = false;
	List<WebElement> namesCount;
	boolean isAuthorFound = false;

	public AuthorRecordPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}

	/**
	 * Method for waiting Author Record page
	 * 
	 * @param test
	 * @throws Exception
	 */
	public void waitForAuthorRecordPage(ExtentTest test) throws Exception {
		pf.getBrowserWaitsInstance(ob).waitUntilText("Search Results");
		pf.getBrowserWaitsInstance(ob).waitUntilText("The following details are available for this author.",
				"This author record is algorithmically generated and may not be complete.",
				"All information is derived from the publication metadata.");
		// pf.getBrowserWaitsInstance(ob).waitUntilText("Future iterations of
		// author search will allow you to claim and","edit your own profile to
		// create a complete and accurate record of your work.");
		pf.getBrowserWaitsInstance(ob).waitUntilText("in Web of Science", "Sorted by");
		test.log(LogStatus.INFO, "User navigated to Author Record page");
	}

	/**
	 * Method for click Search Results tab
	 * 
	 * @param test
	 * @throws Exception
	 */
	public void clickSearchResultsTab(ExtentTest test) throws Exception {
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_SEARCH_RESULTS_TEXT_XPATH);
		waitForAjax(ob);
	}

	/**
	 * Method for click Search tab
	 * 
	 * @param test
	 * @throws Exception
	 */
	public void clickSearchTab(ExtentTest test) throws Exception {
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_SEARCH_LINK_XPATH);
		waitForAjax(ob);
	}

	/**
	 * Method for default avatar
	 * 
	 * @param test
	 * @throws Exception
	 */
	public void defaultAvatar() throws Exception {
		isDefaultAvatarPresent = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_DEFAULT_AVATAR_CSS).isDisplayed();
		if (!isDefaultAvatarPresent) {
			throw new Exception("No Default Avatar/Author Profile Pic is not displayed in Author Record page");
		}
	}

	/**
	 * Method for author record meta title
	 * 
	 * @param test
	 * @throws Exception
	 */
	public void authorRecordMetaTitle() throws Exception {
		metaTitle = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_RECORD_META_TITLE_CSS)
				.getText();
		String[] title = metaTitle.split(" ");
		if (!(title.length >= 2)) {
			throw new Exception("Profile Title in Author Record page should the form form First name, last name");
		}
	}

	/**
	 * Method for author record meta title
	 * 
	 * @param test
	 * @throws Exception
	 */
	public void authorRecordDept() throws Exception {
		metaDept = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_RECORD_META_DEPT_CSS).getText();
		logger.info("Sub-Orgnization-->" + metaDept);
		if (!StringUtils.isNotEmpty(metaOrg)) {
			throw new Exception("Profile metadata doesn't have any Sub-Organization/Dept");
		}
	}

	/**
	 * Method for author record meta affiliation/organization
	 * 
	 * @param test
	 * @throws Exception
	 */
	public void authorRecordMetaOrganization() throws Exception {
		metaOrg = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_RECORD_META_AFFILIATION_CSS)
				.getText();
		logger.info("Orgnization-->" + metaOrg);
		if (!StringUtils.isNotEmpty(metaOrg)) {
			throw new Exception("Profile metadata doesn't have any Organization");
		}
	}

	/**
	 * Method for author record meta city/state/country
	 * 
	 * @param test
	 * @throws Exception
	 */
	public void authorRecordMetaLocation() throws Exception {
		location = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_RECORD_META_LOCATION_CSS)
				.getText();
		logger.info("Location-->" + location);
		if (!StringUtils.isNotEmpty(location)) {
			throw new Exception("Profile metadata doesn't have any city/state/country ");
		}
	}

	public void checkForAlternativeNames() throws Exception {
		String altName = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_ALTERNATIVE_NAME_CSS).getText();
		logger.info("Actual Value : " + altName);
		Assert.assertTrue(altName.equals("Alternative names"));
		// test.log(LogStatus.PASS, "Alternative names tab displayed in author
		// record page");

	}

	/**
	 * Method for check Organization Tab displayed in Author Record page
	 * 
	 * @throws Exception
	 */
	public void checkOrganizationsTab() throws Exception {
		String orgName = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_ORGANISATION_NAME_XPATH).getText();
		logger.info("Actual Value : " + orgName);
		if (!orgName.equals("Organizations")) {
			throw new Exception("Organizations tab Not displayed in author record page");
		}
	}

	/**
	 * Method for click Organizations Tab
	 * 
	 * @throws Exception
	 */
	public void clickOrganizationsTab() throws Exception {
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_ORGANISATION_NAME_XPATH);
		waitForAjax(ob);
		hilightedTab = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_ALTERNATIVE_NAME_TAB_HILIGHTED_CSS).isDisplayed();
		if (!hilightedTab) {
			throw new Exception("Organizations tab is not hilighted");
		}
	}

	public void clickAlternativeNamesTab() throws Exception {
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_ALTERNATIVE_NAME_CSS);
		waitForAjax(ob);
		hilightedTab = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_ALTERNATIVE_NAME_TAB_HILIGHTED_CSS).isDisplayed();
		if (!hilightedTab) {
			throw new Exception("Alternative names tab is not hilighted");
		}

	}

	public void checkAltNamesOrOrgNamesCount(ExtentTest test, String tabName) throws Exception {
		namesCount = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_ALTERNATIVE_NAME_COUNT_CSS);
		if (namesCount.size() <= 5) {
			test.log(LogStatus.INFO, "5 or <5 " + tabName + " are displayed");
		} else {
			throw new Exception("No " + tabName + "are displayed");
		}
	}

	public void checkForAuthorNames(String lastName, ExtentTest test) {
		if (namesCount.size() <= 5) {
			String[] names = lastName.split(" ");
			for (int i = 0; i < namesCount.size(); i++) {
				String name = namesCount.get(i).getText();
				if (name.contains(names[0]) || name.contains(names[1])) {
					test.log(LogStatus.INFO, "Alternative names matching with last name");
				}
			}
		}
	}

	/**
	 * Method for check Organizations Tab status active or inactive
	 * 
	 * @throws Exception
	 */
	public void checkOrganizationsTabStatus() throws Exception {
		String orgName = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_ORGANISATION_TAB_STATUS_XPATH).getText();
		String tabStatus = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_ORGANISATION_TAB_STATUS_XPATH).getAttribute("class");
		isTabDisabled = tabStatus.contains("disabled");
		logger.info("Org Tab Status : " + isTabDisabled);
		logger.info("Actual Value : " + orgName);
		if (!(orgName.equals("Organizations") && isTabDisabled)) {
			throw new Exception("Organizations names tab displayed in active mode");
		}
	}

	/**
	 * Method for check Alternativenames Tab status active or inactive
	 * 
	 * @throws Exception
	 */
	public void checkAlternativenamesTabStatus() throws Exception {
		String orgName = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_ALTERNATIVE_NAME_TAB_STATUS_XPATH).getText();
		String tabStatus = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_ALTERNATIVE_NAME_TAB_STATUS_XPATH)
				.getAttribute("class");
		isTabDisabled = tabStatus.contains("disabled");
		logger.info("Alternative names Tab Status : " + isTabDisabled);
		logger.info("Actual Value : " + orgName);
		if (!(orgName.equals("Organizations") && isTabDisabled)) {
			throw new Exception("Alternative names tab displayed in active mode");
		}
	}

	/**
	 * Method for check Metrics Tab displayed in Author Record page
	 * 
	 * @throws Exception
	 */
	public void checkMetricsTab() throws Exception {
		String metrics = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_METRICS_XPATH)
				.getText();
		logger.info("Actual Value : " + metrics);
		if (!metrics.equals("Metrics")) {
			throw new Exception("Metrics tab Not displayed in author record page");
		}
	}

	/**
	 * Method for check Metrics Tab status active or inactive
	 * 
	 * @throws Exception
	 */
	public void checkMetricsTabStatus() throws Exception {
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_METRICS_XPATH);
		waitForAjax(ob);
		hilightedTab = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_ALTERNATIVE_NAME_TAB_HILIGHTED_CSS).isDisplayed();
		if (!hilightedTab) {
			throw new Exception("Metrics tab is not getting highlighted");
		}
	}

	/**
	 * Method for check Metrics Tab Total Times cited and H-Index label text and
	 * count
	 * 
	 * @throws Exception
	 */
	public void checkMetricsTabItems(String totalTimesCited, String hIndex, ExtentTest test) throws Exception {

		List<WebElement> timesCited = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_METRICS_TAB_TIMESCITED_XPATH);
		logger.info("metrics tab 1" + timesCited.get(0).getText() + "-->" + timesCited.get(1).getText().isEmpty());
		if (!(timesCited.get(0).getText().equalsIgnoreCase(totalTimesCited)
				&& !(timesCited.get(1).getText().isEmpty()))) {
			test.log(LogStatus.FAIL, "TOTAL TIMES CITED label and count not displayed in under Metrics Tab");
		}

		List<WebElement> index = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_METRICS_TAB_HINDEX_XPATH);
		logger.info("metrics tab 2" + index.get(0).getText() + "-->" + index.get(1).getText().isEmpty());
		if (!(index.get(0).getText().equalsIgnoreCase(hIndex) && !(index.get(1).getText().isEmpty()))) {
			test.log(LogStatus.FAIL, "H-INDEX label and count not displayed in under Metrics Tab");
			throw new Exception("H-INDEX label and count not displayed in under Metrics Tab");
		}
	}

	/**
	 * Entering curation mode and verify
	 * 
	 * @param LastName
	 * @author UC225218
	 * @throws Exception
	 * 
	 */
	public void enterCurationMode(ExtentTest test) {
		try {
			pf.getBrowserWaitsInstance(ob)
					.waitForAllElementsToBePresent(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_AUTHOR_PROFILE_ICON_XPATH);
			if (pf.getBrowserActionInstance(ob)
					.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_AUTHOR_PROFILE_ICON_XPATH).isDisplayed()) {
				pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_SUGGEST_UPDATE_BTN_XPATH);
				test.log(LogStatus.INFO, "Entering curation mode");
				Assert.assertTrue(pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_CANCEL_UPDATE_BTN_XPATH)
						.isDisplayed());
				test.log(LogStatus.PASS, "Entered curation mode, Checking for confirmation");
				scrollElementIntoView(ob,
						pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_PUBLICATION_REMOVE_CHKBOX_XPATH));
				Assert.assertTrue(pf.getBrowserActionInstance(ob)
						.getElement(OnePObjectMap.WAT_PUBLICATION_REMOVE_CHKBOX_XPATH).isDisplayed());
				test.log(LogStatus.PASS, "Entered curation mode and confirmed successfully");
			}
		} catch (Exception e) {
			test.log(LogStatus.FAIL, "Didnt entered curation mode");
			e.printStackTrace();
		}
	}

	/**
	 * Method for to check ORCiD Functionality
	 * 
	 * @throws Exception
	 * @throws InterruptedException
	 */
	@SuppressWarnings("static-access")
	public void orcidFunctionality(ExtentTest test) throws Exception, InterruptedException {
		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_CARD_1_XPATH).click();
		pf.getBrowserWaitsInstance(ob).waitTime(3);
		String ORCID = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_ORCID_LINK_XPATH).getText();
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_ORCID_LINK_XPATH);
		pf.getBrowserActionInstance(ob).switchToNewWindow(ob);
		Assert.assertTrue(
				pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_ORCID_ID).getText().contains(ORCID),
				"User not taken to the ORCID page of the Author");
		test.log(LogStatus.PASS, "User is taken to the ORCID page of the Author successfully");
	}

	/**
	 * Method for to check RID Functionality
	 * 
	 * @throws Exception
	 * @throws InterruptedException
	 */
	@SuppressWarnings("static-access")
	public void researcherIdFunctionality(ExtentTest test) throws Exception, InterruptedException {
		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_CARD_1_XPATH).click();
		pf.getBrowserWaitsInstance(ob).waitTime(3);
		String RESEARCHERID = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_RESEARCHERID_LINK_XPATH)
				.getText();
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_RESEARCHERID_LINK_XPATH);
		pf.getBrowserActionInstance(ob).switchToNewWindow(ob);
		Assert.assertTrue(pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_RESEARCHER_XPATH).getText()
				.contains(RESEARCHERID), "User not taken to the RESEARCHER ID page of the Author");
		test.log(LogStatus.PASS, "User is taken to the RESEARCHER ID page of the Author successfully");
	}

	/**
	 * Method for to check ORCiD Functionality
	 * 
	 * @throws Exception
	 * @throws InterruptedException
	 */
	@SuppressWarnings("static-access")
	public void orcid(ExtentTest test) throws Exception, InterruptedException {
		String ORCID = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_ORCID_LINK_XPATH).getText();
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_ORCID_LINK_XPATH);
		pf.getBrowserWaitsInstance(ob).waitTime(3);
		pf.getBrowserActionInstance(ob).switchToNewWindow(ob);
		Assert.assertTrue(
				pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_ORCID_ID).getText().contains(ORCID),
				"User not taken to the ORCID page of the Author");
		test.log(LogStatus.PASS, "User is taken to the ORCID page of the Author successfully");
	}

	/**
	 * Method for to check RID Functionality
	 * 
	 * @throws Exception
	 * @throws InterruptedException
	 */
	@SuppressWarnings("static-access")
	public void rid(ExtentTest test) throws Exception {
		String RESEARCHERID = pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_RESEARCHERID_LINK_XPATH)
				.getText();
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.WAT_RESEARCHERID_LINK_XPATH);
		pf.getBrowserWaitsInstance(ob).waitTime(3);
		pf.getBrowserActionInstance(ob).switchToNewWindow(ob);
		Assert.assertTrue(pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_RESEARCHER_XPATH).getText()
				.contains(RESEARCHERID), "User not taken to the RESEARCHER ID page of the Author");
		test.log(LogStatus.PASS, "User is taken to the RESEARCHER ID page of the Author successfully");
	}

	/**
	 * Method for recommend papers last name
	 * 
	 * @param test
	 * @throws Exception
	 */
	public void recommendPapersLastName(String authorLastname) throws Exception {
		List<WebElement> recommendPapers = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.WAT_AUTHOR_RECORD_RECOMMEND_PAPER_AUTHORS_XPATH);
		HashMap<Integer, Boolean> hm = new HashMap<Integer, Boolean>();
		int count = 0;
		for (WebElement paper : recommendPapers) {
			if (!paper.findElement(By.tagName("a")).getAttribute("class").endsWith("ng-hide")) {
				paper.findElement(By.tagName("a")).click();
				waitForAjax(ob);
			}
			List<WebElement> authors = paper.findElements(By.tagName("div"));
			for (WebElement author : authors) {
				logger.info("recommend paper Author Name-->" + author.getText());
				if (StringUtils.contains(author.getText(), authorLastname)) {
					isAuthorFound = true;
					hm.put(++count, isAuthorFound);
				}
			}
		}
		logger.info("last name recommend paper?" + hm.get(1) + "-->" + hm.get(2) + "--->" + hm.get(3));
		if (!(hm.get(0) && hm.get(1) && hm.get(2))) {
			throw new Exception("Recommend paper last name not matched author record last name");
		}

	}

	/**
	 * Method for recommend papers authors name should not match author record first name or intials
	 * @param test
	 * @throws Exception
	 */
	public void recommendPapersAuthorName() throws Exception{
		
		metaTitle=pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_RECORD_META_TITLE_CSS).getText();
		String[] title=metaTitle.split(" ");
		logger.info("title 1-->"+title[0]);
		logger.info("title 11-->"+title[1]);
		List<WebElement> recommendPapers = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.WAT_AUTHOR_RECORD_RECOMMEND_PAPER_AUTHORS_XPATH);
		
		for(WebElement paper:recommendPapers){
			if(!paper.findElement(By.tagName("a")).getAttribute("class").endsWith("ng-hide")){
				paper.findElement(By.tagName("a")).click();
				waitForAjax(ob);}
				List<WebElement> authors=paper.findElements(By.tagName("div"));
				for(WebElement author:authors){
					logger.info("recommend paper Author Name-->"+author.getText());
					if(StringUtils.contains(author.getText(), title[1])){
						throw new Exception("recommend paper author name should not have author record author first name or initilas");
					}
				}
		}
		
		
    }
	
	/**
	 * Method for recommend papers authors name should not match author record first name or intials
	 * @param test
	 * @throws Exception
	 */
	public void recommendPapersLastnameCount() throws Exception{
		
		metaTitle=pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_RECORD_META_TITLE_CSS).getText();
		String[] title=metaTitle.split(" ");
		logger.info("title 1-->"+title[0]);
		logger.info("title 11-->"+title[1]);
		List<WebElement> recommendPapers = pf.getBrowserActionInstance(ob)
				.getElements(OnePObjectMap.WAT_AUTHOR_RECORD_RECOMMEND_PAPER_AUTHORS_XPATH);
		
		for(WebElement paper:recommendPapers){
			int count=0;
			if(!paper.findElement(By.tagName("a")).getAttribute("class").endsWith("ng-hide")){
				paper.findElement(By.tagName("a")).click();
				waitForAjax(ob);}
				List<WebElement> authors=paper.findElements(By.tagName("div"));
				for(WebElement author:authors){
					logger.info("recommend paper Author Name-->"+author.getText());
					if(StringUtils.contains(author.getText(), title[0])){
						++count;
					}
				}
				logger.info("count match-->"+count);
				if(count==0){
					throw new Exception("Recommended papers atleast one last name not matched with author record last name variants");
				}
		}
		
		
    }
	
	/**
	 * Method for check Suggest update button is displayed in Author Record page
	 * 
	 * @throws Exception
	 */
	public void checkSuggestUpdateBtn() throws Exception {
		String BtnName = pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_SUGGEST_UPDATE_BTN_XPATH).getText();
		logger.info("Actual Value : " + BtnName);
		if (!BtnName.equals("Suggest updates")) {
			throw new Exception("Suggest updates button is not displayed in author record page");
		}
	}

	/**
	 * Method to verify elements to be present in curation mode
	 * 
	 * @param test
	 * @throws Exception
	 */
	public void verifyInCurationModeElements(ExtentTest test) throws Exception {
		Assert.assertTrue(pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_PUBLICATION_REMOVE_CHKBOX_XPATH)
				.isDisplayed(), "Remove Publication button is not displayed");
		test.log(LogStatus.INFO, "Remove Publication button is displayed for each publication");
		Assert.assertTrue(pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_SUBMIT_UPDATE_BTN_XPATH).isDisplayed(),
				"Submit Update button is not displayed");
		test.log(LogStatus.INFO, "Submit Update button is displayed");
		Assert.assertTrue(pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_CANCEL_UPDATE_BTN_XPATH).isDisplayed(),
				"Cancel button is not displayed");
		test.log(LogStatus.INFO, "Cancel button is displayed");
		Assert.assertTrue(pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_IN_CURATION_FILTER_RESET_LINK_XPATH).isDisplayed(),
				"Reset link is not displayed");
		test.log(LogStatus.INFO, "Reset link is displayed");
		Assert.assertTrue(pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_IN_CURATION_FILTER_AUTHOR_NAME_XPATH).isDisplayed(),
				"Author Nmae filter is not displayed");
		test.log(LogStatus.INFO, "Author Name filter is displayed");
		Assert.assertTrue(pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_IN_CURATION_FILTER_JOURNAL_NAME_XPATH).isDisplayed(),
				"Journal Filter is not displayed");
		test.log(LogStatus.INFO, "Journal Filter is displayed");
		Assert.assertTrue(pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_SUGGEST_UPDATE_BTN_XPATH).isDisplayed(),
				"Suggest Update button is displayed during editing.");
		test.log(LogStatus.INFO, "Suggest Update button is not displayed during editing.");
	}

	/**
	 * Method to enter curation mode through Suggest Update button
	 * 
	 * @throws Exception
	 *
	 */
	public void getIntoCurationThruSuggestUpdateBtn(ExtentTest test) throws Exception {
		pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_SUGGEST_UPDATE_BTN_XPATH)
				.click();
		test.log(LogStatus.PASS, "Entered Curation mode through the Suggest Update button");
		Assert.assertTrue(!pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_SUBMIT_UPDATE_BTN_XPATH).isEnabled(),
				"Submit Update button is enabled (Blue colour) before any kind of editing.");
		test.log(LogStatus.INFO, "Submit Update button is not Enabled before any kind of editing.");
		verifyInCurationModeElements(test);
	}

	/**
	 * Method to enter curation mode through accept recommendations button
	 * 
	 * @throws Exception
	 * 
	 */
	public void getIntoCurationThruAcceptRecommendation(ExtentTest test) throws Exception {
		pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_ACCEPT_FIRST_RECOMMENDATION_XPATH).click();
		test.log(LogStatus.PASS, "Entered Curation mode through accept Recommendation");
		Assert.assertTrue(pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_SUBMIT_UPDATE_BTN_XPATH).isEnabled(),
				"Submit Update button is not enabled (Blue colour)");
		test.log(LogStatus.INFO, "Submit Update button is Enabled");
		verifyInCurationModeElements(test);
	}

	/**
	 * Method to enter curation mode through reject recommendations button
	 * 
	 * @throws Exception
	 * 
	 */
	public void getIntoCurationThruRejectRecommendation(ExtentTest test) throws Exception {
		pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_REJECT_FIRST_RECOMMENDATION_XPATH).click();
		test.log(LogStatus.PASS, "Entered Curation mode through reject Recommendation");
		Assert.assertTrue(pf.getBrowserActionInstance(ob)
				.getElement(OnePObjectMap.WAT_AUTHOR_RECORD_PAGE_SUBMIT_UPDATE_BTN_XPATH).isEnabled(),
				"Submit Update button is not enabled (Blue colour)");
		test.log(LogStatus.INFO, "Submit Update button is Enabled");
		verifyInCurationModeElements(test);
	}

	/**
	 * Method to enter curation mode for single author
	 * 
	 * @throws Exception
	 * @throws InterruptedException
	 */
	public void getintoCuration(ExtentTest test, String CurarionVia) throws Exception {
		checkSuggestUpdateBtn();
		test.log(LogStatus.PASS, "Suggest updates button is displayed in author record page");
		if (pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.WAT_PUBLICATION_REMOVE_CHKBOX_XPATH)
				.isDisplayed()) {
			throw new Exception("Remove Publication button displayed even before getting into curation mode");
		}
		switch (CurarionVia) {
		case "SuggestUpdate":
			getIntoCurationThruSuggestUpdateBtn(test);
			break;
		case "AcceptRecommendation":
			getIntoCurationThruAcceptRecommendation(test);
			break;
		case "RejectRecommendation":
			getIntoCurationThruRejectRecommendation(test);
			break;
		default:
			test.log(LogStatus.WARNING, "None of the curation mode executed");
		}
	}

}

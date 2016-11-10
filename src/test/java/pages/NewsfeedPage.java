package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import base.TestBase;
import util.OnePObjectMap;

public class NewsfeedPage extends TestBase{

	public NewsfeedPage(WebDriver ob) {
		this.ob = ob;
		pf = new PageFactory();
	}
	
	public void addFirstPostToGroup(List<String> groupTitle) throws Exception {
		WebElement addToGroup=pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.NEWSFEED_POST_CARD_SECTION_CSS)
		.findElement(By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_DOCUMENT_ADD_TO_GROUP_CSS.toString()));
		
		pf.getWatchlistPageInstance(ob).addDocumentToMultipleGroups(groupTitle, addToGroup);
	}
	
	public void addFirstPostToWatclist(String watchListName) throws Exception {
		WebElement watchbutton=pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.NEWSFEED_POST_CARD_SECTION_CSS)
		.findElement(By.cssSelector(OnePObjectMap.NEWSFEED_POST_CARD_POST_TITLE_ADD_TO_WATCHLIST_CSS.toString()));
		watchOrUnwatchItemToAParticularWatchlist(watchListName, watchbutton);
	}
	
	public void addFirstArticleToWatclist(String watchListName) throws Exception {
		WebElement watchbutton=pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.NEWSFEED_ARTICLE_CARD_SECTION_CSS)
		.findElement(By.cssSelector(OnePObjectMap.NEWSFEED_POST_CARD_POST_TITLE_ADD_TO_WATCHLIST_CSS.toString()));
		watchOrUnwatchItemToAParticularWatchlist(watchListName, watchbutton);
	}
	
	public void addFirstArticleToGroup(List<String> groupTitle) throws Exception {
		WebElement addToGroup=pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.NEWSFEED_ARTICLE_CARD_SECTION_CSS)
		.findElement(By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_DOCUMENT_ADD_TO_GROUP_CSS.toString()));
		
		pf.getWatchlistPageInstance(ob).addDocumentToMultipleGroups(groupTitle, addToGroup);
	}
	
	public String getPostTitle() throws Exception {
		String postCategeory=	pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.NEWSFEED_POST_CARD_SECTION_CSS)
					.findElement(By.cssSelector(OnePObjectMap.NEWSFEED_POST_CARD_SECTION_TITLE_CSS.toString())).getText();
		logger.info("Post Categeory-->"+postCategeory);
		String postTitle=	pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.NEWSFEED_POST_CARD_SECTION_CSS)
				.findElement(By.cssSelector(OnePObjectMap.NEWSFEED_POST_CARD_POST_TITLE_CSS.toString())).getText();
		logger.info("Post Title-->"+postTitle);	
		
		return postTitle;
	}
	
	public String getArticleTitle() throws Exception {
		String postCategeory=	pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.NEWSFEED_ARTICLE_CARD_SECTION_CSS)
					.findElement(By.cssSelector(OnePObjectMap.NEWSFEED_POST_CARD_SECTION_TITLE_CSS.toString())).getText();
		logger.info("Article Categeory-->"+postCategeory);
		String postTitle=	pf.getBrowserActionInstance(ob).getElement(OnePObjectMap.NEWSFEED_ARTICLE_CARD_SECTION_CSS)
				.findElement(By.cssSelector(OnePObjectMap.NEWSFEED_POST_CARD_POST_TITLE_CSS.toString())).getText();
		logger.info("Article Title-->"+postTitle);	
		
		return postTitle;
	}
	
	public void clickNewsfeedLink() throws Exception {
		pf.getBrowserActionInstance(ob).click(OnePObjectMap.HOME_PROJECT_NEON_HEADER_NEWSFEED_CSS);
		pf.getBrowserWaitsInstance(ob).waitUntilText("Trending on Neon","Posts");
		pf.getBrowserWaitsInstance(ob).waitUntilElementIsClickable(OnePObjectMap.SEARCH_RESULTS_PAGE_DOCUMENT_ADD_TO_GROUP_CSS);
	}
	
	public void addPatentToGroup(List<String> groupTitle,String docTitle) throws Exception {
		List<WebElement> cardSections=	pf.getBrowserActionInstance(ob).getElements(OnePObjectMap.NEWSFEED_COMMENT_CARD_SECTION_CSS);
		for(WebElement cardSection:cardSections) {
			String cardCategeory=cardSection.findElement(By.cssSelector(OnePObjectMap.NEWSFEED_POST_CARD_SECTION_TITLE_CSS.toString())).getText();
			if(cardCategeory.contains("New Comment")) {
				String cardTitle=cardSection.findElement(By.cssSelector(OnePObjectMap.NEWSFEED_POST_CARD_POST_TITLE_CSS.toString())).getText();
				if(cardTitle.equalsIgnoreCase(docTitle)) {
					logger.info("cardTitle-->"+cardTitle);
					WebElement addToGroup=cardSection.findElement(By.cssSelector(OnePObjectMap.SEARCH_RESULTS_PAGE_DOCUMENT_ADD_TO_GROUP_CSS.toString()));
					pf.getWatchlistPageInstance(ob).addDocumentToMultipleGroups(groupTitle, addToGroup);
					break;
				}
			}
		}
	}
	
}
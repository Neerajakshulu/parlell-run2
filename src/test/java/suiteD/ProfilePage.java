package suiteD;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import base.TestBase;
import util.BrowserAction;
import util.OnePObjectMap;

public class ProfilePage  extends TestBase {
	
	
	/**
	 * Method for Validate Profile Search with last name
	 * @param lastName
	 * @throws Exception
	 */
	public static void validateProfileLastName(String lastName) throws Exception {
		List<WebElement> profilesLastname=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_NAME_CSS);
		if(profilesLastname.size()>0){
			for(WebElement profileLastname:profilesLastname) {
				if(!StringUtils.containsIgnoreCase(profileLastname.getText(), lastName)) {
					throw new Exception("Profile serach not verifying with Last Name");
				}
			}
		}
		else
			System.out.println("Profile Search Results are not available with \t"+lastName+ "\t last Name");
		
	}
	
	/**
	 * Method for Validate Profile Search with Role/Primary Institution/Country
	 * @param metaData
	 * @throws Exception
	 */
	public static void validateProfileMetaData(String metaData) throws Exception {
		List<WebElement> profilesLastname=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_PROFILE_NAME_CSS);
		if(profilesLastname.size()>0){
			List<WebElement> profilesMetaData=BrowserAction.getElements(OnePObjectMap.HOME_PROJECT_NEON_ARTICLE_PROFILE_METADATA_CSS);
			System.out.println("Profile metadata--->"+profilesMetaData.size());
			for(WebElement profileMetaData:profilesMetaData) {
				System.out.println("Meta Data-->"+profileMetaData.getText());
				if(!StringUtils.containsIgnoreCase(profileMetaData.getText(), metaData)) {
					throw new Exception("Profile serach not verifying with Role/Primary Institution/Country	");
				}
			}
		}
		else
			System.out.println("No Profile Search Results are not available with \t"+metaData+ "\t role/Primary Institution/Country");
	}
	
	/**
	 * Method for Click People after searching an profile
	 * @throws Exception, When People are not present/Disabled
	 */
	public static void clickPeople() throws Exception {
			ob.findElement(By.xpath("//a[contains(text(), 'People')]")).click();
			Thread.sleep(4000);
	}

}

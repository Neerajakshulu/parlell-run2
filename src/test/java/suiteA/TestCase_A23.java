package suiteA;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.SkipException;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import util.ErrorUtil;
import util.TestUtil;
import base.TestBase;

public class TestCase_A23 extends TestBase {
	static int status=1;

	
	
	@BeforeTest
	public void beforeTest() throws Exception{
		String var=xlRead(returnExcelPath(this.getClass().getSimpleName().charAt(9)),Integer.parseInt(this.getClass().getSimpleName().substring(10)+""),1);
		test = extent.startTest(var, "Verify change password link in the account page is working correctly.").assignCategory("Suite A");

	}
	
	@Test
	public void testCaseA23() throws Exception{
		boolean suiteRunmode=TestUtil.isSuiteRunnable(suiteXls, "A Suite");
		boolean testRunmode=TestUtil.isTestCaseRunnable(suiteAxls,this.getClass().getSimpleName());
		boolean master_condition=suiteRunmode && testRunmode;
		
		if(!master_condition){
			
			status=3;//excel
			test.log(LogStatus.SKIP, "Skipping test case "+this.getClass().getSimpleName()+" as the run mode is set to NO");
			throw new SkipException("Skipping Test Case"+this.getClass().getSimpleName()+" as runmode set to NO");//reports
		
		}
		
		test.log(LogStatus.INFO,this.getClass().getSimpleName()+" execution starts--->");
		
		try{
			String password="Transaction@2";
			String first_name="disco";
			String last_name="dancer";
			
//			1)Create a new user
//			2)Login with new user and logout
				openBrowser();
				try{
					maximizeWindow();
					}
					catch(Throwable t){
						
						System.out.println("maximize() command not supported in Selendroid");
					}
				clearCookies();
				
				ob.get("https://www.guerrillamail.com");
				String email=ob.findElement(By.id(OR.getProperty("email_textBox"))).getText();
//				ob.navigate().to(CONFIG.getProperty("testSiteName"));
				ob.navigate().to(host);
//			
				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_login_button")), 30);
				ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click();
//			
				waitForElementTobeVisible(ob, By.linkText(OR.getProperty("TR_register_link")), 30);
				
				
				ob.findElement(By.linkText(OR.getProperty("TR_register_link"))).click();
//		
				waitForElementTobeVisible(ob, By.id(OR.getProperty("reg_email_textBox")), 30);
				ob.findElement(By.id(OR.getProperty("reg_email_textBox"))).sendKeys(email);
				ob.findElement(By.id(OR.getProperty("reg_firstName_textBox"))).sendKeys(first_name);
				ob.findElement(By.id(OR.getProperty("reg_lastName_textBox"))).sendKeys(last_name);
				ob.findElement(By.id(OR.getProperty("reg_password_textBox"))).sendKeys(password);
				ob.findElement(By.id(OR.getProperty("reg_confirmPassword_textBox"))).sendKeys(password);
				ob.findElement(By.id(OR.getProperty("reg_terms_checkBox"))).click();
				ob.findElement(By.xpath(OR.getProperty("reg_register_button"))).click();
				Thread.sleep(10000);
				
				
				ob.get("https://www.guerrillamail.com");
				List<WebElement> email_list=ob.findElements(By.xpath(OR.getProperty("email_list")));
				WebElement myE=email_list.get(0);
				JavascriptExecutor executor = (JavascriptExecutor)ob;
				executor.executeScript("arguments[0].click();", myE);
//				email_list.get(0).click();
				Thread.sleep(2000);
				
				
				WebElement email_body=ob.findElement(By.xpath(OR.getProperty("email_body")));
				List<WebElement> links=email_body.findElements(By.tagName("a"));
				links.get(0).click();
//				ob.get(links.get(0).getAttribute("href"));
				Thread.sleep(8000);
				
				Set<String> myset=ob.getWindowHandles();
				Iterator<String> myIT=myset.iterator();
				ArrayList<String> al=new ArrayList<String>();
				for(int i=0;i<myset.size();i++){
					
					al.add(myIT.next());
				}
				ob.switchTo().window(al.get(1));
				Thread.sleep(5000);
				
				ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).sendKeys(email);
				ob.findElement(By.id(OR.getProperty("TR_password_textBox"))).sendKeys(password);
				ob.findElement(By.id(OR.getProperty("login_button"))).click();
				waitForElementTobeVisible(ob,By.xpath(OR.getProperty("header_label")), 30);
				ob.findElement(By.xpath(OR.getProperty("header_label"))).click();
//				
				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("account_link")), 30);
				ob.findElement(By.xpath(OR.getProperty("account_link"))).click();

				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("change_password_link")), 10);
				ob.findElement(By.xpath(OR.getProperty("change_password_link"))).click();
//				
				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("TR_newPassword_textBox")), 10);
				//changing the passowrd
				ob.findElement(By.xpath(OR.getProperty("TR_newPassword_textBox"))).sendKeys("Transaction@3");
				ob.findElement(By.xpath(OR.getProperty("TR_confirmPassword_textBox"))).sendKeys("Transaction@3");
				ob.findElement(By.xpath(OR.getProperty("TR_forgot_password_submit_button"))).click();
//				
				waitForElementTobeVisible(ob, By.xpath(OR.getProperty("reg_accountConfirmationMessage_label")),10);
				String text=ob.findElement(By.xpath(OR.getProperty("reg_accountConfirmationMessage_label"))).getText();
				String expected_text="Password updated successfully.";
				if(!StringContains(text,expected_text)){
					
					test.log(LogStatus.FAIL, "Password not changed successfully");//extent reports
					status=2;//excel
					test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_password_not_changed_successfully")));//screenshot	
					
				}
				ob.findElement(By.xpath("//a[contains(text(),'Project Neon')]")).click();
				waitForElementTobeVisible(ob,By.xpath(OR.getProperty("header_label")), 30);
				logout();
				
				//login with updated password again
				ob.findElement(By.xpath(OR.getProperty("TR_login_button"))).click();
				waitForElementTobeVisible(ob, By.id(OR.getProperty("TR_email_textBox")),10);
				ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).clear();
				ob.findElement(By.id(OR.getProperty("TR_email_textBox"))).sendKeys(email);
				ob.findElement(By.id(OR.getProperty("TR_password_textBox"))).clear();
				ob.findElement(By.id(OR.getProperty("TR_password_textBox"))).sendKeys("Transaction@3");
				ob.findElement(By.id(OR.getProperty("login_button"))).click();
				waitForElementTobeVisible(ob,By.xpath(OR.getProperty("header_label")), 30);
				logout();
		}catch(Throwable t){
			test.log(LogStatus.FAIL,"Something unexpected happened");//extent reports
			//next 3 lines to print whole testng error in report
			StringWriter errors = new StringWriter();
			t.printStackTrace(new PrintWriter(errors));
			test.log(LogStatus.INFO,errors.toString());//extent reports
			ErrorUtil.addVerificationFailure(t);//testng
			status=2;//excel
			test.log(LogStatus.INFO, "Snapshot below: " + test.addScreenCapture(captureScreenshot(this.getClass().getSimpleName()+"_something_unexpected_happened")));//screenshot
			closeBrowser();
		}
		closeBrowser();
	}
	
	
	
	@AfterTest
	public void reportTestResult(){
		extent.endTest(test);

		if(status==1)
			TestUtil.reportDataSetResult(suiteAxls, "Test Cases", TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "PASS");
		else if(status==2)
			TestUtil.reportDataSetResult(suiteAxls, "Test Cases", TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "FAIL");
		else
			TestUtil.reportDataSetResult(suiteAxls, "Test Cases", TestUtil.getRowNum(suiteAxls,this.getClass().getSimpleName()), "SKIP");

	}
	
	
}

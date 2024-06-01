package com.HF.Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.HF.BasePackage.TestBase;
import com.HF.Utilities.CommonMethods;

public class Login extends TestBase {
	
	CommonMethods commonMethods = new CommonMethods();
	
	public Login() {
		PageFactory.initElements(webDriver, this);
	}

	@FindBy(xpath = "//input[@id='id_username']")
	WebElement userName;
	@FindBy(xpath = "//input[@id='id_password']")
	WebElement passWord;
	@FindBy(xpath = "//input[@id='id_remember_me']")
	WebElement rememberMe;
	@FindBy(xpath = "//input[@value='Login']")
	WebElement login;
	@FindBy(xpath = "//h1[.='Pending Tickets']")
	WebElement pendingTickets_Title;
	
	//Launch the Portal URL
	public void LaunchUrl() throws InterruptedException {
		try {
			webDriver.get(prop.getProperty("Login_URL"));
			commonMethods.waitUntill(userName);
			Assert.assertTrue(commonMethods.validateTitle("Login - HappyFox"),"Failed to validate the Title of the Login Page");
		} catch (Exception e) {
			Util.logger.info("Exception occured - " + e);
		}
	}
	
	//Login to the Portal with credentials
	public boolean loginUser() {
		boolean check = false;
		try {
			userName.sendKeys(data.get("Username"));
			passWord.sendKeys(data.get("Password"));
			commonMethods.clickWithJavaScript(rememberMe);	
			Util.takeOneShot(webDriver, "Launch Page", new WebElement[] {userName,passWord,login}, true);
			commonMethods.clickWithJavaScript(login);
			commonMethods.wait(10);
			check = commonMethods.elementPresenceCheck(pendingTickets_Title);
			Util.takeOneShot(webDriver, "Pending Title", pendingTickets_Title, true);
		}catch (Exception e) {
			Util.logger.info("Exception occured - " + e);

		}
		return check;
	}

}

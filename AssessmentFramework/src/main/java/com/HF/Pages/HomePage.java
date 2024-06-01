package com.HF.Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.HF.BasePackage.TestBase;
import com.HF.Utilities.CommonMethods;

public class HomePage extends TestBase {
	
	CommonMethods commonMethods = new CommonMethods();
	
	public HomePage() {
		PageFactory.initElements(webDriver, this);
	}

	@FindBy(xpath = "//div[@data-test-id='staff-menu']")
	WebElement logoutMenu;
	@FindBy(xpath = "//li[@class='hf-staff-menu_item hf-mod-logout']/a")
	WebElement logoutLink;
	@FindBy(xpath = "//div[contains(text(),'You have logged out ')]")
	WebElement logoutConfirmation;
	
	//To Logout and to validate successful Logout
	public boolean userLogout() {
		boolean check = false;
		try {
			commonMethods.clickWithJavaScript(logoutMenu);
			Util.takeOneShot(webDriver, "Logout Link", logoutLink, true);
			commonMethods.clickWithJavaScript(logoutLink);
			commonMethods.wait(5);
			check = commonMethods.elementPresenceCheck(logoutConfirmation);
			if(check==true) {
				Util.logger.info("Logged out Successfully");
				Util.takeOneShot(webDriver, "logoutConfirmation", logoutConfirmation, true);
			}
			else {
				Util.logger.info("Logout was not successful");
				Util.takeOneShot(webDriver, "logoutConfirmation", logoutConfirmation, false);
			}
		}catch(Exception e ) {
			Util.logger.info("Exception caught is "+e);
		}
		return check;
	}
	

}

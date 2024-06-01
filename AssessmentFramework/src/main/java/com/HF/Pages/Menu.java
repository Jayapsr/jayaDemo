package com.HF.Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.HF.BasePackage.TestBase;
import com.HF.Utilities.CommonMethods;

public class Menu extends TestBase {
	CommonMethods commonMethods = new CommonMethods();

	public Menu() {
		PageFactory.initElements(webDriver, this);
	}

	@FindBy(xpath = "//div[@data-test-id='module-switcher_trigger']")
	WebElement tickets_Menu;
	@FindBy(xpath = "//li//a[contains(text(),'Statuses')]")
	WebElement statuses_SubMenu;
	@FindBy(xpath = "//li//a[contains(text(),'Priorities')]")
	WebElement priorities_Menu;

	//Navigate to Status Page
	public Statuses goToStatus() {
		Util.takeOneShot(webDriver, "Tickets Menu", tickets_Menu, true);
		commonMethods.mouseOver(tickets_Menu);
		commonMethods.wait(2);
		Util.takeOneShot(webDriver, "Status SubMenu", statuses_SubMenu, true);
		commonMethods.clickWithAction(statuses_SubMenu);
		commonMethods.wait(4);
		return new Statuses();
	}
	
	//Navigate to Priority Page
	public Priorities goToPriorities() {
		Util.takeOneShot(webDriver, "Tickets Menu", tickets_Menu, true);
		commonMethods.mouseOver(tickets_Menu);
		commonMethods.wait(2);
		Util.takeOneShot(webDriver, "Priorities SubMenu", priorities_Menu, true);
		commonMethods.clickWithAction(priorities_Menu);
		commonMethods.wait(4);
		return new Priorities();
	}
}

package com.HF.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.HF.BasePackage.TestBase;
import com.HF.Utilities.CommonMethods;

public class Priorities extends TestBase {

	CommonMethods commonMethods = new CommonMethods();

	public Priorities() {
		PageFactory.initElements(webDriver, this);
	}

	@FindBy(xpath = "//button[@class='hf-mod-create']")
	WebElement PlusButton;
	@FindBy(xpath = "//input[@aria-label='Priority Name']")
	WebElement PriorityTextBox;
	@FindBy(xpath = "//textarea[@aria-label='Help text for agents (1500 Characters)']")
	WebElement HelpTextAgentsTextBox;
	@FindBy(xpath = "//textarea[@aria-label='Description']")
	WebElement DescriptionTextBox;
	@FindBy(xpath = "//button[contains(text(),'Add Priority')]")
	WebElement AddPriority_TextBox;
	@FindBy(xpath = "//a[.='Priorities']")
	WebElement PrioritiesLink;
	@FindBy(xpath = "//div[@class='hf-form-field']/div[.='Priority Name']/following::div[1]")
	WebElement PrioritiesName_Delete;
	@FindBy(xpath = "//a[.='Delete']")
	WebElement DeleteButton;
	@FindBy(xpath = "//button[contains(text(),'Delete')]")
	WebElement DeleteAgain;
	@FindBy(xpath = "//header[.='You are about to delete a priority']")
	WebElement DeleteAgainHeader;
	@FindBy(xpath = "//div[@data-test-id='default-priority']")
	WebElement defaultStatus;
	@FindBy(xpath = "//div[@data-test-id='form-field-alternateEntity']/div[@class='ember-basic-dropdown']/div[1]")
	WebElement ChangePriorityDropdown;
	@FindBy(xpath = "//ul//li[@data-option-index='0']")
	WebElement LowPriority;
	
	
	//To add and check for a added Priority
	public boolean addandCheckPriorities() {
		boolean check = false;
		try {
			commonMethods.clickWithJavaScript(PlusButton);
			commonMethods.wait(2);
			commonMethods.enterValues(PriorityTextBox, data.get("PriorityName"));
			commonMethods.enterValues(DescriptionTextBox, data.get("PriorityDescription"));
			commonMethods.enterValues(HelpTextAgentsTextBox, data.get("HelpAgentsText"));
			Util.takeOneShot(webDriver, "Adding Status", new WebElement[] { PriorityTextBox, DescriptionTextBox, HelpTextAgentsTextBox }, true);
			commonMethods.clickWithJavaScript(AddPriority_TextBox);
			WebElement priorityN = webDriver.findElement(By.xpath("//table//tbody//tr//td//span[.='" + data.get("PriorityName") + "']"));
			String priorityName = priorityN.getText();
			WebElement desc = webDriver.findElement(By.xpath("//table//tbody//td//span[.='" + data.get("PriorityDescription") + "']"));
			String description = desc.getText();

			check = priorityName.equalsIgnoreCase(data.get("PriorityName")) &&
					description.equalsIgnoreCase(data.get("PriorityDescription"));
			Util.takeOneShot(webDriver, "Priorities", new WebElement[] {priorityN,desc}, check);
		} catch (Exception e) {
			System.out.println("Exception found is " + e);
		}
		return check;
	}
	
	//To make a added Priority as Default
	public boolean makeDefaultPriority() {
		boolean check = false;
		try {
			WebElement MakeDefault = webDriver.findElement(By.xpath("//table//tbody//td//span[.='"+data.get("PriorityDescription")+"']/following::td[2]/div/div[.='Make Default']"));
			JavascriptExecutor js = (JavascriptExecutor)webDriver;
	        js.executeScript("arguments[0].click();", MakeDefault);
	        commonMethods.wait(5);
			check = commonMethods.elementPresenceCheck(defaultStatus);
			Util.takeOneShot(webDriver, "Changed Priority", defaultStatus, check);
			if(check==true) {
				Util.logger.info("The created Priority is made default");
			}else {
				Util.logger.info("The created Priority is not made default");
			}
		} catch (Exception e) {
			System.out.println("Exception found is " + e);
		}
		return check;
	}

	//To press Priority Button
	public boolean pressPrioritiesButton() {
		boolean check = false;
		Util.takeOneShot(webDriver, "Priority Link", PrioritiesLink, true);
		commonMethods.clickWithJavaScript(PrioritiesLink);
		commonMethods.wait(5);
		check = commonMethods.elementPresenceCheck(PlusButton);
		return check;
	}
	
	//To delete a added Priority
	public boolean deletePriorities() {
		boolean check = false;
		try {
		commonMethods.wait(5);	
		WebElement descriptionRow = webDriver.findElement(By.xpath("//table//tbody//td//span[.='"+data.get("PriorityDescription")+"']"));
		commonMethods.clickWithJavaScript(descriptionRow);
		String name = PrioritiesName_Delete.getText();
		if(name.equalsIgnoreCase(data.get("PriorityName"))) {
			commonMethods.clickWithJavaScript(DeleteButton);
			if(commonMethods.elementPresenceCheck(ChangePriorityDropdown)) {
				commonMethods.clickWithAction(ChangePriorityDropdown);
				commonMethods.clickWithAction(LowPriority);
				Util.takeOneShot(webDriver, "Priority", ChangePriorityDropdown, true);
			}
			
			if(commonMethods.elementPresenceCheck(DeleteAgainHeader)) {
				commonMethods.clickWithJavaScript(DeleteAgain);
			}
		}
		commonMethods.wait(30);
		if(commonMethods.elementPresenceCheck(descriptionRow)==false) {
			System.out.println(commonMethods.elementPresenceCheck(descriptionRow));
			check=true;
			Util.takeOneShot(webDriver, "Deleted Priority", PlusButton, check);
		}else {
			System.out.println(commonMethods.elementPresenceCheck(descriptionRow));
			check=false;
			Util.takeOneShot(webDriver, "Deleted Priority", PlusButton, check);
		}
		} catch (Exception e) {
			System.out.println("Exception found is " + e);
		}
		return check;
	}

}

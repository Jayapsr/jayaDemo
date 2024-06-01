package com.HF.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.HF.BasePackage.TestBase;
import com.HF.Utilities.CommonMethods;

public class Statuses extends TestBase {

	CommonMethods commonMethods = new CommonMethods();

	public Statuses() {
		PageFactory.initElements(webDriver, this);
	}

	@FindBy(xpath = "//button[@class='hf-mod-create']")
	WebElement PlusButton;
	@FindBy(xpath = "//input[@aria-label='Status Name']")
	WebElement StatusName;
	@FindBy(xpath = "//div[@aria-label='Behavior']/span[1]")
	WebElement BehaviourText;
	@FindBy(xpath = "//textarea[@aria-label='Description']")
	WebElement DescriptionTextBox;
	@FindBy(xpath = "//button[contains(text(),'Add Status')]")
	WebElement AddStatus_TextBox;
	@FindBy(xpath = "//div[@class='hf-form-field']/div[.='Status Name']/following::div[1]")
	WebElement StatusName_Delete;
	@FindBy(xpath = "//a[.='Delete']")
	WebElement DeleteButton;
	@FindBy(xpath = "//div[@data-test-id='form-field-alternateEntity']/div[@class='ember-basic-dropdown']/div[1]")
	WebElement ChangeStatusDropdown;
	@FindBy(xpath = "//ul//li[@data-option-index='3']")
	WebElement ClosedStatus;

	@FindBy(xpath = "//button[contains(text(),'Delete')]")
	WebElement DeleteAgain;
	@FindBy(xpath = "//header[.='You are about to delete a status']")
	WebElement DeleteAgainHeader;
	@FindBy(xpath = "//a[.='Statuses']")
	WebElement StatusesLink;
	@FindBy(xpath = "//div[@data-test-id='default-status']")
	WebElement defaultStatus;


	//To Add and Check for added Status
	public boolean addandCheckStatus() {
		boolean check = false;
		try {
			commonMethods.clickWithJavaScript(PlusButton);
			commonMethods.wait(2);
			commonMethods.enterValues(StatusName, data.get("StatusName"));
			String bText = BehaviourText.getText();
			Util.logger.info(bText);
			if (bText.equalsIgnoreCase("Pending")) {
				commonMethods.enterValues(DescriptionTextBox, data.get("Description"));
				Util.takeOneShot(webDriver, "Adding Status", new WebElement[] { StatusName, DescriptionTextBox }, true);
				commonMethods.clickWithJavaScript(AddStatus_TextBox);
			}
			WebElement status = webDriver
					.findElement(By.xpath("//table//tbody//td//div//div[.='" + data.get("StatusName") + "']"));
			String statusName = status.getText();
			WebElement desc = webDriver
					.findElement(By.xpath("//table//tbody//td//span[.='" + data.get("Description") + "']"));
			String description = desc.getText();
			
			check = statusName.equalsIgnoreCase(data.get("StatusName"))
					&& description.equalsIgnoreCase(data.get("Description"));
			Util.takeOneShot(webDriver, "Added Status", new WebElement[] { status, desc }, check);
		} catch (Exception e) {
			Util.logger.info("Exception found is " + e);
		}
		return check;
	}

	//To make an added Status Default
	public boolean makeDefaultStatus() {
		boolean check = false;
		try {
			commonMethods.wait(5);
			WebElement MakeDefault = webDriver.findElement(By.xpath("//table//tbody//td//span[.='"
					+ data.get("Description") + "']/following::td/div/a[.='Make Default']"));
			JavascriptExecutor js = (JavascriptExecutor) webDriver;
			js.executeScript("arguments[0].click();", MakeDefault);
			commonMethods.wait(5);
			check = commonMethods.elementPresenceCheck(defaultStatus);
			Util.takeOneShot(webDriver, "Made Default Status", defaultStatus, true);
			if (check == true) {
				Util.logger.info("The created Status is made default");
			} else {
				Util.logger.info("The created Status is not made default");
			}
		} catch (Exception e) {
			Util.logger.info("Exception found is " + e);
		}
		return check;
	}

	//To press Status Button
	public boolean pressStatusesButton() {
		boolean check = false;
		try {
			Util.takeOneShot(webDriver, "Statuses Button", StatusesLink, true);
			commonMethods.clickWithJavaScript(StatusesLink);
			check = commonMethods.elementPresenceCheck(PlusButton);
		} catch (Exception e) {
			Util.logger.info("Exception found is " + e);
		}
		return check;
	}

	//To press Status Delete Button
	public boolean deleteStatus() {
		boolean check = false;
		try {
			WebElement descriptionRow = webDriver
					.findElement(By.xpath("//table//tbody//td//span[.='" + data.get("Description") + "']"));
			commonMethods.clickWithJavaScript(descriptionRow);
			String name = StatusName_Delete.getText();
			if (name.equalsIgnoreCase(data.get("StatusName"))) {
				Util.takeOneShot(webDriver, "Status Name", StatusName_Delete, true);
				commonMethods.clickWithJavaScript(DeleteButton);
				if (commonMethods.elementPresenceCheck(ChangeStatusDropdown)) {
					commonMethods.clickWithAction(ChangeStatusDropdown);
					commonMethods.wait(2);
					commonMethods.clickWithAction(ClosedStatus);
					Util.takeOneShot(webDriver, "Status", ChangeStatusDropdown, true);
				}
				if (commonMethods.elementPresenceCheck(DeleteAgainHeader)) {
					commonMethods.clickWithJavaScript(DeleteAgain);
				}
			}
			commonMethods.wait(30);
			if (commonMethods.elementPresenceCheck(descriptionRow) == false) {
				Util.takeOneShot(webDriver, "After Status Delete", PlusButton, true);
				System.out.println(commonMethods.elementPresenceCheck(descriptionRow));
				check = true;
			} else {
				System.out.println(commonMethods.elementPresenceCheck(descriptionRow));
				check = false;
			}
		} catch (Exception e) {
			Util.logger.info("Exception found is " + e);
		}
		return check;
	}

}

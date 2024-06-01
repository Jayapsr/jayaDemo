package com.HF.Pages;

import java.util.ArrayList;

import javax.security.auth.Subject;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.HF.BasePackage.TestBase;
import com.HF.Utilities.CommonMethods;

public class NewTicket extends TestBase {

	CommonMethods commonMethods = new CommonMethods();
	Login login = new Login();
	
	public NewTicket() {
		PageFactory.initElements(webDriver, this);
	}

	@FindBy(xpath = "//div[@id='subject-input-container']/input")
	WebElement SubjectTextBox;
	@FindBy(xpath = "//div[@id='cke_1_contents']/div")
	WebElement Description;
	@FindBy(xpath = "//div//button[@id='add-cc']")
	WebElement AddCCButton;
	@FindBy(xpath = "//input[@id='id_cc']")
	WebElement CC_TextBox;
	@FindBy(xpath = "//input[@id='id_name']")
	WebElement Name_Textbox;
	@FindBy(xpath = "//input[@id='id_email']")
	WebElement Email_Textbox;
	@FindBy(xpath = "//input[@id='id_phone']")
	WebElement Phone_Textbox;
	@FindBy(xpath = "//div//button[@id='submit' and contains(text(),'Create Ticket')]")
	WebElement CreateTicketButton;
	@FindBy(xpath = "//div[contains(text(),'Your ticket has been created')]")
	WebElement SuccessMessage;
	@FindBy(xpath = "//div//a[@title='Agent Portal']")
	WebElement AgentPortal_Link;
	@FindBy(xpath = "//div[@data-test-id='default-status']")
	WebElement defaultStatus;
	@FindBy(xpath = "// li//a[.='All Tickets']")
	WebElement allTickets;
	
	@FindBy(xpath = "//div//a[@data-test-id='reply-link']")
	WebElement ReplyButton;
	@FindBy(xpath = "//span[@class='hf-u-vertical-super ']")
	WebElement ChooseReplyButton;
	@FindBy(xpath = "//ul[@class='ember-power-select-options']//li[@data-option-index='0']")
	WebElement ReplyToCustomerQuery;
	@FindBy(xpath = "//button[@data-test-id='hf-add-canned-action' and contains(text(),'Apply')]")
	WebElement ApplyButton;
	@FindBy(xpath = "//ul//li//div[.='STATUS']/following::div[1]")
	WebElement StatusLabel;
	@FindBy(xpath = "//ul//li//div[.='PRIORITY']/following::div[1]")
	WebElement PriorityLabel;
	@FindBy(xpath = "//ul//li//div[.='TAGS']/following::div[1]")
	WebElement TagsLabel;
	@FindBy(xpath = "//div//button[@data-test-id='add-update-reply-button']")
	WebElement AddReplyButton;
	@FindBy(xpath = "//input[@id='id_username']")
	WebElement userName;
	
	//ChangedValues
	@FindBy(xpath = "(//ul[@class='hf-update-activities-area ember-view'])[1]/li[@class='hf-update-activity'][1]/span[1]")
	WebElement PriorityChangedFrom;
	@FindBy(xpath = "(//ul[@class='hf-update-activities-area ember-view'])[1]/li[@class='hf-update-activity'][1]/span[2]")
	WebElement PriorityChangedTo;
	@FindBy(xpath = "(//ul[@class='hf-update-activities-area ember-view'])[1]/li[@class='hf-update-activity'][2]/span[1]")
	WebElement StatusChangedFrom;
	@FindBy(xpath = "(//ul[@class='hf-update-activities-area ember-view'])[1]/li[@class='hf-update-activity'][2]/span[2]")
	WebElement StatusChangedTo;
	@FindBy(xpath = "(//ul[@class='hf-update-activities-area ember-view'])[1]/li[@class='hf-update-activity'][3]/span[1]")
	WebElement TagsChanged;
	

	//Launch Create Ticket URL
	public boolean launchNewTicketURL() {
		boolean check = false;
		try {
			webDriver.get(prop.getProperty("NewTicketURL"));
			Util.takeOneShot(webDriver, "Tickets URL Launched", SubjectTextBox, true);
			check = commonMethods.validateTitle("New Ticket  - Tenmiles - powered by HappyFox");
		} catch (Exception e) {
			System.out.println("Exception occured------>" + e);
		}
		return true;
	}
	
	//To raise New Ticket and Validate the success Message
	public boolean raiseNewTicket() {
		boolean check = false;
		try {
			SubjectTextBox.sendKeys(data.get("SubjectName"));
			commonMethods.clickWithJavaScript(Description);
			Description.sendKeys(data.get("TicketDescription"));
			commonMethods.clickWithJavaScript(AddCCButton);
			commonMethods.wait(5);
			if(commonMethods.elementPresenceCheck(CC_TextBox)) {
				CC_TextBox.sendKeys(data.get("TicketCCEmail"));
			}
			
			Name_Textbox.sendKeys(data.get("NameofTicket"));
			Email_Textbox.sendKeys(data.get("EmailID"));
			Phone_Textbox.sendKeys(data.get("PhoneNo"));
			Util.takeOneShot(webDriver, "Create Ticket Values", SubjectTextBox, true);
			commonMethods.clickWithJavaScript(CreateTicketButton);
			commonMethods.wait(5);
			Util.takeOneShot(webDriver, "Success Message", SuccessMessage, true);
			check = commonMethods.elementPresenceCheck(SuccessMessage);
		}catch(Exception e) {
			System.out.println("Exception occured--->"+e);
		}
		return check;
	}
	
	//To Press Agent Portal Link 
	public boolean pressAgentPortalLink() {
		boolean check = false;
		try {
			Util.takeOneShot(webDriver, "Agent Portal Link", AgentPortal_Link, true);
			commonMethods.clickWithJavaScript(AgentPortal_Link);
//			if(commonMethods.elementPresenceCheck(userName)) {
//				Assert.assertTrue(login.loginUser(),"Failed to Login the Agent Portal");
//			}
			check = commonMethods.elementPresenceCheck(allTickets);
			commonMethods.wait(2);
		} catch (Exception e) {
			System.out.println("Exception found is " + e);
		}
		return check;
	}
	
	//To Press All Tickets Link
	public boolean pressAllTicketsLink() {
		boolean check = false;
		try {
			Util.takeOneShot(webDriver, "All Tickets Link", allTickets, true);
			commonMethods.clickWithJavaScript(allTickets);
			commonMethods.wait(2);
			WebElement subject = webDriver.findElement(By.xpath("// div//a[@title='" + data.get("SubjectName") +"']"));
			Util.takeOneShot(webDriver, "Subject Name", subject, true);
			commonMethods.clickWithJavaScript(subject);
			check = commonMethods.elementPresenceCheck(StatusChangedFrom);
		} catch (Exception e) {
			System.out.println("Exception found is " + e);
		}
		return check;
	}
	
	//To Check for Default Status and Priority
	public boolean verifyDefaultStatusandPriority() {
		boolean check = false;
		try {
			String displayedPriority =  StatusChangedFrom.getText();
			String displayedStatus = TagsChanged.getText();
			
			check = displayedPriority.equalsIgnoreCase(data.get("PriorityName")) &&
					displayedStatus.equalsIgnoreCase(data.get("StatusName"));
			Util.takeOneShot(webDriver, "Default Priority and Status", new WebElement[] {StatusChangedFrom,TagsChanged}, check);
		} catch (Exception e) {
			System.out.println("Exception found is " + e);
		}
		return check;
	}
	
	//To Frame a reply with ReplyToCustomerQuery Canned Actions
	public boolean frameAReply() {
		boolean check = false;
		
		try {
			commonMethods.clickWithJavaScript(ReplyButton);
			commonMethods.clickWithJavaScript(ChooseReplyButton);
			commonMethods.clickWithAction(ReplyToCustomerQuery);
			commonMethods.wait(3);
			String proposedPriority = PriorityLabel.getText();
			String proposedStatus = StatusLabel.getText();
			String proposedTags = TagsLabel.getText();
			Util.takeOneShot(webDriver, "Proposed Priority ,Status and Tags", new WebElement[] {PriorityLabel,StatusLabel,TagsLabel}, true);
			
			commonMethods.clickWithJavaScript(ApplyButton);
			Util.takeOneShot(webDriver, "Add Reply Button", AddReplyButton, true);
			commonMethods.clickWithJavaScript(AddReplyButton);
			commonMethods.wait(5);
			
			String changedPriority = PriorityChangedTo.getText();
			String changedStatus = StatusChangedTo.getText();
			String changedTags = TagsChanged.getText();
			
			check = proposedPriority.equalsIgnoreCase(changedPriority) &&
					proposedStatus.equalsIgnoreCase(changedStatus) &&
					proposedTags.equalsIgnoreCase(changedTags);
			
			System.out.println(proposedPriority.equalsIgnoreCase(changedPriority));
			System.out.println(proposedStatus.equalsIgnoreCase(changedStatus));
			System.out.println(proposedTags.equalsIgnoreCase(changedTags));
			System.out.println(check);
			Util.takeOneShot(webDriver, "Changed Variables", new WebElement[] {PriorityChangedTo,StatusChangedTo,TagsChanged}, true);
		} catch (Exception e) {
			System.out.println("Exception found is " + e);
		}
		return check;
	}
	
	
	

}

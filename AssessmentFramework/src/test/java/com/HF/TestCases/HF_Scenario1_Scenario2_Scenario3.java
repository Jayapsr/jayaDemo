package com.HF.TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.HF.BasePackage.TestBase;
import com.HF.Pages.HomePage;
import com.HF.Pages.Login;
import com.HF.Pages.Menu;
import com.HF.Pages.NewTicket;
import com.HF.Pages.Priorities;
import com.HF.Pages.Statuses;

public class HF_Scenario1_Scenario2_Scenario3 extends TestBase {
	public HF_Scenario1_Scenario2_Scenario3() {
		super();
		Util.testCaseName = this.getClass().getSimpleName();
		sheetName = "data";
	}
	
	@Test
	public void HF_Scenario1_Scenario2_Scenario3() throws InterruptedException {
		Login login = new Login();
		Menu menu = new Menu();
		Statuses status = new Statuses();
		Priorities priorities = new Priorities();
		HomePage home = new HomePage();
		NewTicket newTicket = new NewTicket();
		
		login.LaunchUrl();
		Assert.assertTrue(login.loginUser(),"Failed to Login the Agent Portal");
		menu.goToStatus();
		
		//Add Status and Priority and make it Default
		Assert.assertTrue(status.addandCheckStatus(),"Failed to add and check Status");
		Assert.assertTrue(status.makeDefaultStatus(),"Failed to make Status Default");
		Assert.assertTrue(priorities.pressPrioritiesButton(),"Failed to navigate to Priority Page");
		Assert.assertTrue(priorities.addandCheckPriorities(),"Failed to add and navigate Priority");
		Assert.assertTrue(priorities.makeDefaultPriority(),"Failed to make Priority Default");
		
		//Create New Ticket and Reply to the Ticket
		Assert.assertTrue(newTicket.launchNewTicketURL(), "Failed to launch the New URL");
		Assert.assertTrue(newTicket.raiseNewTicket(), "Failed to raise a new Ticket");
		Assert.assertTrue(newTicket.pressAgentPortalLink(), "Failed to navigate to Agent Portal");
		Assert.assertTrue(newTicket.pressAllTicketsLink(), "Failed to navigate to All Tickets Link");
		Assert.assertTrue(newTicket.verifyDefaultStatusandPriority(), "Failed to navigate to All Tickets Link");
		Assert.assertTrue(newTicket.frameAReply(), "Failed to navigate to All Tickets Link");
		
		//Delete the Status
		menu.goToStatus();
		Assert.assertTrue(status.pressStatusesButton(),"Failed to navigate to Statuses Page");
		Assert.assertTrue(status.deleteStatus(),"Failed to delete a Status");
		
		//Delete Priority
		Assert.assertTrue(priorities.pressPrioritiesButton(),"Failed to navigate to Priority Page");
		Assert.assertTrue(priorities.deletePriorities(),"Failed to navigate to Delete Priority");

		//Logout
		Assert.assertTrue(home.userLogout(),"Failed to Logout from the Portal");
	}
}

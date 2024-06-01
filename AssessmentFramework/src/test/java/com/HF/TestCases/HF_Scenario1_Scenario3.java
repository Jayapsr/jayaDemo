package com.HF.TestCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.HF.BasePackage.TestBase;
import com.HF.Pages.HomePage;
import com.HF.Pages.Login;
import com.HF.Pages.Menu;
import com.HF.Pages.Priorities;
import com.HF.Pages.Statuses;

public class HF_Scenario1_Scenario3 extends TestBase {
	public HF_Scenario1_Scenario3() {
		super();
		Util.testCaseName = this.getClass().getSimpleName();
		sheetName = "data";
	}
	
	@Test
	public void HF_Scenario1_Scenario3() throws InterruptedException {
		Login login = new Login();
		Menu menu = new Menu();
		Statuses status = new Statuses();
		Priorities priorities = new Priorities();
		HomePage home = new HomePage();
		
		login.LaunchUrl();
		Assert.assertTrue(login.loginUser(),"Failed to Login the Agent Portal");
		menu.goToStatus();
		//Add Status and Priority
		Assert.assertTrue(status.addandCheckStatus(),"Failed to add and check Status");
		Assert.assertTrue(status.makeDefaultStatus(),"Failed to add and check Status");
		
		Assert.assertTrue(priorities.pressPrioritiesButton(),"Failed to navigate to Priority Page");
		Assert.assertTrue(priorities.addandCheckPriorities(),"Failed to add and navigate Priority");
		
		//Delete Status
		Assert.assertTrue(status.pressStatusesButton(),"Failed to navigate to Statuses Page");
		Assert.assertTrue(status.deleteStatus(),"Failed to delete a Status");
		
		//Delete Priority
		Assert.assertTrue(priorities.pressPrioritiesButton(),"Failed to navigate to Priority Page");
		Assert.assertTrue(priorities.deletePriorities(),"Failed to navigate to Delete Priority");
		
		//Logout
		Assert.assertTrue(home.userLogout(),"Failed to Logout from the Portal");
	}
}

package com.HF.Utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.HF.BasePackage.TestBase;

public class CommonMethods extends TestBase {
	@FindBy(xpath = "//h1")
	static WebElement title;

	public CommonMethods() {
		PageFactory.initElements(webDriver, this);
	}

	public void wait(int n) {
		try {
			Thread.sleep(n * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void waitUntill(WebElement xpath) {
		try {
			WebDriverWait driverWait = new WebDriverWait(webDriver, 20);
			driverWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(getXpath(xpath))));
		} catch (Exception e) {
		}
	}

	public String getXpath(WebElement element) {
		String[] a = element.toString().split("xpath:");
		String xpath = a[a.length - 1].substring(0, a[a.length - 1].length() - 1);
		return xpath;
	}
	
	public void clickWithJavaScript(WebElement element) {
		wait(2);
		JavascriptExecutor executor = (JavascriptExecutor) webDriver;
		executor.executeScript("arguments[0].click();", element);
		System.out.println("****Element is clicked******");
	}
	
	public void clickWithAction(WebElement element) {
		wait(1);
		Actions obj = new Actions(webDriver);
		obj.click(element);
		Action action  = obj.build();
		action.perform();
	}
	
	public boolean elementPresenceCheck(WebElement element) {
		try {
			System.out.println("*******Element presence check*********" + element.isDisplayed());
			if (element.isDisplayed()) {
				System.out.println("Element is displayed in UI");
				return true;
			} else {
				System.out.println("Element is not presenet in UI");
				return false;
			}
		}catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("No Such Element Exception Occur");
			return false;
		}catch (Exception e) {
			System.out.println("Exception occured - "+e);
			return false;
		}
	}
	
	public void mouseOver(WebElement element) {
    	Actions builder = new Actions(webDriver);
    	builder.moveToElement(element).build().perform();
    }
	
	public void enterValues(WebElement element, String value) {
		element.clear();
		wait(3);
		element.sendKeys(value);
	}
	
	public void moveToElement(WebElement element)
    {
		Actions actions = new Actions(webDriver);
		actions.moveToElement(element);
		actions.perform();
    }
	
	public boolean validateTitle(String expectedTitle) {
		boolean check = false;
		String actual = webDriver.getTitle();
		System.out.println("Title to Match ============>"+actual);
		System.out.println("Expected Title=============>"+expectedTitle);
		if(actual.equalsIgnoreCase(expectedTitle)) {
			System.out.println("The Titles Matched");
			check=true;
		}else {
			System.out.println("The Title Didnt Match");
			check=false;
		}
		return check;
	}
	
	public static String generateRandomName(int size) {
		String temp="0123456789";
		String name="";
		for(int i=0;i<size;i++) {
			name+=temp.charAt((int)(Math.random()*10));
		}
		return name;
	}
	
}

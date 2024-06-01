package com.HF.BasePackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;

import com.HF.Utilities.TestUtil;
import com.HF.Utilities.WebEventListener;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {

	public static WebDriver webDriver;
	public static Properties prop;
	public static EventFiringWebDriver e_driver;
	public static WebEventListener eventListener;
	public static String sheetName = "data";
	public static Map<String, String> data;
	public static TestUtil Util;
	public static ExtentReports extent;
	public static ExtentTest extentTest;

	public TestBase() {
		try {
			prop = new Properties();
			FileInputStream fileInputStream = new FileInputStream("Configuration\\config.properties");
			prop.load(fileInputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@BeforeSuite
	public void readDataFromFile() {
		Util.createResultFolder();
		Util.setUpLog();
		extent = new ExtentReports(Util.resultPath + File.separator + "Extent.html", true);
		Util.logger.info("Logger added ");
		data = Util.getTestData(sheetName, Util.testCaseName);
		initialization();
	}

	public static void initialization() {
		String browserName = prop.getProperty("browser");
		if (browserName.equals("chrome")) {
			ChromeOptions chromeOptions = new ChromeOptions();
			WebDriverManager.chromedriver().setup();
			webDriver = new ChromeDriver(chromeOptions);
		} else if (browserName.equals("FF")) {
			System.setProperty("webdriver.gecko.driver", "Webdrivers/geckoDriver.exe");
			webDriver = new FirefoxDriver();
		}

		e_driver = new EventFiringWebDriver(webDriver);
		eventListener = new WebEventListener();
		e_driver.register(eventListener);
		webDriver = e_driver;
		webDriver.manage().window().maximize();
		webDriver.manage().deleteAllCookies();
		webDriver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
		webDriver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT, TimeUnit.SECONDS);
	}

	@AfterMethod
	public void tearDown() {
		webDriver.quit();
	}

}

package com.HF.Utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.logging.Logger;

import com.HF.BasePackage.TestBase;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestUtil extends TestBase{

	static CommonMethods commonMethods = new CommonMethods();
	public static long PAGE_LOAD_TIMEOUT = 20;
	public static long IMPLICIT_WAIT = 20;

	public static String testDataSheetPath = "src/main/java/com/HF/TestData/TestData.xlsx";
	public static String testCaseName = "";
	public static String screenShotPath = "";
	public static String resultDirPath = "TestResults\\Executed Results";
	public static String resultPath = "";
	public static int i = 1;
	public static Logger logger = Logger.getLogger("logs");

	static Workbook book;
	static Sheet sheet;
	static JavascriptExecutor js;

	public static void takeScreenshotAtEndOfTest() throws IOException {
		File scrFile = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
	}

	public static Map<String, String> getTestData(String sheetName, String scriptName) {
		FileInputStream file = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			try {
				file = new FileInputStream(testDataSheetPath);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				book = WorkbookFactory.create(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			sheet = book.getSheet(sheetName);

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				if (sheet.getRow(i).getCell(0).toString().equalsIgnoreCase(scriptName)) {
					for (int k = 1; k < sheet.getRow(i).getLastCellNum(); k++) {
						String data = sheet.getRow(i).getCell(k).toString();
						map.put(data.substring(0, data.indexOf('=')), data.substring(data.indexOf('=') + 1));
					}
					break;
				}
			}
			if (map.size() == 0) {
				System.out.println("No data is read from test data");
			} else {
				System.out.println("Data read form test data--->" + map.toString());
			}
		} catch (Exception e) {
			System.out.println("Exception occurred in TestData read  - " + e);
		} finally {
			if (file != null) {
				try {
					file.close();
				} catch (IOException e) {
					e.printStackTrace();
					System.out.println("Exception in closing fileinputstream  - " + e);
				}
			}
		}
		return map;
	}

	public static String takeOneShot(WebDriver webDriver,String screenshotName,WebElement highlight,Boolean result) {
		String path = screenShotPath;
		// createFolder(path);
		Util.logger.info("Inside Screen Shot");
		String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
		String name = "Screenshot_"+(i++)+"_"+screenshotName+"_"+ timeStamp;
		CommonMethods commonMethods = new CommonMethods();	
			
		highlightElement(webDriver, commonMethods.getXpath(highlight), result);			
		
		fullshot(webDriver, name, path);
		
		deHighlightElement(webDriver, commonMethods.getXpath(highlight));
		
		Util.logger.info(path + "\\" + name);
		return name + ".png";
	}
	
	public static String takeOneShot(WebDriver webDriver,String screenshotName,WebElement[] highlights,Boolean result) {
		String path = screenShotPath;
		// createFolder(path);
		Util.logger.info("Inside Screen Shot");
		String timeStamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
		String name = "Screenshot_"+(i++)+"_"+screenshotName+"_"+ timeStamp;
		CommonMethods commonMethods = new CommonMethods();
		
		for(WebElement a : highlights) {	
			
			String highlight = commonMethods.getXpath(a);
			highlightElement(webDriver, highlight, result);	
		}
		
		fullshot(webDriver, name, path);
		
		for(WebElement a : highlights) {	
			
			String highlight = commonMethods.getXpath(a);
			deHighlightElement(webDriver, highlight);
		}	
				
		Util.logger.info(path + "\\" + name);
		return name + ".png";
	}

	public static void highlightElement(WebDriver webDriver, String highlight, Boolean result) {
		try {
			Util.logger.info("Highlight Value --->" + highlight);
			if (!(highlight.isEmpty())) {
				JavascriptExecutor js = (JavascriptExecutor) webDriver;

				WebElement element = webDriver.findElement(By.xpath(highlight));
				if (result) {
					js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid #0E8325;');",
							new Object[] { element });
				} else {
					js.executeScript("arguments[0].setAttribute('style', 'border: 2px solid #F31717;');",
							new Object[] { element });
				}
			}
		} catch (Exception e) {
			Util.logger.info("=====Exception Occured in Highlighting the xpath====" + e);
			e.printStackTrace();
		}
	}

	public static void fullshot(WebDriver webDriver, String name, String path) {

		try {
			Properties properties = new Properties();
			properties.load(new FileInputStream("Configuration\\config.properties"));
			String browser = prop.getProperty("browser");
			// Util.logger.info("Browser-->" + browser);
			// path is dynamically created

			if (browser.contains("firefox") || (browser.contains("ie"))) {
				Shutterbug.shootPage(webDriver, ScrollStrategy.BOTH_DIRECTIONS).withName(name).save(path);
			}

			if (browser.contains("chrome")) {
				Shutterbug.shootPage(webDriver, ScrollStrategy.WHOLE_PAGE_CHROME).withName(name).save(path);

			}
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public static void deHighlightElement(WebDriver webDriver, String highlight) {

		// D-highlight
		try {
			if (!(highlight.isEmpty())) {
				JavascriptExecutor js = (JavascriptExecutor) webDriver;
				WebElement element = webDriver.findElement(By.xpath(highlight));
				js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
			}
		} catch (Exception e) {
			Util.logger.info("====Exception Occured in Highlighting the xpath====" + e);
			e.printStackTrace();
		}
	}

	public static void setUpLog() {
		try {
			String serverDir = Util.resultPath;
			File directory = new File(serverDir);
			if (!directory.exists()) {
				directory.mkdir();
				serverDir += "\\DBLogs";
				directory = new File(serverDir);
				directory.mkdir();
			}
			String file_name = serverDir + "\\report.log";

			// Util.logger.info(this.getClass().getName());
			FileHandler fh = new FileHandler(file_name);
			Util.logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);
			Util.logger.info("Logger is inilitized \n");
		} catch (IOException e) {
			Util.logger.info("IO Exception in initializing" + e);
			System.exit(1);
		} catch (Exception e) {
			Util.logger.info("Exception in initializing" + e);
			System.exit(1);
		}

	}

	// Extend report logs
	public static void log(String data, ExtentTest extentTest) {
		Util.logger.info(data);
		extentTest.log(LogStatus.INFO, data);
	}

	public static void log(String data, ExtentTest extentTest, String status) {
		Util.logger.info(data);
		if (status.equalsIgnoreCase("info")) {
			extentTest.log(LogStatus.INFO, data);
		} else if (status.equalsIgnoreCase("pass")) {
			extentTest.log(LogStatus.PASS, data);
		} else if (status.equalsIgnoreCase("fail")) {
			extentTest.log(LogStatus.FAIL, data);
		}

	}
	
	public static void createResultFolder() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		Date date = new Date();

		String path = null, serverName = null;

		try {
			serverName = "" + InetAddress.getLocalHost();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		path = resultDirPath + "\\Execution Results_" + "_"+commonMethods.generateRandomName(4);
		Util.logger.info(path);
		File dir = new File(path);
		boolean success = dir.mkdir();
		if (success) {
			resultPath = path;
			Util.logger.info("Path created successfully--->" + resultPath);
		}
		//Screen Shot paths
		
		path=resultPath+"\\FullPage_Screenshots";
		 dir = new File(path);
		
		 success = dir.mkdir();
		if (success) {
			screenShotPath = path;
			Util.logger.info("Path created ScreenShot page path--->" + resultPath);
		}
	}

}

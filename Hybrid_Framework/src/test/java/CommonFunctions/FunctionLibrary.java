package CommonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Date1904Support;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary 
{
	public static Properties prop;
	public static WebDriver driver;
	public static WebDriver startBrowser() throws Throwable
	{
		prop = new Properties();
		prop.load(new FileInputStream("./propertyFiles/Environment.properties"));
		if(prop.getProperty("Browser").equalsIgnoreCase("Chrome")) 
		{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}else 
	    if(prop.getProperty("Browser").equalsIgnoreCase("firefox")) 
	    {
	    	driver = new FirefoxDriver();
	    }else 
	    {
	    	Reporter.log("Invalid Browser",true);
	    }
		return driver;
	}

	public static void openUrl() 
	{
		driver.get(prop.getProperty("Url"));
		
	}
	public static void waitForElement(String Locatortype,String Locatorvalue,String TestData) 
	{
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(TestData)));
		if(Locatortype.equalsIgnoreCase("xpath")) 
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locatorvalue)));
		}
		if(Locatortype.equalsIgnoreCase("id")) 
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locatorvalue)));
		}
		if(Locatortype.equalsIgnoreCase("name")) 
		{
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locatorvalue)));
		}
	}
	public static void typeAction(String Locatortype,String Locatorvalue,String TestData) 
	{
		if(Locatortype.equalsIgnoreCase("xpath")) 
		{
			driver.findElement(By.xpath(Locatorvalue)).clear();
			driver.findElement(By.xpath(Locatorvalue)).sendKeys(TestData);
		}
		if(Locatortype.equalsIgnoreCase("id")) 
		{
			driver.findElement(By.id(Locatorvalue)).clear();
			driver.findElement(By.id(Locatorvalue)).sendKeys(TestData);
		}
		if(Locatortype.equalsIgnoreCase("name")) 
		{
			driver.findElement(By.name(Locatorvalue)).clear();
			driver.findElement(By.name(Locatorvalue)).sendKeys(TestData);
		}
	}
	public static void clickAction(String Locatortype,String Locatorvalue) 
	{
		if(Locatortype.equalsIgnoreCase("xpath")) 
		{
			driver.findElement(By.xpath(Locatorvalue)).click();
		}
		if(Locatortype.equalsIgnoreCase("name")) 
		{
			driver.findElement(By.name(Locatorvalue)).click();
		}
		if(Locatortype.equalsIgnoreCase("id")) 
		{
			driver.findElement(By.id(Locatorvalue)).sendKeys(Keys.ENTER);
		}
	}
	public static void validateTittle(String Exp_Tittle) 
	{
		String Act_Tittle = driver.getTitle();
		try {
			Assert.assertEquals(Exp_Tittle, Act_Tittle,"Tittle is not Matching");
			
		} catch (AssertionError e) 
		{
			System.out.println(e.getMessage());
		}
	}
	public static void closeBrowser() 
	{
		driver.quit();
	}
	public static String generateDate() 
	{
		Date date = new Date(0);
		DateFormat df = new SimpleDateFormat("YYYY_MM_dd");
		return df.format(date);
	}
	
	public static void dropDownAction(String Locatortype, String Locatorvalue, String TestData) 
	{
		if(Locatortype.equalsIgnoreCase("name")) 
		{
			int value = Integer.parseInt(TestData);
			Select element = new Select(driver.findElement(By.name(Locatorvalue)));
			element.selectByIndex(value);
		}
		if(Locatortype.equalsIgnoreCase("xpath")) 
		{
			int value = Integer.parseInt(TestData);
			Select element = new Select(driver.findElement(By.xpath(Locatorvalue)));
			element.selectByIndex(value);
		}
		if(Locatortype.equalsIgnoreCase("id")) 
		{
			int value = Integer.parseInt(TestData);
			Select element = new Select(driver.findElement(By.id(Locatorvalue)));
			element.selectByIndex(value);
		}
	}
	public static void captureStockNumber(String Locatortype, String Locatorvalue) throws Throwable 
	{
		String stockNum = " ";
		if(Locatortype.equalsIgnoreCase("id")) 
		{
			stockNum = driver.findElement(By.id(Locatorvalue)).getAttribute("value");
		}
		if(Locatortype.equalsIgnoreCase("name")) 
		{
			stockNum = driver.findElement(By.name(Locatorvalue)).getAttribute("value");
		}
		if(Locatortype.equalsIgnoreCase("xpath")) 
		{
			stockNum = driver.findElement(By.xpath(Locatorvalue)).getAttribute("value");
		}
		FileWriter fw = new FileWriter("./CaptureData/StockNum.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(stockNum);
		bw.flush();
		bw.close();
	}
	
	public static void stockTable() throws Throwable 
	{
		FileReader fr = new FileReader("./CaptureData/StockNum.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data = br.readLine();
		if(!driver.findElement(By.xpath(prop.getProperty("Search-textbox"))).isDisplayed()) 
		{
			driver.findElement(By.xpath(prop.getProperty("Search-panel"))).click();
			driver.findElement(By.xpath(prop.getProperty("Search-textbox"))).clear();
			driver.findElement(By.xpath(prop.getProperty("Search-textbox"))).sendKeys(Exp_Data);
			driver.findElement(By.xpath(prop.getProperty("Search-button"))).click();
			String Act_Data = driver.findElement(By.xpath("//table[@class = 'table ewTable']/tbody/tr/td[8]/div/span/span")).getText();
			Reporter.log(Exp_Data+"  "+Act_Data,true);
			try 
			{
				Assert.assertEquals(Exp_Data, Act_Data,"StockNum is not Matching");
				
			} catch (AssertionError a) 
			{
				System.out.println(a.getMessage());
			}
		}
	}
	public static void captureSup(String Locatortype, String Locatorvalue) throws Throwable 
	{
		String SupplierNum = "";
		if(Locatortype.equalsIgnoreCase("xpath")) 
		{
		  SupplierNum = driver.findElement(By.xpath(Locatorvalue)).getAttribute("value");
		}
		if(Locatortype.equalsIgnoreCase("name")) 
		{
		  SupplierNum = driver.findElement(By.name(Locatorvalue)).getAttribute("value");
		}
		if(Locatortype.equalsIgnoreCase("id")) 
		{
		  SupplierNum = driver.findElement(By.id(Locatorvalue)).getAttribute("value");
		}
		FileWriter fw = new FileWriter("./CaptureData/SupplierNum.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(SupplierNum);
		bw.flush();
		bw.close();
	}
	public static void supplierTable() throws Throwable 
	{
		FileReader fr = new FileReader("./CaptureData/SupplierNum.txt");
		BufferedReader br = new BufferedReader(fr);	
		String Exp_Data = br.readLine();
		if(!driver.findElement(By.xpath(prop.getProperty("Search-textbox"))).isDisplayed()) 
		{
			driver.findElement(By.xpath(prop.getProperty("Search-panel"))).click();
			driver.findElement(By.xpath(prop.getProperty("Search-textbox"))).clear();
			driver.findElement(By.xpath(prop.getProperty("Search-textbox"))).sendKeys(Exp_Data);
			driver.findElement(By.xpath(prop.getProperty("Search-button"))).click();
			String Act_Data = driver.findElement(By.xpath("//table[@class = 'table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
			Reporter.log(Exp_Data+" "+Act_Data,true);
			try {
			Assert.assertEquals(Exp_Data, Act_Data,"SupplierNum is not Matching");
			}
			catch (AssertionError a) {
				System.out.println(a.getMessage());
			}
		}
		
	}

	public static void captureCustomer(String Locatortype,String Locatorvalue) throws Throwable  
	{
		String CustomerNum = "";
		if(Locatortype.equalsIgnoreCase("xpath")) 
		{
		  CustomerNum = driver.findElement(By.xpath(Locatorvalue)).getAttribute("value");
		}
		if(Locatortype.equalsIgnoreCase("name")) 
		{
			CustomerNum = driver.findElement(By.name(Locatorvalue)).getAttribute("value");
		}
		if(Locatortype.equalsIgnoreCase("id")) 
		{
			CustomerNum = driver.findElement(By.id(Locatorvalue)).getAttribute("value");
		}
		FileWriter fw = new FileWriter("./CaptureData/CustomerNum.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(CustomerNum);
		bw.flush();
		bw.close();
	}
	public static void customerTable() throws Throwable 
	{
		FileReader fr = new FileReader("./CaptureData/CustomerNum.txt");
		BufferedReader br = new BufferedReader(fr);	
		String Exp_Data = br.readLine();
		if(!driver.findElement(By.xpath(prop.getProperty("Search-textbox"))).isDisplayed()) 
		{
			driver.findElement(By.xpath(prop.getProperty("Search-panel"))).click();
			driver.findElement(By.xpath(prop.getProperty("Search-textbox"))).clear();
			driver.findElement(By.xpath(prop.getProperty("Search-textbox"))).sendKeys(Exp_Data);
			driver.findElement(By.xpath(prop.getProperty("Search-button"))).click();
			String Act_Data = driver.findElement(By.xpath("//table[@class = 'table ewTable']/tbody/tr/td[5]/div/span/span")).getText();
			Reporter.log(Exp_Data+" "+Act_Data,true);
			try {
			Assert.assertEquals(Exp_Data, Act_Data,"CustomerNum is not Matching");
			}
			catch (AssertionError a) {
				System.out.println(a.getMessage());
			}
		}
		
	}
}


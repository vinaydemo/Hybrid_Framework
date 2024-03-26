package driverFactory;

import org.openqa.selenium.WebDriver;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class driverScript 
{
	public static WebDriver driver;
	ExtentReports reports;
	ExtentTest logger;
	String inputpath = "./FileInput/DataEngine.xlsx";
	String outputpath = "./FileOutput/HybridResults.xlsx";
	public void startTest() throws Throwable
	{
		String Moduleststus;
		ExcelFileUtil ex = new ExcelFileUtil(inputpath);
		String Testcases = "MasterTestCases";
		for(int i=1;i<=ex.rowcount(Testcases);i++) 
		{
			if(ex.getCellData(Testcases, i, 2).equalsIgnoreCase("y")) 
			{
				String TcModule = ex.getCellData(Testcases, i, 1);
				reports = new ExtentReports("./target/results/"+TcModule+FunctionLibrary.generateDate()+".html");
				logger = reports.startTest(TcModule);
				for(int j=1;j<=ex.rowcount(TcModule);j++) 
				{
					String Description = ex.getCellData(TcModule, j, 0);
					String Object_type = ex.getCellData(TcModule, j, 1);
					String Locator_type = ex.getCellData(TcModule, j, 2);
					String Locator_Value = ex.getCellData(TcModule, j, 3);
					String Test_Data = ex.getCellData(TcModule, j, 4);
					try {
						if(Object_type.equalsIgnoreCase("startBrowser")) 
						{
							driver = FunctionLibrary.startBrowser();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("openUrl")) 
						{
							FunctionLibrary.openUrl();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("waitForElement")) 
						{
							FunctionLibrary.waitForElement(Locator_type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("typeAction")) 
						{
							FunctionLibrary.typeAction(Locator_type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("clickAction")) 
						{
							FunctionLibrary.clickAction(Locator_type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("validateTittle")) 
						{
							FunctionLibrary.validateTittle(Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("closeBrowser")) 
						{
							FunctionLibrary.closeBrowser();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("dropDownAction")) 
						{
							FunctionLibrary.dropDownAction(Locator_type, Locator_Value, Test_Data);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("captureStockNumber")) 
						{
							FunctionLibrary.captureStockNumber(Locator_type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("stockTable")) 
						{
							FunctionLibrary.stockTable();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("captureSup")) 
						{
							FunctionLibrary.captureSup(Locator_type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("supplierTable")) 
						{
							FunctionLibrary.supplierTable();
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("captureCustomer")) 
						{
							FunctionLibrary.captureCustomer(Locator_type, Locator_Value);
							logger.log(LogStatus.INFO, Description);
						}
						if(Object_type.equalsIgnoreCase("customerTable")) 
						{
							FunctionLibrary.customerTable();
							logger.log(LogStatus.INFO, Description);
						}
						ex.setCellData(TcModule, j, 5, "Pass", outputpath);
						logger.log(LogStatus.PASS, Description);
						Moduleststus = "True";
					} catch (Exception e) {
						System.out.println(e.getMessage());
						ex.setCellData(TcModule, j, 5, "Fail", outputpath);
						logger.log(LogStatus.FAIL, Description);
						Moduleststus = "False";
					}
					if(Moduleststus.equalsIgnoreCase("True")) 
					{
						ex.setCellData(Testcases, i, 3, "Pass", outputpath);
					}else 
					{
						ex.setCellData(Testcases, i, 3, "Fail", outputpath);
					}
					reports.endTest(logger);
					reports.flush();
				}
				
			}else 
			{
				ex.setCellData(Testcases, i, 3, "Blocked", outputpath);
			}
		}
	}

}

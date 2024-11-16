package driverFactory;

import org.openqa.selenium.WebDriver;
//import org.testng.Reporter;

import commonFunctions.FunctionLibrary;
import utilities.ExcelFileUtil;

public class DriverScript 

{
	public static WebDriver driver;
	String inputpath="./FileInput/DataEngine.xlsx";
	String outputpath=".//FileOutput/HybridResult.xlsx";
	String Tcsheet="MasterTestCases";
	
	public void start() throws Throwable
	{
		String Module_status="";
		String Module_new="";
		ExcelFileUtil xl=new ExcelFileUtil(inputpath);
		int rc=xl.rowCount(Tcsheet);
		System.out.println(rc);
		
		for(int i=1;i<=rc;i++)
		{
			if(xl.getcelldata(Tcsheet, i, 2).equalsIgnoreCase("Y"))
			{
				String Tcmodule=xl.getcelldata(Tcsheet, i, 1);
				for(int j=1;j<=xl.rowCount(Tcmodule);j++)
				{
					String Object_Type=xl.getcelldata(Tcmodule, j, 1);
					String Locator_Type=xl.getcelldata(Tcmodule, j, 2);
					String Locator_Value=xl.getcelldata(Tcmodule, j, 3);
					String Test_Data=xl.getcelldata(Tcmodule, j, 4);
					try 
					{
						if(Object_Type.equalsIgnoreCase("startBrowser"))
						{
							driver=FunctionLibrary.startBrowser();
						}
						if(Object_Type.equalsIgnoreCase("openUrl"))
						{
							FunctionLibrary.openUrl();
						}
						if(Object_Type.equalsIgnoreCase("WaitForElement"))
						{
							FunctionLibrary.WaitForElement(Locator_Type, Locator_Value, Test_Data);
						}
						if(Object_Type.equalsIgnoreCase("typeAction"))
						{
							FunctionLibrary.typeAction(Locator_Type, Locator_Value, Test_Data);
						}
						if(Object_Type.equalsIgnoreCase("clickAction"))
						{
							FunctionLibrary.clickAction(Locator_Type, Locator_Value);
						}
						if(Object_Type.equalsIgnoreCase("validateTitle"))
						{
							FunctionLibrary.validateTitle();
						}
						if(Object_Type.equalsIgnoreCase("closeBrowser"))
						{
							FunctionLibrary.closeBrowser();
						}
						
						if(Object_Type.equalsIgnoreCase("dropDownAction"))
						{
							FunctionLibrary.dropDownAction(Locator_Type, Locator_Value, Test_Data);
						}
						
						if(Object_Type.equalsIgnoreCase("capturestock"))
						{
							FunctionLibrary.capturestock(Locator_Type, Locator_Value);
						}
						
						if(Object_Type.equalsIgnoreCase("stocktable"))
						{
							FunctionLibrary.stocktable();
						}
						
						
						// copy and paste it
		
						
						if(Object_Type.equalsIgnoreCase("capturesup"))
						{
							FunctionLibrary.capturesup(Locator_Type, Locator_Value);
						}
						if(Object_Type.equalsIgnoreCase("supplierTable"))
						{
							FunctionLibrary.supplierTable();
						}
						if(Object_Type.equalsIgnoreCase("capturecus"))
						{
							FunctionLibrary.capturecus(Locator_Type, Locator_Value);
						}
						if(Object_Type.equalsIgnoreCase("customerTable"))
						{
							FunctionLibrary.customerTable();
						}

						
						
						
						
						
						
						
						
						
						xl.setcelldata(Tcmodule, j, 5, "PASS",outputpath);
						Module_status="true";
						
						
					} catch (Exception e) {
						System.out.println(e.getMessage());
						xl.setcelldata(Tcmodule, j, 5, "FAIL",outputpath);
						Module_new="false";
					}
				}
				if(Module_status.equalsIgnoreCase("true"))
				{
					xl.setcelldata(Tcsheet, i, 3, "Pass", outputpath);
				}
				
				if(Module_new.equalsIgnoreCase("false"))
				{
					xl.setcelldata(Tcsheet, i, 3, "Fail", outputpath);
				}
			}
			else
			{
				xl.setcelldata(Tcsheet, i, 3, "Blocked", outputpath);
			}
		}
	}
	

}

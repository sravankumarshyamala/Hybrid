package commonFunctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

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

//import com.sun.tools.javac.comp.Enter;

public class FunctionLibrary 
{
	public static WebDriver driver;
	public static Properties conpro;
	
	public static WebDriver startBrowser() throws FileNotFoundException, IOException
	{
		conpro= new Properties();
		conpro.load(new FileInputStream("./PropertyFiles/Environment.properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
		{
			driver=new FirefoxDriver();
		}
		else
		{
		 Reporter.log("browser is not avaliable",true);	
		}
		return driver;
	}
	
	public static void openUrl()
	{
          driver.get(conpro.getProperty("Url"));
	}
	
	public static void WaitForElement(String LType, String Lvalue, String MyWait)
	{
		WebDriverWait wait= new WebDriverWait(driver,Duration.ofSeconds(Integer.parseInt(MyWait)));
		if(LType.equalsIgnoreCase("xpath"))
		{
		   wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Lvalue)));
		}
		if(LType.equalsIgnoreCase("name"))
		{
		   wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Lvalue)));
		}
		if(LType.equalsIgnoreCase("id"))
		{
		   wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Lvalue)));
		}
	}
	
	public static void typeAction(String LType,String Lvalue,String Testdata)
	{
		if(LType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Lvalue)).clear();
			driver.findElement(By.xpath(Lvalue)).sendKeys(Testdata);
		}
		if(LType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Lvalue)).clear();
			driver.findElement(By.id(Lvalue)).sendKeys(Testdata);
		}
		if(LType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Lvalue)).clear();
			driver.findElement(By.name(Lvalue)).sendKeys(Testdata);
		}
	}
	
	public static void clickAction(String LType,String Lvalue)
	{
		if(LType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(Lvalue)).click();
		}
		if(LType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(Lvalue)).click();
		}
		if(LType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(Lvalue)).sendKeys(Keys.ENTER);
		}
	}
	
	public static void validateTitle()
	{
		String actual_title= driver.getTitle();
		try
		{
			Assert.assertEquals(actual_title, actual_title, "Title is not matching");
		}
		catch(Exception e)
		{
			Reporter.log(e.getMessage(),true);
		}
	}
	
	public static void closeBrowser()
	{
		driver.quit();
	}
	
	public static void dropDownAction(String LType,String Lvalue,String Testdata)
	{
		if(LType.equalsIgnoreCase("xpath"))
		{
	    int value= Integer.parseInt(Testdata);
	    Select sc= new Select(driver.findElement(By.xpath(Lvalue)));
	    sc.selectByIndex(value);
	   }
		if(LType.equalsIgnoreCase("id"))
		{
	    int value= Integer.parseInt(Testdata);
	    Select sc= new Select(driver.findElement(By.id(Lvalue)));
	    sc.selectByIndex(value);
	   }
		if(LType.equalsIgnoreCase("name"))
		{
	    int value= Integer.parseInt(Testdata);
	    Select sc= new Select(driver.findElement(By.name(Lvalue)));
	    sc.selectByIndex(value);
	   }
   }
	
	public static void capturestock(String LType,String Lvalue) throws IOException
	{
		String stock_num="";
		if(LType.equalsIgnoreCase("xpath"))
		{
			stock_num = driver.findElement(By.xpath(Lvalue)).getAttribute("value");
		}
		if(LType.equalsIgnoreCase("id"))
		{
			stock_num = driver.findElement(By.id(Lvalue)).getAttribute("value");
		}
		if(LType.equalsIgnoreCase("name"))
		{
			stock_num = driver.findElement(By.name(Lvalue)).getAttribute("value");
		}
		
		FileWriter fw= new FileWriter("./CaptureData/stocknum.txt");
		BufferedWriter bw= new BufferedWriter(fw);
		bw.write(stock_num);
		bw.flush();
		bw.close();
		
	}
	
	public static void stocktable() throws IOException, InterruptedException
	{
		FileReader fr= new FileReader("./CaptureData/stocknum.txt");
		BufferedReader br= new BufferedReader(fr);
		String exp_title= br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("Search-panel"))).click();
		    driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).clear();
		    driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).sendKeys(exp_title);
		    driver.findElement(By.xpath(conpro.getProperty("Search-button"))).click();
		    Thread.sleep(3000);
	String act_title= driver.findElement(By.xpath("//table[@class=\"table ewTable\"]/tbody/tr/td[8]/div/span/span")).getText(); 
		
		Reporter.log(act_title +"  "+ exp_title);
		try
		{
			Assert.assertEquals(exp_title, act_title, "stock number is not found");
		}
		catch(Exception e)
		{
			Reporter.log(e.getMessage());
		}
		
	}
	//method for capturing supplier number into notepad
	public static void capturesup(String Ltype,String LValue)throws Throwable
	{
		String supplierNum="";
		if(Ltype.equalsIgnoreCase("xpath"))
		{
			supplierNum= driver.findElement(By.xpath(LValue)).getAttribute("value");
		}
		if(Ltype.equalsIgnoreCase("name"))
		{
			supplierNum= driver.findElement(By.name(LValue)).getAttribute("value");
		}
		if(Ltype.equalsIgnoreCase("id"))
		{
			supplierNum= driver.findElement(By.id(LValue)).getAttribute("value");
		}
		FileWriter fw = new FileWriter("./CaptureData/supplier.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(supplierNum);
		bw.flush();
		bw.close();
	}
	//method for verify supplier number in supplier table
	public static void supplierTable()throws Throwable
	{
		//read supplier number from above note pad
		FileReader fr = new FileReader("./CaptureData/supplier.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data =br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("Search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("Search-button"))).click();
		Thread.sleep(3000);
		String Act_data =driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
		Reporter.log(Exp_Data+"         "+Act_data,true);
		try {
		Assert.assertEquals(Act_data, Exp_Data,"Supplier Number Not Matching");	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
	}
	//method for capturing customer number into notepad
	public static void capturecus(String Ltype,String LValue)throws Throwable
	{
		String CustomerNum="";
		if(Ltype.equalsIgnoreCase("xpath"))
		{
			CustomerNum= driver.findElement(By.xpath(LValue)).getAttribute("value");
		}
		if(Ltype.equalsIgnoreCase("name"))
		{
			CustomerNum= driver.findElement(By.name(LValue)).getAttribute("value");
		}
		if(Ltype.equalsIgnoreCase("id"))
		{
			CustomerNum= driver.findElement(By.id(LValue)).getAttribute("value");
		}
		FileWriter fw = new FileWriter("./CaptureData/customer.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(CustomerNum);
		bw.flush();
		bw.close();
	}//method for verify customer number in customer table
	public static void customerTable()throws Throwable
	{
		//read supplier number from above note pad
		FileReader fr = new FileReader("./CaptureData/customer.txt");
		BufferedReader br = new BufferedReader(fr);
		String Exp_Data =br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("Search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("Search-button"))).click();
		Thread.sleep(3000);
		String Act_data =driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
		Reporter.log(Exp_Data+"         "+Act_data,true);
		try {
		Assert.assertEquals(Act_data, Exp_Data,"Customer Number Not Matching");	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	
	
	
	
	

}



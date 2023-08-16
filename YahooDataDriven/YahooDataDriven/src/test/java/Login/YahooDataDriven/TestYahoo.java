package Login.YahooDataDriven;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestYahoo extends Base {
	public static List<String[]> readCSV(String filePath) throws IOException, CsvException {
	    CSVReader reader = new CSVReader(new FileReader(filePath));
	    List<String[]> records = reader.readAll();
	    reader.close();
	    return records;
	}
	@BeforeTest
	public void setup() {
		start();
	}
	
	@Test
	public void Login() throws IOException, CsvException, InterruptedException {
		String CSV_path = "C:/Users/Krishnachaithanya/eclipse-workspace/YahooDataDriven/src/main/java/Rec.csv";
		CSVReader reader = new CSVReader(new FileReader(CSV_path));
		List<String[]> records = reader.readAll();
		reader.close();
		records.remove(0);
		
		CSVWriter writer = new CSVWriter(new FileWriter(CSV_path));
		boolean headerWritten = false;
		
      for (String[] record : records) {
	    	
	    	String fn = record[0];
	        String ln = record[1];
	        String pass = record[2];
	        String nye = record[3];
	        String day = record[4];
	        String yr = record[5];
	        String phn = record[6];
	      

	        driver.get(url);

	        driver.findElement(By.id("usernamereg-firstName")).sendKeys(fn);
			driver.findElement(By.id("usernamereg-lastName")).sendKeys(ln);
	        
			driver.findElement(By.xpath("//input[@name='userId']")).sendKeys(nye);
			driver.findElement(By.id("usernamereg-password")).sendKeys(pass);
			Thread.sleep(1000);
			driver.findElement(By.id("usernamereg-day")).sendKeys(day);
			driver.findElement(By.id("usernamereg-year")).sendKeys(yr);
			
			WebElement mnth =  driver.findElement(By.id("usernamereg-month"));
			Select m = new Select(mnth);
			m.selectByVisibleText("September");

			driver.findElement(By.id("reg-submit-button")).click();
			Thread.sleep(3000);
			driver.findElement(By.name("phone")).sendKeys(phn);
			String t = driver.findElement(By.id("usernamereg-phone")).getAttribute("value");
			char[] s = t.toCharArray();
			int count = 0;
			for (int i=0; i<s.length; i++) {
				count++;
			}
			if(count == 10) {
				System.out.println("Entered phone number is valid");
			}else {
				System.out.println("Entered phone number is not valid");
			}

	   
	       if (!headerWritten) {
	            
	            headerWritten = true;
	            writer.writeNext(new String[]{"Firstname", "Lastname", "Password", "NewEmail", "Day", "Year", "PhoneNumber"});
	        }
	        writer.writeNext(record);
	        
       }
 writer.close();
   }
	@Test
	public void Con() {
		String parWin = driver.getWindowHandle();

	    driver.findElement(By.xpath("//a[text()='Terms']")).click();
	    Set<String> aW = driver.getWindowHandles();
	    for (String wH : aW) {
	        if (!wH.equals(parWin)) {
	            driver.switchTo().window(wH);
	            break;
	        }
	    }
	    String cur = driver.getCurrentUrl();
	    String exp = "https://legal.yahoo.com/us/en/yahoo/terms/otos/index.html";
	    Assert.assertEquals(cur, exp);
		}
	@Test
	public void SC() throws IOException, CsvException, InterruptedException {
		start();
		Login();
		driver.findElement(By.xpath("//button[text()='Send code']")).click();
	}
	@AfterTest
	public void exit() {
		end();
	}

}

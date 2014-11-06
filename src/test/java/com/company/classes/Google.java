package com.company.classes;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.company.util.Excel;

public class Google {
	private WebDriver driver;
	
	@BeforeMethod
	public void beforeMethod() {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test
	public void verifyLink() throws Exception{
		Excel excel = new Excel("src/test/resources/data/Text.xls", "Sheet1");
		driver.get("https://google.co.in/");
		driver.findElement(By.id("gbqfq")).sendKeys(excel.getCellValue(1, 1));
		driver.findElement(By.id("gbqfb")).click();
		//ol[@id='rso']/div[@class='srg']/li[1]//h3/a
		Assert.assertTrue(driver.findElement(By.xpath("//ol[@id='rso']/div[@class='srg']/li[1]//h3/a")).getText().toUpperCase().contains( (excel.getCellValue(1, 1)).toUpperCase() ));
	}
	
	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}
}

package com.library_practice.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class    Driver {

 /*
 Private constructor — closing access to the
 object of this class from outside the class
  */
 private Driver(){}

 /*---------------------------------------------*/

 /*
 Private driver — closing access to the driver
 of this class from outside the class
 Static — we will use it in a static method
  */
 //private static WebDriver driver;
 private static InheritableThreadLocal<WebDriver> driverPool = new InheritableThreadLocal<>();

 /*---------------------------------------------*/

 /*
 Creating re-usable method which will return
 same driver instance when called
  */
 public static WebDriver getDriver(){

  if (driverPool.get() == null){
   String browserType = ConfigurationReader.getProperty("browser");
   switch (browserType){
    case "chrome":
     WebDriverManager.chromedriver().setup();
     driverPool.set(new ChromeDriver());
     driverPool.get().manage().window().maximize();
     driverPool.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
     break;
    case "firefox":
     WebDriverManager.firefoxdriver().setup();
     driverPool.set(new FirefoxDriver());
     driverPool.get().manage().window().maximize();
     driverPool.get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
     break;
   }
  }

  return driverPool.get();
 }

 //this method will make sure that after quiting method, driver will always be null
 public static void quitDriver(){
  if (driverPool.get()!=null){
   driverPool.get().quit(); //this line will terminate session ID
   driverPool.remove();
  }
 }

}
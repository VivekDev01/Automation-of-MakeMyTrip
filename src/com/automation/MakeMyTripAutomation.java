package com.automation;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;

import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class MakeMyTripAutomation {

    public static void main(String[] args) throws IOException {
        System.setProperty("web-driver.chrome.driver", "E:\\Downloaded Shoftwares\\testing\\chrome driver\\chromedriver-win64\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.makemytrip.com/railways/");

        takeScreenshot(driver, "InitialScreenshot");
        Logger logger = Logger.getLogger("MyLog");
        FileHandler fh;

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        wait.until(ExpectedConditions.titleContains("MakeMyTrip"));

        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page Title: " + driver.getTitle());

        try {
            Files.createDirectories(Paths.get("logs"));

            fh = new FileHandler("logs/myLogFile.log");
            logger.addHandler(fh);

            //Remove POP Up
            WebElement PopElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='SW']/div[1]/div[2]/div[2]/div/section/span")));
            if(PopElement.isEnabled()) PopElement.click();
            takeScreenshot(driver, "AfterClickingPopElement");
            logger.info("Popup is cancelled.");


            // Select Start City
            WebElement fromCityInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@data-cy='fromCity']")));
            fromCityInput.click();
            WebElement fromInputText = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div/div[2]/div/div[1]/div[1]/div/div/div/input")));
            fromInputText.click();
            fromInputText.sendKeys("Delhi");
            WebElement fromDropdownOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"react-autowhatever-1-section-0-item-0\"]/div/div")));
            fromDropdownOption.click();
            takeScreenshot(driver, "AfterTakingFromCityInput");
            logger.info("Typed and selected the start city DELHI from Dropdown.");



            Thread.sleep(4000);


            // Select Destination City
            WebElement toCityInput = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div/div[2]/div/div[2]")));
            toCityInput.click();
            WebElement toInputText = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div[2]/div/div/div/div[2]/div/div[2]/div[1]/div/div/div/input")));
            toInputText.click();
            toInputText.sendKeys("Lucknow");
            WebElement toDropdownOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='makeFlex']/div/p/span[text()='Lucknow']")));
            toDropdownOption.click();
            takeScreenshot(driver, "AfterTakingToCityInput");
            logger.info("Typed and selected the destination city LUCKNOW from Dropdown.");


            Thread.sleep(4000);


            // Pick Travelling Date
            WebElement travelDate = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div/div[2]/div/div[3]")));
            travelDate.click();

            String flag = "False";
            while(flag.equals("False")) {
                if(!driver.findElements(By.xpath("//div[@class='DayPicker-Day'][contains(@aria-label,'Mar 20 2024')]")).isEmpty()) {
                    driver.findElement(By.xpath("//div[@class='DayPicker-Day'][contains(@aria-label,'Mar 20 2024')]")).click();
                    flag="True";
                }
                else {
                    driver.findElement(By.xpath("//span[@class='DayPicker-NavButton DayPicker-NavButton--next']")).click();
                }
            }
            takeScreenshot(driver, "AfterPickingDate");
            logger.info("Picked the travelling Date from date picker.");


            Thread.sleep(4000);


            // Pick Class
            WebElement classDropdown = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div[1]/div/div[2]/div/div[4]/label/span")));
            classDropdown.click();
            WebElement thirdACOption = driver.findElement(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div/div[2]/div/div[4]/ul/li[3]"));
            thirdACOption.click();
            takeScreenshot(driver, "AfterPickingClass");
            logger.info("Picked the class of journey from dropdown.");


            Thread.sleep(4000);


            // Click Search Button
            WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"root\"]/div/div[2]/div/div/div/div[2]/p/a")));
            searchButton.click();
            takeScreenshot(driver, "AfterClickingOnSearch");
            logger.info("Clicked the search button after entering all the details.");


            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }
    public static void takeScreenshot(WebDriver driver, String fileName) throws IOException {
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String projectDir = System.getProperty("user.dir");
        String screenshotsDir = projectDir + File.separator + "screenshots";
        Files.createDirectories(Paths.get(screenshotsDir));
        File destinationFile = new File(screenshotsDir + File.separator + fileName + ".png");
        Files.copy(screenshotFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
}

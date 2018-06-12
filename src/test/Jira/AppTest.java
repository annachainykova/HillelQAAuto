package Jira;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



public class AppTest {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private String username = "leonid.haidanov";
    private String password = "kbfybn7323";
    private String summary = "This is the test ticket";
    private String errorLoginMessage = "Sorry, your username and password are incorrect - please try again.";
    private String ticketPath;
    private String filePath = "C:\\Users\\anya_\\Downloads\\500.jpeg";
    private String ticketWithFilePath;

    @BeforeTest
    public void openChrome() {

        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");

        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();
        driver.get("http://jira.hillel.it:8080/login.jsp?");
    }

    @AfterTest
    public void closeChrome() {
        driver.quit();
    }
    
    @Test (priority = 0)
    public void logInTestNegative() {
        clearAndFill(By.cssSelector("input[name=os_username]"), username);
        clearAndFill(By.cssSelector("input[name=os_password]"), password+1).submit();
        Assert.assertEquals (driver.findElement(By.className("aui-message")).getText(), errorLoginMessage);
    }

    @Test (priority = 1)
    public void logInTestPositive() {
        clearAndFill(By.cssSelector("input[name=os_username]"), username);
        clearAndFill(By.cssSelector("input[name=os_password]"), password).submit();
        Assert.assertTrue(driver.findElements(By.cssSelector("a[data-username='" + username + "']")).size()>0);
//        driver.get("http://jira.hillel.it:8080/browse/GQR-939"); // just for tests
    }


    @Test (priority = 2)
    public void createIssue() throws InterruptedException {
        driver.findElement(By.cssSelector("a[id=create_link]")).click();
        Thread.sleep(5000);
        clearAndFill(By.cssSelector("input[id=project-field]"), "GQR" +"\n");
        Thread.sleep(5000);
        clearAndFill(By.cssSelector("input[id=summary]"), summary).submit();
        Thread.sleep(2000);
        WebElement successMessage = driver.findElement(By.partialLinkText(summary));
        Assert.assertTrue(successMessage != null);
        ticketPath =  successMessage.getAttribute("href");

        // <a class="issue-created-key issue-link" data-issue-key="GQR-891" href="/browse/GQR-891">GQR-891 - Test issue</a>
    }

    @Test (priority = 3)
    public void openIssue() {
        driver.get(ticketPath);
        Assert.assertTrue(driver.getTitle().contains(summary));
    }



    @Test(dependsOnMethods = "logInTestPositive")
    public void createIssueWithUploadFile() throws InterruptedException {
        Thread.sleep(5000);
        driver.findElement(By.cssSelector("a[id=create_link]")).click();
        Thread.sleep(5000);

        WebElement projectSelect = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[id=project-field]")));
        clearAndFill(By.cssSelector("input[id=project-field]"), "GQR" +"\n");
        Thread.sleep(1000);
        WebElement uploadAttachement = driver.findElement(By.cssSelector("input[type='file']"));
        uploadAttachement.sendKeys(filePath);
        WebElement ex = driver.findElement(By.cssSelector("button[class='aui-button aui-button-subtle aui-button-compact']"));
        Assert.assertTrue((filePath.contains(driver.findElement(By.cssSelector("span[class='upload-progress-bar__file-name']")).getText())) &&
                ex.isDisplayed());
        Thread.sleep(1000);
        clearAndFill(By.cssSelector("input[id=summary]"), summary).submit();
        WebElement successMessage = wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(summary)));
        ticketWithFilePath =  successMessage.getAttribute("href");

    }

    @Test (dependsOnMethods = {"logInTestPositive", "createIssueWithUploadFile" })
    public void downloadFile() throws InterruptedException {
        driver.get(ticketWithFilePath);
        driver.findElement(By.cssSelector("a[class='attachment-title']")).click();
        Thread.sleep(5000);
        driver.findElement(By.cssSelector("a[id=cp-control-panel-download]")).click();


    }

//    @Test (priority = 4)
//    public void attachFile() throws InterruptedException {
////        driver.findElement(By.cssSelector("a[id=opsbar-operations_more]")).click();
//        driver.findElement(By.cssSelector("button [class=issue-drop-zone__button]")).sendKeys("C:\\Users\\test.png");
//
//    }



    private static WebElement clearAndFill(By selector, String data) {
        WebElement element = driver.findElement(selector);
        element.clear();
        element.sendKeys(data);
        return element;
    }




}
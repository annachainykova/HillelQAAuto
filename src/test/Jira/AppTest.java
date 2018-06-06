package Jira;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;



public class AppTest {
    private static WebDriver driver;
    private String username = "leonid.haidanov";
    private String password = "kbfybn7323";
    private String summary = "This is the test ticket";
    private String errorLoginMessage = "Sorry, your username and password are incorrect - please try again.";
    private String path;

    @BeforeTest
    public void openChrome() {
        System.setProperty("webdriver.chrome.driver", "E:\\chromedriver.exe");
        driver = new ChromeDriver();
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
        path =  successMessage.getAttribute("href");

        // <a class="issue-created-key issue-link" data-issue-key="GQR-891" href="/browse/GQR-891">GQR-891 - Test issue</a>
    }

    @Test (priority = 3)
    public void openIssue() {
        driver.get(path);
        Assert.assertTrue(driver.getTitle().contains(summary));
    }

    private static WebElement clearAndFill(By selector, String data) {
        WebElement element = driver.findElement(selector);
        element.clear();
        element.sendKeys(data);
        return element;
    }
}

//Test
//Test
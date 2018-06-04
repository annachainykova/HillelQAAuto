package Jira;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AppTest {
    private static WebDriver driver;


    @DataProvider
    public static Object[][] emailAndPasswordPositive() {
        return new Object[][] { { "a.chainikova", "SarbonaRosta" }, { "leonid.haidanov", "kbfybn7323" }, };
    }

    @DataProvider
    public static Object[][] emailAndPasswordNegative() {
        return new Object[][] { { "a.chainikova", "1111" }, { "leonid.haidanov1", "kbfybn7323" }, };
    }

    @BeforeTest
    public void openChrome()  throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }


    @Test (dataProvider = "emailAndPasswordPositive")
    public void logInTestPositive(String email, String password) {
        driver.get("http://jira.hillel.it:8080/login.jsp?");
        fill(By.cssSelector("input[name=os_username]"), email);
        fill(By.cssSelector("input[name=os_password]"), password).submit();

        Assert.assertTrue(driver.findElements(By.cssSelector("a[data-username='" + email + "']")).size()>0);
    }

    @Test (dataProvider = "emailAndPasswordPositive")
    public void logInTestNegative(String email, String password) {
        driver.get("http://jira.hillel.it:8080/login.jsp?");
        fill(By.cssSelector("input[name=os_username]"), email);
        fill(By.cssSelector("input[name=os_password]"), password).submit();

        Assert.assertFalse(driver.findElements(By.cssSelector("a[data-username='" + email + "']")).size()>0);
    }




//
//    @AfterTest
//    public void closeChrome() {
//        driver.close();
//    }


    private static WebElement fill(By selector, String data) {
        WebElement element = driver.findElement(selector);
        element.sendKeys(data);

        return element;
    }

}

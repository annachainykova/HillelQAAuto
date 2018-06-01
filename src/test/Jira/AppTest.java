package Jira;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AppTest {
    private static WebDriver driver;


    @DataProvider
    public static Object[][] emailAndPassword() {
        return new Object[][] { { "a.chainikova@gmail.com", "SarbonaRosta" }, { "leonid.haidanov@gmail.com", "kbfybn7323" }, };
    }

    @BeforeTest
    public void openChrome()  throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
        driver = new ChromeDriver();
    }


    @Test (dataProvider = "emailAndPassword")
    public void logInTest(String email, String password) {
        driver.get("http://jira.hillel.it:8080/login.jsp?");
        fill(By.cssSelector("input[id=os_username]"), email);
        fill(By.cssSelector("input[id=os_password]"), password).submit();
    }


    @Test (dataProvider = "")


    @AfterTest
    public void closeChrome() {
        driver.quit();
    }


    private static WebElement fill(By selector, String data) {
        WebElement element = driver.findElement(selector);
        element.sendKeys(data);

        return element;
    }

}

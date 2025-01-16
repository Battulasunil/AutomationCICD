package rahulShettyacademy;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class StandAloneTestOriginal {
    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup(); //if we don't have chrome in our local machine, it downloads automatically.
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.manage().window().maximize();
        String productName = "IPHONE 13 PRO";

        driver.get("https://rahulshettyacademy.com/client/");
        driver.findElement(By.id("userEmail")).sendKeys("nanibujji@gmail.com");
        driver.findElement(By.id("userPassword")).sendKeys("Nanibujji@123");
        driver.findElement(By.id("login")).click();

        //ADD TO CART
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
        List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
        WebElement prod = products.stream().filter(product ->
                product.findElement(By.cssSelector("b")).getText().equals(productName)).findFirst().orElse(null);

        prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();


        //identifying loading thing and clicking on cart
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));
        driver.findElement(By.cssSelector("[routerlink*='cart']")).click();

        //cart page
        List<WebElement> cartProducts = driver.findElements(By.cssSelector(".cartSection h3"));
        Boolean match = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
        Assert.assertTrue(match);


        //checkout button click
        //driver.findElement(By.cssSelector(".subtotal li button")).click();
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".subtotal li button")));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,100)");
        Thread.sleep(1000);
        element.click();

        //checkout page after clicking checkout button
        Actions a = new Actions(driver);
        a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".ta-results")));
        driver.findElement(By.cssSelector(".ta-item:nth-of-type(2)")).click();

        //click on place order button
        WebElement placeOrderButton = driver.findElement(By.className("action__submit"));
        JavascriptExecutor js1 = (JavascriptExecutor) driver;
        js1.executeScript("window.scrollBy(0, 500)");
        placeOrderButton.click();

        //after clicking place order \
        String confirmMsg = driver.findElement(By.cssSelector(".hero-primary")).getText();
        System.out.println(confirmMsg);
        Assert.assertTrue(confirmMsg.equalsIgnoreCase("Thankyou for the order."));


    }
}
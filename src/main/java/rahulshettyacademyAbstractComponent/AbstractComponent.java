package rahulshettyacademyAbstractComponent;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import rahulshettyacademy.PageObject.CartPage;
import rahulshettyacademy.PageObject.OrderPage;

import java.time.Duration;

public class AbstractComponent {
    WebDriver driver;
    public AbstractComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(css="[routerlink*='cart']")
    WebElement cartHeader;

    @FindBy(css="[routerlink*='orders']")
    WebElement orderHeader;

    public void waitForElementToAppear(By FindBy){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(FindBy));
    }

    public void waitForWebElementToAppear(WebElement FindBy){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(FindBy));
    }

    public CartPage goToCartPage(){
        cartHeader.click();
        CartPage cartpage = new CartPage(driver);
        return cartpage;

    }

    public OrderPage goToOrdersPage(){
        orderHeader.click();
        OrderPage orderpage = new OrderPage(driver);
        return orderpage;

    }
    public void waitForElementToDisappear(WebElement ele) throws InterruptedException {
        //to make it faster we can use Thraed.sleep
        Thread.sleep(1000);
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        //wait.until(ExpectedConditions.invisibilityOf(ele));
    }
}

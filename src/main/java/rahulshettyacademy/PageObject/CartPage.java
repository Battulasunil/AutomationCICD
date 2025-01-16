package rahulshettyacademy.PageObject;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import rahulshettyacademyAbstractComponent.AbstractComponent;

import java.time.Duration;
import java.util.List;

public class CartPage extends AbstractComponent {

    WebDriver driver;
    public CartPage(WebDriver driver){
        super(driver);
        this.driver=driver;
        PageFactory.initElements(driver,this);

    }
    @FindBy(css=".cartSection h3")
    private List<WebElement> productTitles;

    @FindBy(css=".subtotal li button")
    WebElement checkout;

    public Boolean verifyProductDisplay(String productName){
        Boolean match = productTitles.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
        return match;
    }
    public CheckoutPage checkoutProducts() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Use Actions to scroll the page down
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.PAGE_DOWN).perform();
        // Wait for the checkout element to appear
        WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(checkout));
        checkoutButton.click();
        return new CheckoutPage(driver);
    }

}

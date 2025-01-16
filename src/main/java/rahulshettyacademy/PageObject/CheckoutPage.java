package rahulshettyacademy.PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import rahulshettyacademyAbstractComponent.AbstractComponent;

public class CheckoutPage extends AbstractComponent {
    WebDriver driver;

    public CheckoutPage(WebDriver driver){
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver,this);
    }

    @FindBy(css="[placeholder='Select Country']")
    WebElement country;

    @FindBy(css=".ta-item:nth-of-type(2)")
    WebElement selectCountry;

    @FindBy(className = "action__submit")
    WebElement submit;

    By results =  By.cssSelector(".ta-results");

    public void selectCountry( String countryName){
        Actions a = new Actions(driver);
        a.sendKeys(country, countryName).build().perform();
        waitForElementToAppear(results);
        selectCountry.click();
    }

    public ConfirmationPage placeOrderButton(){
        submit.click();
        return new ConfirmationPage(driver);

//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        // Use Actions to scroll the page down
//        Actions actions = new Actions(driver);
//        actions.sendKeys(Keys.PAGE_DOWN).perform();
//        // Wait for the checkout element to appear
//        WebElement checkoutButton = wait.until(ExpectedConditions.elementToBeClickable(checkout));
//        checkoutButton.click();
    }
}

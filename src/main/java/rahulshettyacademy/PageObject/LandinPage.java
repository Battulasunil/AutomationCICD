package rahulshettyacademy.PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import rahulshettyacademyAbstractComponent.AbstractComponent;

public class LandinPage  extends AbstractComponent {

    WebDriver driver;

    public LandinPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //WebElement userEmail = driver.findElement(By.id("userEmail"));

    //the top we can write simply below type -- below are webElements
    @FindBy(id = "userEmail")
    WebElement userEmail;

    @FindBy(id="userPassword")
    WebElement userPassword;

    @FindBy(id="login")
    WebElement userLogin;

    @FindBy(css = "[class*='flyInOut']")
    WebElement errorMessage;


    //Action methods
    public ProductCatalogue loginApplication(String email, String password){
        userEmail.sendKeys(email);
        userPassword.sendKeys(password);
        userLogin.click();

        ProductCatalogue productcatalogue = new ProductCatalogue(driver);
        return productcatalogue;

    }

    public String getErrorMessage(){
        waitForWebElementToAppear(errorMessage);
       return errorMessage.getText();
    }

    public void goTo(){
        driver.get("https://rahulshettyacademy.com/client/");
    }

}


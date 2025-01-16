package rahulShettyacademy;


import TestComponents.BaseTest;
import TestComponents.Retry;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import rahulshettyacademy.PageObject.CartPage;
import rahulshettyacademy.PageObject.ProductCatalogue;

import java.io.IOException;
import java.util.List;

public class ErrorValidationsTest extends BaseTest {
    @Test(groups = {"ErrorHandling"},retryAnalyzer = Retry.class)
    public void LoginErrorValidation() throws IOException, InterruptedException {

        String productName = "IPHONE 13 PRO";
        landingpage.loginApplication("naibujji@gmail.com", "Nanibuji@123");
        Assert.assertEquals("Incorrect email or password.", landingpage.getErrorMessage());
    }

    @Test
    public void ProductErrorValidatiosn() throws InterruptedException {
        String productName = "IPHONE 13 PRO";
        ProductCatalogue productcatalogue = landingpage.loginApplication("nanibujji@gmail.com", "Nanibujji@123");

        //ADD TO CART
        //ProductCatalogue productcatalogue = new ProductCatalogue(driver);
        List<WebElement> products = productcatalogue.getProductList();
        productcatalogue.addprodToCart(productName);

        //clicking on cart button
        CartPage cartpage = productcatalogue.goToCartPage();

        //cart page
        //CartPage cartpage = new CartPage(driver);
        Boolean match = cartpage.verifyProductDisplay("IPHONE 133 PRO");
        Assert.assertFalse(match);

    }

}

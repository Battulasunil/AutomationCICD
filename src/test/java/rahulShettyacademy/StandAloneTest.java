package rahulShettyacademy;


import TestComponents.BaseTest;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import rahulshettyacademy.PageObject.*;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class StandAloneTest extends BaseTest {
    String productName = "IPHONE 13 PRO";

    @Test(dataProvider = "getData", groups = {"Purchase"})
    public void StandAlonetest(HashMap<String, String> input) throws IOException, InterruptedException {

        ProductCatalogue productcatalogue = landingpage.loginApplication(input.get("email"), input.get("password"));

        //ADD TO CART
        //ProductCatalogue productcatalogue = new ProductCatalogue(driver);
        List<WebElement> products = productcatalogue.getProductList();
        productcatalogue.addprodToCart(input.get("productName"));

        //clicking on cart button
        CartPage cartpage = productcatalogue.goToCartPage();

        //cart page
        //CartPage cartpage = new CartPage(driver);
        Boolean match = cartpage.verifyProductDisplay(input.get("productName"));
        Assert.assertTrue(match);

        //checkout button click
        CheckoutPage checkoutpage = cartpage.checkoutProducts();
        checkoutpage.selectCountry("india");
        ConfirmationPage confirmationpage = checkoutpage.placeOrderButton();

        String confirmmsg = confirmationpage.getconfirmtext();
        System.out.println(confirmmsg);
        Assert.assertTrue(confirmmsg.equalsIgnoreCase("Thankyou for the order."));
    }

    //To verify IPHONE 13 PRO is displaying in orders page
    @Test(dependsOnMethods = {"StandAlonetest"})
    public void OrderHistoryTest() {
        ProductCatalogue productcatalogue = landingpage.loginApplication("nanibujji@gmail.com", "Nanibujji@123");
        OrderPage orderspage = productcatalogue.goToOrdersPage();
        Assert.assertTrue(orderspage.verifyOrderDisplay(productName));
    }

    public String getScreenshot(String testCaseName) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "//reports" + testCaseName + ".png";
        File file = new File(path);
        FileUtils.copyFile(source, file);
        return path;
    }
    //extent reports


    @DataProvider
    public Object[][] getData() throws IOException {
//        HashMap<String,String> map = new HashMap<>();
//        map.put("email","nanibujji@gmail.com");
//        map.put("password","Nanibujji@123");
//        map.put("productName","IPHONE 13 PRO");
//
//        HashMap<String,String> map1 = new HashMap<>();
//        map1.put("email","nanibujji@gmail.com");
//        map1.put("password","Nanibujji@123");
//        map1.put("productName","BANARSI SAREE");

        List<HashMap<String, String>> data = getJsonDataToMap(System.getProperty("user.dir") + "//src//test//java/Data//Purchase.json");
        return new Object[][]{{data.get(0)}, {data.get(1)}};
    }
//
//    public Object[][] getData() {
//        return new Object[][]{{"nanibujji@gmail.com", "Nanibujji@123","IPHONE 13 PRO"}, {"nanibujji@gmail.com","Nanibujji@123", "BANARSI SAREE"}};
//    }


}

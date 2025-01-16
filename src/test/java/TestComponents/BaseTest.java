package TestComponents;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import rahulshettyacademy.PageObject.LandinPage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

public class BaseTest {
    public WebDriver driver;
    public LandinPage landingpage;

    public WebDriver initializer() throws IOException {

        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "//src//main//java//Resources//GlobalData.properties");
        prop.load(fis);
        String browserName =  System.getProperty("browser")!=null ? System.getProperty("browser") : prop.getProperty("browser");
       // prop.getProperty("browser");
//        System.out.println("Browser Name: " + browserName);

        if (browserName.contains("chrome")) {
            ChromeOptions options = new ChromeOptions();
            WebDriverManager.chromedriver().setup(); //if we don't have chrome in our local machine, it downloads automatically.
           if (browserName.contains("headless"))
           {
               options.addArguments("headless");
           }
            driver = new ChromeDriver(options);
            driver.manage().window().setSize(new Dimension(1440,900));//full screen
        } else if (browserName.equalsIgnoreCase("edge")) {
            WebDriverManager.edgedriver().setup(); //if we don't have chrome in our local machine, it downloads automatically.
            driver = new EdgeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup(); //if we don't have chrome in our local machine, it downloads automatically.
            driver = new FirefoxDriver();
        }
        //System.out.println(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.manage().window().maximize();
        return driver;
    }

    public List<HashMap<String, String>> getJsonDataToMap(String filepath) throws IOException {
        //read json to string
        String jsonContent = FileUtils.readFileToString(new File(filepath), StandardCharsets.UTF_8);

        //string to hashmap - jackson databind
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {
        });
        return data;

    }

    public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "//reports//" + testCaseName + ".png";
        File file = new File(path);
        FileUtils.copyFile(source, file);
        return path;
    }

    @BeforeMethod(alwaysRun = true)
    public LandinPage launchApp() throws IOException {
        driver = initializer();
        landingpage = new LandinPage(driver);
        landingpage.goTo();
        return landingpage;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.close();
    }
}

package Resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {
    public  static  ExtentReports getReportObject(){
        String path = System.getProperty("user.dir")+"//reports//index.html";
        //helper class
        ExtentSparkReporter reporter = new ExtentSparkReporter(path);
        reporter.config().setReportName("Web Automation Results");
        reporter.config().setDocumentTitle("Test Results");

        //main class
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(reporter);
        //tester name
        extent.setSystemInfo("Tester", "sunil");
        return extent;
    }


}

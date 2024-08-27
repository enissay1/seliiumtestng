package lu.distri2b;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class CustomTestListener implements ITestListener {
    public static int totalTests;
    public static int totalPassedTests;
    public static int totalFailedTests;
    public static int totalSkippedTests;

    private BufferedWriter writer;
    private File reportFile = new File("test-report.html");

    @Override
    public void onStart(ITestContext context) {
        // Récupérer le nombre total de tests planifiés
        totalTests = context.getAllTestMethods().length;
        // Ouvrir le fichier HTML au début des tests
        try {
            writer = new BufferedWriter(new FileWriter("test-report.html"));
            writer.write("<html><head><title>Test Report</title></head><body>");
            writer.write("<h1>Test Report</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onTestSuccess(ITestResult result) {

        // Ajouter un message de succès pour chaque test réussi
        try {
            writer.write("<div style='color:green;'>");
            writer.write("<h2>Test Passed: " + result.getName() + "</h2>");
            writer.write("<p>Details: Test method " + result.getMethod().getMethodName() + " passed successfully.</p>");
            writer.write("</div>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = (WebDriver) result.getTestContext().getAttribute("WebDriver");

        if(driver == null){
            System.out.println("mon driver est " + driver);
        }

        try {
                // Ajout du message d'échec au rapport HTML
                writer.write("<div style='color:red;'>");
                writer.write("<h2>Test Failed: " + result.getName() + "</h2>");
                writer.write("<p>Details: Test method " + result.getMethod().getMethodName() + " failed with exception: " + result.getThrowable() + "</p>");

                // Capture d'écran lors de l'échec du test
                File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String screenshotPath = "C:/Users/Yassine/IdeaProjects/seliiumtestng/errorScreenshots/" + result.getName() + "-" + Arrays.toString(result.getParameters()) + ".jpg";
                FileUtils.copyFile(scrFile, new File(screenshotPath));

                // Intégrer l'image dans le rapport HTML
                writer.write("<p><img src='" + screenshotPath + "' alt='Screenshot' style='width:600px;'/></p>");
                writer.write("</div>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        // Fermer le fichier HTML à la fin des tests
        try {
            writer.write("</body></html>");
            writer.close();


            // Récupérer le nombre de tests passés, échoués et ignorés à la fin de l'exécution des tests
            totalPassedTests = context.getPassedTests().size();
            totalFailedTests = context.getFailedTests().size();
            totalSkippedTests = context.getSkippedTests().size();
            //envoi de mail
            MailtrapEmailExample.sendEmailWithAttachment(reportFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
    private String captureScreenshot(String testName) {
        String screenshotName = "screenshot_" + testName + ".png";

        try {
            WebDriver driver = WebDriverManager.getDriver();
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String screenshotPath = System.getProperty("user.dir") + File.separator + "screenshots" + File.separator + screenshotName;
            FileUtils.copyFile(screenshot, new File(screenshotPath));
            return screenshotPath;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    */
}

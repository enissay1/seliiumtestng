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
    private final File reportFile = new File("test-report.html");

    @Override
    public void onStart(ITestContext context) {
        totalTests = context.getAllTestMethods().length;
        try {
            writer = new BufferedWriter(new FileWriter(reportFile));
            writer.write("<html><head><title>Test Report</title></head><body>");
            writer.write("<h1>Test Report</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        setTestDescription(result);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        writeTestResult(result, "green", "Test Passed: ");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        WebDriver driver = (WebDriver) result.getTestContext().getAttribute("WebDriver");
        writeTestResult(result, "red", "Test Failed: ");
        if (driver != null) {
            takeScreenshot(driver, result);
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        try {
            writer.write("</body></html>");
            writer.close();
            totalPassedTests = context.getPassedTests().size();
            totalFailedTests = context.getFailedTests().size();
            totalSkippedTests = context.getSkippedTests().size();
            MailtrapEmailExample.sendEmailWithAttachment(reportFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setTestDescription(ITestResult result) {
        String description = "";
        switch (result.getName()) {
            case "modifierAgent":
                description = "Après le clic sur le bouton modifier, ce test vérifie le processus complet de modification d'un agent via le formulaire. Toutes les étapes ont été exécutées correctement, y compris le remplissage du formulaire et la sélection des options du dropdown.\n";
                break;
            case "addAgent":
                description = "Ce test vérifie le processus complet d'ajout d'un agent via le formulaire. Toutes les étapes ont été exécutées correctement, y compris le remplissage du formulaire et la sélection des options du dropdown.\n";
                break;
            case "filtreAgent":
                description = "Ce test vérifie le processus de filtrage d'un agent via le formulaire. Toutes les étapes ont été exécutées correctement, y compris le remplissage de tous les champs avant de lancer la recherche.\n";
                break;
            case "paginateAgent":
                description = "Ce test vérifie le processus de pagination d'un agent via les différentes pages. Toutes les étapes ont été exécutées correctement.\n";
                break;
        }
        result.setAttribute("description", description);
    }

    private void writeTestResult(ITestResult result, String color, String messagePrefix) {
        long durationInSeconds = (result.getEndMillis() - result.getStartMillis()) / 1000;
        String description = (String) result.getAttribute("description");

        try {
            writer.write("<div style='color:" + color + ";'>");
            writer.write("<h2>" + messagePrefix + result.getName() + "</h2>");
            writer.write("<p>Description: " + description + "</p>");
            writer.write("<p>Details: Test method " + result.getMethod().getMethodName() + " completed in " + durationInSeconds + " seconds.</p>\n");
            if(messagePrefix.startsWith("Test Failed: ")){
                writer.write("<p>"+ result.getMethod().getMethodName() + " failed with exception: " + result.getThrowable() + "</p>");
            };
            writer.write("</div>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void takeScreenshot(WebDriver driver, ITestResult result) {
        try {
            File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String screenshotPath = "C:/Users/Yassine/IdeaProjects/seliiumtestng/errorScreenshots/" + result.getName() + "-" + Arrays.toString(result.getParameters()) + ".jpg";
            FileUtils.copyFile(scrFile, new File(screenshotPath));
            writer.write("<p><img src='" + screenshotPath + "' alt='Screenshot' style='width:600px;'/></p>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package basics;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;

import java.time.Duration;

public class OpenBrowserTest1 {
        public static void main (String[] args){
            WebDriver driver = new ChromeDriver();
            driver.get("https://www.saucedemo.com/");
            driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

            WebElement username = driver.findElement(By.name("user-name"));
            WebElement password = driver.findElement(By.name("password"));
            WebElement submitButton = driver.findElement(By.cssSelector("#login-button"));

            username.sendKeys("standard_user");
            password.sendKeys("secret_sauce");

            submitButton.click();


            // Localisation de l'élément par son sélecteur
            WebElement titleElement = driver.findElement(By.cssSelector("span.title[data-test='title']"));

            // Récupération du texte de l'élément
            String titleText = titleElement.getText();

            // Vérification si le texte est égal à "Products"
            if (titleText.equals("Products")) {
                System.out.println("Le titre de l'élément est égal à 'Products'");
            } else {
                System.out.println("Le titre de l'élément n'est pas égal à 'Products'. Le titre est: " + titleText);
            }

            WebElement BtnAddProduct1 = driver.findElement(By.cssSelector("#add-to-cart-sauce-labs-backpack"));
            WebElement BtnAddProduct2 = driver.findElement(By.cssSelector("#add-to-cart-sauce-labs-bike-light"));

            BtnAddProduct1.click();
            BtnAddProduct2.click();

            WebElement ElmtPanier = driver.findElement(By.cssSelector("span.shopping_cart_badge[data-test='shopping-cart-badge']"));
            String valPanier = ElmtPanier.getText();

            if (valPanier.equals("2")) {
                System.out.println(" panier est égal à 2 ");
            } else {
                System.out.println("panier n'est pas égal à 2 . Le panier est: " + valPanier);
            }

            WebElement clickElmtPanier = driver.findElement(By.cssSelector("a.shopping_cart_link[data-test='shopping-cart-link']"));
            clickElmtPanier.click();

            WebElement Product1 = driver.findElement(By.cssSelector("#item_0_title_link .inventory_item_name"));
            WebElement Product2 = driver.findElement(By.cssSelector("#item_4_title_link .inventory_item_name"));

            //if display return = false
            boolean Product3 = driver.findElement(By.cssSelector("#item_0_title_link .inventory_item_name")).isDisplayed();
            System.out.println(Product3);

            String titleProduct1= Product1.getText();
            String titleProduct2= Product2.getText();

            if (titleProduct1.equals("Sauce Labs Bike Light")) {
                System.out.println(" product 1 exist ");
            } else {
                System.out.println("product 1 dont exist sa valeur  est: " + titleProduct1);
            }
            if (titleProduct2.equals("Sauce Labs Backpack")) {
                System.out.println(" product 2 exist ");
            } else {
                System.out.println("product 2 dont exist sa valeur  est: " + titleProduct2);
            }

            WebElement BtnCheckOut = driver.findElement(By.cssSelector("#checkout"));
            BtnCheckOut.click();

            WebElement firstName = driver.findElement(By.cssSelector("#first-name"));
            WebElement lastName = driver.findElement(By.cssSelector("#last-name"));
            WebElement postalCode = driver.findElement(By.cssSelector("#postal-code"));
            WebElement BtnContinue = driver.findElement(By.cssSelector("#continue"));

            firstName.sendKeys("yassine");
            lastName.sendKeys("ezzahr");
            postalCode.sendKeys("20000");
            BtnContinue.click();

            WebElement BtnFinish = driver.findElement(By.cssSelector("#finish"));
            BtnFinish.click();

            WebElement messageAlert = driver.findElement(By.cssSelector("#checkout_complete_container .complete-header"));
            String message=messageAlert.getText();
            if (message.equals("Thank you for your order!")) {
                System.out.println(" commande bien livrer ");
            } else {
                System.out.println("commande non livrer: ");
            }
            WebElement BtnBackToProduct = driver.findElement(By.cssSelector("#back-to-products"));
            BtnBackToProduct.click();


            WebElement BtnBurgerMenu = driver.findElement(By.cssSelector("#react-burger-menu-btn"));
            BtnBurgerMenu.click();

            WebElement BtnLogOut = driver.findElement(By.cssSelector("#logout_sidebar_link"));
            BtnLogOut.click();


            driver.quit();



        }
}

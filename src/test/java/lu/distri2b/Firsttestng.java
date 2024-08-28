package lu.distri2b;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.List;
//@Listeners(lu.distri2b.CustomTestListener.class)
public class Firsttestng {
    public String baseUrl = "https://bo.distri2b.lu/";
    public WebDriver driver;

    @BeforeTest
    public void launchBrowser(ITestContext context) {
        // Configuration de WebDriverManager pour gérer ChromeDriver
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.manage().window().maximize();

        context.setAttribute("WebDriver", driver);
        driver.get(baseUrl);
        // Localisation des éléments de connexion
        try {
            WebElement username = driver.findElement(By.name("username"));
            WebElement password = driver.findElement(By.name("password"));
            WebElement submitButton = driver.findElement(By.cssSelector("button"));

            // Remplissage des champs de connexion
            username.sendKeys("admin");
            password.sendKeys("123456");
            submitButton.click();

            // Vérification de la connexion réussie
            WebElement dashboard = driver.findElement(By.xpath("//*[@id=\"layout-wrapper\"]/div[1]/div/div/div/div/h4"));
            Assert.assertNotNull(dashboard, "Connexion échouée, élément 'dashboard' non trouvé");

        } catch (NoSuchElementException e) {
            System.out.println("Erreur lors de la connexion : Élément non trouvé.");
            Assert.fail("Connexion échouée");
        }
    }

    @Test
    public void addAgent() {
        try {
            // Naviguer vers la page des agents
            WebElement agentsLink = driver.findElement(By.xpath("//a[@href='/fr/agents']"));
            agentsLink.click();

            // Naviguer vers le formulaire d'ajout d'agent
            WebElement newAgentLink = driver.findElement(By.xpath("//a[@href='/fr/agent/new']"));
            newAgentLink.click();

            // Remplir le formulaire d'ajout d'agent
            WebElement agentUsername = driver.findElement(By.cssSelector("#agent_form_username"));
            WebElement agentPassword = driver.findElement(By.cssSelector("#agent_form_password_first"));
            WebElement agentConfirmPassword = driver.findElement(By.cssSelector("#agent_form_password_second"));
            WebElement agentPrenom = driver.findElement(By.cssSelector("#agent_form_firstName"));
            WebElement agentNom = driver.findElement(By.cssSelector("#agent_form_lastName"));
            WebElement agentEmail = driver.findElement(By.cssSelector("#agent_form_email"));
            WebElement agentComment = driver.findElement(By.cssSelector("#agent_form_comment"));

            agentUsername.sendKeys("user1");
            agentPassword.sendKeys("Admin@123456");
            agentConfirmPassword.sendKeys("Admin@123456");
            agentNom.sendKeys("ezzahr");
            agentPrenom.sendKeys("yassine");
            agentEmail.sendKeys("user1@gmail.com");
            agentComment.sendKeys("This is a test comment");

            // Sélectionner les options du dropdown
            Select droitDropdown = new Select(driver.findElement(By.id("agent_form_profile")));
            Select langDropdown = new Select(driver.findElement(By.id("agent_form_lang")));

            droitDropdown.selectByValue("3");
            langDropdown.selectByValue("1");

            // Défilement jusqu'à la fin de la page pour cliquer sur le bouton
            JavascriptExecutor js = (JavascriptExecutor) driver;

            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            // Cliquer sur le bouton d'ajout d'agent
            WebElement btnAddAgent = driver.findElement(By.cssSelector("#addAgent"));
            try {
                Thread.sleep(2000);  // Mettre en pause pour s'assurer que l'élément est visible
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restaurer l'état d'interruption
                System.out.println("Le thread a été interrompu.");
            }
            btnAddAgent.click();
            // Vérification du message de succès
            WebElement messageAddAgent = driver.findElement(By.xpath("//*[contains(text(), 'Agent ajouté avec succès')]"));
            Assert.assertNotNull(messageAddAgent, "Création échouée, message 'success' non trouvé");

        } catch (NoSuchElementException e) {
            System.out.println("Erreur lors de l'ajout d'agent : Élément non trouvé.");
            Assert.fail("Création échouée");
        }
    }

    @Test (priority = 1)
    public void filtreAgent() {
        try {
            // Naviguer vers la page des agents
            WebElement agentsLink = driver.findElement(By.xpath("//a[@href='/fr/agents']"));
            agentsLink.click();

            // Filtrer par nom d'utilisateur
            WebElement filterUsername = driver.findElement(By.name("username"));
            filterUsername.sendKeys("user1");

            WebElement filterRecherche = driver.findElement(By.xpath("//*[@id=\"layout-wrapper\"]/div[2]/div/div/div[3]/div[2]/div/div[2]/form/button"));
            filterRecherche.click();

            // Vérification du résultat du filtre
            WebElement messageFilterAgent = driver.findElement(By.xpath("//*[@id=\"layout-wrapper\"]/div[2]/div/div/div[3]/div[1]/div/div/div[1]/h4"));
            String messageTotal = messageFilterAgent.getText();
            Assert.assertEquals(messageTotal, "Liste des agents (Total: 1)", "Le filtre n'a pas retourné le résultat attendu.");

        } catch (NoSuchElementException e) {
            System.out.println("Erreur lors du filtrage d'agent : Élément non trouvé.");
            Assert.fail("Filtrage échoué");
        }
    }

    @Test
    public void paginateAgent() {
        // Naviguer vers la page des agents
        WebElement agentsLink = driver.findElement(By.xpath("//a[@href='/fr/agents']"));
        agentsLink.click();

        boolean usernameTrouve = false;
        String usernameRechercher="user1";

        while (!usernameTrouve) {
            // Récupérer toutes les lignes de la table contenant les usernames
            List<WebElement> lignesTable = driver.findElements(By.xpath("//table//tr"));
            for (WebElement ligne : lignesTable) {
                // Rechercher toutes les cellules (td) de la ligne actuelle
                List<WebElement> cellules = ligne.findElements(By.tagName("td"));

                // Vérifier si la ligne contient des cellules
                if (cellules.size() >= 2) {
                    //WebElement colonneUsername = ligne.findElement(By.xpath(".//td[2]")); //renvoie le deuxieme curent td
                    //String username1= colonneUsername.getText();
                    //System.out.println("colone username : "+ username1);

                    // Extraire le texte de la deuxième cellule (colonne username)
                    String user1 = cellules.get(1).getText(); // Les indices commencent à 0, donc 1 correspond à la deuxième cellule
                    //System.out.println(user1);
                    // Vérifiez si le username correspond à celui recherché
                    if (user1.equals(usernameRechercher)) {
                        WebElement agentsLinkEdit = driver.findElement(By.xpath("//a[@href='/fr/agent/860/edit']"));
                        JavascriptExecutor js = (JavascriptExecutor) driver;
                        // Obtenez la position de l'élément dans la fenêtre
                        int elementPosition = agentsLinkEdit.getLocation().getY();

                        // Défilez vers la position de l'élément, mais ajustez légèrement la position
                        js.executeScript("window.scrollBy(0, arguments[0] - 100);", elementPosition);

                        agentsLinkEdit.click();
                        // /fr/agent/860/change-password
                        WebElement agentsLinkEditPassword = driver.findElement(By.xpath("//a[@href='/fr/agent/860/change-password']"));
                        agentsLinkEditPassword.click();
                        System.out.println("Username '" + usernameRechercher + "' trouvé !");
                        usernameTrouve = true;
                        break;
                    }
                } else {
                    // Ignorer les lignes qui n'ont pas assez de cellules
                    System.out.println("Ligne sans colonnes suffisantes, ignorée.");
                }
            }

            // Si le username n'a pas été trouvé, passer à la page suivante
            if (!usernameTrouve) {
                try {
                    WebElement nextPageLink = driver.findElement(By.xpath("//*[@id=\"layout-wrapper\"]/div[2]/div/div/div[3]/div[1]/div/div/div[2]/div[2]/nav/ul/li[9]/a"));
                    // Défilement jusqu'à la fin de la page pour cliquer sur le bouton
                    JavascriptExecutor js = (JavascriptExecutor) driver;
                    js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
                    // Attente de 2 secondes avant de cliquer
                    try {
                        Thread.sleep(3000);  // Mettre en pause pour s'assurer que l'élément est visible
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Restaurer l'état d'interruption
                        System.out.println("Le thread a été interrompu.");
                    }
                    nextPageLink.click();
                    // Attendez que la page suivante se charge
                    Thread.sleep(2000);
                } catch (NoSuchElementException e) {
                    System.out.println("Fin de la pagination. Le username n'a pas été trouvé.");
                    break;
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        if (!usernameTrouve) {
            System.out.println("Le username '" + usernameRechercher + "' n'a pas été trouvé dans toutes les pages.");
        }
    }

    @Test
    public void modifierAgent() {
        try {
            // Naviguer vers la page des agents
            WebElement agentsLink = driver.findElement(By.xpath("//a[@href='/fr/agents']"));
            agentsLink.click();
            WebElement agentsLinkEdit = driver.findElement(By.xpath("//a[@href='/fr/agent/860/edit']"));

            JavascriptExecutor js = (JavascriptExecutor) driver;
            Long pageHeight = (Long) js.executeScript("return document.body.scrollHeight");
            // Obtenez la position de l'élément dans la fenêtre
            int elementPosition = agentsLinkEdit.getLocation().getY();
            // Défilez vers la position de l'élément, mais ajustez légèrement la position
            js.executeScript("window.scrollBy(0, arguments[0] - 100);", elementPosition);

            Thread.sleep(2000);
            agentsLinkEdit.click();

            // Modifier les champs du formulaire
            WebElement agentUsername = driver.findElement(By.cssSelector("#agent_form_username"));
            WebElement agentPrenom = driver.findElement(By.cssSelector("#agent_form_firstName"));
            WebElement agentNom = driver.findElement(By.cssSelector("#agent_form_lastName"));
            WebElement agentEmail = driver.findElement(By.cssSelector("#agent_form_email"));
            WebElement agentComment = driver.findElement(By.cssSelector("#agent_form_comment"));

            agentUsername.clear();
            agentPrenom.clear();
            agentNom.clear();
            agentEmail.clear();
            agentComment.clear();

            agentUsername.sendKeys("user1");
            agentNom.sendKeys("ezz");
            agentPrenom.sendKeys("yassine");
            agentEmail.sendKeys("user1@gmail.com");
            agentComment.sendKeys("This is a test comment edit");

            // Sélectionner les options du dropdown
            Select droitDropdown = new Select(driver.findElement(By.id("agent_form_profile")));
            Select langDropdown = new Select(driver.findElement(By.id("agent_form_lang")));

            droitDropdown.selectByValue("7");
            langDropdown.selectByValue("1");

            // Défilement jusqu'au milieu de la page pour cliquer sur le bouton
            Long middlePosition = ((pageHeight / 2 )+ 20);
            js.executeScript("window.scrollTo(0, arguments[0]);", middlePosition);

            // Attente de 2 secondes avant de cliquer
            Thread.sleep(2000);

            // Cliquer sur le bouton pour sauvegarder les modifications
            WebElement btnEditAgent = driver.findElement(By.xpath("//*[@id=\"layout-wrapper\"]/div[2]/div/div/div[3]/div/div/div[2]/div/div/form/input"));
            btnEditAgent.click();

            // Vérification du message de succès
            WebElement messageEditAgent = driver.findElement(By.xpath("//*[@id=\"layout-wrapper\"]/div[2]/div/div/div[2]/div/div"));
            Assert.assertNotNull(messageEditAgent, "Modification échouée, message 'success' non trouvé");
            System.out.println(messageEditAgent);

        } catch (NoSuchElementException e) {
            System.out.println("Erreur lors de la modification d'agent : Élément non trouvé.");
            Assert.fail("Modification échouée");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Réinterrompre le thread si l'attente est interrompue
        }
    }

    @AfterTest
    public void terminateBrowser() {
        driver.quit();// Fermer le navigateur à la fin du test
    }
}

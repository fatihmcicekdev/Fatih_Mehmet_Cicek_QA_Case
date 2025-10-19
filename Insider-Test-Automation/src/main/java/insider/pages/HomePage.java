package insider.pages;

import insider.base.BasePage;
import insider.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(xpath = "//a[contains(text(),'Company')]")
    private WebElement companyMenu;

    @FindBy(xpath = "//a[contains(text(),'Careers')]")
    private WebElement careersLink;

    public void navigateToHomePage() {
        driver.get(ConfigReader.getBaseUrl());
        waitForPageLoad();
        acceptCookies();
    }

    public boolean isHomePageLoaded() {
        return driver.getCurrentUrl().equals(ConfigReader.getBaseUrl());
    }

    public CareersPage navigateToCareers() {
        clickElement(companyMenu);
        clickElement(careersLink);
        return new CareersPage(driver);
    }
}

package insider.pages;

import insider.base.BasePage;
import insider.utils.ConfigReader;
import org.openqa.selenium.WebDriver;

public class QaPage extends BasePage {
    public QaPage(WebDriver driver) {
        super(driver);
    }

    public void navigateToQaPage() {
        driver.get(ConfigReader.getCareersQaUrl());
        waitForPageLoad();
    }
}

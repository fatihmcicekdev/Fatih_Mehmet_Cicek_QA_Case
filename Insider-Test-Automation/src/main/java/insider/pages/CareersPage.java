package insider.pages;

import insider.base.BasePage;
import insider.utils.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import java.util.List;

public class CareersPage extends BasePage {

    public CareersPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = ".ml-0")
    private WebElement locationsBlock;

    @FindBy(xpath = "//a[contains(text(),'See all teams')]")
    private WebElement teamsBlock;

    @FindBy(xpath = "//h2[contains(text(),'Life at Insider')]")
    private WebElement lifeAtInsiderBlock;

    @FindBy(xpath = "//a[contains(text(), 'dream job')]")
    private List<WebElement> dreamJobLinks;

    public boolean areAllBlocksDisplayed() {
        try {
            waitForElementVisible(locationsBlock);
            scrollToElement(teamsBlock);
            waitForElementVisible(teamsBlock);
            scrollToElement(lifeAtInsiderBlock);
            waitForElementVisible(lifeAtInsiderBlock);
            
            return locationsBlock.isDisplayed() &&
                    teamsBlock.isDisplayed() &&
                    lifeAtInsiderBlock.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isCareersPageUrl() {
        return driver.getCurrentUrl().equals(ConfigReader.getCareersUrl());
    }

    public String getCareersPageTitle() {
        return driver.getTitle();
    }

    public String getExpectedCareersPageTitle() {
        return ConfigReader.getPageTitleCareers();
    }

    public boolean isDreamJobLinkVisible() {
        try {
            if (dreamJobLinks.isEmpty()) {
                return false;
            }
            WebElement firstDreamJobLink = dreamJobLinks.get(0);
            scrollToElement(firstDreamJobLink);
            waitForElementVisible(firstDreamJobLink);
            return firstDreamJobLink.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public OpenPositionsPage clickDreamJobLink() {
        if (!dreamJobLinks.isEmpty()) {
            WebElement firstDreamJobLink = dreamJobLinks.get(0);
            scrollToElement(firstDreamJobLink);
            waitForElementClickable(firstDreamJobLink);
            clickElement(firstDreamJobLink);
        }
        return new OpenPositionsPage(driver);
    }
}

package insider.pages;

import insider.base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
}

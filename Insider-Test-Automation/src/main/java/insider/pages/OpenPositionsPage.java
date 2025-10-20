package insider.pages;

import insider.base.BasePage;
import insider.utils.ConfigReader;
import insider.utils.LoggerUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.interactions.Actions;

public class OpenPositionsPage extends BasePage {

    public OpenPositionsPage(WebDriver driver) {
        super(driver);
    }

    @FindBy(id = "deneme")
    private WebElement totalResultElement;

    @FindBy(xpath = "//span[@id='select2-filter-by-location-container']")
    private WebElement locationFilterContainer;

    @FindBy(xpath = "//*[@class='select2-results']")
    private WebElement dropdownResults;

    @FindBy(xpath = "//li[contains(@class, 'select2-results__option') and contains(text(), 'Istanbul, Turkiye')]")
    private WebElement istanbulOption;

    @FindBy(xpath = "//span[@id='select2-filter-by-location-container' and @title='Istanbul, Turkiye']")
    private WebElement selectedLocationLabel;

    @FindBy(xpath = "//span[@id='select2-filter-by-department-container']")
    private WebElement departmentFilterContainer;

    @FindBy(xpath = "//li[contains(@class, 'select2-results__option') and contains(text(), 'Quality Assurance')]")
    private WebElement qualityAssuranceOption;

    @FindBy(xpath = "//span[@id='select2-filter-by-department-container' and @title='Quality Assurance']")
    private WebElement selectedDepartmentLabel;

    @FindBy(xpath = "//div[contains(@class, 'position-list-item')]")
    private java.util.List<WebElement> jobListItems;

    @FindBy(xpath = "//p[@class='position-title font-weight-bold']")
    private java.util.List<WebElement> positionTitles;

    @FindBy(xpath = "//span[@class='position-department text-large font-weight-600 text-primary']")
    private java.util.List<WebElement> positionDepartments;

    @FindBy(xpath = "//div[@class='position-location text-large']")
    private java.util.List<WebElement> positionLocations;

    @FindBy(xpath = "//a[contains(@class, 'btn') and contains(text(), 'View Role')]")
    private java.util.List<WebElement> viewRoleButtons;

    public boolean isOpenPositionsPageLoaded() {
        return driver.getCurrentUrl().equals(ConfigReader.getOpenPositionsUrl());
    }

    public boolean waitForTotalResultsToLoad() {
        try {
            LoggerUtil.info("Total Result bekleniyor...");
            
            // Element görünür olana kadar bekle
            waitForElementVisible(totalResultElement);
            LoggerUtil.info("Element görünür");
            
            // totalResult'ın gerçek bir sayı olmasını bekle (NaN değil)
            for (int i = 0; i < 30; i++) {
                try {
                    String totalText = totalResultElement.getText().trim();
                    LoggerUtil.debug("Total text (" + i + "): '" + totalText + "'");
                    
                    if (!totalText.isEmpty() && !totalText.equals("NaN") && totalText.matches("\\d+")) {
                        LoggerUtil.success("Total Result yüklendi: " + totalText);
                        return true;
                    }
                } catch (Exception innerE) {
                    LoggerUtil.debug("getText hatası: " + innerE.getMessage());
                }
                
                Thread.sleep(500);
            }
            
            LoggerUtil.error("Total Result 15 saniyede yüklenemedi");
            return false;
        } catch (Exception e) {
            LoggerUtil.exception("Kritik hata", e);
            return false;
        }
    }

    public void clickLocationFilter() {
        waitForElementClickable(locationFilterContainer);
        clickElement(locationFilterContainer);
        LoggerUtil.success("Location filter tıklandı");
    }

    public boolean isLocationDropdownVisible() {
        try {
            waitForElementVisible(dropdownResults);
            boolean visible = dropdownResults.isDisplayed();
            if (visible) {
                LoggerUtil.success("Dropdown görünür");
            }
            return visible;
        } catch (Exception e) {
            LoggerUtil.error("Dropdown görünmüyor");
            return false;
        }
    }

    public void selectIstanbulLocation() {
        waitForElementClickable(istanbulOption);
        clickElement(istanbulOption);
        LoggerUtil.success("Istanbul tıklandı");
    }

    public boolean isIstanbulLocationSelected() {
        try {
            waitForElementVisible(selectedLocationLabel);
            String selectedText = selectedLocationLabel.getDomAttribute("title");
            String expectedLocation = ConfigReader.getJobLocation();
            boolean isSelected = selectedText != null && selectedText.equals(expectedLocation);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            if (isSelected) {
                LoggerUtil.success("" + expectedLocation + " seçili");
            } else {
                LoggerUtil.error("✗ Seçim başarısız, title: " + selectedText);
            }
            return isSelected;
        } catch (Exception e) {
            LoggerUtil.error("✗ Seçim kontrolü hatası: " + e.getMessage());
            return false;
        }
    }

    public void clickDepartmentFilter() {
        waitForElementClickable(departmentFilterContainer);
        clickElement(departmentFilterContainer);
        LoggerUtil.success("Department filter tıklandı");
    }

    public void selectQualityAssuranceDepartment() {
        waitForElementClickable(qualityAssuranceOption);
        clickElement(qualityAssuranceOption);
        LoggerUtil.success("Quality Assurance tıklandı");
    }

    public boolean isQualityAssuranceDepartmentSelected() {
        try {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            waitForElementVisible(selectedDepartmentLabel);
            String selectedText = selectedDepartmentLabel.getDomAttribute("title");
            String expectedDepartment = ConfigReader.getJobDepartment();
            boolean isSelected = selectedText != null && selectedText.equals(expectedDepartment);

            if (isSelected) {
                LoggerUtil.success("" + expectedDepartment + " seçili");
            } else {
                LoggerUtil.error("✗ Department seçimi başarısız, title: " + selectedText);
            }
            return isSelected;
        } catch (Exception e) {
            LoggerUtil.error("✗ Department seçim kontrolü hatası: " + e.getMessage());
            return false;
        }
    }

    public boolean verifyAllJobsMatchFilters() {
        try {
            LoggerUtil.info("Filtrelenmiş job kartlarının yüklenmesi bekleniyor...");
            
            // Job kartlarının yeniden yüklenmesi için bekle (filtreleme sonrası)
            Thread.sleep(4000);
            
            LoggerUtil.info("Job kartları kontrol ediliyor...");
            
            if (jobListItems.isEmpty()) {
                LoggerUtil.error("Hiç job kartı bulunamadı");
                return false;
            }
            
            int totalJobs = jobListItems.size();
            LoggerUtil.info("Toplam " + totalJobs + " job kartı kontrol ediliyor");
            
            // Config'den beklenen değerleri al
            String expectedDepartment = ConfigReader.getJobDepartment();
            String expectedLocation = ConfigReader.getJobLocation();
            
            // Her job kartını kontrol et
            for (int i = 0; i < positionDepartments.size(); i++) {
                String department = positionDepartments.get(i).getText().trim();
                String location = positionLocations.get(i).getText().trim();
                String position = positionTitles.get(i).getText().trim();
                
                LoggerUtil.info("→ Job " + (i+1) + ": " + position);
                LoggerUtil.info("  - Department: " + department);
                LoggerUtil.info("  - Location: " + location);
                
                if (!department.equals(expectedDepartment)) {
                    LoggerUtil.error("✗ Yanlış department: " + department);
                    return false;
                }
                
                if (!location.equals(expectedLocation)) {
                    LoggerUtil.error("✗ Yanlış location: " + location);
                    return false;
                }
            }
            
            LoggerUtil.success("Tüm job kartları filtrelere uygun (" + totalJobs + " pozisyon)");
            return true;
            
        } catch (Exception e) {
            LoggerUtil.error("✗ Job verification hatası: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void clickFirstViewRoleButton() {
        try {
            if (jobListItems.isEmpty() || viewRoleButtons.isEmpty()) {
                LoggerUtil.error("Job kartı veya View Role butonu bulunamadı");
                return;
            }
            
            // İlk job kartına scroll yap
            WebElement firstJobCard = jobListItems.get(0);
            scrollToElement(firstJobCard);
            LoggerUtil.info("İlk job kartına scroll yapıldı");
            
            // Hover yap (View Role butonunu görünür yap)
            Actions actions = new Actions(driver);
            actions.moveToElement(firstJobCard).perform();
            LoggerUtil.success("Job kartına hover yapıldı");
            
            // Hover efektinin tam yüklenmesi için bekle
            Thread.sleep(2000);
            
            // View Role butonuna JavaScript ile tıkla (hover'dan sonra daha güvenilir)
            WebElement viewRoleBtn = viewRoleButtons.get(0);
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", viewRoleBtn);
            LoggerUtil.success("View Role butonuna tıklandı (JavaScript)");
            
        } catch (Exception e) {
            LoggerUtil.error("✗ View Role tıklama hatası: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean switchToLeverJobPage() {
        try {
            LoggerUtil.info("Yeni sekmeye geçiliyor...");
            
            String originalWindow = driver.getWindowHandle();
            LoggerUtil.info("→ Orijinal sekme: " + originalWindow);
            
            // Yeni sekmenin açılması için bekle (max 10 saniye)
            for (int i = 0; i < 20; i++) {
                java.util.Set<String> windowHandles = driver.getWindowHandles();
                if (windowHandles.size() > 1) {
                    LoggerUtil.success("Yeni sekme açıldı (Toplam: " + windowHandles.size() + " sekme)");
                    break;
                }
                Thread.sleep(500);
                LoggerUtil.info("Yeni sekme bekleniyor... (" + (i+1) + "/20)");
            }
            
            // Tüm açık sekmeleri al
            java.util.Set<String> windowHandles = driver.getWindowHandles();
            
            if (windowHandles.size() <= 1) {
                LoggerUtil.error("Yeni sekme açılmadı!");
                return false;
            }
            
            // Yeni sekmeye geç
            for (String windowHandle : windowHandles) {
                if (!windowHandle.equals(originalWindow)) {
                    driver.switchTo().window(windowHandle);
                    LoggerUtil.info("→ Yeni sekmeye geçildi: " + windowHandle);
                    break;
                }
            }
            
            String currentUrl = driver.getCurrentUrl();
            LoggerUtil.info("→ Yeni sekme URL: " + currentUrl);
            
            // URL'in Lever.co içerdiğini kontrol et
            String expectedUrlPattern = ConfigReader.getLeverJobUrlPattern();
            if (currentUrl.contains(expectedUrlPattern)) {
                LoggerUtil.success("Lever.co job sayfasına yönlendirildi");
                return true;
            } else {
                LoggerUtil.error("✗ Yanlış URL: " + currentUrl);
                return false;
            }
            
        } catch (Exception e) {
            LoggerUtil.error("✗ Sekme geçişi hatası: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}


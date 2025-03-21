package pages;

import common.AbsCommon;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public abstract class AbsBasePage extends AbsCommon {
    private final String BASE_URL;

    public AbsBasePage(WebDriver driver) {
        super(driver);
        this.BASE_URL = System.getProperty("base.url", "https://otus.ru");
    }

    public void cookieAccess() {
        logger.info("Начало работы с куками.");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.localStorage.setItem('cookieAccess', 'true');");
        js.executeScript("window.localStorage.setItem('cookieAccept', 'true');");
        logger.info("Куки установлены, перезагрузка страницы.");
        js.executeScript("location.reload();");

        // Вместо Thread.sleep используем ожидание определённого состояния (например, появления элемента)
        try {
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.jsReturnsValue("return document.readyState=='complete'"));
            logger.info("Страница загружена после перезагрузки.");
        } catch (Exception e) {
            logger.error("Ошибка ожидания загрузки страницы: {}", e.getMessage());
            throw e;
        }
    }

    public void open(String path) {
        String fullUrl = BASE_URL + path;
        logger.info("Открытие страницы: {}", fullUrl);
        driver.get(fullUrl);
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.urlToBe(fullUrl));
        logger.info("Страница {} успешно загружена.", fullUrl);
    }

    public List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }
}

package pages;

import data.CourseData;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;
import java.util.List;

public class CatalogTestingPage extends AbsBasePage {
    private final String PAGE_URL = "/categories/testing/";
    private final int numberOfCourses = 11;
    private final String allCounts = "div.sc-18q05a6-1.bwGwUO [class^=\"sc-zzdkm7-0\"]";
    private final String showMoreButton = "button.sc-prqxfo-0";

    public CatalogTestingPage(WebDriver driver) {
        super(driver);
    }

    public void openPage() {
        logger.info("Открытие страницы каталога тестирования.");
        open(PAGE_URL);
    }

    public void clickShowMoreButton() {
        By showMoreButtonLocator = By.cssSelector(showMoreButton);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        boolean buttonClickedAtLeastOnce = false;
        try {
            while (true) {
                logger.info("Ожидание кнопки 'Показать еще'.");
                WebElement showMoreButtonElement = wait.until(ExpectedConditions.elementToBeClickable(showMoreButtonLocator));
                Assert.assertNotNull(showMoreButtonElement, "Кнопка 'Показать еще' не найдена.");
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", showMoreButtonElement);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", showMoreButtonElement);
                logger.info("Нажата кнопка 'Показать еще'.");
                buttonClickedAtLeastOnce = true;
                // Ждем обновления страницы и проверяем отображение кнопки
                try {
                    WebElement refreshedButton = driver.findElement(showMoreButtonLocator);
                    if (!refreshedButton.isDisplayed()) {
                        logger.info("Кнопка 'Показать еще' больше не отображается.");
                        break;
                    }
                } catch (NoSuchElementException e) {
                    logger.info("Кнопка 'Показать еще' отсутствует на странице.");
                    break;
                }
            }
            Assert.assertTrue(buttonClickedAtLeastOnce, "Кнопка 'Показать еще' не была нажата ни разу.");
        } catch (TimeoutException e) {
            logger.error("Таймаут ожидания кнопки 'Показать еще': {}", e.getMessage());
            Assert.fail("Не удалось найти кнопку 'Показать еще' за установленное время.");
        } catch (Exception e) {
            logger.error("Ошибка при нажатии кнопки 'Показать еще': {}", e.getMessage());
            Assert.fail("Непредвиденная ошибка: " + e.getMessage());
        }
    }

    public void assertCount(By locator, int expectedCount) {
        List<WebElement> elements = findElements(locator);
        int actualCount = elements.size();
        logger.info("Проверка количества элементов: ожидаемое - {}, фактическое - {}", expectedCount, actualCount);
        Assert.assertEquals(actualCount, expectedCount, "Количество элементов не соответствует ожидаемому");
    }

    public void countOfCourses() {
        logger.info("Начало проверки количества курсов.");
        assertCount(By.cssSelector(allCounts), numberOfCourses);
        logger.info("Количество курсов ({}) соответствует ожидаемому.", numberOfCourses);
    }

    public void clickElementByIndex(int index) {
        List<WebElement> elements = findElements(By.cssSelector(allCounts));
        logger.info("Найдено {} элементов для взаимодействия.", elements.size());
        Assert.assertTrue(index >= 0 && index < elements.size(), "Индекс " + index + " вне диапазона допустимых значений.");
        WebElement element = elements.get(index);
        logger.info("Прокрутка к элементу с индексом {}.", index);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
        element.click();
        logger.info("Элемент с индексом {} успешно нажат.", index);
    }

    public void checkText(By selector, String expectedValue) {
        logger.info("Проверка текста элемента по селектору '{}'", selector);
        WebElement element = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(selector));
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
        String actualText = element.getText().trim();
        Assert.assertEquals(actualText, expectedValue, "Текст элемента не соответствует ожидаемому");
        logger.info("Текст элемента соответствует ожидаемому значению \"{}\"", expectedValue);
    }
}

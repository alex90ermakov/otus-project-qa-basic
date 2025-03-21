package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static org.testng.Assert.*;

public class EventsPage {

    private WebDriver driver;
    private WebDriverWait wait;
    private static final Logger logger = LogManager.getLogger(EventsPage.class);

    private final By educationButton = By.xpath("//span[contains(text(), 'Обучение')]");
    private final By calendarButton = By.xpath("//a[contains(text(), 'Календарь мероприятий')]");
    private final By allEventsButton = By.xpath("//span[contains(text(), 'Все мероприятия')]");
    private final By openWebinarButton = By.xpath("//a[contains(text(), 'Открытый вебинар')]");
    private final By eventCards = By.xpath("//a[contains(@class, 'dod_new-event')]");
    private final By eventType = By.xpath("//div[contains(@class, 'dod_new-event__type')]");
    private final By eventDate = By.xpath("//span[@class='dod_new-event__date-text']");

    private static final DateTimeFormatter dateFormatter =
            DateTimeFormatter.ofPattern("d MMMM yyyy", new Locale("ru"));

    public EventsPage(WebDriver driver) {
        this.driver = driver;
        Duration duration = Duration.ofSeconds(5);
        this.wait = new WebDriverWait(driver, duration);
    }


    public void openMainPage(String url) {
        logger.info("Открытие главной страницы: {}", url);
        driver.get(url);
        assertEquals(driver.getCurrentUrl(), url, "Главная страница не открылась");
    }

    public void clickEducationButton() {
        logger.info("Нажатие на кнопку 'Обучение'");
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(educationButton));
        button.click();
    }

    public void clickCalendarButton() {
        logger.info("Нажатие на кнопку 'Календарь мероприятий'");
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(calendarButton));
        button.click();
    }

    public void clickAllEventsButton() {
        logger.info("Нажатие на кнопку 'Все мероприятия'");
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(allEventsButton));
        button.click();
    }

    public void clickOpenWebinarButton() {
        logger.info("Нажатие на кнопку 'Открытый вебинар'");
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(openWebinarButton));
        button.click();
    }

    public void scrollToBottom() {
        logger.info("Скроллинг страницы вниз для загрузки всех мероприятий");
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long lastHeight = (long) js.executeScript("return document.body.scrollHeight");

        while (true) {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                logger.error("Ошибка во время ожидания скроллинга: {}", e.getMessage());
            }
            long newHeight = (long) js.executeScript("return document.body.scrollHeight");
            if (newHeight == lastHeight) {
                break;
            }
            lastHeight = newHeight;
        }
    }

    public void validateEventType() {
        logger.info("Проверка типа мероприятий");
        List<WebElement> events = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(eventCards));
        assertFalse(events.isEmpty(), "Список мероприятий пуст");

        for (WebElement event : events) {
            String eventTypeText = event.findElement(eventType).getText().trim();
            logger.info("Тип мероприятия: {}", eventTypeText);
            assertEquals(eventTypeText, "Открытый вебинар", "Некорректный тип мероприятия");
        }
    }

    public void validateEventDates() {
        logger.info("Проверка дат мероприятий");
        List<WebElement> events = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(eventCards));
        assertFalse(events.isEmpty(), "Список мероприятий пуст");

        LocalDate currentDate = LocalDate.now();

        for (WebElement event : events) {
            try {
                String dateText = event.findElement(eventDate).getText().trim();
                LocalDate parsedDate = parseDate(dateText);
                assertTrue(parsedDate.isEqual(currentDate) || parsedDate.isAfter(currentDate),
                        "Дата мероприятия устарела: " + parsedDate);
            } catch (Exception e) {
                logger.error("Ошибка при обработке даты мероприятия: {}", e.getMessage());
                assertTrue(false, "Ошибка валидации даты мероприятия");
            }
        }
        logger.info("Проверка дат мероприятий завершена успешно");
    }

    private LocalDate parseDate(String dateText) {
        int currentYear = LocalDate.now().getYear();
        String fullDateText = dateText + " " + currentYear;
        return LocalDate.parse(fullDateText, dateFormatter);
    }

//    public void validateEventDates() {
//        logger.info("Проверка дат мероприятий");
//        List<WebElement> events = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(eventCards));
//        assertTrue(!events.isEmpty(), "Список мероприятий пуст");
//        List<WebElement> dateElements = driver.findElements(By.xpath(String.valueOf(eventDate)));
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");// Формат: "25 ноября 2024"
//        LocalDate currentDate = LocalDate.now();// Получаем текущую дату
//        logger.info("Текущая дата: " + currentDate);
//
//        for (int i = 0; i < dateElements.size(); i += 2) {
//            WebElement dateElement = dateElements.get(i);
//            String dateText = dateElement.getText().trim();// Получаем только дату
//            LocalDate eventDate = parseDate(dateText, dateFormatter);// Преобразуем в формат d MMMM yyyy
//            if (eventDate == null || eventDate.isBefore(currentDate)) {
//                logger.info("Ошибка: Элемент с индексом " + (i + 1) + " содержит дату '" + dateText + "', которая раньше сегодняшней: '" + currentDate + "'.");
//                Assert.fail("Дата элемента на индексе " + (i + 1) + " '" + dateText + "' меньше текущей даты '" + currentDate + "'.");
//            }
//        }
//        logger.info("Все даты на плитках не старше сегодняшней.");
//    }
//
//    private LocalDate parseDate(String dateText, DateTimeFormatter formatter) {
//        try {
//            int currentYear = LocalDate.now().getYear();// Получаем текущий год
//            String fullDateText = dateText + " " + currentYear;// Добавляем год к дате
//            LocalDate parsedDate = LocalDate.parse(fullDateText, formatter);// Парсим в гггг-мм-дд
//            return parsedDate;
//        } catch (Exception e) {
//            logger.info("Ошибка при парсинге даты: " + dateText);
//            return null;
//        }
//    }
}
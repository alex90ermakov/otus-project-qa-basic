import factory.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.EventsPage;

public class EventsTest {

    private WebDriver driver;
    private EventsPage eventsPage;

    @BeforeMethod
    public void setUp() {
        this.driver = WebDriverFactory.create();
        driver.manage().window().maximize();
        eventsPage = new EventsPage(driver);
        eventsPage.openMainPage();
        eventsPage.clickEducationButton();
        eventsPage.clickCalendarButton();
        eventsPage.clickAllEventsButton();
        eventsPage.clickOpenWebinarButton();
    }

    @Test
    public void testValidateEventType() {
        eventsPage.scrollToBottom();
        eventsPage.validateEventType();
    }

    @Test
    public void testValidateEventDates() {
        eventsPage.scrollToBottom();
        eventsPage.validateEventDates();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
import data.CourseData;
import data.ForTestingCoursesData;
import factory.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.CatalogTestingPage;

public class CatalogTestingTest {

    private WebDriver driver;
    private CatalogTestingPage onPage;

    @BeforeMethod
    public void setUp() {
        this.driver = WebDriverFactory.create();
        this.onPage = new CatalogTestingPage(driver);
        onPage.openPage();
        onPage.cookieAccess();
    }

    @AfterMethod
    public void tearDown() {
        if (this.driver != null) {
            this.driver.quit();
        }
    }

    @Test(priority = 1)
    public void checkingNumberOfCourses() {
        onPage.clickShowMoreButton();
        onPage.countOfCourses();
    }

    @Test(priority = 2, dataProvider = "courseIndices")
    public void checkingContentsOfCourseCard(int index) {
//        onPage.clickShowMoreButton();
        onPage.clickElementByIndex(index);

        CourseData course = ForTestingCoursesData.getCourseData(index);
        onPage.checkText(course.getNameSelector(), course.getName());
        onPage.checkText(course.getDescriptionSelector(), course.getDescription());
        onPage.checkText(course.getDurationSelector(), course.getDuration());
        onPage.checkText(course.getFormatSelector(), course.getFormat());
    }

    @DataProvider(name = "courseIndices")
    public Object[][] courseIndices() {
        return new Object[][] { {3} };
    }
}

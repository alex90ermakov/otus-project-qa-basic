package data;

import org.openqa.selenium.By;
import java.util.List;

public class ForTestingCoursesData {
    public static final List<CourseData> COURSES = List.of(
            new CourseData(
                    By.cssSelector("h1.sc-1x9oq14-0"),
                    "QA Automation Engineer",
                    By.cssSelector("div.sc-1x9oq14-0:nth-child(4)"),
                    "Идеальная точка входа в тестирование на Java",
                    By.cssSelector("div.sc-3cb1l3-4:nth-child(3) > p:nth-child(2)"),
                    "10 месяцев",
                    By.cssSelector("div.sc-3cb1l3-4:nth-child(4) > p:nth-child(2)"),
                    "Онлайн"
            ),
            new CourseData(
                    By.cssSelector("h1.sc-1x9oq14-0"),
                    "Java QA Engineer. Basic",
                    By.cssSelector(".sc-s2pydo-3"),
                    "Курс по автоматизации тестирования на Java для начинающих: синтаксис Java, автотесты для UI и API, фреймворки",
                    By.cssSelector("div.sc-3cb1l3-4:nth-child(3) > p:nth-child(2)"),
                    "5 месяцев",
                    By.cssSelector("div.sc-3cb1l3-4:nth-child(4) > p:nth-child(2)"),
                    "Онлайн"
            ),
            new CourseData(
                    By.cssSelector("h1.sc-1x9oq14-0"),
                    "Game QA Engineer",
                    By.cssSelector(".sc-s2pydo-3 > p:nth-child(1)"),
                    "Научитесь с нуля тестировать игры на платформах:\n" +
                            "iOS, Android, PlayStation, Xbox, Switch и PC",
                    By.cssSelector("div.sc-3cb1l3-4:nth-child(3) > p:nth-child(2)"),
                    "4 месяца",
                    By.cssSelector("div.sc-3cb1l3-4:nth-child(4) > p:nth-child(2)"),
                    "Онлайн"
            ),
            new CourseData(
                    By.cssSelector("h1.sc-1x9oq14-0"),
                    "Java QA Engineer. Professional",
                    By.cssSelector(".sc-s2pydo-3"),
                    "Курс по автоматизированному тестированию на Java: продвинутые инструменты, новые карьерные возможности",
                    By.cssSelector("div.sc-3cb1l3-4:nth-child(3) > p:nth-child(2)"),
                    "4 месяца",
                    By.cssSelector("div.sc-3cb1l3-4:nth-child(4) > p:nth-child(2)"),
                    "Онлайн"
            )
    );

    public static CourseData getCourseData(int index) {
        return COURSES.get(index);
    }
}

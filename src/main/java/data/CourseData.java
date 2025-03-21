package data;

import org.openqa.selenium.By;

public class CourseData {
    private By nameSelector;
    private String name;
    private By descriptionSelector;
    private String description;
    private By durationSelector;
    private String duration;
    private By formatSelector;
    private String format;

    public CourseData(By nameSelector, String name, By descriptionSelector, String description,
                      By durationSelector, String duration, By formatSelector, String format) {
        this.nameSelector = nameSelector;
        this.name = name;
        this.descriptionSelector = descriptionSelector;
        this.description = description;
        this.durationSelector = durationSelector;
        this.duration = duration;
        this.formatSelector = formatSelector;
        this.format = format;
    }

    public By getNameSelector() {
        return nameSelector;
    }

    public String getName() {
        return name;
    }

    public By getDescriptionSelector() {
        return descriptionSelector;
    }

    public String getDescription() {
        return description;
    }

    public By getDurationSelector() {
        return durationSelector;
    }

    public String getDuration() {
        return duration;
    }

    public By getFormatSelector() {
        return formatSelector;
    }

    public String getFormat() {
        return format;
    }
}

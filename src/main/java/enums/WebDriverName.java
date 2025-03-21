package enums;

import exception.BrowserNotFoundException;

public enum WebDriverName {
    CHROME,
    FIREFOX,
    EDGE;

    public static WebDriverName fromSystemProperty() {
        String browserName = System.getProperty("browser", "firefox").toUpperCase();
        try {
            return WebDriverName.valueOf(browserName);
        } catch (IllegalArgumentException e) {
            throw new BrowserNotFoundException("Неизвестный броузер: " + browserName);
        }
    }
}

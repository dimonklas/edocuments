package utils;

import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

@Log4j
public class Utils {

    private final IConfigurationVariables CV = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());

    public static String generateRandomFloatNumber(int numbersAfterPoint) {
        return String.format("%." + numbersAfterPoint + "f", (Math.random() * 100 + 10)).replace(",", ".");
    }

    public static void clearField(SelenideElement element) {
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.chord(Keys.BACK_SPACE));
    }

    public static boolean isAlertPresent() {
        try {
            switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    public static void waitPreloader() {
        $(By.xpath("//*[contains(@class,'spinner')]")).waitUntil(not(visible), 15000);
    }
}

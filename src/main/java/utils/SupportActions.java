package utils;

import com.codeborne.selenide.SelenideElement;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.sleep;

@Log4j
public class SupportActions {

    public static String generateRandomFloatNumber(int numbersAfterPoint) {
        return String.format("%." + numbersAfterPoint + "f", (Math.random()*100+10)).replace(",", ".");
    }

    public static void clearField(SelenideElement element) {
        element.sendKeys(Keys.chord(Keys.CONTROL, "a"));
        element.sendKeys(Keys.chord(Keys.BACK_SPACE));

//        int length = element.getValue().length();
//        element.click();
//        element.sendKeys(Keys.chord(Keys.END));
//        for (int i = 0; i < length; i++) {
//            element.sendKeys(Keys.chord(Keys.BACK_SPACE));
//        }
    }
}

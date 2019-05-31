package utils;

import com.codeborne.selenide.SelenideElement;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.extern.log4j.Log4j;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import utils.sid.ProminSession;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.switchTo;

@Log4j
public class SupportActions {

    private final IConfigurationVariables CV = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());

    public static String generateRandomFloatNumber(int numbersAfterPoint) {
        return String.format("%." + numbersAfterPoint + "f", (Math.random() * 100 + 10)).replace(",", ".");
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

    public static RequestSpecification getRequestSpec() {
        String url = ConfigFactory.create(IConfigurationVariables.class, System.getProperties()).urlApiTest();
        return new RequestSpecBuilder()
                .setBaseUri(url)
                .addQueryParam("token", new ProminSession().getAdminSession())
//                .setContentType("application/x-www-form-urlencoded")
                .build();
    }
}

package utils;

import com.codeborne.selenide.WebDriverRunner;
import lombok.Getter;

import java.util.HashSet;

import static com.codeborne.selenide.Selenide.sleep;

@Getter
public class Cookie {

    public static String phpsessid;
    public static String csrfToken;


    public static void setCookie() {
        sleep(2000);
        phpsessid = WebDriverRunner.getWebDriver().manage().getCookieNamed("PHPSESSID").getValue();
        csrfToken = WebDriverRunner.getWebDriver().manage().getCookieNamed("CSRF-TOKEN").getValue();
    }
}

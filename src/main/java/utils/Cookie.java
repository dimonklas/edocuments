package utils;

import com.codeborne.selenide.WebDriverRunner;
import lombok.Getter;
import lombok.extern.log4j.Log4j;


import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.Selenide.sleep;

@Getter
@Log4j
public class Cookie {

    public static String phpSessId;
    public static String csrfToken;

    private static int count = 0;


    public static void getBrowserCookies() {
        if (count < 5) {
            try {
                sleep(1000);
                phpSessId = WebDriverRunner.getWebDriver().manage().getCookieNamed("PHPSESSID").getValue();
                csrfToken = WebDriverRunner.getWebDriver().manage().getCookieNamed("CSRF-TOKEN").getValue();
            } catch (NullPointerException e) {
                log.info("####################NullPointerException#################");
                refresh();
                count++;
                getBrowserCookies();
            }
        }
    }
}
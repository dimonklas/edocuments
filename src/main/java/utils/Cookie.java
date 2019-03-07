package utils;

import com.codeborne.selenide.WebDriverRunner;
import lombok.Getter;


import static com.codeborne.selenide.Selenide.refresh;
import static com.codeborne.selenide.Selenide.sleep;

@Getter
public class Cookie {

    public static String phpSessId;
    public static String csrfToken;

    private static int count = 0;


    public static void setCookie() {
        if (count < 10) {
            try {
                sleep(1000);
                phpSessId = WebDriverRunner.getWebDriver().manage().getCookieNamed("PHPSESSID").getValue();
                csrfToken = WebDriverRunner.getWebDriver().manage().getCookieNamed("CSRF-TOKEN").getValue();
            } catch (NullPointerException e) {
                System.out.println("####################NullPointerException#################");
                refresh();
                count++;
                setCookie();
            }
        }
    }
}
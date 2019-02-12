package utils;

import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.WebDriverRunner.url;

public interface ICustomWait {

    default boolean isWaitUntilUrlContains (String urlString, long wait) {
        boolean flag = true;
        long maxTime = 0;
        boolean resultContainsUrl = false;

        while (!resultContainsUrl || !flag) {
            if (maxTime > wait) break;

            sleep(500);
            resultContainsUrl = url().contains(urlString);
            maxTime += 500;
        }
        return resultContainsUrl;
    }
}

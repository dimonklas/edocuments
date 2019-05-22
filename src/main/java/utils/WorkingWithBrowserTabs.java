package utils;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j;

import static com.codeborne.selenide.Selenide.sleep;
import static com.codeborne.selenide.Selenide.switchTo;

@Log4j
public class WorkingWithBrowserTabs {

    @Step("Закрыть вкладку {nameTittle}: и переключиться на основную")
    public static void closeBrowserTab(String nameTittle) {
        sleep(500);
        if (WebDriverRunner.getWebDriver().getWindowHandles().size() > 1) {
            switchTo().window(nameTittle).close();
            switchTo().window(0);
        } else log.info("Вкладка не открылась");
    }
}

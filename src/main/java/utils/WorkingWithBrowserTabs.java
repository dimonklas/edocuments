package utils;

import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.switchTo;

public interface WorkingWithBrowserTabs {

    @Step("Закрыть вкладку {nameTitle}: и переключиться на основную")
    default void closeBrowserTab(String nameTittle) {
        switchTo().window(nameTittle).close();
        switchTo().window(0);
    }
}

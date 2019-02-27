package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import utils.WorkingWithBrowserTabs;


import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertTrue;

@Getter
@Log4j
public class DropDownListPage implements WorkingWithBrowserTabs {
    private SelenideElement searchField = $(By.id("search"));
    private SelenideElement createButton = $(By.id("btn_create"));
    private SelenideElement viewDropDownList = $(By.id("btn_view"));
    private SelenideElement deleteButton = $(By.id("btn_delete"));
    private SelenideElement acceptAlertButton = $(By.id("btn_yes"));


    @Step("Перейти на создание нового Выпадающего списка")
    public CreateDropDownListPage openCreateNewDropDownList() {
        createButton.shouldBe(Condition.visible).click();
        closeBrowserTab("Выпадающие списки");
        return new CreateDropDownListPage();
    }

    @Step("Проверка поиска по значению \"{value}\"")
    public CreateDropDownListPage searchAndOpenDocument(String value) {
        refresh();
        searchField.shouldBe(visible).sendKeys(value);
        $(By.xpath("//tbody//tr")).click();
        viewDropDownList.click();
        closeBrowserTab("Выпадающие списки");
        return new CreateDropDownListPage();
    }

    @Step("Удаление всех документов по значению \"{valueForDelete}\"")
    public void deleteAllDocument(String valueForDelete) {
        searchField.shouldBe(visible).setValue(valueForDelete);
        int count =  $$(By.xpath("//tbody//tr[@role='row']/td[contains(.,'" + valueForDelete + "')]")).size();
        for (int i = 0; i < count; i++) {
            $(By.xpath("//tbody//tr[@role='row']/td[contains(.,'" + valueForDelete + "')]")).click();
            deleteButton.shouldBe(visible).click();
            acceptAlertButton.shouldBe(visible).click();
            sleep(1000);
        }
        assertTrue($(By.xpath("//*[@id='lists_table']//td[text()='Записи отсутствуют.']")).exists());
    }
}

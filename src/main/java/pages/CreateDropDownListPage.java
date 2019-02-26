package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import pages.document.dropDownListData.DropDownListData;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertEquals;

@Log4j
@Getter
public class CreateDropDownListPage {
    private SelenideElement listName = $(By.id("list_name"));
    private SelenideElement saveButton = $(By.id("btn_save"));
    private SelenideElement deleteButton = $(By.id("btn_delete"));
    private SelenideElement cancelButton = $(By.id("btn_cancel"));
    private SelenideElement elementAdd = $(By.id("btn_element_add"));
    private SelenideElement elementDelete = $(By.id("btn_element_delete"));
    private SelenideElement modalConfirmWindow = $(By.className("modal-header"));
    private SelenideElement closeConfirmWindow = $(By.id("btn_close_red"));
    private SelenideElement linkOnHeader = $(By.id("section_title"));

    @Step("Создание нового выпадающего списка")
    public void createNewDropDownList(DropDownListData listData) {
        listName.shouldBe(Condition.visible).setValue(listData.getName());

        if (listData.getElementListData().size() > 0) {
            for (int i = 0; i < listData.getElementListData().size(); i++) {
                elementAdd.shouldBe(Condition.visible).click();
                $$(By.name("element_key")).last().setValue(listData.getElementListData().get(i).getKey());
                $$(By.name("element_value")).last().setValue(listData.getElementListData().get(i).getValue());
            }
        }
    }

    @Step("Сохранить новый выпадающий список")
    public String saveCurrentDocAndReturnId() {
        saveButton.shouldBe(visible).click();
        sleep(1000);

        if (modalConfirmWindow.is(visible)) {
            closeConfirmWindow.shouldBe(visible).click();
            goToMainPage();
//            confirm();
            return "Ошибка";
        } else return $(By.id("page_title")).getText().replaceAll("\\D+", "");
    }

    @Step("Переход на главную страницу")
    public MainPage goToMainPage() {
        linkOnHeader.shouldBe(visible).click();
        return new MainPage();
    }

    @Step("Проверка выпадающего списка")
    public void checkDropDownList(DropDownListData listData, String idList) {
        assertEquals(idList, $(By.id("list_id")).shouldBe(visible).getValue(), "Неверный ID у выпадающего списка");
        assertEquals(listData.getName(), listName.shouldBe(visible).getValue(), "Неверное наименование у выпадающего списка");

        int xpathValue = 1;
        for (int i = 0; i < listData.getElementListData().size(); i++) {

            assertEquals(listData.getElementListData().get(i).getKey(), $x("(//*[@name='element_key'])[" + xpathValue + "]").getValue(), "Неверный ключ у "+xpathValue+"-го элемента");
            assertEquals(listData.getElementListData().get(i).getValue(), $x("(//*[@name='element_value'])[" + xpathValue + "]").getValue(), "Неверное значение у "+xpathValue+"-го элемента");

            xpathValue++;
        }
    }
}

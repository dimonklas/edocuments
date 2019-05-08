package pages;


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import pages.document.FormFields;

import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

@Log4j
@Getter
public class GeneralDataTypesPage {

    private SelenideElement linkOnHeader = $(By.id("section_title"));
    private SelenideElement searchField = $(By.id("search"));


    @Step("Переход на главную страницу")
    public MainPage goToMainPage() {
        linkOnHeader.shouldBe(visible).click();
        return new MainPage();
    }

    @Step("Поиск типа данных по значению: \"{value}\"")
    public void searchAndOpenDataType(String value) {
        searchField.shouldBe(Condition.visible).clear();
        searchField.shouldBe(Condition.visible).setValue(value);

        $x("//*[@id='data_types_table']//td[text()='" + value + "']").shouldBe(Condition.visible).click();
        $x("//*[@id='data_types_table']//td[text()='" + value + "']").shouldBe(Condition.visible).click();
    }

    @Step("Редактирование типов данных")
    public void editDataTypes(List<FormFields> fields) {
        for (int i = 0; i < fields.size(); i++) {
            searchAndOpenDataType(fields.get(i).getDataType());
            $x("//*[@id='data_types_table']//td[text()='" + fields.get(i).getDataType() + "']/../..//*[@class='btn_edit_field_type']").shouldBe(Condition.visible).click();
            $x("//*[@id='data_types_table']//td[text()='" + fields.get(i).getDataType() + "']/../..//select[not(@hidden='hidden')]").selectOption(fields.get(i).getFieldType());
            if (fields.get(i).getFieldType().equals("Список"))
                $x("(//*[@id='data_types_table']//td[text()='" + fields.get(i).getDataType() + "']/../..//select[not(@hidden='hidden')])[2]").selectOption("STATOBL");
            $x("//*[@id='data_types_table']//td[text()='" + fields.get(i).getDataType() + "']/../..//*[@class='btn_save_field_type']").shouldBe(Condition.visible).click();
        }
    }

}

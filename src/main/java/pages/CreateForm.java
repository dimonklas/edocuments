package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.By;
import pages.document.FormDataAbstract;
import utils.IConfigurationVariables;

import java.io.File;
import org.apache.commons.io.FilenameUtils;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.sleep;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CreateForm {

    private final IConfigurationVariables CV = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());

    private SelenideElement uploadFileButton = $(By.id("btn_xsd_upload"));
    private SelenideElement uploadFileInput = $(By.id("input_file_xsd"));
    private SelenideElement unloadFileButton = $(By.id("btn_xsd_unload"));
    private SelenideElement editFormButton = $(By.id("btn_edit"));
    private SelenideElement saveFormButton = $(By.id("btn_save"));
    private SelenideElement cancelFormButton = $(By.id("btn_cancel"));
    private SelenideElement deleteFormButton = $(By.id("btn_delete"));
    private SelenideElement codeFormInput = $(By.id("form_code"));
    private SelenideElement descriptionFormInput = $(By.id("form_description"));
    private SelenideElement searchFieldInput = $(By.id("field_search"));
    private SelenideElement onlyNotUsedFildsCheckBox = $(By.className("custom-control custom-checkbox"));
    private SelenideElement modalWindowError = $x("//div[@class='modal-content' and contains(.,'Ошибка')]");
    private SelenideElement linkOnHeader = $(By.id("section_title"));


    @Step("Загрузка файла формы")
    public boolean uploadFile(String file){
        uploadFileButton.shouldBe(visible);
        String fileName = FilenameUtils.removeExtension(new File("src/main/resources/supportFiles/" + file).getName());
        uploadFileInput.uploadFile(new File("src/main/resources/supportFiles/" + file).getAbsoluteFile());
        sleep(2000);
        if (modalWindowError.isDisplayed()){
            $x("//a[contains(.,\"Перейти в форму '" + fileName + "'\")]").shouldBe(Condition.visible).click();
            return false;
        } else return true;
    }

    @Step("Переход на главную страницу")
    public MainPage goToMainPage() {
        linkOnHeader.shouldBe(visible).click();
        return new MainPage();
    }


    @Step("Заполнение формы")
    public void setValuesToForm(){
        editFormButton.shouldBe(visible).click();
    }

    @Step("Проверка формы")
    public void checkForm(FormDataAbstract form) {
        unloadFileButton.shouldBe(visible);
        int xpathValue = 1;
        assertEquals(codeFormInput.getValue(), form.getCODE(), "Код формы неправильный");
        assertEquals(descriptionFormInput.getValue(), form.getDESCRIPTION(), "Наименование формы неправильное");

        for (int i = 0; i < form.getFields().size(); i++) {
            assertEquals($x("//table[@id='fields_table']//tbody//tr[" + xpathValue + "]/td[2]").getText(), (form.getFields().get(i).getCodeField()), "Код поля неправильный");
            assertEquals($x("//table[@id='fields_table']//tbody//tr[" + xpathValue + "]/td[3]").getText(), (form.getFields().get(i).getDataType()), "Тип данных неправильный");
            assertEquals($x("//table[@id='fields_table']//tbody//tr[" + xpathValue + "]/td[4]").getText(), (form.getFields().get(i).getFieldType()), "Тип поля неправильный");
            assertEquals($x("//table[@id='fields_table']//tbody//tr[" + xpathValue + "]/td[5]").getText(), (form.getFields().get(i).getTabNumber()), "№ таблицы неправильный");
            assertEquals($x("//table[@id='fields_table']//tbody//tr[" + xpathValue + "]/td[6]").getText(), (form.getFields().get(i).getFieldsGroup()), "Группа полей неправильный");
            assertEquals($x("//table[@id='fields_table']//tbody//tr[" + xpathValue + "]/td[7]/select").getSelectedOption().getValue(), (form.getFields().get(i).getParamAutoSet()), "Параметр автозаполнения неправильный");
            assertEquals($x("//table[@id='fields_table']//tbody//tr[" + xpathValue + "]/td[8]/input").getValue(), (form.getFields().get(i).getRule()), "Правила неправильный");
            assertEquals($x("//table[@id='fields_table']//tbody//tr[" + xpathValue + "]/td[9]").getText(), (form.getFields().get(i).getHint()), "Подсказка неправильная");
            xpathValue++;
        }
    }
}

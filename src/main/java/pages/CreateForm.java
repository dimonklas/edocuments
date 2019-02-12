package pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class CreateForm {
    private SelenideElement uploadFileButton = $(By.id("btn_xsd_upload"));
    private SelenideElement unloadFileButton = $(By.id("btn_xsd_unload"));
    private SelenideElement editFormButton = $(By.id("btn_edit"));
    private SelenideElement saveFormButton = $(By.id("btn_save"));
    private SelenideElement cancelFormButton = $(By.id("btn_cancel"));
    private SelenideElement deleteFormButton = $(By.id("btn_delete"));
    private SelenideElement codeFormInput = $(By.id("form_code"));
    private SelenideElement descriptionFormInput = $(By.id("form_description"));
    private SelenideElement searchFieldInput = $(By.id("field_search"));
    private SelenideElement onlyNotUsedFildsCheckBox = $(By.className("custom-control custom-checkbox"));
}

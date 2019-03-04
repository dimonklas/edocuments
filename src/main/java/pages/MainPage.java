package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import utils.WorkingWithBrowserTabs;

import static com.codeborne.selenide.Selenide.$;

public class MainPage implements WorkingWithBrowserTabs {

    private SelenideElement switchToReportTypeList = $(By.xpath("//h5[normalize-space()='Типы отчетных документов']/..//a[contains(.,'список')]"));
    private SelenideElement createNewType = $(By.xpath("//h5[normalize-space()='Типы отчетных документов']/..//a[contains(.,'тип')]"));
    private SelenideElement formDocumentList = $(By.xpath("//h5[normalize-space()='Формы отчетных документов']/..//a[contains(.,'список')]"));
    private SelenideElement createForm = $(By.xpath("//h5[normalize-space()='Формы отчетных документов']/..//a[contains(.,'Создать новую форму')]"));
    private SelenideElement dropDownList = $(By.xpath("//h5[normalize-space()='Справочники']/..//a[contains(.,'Выпадающие списки')]"));
    private SelenideElement generalDataTypesList = $(By.xpath("//h5[normalize-space()='Справочники']/..//a[contains(.,'Общие типы данных')]"));

    @Step("Перейти на страницу со списком отчетных докуметов")
    public DocumentTypesListPage openReportTypesListPage() {
        switchToReportTypeList.click();
        closeBrowserTab("Конструктор отчетов");
        return new DocumentTypesListPage();
    }

    @Step("Перейти на страницу 'Создать новый тип'")
    public CreateDocumentTypePage openCreateNewTypePage() {
        createNewType.click();
        closeBrowserTab("Конструктор отчетов");
        return new CreateDocumentTypePage();
    }

    @Step("Перейти на страницу 'Формы документов'")
    public DocumentFormListPage openDocumentFormListPage() {
        formDocumentList.click();
        closeBrowserTab("Конструктор отчетов");
        return new DocumentFormListPage();
    }

    @Step("Перейти на страницу 'Создать новую форму'")
    public CreateForm openCreateForm() {
        createForm.click();
        closeBrowserTab("Конструктор отчетов");
        return new CreateForm();
    }

    @Step("Перейти на страницу 'Выпадающие списки'")
    public DropDownListPage openDropDownList() {
        dropDownList.click();
        closeBrowserTab("Конструктор отчетов");
        return new DropDownListPage();
    }

    @Step("Перейти на страницу 'Общие типы данных'")
    public GeneralDataTypesPage openGeneralDataTypes() {
        generalDataTypesList.click();
        closeBrowserTab("Конструктор отчетов");
        return new GeneralDataTypesPage();
    }
}
package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import utils.DateUtil;
import utils.WorkingWithBrowserTabs;

import java.util.Random;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.confirm;


@Log4j
public class OpenCreateDocument implements WorkingWithBrowserTabs {
    private SelenideElement fieldName = $(By.id("type_name"));
    private SelenideElement saveButton = $(By.id("btn_save"));
    private SelenideElement groupJournalCheckBox = $(By.xpath("//*[@id='group_journal']/../label"));
    private SelenideElement finReportCheckBox = $(By.xpath("//*[@id='fin_reporting']/../label"));
    private SelenideElement serviceDropdown = $(By.id("service"));
    private SelenideElement gatewayDropdown = $(By.id("gateway"));
    private SelenideElement vddoc = $(By.id("fin_reporting_vddoc"));
    private SelenideElement groupId = $(By.id("fin_reporting_group"));
    private SelenideElement addVersioButton = $(By.id("btn_version_add"));
    private SelenideElement copyVersioButton = $(By.id("btn_version_copy"));
    private SelenideElement deleteVersioButton = $(By.id("btn_version_delete"));
    private SelenideElement createDocumentButton = $(By.id("btn_document_create"));
    private SelenideElement checkBoxInTable = $(By.xpath("//td[@class=' row-checkbox']"));
    private ElementsCollection dateFromVersion = $$(By.name("version_date_start"));
    private ElementsCollection comTypeVersion = $$(By.name("com_type"));
    private ElementsCollection periodTypeVersion = $$(By.name("period_type"));
    private SelenideElement linkOnHeader = $(By.id("section_title"));
    private ElementsCollection cumulativeControl = $$(By.xpath("//input[@name='cumulative']/../.."));

    @Step("Создание нового типа документа без версии")
    public String createNewDocumentTypeWithoutVersion() {
        String docName = "bla_name_" + new DateUtil().getCurrentDateTime("hhmmssSSS");
        fieldName.shouldBe(visible).sendKeys(docName);
        serviceDropdown.shouldBe(visible).selectOption(1);
        gatewayDropdown.shouldBe(visible).selectOption(1);
        saveButton.shouldBe(visible).click();
        return docName;
    }

    @Step("Создание нового типа документа с версией")
    public String createNewDocumentTypeWithVersion(int versionCount) {
        String docName = "bla_name_" + new DateUtil().getCurrentDateTime("hhmmssSSS");
        fieldName.shouldBe(visible).sendKeys(docName);
        serviceDropdown.shouldBe(visible).selectOption(1);
        gatewayDropdown.shouldBe(visible).selectOption(1);
        for (int i = 1; i < versionCount; i++) { addVersionToDocument(); }
        saveButton.shouldBe(visible).click();
        return docName;
    }

    @Step("Удаление последней версии документа")
    public void deleteLastVersion() {

    }

    @Step("Добавление версии в документ")
    public void addVersionToDocument() {
        Random random = new Random();
        addVersioButton.click();
        dateFromVersion.last().sendKeys(new DateUtil().getCurrentDateTime("yyyy.MM.dd"));
        comTypeVersion.last().selectOption(1 + random.nextInt(3-1));
        int num = 1 + random.nextInt(5-1);
        periodTypeVersion.last().selectOption(num);
        if (num == 3) cumulativeControl.last().click();
    }

    @Step("Добавление версии в документ")
    public void addVersionToDocument(String date, String comType, String typeReportPeriod, boolean cumulativeTotal) {
        addVersioButton.click();
        dateFromVersion.last().sendKeys(date);
        comTypeVersion.last().selectOption(comType);
        periodTypeVersion.last().selectOption(typeReportPeriod);
        if (cumulativeTotal) cumulativeControl.last().click();
    }

    @Step("Переход на главную страницу")
    public MainPage goToMainPage(){
        linkOnHeader.shouldBe(visible).click();
        return new MainPage();
    }

    @Step("Сохранить текущий документ")
    public void saveCurrentDoc(){
        saveButton.click();
    }

    @Step("Перейти на создание докумета \"J0301206\"")
    public CreateReportDeclarationJ0301206 openCreateReportDeclarationJ0301206() {
        String title = $(By.id("page_title")).getText();
        $(By.xpath("//table[@id='versions_table']//input[@id='checkbox-0']/../..")).shouldBe(visible).click();
        createDocumentButton.shouldBe(visible).click();
        closeBrowserTab(title);
        return new CreateReportDeclarationJ0301206();
    }

    @Step("Перейти на создание докумета \"J0301206\"")
    public CreateReportDeclarationS0501408 openCreateReportDeclarationS0501408() {
        String title = $(By.id("page_title")).getText();
        $(By.xpath("//table[@id='versions_table']//input[@id='checkbox-0']/../..")).shouldBe(visible).click();
        createDocumentButton.shouldBe(visible).click();
        closeBrowserTab(title);
        return new CreateReportDeclarationS0501408();
    }


    @Step("Удаление всех версий с документа")
    public void deleteAllVersions() {
        int count = comTypeVersion.size();
        for (int i = 0; i < count ; i++) {
            $(By.xpath("//table[@id='versions_table']//td[@class=' row-checkbox']")).click();
            deleteVersioButton.click();
        }
        goToMainPage();
        confirm();
    }

    @Step("Проверка наличия версий в документе")
    public boolean checkVersionExists() {
        return  $(By.xpath("//table[@id='versions_table']//td[@class=' row-checkbox']")).exists();
    }
}

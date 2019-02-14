package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import pages.document.CreateDocumentData;
import pages.document.VersionData;
import utils.DateUtil;
import utils.WorkingWithBrowserTabs;

import java.util.ArrayList;
import java.util.Random;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.*;


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
    public String createNewDocumentTypeWithoutVersion(CreateDocumentData data) {
        fieldName.shouldBe(visible).sendKeys(data.getDocName());
        serviceDropdown.selectOption(data.getService());
        gatewayDropdown.selectOption(data.getGateway());
        if (data.isGroupJournal()) groupJournalCheckBox.click();
        if (data.isFinReport()) {
            finReportCheckBox.click();
            vddoc.clear();
            vddoc.sendKeys(data.getVddoc());
            groupId.clear();
            groupId.sendKeys(data.getGroupId());
        }
        saveButton.shouldBe(visible).click();
        return $(By.id("page_title")).getText().replaceAll("\\D+", "");
    }

    @Step("Создание нового типа документа с версией")
    public String createNewDocumentTypeWithVersion(CreateDocumentData data, ArrayList<VersionData> versions) {
        fieldName.shouldBe(visible).sendKeys(data.getDocName());
        serviceDropdown.selectOption(data.getService());
        gatewayDropdown.selectOption(data.getGateway());
        if (data.isGroupJournal()) groupJournalCheckBox.click();
        if (data.isFinReport()) {
            finReportCheckBox.click();
            vddoc.clear();
            vddoc.sendKeys(data.getVddoc());
            groupId.clear();
            groupId.sendKeys(data.getGroupId());
        }
        addVersionToDocument(versions);
        saveButton.shouldBe(visible).click();
        return $(By.id("page_title")).getText().replaceAll("\\D+", "");
    }

    @Step("Добавление версии в документ")
    public void addVersionToDocument(ArrayList<VersionData> versions) {
        for (int i = 0; i < versions.size(); i++) {
            addVersioButton.shouldBe(visible).click();
            dateFromVersion.last().sendKeys(versions.get(i).getDate());
            comTypeVersion.last().sendKeys(versions.get(i).getComType());
            periodTypeVersion.last().sendKeys(versions.get(i).getTypeReportPeriod());
            if (versions.get(i).isCumulativeTotal()) cumulativeControl.last().click();
        }
    }

    @Step("Проверка документа после создания")
    public void checkDocument(CreateDocumentData data, String docId) {
        assertEquals($(By.id("page_title")).getText().replaceAll("\\D+", ""), docId);
        assertEquals(fieldName.getValue(), data.getDocName());
        assertEquals(serviceDropdown.getText(), data.getService());
        assertEquals(gatewayDropdown.getText(), data.getGateway());

        boolean gropJournalCheked = executeJavaScript("return document.getElementById(\"group_journal\").checked;");
        boolean finReportChecked = executeJavaScript("return  document.getElementById(\"fin_reporting\").checked;");

        assertEquals(data.isGroupJournal(), gropJournalCheked);
        assertEquals(data.isFinReport(), finReportChecked);

        if (data.isFinReport()) {
            assertEquals(vddoc.getValue(), data.getVddoc());
            assertEquals(groupId.getValue(), data.getGroupId());
        }
    }

    @Step("Проверка документа после создания")
    public void checkDocument(CreateDocumentData data, String docId, ArrayList<VersionData> versions) {
        assertEquals($(By.id("page_title")).shouldBe(visible).getText().replaceAll("\\D+", ""), docId);
        assertEquals($(By.id("type_id")).shouldBe(visible).getValue(), docId);
        assertEquals(fieldName.getValue(), data.getDocName());
        assertEquals(serviceDropdown.getText(), data.getService());
        assertEquals(gatewayDropdown.getText(), data.getGateway());

        boolean gropJournalCheked = executeJavaScript("return document.getElementById(\"group_journal\").checked;");
        boolean finReportChecked = executeJavaScript("return  document.getElementById(\"fin_reporting\").checked;");

        assertEquals(data.isGroupJournal(), gropJournalCheked);
        assertEquals(data.isFinReport(), finReportChecked);

        if (data.isFinReport()) {
            assertEquals(vddoc.getValue(), data.getVddoc());
            assertEquals(groupId.getValue(), data.getGroupId());
        }

        for (int i = 0; i < versions.size(); i++) {
            assertEquals(dateFromVersion.get(i).getValue(), versions.get(i).getDate());
            assertEquals(comTypeVersion.get(i).getText(), versions.get(i).getComType());
            assertEquals(periodTypeVersion.get(i).getText(), versions.get(i).getTypeReportPeriod());
        }
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


    @Step("Переход на главную страницу")
    public MainPage goToMainPage(){
        linkOnHeader.shouldBe(visible).click();
        return new MainPage();
    }

    @Step("Сохранить текущий документ")
    public String saveCurrentDoc(){
        saveButton.click();
        return $(By.id("page_title")).getText().replaceAll("\\D+", "");
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

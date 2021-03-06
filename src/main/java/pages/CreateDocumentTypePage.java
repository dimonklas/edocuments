package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import pages.document.CreateDocumentData;
import pages.document.TabsData;
import pages.document.VersionData;

import java.util.ArrayList;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.*;
import static utils.Utils.isAlertPresent;
import static utils.WorkingWithBrowserTabs.closeBrowserTab;


@Log4j
@Getter
public class CreateDocumentTypePage {
    private SelenideElement fieldName = $(By.id("type_name"));
    private SelenideElement saveButton = $(By.id("btn_save"));
    private SelenideElement cancelButton = $(By.id("btn_cancel"));
    private SelenideElement editButton = $(By.id("btn_edit"));
    private SelenideElement deleteButton = $(By.id("btn_delete"));
    private SelenideElement openButton = $(By.id("btn_open"));
    private SelenideElement closeButton = $(By.id("btn_close"));
    private SelenideElement groupJournalCheckBox = $(By.xpath("//*[@id='group_journal']/../label"));
    private SelenideElement finReportCheckBox = $(By.xpath("//*[@id='fin_reporting']/../label"));
    private SelenideElement serviceDropdown = $(By.id("service"));
    private SelenideElement gatewayDropdown = $(By.id("gateway"));
    private SelenideElement vddoc = $(By.id("fin_reporting_vddoc"));
    private SelenideElement groupId = $(By.id("fin_reporting_group"));
    private SelenideElement addVersionButton = $(By.id("btn_version_add"));
    private SelenideElement copyVersionButton = $(By.id("btn_version_copy"));
    private SelenideElement openVersionButton = $(By.id("btn_version_open"));
    private SelenideElement closeVersionButton = $(By.id("btn_version_close"));
    private SelenideElement deleteVersionButton = $(By.id("btn_version_delete"));
    private SelenideElement createDocumentButton = $(By.id("btn_document_create"));
    private SelenideElement checkBoxInTable = $(By.xpath("//td[@class=' row-checkbox']"));
    private ElementsCollection dateFromVersion = $$(By.name("version_date_start"));
    private ElementsCollection comTypeVersion = $$(By.name("com_type"));
    private ElementsCollection periodTypeVersion = $$(By.name("period_type"));
    private SelenideElement linkOnHeader = $(By.id("section_title"));
    private ElementsCollection cumulativeControl = $$(By.xpath("//input[@name='cumulative']/../.."));
    private SelenideElement modalConfirmWindow = $(By.className("modal-header"));
    private SelenideElement closeConfirmWindow = $(By.id("btn_close_red"));
    /***** Вкладки *****/
    private SelenideElement addTabButton = $(By.id("btn_tab_add"));
    private SelenideElement deleteTabButton = $(By.id("btn_tab_delete"));


    @Step("Создание нового типа документа без версии")
    public void setDataToDocumentType(CreateDocumentData data) {
        fieldName.shouldBe(visible).clear();
        fieldName.shouldBe(visible).sendKeys(data.getDocName());
        serviceDropdown.selectOption(data.getService());
        gatewayDropdown.selectOption(data.getGateway());

        boolean groupJournalChecked = executeJavaScript("return document.getElementById(\"group_journal\").checked;");
        boolean finReportChecked = executeJavaScript("return  document.getElementById(\"fin_reporting\").checked;");

        if (data.isGroupJournal() && !groupJournalChecked) groupJournalCheckBox.click();
        if (!data.isGroupJournal() && groupJournalChecked) groupJournalCheckBox.click();
        if (data.isFinReport() && !finReportChecked) {
            finReportCheckBox.click();
            vddoc.clear();
            vddoc.sendKeys(data.getVddoc());
            groupId.clear();
            groupId.sendKeys(data.getGroupId());
        }
        if (!data.isFinReport() && finReportChecked) finReportCheckBox.click();

        if ($$(By.xpath("//table[@id='versions_table']//tbody/tr")).size() > 0) removeAllVersions();
    }

    @Step("Создание нового типа документа с версиями")
    public void setDataToDocumentType(CreateDocumentData data, VersionData version) {
        fieldName.shouldBe(visible).clear();
        fieldName.shouldBe(visible).sendKeys(data.getDocName());
        serviceDropdown.selectOption(data.getService());
        gatewayDropdown.selectOption(data.getGateway());

        boolean groupJournalChecked = executeJavaScript("return document.getElementById(\"group_journal\").checked;");
        boolean finReportChecked = executeJavaScript("return  document.getElementById(\"fin_reporting\").checked;");

        if (data.isGroupJournal() && !groupJournalChecked) groupJournalCheckBox.click();
        if (!data.isGroupJournal() && groupJournalChecked) groupJournalCheckBox.click();
        if (data.isFinReport() && !finReportChecked) {
            finReportCheckBox.click();
            vddoc.clear();
            vddoc.sendKeys(data.getVddoc());
            groupId.clear();
            groupId.sendKeys(data.getGroupId());
        }
        if (!data.isFinReport() && finReportChecked) finReportCheckBox.click();

        addVersionToDocument(version);
    }

    @Step("Создание нового типа документа с версиями")
    public void setDataToDocumentType(CreateDocumentData data, ArrayList<VersionData> versions) {
        fieldName.shouldBe(visible).clear();
        fieldName.shouldBe(visible).sendKeys(data.getDocName());
        serviceDropdown.selectOption(data.getService());
        gatewayDropdown.selectOption(data.getGateway());

        boolean groupJournalChecked = executeJavaScript("return document.getElementById(\"group_journal\").checked;");
        boolean finReportChecked = executeJavaScript("return  document.getElementById(\"fin_reporting\").checked;");

        if (data.isGroupJournal() && !groupJournalChecked) groupJournalCheckBox.click();
        if (!data.isGroupJournal() && groupJournalChecked) groupJournalCheckBox.click();
        if (data.isFinReport() && !finReportChecked) {
            finReportCheckBox.click();
            vddoc.clear();
            vddoc.sendKeys(data.getVddoc());
            groupId.clear();
            groupId.sendKeys(data.getGroupId());
        }
        if (!data.isFinReport() && finReportChecked) finReportCheckBox.click();

        addVersionToDocument(versions);
    }

    @Step("Создание нового типа документа с версиями и вкладками")
    public void setDataToDocumentType(CreateDocumentData data, ArrayList<VersionData> versionsList, ArrayList<ArrayList<TabsData>> tabsObject) {
        fieldName.shouldBe(visible).clear();
        fieldName.shouldBe(visible).sendKeys(data.getDocName());
        serviceDropdown.selectOption(data.getService());
        gatewayDropdown.selectOption(data.getGateway());

        boolean groupJournalChecked = executeJavaScript("return document.getElementById(\"group_journal\").checked;");
        boolean finReportChecked = executeJavaScript("return  document.getElementById(\"fin_reporting\").checked;");

        if (data.isGroupJournal() && !groupJournalChecked) groupJournalCheckBox.click();
        if (!data.isGroupJournal() && groupJournalChecked) groupJournalCheckBox.click();
        if (data.isFinReport() && !finReportChecked) {
            finReportCheckBox.click();
            vddoc.clear();
            vddoc.sendKeys(data.getVddoc());
            groupId.clear();
            groupId.sendKeys(data.getGroupId());
        }
        if (!data.isFinReport() && finReportChecked) finReportCheckBox.click();

        addVersionAndTabsToDocument(versionsList, tabsObject);
    }

    @Step("Добавление версии в документ")
    public void addVersionToDocument(ArrayList<VersionData> versions) {
        for (int i = 0; i < versions.size(); i++) {
            addVersionButton.shouldBe(visible).click();
            dateFromVersion.last().sendKeys(versions.get(i).getDate());
            comTypeVersion.last().sendKeys(versions.get(i).getComType());
            periodTypeVersion.last().sendKeys(versions.get(i).getTypeReportPeriod());
            if (versions.get(i).isCumulativeTotal()) cumulativeControl.last().click();
        }
    }

    @Step("Добавление версии в документ")
    public void addVersionToDocument(VersionData version) {
        addVersionButton.shouldBe(visible).click();
        dateFromVersion.last().sendKeys(version.getDate());
        comTypeVersion.last().sendKeys(version.getComType());
        periodTypeVersion.last().sendKeys(version.getTypeReportPeriod());
        if (version.isCumulativeTotal()) cumulativeControl.last().click();
    }

    @Step("Добавление версии в документ")
    public void addVersionAndTabsToDocument(ArrayList<VersionData> versions, ArrayList<ArrayList<TabsData>> tabsObject) {
        for (int i = 0; i < versions.size(); i++) {
            addVersionButton.shouldBe(visible).click();
            dateFromVersion.last().sendKeys(versions.get(i).getDate());
            comTypeVersion.last().sendKeys(versions.get(i).getComType());
            periodTypeVersion.last().sendKeys(versions.get(i).getTypeReportPeriod());
            if (versions.get(i).isCumulativeTotal()) cumulativeControl.last().click();

            if (i < tabsObject.size()) addTabsToDocument(tabsObject.get(i));
        }
    }

    @Step("Добавление вкладок в документ")
    public void addTabsToDocument(ArrayList<TabsData> tabsData) {

        for (int i = 1; i <= tabsData.size(); i++) {

            addTabButton.shouldBe(visible).click();

            if (i == 1) {
                $x("//*[@id='tabs_table']//tr[last()]//td[5]//input[@name='form_code']").shouldBe(visible).setValue(tabsData.get(i - 1).getFormCode());
            }

            if (i > 1) {
                $x("//*[@id='tabs_table']//tr[last()]//td[4]/input").shouldBe(visible).setValue(tabsData.get(i - 1).getName());
                $x("//*[@id='tabs_table']//tr[last()]//td[5]//input[@name='form_code']").shouldBe(visible).setValue(tabsData.get(i - 1).getFormCode());
            }
        }
    }

    @Step("Удаление вкладко с документа")
    public void deleteTabsFromDocument() {
        editButton.shouldBe(visible).click();
        int versionsCount = $$x("//table[@id='versions_table']//tbody/tr").size();

        for (int i = 1; i <= versionsCount; i++) {
            $x("//table[@id='versions_table']//tbody/tr[" + i + "]/td[1]").click();
            int tabsCount = $$x("//table[@id='tabs_table']//tbody/tr[not(@hidden='hidden')]").size();
            for (int j = 0; j < tabsCount; j++) {
                $x("//table[@id='tabs_table']//tbody/tr[1]/td[1]").click();
                deleteTabButton.shouldBe(visible).click();
            }
        }
        assertEquals($x("//table[@id='tabs_table']//tbody//td").getText(), "В таблице отсутствуют данные");
    }

    @Step("Проверка документа после создания")
    public void checkDocument(CreateDocumentData data, String docId) {
        assertEquals($(By.id("page_title")).getText().replaceAll("\\D+", ""), docId);
        assertEquals(fieldName.getValue(), data.getDocName());
        assertEquals(serviceDropdown.getText(), data.getService());
        assertEquals(gatewayDropdown.getText(), data.getGateway());

        boolean groupJournalChecked = executeJavaScript("return document.getElementById(\"group_journal\").checked;");
        boolean finReportChecked = executeJavaScript("return  document.getElementById(\"fin_reporting\").checked;");

        assertEquals(data.isGroupJournal(), groupJournalChecked);
        assertEquals(data.isFinReport(), finReportChecked);

        if (data.isFinReport()) {
            assertEquals(vddoc.getValue(), data.getVddoc());
            assertEquals(groupId.getValue(), data.getGroupId());
        }
        assertEquals("В таблице отсутствуют данные", $(By.xpath("//table[@id='versions_table']//tbody//td")).getText());
        assertEquals($x("//table[@id='tabs_table']//tbody//td").getText(), "В таблице отсутствуют данные");
    }

    @Step("Проверка документа после создания")
    public void checkDocument(CreateDocumentData data, String docId, ArrayList<VersionData> versions) {
        editButton.shouldBe(visible).click();
        assertEquals($(By.id("page_title")).shouldBe(visible).getText().replaceAll("\\D+", ""), docId);
        assertEquals($(By.id("type_id")).shouldBe(visible).getValue(), docId);
        assertEquals(fieldName.getValue(), data.getDocName());
        assertEquals(serviceDropdown.getText(), data.getService());
        assertEquals(gatewayDropdown.getText(), data.getGateway());

        boolean groupJournalChecked = executeJavaScript("return document.getElementById(\"group_journal\").checked;");
        boolean finReportChecked = executeJavaScript("return  document.getElementById(\"fin_reporting\").checked;");

        assertEquals(data.isGroupJournal(), groupJournalChecked);
        assertEquals(data.isFinReport(), finReportChecked);

        if (data.isFinReport()) {
            assertEquals(vddoc.getValue(), data.getVddoc());
            assertEquals(groupId.getValue(), data.getGroupId());
        }

        int xpathValue = 1;
        for (int i = 0; i < versions.size(); i++) {
            $x("//table[@id='versions_table']//tbody/tr[" + xpathValue + "]/td[1]").click();
            assertEquals(dateFromVersion.get(i).getValue(), versions.get(i).getDate());
            assertEquals(comTypeVersion.get(i).getText(), versions.get(i).getComType());
            assertEquals(periodTypeVersion.get(i).getText(), versions.get(i).getTypeReportPeriod());
            assertEquals($x("//table[@id='tabs_table']//tbody//td").getText(), "В таблице отсутствуют данные");
            xpathValue++;
        }
        saveCurrentDocAndReturnId();
    }


    @Step("Проверка документа после создания")
    public void checkDocument(CreateDocumentData data, String docId, ArrayList<VersionData> versions, ArrayList<ArrayList<TabsData>> tabsObject) {
        assertEquals($(By.id("page_title")).shouldBe(visible).getText().replaceAll("\\D+", ""), docId);
        assertEquals($(By.id("type_id")).shouldBe(visible).getValue(), docId);
        assertEquals(fieldName.getValue(), data.getDocName());
        assertEquals(serviceDropdown.getText(), data.getService());
        assertEquals(gatewayDropdown.getText(), data.getGateway());

        boolean groupJournalChecked = executeJavaScript("return document.getElementById(\"group_journal\").checked;");
        boolean finReportChecked = executeJavaScript("return  document.getElementById(\"fin_reporting\").checked;");

        assertEquals(data.isGroupJournal(), groupJournalChecked);
        assertEquals(data.isFinReport(), finReportChecked);

        if (data.isFinReport()) {
            assertEquals(vddoc.getValue(), data.getVddoc());
            assertEquals(groupId.getValue(), data.getGroupId());
        }

        ElementsCollection tabs = $$x("//table[@id='versions_table']//tbody/tr/td[1]");
        for (int i = 0; i < versions.size(); i++) {
            assertEquals(dateFromVersion.get(i).getValue(), versions.get(i).getDate());
            assertEquals(comTypeVersion.get(i).getText(), versions.get(i).getComType());
            assertEquals(periodTypeVersion.get(i).getText(), versions.get(i).getTypeReportPeriod());

            tabs.get(i).click();
            if (i < tabsObject.size()) checkTabsInDocType(tabsObject.get(i));
        }
    }

    @Step("Проверка вкладок в документе")
    public void checkTabsInDocType(ArrayList<TabsData> tabsData) {
        int xpathCount = 1;
        for (int i = 0; i < tabsData.size(); i++) {

            if (i == 0) {
                assertEquals($x("//*[@id='tabs_table']//tr[not(@hidden='hidden')][" + xpathCount + "]//td[2]/span").getText(), i + "");
                assertEquals($x("//*[@id='tabs_table']//tr[not(@hidden='hidden')][" + xpathCount + "]//td[3]/input").getText(), "");
                assertEquals($x("//*//*[@id='tabs_table']//tr[not(@hidden='hidden')][" + xpathCount + "]//td[4]/input").getValue(), "Главная форма");
                assertEquals($x("//*//*[@id='tabs_table']//tr[not(@hidden='hidden')][" + xpathCount + "]//td[5]//input[@name='form_code']").getValue(), tabsData.get(i).getFormCode());
            }
            if (i > 1) {
                assertEquals($x("//*[@id='tabs_table']//tr[not(@hidden='hidden')][" + xpathCount + "]//td[2]/span").getText(), i + "");
                assertEquals($x("//*[@id='tabs_table']//tr[not(@hidden='hidden')][" + xpathCount + "]//td[3]/input").getText(), "");
                assertEquals($x("//*//*[@id='tabs_table']//tr[not(@hidden='hidden')][" + xpathCount + "]//td[4]/input").getValue(), tabsData.get(i).getName());
                assertEquals($x("//*//*[@id='tabs_table']//tr[not(@hidden='hidden')][" + xpathCount + "]//td[5]//input[@name='form_code']").getValue(), tabsData.get(i).getFormCode());
            }
            xpathCount++;
        }
    }

    @Step("Переход на главную страницу")
    public MainPage goToMainPage() {
        linkOnHeader.shouldBe(visible).click();
        return new MainPage();
    }

    @Step("Переход на главную страницу")
    public MainPage goToMainPageWithConfirm() {
        linkOnHeader.shouldBe(visible).click();
        if (isAlertPresent()) confirm();
        return new MainPage();
    }

    @Step("Сохранить текущий документ")
    public String saveCurrentDocAndReturnId() {
        saveButton.shouldBe(visible).click();
        sleep(1000);

        if (modalConfirmWindow.is(visible)) {
            closeConfirmWindow.shouldBe(visible).click();
            goToMainPage();
            if (isAlertPresent()) confirm();
            return "Ошибка";
        } else return $(By.id("page_title")).getText().replaceAll("\\D+", "");
    }

    @Step("Отмена изменений в типе документа")
    public void cancelChanges() {
        cancelButton.shouldBe(visible).click();
        sleep(1000);
        if ($(By.className("modal-header")).is(visible)) $(By.id("btn_yes")).shouldBe(visible).click();
        sleep(1000);
    }

    @Step("Удаление документа")
    public void removeDocument() {
        deleteButton.shouldBe(visible).click();
        sleep(1000);
        if ($(By.className("modal-header")).is(visible)) $(By.id("btn_yes")).shouldBe(visible).click();
        sleep(1000);
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
    public void removeAllVersions() {
        int count = comTypeVersion.size();
        for (int i = 0; i < count; i++) {
            $(By.xpath("//table[@id='versions_table']//td[@class=' row-checkbox']")).click();
            deleteVersionButton.click();
        }
    }

    @Step("Копирование последней версии в документе")
    public void copyLastVersion() {
        $x("(//table[@id='versions_table']//tbody//tr)[last()]/td").shouldBe(visible).click();
        copyVersionButton.shouldBe(visible).click();
    }

    @Step("Проверка наличия версий в документе")
    public boolean checkVersionExists() {
        return $(By.xpath("//table[@id='versions_table']//td[@class=' row-checkbox']")).exists();
    }

    @Step("Открытие документа")
    public boolean openDocument() {
        if (openButton.is(visible) && $x("//div[@id='type_data']//span").is(visible)) {
            openButton.click();
            sleep(500);
            return closeButton.is(visible) && $x("//div[@id='type_data']//span").is(not(visible));
        } else return false;
    }

    @Step("Закрытие документа")
    public boolean closeDocument() {
        if (closeButton.is(visible) && $x("//div[@id='type_data']//span").is(not(visible))) {
            closeButton.click();
            sleep(500);
            return openButton.is(visible) && $x("//div[@id='type_data']//span").is(visible);
        } else return false;
    }

    @Step("Открытие версии документа")
    public boolean openAllVersion() {
        int count = comTypeVersion.size();
        int currentCloseVersion = $$x("//table[@id='versions_table']//tr[contains(.,('Закрыта'))]").size();
        int countCloseVersion = 0;

        for (int i = 1; i <= count; i++) {
            if ($x("(//table[@id='versions_table']//tbody//tr)[" + i + "]/td[contains(.,'Закрыта')]").is(exist)) {
                $x("(//table[@id='versions_table']//tbody//tr)[" + i + "]/td").click();
                openVersionButton.shouldBe(visible).click();
            }
            sleep(500);
            if (closeVersionButton.is(visible) && $x("(//table[@id='versions_table']//tbody//tr)[" + i + "]//td[3]").getText().equals("Открыта"))
                countCloseVersion++;
        }
        return countCloseVersion == currentCloseVersion;
    }

    @Step("Открытие версии документа")
    public boolean closeAllVersion() {
        int count = comTypeVersion.size();
        int currentCloseVersion = $$x("//table[@id='versions_table']//tr[contains(.,('Открыта'))]").size();
        int countCloseVersion = 0;

        for (int i = 1; i <= count; i++) {
            if ($x("(//table[@id='versions_table']//tbody//tr)[" + i + "]/td[contains(.,'Открыта')]").is(exist)) {
                $x("(//table[@id='versions_table']//tbody//tr)[" + i + "]/td").click();
                closeVersionButton.shouldBe(visible).click();
            }
            sleep(500);

            if (openVersionButton.is(visible) && $x("(//table[@id='versions_table']//tbody//tr)[" + i + "]//td[3]").getText().equals("Закрыта"))
                countCloseVersion++;
        }
        return countCloseVersion == currentCloseVersion;
    }
}

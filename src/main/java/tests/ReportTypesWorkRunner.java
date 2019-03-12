package tests;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import lombok.extern.log4j.Log4j;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.*;
import pages.document.*;
import pages.documentObjects.*;
import utils.AllureOnFailListener;

import static org.testng.Assert.*;


@Log4j
@Feature("Тестирование работы типов отчетных документов")
@Listeners(AllureOnFailListener.class)
public class ReportTypesWorkRunner extends BaseTest {

    private CreateDocumentObject documentObject;
    private VersionsObject versionsList;
    private TabsObject tabsObject;


    @DataProvider
    public Object[][] incorrectDataForCreateForm() {
        return new Object[][]{
                {new CreateDocumentObject().getDocumentIncorrectDataFirst()},
                {new CreateDocumentObject().getDocumentIncorrectDataSecond()},
                {new CreateDocumentObject().getDocumentIncorrectDataThird()},
                {new CreateDocumentObject().getDocumentIncorrectDataFour()}
        };
    }


    @Story("Проверка отображения ошибки при некорректном создании типа документа")
    @Test(description = "Проверка отображения ошибки при создании документа без версии", dataProvider = "incorrectDataForCreateForm")
    public void checkErrorWhileCreateNewDoc(CreateDocumentData documentData) {
        CreateDocumentTypePage typePage = new MainPage().openCreateNewTypePage();
        typePage.setDataToDocumentType(documentData);
        assertEquals("Ошибка", typePage.saveCurrentDocAndReturnId(), "Документ сохранился с некорректными данными");
    }

    @Story("Проверка отображения ошибки при некорректном создании типа документа")
    @Test(description = "Проверка отображения ошибки при создании документа с версиями", dataProvider = "incorrectDataForCreateForm")
    public void checkErrorWhileCreateNewDocWithVersions(CreateDocumentData documentData) {
        /***** Генерим тестовые данные *****/
        versionsList = new VersionsObject();

        CreateDocumentTypePage typePage = new MainPage().openCreateNewTypePage();
        typePage.setDataToDocumentType(documentData, versionsList.getVersionList());
        assertEquals("Ошибка", typePage.saveCurrentDocAndReturnId());
    }

    @Story("Проверка кнопки \"Отменить\" в редактировании типа тендера")
    @Test(description = "Проверка отмены изменений в редактировании тендера, с удалением версий")
    public void checkCancelButtonRemoveVersions() {
        /***** Генерим тестовые данные *****/
        documentObject = new CreateDocumentObject();
        versionsList = new VersionsObject();

        /***** Тест *****/
        CreateDocumentTypePage typePage = new MainPage().openCreateNewTypePage();
        /***** Создаем документ *****/
        typePage.setDataToDocumentType(documentObject.getDocumentDataFirst(), versionsList.getVersionList());
        String docId = typePage.saveCurrentDocAndReturnId();
        MainPage mainPage = typePage.goToMainPage();
        DocumentTypesListPage typesListPage = mainPage.openReportTypesListPage();

        typePage = typesListPage.searchAndOpenDocument(documentObject.getDocumentDataFirst().getDocName());
        typePage.getEditButton().shouldBe(Condition.visible).click();
        /***** Редактируем документ (добавляем версию) *****/
        typePage.setDataToDocumentType(documentObject.getDocumentDataSecond());
        typePage.cancelChanges();
        typePage.checkDocument(documentObject.getDocumentDataFirst(), docId, versionsList.getVersionList());
        mainPage = typePage.goToMainPage();
        typesListPage = mainPage.openReportTypesListPage();
        typePage = typesListPage.searchAndOpenDocument(documentObject.getDocumentDataFirst().getDocName());
        typePage.checkDocument(documentObject.getDocumentDataFirst(), docId, versionsList.getVersionList());
    }

    @Story("Проверка кнопки \"Отменить\" в редактировании типа тендера")
    @Test(description = "Проверка отмены изменений в редактировании тендера, с добавлением версий")
    public void checkCancelButtonAddVersions() {
        /***** Генерим тестовые данные *****/
        documentObject = new CreateDocumentObject();
        versionsList = new VersionsObject();

        /***** Тест *****/
        CreateDocumentTypePage typePage = new MainPage().openCreateNewTypePage();
        /***** Создаем документ *****/
        typePage.setDataToDocumentType(documentObject.getDocumentDataFirst());
        String docId = typePage.saveCurrentDocAndReturnId();
        MainPage mainPage = typePage.goToMainPage();
        DocumentTypesListPage typesListPage = mainPage.openReportTypesListPage();

        typePage = typesListPage.searchAndOpenDocument(documentObject.getDocumentDataFirst().getDocName());
        typePage.getEditButton().shouldBe(Condition.visible).click();
        /***** Редактируем документ (добавляем версию) *****/
        typePage.setDataToDocumentType(documentObject.getDocumentDataSecond(), versionsList.getVersionList());
        typePage.cancelChanges();
        typePage.checkDocument(documentObject.getDocumentDataFirst(), docId);
        mainPage = typePage.goToMainPage();
        typesListPage = mainPage.openReportTypesListPage();
        typePage = typesListPage.searchAndOpenDocument(documentObject.getDocumentDataFirst().getDocName());
        typePage.checkDocument(documentObject.getDocumentDataFirst(), docId);
    }

    @Story("Проверка кнопки \"Удалить\" в типе документа")
    @Test(description = "Удаление документа с версиями")
    public void checkDeleteButtonInDocTypeWithVersions() {
        /***** Генерим тестовые данные *****/
        documentObject = new CreateDocumentObject();

        /***** Тест *****/
        DocumentTypesListPage typesListPage = new MainPage().openReportTypesListPage();
        CreateDocumentTypePage typePage = typesListPage.goToCreateNewDocumentPage();
        /***** Создаем документ *****/
        typePage.setDataToDocumentType(documentObject.getDocumentDataFirst());
        typePage.saveCurrentDocAndReturnId();
        MainPage mainPage = typePage.goToMainPage();
        typesListPage = mainPage.openReportTypesListPage();

        typePage = typesListPage.searchAndOpenDocument(documentObject.getDocumentDataFirst().getDocName());

        /***** Удаляем документ *****/
        typePage.removeDocument();
        mainPage = typePage.goToMainPageWithConfirm();
        typesListPage = mainPage.openReportTypesListPage();
        assertFalse(typesListPage.searchDocument(documentObject.getDocumentDataFirst().getDocName()), "Тип документа не удалился");
    }

    @Story("Проверка кнопки \"Удалить\" в типе документа")
    @Test(description = "Удаление документа без версий")
    public void checkDeleteButtonInDocTypeWithoutVersions() {
        /***** Генерим тестовые данные *****/
        documentObject = new CreateDocumentObject();
        versionsList = new VersionsObject();

        /***** Тест *****/
        DocumentTypesListPage typesListPage = new MainPage().openReportTypesListPage();
        CreateDocumentTypePage typePage = typesListPage.goToCreateNewDocumentPage();
        /***** Создаем документ *****/
        typePage.setDataToDocumentType(documentObject.getDocumentDataFirst(), versionsList.getVersionList());
        typePage.saveCurrentDocAndReturnId();
        MainPage mainPage = typePage.goToMainPage();
        typesListPage = mainPage.openReportTypesListPage();

        typePage = typesListPage.searchAndOpenDocument(documentObject.getDocumentDataFirst().getDocName());

        /***** Удаляем документ *****/
        typePage.removeDocument();
        mainPage = typePage.goToMainPageWithConfirm();
        typesListPage = mainPage.openReportTypesListPage();
        assertFalse(typesListPage.searchDocument(documentObject.getDocumentDataFirst().getDocName()), "Тип документа не удалился");
    }

    @Story("Открытие и закрытие документа")
    @Test(description = "Открытие и закрытие документа через список документов")
    public void checkOpenCloseDocumentThroughList() {
        /***** Генерим тестовые данные *****/
        documentObject = new CreateDocumentObject();
        versionsList = new VersionsObject();

        /***** Тест *****/
        DocumentTypesListPage typesListPage = new MainPage().openReportTypesListPage();
        CreateDocumentTypePage typePage = typesListPage.goToCreateNewDocumentPage();
        /***** Создаем документ *****/
        typePage.setDataToDocumentType(documentObject.getDocumentDataFirst(), versionsList.getVersionList());
        typePage.saveCurrentDocAndReturnId();
        MainPage mainPage = typePage.goToMainPage();
        typesListPage = mainPage.openReportTypesListPage();

        typesListPage.searchDocument(documentObject.getDocumentDataFirst().getDocName());
        assertTrue(typesListPage.openDocument(), "Документ не открылся");
        assertTrue(typesListPage.closeDocument(), "Документ не закрылся");
    }

    @Story("Открытие и закрытие документа")
    @Test(description = "Открытие и закрытие документа через список редактирование документа")
    public void checkOpenCloseDocumentThroughDocumentPage() {
        /***** Генерим тестовые данные *****/
        documentObject = new CreateDocumentObject();
        versionsList = new VersionsObject();

        /***** Тест *****/
        DocumentTypesListPage typesListPage = new MainPage().openReportTypesListPage();
        CreateDocumentTypePage typePage = typesListPage.goToCreateNewDocumentPage();
        /***** Создаем документ *****/
        typePage.setDataToDocumentType(documentObject.getDocumentDataFirst(), versionsList.getVersionList());
        typePage.saveCurrentDocAndReturnId();
        MainPage mainPage = typePage.goToMainPage();
        typesListPage = mainPage.openReportTypesListPage();

        typePage = typesListPage.searchAndOpenDocument(documentObject.getDocumentDataFirst().getDocName());
        assertTrue(typePage.openDocument(), "Документ не открылся");
        assertTrue(typePage.closeDocument(), "Документ не открылся");
    }

    @Story("Открытие и закрытие документа")
    @Test(description = "Открытие и закрытие документа через список редактирование документа + открытие версий")
    public void checkOpenDocumentVersion() {
        /***** Генерим тестовые данные *****/
        documentObject = new CreateDocumentObject();
        versionsList = new VersionsObject();

        /***** Тест *****/
        DocumentTypesListPage typesListPage = new MainPage().openReportTypesListPage();
        CreateDocumentTypePage typePage = typesListPage.goToCreateNewDocumentPage();
        /***** Создаем документ *****/
        typePage.setDataToDocumentType(documentObject.getDocumentDataFirst(), versionsList.getVersionList());
        typePage.saveCurrentDocAndReturnId();
        MainPage mainPage = typePage.goToMainPage();
        typesListPage = mainPage.openReportTypesListPage();

        typePage = typesListPage.searchAndOpenDocument(documentObject.getDocumentDataFirst().getDocName());
        assertTrue(typePage.openAllVersion(), "Версия не открылась");
        assertTrue(typePage.closeAllVersion(), "Версия не закрылась");
    }

    @Story("Создание типа документа с вкладками")
    @Test(description = "Создание типа документа с вкладками")
    public void checkCreateDocTypeWithTabs() {
        /***** Генерим тестовые данные *****/
        documentObject = new CreateDocumentObject();
        versionsList = new VersionsObject();
        tabsObject = new TabsObject();


        /***** Тест *****/
        CreateDocumentTypePage typePage = new MainPage().openCreateNewTypePage();
        /***** Создаем документ *****/
        typePage.setDataToDocumentType(documentObject.getDocumentDataFirst(), versionsList.getVersionList(), tabsObject.tabsArray());
        String docId = typePage.saveCurrentDocAndReturnId();
        MainPage mainPage = typePage.goToMainPage();
        DocumentTypesListPage typesListPage = mainPage.openReportTypesListPage();

        typePage = typesListPage.searchAndOpenDocument(documentObject.getDocumentDataFirst().getDocName());
        typePage.checkDocument(documentObject.getDocumentDataFirst(), docId, versionsList.getVersionList(), tabsObject.tabsArray());
    }

    @Story("Удаление вкладок с документа")
    @Test(description = "Удаление вкладок с документа")
    public void checkDeleteTabsFromDocument() {
        /***** Генерим тестовые данные *****/
        documentObject = new CreateDocumentObject();
        versionsList = new VersionsObject();
        tabsObject = new TabsObject();


        /***** Тест *****/
        CreateDocumentTypePage typePage = new MainPage().openCreateNewTypePage();
        /***** Создаем документ *****/
        typePage.setDataToDocumentType(documentObject.getDocumentDataFirst(), versionsList.getVersionList(), tabsObject.tabsArray());
        typePage.saveCurrentDocAndReturnId();
        MainPage mainPage = typePage.goToMainPage();
        DocumentTypesListPage typesListPage = mainPage.openReportTypesListPage();

        typePage = typesListPage.searchAndOpenDocument(documentObject.getDocumentDataFirst().getDocName());
        typePage.deleteTabsFromDocument();
        String docId = typePage.saveCurrentDocAndReturnId();
        typePage.goToMainPage();
        typesListPage = mainPage.openReportTypesListPage();
        typesListPage.searchAndOpenDocument(docId);
        typePage.checkDocument(documentObject.getDocumentDataFirst(), docId, versionsList.getVersionList());
    }

    @Story("Создание типа документа с вкладками (негативный сценарий. некорректные данные для вкладок)")
    @Test(description = "Создание типа документа с вкладками")
    public void checkCreateDocTypeWithTabsNegative() {
        /***** Генерим тестовые данные *****/
        documentObject = new CreateDocumentObject();
        versionsList = new VersionsObject();
        tabsObject = new TabsObject();

        /***** Тест *****/
        CreateDocumentTypePage typePage = new MainPage().openCreateNewTypePage();
        /***** Создаем документ *****/
        typePage.setDataToDocumentType(documentObject.getDocumentDataFirst(), versionsList.getVersionList(), tabsObject.tabsArrayNegative());
        assertEquals("Ошибка", typePage.saveCurrentDocAndReturnId(), "Документ сохранился с некорректными данными");
    }
}
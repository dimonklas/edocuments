package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import lombok.extern.log4j.Log4j;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.CreateDocumentTypePage;
import pages.DocumentTypesListPage;
import pages.MainPage;
import pages.documentObjects.*;
import utils.AllureOnFailListener;

@Log4j
@Feature("Тестирование типов отчетных документов")
@Listeners(AllureOnFailListener.class)
public class ReportTypesRunner extends BaseTest {
    private CreateDocumentObject documentObject;
    private VersionsObject versionsList;
    private J0301206Object j0301206Object;
    private S0501408Object s0501408Object;
    private TabsObject tabsObject;

    @DataProvider
    public Object[][] getDataForSort() {
        return new Object[][]
                {
                        {"ID"},
//                        {"Код формы"},
//                        {"Наименование документа"},
//                        {"Служба"},
//                        {"Шлюз"},
                };
    }


    @Story("Сортировка по возростанию")
    @Test(dataProvider = "getDataForSort", description = "Сортировка по значению ->")
    public void checkSortedAsc(String sorted) {
        DocumentTypesListPage documentTypesListPage = new MainPage().openReportTypesListPage();
        documentTypesListPage.checkDocumentTypesListPage();
        documentTypesListPage.checkSortingAsc(sorted);
    }

    @Story("Сортировка по убыванию")
    @Test(dataProvider = "getDataForSort", description = "Сортировка по значению ->")
    public void checkSortedDesc(String sorted) {
        DocumentTypesListPage documentTypesListPage = new MainPage().openReportTypesListPage();
        documentTypesListPage.checkDocumentTypesListPage();
        documentTypesListPage.checkSortingDesc(sorted);
    }

    @Story("Тестирование поиска")
    @Test(description = "Поиск документа \"S0210110\"")
    public void checkSearchOnDocsTypes() {
        DocumentTypesListPage documentTypesListPage = new MainPage().openReportTypesListPage();
        documentTypesListPage.searchDocument("S0210110");
    }

    @Story("Создание нового типа без версии документа")
    @Test(description = "Создание нового документа без указания версии")
    public void createNewDocument() {
        /***** Генерим тестовые данные *****/
        documentObject = new CreateDocumentObject();
        /***** Тест *****/
        CreateDocumentTypePage typePage = new MainPage().openCreateNewTypePage();
        typePage.setDataToDocumentType(documentObject.getDocumentDataFirst());
        String docId = typePage.saveCurrentDocAndReturnId();
        MainPage mainPage = typePage.goToMainPage();
        DocumentTypesListPage typesListPage = mainPage.openReportTypesListPage();
        typePage = typesListPage.searchAndOpenDocument(documentObject.getDocumentDataFirst().getDocName());
        typePage.checkDocument(documentObject.getDocumentDataFirst(), docId);
    }

    @Story("Создание нового типа с версией документа")
    @Test(description = "Создание нового документа с указанием версии")
    public void createNewDocumentWithVersion() {
        /***** Генерим тестовые данные *****/
        documentObject = new CreateDocumentObject();
        versionsList = new VersionsObject();

        /***** Тест *****/
        CreateDocumentTypePage typePage = new MainPage().openCreateNewTypePage();
        typePage.setDataToDocumentType(documentObject.getDocumentDataFirst(), versionsList.getVersionList());
        String docId = typePage.saveCurrentDocAndReturnId();
        MainPage mainPage = typePage.goToMainPage();
        DocumentTypesListPage typesListPage = mainPage.openReportTypesListPage();
        typePage = typesListPage.searchAndOpenDocument(documentObject.getDocumentDataFirst().getDocName());
        typePage.checkDocument(documentObject.getDocumentDataFirst(), docId, versionsList.getVersionList());
    }

    @Story("Создание копии документа с версией")
    @Test(description = "Создание копии документа с версией")
    public void checkCopyDocumentWithVersion() {
        /***** Генерим тестовые данные *****/
        documentObject = new CreateDocumentObject();
        versionsList = new VersionsObject();

        /***** Тест *****/
        CreateDocumentTypePage typePage = new MainPage().openCreateNewTypePage();
        typePage.setDataToDocumentType(documentObject.getDocumentDataFirst(), versionsList.getVersionList());
        typePage.saveCurrentDocAndReturnId();
        MainPage mainPage = typePage.goToMainPage();
        DocumentTypesListPage typesListPage = mainPage.openReportTypesListPage();
        typesListPage.searchDocument(documentObject.getDocumentDataFirst().getDocName());
        typePage = typesListPage.selectAndCopyDocType(documentObject.getDocumentDataFirst().getDocName());
        String docCopyId = typePage.saveCurrentDocAndReturnId();
        mainPage = typePage.goToMainPage();
        typesListPage = mainPage.openReportTypesListPage();
        typesListPage.searchDocument(documentObject.getDocumentDataFirst().getDocName());
        typesListPage.checkTwoRows();
        typePage = typesListPage.searchAndOpenDocument(docCopyId);
        typePage.checkDocument(documentObject.getDocumentDataFirst(), docCopyId, versionsList.getVersionList());
    }

    @Story("Создание копии документа без версии")
    @Test(description = "Создание копии документа без версии")
    public void checkCopyDocumentWithoutVersion() {
        /***** Генерим тестовые данные *****/
        documentObject = new CreateDocumentObject();
        /***** Тест *****/
        CreateDocumentTypePage typePage = new MainPage().openCreateNewTypePage();
        typePage.setDataToDocumentType(documentObject.getDocumentDataFirst());
        typePage.saveCurrentDocAndReturnId();
        MainPage mainPage = typePage.goToMainPage();
        DocumentTypesListPage typesListPage = mainPage.openReportTypesListPage();
        typesListPage.searchDocument(documentObject.getDocumentDataFirst().getDocName());
        typePage = typesListPage.selectAndCopyDocType(documentObject.getDocumentDataFirst().getDocName());
        String docCopyId = typePage.saveCurrentDocAndReturnId();
        mainPage = typePage.goToMainPage();
        typesListPage = mainPage.openReportTypesListPage();
        typesListPage.searchDocument(documentObject.getDocumentDataFirst().getDocName());
        typesListPage.checkTwoRows();
        typePage = typesListPage.searchAndOpenDocument(docCopyId);
        typePage.checkDocument(documentObject.getDocumentDataFirst(), docCopyId);
    }
}

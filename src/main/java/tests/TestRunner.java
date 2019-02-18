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
import pages.documentObjects.CreateDocumentObject;
import pages.documentObjects.J0301206Object;
import pages.documentObjects.S0501408Object;
import pages.documentObjects.VersionsObject;
import utils.AllureOnFailListener;

import static org.testng.Assert.assertEquals;


@Log4j
@Feature("Тестирование документов")
@Listeners(AllureOnFailListener.class)
public class TestRunner extends BaseTest {

    private CreateDocumentObject documentObject;
    private VersionsObject versionsList;
    private J0301206Object j0301206Object;
    private S0501408Object s0501408Object;

    @DataProvider
    public Object[][] getDataForSort() {
        return new Object[][]
                {
                        {"ID"},
                        {"Код формы"},
                        {"Наименование документа"},
                        {"Служба"},
                        {"Шлюз"},
                };
    }

    @DataProvider
    public Object[][] getDataForCreateNegativeDoc() {
        return new Object[][]
                {
//                {"~!@#$^&*()_+{}|:</\\\\>?\\\"|Ё!№;:?*().,"},
                        {"qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM"},
                        {"ёйцукенгшщзхъфывапролджэячсмитьбюЁЙЦУКЕНГШЩЗФЫВАПРОЛДЖЭХЪЯЧСМИТЬБЮ"},
//                {"-10"}
                };
    }

    @DataProvider
    public Object[][] incorrectDataForCreateForm() {
        return new Object[][]{
                {documentObject.getDocumentIncorrectDataFirst()},
                {documentObject.getDocumentIncorrectDataSecond()},
                {documentObject.getDocumentIncorrectDataThird()},
                {documentObject.getDocumentIncorrectDataFour()}
        };
    }

    @Story("Проверка сортировки по возростанию")
    @Test(dataProvider = "getDataForSort", description = "Сортировка по значению ->", enabled = false)
    public void checkSortedAsc(String sorted) {
        DocumentTypesListPage documentTypesListPage = new MainPage().openReportTypesListPage();
        documentTypesListPage.checkDocumentTypesListPage();
        documentTypesListPage.checkSortingAsc(sorted);
    }

    @Story("Проверка сортировки по убыванию")
    @Test(dataProvider = "getDataForSort", description = "Сортировка по значению ->", enabled = false)
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
        /***** Генерим тестовые данные *****/;
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

    @Story("Проверка создания копии документа с версией")
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

    @Story("Проверка создания копии документа без версии")
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


    @Story("Создание документа (позитивный сценарий) J0301206")
    @Test(description = "Создать новый документ (J0301206)")
    public void createNewReportDeclarationJ0301206() {

        /***** Генерим данные для заполнения документа *****/
        j0301206Object = new J0301206Object();

        DocumentTypesListPage documentTypesListPage = new MainPage().openReportTypesListPage();
        documentTypesListPage.checkDocumentTypesListPage();
        documentTypesListPage.searchDocument("0301206");
        CreateDocumentTypePage document = documentTypesListPage.openDocument("0301206");

        CreateReportDeclarationJ0301206 reportDeclaration = document.openCreateReportDeclarationJ0301206();
        reportDeclaration.clickEditButton();

        /***** І. Загальні відомості *****/
        j0301206Object.getDeclarationData().getDeclarationType().chooseTypeDeclaration();
        j0301206Object.getDeclarationData().getSpecifiedPeriodType().chooseReportSpecifiedPeriod();

        reportDeclaration.declarationJ0301206Fields.forEach((key, value) -> reportDeclaration.setValueToGeneralInfo(key, value, j0301206Object.getDeclarationData()));

        /**** ІІ. Розрахунок податкових зобов'язань збору за місця для паркування транспортних засобів *****/
        reportDeclaration.declarationJ0301206Fields.forEach((key, value) -> reportDeclaration.setValueToCalculationTax(key, value, j0301206Object.getCalculationTax()));

        /***** Заполнение персональных данных (футер страницы) *****/
        reportDeclaration.declarationJ0301206Fields.forEach((key, value) -> reportDeclaration.setValueToPersonalInfo(key, value, j0301206Object.getPersonInfo()));

        /**** Проверка формул в документе *****/
        String expectedTax = reportDeclaration.calculationExpectedTax(j0301206Object.getCalculationTax().getSquare(), j0301206Object.getCalculationTax().getMinSalary(), j0301206Object.getCalculationTax().getCountDays(), j0301206Object.getCalculationTax().getPercent());
        String expectedTaxReportPeriod = reportDeclaration.calculationExpectedTaxForReportPeriod(j0301206Object.getCalculationTax().getSquare(), j0301206Object.getCalculationTax().getMinSalary(), j0301206Object.getCalculationTax().getCountDays(), j0301206Object.getCalculationTax().getPercent(), j0301206Object.getCalculationTax().getTaxSum());
        assertEquals(expectedTax, reportDeclaration.getResultSum().getValue(), "Неправильный расчет суммы");
        assertEquals(expectedTaxReportPeriod, reportDeclaration.getResultSumForReportPeriod().getValue(), "Неправильный расчет суммы");

        /***** Сохраняем отчет *****/
        reportDeclaration.saveReport();

        /***** Проверяем данные после сохраниния *****/
        reportDeclaration.declarationJ0301206Fields.forEach((key, value) -> reportDeclaration.checkValueToGeneralInfo(key, value, j0301206Object.getDeclarationData()));
        reportDeclaration.declarationJ0301206Fields.forEach((key, value) -> reportDeclaration.checkValueToCalculationTax(key, value, j0301206Object.getCalculationTax()));
        reportDeclaration.declarationJ0301206Fields.forEach((key, value) -> reportDeclaration.checkValueToPersonalInfo(key, value, j0301206Object.getPersonInfo()));

        /***** Отправляем отчет *****/
        reportDeclaration.subscribeAndSendReport();
        assertEquals("Надіслано, очікуйте...", reportDeclaration.waitReportStatusChange(), "Неверный статус формы");
    }

    @Story("Создание документа (негативный сценарий) J0301206")
    @Test(description = "Создать новый документ (J0301206) с некорректными данными", dataProvider = "getDataForCreateNegativeDoc")
    public void createNewReportDeclarationNegativeJ0301206(String dataProviderValue) {

        /***** Генерим данные для заполнения документа *****/
        j0301206Object = new J0301206Object();

        DocumentTypesListPage documentTypesListPage = new MainPage().openReportTypesListPage();
        documentTypesListPage.checkDocumentTypesListPage();
        documentTypesListPage.searchDocument("0301206");
        CreateDocumentTypePage document = documentTypesListPage.openDocument("0301206");

        CreateReportDeclarationJ0301206 reportDeclaration = document.openCreateReportDeclarationJ0301206();
        reportDeclaration.clickEditButton();

        /***** І. Загальні відомості *****/
        j0301206Object.getDeclarationData().getDeclarationType().chooseTypeDeclaration();
        j0301206Object.getDeclarationData().getSpecifiedPeriodType().chooseReportSpecifiedPeriod();

        reportDeclaration.declarationJ0301206Fields.forEach((key, value) -> reportDeclaration.setValueToGeneralInfo(key, value, j0301206Object.generateGeneralInfoFromDataProvider(dataProviderValue)));

        /**** ІІ. Розрахунок податкових зобов'язань збору за місця для паркування транспортних засобів *****/
        reportDeclaration.declarationJ0301206Fields.forEach((key, value) -> reportDeclaration.setValueToCalculationTax(key, value, j0301206Object.generateCalculationTaxFromDataProvider(dataProviderValue)));

        /***** Заполнение персональных данных (футер страницы) *****/
        reportDeclaration.declarationJ0301206Fields.forEach((key, value) -> reportDeclaration.setValueToPersonalInfo(key, value, j0301206Object.generatePersonalInfoFromDataProvider(dataProviderValue)));

        /***** Проверка значений в полях хедера *****/
        reportDeclaration.declarationJ0301206Fields.forEach((key, value) -> reportDeclaration.checkValueInGeneralInfo(key, value, dataProviderValue));

        /***** Проверка значений в полях расчетов *****/
        reportDeclaration.declarationJ0301206Fields.forEach(reportDeclaration::checkValueInCalculationTax);

        /***** Проверка значений в полях футера *****/
        reportDeclaration.declarationJ0301206Fields.forEach(reportDeclaration::checkValueInPersonalInfo);

        /**** Проверка формул в документе *****/
        assertEquals("0.00", reportDeclaration.getResultSum().getValue(), "Неправильный расчет суммы");
        assertEquals("0.00", reportDeclaration.getResultSumForReportPeriod().getValue(), "Неправильный расчет суммы");

        /***** Сохраняем отчет и отправляем *****/
        reportDeclaration.saveReport();

        /***** Проверка значений в полях хедера после сохранения *****/
        reportDeclaration.declarationJ0301206Fields.forEach((key, value) -> reportDeclaration.checkValueInGeneralInfo(key, value, dataProviderValue));

        /***** Проверка значений в полях расчетов после сохранения *****/
        reportDeclaration.declarationJ0301206Fields.forEach(reportDeclaration::checkValueInCalculationTax);

        /***** Проверка значений в полях футера после сохранения *****/
        reportDeclaration.declarationJ0301206Fields.forEach(reportDeclaration::checkValueInPersonalInfo);
    }

    @Story("Создание документа (позитивный сценарий) S0501408")
    @Test(description = "Создать новый документ (S0501408)")
    public void createNewReportDeclarationS0501408() {

        /***** Генерим данные для заполнения документа *****/
        s0501408Object = new S0501408Object();

        DocumentTypesListPage documentTypesListPage = new MainPage().openReportTypesListPage();
        documentTypesListPage.checkDocumentTypesListPage();
        documentTypesListPage.searchDocument("S0501408");
        CreateDocumentTypePage document = documentTypesListPage.openDocument("S0501408");

        CreateReportDeclarationS0501408 reportDeclaration = document.openCreateReportDeclarationS0501408();
        reportDeclaration.clickEditButton();

        /***** Респондент *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.setValueToHeader(key, value, s0501408Object.getDataDeclaration()));

        /**** Экспорт товаров *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.setValueToExportProducts(key, value, s0501408Object.getDataDeclaration()));

        /**** Импорт товаров товаров *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.setValueToImportProducts(key, value, s0501408Object.getDataDeclaration()));

        /***** Персональные данные *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.setValueToFooter(key, value, s0501408Object.getDataDeclaration()));

        /***** Сохраняем отчет *****/
        reportDeclaration.saveReport();

        /***** Проверка всех полей после сохранения документа *****/
        /***** Респондент *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.checkValueToHeader(key, value, s0501408Object.getDataDeclaration()));

        /**** Экспорт товаров *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.checkValueToExportProducts(key, value, s0501408Object.getDataDeclaration()));

        /**** Импорт товаров товаров *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.checkValueToImportProducts(key, value, s0501408Object.getDataDeclaration()));

        /***** Персональные данные *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.checkValueToFooter(key, value, s0501408Object.getDataDeclaration()));

        /***** Отправляем отчет *****/
        reportDeclaration.subscribeAndSendReport();
        assertEquals("Не розшифрований", reportDeclaration.waitReportStatusChange(), "Неверный статус формы");
        reportDeclaration.decryptionReceipt();

    }

    @Story("Создание документа (негативный сценарий) S0501408")
    @Test(description = "Создать новый документ (S0501408) с некорректными данными", dataProvider = "getDataForCreateNegativeDoc")
    public void createNewReportDeclarationS0501408NegativeDoc(String dataProviderValue) {

        /***** Генерим данные для заполнения документа *****/
        s0501408Object = new S0501408Object();

        DocumentTypesListPage documentTypesListPage = new MainPage().openReportTypesListPage();
        documentTypesListPage.checkDocumentTypesListPage();
        documentTypesListPage.searchDocument("S0501408");
        CreateDocumentTypePage document = documentTypesListPage.openDocument("S0501408");

        CreateReportDeclarationS0501408 reportDeclaration = document.openCreateReportDeclarationS0501408();
        reportDeclaration.clickEditButton();

        /***** Респондент *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.setValueToHeader(key, value, s0501408Object.generateDataFromDataProvider(dataProviderValue)));

        /**** Экспорт товаров *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.setValueToExportProducts(key, value, s0501408Object.generateDataFromDataProvider(dataProviderValue)));

        /**** Импорт товаров товаров *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.setValueToImportProducts(key, value, s0501408Object.generateDataFromDataProvider(dataProviderValue)));

        /***** Персональные данные *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.setValueToFooter(key, value, s0501408Object.generateDataFromDataProvider(dataProviderValue)));

        /***** Сохраняем отчет *****/
        reportDeclaration.saveReport();

        /***** Проверка всех полей после сохранения документа *****/
        /***** Респондент *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.checkValueToHeader(key, value, s0501408Object.getDataDeclaration()));

        /**** Экспорт товаров *****/
        reportDeclaration.declarationS0501408Fields.forEach(reportDeclaration::checkValueToExportProducts);

        /**** Импорт товаров товаров *****/
        reportDeclaration.declarationS0501408Fields.forEach(reportDeclaration::checkValueToImportProducts);

        /***** Персональные данные *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.checkValueToFooter(key, value, s0501408Object.getDataDeclaration()));
    }

    @Story("Копирование отчета S0501408")
    @Test(description = "Копирование отчета (S0501408)", enabled = false)
    public void checkCopyReport() {
        /***** Генерим данные для заполнения документа *****/
        s0501408Object = new S0501408Object();

        DocumentTypesListPage documentTypesListPage = new MainPage().openReportTypesListPage();
        documentTypesListPage.checkDocumentTypesListPage();
        documentTypesListPage.searchDocument("S0501408");
        CreateDocumentTypePage document = documentTypesListPage.openDocument("S0501408");

        CreateReportDeclarationS0501408 reportDeclaration = document.openCreateReportDeclarationS0501408();
        reportDeclaration.clickEditButton();

        /***** Респондент *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.setValueToHeader(key, value, s0501408Object.getDataDeclaration()));

        /**** Экспорт товаров *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.setValueToExportProducts(key, value, s0501408Object.getDataDeclaration()));

        /**** Импорт товаров товаров *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.setValueToImportProducts(key, value, s0501408Object.getDataDeclaration()));

        /***** Персональные данные *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.setValueToFooter(key, value, s0501408Object.getDataDeclaration()));

        /***** Сохраняем отчет и копируем его *****/
        reportDeclaration.saveReport();
        reportDeclaration.copyReport();

        /***** Проверяем все поля скопированного отчета *****/
        reportDeclaration.clickEditButton();

        /***** Проверяем поля после копирования отчета *****/
        /***** Экспорт товаров *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.checkValueToExportProducts(key, value, s0501408Object.getDataDeclaration()));

        /***** Импорт товаров *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.checkValueToExportProducts(key, value, s0501408Object.getDataDeclaration()));
    }

    @Story("Проверка редактирования формы документа")
    @Test(description = "Проверка редактирования документа без версий")
    public void checkEditDocType() {
        /***** Генерим тестовые данные *****/
        documentObject = new CreateDocumentObject();
        versionsList = new VersionsObject();

        /***** Тест *****/
        CreateDocumentTypePage typePage = new MainPage().openCreateNewTypePage();
        /***** Создаем документ *****/
        typePage.setDataToDocumentType(documentObject.getDocumentDataFirst());
        typePage.saveCurrentDocAndReturnId();
        MainPage mainPage = typePage.goToMainPage();
        DocumentTypesListPage typesListPage = mainPage.openReportTypesListPage();

        typePage = typesListPage.searchAndOpenDocument(documentObject.getDocumentDataFirst().getDocName());
        typePage.getEditButton().shouldBe(Condition.visible).click();
        /***** Редактируем документ *****/
        typePage.setDataToDocumentType(documentObject.getDocumentDataSecond(), versionsList.getVersionList());
        String docEditId = typePage.saveCurrentDocAndReturnId();
        mainPage = typePage.goToMainPage();
        typesListPage = mainPage.openReportTypesListPage();
        /***** Проверяем документ после редактирования *****/
        typePage = typesListPage.searchAndOpenDocument(documentObject.getDocumentDataSecond().getDocName());
        typePage.checkDocument(documentObject.getDocumentDataSecond(), docEditId, versionsList.getVersionList());
    }

    @Story("Проверка редактирования формы документа")
    @Test(description = "Проверка редактирования документа, с добавлением версий")
    public void checkEditDocTypeAddNewVersion() {
        /***** Генерим тестовые данные *****/
        documentObject = new CreateDocumentObject();
        versionsList = new VersionsObject();

        VersionData versionData = VersionData.builder().date("2018-11-17").comType("Юр. лицо").typeReportPeriod("Квартал").cumulativeTotal(true).build();

        /***** Тест *****/
        CreateDocumentTypePage typePage = new MainPage().openCreateNewTypePage();
        /***** Создаем документ *****/
        typePage.setDataToDocumentType(documentObject.getDocumentDataFirst(), versionsList.getVersionList());
        typePage.saveCurrentDocAndReturnId();
        MainPage mainPage = typePage.goToMainPage();
        DocumentTypesListPage typesListPage = mainPage.openReportTypesListPage();

        typePage = typesListPage.searchAndOpenDocument(documentObject.getDocumentDataFirst().getDocName());
        typePage.getEditButton().shouldBe(Condition.visible).click();
        /***** Редатируем документ (добавляем версию) *****/
        typePage.setDataToDocumentType(documentObject.getDocumentDataSecond(), versionData);
        String docEditId = typePage.saveCurrentDocAndReturnId();
        mainPage = typePage.goToMainPage();
        typesListPage = mainPage.openReportTypesListPage();
        typePage = typesListPage.searchAndOpenDocument(documentObject.getDocumentDataSecond().getDocName());
        /***** Копируем последнюю версию в документе *****/
        typePage.getEditButton().shouldBe(Condition.visible).click();
        typePage.copyLastVersion();
        typePage.saveCurrentDocAndReturnId();

        mainPage = typePage.goToMainPage();
        typesListPage = mainPage.openReportTypesListPage();
        typePage = typesListPage.searchAndOpenDocument(documentObject.getDocumentDataSecond().getDocName());

        /***** Проверяем документ *****/
        versionsList.getVersionList().add(versionData);
        versionsList.getVersionList().add(versionData);
        typePage.checkDocument(documentObject.getDocumentDataSecond(), docEditId, versionsList.getVersionList());

        typePage.getEditButton().shouldBe(Condition.visible).click();
        /***** Редактируем документ (удаляем все версии) *****/
        typePage.setDataToDocumentType(documentObject.getDocumentDataFirst());
        typePage.saveCurrentDocAndReturnId();
        mainPage = typePage.goToMainPage();
        typesListPage = mainPage.openReportTypesListPage();
        typePage = typesListPage.searchAndOpenDocument(documentObject.getDocumentDataFirst().getDocName());
        /***** Проверяем документ *****/
        typePage.checkDocument(documentObject.getDocumentDataFirst(), docEditId);
    }

    @Story("Проверка отображения ошибки при некорректном создании типа документа")
    @Test(description = "Проверка отображения ошибки при создании документа без версии", dataProvider = "incorrectDataForCreateForm")
    public void checkErrorWhileCreateNewDoc(CreateDocumentData documentData) {
        CreateDocumentTypePage typePage = new MainPage().openCreateNewTypePage();
        typePage.setDataToDocumentType(documentData);
        assertEquals("Ошибка", typePage.saveCurrentDocAndReturnId());
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
        /***** Редатируем документ (добавляем версию) *****/
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
        /***** Редатируем документ (добавляем версию) *****/
        typePage.setDataToDocumentType(documentObject.getDocumentDataSecond(), versionsList.getVersionList());
        typePage.cancelChanges();
        typePage.checkDocument(documentObject.getDocumentDataFirst(), docId);
        mainPage = typePage.goToMainPage();
        typesListPage = mainPage.openReportTypesListPage();
        typePage = typesListPage.searchAndOpenDocument(documentObject.getDocumentDataFirst().getDocName());
        typePage.checkDocument(documentObject.getDocumentDataFirst(), docId);
    }

    @Story("Проверка открытия и закрытия документа")
    @Test(description = "Открытие и закрытие документа", enabled = false)
    public void checkOpenAndCloseDocument() {
        DocumentTypesListPage typesListPage = new MainPage().openReportTypesListPage();
        typesListPage.searchDocument("bla_name_1107");
        typesListPage.checkOpenCloseDocument();
    }
}
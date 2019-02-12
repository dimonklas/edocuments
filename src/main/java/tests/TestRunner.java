package tests;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import lombok.extern.log4j.Log4j;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.*;
import pages.document.*;
import utils.AllureOnFailListener;
import utils.IConfigurationVariables;

import java.util.Map;

import static com.codeborne.selenide.Selenide.$;
import static org.testng.Assert.assertEquals;
import static utils.SupportActions.generateRandomFloatNumber;


@Log4j
@Feature("Тестирование документов")
@Listeners(AllureOnFailListener.class)
public class TestRunner extends BaseTest {
    IConfigurationVariables CV = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());

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

    @Story("Проверка сортировки по возростанию")
    @Test(dataProvider = "getDataForSort", description = "Сортировка по значению ->", enabled = false)
    public void checkSortedAsc(String sorted) {
        DocumentTypesListPage documentTypesListPage = new MainPage().openReportTypesListPage();
        documentTypesListPage.checkDocumentTypesListPage();
        addStepToTheReport("Сортировка по " + sorted);
        documentTypesListPage.checkSortingAsc(sorted);
    }

    @Story("Проверка сортировки по убыванию")
    @Test(dataProvider = "getDataForSort", description = "Сортировка по значению ->", enabled = false)
    public void checkSortedDesc(String sorted) {
        DocumentTypesListPage documentTypesListPage = new MainPage().openReportTypesListPage();
        documentTypesListPage.checkDocumentTypesListPage();
        addStepToTheReport("Сортировка по " + sorted);
        documentTypesListPage.checkSortingDesc(sorted);
    }

    @Story("Тестирование поиска")
    @Test(description = "Поиск документа \"S0210110\"")
    public void checkSearchOnDocsTypes() {
        DocumentTypesListPage documentTypesListPage = new MainPage().openReportTypesListPage();
        addStepToTheReport("Поиск по значению S0210110");
        documentTypesListPage.search("S0210110");
    }

    @Story("Создание нового типа без версии документа")
    @Test(description = "Создание нового документа без указания версии")
    public void createNewDocument() {
        addStepToTheReport("Переход на страницу создания документа");
        OpenCreateDocument typePage = new MainPage().openCreateNewTypePage();
        addStepToTheReport("Создания документа");
        String docNumber = typePage.createNewDocumentTypeWithoutVersion();
        MainPage mainPage = typePage.goToMainPage();
        DocumentTypesListPage typesListPage = mainPage.openReportTypesListPage();
        typesListPage.search(docNumber);
        typesListPage.deleteDocument(docNumber);
        typesListPage.search(docNumber);
        Assert.assertTrue($(By.xpath("//*[@id='types_table']//td[text()='Записи отсутствуют.']")).exists());
    }

    @Story("Создание нового типа с версией документа")
    @Test(description = "Создание нового документа с указанием версии")
    public void createNewDocumentWithVersion() {
        addStepToTheReport("Переход на страницу создания документа");
        OpenCreateDocument typePage = new MainPage().openCreateNewTypePage();
        addStepToTheReport("Создания документа");
        String docNumber = typePage.createNewDocumentTypeWithVersion(5);
        MainPage mainPage = typePage.goToMainPage();
        DocumentTypesListPage typesListPage = mainPage.openReportTypesListPage();
        typesListPage.search(docNumber);
        typesListPage.deleteDocument(docNumber);
        typesListPage.search(docNumber);
        Assert.assertTrue($(By.xpath("//*[@id='types_table']//td[text()='Записи отсутствуют.']")).exists());
    }

    @Story("Создание нового типа с несколькими версиями документа")
    @Test(description = "Создание нового документа с указанием версии")
    public void createNewDocumentWithMoreVersion() {
        addStepToTheReport("Переход на страницу создания документа");
        OpenCreateDocument typePage = new MainPage().openCreateNewTypePage();
        addStepToTheReport("Создания документа");
        String docNumber = typePage.createNewDocumentTypeWithVersion(2);
        MainPage mainPage = typePage.goToMainPage();
        DocumentTypesListPage typesListPage = mainPage.openReportTypesListPage();
        typesListPage.search(docNumber);
        typesListPage.deleteDocument(docNumber);
        typesListPage.search(docNumber);
        Assert.assertTrue($(By.xpath("//*[@id='types_table']//td[text()='Записи отсутствуют.']")).exists());
    }

    @Story("Проверка создания копии документа с версией")
    @Test(description = "Создание копии документа с версией")
    public void checkCopyDocumentWithVersion() {
        addStepToTheReport("Переход на страницу создания документа");
        OpenCreateDocument typePage = new MainPage().openCreateNewTypePage();
        addStepToTheReport("Создания документа");
        String docNumber = typePage.createNewDocumentTypeWithVersion(7);
        addStepToTheReport("На главную страницу");
        MainPage mainPage = typePage.goToMainPage();
        addStepToTheReport("Открываем список документов");
        DocumentTypesListPage typesListPage = mainPage.openReportTypesListPage();
        addStepToTheReport("Ищем документ со значением " + docNumber);
        typesListPage.search(docNumber);
        addStepToTheReport("Нажимаем на кнопку \"Копировать\"");
        typePage = typesListPage.clickToCopyButton(docNumber);
        addStepToTheReport("Нажимаем на кнопку \"Сохранить\"");
        typePage.saveCurrentDoc();
        addStepToTheReport("Нажимаем на Tittle страницы и идём переходим на главную страницу");
        mainPage = typePage.goToMainPage();
        addStepToTheReport("Открываем список документов");
        typesListPage = mainPage.openReportTypesListPage();
        addStepToTheReport("Ищем документ со значением " + docNumber);
        typesListPage.search(docNumber);
        addStepToTheReport("Сверяем документы");
        typesListPage.checkTwoRows();
        addStepToTheReport("Удаляем документ со значением " + docNumber);
        typesListPage.deleteDocument(docNumber);
        addStepToTheReport("Удаляем документ со значением " + docNumber);
        typesListPage.deleteDocument(docNumber);
    }

    @Story("Проверка создания копии документа без версии")
    @Test(description = "Создание копии документа без версии")
    public void checkCopyDocumentWithoutVersion() {
        addStepToTheReport("Переход на страницу создания документа");
        OpenCreateDocument typePage = new MainPage().openCreateNewTypePage();
        addStepToTheReport("Создания документа");
        String docNumber = typePage.createNewDocumentTypeWithoutVersion();
        addStepToTheReport("На главную страницу");
        MainPage mainPage = typePage.goToMainPage();
        addStepToTheReport("Открываем список документов");
        DocumentTypesListPage typesListPage = mainPage.openReportTypesListPage();
        addStepToTheReport("Ищем документ со значением " + docNumber);
        typesListPage.search(docNumber);
        addStepToTheReport("Нажимаем на кнопку \"Копировать\"");
        typePage = typesListPage.clickToCopyButton(docNumber);
        addStepToTheReport("Нажимаем на кнопку \"Сохранить\"");
        typePage.saveCurrentDoc();
        addStepToTheReport("Нажимаем на Tittle страницы и идём переходим на главную страницу");
        mainPage = typePage.goToMainPage();
        addStepToTheReport("Открываем список документов");
        typesListPage = mainPage.openReportTypesListPage();
        addStepToTheReport("Ищем документ со значением " + docNumber);
        typesListPage.search(docNumber);
        addStepToTheReport("Сверяем документы");
        typesListPage.checkTwoRows();
        addStepToTheReport("Удаляем документ со значением " + docNumber);
        typesListPage.deleteDocument(docNumber);
        addStepToTheReport("Удаляем документ со значением " + docNumber);
        typesListPage.deleteDocument(docNumber);
    }


    @Story("Создание документа (позитивный сценарий) J0301206")
    @Test(description = "Создать новый документ (J0301206)")
    public void createNewReportDeclarationJ0301206() {

        /***** Генерим данные для заполнения документа *****/
        DeclarationDataGeneralInformationJ0301206 declarationData = DeclarationDataGeneralInformationJ0301206.builder()
                .declarationType(new CreateReportDeclarationJ0301206(CV).chooseReportNewCheckBox)
                .specifiedPeriodType(new CreateReportDeclarationJ0301206(CV).chooseSpecifiedPeriodYear)
                .year(CV.year())
                .comName(CV.comName())
                .zip(CV.zip())
                .sequenceNumber(CV.sequenceNumber())
                .kved(CV.kved())
                .cityCode(CV.cityCode())
                .innNumberOrPassport(CV.innNumberOrPassport())
                .telNumber(CV.telNumber())
                .faxNumber(CV.faxNumber())
                .locationAddress(CV.locationAddress())
                .email(CV.email())
                .controlAuthority(CV.controlAuthority())
                .build();

        /***** Генерим данные для заполнения документа *****/
        DeclarationDataCalculationTaxJ0301206 calculationTax = DeclarationDataCalculationTaxJ0301206.builder()
                .sumName(CV.sumName())
                .square(generateRandomFloatNumber(3))
                .minSalary(generateRandomFloatNumber(2))
                .countDays(generateRandomFloatNumber(0))
                .percent(generateRandomFloatNumber(4))
                .taxSum(generateRandomFloatNumber(2))
                .taxSumSpecified(CV.taxSumSpecified())
                .specifiedSum(CV.specifiedSum())
                .fineSum(CV.fineSum())
                .penaltySum(CV.penaltySum())
                .addText(CV.addText())
                .addDeclaration(CV.addDeclaration())
                .build();

        /***** Генерим данные для заполнения документа *****/
        DeclarationDataPersonInfoJ0301206 personInfo = DeclarationDataPersonInfoJ0301206.builder()
                .dateDeclaration(CV.dateDeclaration())
                .FIO(CV.FIO())
                .inn(CV.inn())
                .accountant(CV.accountant())
                .accountantInn(CV.accountantInn())
                .build();

        addStepToTheReport("Перейти в список документов");
        DocumentTypesListPage documentTypesListPage = new MainPage().openReportTypesListPage();
        addStepToTheReport("Проверка правильнрости перехода");
        documentTypesListPage.checkDocumentTypesListPage();
        addStepToTheReport("Поиск документа с формой \"J0301206\"");
        documentTypesListPage.search("0301206");
        OpenCreateDocument document = documentTypesListPage.openDocument("0301206");

        CreateReportDeclarationJ0301206 reportDeclaration = document.openCreateReportDeclarationJ0301206();
        reportDeclaration.clickEditButton();

        /***** І. Загальні відомості *****/
        declarationData.getDeclarationType().chooseTypeDeclaration();
        declarationData.getSpecifiedPeriodType().chooseReportSpecifiedPeriod();

        reportDeclaration.declarationJ0301206Fields.forEach((key, value) -> reportDeclaration.setValueToGeneralInfo(key, value, declarationData));

        /**** ІІ. Розрахунок податкових зобов'язань збору за місця для паркування транспортних засобів *****/
        reportDeclaration.declarationJ0301206Fields.forEach((key, value) -> reportDeclaration.setValueToCalculationTax(key, value, calculationTax));

        /***** Заполнение персональных данных (футер страницы) *****/
        reportDeclaration.declarationJ0301206Fields.forEach((key, value) -> reportDeclaration.setValueToPersonalInfo(key, value, personInfo));

        /**** Проверка формул в документе *****/
        String expectedTax = reportDeclaration.calculationExpectedTax(calculationTax.getSquare(), calculationTax.getMinSalary(), calculationTax.getCountDays(), calculationTax.getPercent());
        String expectedTaxReportPeriod = reportDeclaration.calculationExpectedTaxForReportPeriod(calculationTax.getSquare(), calculationTax.getMinSalary(), calculationTax.getCountDays(), calculationTax.getPercent(), calculationTax.getTaxSum());
        assertEquals(expectedTax, reportDeclaration.getResultSum(), "Неправильный расчет суммы");
        assertEquals(expectedTaxReportPeriod, reportDeclaration.getResultSumForReportPeriod(), "Неправильный расчет суммы");

        /***** Сохраняем отчет и отправляем *****/
        reportDeclaration.saveReport();
        reportDeclaration.subscribeAndSendReport();
        assertEquals("Надіслано, очікуйте...", reportDeclaration.waitReportStatusChange(), "Неверный статус формы");
    }

    @Story("Проверка создания и удаления новых версий")
    @Test(description = "создание и удаления новых версий в документе")
    public void checkAddVersion() {
        /***** Содание данных для теста *****/
        VersionData version1 = VersionData.builder().date("2018.01.25").comType("Юр. лицо").typeReportPeriod("Квартал").cumulativeTotal(true).build();
        VersionData version2 = VersionData.builder().date("2018.10.05").comType("Физ. лицо").typeReportPeriod("Месяц").cumulativeTotal(false).build();
        VersionData version3 = VersionData.builder().date("2017.09.09").comType("Юр. лицо").typeReportPeriod("Без периода").cumulativeTotal(false).build();

        addStepToTheReport("Переход на страницу создания документа");
        OpenCreateDocument typePage = new MainPage().openCreateNewTypePage();
        addStepToTheReport("Добавление версии 1");
        typePage.addVersionToDocument(version1.getDate(), version1.getComType(), version1.getTypeReportPeriod(), version1.isCumulativeTotal());
        addStepToTheReport("Добавление версии 2");
        typePage.addVersionToDocument(version2.getDate(), version2.getComType(), version2.getTypeReportPeriod(), version2.isCumulativeTotal());
        addStepToTheReport("Добавление версии 3");
        typePage.addVersionToDocument(version3.getDate(), version3.getComType(), version3.getTypeReportPeriod(), version3.isCumulativeTotal());
        addStepToTheReport("Удаление всех версий");
        typePage.deleteAllVersions();
        addStepToTheReport("Проверка отсутствия версий");
        Assert.assertFalse(typePage.checkVersionExists(), "Количество версий документа больше одного");
    }

    @Story("Создание документа (позитивный сценарий) S0501408")
    @Test(description = "Создать новый документ (S0501408)")
    public void createNewReportDeclarationS0501408() {

        /***** Генерим данные для заполнения документа *****/
        DeclarationDataS0501408 dataDeclaration = DeclarationDataS0501408.builder()
                .comEDRPOU(CV.comEDRPOU())
                .comName(CV.comName())
                .locationAddress(CV.locationAddress())
                .locationAddressFact(CV.locationAddressFact())

                .countryNameExport(CV.countryNameExport())
                .countryCodeExport(CV.countryCodeExport())
                .productNameExport(CV.productNameExport())
                .productCodeExport(CV.productCodeExport())
                .currencyNameExport(CV.currencyNameExport())
                .currencyCodeExport(CV.currencyCodeExport())
                .countProductsExport(CV.countProductsExport())
                .productPriceExport(CV.productPriceExport())

                .countryNameImport(CV.countryNameImport())
                .countryCodeImport(CV.countryCodeImport())
                .productNameImport(CV.productNameImport())
                .productCodeImport(CV.productCodeImport())
                .currencyNameImport(CV.currencyNameImport())
                .currencyCodeImport(CV.currencyCodeImport())
                .countProductsImport(CV.countProductsImport())
                .productPriceImport(CV.productPriceImport())

                .fioDir(CV.fioDir())
                .fio(CV.fio())
                .telNumber(CV.telNumber())
                .fax(CV.fax())
                .email(CV.email())
                .build();

        addStepToTheReport("Перейти в список документов");
        DocumentTypesListPage documentTypesListPage = new MainPage().openReportTypesListPage();
        addStepToTheReport("Проверка правильнрости перехода");
        documentTypesListPage.checkDocumentTypesListPage();
        addStepToTheReport("Поиск документа с формой \"S0501408\"");
        documentTypesListPage.search("S0501408");
        OpenCreateDocument document = documentTypesListPage.openDocument("S0501408");

        CreateReportDeclarationS0501408 reportDeclaration = document.openCreateReportDeclarationS0501408();
        reportDeclaration.clickEditButton();

        /***** Респондент *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.setValueToHeader(key, value, dataDeclaration));

        /**** Экспорт товаров *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.setValueToExportProducts(key, value, dataDeclaration));

        /**** Импорт товаров товаров *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.setValueToImportProducts(key, value, dataDeclaration));

        /***** Персональные данные *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.setValueToFooter(key, value, dataDeclaration));

        /***** Сохраняем отчет *****/
        reportDeclaration.saveReport();


        /***** Проверка всех полей после сохранения документа *****/
        /***** Респондент *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.checkValueToHeader(key, value, dataDeclaration));

        /**** Экспорт товаров *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.checktValueToExportProducts(key, value, dataDeclaration));

        /**** Импорт товаров товаров *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.checkValueToImportProducts(key, value, dataDeclaration));

        /***** Персональные данные *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.checkValueToFooter(key, value, dataDeclaration));

        /***** Отправляем отчет *****/
        reportDeclaration.subscribeAndSendReport();
        assertEquals("Не розшифрований", reportDeclaration.waitReportStatusChange(), "Неверный статус формы");
        reportDeclaration.decryptionReceipt();

    }

    @Story("Копирование отчета S0501408")
    @Test(description = "Копирование отчета (S0501408)")
    public void checkCopyReport() {
        /***** Генерим данные для заполнения документа *****/
        DeclarationDataS0501408 dataDeclaration = DeclarationDataS0501408.builder()
                .comEDRPOU(CV.comEDRPOU())
                .comName(CV.comName())
                .locationAddress(CV.locationAddress())
                .locationAddressFact(CV.locationAddressFact())

                .countryNameExport(CV.countryNameExport())
                .countryCodeExport(CV.countryCodeExport())
                .productNameExport(CV.productNameExport())
                .productCodeExport(CV.productCodeExport())
                .currencyNameExport(CV.currencyNameExport())
                .currencyCodeExport(CV.currencyCodeExport())
                .countProductsExport(CV.countProductsExport())
                .productPriceExport(CV.productPriceExport())

                .countryNameImport(CV.countryNameImport())
                .countryCodeImport(CV.countryCodeImport())
                .productNameImport(CV.productNameImport())
                .productCodeImport(CV.productCodeImport())
                .currencyNameImport(CV.currencyNameImport())
                .currencyCodeImport(CV.currencyCodeImport())
                .countProductsImport(CV.countProductsImport())
                .productPriceImport(CV.productPriceImport())

                .fioDir(CV.fioDir())
                .fio(CV.fio())
                .telNumber(CV.telNumber())
                .fax(CV.fax())
                .email(CV.email())
                .build();

        addStepToTheReport("Перейти в список документов");
        DocumentTypesListPage documentTypesListPage = new MainPage().openReportTypesListPage();
        addStepToTheReport("Проверка правильнрости перехода");
        documentTypesListPage.checkDocumentTypesListPage();
        addStepToTheReport("Поиск документа с формой \"S0501408\"");
        documentTypesListPage.search("S0501408");
        OpenCreateDocument document = documentTypesListPage.openDocument("S0501408");

        CreateReportDeclarationS0501408 reportDeclaration = document.openCreateReportDeclarationS0501408();
        reportDeclaration.clickEditButton();

        /***** Респондент *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.setValueToHeader(key, value, dataDeclaration));

        /**** Экспорт товаров *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.setValueToExportProducts(key, value, dataDeclaration));

        /**** Импорт товаров товаров *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.setValueToImportProducts(key, value, dataDeclaration));

        /***** Персональные данные *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.setValueToFooter(key, value, dataDeclaration));


        /***** Сохраняем отчет и копируем его *****/
        reportDeclaration.saveReport();
        reportDeclaration.copyReport();

        /***** Проверяем все поля скопированного отчета *****/
        reportDeclaration.clickEditButton();


        /***** Проверяем поля после копирования отчета *****/
        /***** Экспорт товаров *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.checktValueToExportProducts(key, value, dataDeclaration));

        /***** Импорт товаров *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.checktValueToExportProducts(key, value, dataDeclaration));
    }
}
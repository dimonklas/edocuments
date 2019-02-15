package tests;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import lombok.extern.log4j.Log4j;
import org.aeonbits.owner.ConfigFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.*;
import pages.document.*;
import utils.AllureOnFailListener;
import utils.DateUtil;
import utils.IConfigurationVariables;

import java.util.ArrayList;

import static org.testng.Assert.assertEquals;
import static com.codeborne.selenide.Condition.*;
import static org.testng.Assert.assertTrue;
import static utils.SupportActions.generateRandomFloatNumber;


@Log4j
@Feature("Тестирование документов")
@Listeners(AllureOnFailListener.class)
public class TestRunner extends BaseTest {
    private final IConfigurationVariables CV = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());

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
                {
                        CreateDocumentData.builder().docName("")
                                .service("Фискальная служба").gateway("Шлюз службы статистики").groupJournal(true).finReport(true)
                                .vddoc("123").groupId("777").build()
                },
                {
                        CreateDocumentData.builder().docName(CV.docName() + new DateUtil().getCurrentDateTime("hhmmssSSS"))
                                .service("").gateway("Шлюз пенсионного фонда").groupJournal(false).finReport(true)
                                .vddoc("123").groupId("777").build()
                },
                {
                        CreateDocumentData.builder().docName(CV.docName() + new DateUtil().getCurrentDateTime("hhmmssSSS"))
                                .service("Служба статистики").gateway("").groupJournal(false).finReport(true)
                                .vddoc("123").groupId("777").build()
                },
                {
                        CreateDocumentData.builder().docName(CV.docName() + new DateUtil().getCurrentDateTime("hhmmssSSS"))
                                .service("Пенсионный фонд").gateway("Шлюз пенсионного фонда").groupJournal(false).finReport(true)
                                .vddoc("0").groupId("777").build()
                }
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
        /***** Генерим тестовые данные *****/
        CreateDocumentData data = CreateDocumentData.builder()
                .docName(CV.docName() + new DateUtil().getCurrentDateTime("hhmmssSSS"))
                .service("Фискальная служба")
                .gateway("Шлюз службы статистики")
                .groupJournal(true)
                .finReport(true)
                .vddoc("123")
                .groupId("777")
                .build();

        /***** Тест *****/
        CreateDocumentTypePage typePage = new MainPage().openCreateNewTypePage();
        String docId = typePage.createNewDocumentType(data);
        MainPage mainPage = typePage.goToMainPage();
        DocumentTypesListPage typesListPage = mainPage.openReportTypesListPage();
        typePage = typesListPage.searchAndOpenDocument(data.getDocName());
        typePage.checkDocument(data, docId);
    }

    @Story("Создание нового типа с версией документа")
    @Test(description = "Создание нового документа с указанием версии")
    public void createNewDocumentWithVersion() {
        /***** Генерим тестовые данные *****/
        CreateDocumentData documentData = CreateDocumentData.builder()
                .docName(CV.docName() + new DateUtil().getCurrentDateTime("hhmmssSSS"))
                .service("Фискальная служба")
                .gateway("Шлюз службы статистики")
                .groupJournal(true)
                .finReport(true)
                .vddoc("123")
                .groupId("777")
                .build();
        ArrayList<VersionData> versionList = new ArrayList<>();
        versionList.add(VersionData.builder().date("2018-01-25").comType("Юр. лицо").typeReportPeriod("Квартал").cumulativeTotal(true).build());
        versionList.add(VersionData.builder().date("2019-12-09").comType("Физ. лицо").typeReportPeriod("Без периода").cumulativeTotal(false).build());
        versionList.add(VersionData.builder().date("2018-07-01").comType("Юр. лицо").typeReportPeriod("Месяц").cumulativeTotal(false).build());
        versionList.add(VersionData.builder().date("2018-01-20").comType("Юр. лицо").typeReportPeriod("Квартал").cumulativeTotal(true).build());

        /***** Тест *****/
        CreateDocumentTypePage typePage = new MainPage().openCreateNewTypePage();
        String docId = typePage.createNewDocumentType(documentData, versionList);
        MainPage mainPage = typePage.goToMainPage();
        DocumentTypesListPage typesListPage = mainPage.openReportTypesListPage();
        typePage = typesListPage.searchAndOpenDocument(documentData.getDocName());
        typePage.checkDocument(documentData, docId, versionList);
    }

    @Story("Проверка создания копии документа с версией")
    @Test(description = "Создание копии документа с версией")
    public void checkCopyDocumentWithVersion() {
        /***** Генерим тестовые данные *****/
        CreateDocumentData documentData = CreateDocumentData.builder()
                .docName(CV.docName() + new DateUtil().getCurrentDateTime("hhmmssSSS"))
                .service("Фискальная служба")
                .gateway("Шлюз службы статистики")
                .groupJournal(true)
                .finReport(true)
                .vddoc("123")
                .groupId("777")
                .build();
        ArrayList<VersionData> versionList = new ArrayList<>();
        versionList.add(VersionData.builder().date("2018-01-25").comType("Юр. лицо").typeReportPeriod("Квартал").cumulativeTotal(true).build());
        versionList.add(VersionData.builder().date("2019-01-25").comType("Физ. лицо").typeReportPeriod("Без периода").cumulativeTotal(false).build());
        versionList.add(VersionData.builder().date("2018-01-25").comType("Юр. лицо").typeReportPeriod("Месяц").cumulativeTotal(false).build());
        versionList.add(VersionData.builder().date("2018-01-25").comType("Юр. лицо").typeReportPeriod("Квартал").cumulativeTotal(true).build());

        /***** Тест *****/
        CreateDocumentTypePage typePage = new MainPage().openCreateNewTypePage();
        typePage.createNewDocumentType(documentData, versionList);
        MainPage mainPage = typePage.goToMainPage();
        DocumentTypesListPage typesListPage = mainPage.openReportTypesListPage();
        typesListPage.searchDocument(documentData.getDocName());
        typePage = typesListPage.selectAndCopyDocType(documentData.getDocName());
        String docCopyId = typePage.saveCurrentDocAndReturnId();
        mainPage = typePage.goToMainPage();
        typesListPage = mainPage.openReportTypesListPage();
        typesListPage.searchDocument(documentData.getDocName());
        typesListPage.checkTwoRows();
        typePage = typesListPage.searchAndOpenDocument(docCopyId);
        typePage.checkDocument(documentData, docCopyId, versionList);
    }

    @Story("Проверка создания копии документа без версии")
    @Test(description = "Создание копии документа без версии")
    public void checkCopyDocumentWithoutVersion() {
        /***** Генерим тестовые данные *****/
        CreateDocumentData documentData = CreateDocumentData.builder()
                .docName(CV.docName() + new DateUtil().getCurrentDateTime("hhmmssSSS"))
                .service("Фискальная служба")
                .gateway("Шлюз службы статистики")
                .groupJournal(true)
                .finReport(true)
                .vddoc("123")
                .groupId("777")
                .build();

        /***** Тест *****/
        CreateDocumentTypePage typePage = new MainPage().openCreateNewTypePage();
        typePage.createNewDocumentType(documentData);
        MainPage mainPage = typePage.goToMainPage();
        DocumentTypesListPage typesListPage = mainPage.openReportTypesListPage();
        typesListPage.searchDocument(documentData.getDocName());
        typePage = typesListPage.selectAndCopyDocType(documentData.getDocName());
        String docCopyId = typePage.saveCurrentDocAndReturnId();
        mainPage = typePage.goToMainPage();
        typesListPage = mainPage.openReportTypesListPage();
        typesListPage.searchDocument(documentData.getDocName());
        typesListPage.checkTwoRows();
        typePage = typesListPage.searchAndOpenDocument(docCopyId);
        typePage.checkDocument(documentData, docCopyId);
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

        DocumentTypesListPage documentTypesListPage = new MainPage().openReportTypesListPage();
        documentTypesListPage.checkDocumentTypesListPage();
        documentTypesListPage.searchDocument("0301206");
        CreateDocumentTypePage document = documentTypesListPage.openDocument("0301206");

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
        assertEquals(expectedTax, reportDeclaration.getResultSum().getValue(), "Неправильный расчет суммы");
        assertEquals(expectedTaxReportPeriod, reportDeclaration.getResultSumForReportPeriod().getValue(), "Неправильный расчет суммы");

        /***** Сохраняем отчет *****/
        reportDeclaration.saveReport();

        /***** Проверяем данные после сохраниния *****/
        reportDeclaration.declarationJ0301206Fields.forEach((key, value) -> reportDeclaration.checkValueToGeneralInfo(key, value, declarationData));
        reportDeclaration.declarationJ0301206Fields.forEach((key, value) -> reportDeclaration.checkValueToCalculationTax(key, value, calculationTax));
        reportDeclaration.declarationJ0301206Fields.forEach((key, value) -> reportDeclaration.checkValueToPersonalInfo(key, value, personInfo));

        /***** Отправляем отчет *****/
        reportDeclaration.subscribeAndSendReport();
        assertEquals("Надіслано, очікуйте...", reportDeclaration.waitReportStatusChange(), "Неверный статус формы");
    }

    @Story("Создание документа (негативный сценарий) J0301206")
    @Test(description = "Создать новый документ (J0301206) с некорректными данными", dataProvider = "getDataForCreateNegativeDoc")
    public void createNewReportDeclarationNegativeJ0301206(String dataProviderValue) {

        /***** Генерим данные для заполнения документа *****/
        DeclarationDataGeneralInformationJ0301206 declarationData = DeclarationDataGeneralInformationJ0301206.builder()
                .declarationType(new CreateReportDeclarationJ0301206(CV).chooseReportNewCheckBox)
                .specifiedPeriodType(new CreateReportDeclarationJ0301206(CV).chooseSpecifiedPeriodYear)
                .year(dataProviderValue)
                .comName(CV.comName())
                .zip(dataProviderValue)
                .sequenceNumber(dataProviderValue)
                .kved(CV.kved())
                .cityCode(dataProviderValue)
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
                .square(dataProviderValue)
                .minSalary(dataProviderValue)
                .countDays(dataProviderValue)
                .percent(dataProviderValue)
                .taxSum(dataProviderValue)
                .taxSumSpecified(dataProviderValue)
                .specifiedSum(dataProviderValue)
                .fineSum(dataProviderValue)
                .penaltySum(dataProviderValue)
                .addText(CV.addText())
                .addDeclaration(dataProviderValue)
                .build();

        /***** Генерим данные для заполнения документа *****/
        DeclarationDataPersonInfoJ0301206 personInfo = DeclarationDataPersonInfoJ0301206.builder()
                .dateDeclaration(dataProviderValue)
                .FIO(CV.FIO())
                .inn(CV.inn())
                .accountant(CV.accountant())
                .accountantInn(CV.accountantInn())
                .build();

        DocumentTypesListPage documentTypesListPage = new MainPage().openReportTypesListPage();
        documentTypesListPage.checkDocumentTypesListPage();
        documentTypesListPage.searchDocument("0301206");
        CreateDocumentTypePage document = documentTypesListPage.openDocument("0301206");

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

        DocumentTypesListPage documentTypesListPage = new MainPage().openReportTypesListPage();
        documentTypesListPage.checkDocumentTypesListPage();
        documentTypesListPage.searchDocument("S0501408");
        CreateDocumentTypePage document = documentTypesListPage.openDocument("S0501408");

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
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.checkValueToExportProducts(key, value, dataDeclaration));

        /**** Импорт товаров товаров *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.checkValueToImportProducts(key, value, dataDeclaration));

        /***** Персональные данные *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.checkValueToFooter(key, value, dataDeclaration));

        /***** Отправляем отчет *****/
        reportDeclaration.subscribeAndSendReport();
        assertEquals("Не розшифрований", reportDeclaration.waitReportStatusChange(), "Неверный статус формы");
        reportDeclaration.decryptionReceipt();

    }

    @Story("Создание документа (негативный сценарий) S0501408")
    @Test(description = "Создать новый документ (S0501408) с некорректными данными", dataProvider = "getDataForCreateNegativeDoc")
    public void createNewReportDeclarationS0501408NegativeDoc(String dataProviderValue) {

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
                .countProductsExport(dataProviderValue)
                .productPriceExport(dataProviderValue)

                .countryNameImport(CV.countryNameImport())
                .countryCodeImport(CV.countryCodeImport())
                .productNameImport(CV.productNameImport())
                .productCodeImport(CV.productCodeImport())
                .currencyNameImport(CV.currencyNameImport())
                .currencyCodeImport(CV.currencyCodeImport())
                .countProductsImport(dataProviderValue)
                .productPriceImport(dataProviderValue)

                .fioDir(CV.fioDir())
                .fio(CV.fio())
                .telNumber(CV.telNumber())
                .fax(CV.fax())
                .email(CV.email())
                .build();

        DocumentTypesListPage documentTypesListPage = new MainPage().openReportTypesListPage();
        documentTypesListPage.checkDocumentTypesListPage();
        documentTypesListPage.searchDocument("S0501408");
        CreateDocumentTypePage document = documentTypesListPage.openDocument("S0501408");

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
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.checkValueToExportProducts(key, value));

        /**** Импорт товаров товаров *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.checkValueToImportProducts(key, value));

        /***** Персональные данные *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.checkValueToFooter(key, value, dataDeclaration));
    }

    @Story("Копирование отчета S0501408")
    @Test(description = "Копирование отчета (S0501408)", enabled = false)
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

        DocumentTypesListPage documentTypesListPage = new MainPage().openReportTypesListPage();
        documentTypesListPage.checkDocumentTypesListPage();
        documentTypesListPage.searchDocument("S0501408");
        CreateDocumentTypePage document = documentTypesListPage.openDocument("S0501408");

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
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.checkValueToExportProducts(key, value, dataDeclaration));

        /***** Импорт товаров *****/
        reportDeclaration.declarationS0501408Fields.forEach((key, value) -> reportDeclaration.checkValueToExportProducts(key, value, dataDeclaration));
    }

    @Story("Проверка редактирования формы документа")
    @Test(description = "Проверка редактирования документа без версий")
    public void checkEditDocType() {
        /***** Генерим тестовые данные *****/
        CreateDocumentData documentDataOriginal = CreateDocumentData.builder()
                .docName(CV.docName() + new DateUtil().getCurrentDateTime("hhmmssSSS"))
                .service("Фискальная служба")
                .gateway("Шлюз службы статистики")
                .groupJournal(true)
                .finReport(true)
                .vddoc("123")
                .groupId("777")
                .build();

        CreateDocumentData documentDataEdit = CreateDocumentData.builder()
                .docName(CV.docName() + new DateUtil().getCurrentDateTime("hhmmssSSS"))
                .service("Служба статистики")
                .gateway("Шлюз пенсионного фонда")
                .groupJournal(false)
                .finReport(false)
                .vddoc("2775")
                .groupId("552288")
                .build();
        ArrayList<VersionData> versionList = new ArrayList<>();
        versionList.add(VersionData.builder().date("2018-01-25").comType("Юр. лицо").typeReportPeriod("Квартал").cumulativeTotal(true).build());
        versionList.add(VersionData.builder().date("2019-01-25").comType("Физ. лицо").typeReportPeriod("Без периода").cumulativeTotal(false).build());
        versionList.add(VersionData.builder().date("2018-01-25").comType("Юр. лицо").typeReportPeriod("Месяц").cumulativeTotal(false).build());
        versionList.add(VersionData.builder().date("2018-01-25").comType("Юр. лицо").typeReportPeriod("Квартал").cumulativeTotal(true).build());

        /***** Тест *****/
        CreateDocumentTypePage typePage = new MainPage().openCreateNewTypePage();
        /***** Создаем документ *****/
        typePage.createNewDocumentType(documentDataOriginal);
        MainPage mainPage = typePage.goToMainPage();
        DocumentTypesListPage typesListPage = mainPage.openReportTypesListPage();

        typePage = typesListPage.searchAndOpenDocument(documentDataOriginal.getDocName());
        typePage.getEditButton().shouldBe(Condition.visible).click();
        /***** Редактируем документ *****/
        String docEditId = typePage.createNewDocumentType(documentDataEdit, versionList);
        mainPage = typePage.goToMainPage();
        typesListPage = mainPage.openReportTypesListPage();
        /***** Проверяем документ после редактирования *****/
        typePage = typesListPage.searchAndOpenDocument(documentDataEdit.getDocName());
        typePage.checkDocument(documentDataEdit, docEditId, versionList);
    }

    @Story("Проверка редактирования формы документа")
    @Test(description = "Проверка редактирования документа, с добавлением версий")
    public void checkEditDocTypeAddNewVersion() {
        /***** Генерим тестовые данные *****/
        CreateDocumentData documentDataOriginal = CreateDocumentData.builder()
                .docName(CV.docName() + new DateUtil().getCurrentDateTime("hhmmssSSS"))
                .service("Фискальная служба")
                .gateway("Шлюз службы статистики")
                .groupJournal(true)
                .finReport(true)
                .vddoc("123")
                .groupId("777")
                .build();

        CreateDocumentData documentDataEdit = CreateDocumentData.builder()
                .docName(CV.docName() + new DateUtil().getCurrentDateTime("hhmmssSSS"))
                .service("Служба статистики")
                .gateway("Шлюз пенсионного фонда")
                .groupJournal(false)
                .finReport(false)
                .vddoc("2775")
                .groupId("552288")
                .build();
        ArrayList<VersionData> versionListOriginal = new ArrayList<>();
        versionListOriginal.add(VersionData.builder().date("2018-01-31").comType("Юр. лицо").typeReportPeriod("Квартал").cumulativeTotal(true).build());
        versionListOriginal.add(VersionData.builder().date("2020-02-29").comType("Физ. лицо").typeReportPeriod("Без периода").cumulativeTotal(false).build());
        versionListOriginal.add(VersionData.builder().date("2018-12-23").comType("Юр. лицо").typeReportPeriod("Месяц").cumulativeTotal(false).build());
        versionListOriginal.add(VersionData.builder().date("2018-07-30").comType("Юр. лицо").typeReportPeriod("Квартал").cumulativeTotal(true).build());

        VersionData versionData = VersionData.builder().date("2018-11-17").comType("Юр. лицо").typeReportPeriod("Квартал").cumulativeTotal(true).build();

        /***** Тест *****/
        CreateDocumentTypePage typePage = new MainPage().openCreateNewTypePage();
        /***** Создаем документ *****/
        typePage.createNewDocumentType(documentDataOriginal, versionListOriginal);
        MainPage mainPage = typePage.goToMainPage();
        DocumentTypesListPage typesListPage = mainPage.openReportTypesListPage();

        typePage = typesListPage.searchAndOpenDocument(documentDataOriginal.getDocName());
        typePage.getEditButton().shouldBe(Condition.visible).click();
        /***** Редатируем документ (добавляем версию) *****/
        String docCopyId = typePage.createNewDocumentType(documentDataEdit, versionData);
        mainPage = typePage.goToMainPage();
        typesListPage = mainPage.openReportTypesListPage();
        typePage = typesListPage.searchAndOpenDocument(documentDataEdit.getDocName());

        /***** Проверяем документ *****/
        versionListOriginal.add(versionData);
        typePage.checkDocument(documentDataEdit, docCopyId, versionListOriginal);

        typePage.getEditButton().shouldBe(Condition.visible).click();
        /***** Редактируем документ (удаляем все версии) *****/
        typePage.createNewDocumentType(documentDataOriginal);
        mainPage = typePage.goToMainPage();
        typesListPage = mainPage.openReportTypesListPage();
        typePage = typesListPage.searchAndOpenDocument(documentDataOriginal.getDocName());
        /***** Проверяем документ *****/
        typePage.checkDocument(documentDataOriginal, docCopyId);
    }

    @Story("Проверка отображения ошибки при некорректном создании типа документа")
    @Test(description = "Проверка отображения ошибки при создании документа без версии", dataProvider = "incorrectDataForCreateForm")
    public void checkErrorWhileCreateNewDoc(CreateDocumentData documentData) {
        CreateDocumentTypePage typePage = new MainPage().openCreateNewTypePage();
        assertEquals("Ошибка", typePage.createNewDocumentType(documentData));
    }

    @Story("Проверка отображения ошибки при некорректном создании типа документа")
    @Test(description = "Проверка отображения ошибки при создании документа с версиями", dataProvider = "incorrectDataForCreateForm")
    public void checkErrorWhileCreateNewDocWithVersions(CreateDocumentData documentData) {
        ArrayList<VersionData> versionList = new ArrayList<>();
        versionList.add(VersionData.builder().date("2018-01-25").comType("Юр. лицо").typeReportPeriod("Квартал").cumulativeTotal(true).build());
        versionList.add(VersionData.builder().date("2019-01-25").comType("Физ. лицо").typeReportPeriod("Без периода").cumulativeTotal(false).build());

        CreateDocumentTypePage typePage = new MainPage().openCreateNewTypePage();
        assertEquals("Ошибка", typePage.createNewDocumentType(documentData, versionList));
    }
}
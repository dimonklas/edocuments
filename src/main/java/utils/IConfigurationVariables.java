package utils;

import org.aeonbits.owner.Config;

import static org.aeonbits.owner.Config.*;

/**
 * http://owner.aeonbits.org/docs/importing-properties/
 * библиотека для чтения пропертей, аннотацией @Sources указывается порядок чтения файлов пропертей
 */

@LoadPolicy(Config.LoadType.MERGE)
@Sources({
        "classpath:config.properties",
        "classpath:testData.properties"
})

public interface IConfigurationVariables extends Config {
    String mainPageUrl();

    String userLogin();

    String userPassword();

    String CurrentBrowser();

    String urlPromin();

    String techLogin();

    String techPassword();

    /****** Тестовые данные для J0301206****/
    /****** Тестовые данные для "Загальних відомостей"****/
    String year();

    String sequenceNumber();

    String comName();

    String innNumberOrPassport();

    String kved();

    String zip();

    String cityCode();

    String telNumber();

    String faxNumber();

    String locationAddress();

    String email();

    Integer controlAuthority();


    /****** Тестовые данные для "Розрахунок податкових зобов'язань збору за місця для паркування транспортних засобів"****/
    String sumName();

    String square();

    String minSalary();

    String countDays();

    String percent();

    String taxSum();

    String taxSumSpecified();

    String specifiedSum();

    String fineSum();

    String penaltySum();

    String addText();

    String addDeclaration();

    /****** Тестовые данные для "Персональные данные"****/
    String dateDeclaration();

    String FIO();

    String inn();

    String accountant();

    String accountantInn();


    /****** Тестовые данные для S0501408****/
    /****** Тестовые данные для "Респондент"****/
    String comEDRPOU();

    String locationAddressFact();

    /**** Экспорт товаров *****/
    String countryNameExport();

    String countryCodeExport();

    String productNameExport();

    String productCodeExport();

    String currencyNameExport();

    String currencyCodeExport();

    String countProductsExport();

    String productPriceExport();

    /**** Импорт товаров товаров *****/
    String countryNameImport();

    String countryCodeImport();

    String productNameImport();

    String productCodeImport();

    String currencyNameImport();

    String currencyCodeImport();

    String countProductsImport();

    String productPriceImport();

    /***** Персональные данные *****/
    String fioDir();

    String fio();

    String fax();

    String pbKey();

    String pbKeyPassword();

    String fileS0501408xsd();

    /***** Создание отчета *****/
    String docName();

}

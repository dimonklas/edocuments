package pages.document;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DeclarationDataS0501408 {

    /***** Респондент *****/
    String comEDRPOU;
    String comName;
    String locationAddress;
    String locationAddressFact;

    /**** Экспорт товаров *****/
    String countryNameExport;
    String countryCodeExport;
    String productNameExport;
    String productCodeExport;
    String currencyNameExport;
    String currencyCodeExport;
    String countProductsExport;
    String productPriceExport;

    /**** Импорт товаров товаров *****/
    String countryNameImport;
    String countryCodeImport;
    String productNameImport;
    String productCodeImport;
    String currencyNameImport;
    String currencyCodeImport;
    String countProductsImport;
    String productPriceImport;

    /***** Персональные данные *****/
    String fioDir;
    String fio;
    String telNumber;
    String fax;
    String email;
}

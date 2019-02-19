package pages.documentObjects;

import lombok.AccessLevel;
import lombok.Getter;
import org.aeonbits.owner.ConfigFactory;
import pages.document.DeclarationDataS0501408;
import utils.IConfigurationVariables;

@Getter
public class S0501408Object {

    @Getter(AccessLevel.NONE)
    private final IConfigurationVariables CV = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());

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

    public DeclarationDataS0501408 generateDataFromDataProvider(String dataProviderValue) {
        return dataDeclaration = DeclarationDataS0501408.builder()
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
    }
}

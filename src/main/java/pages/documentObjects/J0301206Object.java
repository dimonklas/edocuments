package pages.documentObjects;

import lombok.AccessLevel;
import lombok.Getter;
import org.aeonbits.owner.ConfigFactory;
import pages.CreateReportDeclarationJ0301206;
import pages.document.j0301206.DeclarationDataCalculationTaxJ0301206;
import pages.document.j0301206.DeclarationDataGeneralInformationJ0301206;
import pages.document.j0301206.DeclarationDataPersonInfoJ0301206;
import utils.IConfigurationVariables;

import static utils.SupportActions.generateRandomFloatNumber;

@Getter
public class J0301206Object {

    @Getter(AccessLevel.NONE)
    private final IConfigurationVariables CV = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());

    /***** Генерим данные для заполнения документа *****/
    DeclarationDataGeneralInformationJ0301206 declarationData = DeclarationDataGeneralInformationJ0301206.builder()
            .declarationType(new CreateReportDeclarationJ0301206().chooseReportNewCheckBox)
            .specifiedPeriodType(new CreateReportDeclarationJ0301206().chooseSpecifiedPeriodYear)
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

    public DeclarationDataGeneralInformationJ0301206 generateGeneralInfoFromDataProvider(String dataProviderValue) {
        return declarationData = DeclarationDataGeneralInformationJ0301206.builder()
                .declarationType(new CreateReportDeclarationJ0301206().chooseReportNewCheckBox)
                .specifiedPeriodType(new CreateReportDeclarationJ0301206().chooseSpecifiedPeriodYear)
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
    }

    public DeclarationDataCalculationTaxJ0301206 generateCalculationTaxFromDataProvider(String dataProviderValue) {
        return calculationTax = DeclarationDataCalculationTaxJ0301206.builder()
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
    }

    public DeclarationDataPersonInfoJ0301206 generatePersonalInfoFromDataProvider(String dataProviderValue) {
        return personInfo = DeclarationDataPersonInfoJ0301206.builder()
                .dateDeclaration(dataProviderValue)
                .FIO(CV.FIO())
                .inn(CV.inn())
                .accountant(CV.accountant())
                .accountantInn(CV.accountantInn())
                .build();
    }

}

package pages.document.j0301206;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DeclarationDataGeneralInformationJ0301206 {
    TaxDeclarationType declarationType;
    ReportPeriodType periodType;
    ReportPeriodSpecifiedType specifiedPeriodType;
    String year;
    String sequenceNumber;
    String comName;
    String innNumberOrPassport;
    String kved;
    String zip;
    String cityCode;
    String telNumber;
    String faxNumber;
    String locationAddress;
    String email;
    Integer controlAuthority;
}
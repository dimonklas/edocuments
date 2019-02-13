package pages.document;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class VersionData {
    int numDoc;
    String date;
    String comType;
    String typeReportPeriod;
    boolean cumulativeTotal;
}

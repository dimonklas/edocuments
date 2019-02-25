package pages.documentObjects;

import lombok.AccessLevel;
import lombok.Getter;
import org.aeonbits.owner.ConfigFactory;
import pages.document.VersionData;
import utils.IConfigurationVariables;

import java.util.ArrayList;

@Getter
public class VersionsObject {

    @Getter(AccessLevel.NONE)
    private final IConfigurationVariables CV = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());

    private ArrayList<VersionData> versionList = new ArrayList<>();

    public VersionsObject() {
        versionList.add(VersionData.builder().date("2018-01-25").comType("Юр. лицо").typeReportPeriod("Квартал").cumulativeTotal(true).build());
        versionList.add(VersionData.builder().date("2019-12-09").comType("Физ. лицо").typeReportPeriod("Без периода").cumulativeTotal(false).build());
        versionList.add(VersionData.builder().date("2018-07-01").comType("Юр. лицо").typeReportPeriod("Месяц").cumulativeTotal(false).build());
        versionList.add(VersionData.builder().date("2018-01-20").comType("Юр. лицо").typeReportPeriod("Квартал").cumulativeTotal(true).build());
    }
}

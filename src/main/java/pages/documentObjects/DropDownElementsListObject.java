package pages.documentObjects;

import org.aeonbits.owner.ConfigFactory;
import pages.document.dropDownListData.DropDownListData;
import pages.document.dropDownListData.ElementListData;
import utils.DateUtil;
import utils.IConfigurationVariables;

import java.util.ArrayList;

public class DropDownElementsListObject {
    private final IConfigurationVariables CV = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());

    public DropDownListData getListDataObject() {
        return DropDownListData.builder()
                .name(CV.docName() + new DateUtil().getCurrentDateTime("hhmmssSSS"))
                .elementListData(getElementList()).build();
    }

    public DropDownListData getListDataObjectNegative() {
        return DropDownListData.builder()
                .name(CV.docName() + new DateUtil().getCurrentDateTime("hhmmssSSS"))
                .elementListData(getElementListNegative()).build();
    }


    private ArrayList<ElementListData> getElementList() {
        ArrayList<ElementListData> arrayList = new ArrayList<>();

        arrayList.add(ElementListData.builder().key("testKey1").value("testValue1").build());
        arrayList.add(ElementListData.builder().key("testKey2").value("testValue2").build());
        arrayList.add(ElementListData.builder().key("testKey3").value("testValue3").build());
        arrayList.add(ElementListData.builder().key("testKey4").value("testValue4").build());
        arrayList.add(ElementListData.builder().key("testKey5").value("testValue5").build());
        arrayList.add(ElementListData.builder().key("testKey6").value("testValue5").build());

        return arrayList;
    }

    private ArrayList<ElementListData> getElementListNegative() {
        ArrayList<ElementListData> arrayList = new ArrayList<>();

        arrayList.add(ElementListData.builder().key("testKey1").value("testValue1").build());
        arrayList.add(ElementListData.builder().key("testKey2").value("testValue2").build());
        arrayList.add(ElementListData.builder().key("testKey3").value("testValue3").build());
        arrayList.add(ElementListData.builder().key("testKey4").value("testValue4").build());
        arrayList.add(ElementListData.builder().key("testKey5").value("testValue5").build());
        arrayList.add(ElementListData.builder().key("testKey5").value("testValue6").build());

        return arrayList;
    }
}

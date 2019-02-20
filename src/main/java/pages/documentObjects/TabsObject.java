package pages.documentObjects;

import lombok.AccessLevel;
import lombok.Getter;
import org.aeonbits.owner.ConfigFactory;
import pages.document.TabsData;
import utils.IConfigurationVariables;

import java.util.ArrayList;

@Getter
public class TabsObject {

    @Getter(AccessLevel.NONE)
    private final IConfigurationVariables CV = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());

    public ArrayList<ArrayList<TabsData>> tabsArray() {

        ArrayList<ArrayList<TabsData>> arrayLists = new ArrayList<>();

        arrayLists.add(getTabsObject1());
        arrayLists.add(getTabsObject2());
        arrayLists.add(getTabsObject3());
        arrayLists.add(getTabsObject4());
        arrayLists.add(getTabsObject5());

        return arrayLists;
    }

    public ArrayList<ArrayList<TabsData>> tabsArrayNegative() {

        ArrayList<ArrayList<TabsData>> arrayLists = new ArrayList<>();

        arrayLists.add(getTabsObject1());
        arrayLists.add(getTabsObject6());

        return arrayLists;
    }

    private ArrayList<TabsData> getTabsObject1() {
        ArrayList<TabsData> tabsArrayObjects = new ArrayList<>();

        tabsArrayObjects.add(TabsData.builder().name("").formCode("J0100114").build());
        tabsArrayObjects.add(TabsData.builder().name("Тест1").formCode("S0501408").build());
        tabsArrayObjects.add(TabsData.builder().name("Тест під номером 1").formCode("S0100113").build());
        tabsArrayObjects.add(TabsData.builder().name("Test 1").formCode("S0100113").build());

        return tabsArrayObjects;
    }

    private ArrayList<TabsData> getTabsObject2() {
        ArrayList<TabsData> tabsArrayObjects = new ArrayList<>();

        tabsArrayObjects.add(TabsData.builder().name("").formCode("J0100114").build());
        tabsArrayObjects.add(TabsData.builder().name("Тест2").formCode("S0501408").build());
        tabsArrayObjects.add(TabsData.builder().name("Тест під номером 2").formCode("S0100113").build());
        tabsArrayObjects.add(TabsData.builder().name("Test 2").formCode("S0100113").build());

        return tabsArrayObjects;
    }

    private ArrayList<TabsData> getTabsObject3() {
        ArrayList<TabsData> tabsArrayObjects = new ArrayList<>();

        tabsArrayObjects.add(TabsData.builder().name("").formCode("J0100114").build());
        tabsArrayObjects.add(TabsData.builder().name("Тест3").formCode("S0501408").build());
        tabsArrayObjects.add(TabsData.builder().name("Тест під номером 4").formCode("S0100113").build());
        tabsArrayObjects.add(TabsData.builder().name("Test 3").formCode("S0100113").build());
        tabsArrayObjects.add(TabsData.builder().name("Test 3-1").formCode("S0100113").build());
        tabsArrayObjects.add(TabsData.builder().name("Test 3-2").formCode("S0100113").build());
        tabsArrayObjects.add(TabsData.builder().name("Test 3-3").formCode("S0100113").build());

        return tabsArrayObjects;
    }

    private ArrayList<TabsData> getTabsObject4() {
        ArrayList<TabsData> tabsArrayObjects = new ArrayList<>();

        tabsArrayObjects.add(TabsData.builder().name("").formCode("J0100114").build());
        tabsArrayObjects.add(TabsData.builder().name("Тест4").formCode("S0501408").build());
        tabsArrayObjects.add(TabsData.builder().name("Тест під номером 4").formCode("S0100113").build());
        tabsArrayObjects.add(TabsData.builder().name("Test 4").formCode("S0100113").build());

        return tabsArrayObjects;
    }

    private ArrayList<TabsData> getTabsObject5() {
        ArrayList<TabsData> tabsArrayObjects = new ArrayList<>();

        tabsArrayObjects.add(TabsData.builder().name("").formCode("J0100114").build());
        tabsArrayObjects.add(TabsData.builder().name("Тест5").formCode("S0501408").build());
        tabsArrayObjects.add(TabsData.builder().name("Тест під номером 5").formCode("S0100113").build());
        tabsArrayObjects.add(TabsData.builder().name("Test 5").formCode("S0100113").build());

        return tabsArrayObjects;
    }

    private ArrayList<TabsData> getTabsObject6() {
        ArrayList<TabsData> tabsArrayObjects = new ArrayList<>();

        tabsArrayObjects.add(TabsData.builder().name("").formCode("------").build());
        tabsArrayObjects.add(TabsData.builder().name("").formCode("321").build());

        return tabsArrayObjects;
    }
}

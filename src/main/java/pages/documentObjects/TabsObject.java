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

    ArrayList<ArrayList<TabsData>> arrayLists = new ArrayList<>();

    public TabsObject() {
        arrayLists.add(TabsObject1());
        arrayLists.add(TabsObject2());
        arrayLists.add(TabsObject3());
        arrayLists.add(TabsObject4());
        arrayLists.add(TabsObject5());
    }

    private ArrayList<TabsData> TabsObject1() {
        ArrayList<TabsData> tabsArrayObjects1 = new ArrayList<>();

        tabsArrayObjects1.add(TabsData.builder().name("").formCode("J0100114").build());
        tabsArrayObjects1.add(TabsData.builder().name("Тест1").formCode("S0501408").build());
        tabsArrayObjects1.add(TabsData.builder().name("Тест під номером 1").formCode("S0100113").build());
        tabsArrayObjects1.add(TabsData.builder().name("Test 1").formCode("S0100113").build());

        return tabsArrayObjects1;
    }

    private ArrayList<TabsData> TabsObject2() {
        ArrayList<TabsData> tabsArrayObjects2 = new ArrayList<>();

        tabsArrayObjects2.add(TabsData.builder().name("").formCode("J0100114").build());
        tabsArrayObjects2.add(TabsData.builder().name("Тест2").formCode("S0501408").build());
        tabsArrayObjects2.add(TabsData.builder().name("Тест під номером 2").formCode("S0100113").build());
        tabsArrayObjects2.add(TabsData.builder().name("Test 2").formCode("S0100113").build());

        return tabsArrayObjects2;
    }

    private ArrayList<TabsData> TabsObject3() {
        ArrayList<TabsData> tabsArrayObjects3 = new ArrayList<>();

        tabsArrayObjects3.add(TabsData.builder().name("").formCode("J0100114").build());
        tabsArrayObjects3.add(TabsData.builder().name("Тест3").formCode("S0501408").build());
        tabsArrayObjects3.add(TabsData.builder().name("Тест під номером 4").formCode("S0100113").build());
        tabsArrayObjects3.add(TabsData.builder().name("Test 3").formCode("S0100113").build());
        tabsArrayObjects3.add(TabsData.builder().name("Test 3-1").formCode("S0100113").build());
        tabsArrayObjects3.add(TabsData.builder().name("Test 3-2").formCode("S0100113").build());
        tabsArrayObjects3.add(TabsData.builder().name("Test 3-3").formCode("S0100113").build());

        return tabsArrayObjects3;
    }

    private ArrayList<TabsData> TabsObject4() {
        ArrayList<TabsData> tabsArrayObjects3 = new ArrayList<>();

        tabsArrayObjects3.add(TabsData.builder().name("").formCode("J0100114").build());
        tabsArrayObjects3.add(TabsData.builder().name("Тест4").formCode("S0501408").build());
        tabsArrayObjects3.add(TabsData.builder().name("Тест під номером 4").formCode("S0100113").build());
        tabsArrayObjects3.add(TabsData.builder().name("Test 4").formCode("S0100113").build());

        return tabsArrayObjects3;
    }

    private ArrayList<TabsData> TabsObject5() {
        ArrayList<TabsData> tabsArrayObjects3 = new ArrayList<>();

        tabsArrayObjects3.add(TabsData.builder().name("").formCode("J0100114").build());
        tabsArrayObjects3.add(TabsData.builder().name("Тест5").formCode("S0501408").build());
        tabsArrayObjects3.add(TabsData.builder().name("Тест під номером 5").formCode("S0100113").build());
        tabsArrayObjects3.add(TabsData.builder().name("Test 5").formCode("S0100113").build());

        return tabsArrayObjects3;
    }
}

package tests;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import lombok.extern.log4j.Log4j;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.CreateDropDownListPage;
import pages.DropDownListPage;
import pages.MainPage;
import pages.document.dropDownListData.DropDownListData;
import pages.documentObjects.DropDownElementsListObject;
import utils.AllureOnFailListener;

import java.util.stream.IntStream;

import static org.testng.Assert.assertEquals;

@Log4j
@Feature("Тестирование выпадающего списка")
@Listeners(AllureOnFailListener.class)
public class DropDownRunner extends BaseTest {
    @Story("Создание выпадающего списка")
    @Test(description = "Создание выпадающего списка")
    public void checkAddDropDownElement() {
        DropDownListData listData = new DropDownElementsListObject().getListDataObject();

        DropDownListPage dropDownListPage = new MainPage().openDropDownList();
        CreateDropDownListPage createDropDownListPage = dropDownListPage.openCreateNewDropDownList();
        createDropDownListPage.createNewDropDownList(listData);
        String idList = createDropDownListPage.saveCurrentDocAndReturnId();
        createDropDownListPage.goToMainPage();

        dropDownListPage = new MainPage().openDropDownList();
        createDropDownListPage = dropDownListPage.searchAndOpenDocument(idList);
        createDropDownListPage.checkDropDownList(listData, idList);
    }

    @Story("Создание выпадающего списка (негативный сценарий)")
    @Test(description = "Создание выпадающего списка (негативный сценарий)")
    public void checkAddDropDownElementNegative() {
        DropDownListData listData = new DropDownElementsListObject().getListDataObjectNegative();

        DropDownListPage dropDownListPage = new MainPage().openDropDownList();
        CreateDropDownListPage createDropDownListPage = dropDownListPage.openCreateNewDropDownList();
        createDropDownListPage.createNewDropDownList(listData);
        assertEquals(createDropDownListPage.saveCurrentDocAndReturnId(), "Ошибка", "Произошло сохранение с повторяющимся ключом!");
    }

    @Story("Редактирования выпадающего списка")
    @Test(description = "редактирования выпадающего списка")
    public void checkEditDropDownElement() {
        DropDownListData listData = new DropDownElementsListObject().getListDataObject();
        DropDownListData listDataOneElement = new DropDownElementsListObject().getListDataObjectWithOneElement();


        DropDownListPage dropDownListPage = new MainPage().openDropDownList();
        CreateDropDownListPage createDropDownListPage = dropDownListPage.openCreateNewDropDownList();
        createDropDownListPage.createNewDropDownList(listData);
        String idList = createDropDownListPage.saveCurrentDocAndReturnId();
        createDropDownListPage.goToMainPage();

        dropDownListPage = new MainPage().openDropDownList();
        createDropDownListPage = dropDownListPage.searchAndOpenDocument(idList);
        createDropDownListPage.createNewDropDownList(listDataOneElement);
        createDropDownListPage.saveCurrentDocAndReturnId();
        createDropDownListPage.goToMainPage();

        dropDownListPage = new MainPage().openDropDownList();
        createDropDownListPage = dropDownListPage.searchAndOpenDocument(idList);

        /***** Проверка элементов после редактирование выпадающего списка *****/
        IntStream.range(0, listData.getElementListData().size()).forEach(i -> listDataOneElement.getElementListData().add(listData.getElementListData().get(i)));

        createDropDownListPage.checkDropDownList(listDataOneElement, idList);
    }
}

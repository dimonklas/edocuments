package tests;

import io.qameta.allure.Story;
import org.testng.annotations.Test;
import pages.DocumentTypesListPage;
import pages.DropDownListPage;
import pages.MainPage;

public class TestDelete extends BaseTest {

    @Story("Удаление всех типов документов через Rest-Assured")
    @Test(description = "Удаление всех документов")
    public void deleteAllDocTypes() {
        DocumentTypesListPage listPage = new MainPage().openReportTypesListPage();
        listPage.deleteAllDocumentThroughRest();
    }

    @Story("Удаление всех типов документов через Rest-Assured")
    @Test(description = "Удаление всех документов")
    public void deleteAllDropDownDocs() {
        DropDownListPage listPage = new MainPage().openDropDownList();
        listPage.deleteAllDocumentThroughRest();
    }

}

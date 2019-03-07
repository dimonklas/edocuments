package tests;

import io.qameta.allure.Story;
import org.aeonbits.owner.ConfigFactory;
import org.testng.annotations.Test;
import pages.DocumentTypesListPage;
import pages.DropDownListPage;
import pages.MainPage;
import utils.IConfigurationVariables;

import static org.testng.Assert.assertFalse;

public class TestDelete extends BaseTest {

    private final IConfigurationVariables CV = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());

    @Story("Удаление всех типов документов через Rest-Assured")
    @Test(description = "Удаление всех документов")
    public void deleteAllDocTypes() {
        DocumentTypesListPage listPage = new MainPage().openReportTypesListPage();
        listPage.deleteAllDocumentThroughRest(CV.docName());

        MainPage mainPage = listPage.goToMainPage();
        listPage = mainPage.openReportTypesListPage();
        assertFalse(listPage.searchDocument(CV.docName()), "записи не удалились");

    }

    @Story("Удаление всех типов документов через Rest-Assured")
    @Test(description = "Удаление всех документов")
    public void deleteAllDropDownDocs() {
        DropDownListPage listPage = new MainPage().openDropDownList();
        listPage.deleteAllDocumentThroughRest(CV.docName());

        MainPage mainPage = listPage.goToMainPage();
        listPage = mainPage.openDropDownList();
        assertFalse(listPage.searchDocument(CV.docName()), "записи не удалились");
    }

}

package pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.By;
import utils.IConfigurationVariables;
import utils.WorkingWithBrowserTabs;


import java.util.HashSet;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static io.restassured.specification.ProxySpecification.host;
import static org.testng.Assert.assertTrue;
import static utils.Cookie.csrfToken;
import static utils.Cookie.phpsessid;

@Getter
@Log4j
public class DropDownListPage implements WorkingWithBrowserTabs {

    private final IConfigurationVariables CV = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());

    private SelenideElement searchField = $(By.id("search"));
    private SelenideElement createButton = $(By.id("btn_create"));
    private SelenideElement viewDropDownList = $(By.id("btn_view"));
    private SelenideElement deleteButton = $(By.id("btn_delete"));
    private SelenideElement acceptAlertButton = $(By.id("btn_yes"));


    @Step("Перейти на создание нового Выпадающего списка")
    public CreateDropDownListPage openCreateNewDropDownList() {
        createButton.shouldBe(Condition.visible).click();
        closeBrowserTab("Выпадающие списки");
        return new CreateDropDownListPage();
    }

    @Step("Проверка поиска по значению \"{value}\"")
    public CreateDropDownListPage searchAndOpenDocument(String value) {
        refresh();
        searchField.shouldBe(visible).sendKeys(value);
        $(By.xpath("//tbody//tr")).click();
        viewDropDownList.click();
        closeBrowserTab("Выпадающие списки");
        return new CreateDropDownListPage();
    }

    @Step("Удаление всех документов по значению \"{valueForDelete}\"")
    public void deleteAllDocument(String valueForDelete) {
        searchField.shouldBe(visible).setValue(valueForDelete);
        int count =  $$(By.xpath("//tbody//tr[@role='row']/td[contains(.,'" + valueForDelete + "')]")).size();
        for (int i = 0; i < count; i++) {
            $(By.xpath("//tbody//tr[@role='row']/td[contains(.,'" + valueForDelete + "')]")).click();
            deleteButton.shouldBe(visible).click();
            acceptAlertButton.shouldBe(visible).click();
            sleep(1000);
        }
        assertTrue($(By.xpath("//*[@id='lists_table']//td[text()='Записи отсутствуют.']")).exists());
    }

    @Step("Удаление всех документов через REST-assured")
    public void deleteAllDocumentThroughRest() {
        searchField.shouldBe(visible).clear();
        searchField.shouldBe(visible).sendKeys(CV.docName());

        ElementsCollection elementsId = $$x("//*[@id='lists_table']//tbody//tr/td[1]");
        HashSet<String> allIds = new HashSet<>();

        for (SelenideElement anElementsId : elementsId) {
            allIds.add(anElementsId.getText());
        }

        for (String pair : allIds) {
            Response response = given()
//                    .proxy(host("proxy.pbank.com.ua").withPort(8080))
                    .relaxedHTTPSValidation()
                    .log().all()
                    .cookie("PHPSESSID", phpsessid)
                    .cookie("CSRF-TOKEN", csrfToken)
                    .header("X-CSRF-Token",csrfToken)
                    .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                    .formParam("object","drop_down_list")
                    .formParam("list_id",pair)
                    .formParam("action","delete")
                    .post("http://buhonline.test.it.loc/admin/ajax.php");

            response.then().log().all();
        }
    }
}

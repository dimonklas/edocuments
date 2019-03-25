package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.log4j.Log4j;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.By;
import org.testng.Assert;
import utils.IConfigurationVariables;
import utils.WorkingWithBrowserTabs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static utils.Cookie.csrfToken;
import static utils.Cookie.phpSessId;
import static utils.WorkingWithBrowserTabs.closeBrowserTab;

@Log4j
public class DocumentTypesListPage {

    private final IConfigurationVariables CV = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());

    private SelenideElement pageTittle = $(By.xpath("//div[@id='page_title']"));
    private SelenideElement searchField = $(By.id("search"));
    private SelenideElement createButton = $(By.id("btn_create"));
    private SelenideElement deleteButton = $(By.id("btn_delete"));
    private SelenideElement copyButton = $(By.id("btn_copy"));
    private SelenideElement viewDocButton = $(By.id("btn_view"));
    private SelenideElement openDocButton = $(By.id("btn_open"));
    private SelenideElement closeDocButton = $(By.id("btn_close"));
    private SelenideElement acceptAlertButton = $(By.id("btn_yes"));
    private SelenideElement dissmisAlertButton = $(By.id("btn_no"));
    private SelenideElement linkOnHeader = $(By.id("section_title"));


    @Step("Проверка страницы \"Типы документов\"")
    public boolean checkDocumentTypesListPage() {
        String pageText = pageTittle.innerText();
        return pageText.equals("Типы документов");
    }

    @Step("Переход на главную страницу")
    public MainPage goToMainPage() {
        linkOnHeader.shouldBe(visible).click();
        return new MainPage();
    }

    @Step("Проверка сортировки по \"{sort}\"")
    public void checkSortingAsc(String sort) {

        String attrClass = $(By.xpath("//th[contains(.,'" + sort + "')]")).getAttribute("class");
        if (!attrClass.equals("sorting_asc")) $(By.xpath("//th[contains(.,'" + sort + "')]")).click();
        ElementsCollection allSortRows = $$(By.xpath("//tbody//tr[@role='row']/td[@class='sorting_1']"));

        LinkedList<Integer> dataIntegers = new LinkedList<>();
        LinkedList<String> dataStrings = new LinkedList<>();

        for (SelenideElement element : allSortRows) {
            if (sort.equals("ID")) dataIntegers.add(Integer.valueOf(element.getText()));
            else dataStrings.add(element.getText().toLowerCase());
        }

        LinkedList<Integer> dataIntegersAfterSort = new LinkedList<>();
        LinkedList<String> dataStringsAfterSort = new LinkedList<>();

        for (SelenideElement element : allSortRows) {
            if (sort.equals("ID")) dataIntegersAfterSort.add(Integer.valueOf(element.getText()));
            else dataStringsAfterSort.add(element.getText().toLowerCase());
        }

        Collections.sort(dataIntegers);
        Collections.sort(dataStrings);

        if (dataIntegers.size() > dataStrings.size())
            assertEquals(dataIntegers, dataIntegersAfterSort, "Сортировка чисел неправильная");
        else assertEquals(dataStrings, dataStringsAfterSort, "Сортировка строк неправильная");

    }

    @Step("Проверка сортировки по \"{sort}\"")
    public void checkSortingDesc(String sort) {

        String attrClass = $(By.xpath("//th[contains(.,'" + sort + "')]")).getAttribute("class");
        if (!attrClass.equals("sorting_desc")) {
            $(By.xpath("//th[contains(.,'" + sort + "')]")).click();
            $(By.xpath("//th[contains(.,'" + sort + "')]")).click();
        }
        ElementsCollection allSortRows = $$(By.xpath("//tbody//tr[@role='row']/td[@class='sorting_1']"));

        LinkedList<Integer> dataIntegers = new LinkedList<>();
        LinkedList<String> dataStrings = new LinkedList<>();

        for (SelenideElement element : allSortRows) {
            if (sort.equals("ID")) dataIntegers.add(Integer.valueOf(element.getText()));
            else dataStrings.add(element.getText().toLowerCase());
        }

        LinkedList<Integer> dataIntegersAfterSort = new LinkedList<>();
        LinkedList<String> dataStringsAfterSort = new LinkedList<>();

        for (SelenideElement element : allSortRows) {
            if (sort.equals("ID")) dataIntegersAfterSort.add(Integer.valueOf(element.getText()));
            else dataStringsAfterSort.add(element.getText().toLowerCase());
        }

        dataIntegers.sort(Collections.reverseOrder());
        dataStrings.sort(Collections.reverseOrder());

        if (dataIntegers.size() > dataStrings.size())
            assertEquals(dataIntegers, dataIntegersAfterSort, "Сортировка чисел неправильная");
        else assertEquals(dataStrings, dataStringsAfterSort, "Сортировка строк неправильная");

    }

    @Step("Проверка поиска по значению \"{value}\"")
    public boolean searchDocument(String value) {
        refresh();
        searchField.shouldBe(visible).clear();
        searchField.shouldBe(visible).sendKeys(value);
        StringBuilder stringBuilder = new StringBuilder();

        if (!$(By.xpath("//*[@id='types_table']//td[text()='Записи отсутствуют.']")).exists()) {
            for (int i = 1; i < 6; i++) {
                stringBuilder.append($(By.xpath("(//tbody//tr/td)[" + i + "]")).getText());
            }
            String result = stringBuilder.toString();
            log.info(result);
            return true;
        } else {
            log.info("Записи отсутствуют");
            return false;
        }
    }

    @Step("Проверка поиска по значению \"{value}\"")
    public CreateDocumentTypePage searchAndOpenDocument(String value) {
        refresh();
        searchField.shouldBe(visible).sendKeys(value);
        $(By.xpath("//tbody//tr/td")).click();
        viewDocButton.click();
        closeBrowserTab("Типы документов");
        return new CreateDocumentTypePage();
    }

    @Step("Удаление документа по значению \"{valueForDelete}\"")
    public void deleteDocument(String valueForDelete) {
        $(By.xpath("//tbody//tr[@role='row']/td[text()='" + valueForDelete + "']")).click();
        deleteButton.shouldBe(visible).click();
        acceptAlertButton.shouldBe(visible).click();
        sleep(1000);
    }

    @Step("Удаление всех документов по значению \"{valueForDelete}\"")
    public void deleteAllDocument(String valueForDelete) {
        searchField.shouldBe(visible).setValue(valueForDelete);
        int count = $$(By.xpath("//tbody//tr[@role='row']/td[contains(.,'" + valueForDelete + "')]")).size();
        for (int i = 0; i < count; i++) {
            $(By.xpath("//tbody//tr[@role='row']/td[contains(.,'" + valueForDelete + "')]")).click();
            deleteButton.shouldBe(visible).click();
            acceptAlertButton.shouldBe(visible).click();
            sleep(1000);
        }
        assertTrue($(By.xpath("//*[@id='types_table']//td[text()='Записи отсутствуют.']")).exists());
    }

    @Step("Переход на страницу создания документа")
    public CreateDocumentTypePage goToCreateNewDocumentPage() {
        createButton.shouldBe(visible).click();
        closeBrowserTab("Типы документов");
        return new CreateDocumentTypePage();
    }

    @Step("Создание копии документа со значением {valueForCopy}")
    public CreateDocumentTypePage selectAndCopyDocType(String valueForCopy) {
        $(By.xpath("//tbody//tr[@role='row']/td[text()='" + valueForCopy + "']")).click();
        copyButton.click();
        closeBrowserTab("Типы документов");
        return new CreateDocumentTypePage();
    }

    @Step("Проверка двух документов после копирования")
    public void checkTwoRows() {
        ArrayList<String> arrayList = new ArrayList<>();

        for (int i = 1; i < 3; i++) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int j = 2; j < 6; j++) {
                stringBuilder.append($(By.xpath("(//tbody//tr[" + i + "]/td)[" + j + "]")).getText());
            }
            arrayList.add(stringBuilder.toString());
        }
        Assert.assertEquals(arrayList.get(0), arrayList.get(1), "Разные документы");
    }

    @Step("Открытие документа")
    public CreateDocumentTypePage openDocument(String value) {
        $(By.xpath("//tr/td[contains(., '" + value + "')]")).shouldBe(visible).click();
        viewDocButton.click();
        closeBrowserTab("Типы документов");
        return new CreateDocumentTypePage();
    }

    @Step("Открытие документа через сраницу \"Список докуменов\"")
    public boolean openDocument() {
        SelenideElement documentInList = $x("(//table[@id='types_table']//tbody//tr)[1]");
        String elementClass = documentInList.attr("class");
        if (!elementClass.contains("selected")) $x("(//table[@id='types_table']//tbody//tr)[1]/td").click();
        elementClass = documentInList.attr("class");
        if (elementClass.equals("closed odd selected")) openDocButton.shouldBe(visible).click();
        sleep(1000);

        return closeDocButton.is(visible) && documentInList.attr("class").equals("odd selected");
    }

    @Step("Закрытие документа через сраницу \"Список докуменов\"")
    public boolean closeDocument() {
        SelenideElement documentInList = $x("(//table[@id='types_table']//tbody//tr)[1]");
        String elementClass = documentInList.attr("class");
        if (!elementClass.contains("selected")) $x("(//table[@id='types_table']//tbody//tr)[1]/td").click();
        elementClass = documentInList.attr("class");
        if (elementClass.equals("odd selected")) closeDocButton.shouldBe(visible).click();
        sleep(1000);

        return openDocButton.is(visible) && documentInList.attr("class").equals("odd selected closed");
    }

    @Step("Удаление типов документов через рест")
    public void deleteAllDocumentThroughRest(String value) {
        searchField.shouldBe(visible).clear();
        searchField.shouldBe(visible).sendKeys(value);

        ElementsCollection elementsId = $$x("//*[@id='types_table']//tbody//tr/td[1]");
        HashSet<String> allIds = new HashSet<>();

        if (!elementsId.get(0).getText().equals("Записи отсутствуют.")) {
            for (SelenideElement anElementsId : elementsId) {
                allIds.add(anElementsId.getText());
            }

            for (String pair : allIds) {
                Response response = given()
//                .proxy(host("proxy.pbank.com.ua").withPort(8080))
                        .relaxedHTTPSValidation()
//                        .log().all()
                        .cookie("PHPSESSID", phpSessId)
                        .cookie("CSRF-TOKEN", csrfToken)
                        .header("X-CSRF-Token", csrfToken)
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        .formParam("object", "type")
                        .formParam("type_id", pair)
                        .formParam("action", "delete")
                        .post("http://buhonline.test.it.loc/admin/ajax.php");

                assertTrue(response.getBody().prettyPrint().contains("result\":\"success\""), "Операция по удалению документа неуспешна");
            }
        }
    }
}
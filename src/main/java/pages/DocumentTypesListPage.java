package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import org.testng.Assert;
import utils.WorkingWithBrowserTabs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

@Log4j
public class DocumentTypesListPage implements WorkingWithBrowserTabs {
    private SelenideElement pageTittle = $(By.xpath("//div[@id='page_title']"));
    private SelenideElement searchField = $(By.id("search"));
    private SelenideElement createButton = $(By.id("btn_create"));
    private SelenideElement deleteButton = $(By.id("btn_delete"));
    private SelenideElement copyButton = $(By.id("btn_copy"));
    private SelenideElement viewDocButton = $(By.id("btn_view"));
    private SelenideElement acceptAlertButton = $(By.id("btn_yes"));
    private SelenideElement dissmisAlertButton = $(By.id("btn_no"));


    @Step("Проверка страницы \"Типы документов\"")
    public boolean checkDocumentTypesListPage() {
        String pageText = pageTittle.innerText();
        return pageText.equals("Типы документов");
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
            Assert.assertTrue(dataIntegers.equals(dataIntegersAfterSort), "Сортировка чисел неправильная");
        else Assert.assertTrue(dataStrings.equals(dataStringsAfterSort), "Сортировка строк неправильная");

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

        Collections.sort(dataIntegers, Collections.reverseOrder());
        Collections.sort(dataStrings, Collections.reverseOrder());

        if (dataIntegers.size() > dataStrings.size())
            Assert.assertTrue(dataIntegers.equals(dataIntegersAfterSort), "Сортировка чисел неправильная");
        else Assert.assertTrue(dataStrings.equals(dataStringsAfterSort), "Сортировка строк неправильная");

    }

    @Step("Проверка поиска по значению \"{value}\"")
    public void search(String value) {
        refresh();
        searchField.shouldBe(visible).sendKeys(value);
        StringBuilder stringBuilder = new StringBuilder();

        if (!$(By.xpath("//*[@id='types_table']//td[text()='Записи отсутствуют.']")).exists()) {
            for (int i = 1; i < 6; i++) {
                stringBuilder.append($(By.xpath("(//tbody//tr/td)[" + i + "]")).getText());
            }
            String result = stringBuilder.toString();
            log.info(result);
            Assert.assertTrue(result.contains(value), "Записи отсутствуют");
        } else {
            log.info("Записи отсутствуют");
        }
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
        int count =  $$(By.xpath("//tbody//tr[@role='row']/td[contains(.,'" + valueForDelete + "')]")).size();
        for (int i = 0; i < count; i++) {
            $(By.xpath("//tbody//tr[@role='row']/td[contains(.,'" + valueForDelete + "')]")).click();
            deleteButton.shouldBe(visible).click();
            acceptAlertButton.shouldBe(visible).click();
            sleep(1000);
        }
    }

    @Step("Переход на страницу создания документа")
    public OpenCreateDocument goToCreateNewDocumentPage() {
        createButton.shouldBe(visible).click();
        closeBrowserTab("Типы документов");
        return new OpenCreateDocument();
    }

    @Step("Создание копии документа со значением {valueForCopy}")
    public OpenCreateDocument clickToCopyButton(String valueForCopy) {
        $(By.xpath("//tbody//tr[@role='row']/td[text()='" + valueForCopy + "']")).click();
        copyButton.click();
        closeBrowserTab("Типы документов");
        return new OpenCreateDocument();
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
        log.info(arrayList.get(0));
        log.info(arrayList.get(1));
        Assert.assertEquals(arrayList.get(0), arrayList.get(1), "Разные документы");
    }

    @Step("Открытие документа")
    public OpenCreateDocument openDocument(String value){
        $(By.xpath("//tr/td[contains(., '" + value + "')]")).shouldBe(visible).click();
        viewDocButton.click();
        closeBrowserTab("Типы документов");
        return new OpenCreateDocument();
    }
}
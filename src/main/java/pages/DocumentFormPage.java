package pages;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.Collections;

import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertEquals;

@Getter
@Log4j
public class DocumentFormPage {
    private SelenideElement createButton = $(By.id("btn_create"));
    private SelenideElement viewButton = $(By.id("btn_view"));
    private SelenideElement deleteButton = $(By.id("btn_delete"));


    @Step("Проверка сортировки формы документов по возрастанию столбца \"{value}\"")
    public void checkSortAsc(String value) {
        SelenideElement sortCodeFormButton = $x("//div[@class='dataTables_scrollHead']//th[contains(.,'" + value + "')]");
        ArrayList<String> values = new ArrayList<>();
        ArrayList<String> valuesAfterSort = new ArrayList<>();

        if (!sortCodeFormButton.attr("class").equals("sorting_asc")) {
            sortCodeFormButton.click();
        }

        if (!sortCodeFormButton.attr("class").equals("sorting_asc")) {
            sortCodeFormButton.click();
        }

        ElementsCollection valuesFromPage = $$(By.xpath("//table[@id='forms_table']//tbody//td[@class='sorting_1']"));

        for (int i = 1; i <= valuesFromPage.size(); i++) {
            values.add($x("(//table[@id='forms_table']//tbody//td[@class='sorting_1'])[" + i + "]").getText().toLowerCase());
        }
        Collections.sort(values);


        for (int i = 1; i <= valuesFromPage.size(); i++) {
            valuesAfterSort.add($x("(//table[@id='forms_table']//tbody//td[@class='sorting_1'])[" + i + "]").getText().toLowerCase());
        }

        assertEquals(values, valuesAfterSort, "Сортировка не правильная");
    }

    @Step("Проверка сортировки формы документов по убыванию столбца \"{value}\"")
    public void checkSortDesc(String value) {
        SelenideElement sortCodeFormButton = $x("//div[@class='dataTables_scrollHead']//th[contains(.,'" + value + "')]");
        ArrayList<String> values = new ArrayList<>();
        ArrayList<String> valuesAfterSort = new ArrayList<>();

        if (!sortCodeFormButton.attr("class").equals("sorting_desc")) {
            sortCodeFormButton.click();
        }

        if (!sortCodeFormButton.attr("class").equals("sorting_desc")) {
            sortCodeFormButton.click();
        }

        ElementsCollection valuesFromPage = $$(By.xpath("//table[@id='forms_table']//tbody//td[@class='sorting_1']"));

        for (int i = 1; i <= valuesFromPage.size(); i++) {
            values.add($x("(//table[@id='forms_table']//tbody//td[@class='sorting_1'])[" + i + "]").getText().toLowerCase());
        }
        values.sort(Collections.reverseOrder());

        for (int i = 1; i <= valuesFromPage.size(); i++) {
            valuesAfterSort.add($x("(//table[@id='forms_table']//tbody//td[@class='sorting_1'])[" + i + "]").getText().toLowerCase());
        }

        assertEquals(values, valuesAfterSort, "Сортировка не правильная");
    }

}

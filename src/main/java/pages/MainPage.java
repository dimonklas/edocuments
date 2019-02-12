package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import utils.WorkingWithBrowserTabs;

import static com.codeborne.selenide.Selenide.$;

public class MainPage implements WorkingWithBrowserTabs {
    private SelenideElement switchToReportTypeList = $(By.xpath("//h5[normalize-space()='Типы отчетных документов']/..//a[contains(.,'список')]"));
    private SelenideElement createNewType = $(By.xpath("//h5[normalize-space()='Типы отчетных документов']/..//a[contains(.,'тип')]"));

    @Step("Перейти на страницу со списком отчетных докуметов")
    public DocumentTypesListPage openReportTypesListPage() {
        switchToReportTypeList.click();
        closeBrowserTab("Конструктор отчетов");
        return new DocumentTypesListPage();
    }

    @Step("Перейти на страницу со 'Создать новый тип'")
    public OpenCreateDocument openCreateNewTypePage() {
        createNewType.click();
        closeBrowserTab("Конструктор отчетов");
        return new OpenCreateDocument();
    }
}
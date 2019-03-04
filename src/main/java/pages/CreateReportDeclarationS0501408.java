package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.By;
import pages.document.s0501408.DeclarationDataS0501408;
import utils.IConfigurationVariables;

import java.io.File;
import java.util.HashMap;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static utils.SupportActions.clearField;

@Log4j
@Getter
public class CreateReportDeclarationS0501408 {

    private final IConfigurationVariables CV = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());

    private SelenideElement uploadFromFileButton = $(By.xpath("//input[@value='Завантажити з файлу']"));
    private SelenideElement editButton = $(By.xpath("//input[@value='Редагувати']"));
    private SelenideElement subscribeAndSendButton = $(By.xpath("//input[@value='Підписати і надіслати']"));
    private SelenideElement copyReportButton = $(By.xpath("//input[@value='Копіювати звіт']"));
    private SelenideElement save = $(By.xpath("//input[@value='Зберегти']"));
    private SelenideElement cancel = $(By.xpath("//input[@value='Скасувати']"));

    /***** Респондент *****/
    private SelenideElement comEDRPOU = $(By.name("FIRM_EDRPOU"));
    private SelenideElement comName = $(By.name("FIRM_NAME"));
    private SelenideElement locationAddress = $(By.name("FIRM_ADR"));
    private SelenideElement locationAddressFact = $(By.name("FIRM_ADR_FIZ"));

    /**** Экспорт товаров *****/
    private SelenideElement countryNameExport = $(By.name("T1RXXXXG2S__row1"));
    private SelenideElement countryCodeExport = $(By.name("T1RXXXXG3S__row1"));
    private SelenideElement productNameExport = $(By.name("T1RXXXXG7S__row1"));
    private SelenideElement productCodeExport = $(By.name("T1RXXXXG6S__row1"));
    private SelenideElement currencyNameExport = $(By.name("T1RXXXXG4S__row1"));
    private SelenideElement currencyCodeExport = $(By.name("T1RXXXXG5S__row1"));
    private SelenideElement countProductsExport = $(By.name("T1RXXXXG1__row1"));
    private SelenideElement productPriceExport = $(By.name("T1RXXXXG8__row1"));

    /**** Импорт товаров товаров *****/
    private SelenideElement countryNameImport = $(By.name("T2RXXXXG2S__row1"));
    private SelenideElement countryCodeImport = $(By.name("T2RXXXXG6S__row1"));
    private SelenideElement productNameImport = $(By.name("T2RXXXXG5S__row1"));
    private SelenideElement productCodeImport = $(By.name("T2RXXXXG4S__row1"));
    private SelenideElement currencyNameImport = $(By.name("T2RXXXXG3S__row1"));
    private SelenideElement currencyCodeImport = $(By.name("T2RXXXXG7S__row1"));
    private SelenideElement countProductsImport = $(By.name("T2RXXXXG1__row1"));
    private SelenideElement productPriceImport = $(By.name("T2RXXXXG8__row1"));

    /***** Персональные данные *****/
    private SelenideElement fioDir = $(By.name("VIK_RUK"));
    private SelenideElement fio = $(By.name("VIK"));
    private SelenideElement telNumber = $(By.name("VIK_TEL"));
    private SelenideElement fax = $(By.name("FIRM_FAXORG"));
    private SelenideElement email = $(By.name("VIK_EMAIL"));

    /***** Подписание отчета, загрузка ключа *****/

    private SelenideElement saveReportButton = $(By.xpath("(//*[@class='btn_save p24_btn_green'])[1]"));
    private SelenideElement cancelReportButton = $(By.xpath("(//*[@class='btn_cancel p24_btn_gray'])[1]"));
    private SelenideElement shadowWindow = $(By.className("x-css-shadow"));
    private SelenideElement confirmSaveReport = $(By.xpath("//span[@class='x-btn-inner' and text()='Так']/../../.."));
    private SelenideElement cancelSaveReport = $(By.xpath("//span[@class='x-btn-inner' and text()='Ні']/../../.."));
    private SelenideElement showErrorsReport = $(By.xpath("//span[@class='x-btn-inner' and text()='Показати']/../../.."));

    private SelenideElement iframeSendReport = $(By.xpath("//div[@id='sign_container']/iframe"));
    private SelenideElement iframeDecryptionReceipt = $(By.xpath("//div[@id='reportAnswerWin-body']/iframe"));
    private SelenideElement pbKeysSelect = $(By.xpath("//span[text()='Ключі ПриватБанку']"));
    private SelenideElement keyPathInput = $(By.xpath("(//body[@id='sign']//input[@type='text'])[1]"));
    private SelenideElement passwordKeyInput = $(By.xpath("(//body[@id='sign']//div[@data-type='jks1']//input[@type='password'])[1]"));
    private SelenideElement nextButton = $(By.xpath("//div[@class='btn btn-green']"));
    private SelenideElement signAndSendButton = $(By.id("sign_and_send"));
    private SelenideElement roleSelect = $(By.xpath("//table[@class='keys-table']//select"));
    private SelenideElement keysSave = $(By.id("keys-save"));
    private SelenideElement keysCancel = $(By.id("keys-cancel"));
    private SelenideElement reportStage = $(By.id("report_stage"));

    private SelenideElement decrtyptReceiptButton = $(By.xpath("//*[@class='btn_decrypt p24_btn_green']"));

    public HashMap<SelenideElement, String> declarationS0501408Fields = new HashMap<>();

    public CreateReportDeclarationS0501408() {
        /***** Респондент *****/
        declarationS0501408Fields.put(comEDRPOU, "код ЄДРПОУ");
        declarationS0501408Fields.put(comName, "Найменування");
        declarationS0501408Fields.put(locationAddress, "юридична адреса");
        declarationS0501408Fields.put(locationAddressFact, "фактична адреса");

        /**** Экспорт товаров *****/
        declarationS0501408Fields.put(countryNameExport, "Назва країни експорт");
        declarationS0501408Fields.put(countryCodeExport, "Код країни експорт");
        declarationS0501408Fields.put(productNameExport, "Найменування товару експорт");
        declarationS0501408Fields.put(productCodeExport, "Код товару експорт");
        declarationS0501408Fields.put(currencyNameExport, "Назва валюти експорт");
        declarationS0501408Fields.put(currencyCodeExport, "Код валюти експорт");
        declarationS0501408Fields.put(countProductsExport, "Кількість експортваних товарів експорт");
        declarationS0501408Fields.put(productPriceExport, "Вартість експортованих товарів експорт");

        /**** Импорт товаров товаров *****/
        declarationS0501408Fields.put(countryNameImport, "Назва країни імпорт");
        declarationS0501408Fields.put(countryCodeImport, "Код країни імпорт");
        declarationS0501408Fields.put(productNameImport, "Найменування товару імпорт");
        declarationS0501408Fields.put(productCodeImport, "Код товару імпорт");
        declarationS0501408Fields.put(currencyNameImport, "Назва валюти імпорт");
        declarationS0501408Fields.put(currencyCodeImport, "Код валюти імпорт");
        declarationS0501408Fields.put(countProductsImport, "Кількість імпортованих товарів");
        declarationS0501408Fields.put(productPriceImport, "Вартість імпортованих товарів");

        /***** Персональные данные *****/
        declarationS0501408Fields.put(fioDir, "ФІО дир");
        declarationS0501408Fields.put(fio, "ФІО бух");
        declarationS0501408Fields.put(telNumber, "телефон");
        declarationS0501408Fields.put(fax, "факс");
        declarationS0501408Fields.put(email, "електронна пошта");
    }

    @Step("Открыть редактирование документа")
    public void clickEditButton() {
        editButton.shouldBe(visible).click();
    }


    /*****---------------------------------------------------------------------------------------*****/
    @Step("Сохранение текущего отчета")
    public void saveReport() {
        switchTo().defaultContent();
        saveReportButton.click();
        sleep(1000);
        if (shadowWindow.exists()) confirmSaveReport.shouldBe(visible).click();
    }

    @Step("Подписать и отправить отчет")
    public void subscribeAndSendReport() {
        subscribeAndSendButton.shouldBe(visible).click();
        sleep(2000);
        switchTo().frame(iframeSendReport.shouldBe(visible));
        if (pbKeysSelect.is(visible)) {
            sleep(2000);
            pbKeysSelect.shouldBe(visible).click();
            keyPathInput.shouldBe(visible).sendKeys(new File("src/main/resources/supportFiles/" + CV.pbKey() + "").getAbsolutePath());
            nextButton.shouldBe(visible).click();
            passwordKeyInput.shouldBe(visible).sendKeys(CV.pbKeyPassword());
            signAndSendButton.shouldBe(visible).click();
            roleSelect.shouldBe(visible).selectOption("Директор");
            keysSave.shouldBe(visible).click();
            switchTo().defaultContent();
            log.info("Отправляем отчет");
        } else {
            passwordKeyInput.shouldBe(visible).sendKeys(CV.pbKeyPassword());
            signAndSendButton.shouldBe(visible).click();
            switchTo().defaultContent();
            log.info("Отправляем отчет");
        }
        sleep(5000);
    }

    @Step("Ожидание изменения статуса")
    public String waitReportStatusChange() {
        int countWait = 0;

        while (!reportStage.getText().equals("Не прийнятий") && countWait < 300) {
            sleep(1000);
            refresh();
            countWait++;
            if (reportStage.getText().equals("Не розшифрований")) decryptionReceipt();
        }

        return reportStage.getText();
    }

    @Step("Расшифровка квитанции")
    public void decryptionReceipt() {
        decrtyptReceiptButton.shouldBe(visible).click();
        $(By.xpath("(//input[@title='На печать'])[2]")).shouldBe(visible).click();
        $(By.xpath("//*[@class='btn btn-decrypt-receipts']")).shouldBe(visible).click();
        switchTo().frame(iframeDecryptionReceipt);
        $(By.xpath("//div[text()='" + CV.pbKey() + ":']/following-sibling::div/input")).shouldBe(visible).sendKeys(CV.pbKeyPassword());
        $(By.id("btn_decrypt")).shouldBe(visible).click();
        sleep(5000);
        $(By.xpath("(//div[@data-type='content' and contains( . , 'Код форми документу')])[1]")).exists();
        $(By.xpath("(//div[@data-type='content' and contains( . , 'Платник податків:')])[1]")).exists();
        $(By.xpath("(//div[@data-type='content' and contains( . , 'Документ доставлено до:')])[1]")).exists();
    }

    @Step("Копирование отчета")
    public void copyReport() {
        String originalId = ($(By.xpath("//*[@id='report_stage']/following-sibling::span")).getText()).replaceAll("\\D+", "");
        copyReportButton.shouldBe(visible).click();
        $(By.xpath("//span[text()='Створити']/..")).shouldBe(visible).click();
        sleep(1000);
        String copyId = $(By.xpath("//*[@id='report_stage']/following-sibling::span")).getText().replaceAll("\\D+", "");
        assertNotEquals(originalId, copyId);
    }

    @Step("Ввести в поле значение \"{value}\"")
    public void setValueToField(SelenideElement element, String value) {
        clearField(element);
        element.shouldBe(visible).setValue(value);
        element.pressEnter();
    }

    /***** Заполнений формы *****/
    @Step("заполненяем в \"Респонтент\' поле {name}")
    public void setValueToHeader(SelenideElement element, String name, DeclarationDataS0501408 data) {
        switch (name) {
            case "код ЄДРПОУ":
                setValueToField(element, data.getComEDRPOU());
                break;
            case "Найменування":
                setValueToField(element, data.getComName());
                break;
            case "юридична адреса":
                setValueToField(element, data.getLocationAddress());
                break;
            case "фактична адреса":
                setValueToField(element, data.getLocationAddressFact());
                break;
        }
    }

    @Step("заполненяев в \"Експорт товарів\' поле {name}")
    public void setValueToExportProducts(SelenideElement element, String name, DeclarationDataS0501408 data) {
        switch (name) {
            case "Назва країни експорт":
                setValueToField(element, data.getCountryNameExport());
                break;
            case "Найменування товару експорт":
                setValueToField(element, data.getProductNameExport());
                break;
            case "Код товару експорт":
                setValueToField(element, data.getProductCodeExport());
                break;
            case "Код країни експорт":
                setValueToField(element, data.getCountryCodeExport());
                break;
            case "Назва валюти експорт":
                setValueToField(element, data.getCurrencyNameExport());
                break;
            case "Код валюти експорт":
                setValueToField(element, data.getCurrencyCodeExport());
                break;
            case "Кількість експортваних товарів експорт":
                setValueToField(element, data.getCountProductsExport());
                break;
            case "Вартість експортованих товарів експорт":
                setValueToField(element, data.getProductPriceExport());
                break;
        }
    }

    @Step("заполненяем в \"Імпорт товарів\' поле {name}")
    public void setValueToImportProducts(SelenideElement element, String name, DeclarationDataS0501408 data) {
        switch (name) {
            case "Назва країни імпорт":
                setValueToField(element, data.getCountryNameImport());
                break;
            case "Найменування товару імпорт":
                setValueToField(element, data.getProductNameImport());
                break;
            case "Код товару імпорт":
                setValueToField(element, data.getProductCodeImport());
                break;
            case "Код країни імпорт":
                setValueToField(element, data.getCountryCodeImport());
                break;
            case "Назва валюти імпорт":
                setValueToField(element, data.getCurrencyNameImport());
                break;
            case "Код валюти імпорт":
                setValueToField(element, data.getCurrencyCodeImport());
                break;
            case "Кількість імпортованих товарів":
                setValueToField(element, data.getCountProductsImport());
                break;
            case "Вартість імпортованих товарів":
                setValueToField(element, data.getProductPriceImport());
                break;
        }
    }

    @Step("заполняем в  \"футер\' поле {name}")
    public void setValueToFooter(SelenideElement element, String name, DeclarationDataS0501408 data) {
        switch (name) {
            case "ФІО дир":
                setValueToField(element, data.getFioDir());
                break;
            case "ФІО бух":
                setValueToField(element, data.getFio());
                break;
            case "телефон":
                setValueToField(element, data.getTelNumber());
                break;
            case "факс":
                setValueToField(element, data.getFax());
                break;
            case "електронна пошта":
                setValueToField(element, data.getEmail());
                break;
        }
    }


    /***** Проверка формы *****/
    @Step("проверяем в \"Респонтент\' поле {name}")
    public void checkValueToHeader(SelenideElement element, String name, DeclarationDataS0501408 data) {
        switch (name) {
            /***** Респондент *****/
            case "код ЄДРПОУ":
                assertEquals(element.getValue(), data.getComEDRPOU());
                break;
            case "Найменування":
                assertEquals(element.getValue(), data.getComName());
                break;
            case "юридична адреса":
                assertEquals(element.getValue(), data.getLocationAddress());
                break;
            case "фактична адреса":
                assertEquals(element.getValue(), data.getLocationAddressFact());
                break;
        }
    }

    @Step("проверяем в \"Експорт товарів\' поле {name}")
    public void checkValueToExportProducts(SelenideElement element, String name, DeclarationDataS0501408 data) {
        switch (name) {
            case "Назва країни експорт":
                assertEquals(element.getValue(), data.getCountryNameExport());
                break;
            case "Найменування товару експорт":
                assertEquals(element.getValue(), data.getProductNameExport());
                break;
            case "Код товару експорт":
                assertEquals(element.getValue(), data.getProductCodeExport());
                break;
            case "Код країни експорт":
                assertEquals(element.getValue(), data.getCountryCodeExport());
                break;
            case "Назва валюти експорт":
                assertEquals(element.getValue(), data.getCurrencyNameExport());
                break;
            case "Код валюти експорт":
                assertEquals(element.getValue(), data.getCurrencyCodeExport());
                break;
            case "Кількість експортваних товарів експорт":
                assertEquals(element.getValue(), data.getCountProductsExport());
                break;
            case "Вартість експортованих товарів експорт":
                assertEquals(element.getValue(), data.getProductPriceExport());
                break;
        }
    }

    @Step("проверяем в \"Експорт товарів\' поле {name}")
    public void checkValueToExportProducts(SelenideElement element, String name) {
        switch (name) {
            case "Кількість експортваних товарів експорт":
                assertEquals(element.getValue(), "0.000000");
                break;
            case "Вартість експортованих товарів експорт":
                assertEquals(element.getValue(), "0.000000");
                break;
        }
    }

    @Step("проверяем в \"Імпорт товарів\' поле {name}")
    public void checkValueToImportProducts(SelenideElement element, String name, DeclarationDataS0501408 data) {
        switch (name) {
            case "Назва країни імпорт":
                assertEquals(element.getValue(), data.getCountryNameImport());
                break;
            case "Найменування товару імпорт":
                assertEquals(element.getValue(), data.getProductNameImport());
                break;
            case "Код товару імпорт":
                assertEquals(element.getValue(), data.getProductCodeImport());
                break;
            case "Код країни імпорт":
                assertEquals(element.getValue(), data.getCountryCodeImport());
                break;
            case "Назва валюти імпорт":
                assertEquals(element.getValue(), data.getCurrencyNameImport());
                break;
            case "Код валюти імпорт":
                assertEquals(element.getValue(), data.getCurrencyCodeImport());
                break;
            case "Кількість експортваних товарів імпорт":
                assertEquals(element.getValue(), data.getCountProductsImport());
                break;
            case "Вартість експортованих товарів імпорт":
                assertEquals(element.getValue(), data.getProductPriceImport());
                break;
        }
    }

    @Step("проверяем в \"Імпорт товарів\' поле {name}")
    public void checkValueToImportProducts(SelenideElement element, String name) {
        switch (name) {
            case "Кількість експортваних товарів імпорт":
                assertEquals(element.getValue(), "0.000000");
                break;
            case "Вартість експортованих товарів імпорт":
                assertEquals(element.getValue(), "0.000000");
                break;
        }
    }

    @Step("проверяем в \"футер\' поле {name}")
    public void checkValueToFooter(SelenideElement element, String name, DeclarationDataS0501408 data) {
        switch (name) {
            case "ФІО дир":
                assertEquals(element.getValue(), data.getFioDir());
                break;
            case "ФІО бух":
                assertEquals(element.getValue(), data.getFio());
                break;
            case "телефон":
                assertEquals(element.getValue(), data.getTelNumber());
                break;
            case "факс":
                assertEquals(element.getValue(), data.getFax());
                break;
            case "електронна пошта":
                assertEquals(element.getValue(), data.getEmail());
                break;
        }
    }
}

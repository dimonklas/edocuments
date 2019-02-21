package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.By;
import pages.document.*;
import utils.IConfigurationVariables;

import java.io.File;
import java.util.HashMap;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.assertEquals;
import static utils.SupportActions.clearField;

@Log4j
@Getter
public class CreateReportDeclarationJ0301206 {

    IConfigurationVariables CV = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());

    private SelenideElement uploadFromFileButton = $(By.xpath("//input[@value='Завантажити з файлу']"));
    private SelenideElement editButton = $(By.xpath("//input[@value='Редагувати']"));
    private SelenideElement subscribeAndSendButton = $(By.xpath("//input[@value='Підписати і надіслати']"));
    private SelenideElement copyReportButton = $(By.xpath("//input[@value='Копіювати звіт']"));
    private SelenideElement save = $(By.xpath("//input[@value='Зберегти']"));
    private SelenideElement cancel = $(By.xpath("//input[@value='Скасувати']"));

    /***** Головна форма *****/
    /***** I Загальні відомості *****/
    private SelenideElement reportCheckBox = $(By.name("HZ"));
    private SelenideElement reportNewCheckBox = $(By.name("HZN"));
    private SelenideElement clarificationCheckBox = $(By.name("HZU"));

    private SelenideElement reportPeriodFirstQuarter = $(By.name("H1KV"));
    private SelenideElement reportPeriodHalfYear = $(By.name("HHY"));
    private SelenideElement reportPeriodThreeQuarter = $(By.name("H3KV"));
    private SelenideElement reportPeriodYear = $(By.name("HY"));

    private SelenideElement year = $(By.name("HZYP"));
    private SelenideElement reportPeriodFirstQuarterSpecified = $(By.name("H1KVP"));
    private SelenideElement reportPeriodHalfYearSpecified = $(By.name("HHYP"));
    private SelenideElement reportPeriodThreeQuarterSpecified = $(By.name("H3KVP"));
    private SelenideElement reportPeriodYearSpecified = $(By.name("HYP"));

    private SelenideElement sequenceNumber = $(By.name("HNY"));
    private SelenideElement comName = $(By.name("HNAME"));
    private SelenideElement innOrPassportSeria = $(By.name("HTIN"));
    private SelenideElement kved = $(By.name("HKVED"));
    private SelenideElement locationAddress = $(By.name("HLOC"));
    private SelenideElement zip = $(By.name("HZIP"));
    private SelenideElement cityCode = $(By.name("HINTURB"));
    private SelenideElement telNumber = $(By.name("HTEL"));
    private SelenideElement faxNumber = $(By.name("HFAX"));
    private SelenideElement email = $(By.name("HEMAIL"));
    private SelenideElement controlAuthorityDropdown = $(By.name("HSTI"));

    /***** II Розрахунок податкових зобов'язань збору за місця для паркування транспортних засобів *****/
    private SelenideElement sumName = $(By.name("T1RXXXXG2S__row1"));
    private SelenideElement square = $(By.name("T1RXXXXG3__row1"));
    private SelenideElement minSalary = $(By.name("T1RXXXXG4__row1"));
    private SelenideElement countDays = $(By.name("T1RXXXXG5__row1"));
    private SelenideElement percent = $(By.name("T1RXXXXG6__row1"));
    private SelenideElement taxSum = $(By.name("R02G7"));
    private SelenideElement taxSumSpecified = $(By.name("R04G7"));
    private SelenideElement specifiedSum = $(By.name("R05G7"));
    private SelenideElement fineSum = $(By.name("R08G7"));
    private SelenideElement penaltySum = $(By.name("R09G7"));
    private SelenideElement addText = $(By.name("T2RXXXXG2S__row1"));
    private SelenideElement addDeclaration = $(By.name("HJAR"));

    private SelenideElement dateDeclaration = $(By.name("HFILL"));
    private SelenideElement FIO = $(By.name("HBOS"));
    private SelenideElement inn = $(By.name("HKBOS"));
    private SelenideElement accountant = $(By.name("HBUH"));
    private SelenideElement accountantInn = $(By.name("HKBUH"));

    /***** Подписание отчета, загрузка ключа *****/

    private SelenideElement saveReportButton = $(By.xpath("(//*[@class='btn_save p24_btn_green'])[1]"));
    private SelenideElement cancelReportButton = $(By.xpath("(//*[@class='btn_cancel p24_btn_gray'])[1]"));
    private SelenideElement shadowWindow = $(By.className("x-css-shadow"));
    private SelenideElement confirmSaveReport = $(By.xpath("//span[@class='x-btn-inner' and text()='Так']/../../.."));
    private SelenideElement cancelSaveReport = $(By.xpath("//span[@class='x-btn-inner' and text()='Ні']/../../.."));
    private SelenideElement showErrorsReport = $(By.xpath("//span[@class='x-btn-inner' and text()='Показати']/../../.."));

    private SelenideElement iframeSendReport = $(By.xpath("//div[@id='sign_container']/iframe"));
    private SelenideElement pbKeysSelect = $(By.xpath("//span[text()='Ключі ПриватБанку']"));
    private SelenideElement keyPathInput = $(By.xpath("(//body[@id='sign']//input[@type='text'])[1]"));
    private SelenideElement passwordKeyInput = $(By.xpath("(//body[@id='sign']//div[@data-type='jks1']//input[@type='password'])[1]"));
    private SelenideElement nextButton = $(By.xpath("//div[@class='btn btn-green']"));
    private SelenideElement signAndSendButton = $(By.id("sign_and_send"));
    private SelenideElement roleSelect = $(By.xpath("//table[@class='keys-table']//select"));
    private SelenideElement keysSave = $(By.id("keys-save"));
    private SelenideElement keysCancel = $(By.id("keys-cancel"));
    private SelenideElement reportStage = $(By.id("report_stage"));

    private SelenideElement resultSum = $(By.name("T1RXXXXG7__row1"));
    private SelenideElement resultSumForReportPeriod = $(By.name("R03G7"));

    public CreateReportDeclarationJ0301206(IConfigurationVariables CV) {
        this.CV = CV;
    }

    public HashMap<SelenideElement, String> declarationJ0301206Fields = new HashMap<>();

    public CreateReportDeclarationJ0301206() {
        /***** І. Загальні відомості *****/
        declarationJ0301206Fields.put(year, "Рік");
        declarationJ0301206Fields.put(sequenceNumber, "порядковий N");
        declarationJ0301206Fields.put(comName, "Платник податку");
        declarationJ0301206Fields.put(innOrPassportSeria, "Податковий номер платника");
        declarationJ0301206Fields.put(kved, "КВЕД");
        declarationJ0301206Fields.put(zip, "поштовий індекс");
        declarationJ0301206Fields.put(cityCode, "міжміський код");
        declarationJ0301206Fields.put(telNumber, "телефон");
        declarationJ0301206Fields.put(faxNumber, "факс");
        declarationJ0301206Fields.put(locationAddress, "податкова адреса");
        declarationJ0301206Fields.put(email, "електронна адреса");
        declarationJ0301206Fields.put(controlAuthorityDropdown, "контролюючий орган");

        /***** ІІ. Розрахунок податкових зобов'язань збору за місця для паркування транспортних засобів *****/
        declarationJ0301206Fields.put(sumName, "Назва платежа");
        declarationJ0301206Fields.put(square, "Площа земельної ділянки");
        declarationJ0301206Fields.put(minSalary, "Мінімальна заробітна плата");
        declarationJ0301206Fields.put(countDays, "Кількість днів провадження");
        declarationJ0301206Fields.put(percent, "Ставка збору");
        declarationJ0301206Fields.put(taxSum, "Нарахована сума збору");
        declarationJ0301206Fields.put(taxSumSpecified, "сума збору за даними раніше поданої декларації");
        declarationJ0301206Fields.put(specifiedSum, "Уточнена сума");
        declarationJ0301206Fields.put(fineSum, "Сума штрафу");
        declarationJ0301206Fields.put(penaltySum, "Сума пені");
        declarationJ0301206Fields.put(addText, "Зміст доповнення");
        declarationJ0301206Fields.put(addDeclaration, "Доповнення до декларації");

        /***** ІІІ. Футер *****/
        declarationJ0301206Fields.put(dateDeclaration, "Дата заповнення");
        declarationJ0301206Fields.put(FIO, "Керівник");
        declarationJ0301206Fields.put(inn, "Реєстраційний номер");
        declarationJ0301206Fields.put(accountant, "Головний бухгалтер");
        declarationJ0301206Fields.put(accountantInn, "Реєстраційний номер бухгалтера");
    }


    public TaxDeclarationType chooseReportCheckBox =  () -> reportCheckBox.click();
    public TaxDeclarationType chooseReportNewCheckBox =  () -> reportNewCheckBox.click();
    public TaxDeclarationType chooseClarificationCheckBox =  () -> clarificationCheckBox.click();

    public ReportPeriodType choosePeriodFirstQuater = () -> reportPeriodFirstQuarter.click();
    public ReportPeriodType choosePeriodHalfYear = () -> reportPeriodHalfYear.click();
    public ReportPeriodType choosePeriodThreeQuarter = () -> reportPeriodThreeQuarter.click();
    public ReportPeriodType choosePeriodYear = () -> reportPeriodYear.click();

    public ReportPeriodSpecifiedType chooseSpecifiedPeriodFirstQuater = () -> reportPeriodFirstQuarterSpecified.click();
    public ReportPeriodSpecifiedType chooseSpecifiedPeriodHalfYear = () -> reportPeriodHalfYearSpecified.click();
    public ReportPeriodSpecifiedType chooseSpecifiedPeriodThreeQuarter = () -> reportPeriodThreeQuarterSpecified.click();
    public ReportPeriodSpecifiedType chooseSpecifiedPeriodYear = () -> reportPeriodYearSpecified.click();


    @Step("Открыть редактирование документа")
    public void clickEditButton() {
        editButton.shouldBe(visible).click();
    }

    @Step("Расчет ожидаемой суммы налога")
    public String calculationExpectedTax(String value1, String value2, String value3, String value4) {
        double num1 = Double.parseDouble(value1);
        double num2 = Double.parseDouble(value2);
        double num3 = Double.parseDouble(value3);
        double num4 = Double.parseDouble(value4);

        return String.format("%.2f", (num1 * num2 * num3 * num4) / 100).replace(",", ".");
    }

    @Step("Расчет насчитаной суммы за отчетный период")
    public String calculationExpectedTaxForReportPeriod(String value1, String value2, String value3, String value4, String value5) {
        double num1 = Double.parseDouble(value1);
        double num2 = Double.parseDouble(value2);
        double num3 = Double.parseDouble(value3);
        double num4 = Double.parseDouble(value4);
        double num5 = Double.parseDouble(value5);

        return String.format("%.2f", (num1 * num2 * num3 * num4) / 100 - num5).replace(",", ".");
    }

    @Step("Обираемо контролюючий орган, до якого подається податкова декларація")
    public String setControlAuthorityDropdown(Integer value) {
        controlAuthorityDropdown.selectOption(value);
        return controlAuthorityDropdown.getValue();
    }

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
        confirmSaveReport.shouldBe(visible).click(); //для отчета у которого есть ошибка
        sleep(2000);
        switchTo().frame(iframeSendReport.shouldBe(visible));
        if (pbKeysSelect.is(visible)) {
            sleep(2000);
            pbKeysSelect.shouldBe(visible).click();
            keyPathInput.shouldBe(visible).sendKeys(new File("src/main/resources/supportFiles/pb_3324314930.jks").getAbsolutePath());
            nextButton.shouldBe(visible).click();
            passwordKeyInput.shouldBe(visible).sendKeys("qwerty123");
            signAndSendButton.shouldBe(visible).click();
            roleSelect.shouldBe(visible).selectOption("Директор");
            keysSave.shouldBe(visible).click();
        } else {
            passwordKeyInput.shouldBe(visible).sendKeys("qwerty123");
            signAndSendButton.shouldBe(visible).click();
        }
    }

    @Step("Ожидание изменения статуса")
    public String waitReportStatusChange() {
        int countWait = 0;
        while (!reportStage.getText().equals("Надіслано, очікуйте...") && countWait < 60) {
            sleep(1000);
            refresh();
            countWait++;
        }
        return reportStage.getText();
    }

    @Step("Ввести в поле значение \"{value}\"")
    public void setValueToField(SelenideElement element, String value){
        clearField(element);
        element.shouldBe(visible).setValue(value);
        element.pressEnter();
    }

    @Step("Заполнение поля {name} в загальних відомостях")
    public void setValueToGeneralInfo(SelenideElement element, String name, DeclarationDataGeneralInformationJ0301206 data) {
        switch (name){
            case "Рік":setValueToField(element, data.getYear());
                break;
            case "порядковий N":setValueToField(element, data.getSequenceNumber());
                break;
            case "Платник податку":setValueToField(element, data.getComName());
                break;
            case "Податковий номер платника":setValueToField(element, data.getInnNumberOrPassport());
                break;
            case "КВЕД":setValueToField(element, data.getKved());
                break;
            case "поштовий індекс":setValueToField(element, data.getZip());
                break;
            case "міжміський код":setValueToField(element, data.getCityCode());
                break;
            case "телефон":setValueToField(element, data.getTelNumber());
                break;
            case "факс":setValueToField(element, data.getFaxNumber());
                break;
            case "податкова адреса":setValueToField(element, data.getLocationAddress());
                break;
            case "електронна адреса":setValueToField(element, data.getEmail());
                break;
            case "контролюючий орган":element.selectOption(data.getControlAuthority());
                break;
        }
    }

    @Step("Заполнение поля {name} в розрахуноках податкових зобов'язань")
    public void setValueToCalculationTax(SelenideElement element, String name, DeclarationDataCalculationTaxJ0301206 data) {
        switch (name){
            case "Назва платежа":setValueToField(element, data.getSumName());
                break;
            case "Площа земельної ділянки":setValueToField(element, data.getSquare());
                break;
            case "Мінімальна заробітна плата":setValueToField(element, data.getMinSalary());
                break;
            case "Кількість днів провадження":setValueToField(element, data.getCountDays());
                break;
            case "Ставка збору":setValueToField(element, data.getPercent());
                break;
            case "Нарахована сума збору":setValueToField(element, data.getTaxSum());
                break;
            case "сума збору за даними раніше поданої декларації":setValueToField(element, data.getTaxSumSpecified());
                break;
            case "Уточнена сума":setValueToField(element, data.getSpecifiedSum());
                break;
            case "Сума штрафу":setValueToField(element, data.getFineSum());
                break;
            case "Сума пені":setValueToField(element, data.getPenaltySum());
                break;
            case "Зміст доповнення":setValueToField(element, data.getAddText());
                break;
            case "Доповнення до декларації":setValueToField(element, data.getAddDeclaration());
                break;
        }
    }


    @Step("Заполнение поля {name} в персональну інформацію")
    public void setValueToPersonalInfo(SelenideElement element, String name, DeclarationDataPersonInfoJ0301206 data) {
        switch (name){
            case "Дата заповнення":setValueToField(element, data.getDateDeclaration());
                break;
            case "Керівник":setValueToField(element, data.getFIO());
                break;
            case "Реєстраційний номер":setValueToField(element, data.getInn());
                break;
            case "Головний бухгалтер":setValueToField(element, data.getAccountant());
                break;
            case "Реєстраційний номер бухгалтера":setValueToField(element, data.getAccountantInn());
                break;
        }
    }

    /***** Проверка данных в форме *****/
    @Step("Проверка поля {name} в загальних відомостях")
    public void checkValueInGeneralInfo(SelenideElement element, String name, String fieldSetValue) {
        switch (name){
            case "Рік":assertEquals(element.getValue(), fieldSetValue.replaceAll("\\D+", ""));
                break;
            case "порядковий N":assertEquals(element.getValue(), fieldSetValue.replaceAll("\\D+", ""));
                break;
            case "поштовий індекс":assertEquals(element.getValue(), fieldSetValue.replaceAll("\\D+", ""));
                break;
            case "міжміський код":assertEquals(element.getValue(), fieldSetValue.replaceAll("\\D+", ""));
                break;
        }
    }

    @Step("Проверка поля {name} в розрахуноках податкових зобов'язань")
    public void checkValueInCalculationTax(SelenideElement element, String name) {
        switch (name){
            case "Площа земельної ділянки":assertEquals(element.getValue(), "0.000");
                break;
            case "Мінімальна заробітна плата":assertEquals(element.getValue(), "0.00");
                break;
            case "Кількість днів провадження":assertEquals(element.getValue(), "0");
                break;
            case "Ставка збору":assertEquals(element.getValue(), "0.0000");
                break;
            case "Нарахована сума збору":assertEquals(element.getValue(), "0.00");
                break;
            case "сума збору за даними раніше поданої декларації":assertEquals(element.getValue(), "0.00");
                break;
            case "Уточнена сума":assertEquals(element.getValue(), "0.00");
                break;
            case "Сума штрафу":assertEquals(element.getValue(), "0.00");
                break;
            case "Сума пені":assertEquals(element.getValue(), "0.00");
                break;
            case "Доповнення до декларації":assertEquals(element.getValue(), "0");
                break;
        }
    }

    @Step("Заполнение поля {name} в персональну інформацію")
    public void checkValueInPersonalInfo(SelenideElement element, String name) {
        switch (name){
            case "Дата заповнення":assertEquals(element.getValue(), "");
                break;
        }
    }

    /***** Проверка данніх в полях формі, после корректного заполнения ****/

    @Step("Заполнение поля {name} в загальних відомостях")
    public void checkValueToGeneralInfo(SelenideElement element, String name, DeclarationDataGeneralInformationJ0301206 data) {
        switch (name){
            case "Рік":assertEquals(element.getValue(), data.getYear());
                break;
            case "порядковий N":assertEquals(element.getValue(), data.getSequenceNumber());
                break;
            case "Платник податку":assertEquals(element.getValue(), data.getComName());
                break;
            case "Податковий номер платника":assertEquals(element.getValue(), data.getInnNumberOrPassport());
                break;
            case "КВЕД":assertEquals(element.getValue(), data.getKved());
                break;
            case "поштовий індекс":assertEquals(element.getValue(), data.getZip());
                break;
            case "міжміський код":assertEquals(element.getValue(), data.getCityCode());
                break;
            case "телефон":assertEquals(element.getValue(), data.getTelNumber());
                break;
            case "факс":assertEquals(element.getValue(), data.getFaxNumber());
                break;
            case "податкова адреса":assertEquals(element.getValue(), data.getLocationAddress());
                break;
            case "електронна адреса":assertEquals(element.getValue(), data.getEmail());
                break;
//            case "контролюючий орган":assertEquals(element.getValue(), data.getControlAuthority());
//                break;
        }
    }

    @Step("Заполнение поля {name} в розрахуноках податкових зобов'язань")
    public void checkValueToCalculationTax(SelenideElement element, String name, DeclarationDataCalculationTaxJ0301206 data) {
        switch (name){
            case "Назва платежа":assertEquals(element.getValue(), data.getSumName());
                break;
            case "Площа земельної ділянки":assertEquals(element.getValue(), data.getSquare());
                break;
            case "Мінімальна заробітна плата":assertEquals(element.getValue(), data.getMinSalary());
                break;
            case "Кількість днів провадження":assertEquals(element.getValue(), data.getCountDays());
                break;
            case "Ставка збору":assertEquals(element.getValue(), data.getPercent());
                break;
            case "Нарахована сума збору":assertEquals(element.getValue(), data.getTaxSum());
                break;
            case "сума збору за даними раніше поданої декларації":assertEquals(element.getValue(), data.getTaxSumSpecified());
                break;
            case "Уточнена сума":assertEquals(element.getValue(), data.getSpecifiedSum());
                break;
            case "Сума штрафу":assertEquals(element.getValue(), data.getFineSum());
                break;
            case "Сума пені":assertEquals(element.getValue(), data.getPenaltySum());
                break;
            case "Зміст доповнення":assertEquals(element.getValue(), data.getAddText());
                break;
            case "Доповнення до декларації":assertEquals(element.getValue(), data.getAddDeclaration());
                break;
        }
    }


    @Step("Заполнение поля {name} в персональну інформацію")
    public void checkValueToPersonalInfo(SelenideElement element, String name, DeclarationDataPersonInfoJ0301206 data) {
        switch (name){
            case "Дата заповнення":assertEquals(element.getValue(), data.getDateDeclaration());
                break;
            case "Керівник":assertEquals(element.getValue(), data.getFIO());
                break;
            case "Реєстраційний номер":assertEquals(element.getValue(), data.getInn());
                break;
            case "Головний бухгалтер":assertEquals(element.getValue(), data.getAccountant());
                break;
            case "Реєстраційний номер бухгалтера":assertEquals(element.getValue(), data.getAccountantInn());
                break;
        }
    }
}




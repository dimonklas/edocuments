package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j;
import org.openqa.selenium.By;
import utils.ICustomWait;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;

@Log4j
public class LoginPage implements ICustomWait {

    private SelenideElement loginField = $(By.xpath("//input[@name='login']"));
    private SelenideElement passwordField = $(By.xpath("//input[@name='password']"));
    private SelenideElement submitButton = $(By.xpath("//button[@id='firstAuth']"));
    private SelenideElement warningMessage = $(By.xpath("//div[@class='header ng-binding']"));
    private SelenideElement warningSubmitButton = $(By.xpath("//*[@ng-click='model.stage = model.nextStage;']"));
    private SelenideElement authenticationPageTitle = $(By.xpath("//div[@class='header ng-binding']"));
    private SelenideElement authenticationNextButton = $(By.xpath("//button[@id='region']"));

    @Step("Авторизация")
    public void login(String login, String password) {
        loginField.shouldBe(visible).setValue(login);
        passwordField.shouldBe(visible).setValue(password);
        submitButton.shouldBe(visible).click();

        if (warningMessage.exists()) warningSubmitButton.shouldBe(visible).click();
        if (authenticationPageTitle.exists()) authenticationNextButton.shouldBe(visible).click();
    }

    public boolean needAuth() {
        return loginField.isDisplayed();
    }
}

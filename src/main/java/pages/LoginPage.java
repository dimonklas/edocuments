package pages;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j;
import org.aeonbits.owner.ConfigFactory;
import org.openqa.selenium.By;
import utils.IConfigurationVariables;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static utils.Utils.waitPreloader;

@Log4j
public class LoginPage {

    private final IConfigurationVariables CV = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());

    private SelenideElement loginField = $(By.xpath("//input[@name='login']"));
    private SelenideElement passwordField = $(By.xpath("//input[@name='password']"));
    private SelenideElement submitButton = $(By.xpath("//button[@id='firstAuth']"));
    private SelenideElement warningMessage = $(By.xpath("//div[@class='header ng-binding']"));
    private SelenideElement warningSubmitButton = $(By.xpath("//*[@ng-click='model.stage = model.nextStage;']"));
    private SelenideElement authenticationPageTitle = $(By.xpath("//div[@class='header ng-binding']"));
    private SelenideElement authenticationNextButton = $(By.xpath("//button[@id='region']"));


    @Step("Авторизация")
    public void login(String login) {
        loginField.shouldBe(visible).setValue(login);
        passwordField.shouldBe(visible).setValue(CV.userPassword());
        waitPreloader();
        submitButton.shouldBe(visible, enabled).click();
        waitPreloader();
        $x("//*[text()='Несуществующий логин или неверный пароль']").waitUntil(hidden, 1000);
        $x("//*[text()='Неправильный пользователь или пароль']").waitUntil(hidden, 1000);
        if (warningSubmitButton.isDisplayed()) warningSubmitButton.shouldBe(visible, enabled).click();
        waitPreloader();
        if (authenticationNextButton.isDisplayed()) authenticationNextButton.shouldBe(visible, enabled).click();
        waitPreloader();
    }

    public boolean needAuth() {
        return loginField.isDisplayed();
    }
}

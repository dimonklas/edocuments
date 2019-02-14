package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.testng.TextReport;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j;
import org.aeonbits.owner.ConfigFactory;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import pages.DocumentTypesListPage;
import pages.LoginPage;
import pages.MainPage;
import utils.AllureOnFailListener;
import utils.IConfigurationVariables;


import static com.codeborne.selenide.Selenide.*;

@Log4j
@Listeners({AllureOnFailListener.class, TextReport.class})
public class BaseTest {
    IConfigurationVariables confVariable = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());

    @BeforeSuite
    public void setUp() {
        Configuration.timeout = 10000;
        Configuration.startMaximized = true;
        Configuration.baseUrl = confVariable.mainPageUrl();
        Configuration.browser = "utils.driverProvider.ChromeDriverProvider";

        switch (Integer.parseInt(confVariable.CurrentBrowser())) {
            case 0:
                Configuration.browser = "utils.driverProvider.FirefoxDriverProvider";
                break;
            case 1:
                Configuration.browser = "utils.driverProvider.ChromeDriverProvider";
                break;
            default:
                Configuration.browser = "utils.driverProvider.FirefoxDriverProvider";
        }
    }

    @BeforeMethod
    public void authorization(){
        open(Configuration.baseUrl);
        switchToDefaultContent();
        clearBrowserCookiesAndCache();
        sleep(1000);

        LoginPage loginPage = new LoginPage();
        if (loginPage.needAuth()) loginPage.login(confVariable.userLogin(), confVariable.userPassword());
    }

    public void clearBrowserCookiesAndCache() {
        WebDriverRunner.clearBrowserCache();
        Selenide.clearBrowserCookies();
        refresh();
    }


    public void switchToDefaultContent() {
        long waitCount = 0;
        long sleep = 1000;
        long wait = 10000;
        boolean flag = false;

        while (!flag) {
            try {
                waitCount += sleep;
                switchTo().defaultContent();
                flag = true;
            } catch (Throwable e) {
                log.info("Не получилось переключится");
                sleep(sleep);
                if (waitCount > wait) break;
            }
        }
    }

    @AfterSuite(enabled = true)
    public void tearDown() {
        open(Configuration.baseUrl);
        switchToDefaultContent();
        clearBrowserCookiesAndCache();
        sleep(1000);

        LoginPage loginPage = new LoginPage();
        if (loginPage.needAuth()) loginPage.login(confVariable.userLogin(), confVariable.userPassword());
        DocumentTypesListPage typesListPage = new MainPage().openReportTypesListPage();
        typesListPage.searchDocument(confVariable.docName());
        typesListPage.deleteAllDocument(confVariable.docName());
    }
}

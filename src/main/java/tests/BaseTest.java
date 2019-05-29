package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.codeborne.selenide.testng.TextReport;
import lombok.extern.log4j.Log4j;
import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.*;
import pages.DocumentTypesListPage;
import pages.DropDownListPage;
import pages.LoginPage;
import pages.MainPage;
import utils.AllureOnFailListener;
import utils.IConfigurationVariables;
import utils.sid.ProminSession;


import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import static com.codeborne.selenide.Configuration.baseUrl;
import static com.codeborne.selenide.Selenide.*;
import static java.lang.System.getProperty;
import static java.util.Optional.ofNullable;
import static utils.Cookie.*;
import static utils.SupportActions.waitPreloader;

@Log4j
@Listeners({AllureOnFailListener.class, TextReport.class})
public class BaseTest {
    private final IConfigurationVariables confVariable = ConfigFactory.create(IConfigurationVariables.class, System.getProperties());
    private static final Logger LOGGER = Logger.getLogger(BaseTest.class);
    protected String prominSession;

    @BeforeSuite
    public void setUp() {
        Configuration.timeout = 10000;
        Configuration.startMaximized = true;
        Configuration.baseUrl = confVariable.mainPageUrl();
        Configuration.browser = "utils.driverProvider.ChromeDriverProvider";
        prominSession = new ProminSession().getAdminSession();

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
    public void authorization() {
        open(Configuration.baseUrl);
        switchToDefaultContent();
        clearBrowserCookiesAndCache();
        waitPreloader();

        LoginPage loginPage = new LoginPage();
        if (loginPage.needAuth()) loginPage.login(confVariable.userLogin());
        setCookie();
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

    @AfterSuite(enabled = false)
    public void tearDown() {
        open(Configuration.baseUrl);
        switchToDefaultContent();
        clearBrowserCookiesAndCache();
        sleep(1000);

        LoginPage loginPage = new LoginPage();
        if (loginPage.needAuth()) loginPage.login(confVariable.userLogin());
        DocumentTypesListPage typesListPage = new MainPage().openReportTypesListPage();
        typesListPage.deleteAllDocument(confVariable.docName());

        typesListPage.goToMainPage();
        DropDownListPage dropDownListPage = new MainPage().openDropDownList();
        dropDownListPage.deleteAllDocument(confVariable.docName());
    }

    @AfterSuite(alwaysRun = true)
    public void createEnvironmentProps() {
        FileOutputStream fos = null;
        try {
            Properties props = new Properties();
            fos = new FileOutputStream("target/allure-results/environment.properties");

            ofNullable(baseUrl).ifPresent(s -> props.setProperty("project.URL", confVariable.mainPageUrl()));
            ofNullable(getProperty("browser")).ifPresent(s -> props.setProperty("browser", s));
            ofNullable(getProperty("driver.version")).ifPresent(s -> props.setProperty("driver.version", s));
            ofNullable(getProperty("os.name")).ifPresent(s -> props.setProperty("os.name", s));
            ofNullable(getProperty("os.version")).ifPresent(s -> props.setProperty("os.version", s));
            ofNullable(getProperty("os.arch")).ifPresent(s -> props.setProperty("os.arch", s));

            props.store(fos, "See https://github.com/allure-framework/allure-app/wiki/Environment");

            fos.close();
        } catch (IOException e) {
            LOGGER.error("IO problem when writing allure properties file", e);
        } finally {
            IOUtils.closeQuietly(fos);
        }
    }
}

package utils.driverProvider;

import com.codeborne.selenide.WebDriverProvider;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

public class FirefoxDriverProvider implements WebDriverProvider {
    @Override
    public WebDriver createDriver(DesiredCapabilities desiredCapabilities) {

        FirefoxProfile myProfile = new FirefoxProfile();
        myProfile.addExtension(new File( new File("dependencies/cryptoplugin_ext_id_1.0.2@privatbank.ua.xpi").getAbsolutePath()));
        FirefoxOptions options = new FirefoxOptions()
                .addPreference("network.proxy.type",0)
                .addPreference("browser.cache.disk.enable", false)
                .addPreference("browser.cache.memory.enable", false)
                .addPreference("browser.cache.offline.enable", false)
                .addPreference("network.negotiate-auth.trusted-uris", false)
                .addPreference("browser.download.folderList",2)
                .addPreference("browser.download.manager.showWhenStarting",false)
                .addPreference("browser.download.dir",System.getProperty("user.dir"))
                .addPreference("browser.helperApps.neverAsk.saveToDisk", "text/plain, application/vnd.ms-excel, text/csv, application/csv, text/comma-separated-values, application/download, application/octet-stream, binary/octet-stream, application/binary, application/x-unknown")
                .setProfile(myProfile)
                .addPreference("network.http.use-cache", false);

        System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"/dev/null");
        FirefoxDriverManager.firefoxdriver().setup();
        FirefoxDriver driver = new FirefoxDriver(options);

        return driver;
    }
}

package ChromeSelenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class GpdiDemo {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver","C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();

        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
        WebDriver webDriver = new ChromeDriver(options);

//        webDriver.get("http://10.202.246.70/?service=chinaccs.cn/#/portals");
        webDriver.get("http://10.202.246.70/?service=chinaccs.cn/#/portals");
        System.out.println(webDriver.getTitle());
        webDriver.findElement(By.xpath("//div[@id='app']/div/div/div/div/div[3]/div/ul/li[2]")).click();
        webDriver.findElement(By.xpath("//div[@id='app']/div/div/div")).click();
        webDriver.findElement(By.xpath("//div[@id='app']/div/div[2]/div/div/div/ul/div/li/ul/div[8]/a/li")).click();
        webDriver.findElement(By.xpath("//div[@id='app']/div/div[2]/div[2]/section/div/div[2]/div/div/div/div/button")).click();

    }
}

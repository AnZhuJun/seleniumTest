package Selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FirstDemo {
    public static void main(String[] args) {

        System.setProperty("webdriver.chrome.driver","D:\\chromedriver\\chromedriver.exe");

        WebDriver webDriver = new ChromeDriver();

        webDriver.get("https://cn.bing.com/");
        System.out.println("This test page title is : " + webDriver.getTitle());

        webDriver.manage().window().maximize();

        webDriver.findElement(By.id("sb_form_q")).sendKeys("bilibili");
        webDriver.findElement(By.id("sb_form_go")).click();
        webDriver.findElement(By.linkText("哔哩哔哩 (゜-゜)つロ 干杯~-bilibili")).click();

        Set<String> windowhandle = webDriver.getWindowHandles();
        List<String> allWindows = new ArrayList<String>(windowhandle);
        webDriver.switchTo().window(allWindows.get(1));
//        webDriver.findElement(By.xpath("//form[@id='nav_searchform']/input")).sendKeys("java");
//        webDriver.findElement(By.xpath("//form[@id='nav_searchform']/div")).click();

//       System.out.println(webDriver.findElement(By.id("primaryChannelMenu")).getText());
//       System.out.println(webDriver.findElement(By.id("primaryChannelMenu")).findElement(By.xpath("//span[text()=\"娱乐\"]")).getText());
        webDriver.findElement(By.id("primaryChannelMenu")).findElement(By.xpath("//span[text()=\"娱乐\"]")).click();
//        System.out.println(webDriver.findElement(By.xpath("/html/body/div")).getText());
        webDriver.findElement(By.xpath("//div[3]/div/div/a/div/img")).click();
//        String js = "document.getElementById(\"van-popover-5857\").style.display='block'";
//        JavascriptExecutor javascriptExecutor = (JavascriptExecutor)webDriver;
//        javascriptExecutor.executeScript(js);

    }
}

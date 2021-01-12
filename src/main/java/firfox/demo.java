package firfox;

import ChromeSelenium.tools.ExcelData;
import ChromeSelenium.tools.TakeFilePathAndName;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static ChromeSelenium.ptService.普通项目置购物资模块.pressEnter;

public class demo {
    public static void main(String[] args) throws InterruptedException, AWTException, IOException {
        WebDriver webDriver;
        System.setProperty("webdriver.firefox.bin","C:\\Program Files\\Mozilla Firefox\\firefox.exe");
        System.setProperty("webdriver.gecko.driver","C:\\Program Files\\Mozilla Firefox\\geckodriver.exe");

//        FirefoxOptions options = new FirefoxOptions();
//        options.setLogLevel(FirefoxDriverLogLevel.DEBUG);
//        options.addPreference("debuggerAddress", "127.0.0.1:4444");
        webDriver = new FirefoxDriver();
        webDriver.get("http://4a.chinatowercom.cn:20000/uac/index");

        ExcelData excelData = new ExcelData("D:\\seleniumWork\\excel数据表格\\excel1.xlsx", "sheet1");
        int excelRows = excelData.getRows();

        for (int i = 2; i <= excelRows; i++) {
            String Bi = excelData.getExcelDateByIndex(i - 1, 2 - 1);
            System.out.println(Bi);
            String Ai = excelData.getExcelDateByIndex(i - 1, 1 - 1);
            System.out.println(Ai);

            List<String> fileNameType = new ArrayList<String>();

            logIn(webDriver);

            if (Bi.equals("0.0")) {

                Thread.sleep(1000);
                doNYByTitle(webDriver);

                Thread.sleep(1000);
                findTittletext(webDriver, Ai);

                fileNameType = TakeFilePathAndName.getFile(Ai, 0);

                Thread.sleep(5000);
                pressEnter();

                Thread.sleep(1000);

                for (int index = 0; index < fileNameType.size(); index++) {

                    Thread.sleep(1000);
                    uoloadFile(webDriver, fileNameType.get(index));
                    excelData.writeExcelDateByIndex("D:\\seleniumWork\\excel数据表格\\excel1.xlsx", "sheet1", i + 1, 2 - 1, "1");
                }

                Thread.sleep(1000);
                closeNYDetail(webDriver);
            }else {
                continue;
            }
        }
    }

    public static void closeNYDetail(WebDriver webDriver) {
        Set<String> windowhandle = webDriver.getWindowHandles();
        List<String> allWindows = new ArrayList<String>(windowhandle);
        webDriver.switchTo().window(allWindows.get(2)).close();
        windowhandle = webDriver.getWindowHandles();
        allWindows = new ArrayList<String>(windowhandle);
        webDriver.switchTo().window(allWindows.get(1)).close();
    }


    public static void uoloadFile(WebDriver webDriver, String fileName) throws InterruptedException, IOException {
        //点上传附件
        webDriver.findElement(By.id("fileDiv")).findElement(By.xpath(".//div/div[2]/div[2]/div[1]/table/tbody/tr/td/span/a/span")).click();

        //切换到上传附件窗口iframe
        webDriver.switchTo().frame(webDriver.findElement(By.xpath("//iframe[con" +
                "tains(@src,'/pms/pub/uploadfile/FlashUpload')]")));


        //上传附件点击
        webDriver.findElement(By.xpath("//html/body/div[2]/form/table/tbody/tr[2]/td/span/span")).click();
        Thread.sleep(1000);
        webDriver.findElement(By.xpath("//html/body/div[2]/form/table/tbody/tr[2]/td/span")).findElement(By.cssSelector("object.swfupload")).click();
        Thread.sleep(1000);
        Runtime.getRuntime().exec("D:\\autoltWork\\" + fileName + "文件选择程序.exe");

        //上传类型--查勘表
        webDriver.findElement(By.xpath("//html/body/div[2]/form/table/tbody/tr/td/span/span/input")).click();
        int size = webDriver.findElement(By.xpath("//html/body/div[4]/div/div[1]/div[2]/div[1]/table/tbody")).findElements(By.tagName("tr")).size();
        webDriver.findElement(By.xpath("//html/body/div[2]/form/table/tbody/tr/td/span/span/input")).click();
        for(int i = 1;i<size;i++) {
            String temp = fileName;
            String temp1;

            webDriver.findElement(By.xpath("//html/body/div[2]/form/table/tbody/tr/td/span/span/input")).click();
            Thread.sleep(500);
            String contents = webDriver.findElement(By.xpath("//html/body/div[4]/div/div[1]/div[2]/div[1]/table/tbody/tr[" + i + "]/td[2]")).getAttribute("textContent");
            System.out.println(contents + "11");
            webDriver.findElement(By.xpath("//html/body/div[4]/div/div[1]/div[2]/div[1]/table/tbody/tr[" + i + "]/td[2]")).click();

            if(temp.equals("设计变更单") || temp.equals("说明文本") || temp.equals("预设计表"))
                temp = "设计文件（含设计方案会审纪要）";

            System.out.println(temp);

            if (contents.equals(temp))
                break;
        }

        Thread.sleep(1300);
        //上传
        webDriver.findElement(By.xpath("//html/body/div[2]/div/table/tbody/tr/td/a[1]/span")).click();
        Thread.sleep(2000);
        //取消按钮
        webDriver.findElement(By.xpath("//html/body/div[3]/div/div[2]/div[2]/div/table/tbody/tr[3]/td/a[1]/span")).click();
        Thread.sleep(3000);
        //关闭上传附件窗口
        Thread.sleep(3000);
        webDriver.switchTo().defaultContent();
        webDriver.findElement(By.xpath("//html/body/div[4]/div/div[1]/div/div[2]/span[4]")).click();

    }

    public static void logIn(WebDriver webDriver) {
        webDriver.findElement(By.id("username")).sendKeys("18911786690");
        webDriver.findElement(By.id("password")).sendKeys("gpDI510630");
        webDriver.findElement(By.xpath("//html/body/div[1]/div[2]/div[2]/div[2]/input[2]")).click();
    }

    public static void doNYByTitle(WebDriver webDriver) throws InterruptedException {
        Set<String> tempwh = webDriver.getWindowHandles();

        webDriver.get("http://4a.chinatowercom.cn:20000/uac/index");
        webDriver.findElement(By.xpath("//span[contains(.,'设计监理施工人员')]")).click();

        Thread.sleep(1000);
        Set<String> windowhandle = webDriver.getWindowHandles();
        List<String> allWindows = new ArrayList<String>(windowhandle);
        webDriver.switchTo().window(allWindows.get(1));
//        webDriver.findElement(By.xpath("//html/body/div[3]/div[2]/div[1]/ul/li/a")).click();
        webDriver.findElement(By.linkText("我的工作")).click();
        Thread.sleep(1000);
        webDriver.findElement(By.linkText("我的待办")).click();
        webDriver.switchTo().window(allWindows.get(1));
        Thread.sleep(1000);
        webDriver.switchTo().frame("pageSet");
        Thread.sleep(1000);
        webDriver.switchTo().frame("mainframe");
        Thread.sleep(1000);
        webDriver.switchTo().frame("tab1");
    }


    public static void findTittletext(WebDriver webDriver,String taskTitletext) throws InterruptedException {
        Set<String> windowhandle = webDriver.getWindowHandles();
        List<String> allWindows = new ArrayList<String>(windowhandle);
        //通过taskTittletext查询
        Thread.sleep(1500);
        webDriver.findElement(By.id("taskTitle$text")).sendKeys(taskTitletext);
        webDriver.findElement(By.linkText("查询")).click();

        Thread.sleep(3000);
        webDriver.findElement(By.id("taskDataGrid1")).findElement(By.xpath(".//div/div[2]/div[4]/div[2]/div/table/tbody")).findElement(By.xpath(".//*[@id=\"mini-29$row2$11\"]/td[2]/div/a")).click();
        Thread.sleep(1000);
        windowhandle = webDriver.getWindowHandles();
        allWindows = new ArrayList<String>(windowhandle);
        webDriver.switchTo().window(allWindows.get(2));
    }


}

package ChromeSelenium.ptService;

import ChromeSelenium.tools.ExcelData;
import ChromeSelenium.tools.TakeFilePathAndName;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

//普通项目上传附件
public class 普通项目上传附件 {
    public static void main(String[] args) throws Exception {
            System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");
            ChromeOptions options = new ChromeOptions();
            ExcelData excelData = new ExcelData("D:\\seleniumWork\\excel数据表格\\excel1.xlsx", "sheet1");

            options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
            WebDriver webDriver = new ChromeDriver(options);

//        webDriver.get("http://10.202.246.70/?service=chinaccs.cn/#/portals");
            webDriver.get("http://4a.chinatowercom.cn:20000/uac/index");

            int excelRows = excelData.getRows();
            for (int i = 2; i <= excelRows; i++) {
                String Bi = excelData.getExcelDateByIndex(i - 1, 2 - 1);
                System.out.println(Bi);
                String Ai = excelData.getExcelDateByIndex(i - 1, 1 - 1);
                System.out.println(Ai);

                List<String> fileNameType = new ArrayList<String>();

                if (Bi.equals("0.0")) {
                    webDriver.get("chrome://settings/clearBrowserData");
                    webDriver.manage().window().maximize();
                    JavascriptExecutor js1 = (JavascriptExecutor) webDriver;
                    WebElement clearData = (WebElement) js1.executeScript("return document.querySelector('settings-ui').shadowRoot.querySelector('settings-main').shadowRoot.querySelector('settings-basic-page').shadowRoot.querySelector('settings-section > settings-privacy-page').shadowRoot.querySelector('settings-clear-browsing-data-dialog').shadowRoot.querySelector('#clearBrowsingDataDialog').querySelector('#clearBrowsingDataConfirm')");
// now you can click on clear data button
                    clearData.click();

                    webDriver.manage().deleteAllCookies();
                    String js = "window.localStorage.clear()";
                    JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
                    javascriptExecutor.executeScript(js);

                    webDriver.get("http://4a.chinatowercom.cn:20000/uac/index");
                    logIn(webDriver);
                    webDriver.get("http://4a.chinatowercom.cn:20000/uac/index");
                    doNYByTitle(webDriver);
                    findTittletext(webDriver, Ai);

                    fileNameType = TakeFilePathAndName.getFile(Ai, 0);

                    Thread.sleep(6000);
                    pressEnter();

                    for (int index = 0; index < fileNameType.size(); index++) {

                        Thread.sleep(1000);
                        uoloadFile(webDriver, fileNameType.get(index));
                    }

                    excelData.writeExcelDateByIndex("D:\\seleniumWork\\excel数据表格\\excel1.xlsx", "sheet1", i - 1, 2 - 1, "1");
                    closeNYDetail(webDriver);
                    cleanAllCookies(webDriver);
                } else {
                    continue;
                }
            }
//        }

    }

    public static void cleanAllCookies(WebDriver webDriver) {
        Set<String> windowhandle = webDriver.getWindowHandles();
        List<String> allWindows = new ArrayList<String>(windowhandle);
        windowhandle = webDriver.getWindowHandles();
        allWindows = new ArrayList<String>(windowhandle);

        webDriver.switchTo().window(allWindows.get(0));
        webDriver.manage().deleteAllCookies();
        String js = "window.localStorage.clear()";
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
        javascriptExecutor.executeScript(js);
    }

    public static void logIn(WebDriver webDriver) throws InterruptedException {
        webDriver.findElement(By.id("username")).sendKeys("18911786690");
        webDriver.findElement(By.id("password")).sendKeys("gpDI510630");
        Thread.sleep(500);
        webDriver.findElement(By.xpath("//html/body/div[1]/div[2]/div[2]/div[2]/input[2]")).click();

    }

    public static void closeNYDetail(WebDriver webDriver) {
        Set<String> windowhandle = webDriver.getWindowHandles();
        List<String> allWindows = new ArrayList<String>(windowhandle);
        webDriver.switchTo().window(allWindows.get(2)).close();
        windowhandle = webDriver.getWindowHandles();
        allWindows = new ArrayList<String>(windowhandle);
        webDriver.switchTo().window(allWindows.get(1)).close();
    }

    public static void doNYByTitle(WebDriver webDriver) throws InterruptedException {
        Set<String> tempwh = webDriver.getWindowHandles();
        List<String> tempall = new ArrayList<String>(tempwh);
        webDriver.switchTo().window(tempall.get(0));
        webDriver.get("http://4a.chinatowercom.cn:20000/uac/index");
        webDriver.findElement(By.xpath("//span[contains(.,'设计监理施工人员')]")).click();

        Set<String> windowhandle = webDriver.getWindowHandles();
        List<String> allWindows = new ArrayList<String>(windowhandle);
        webDriver.switchTo().window(allWindows.get(1));
        webDriver.findElement(By.linkText("我的工作")).click();
        webDriver.findElement(By.linkText("我的待办")).click();
        webDriver.switchTo().window(allWindows.get(1));
        webDriver.switchTo().frame("pageSet");
        webDriver.switchTo().frame("mainframe");
        webDriver.switchTo().frame("tab1");
    }

    public static void findTittletext(WebDriver webDriver,String taskTitletext) throws InterruptedException {
        Set<String> windowhandle = webDriver.getWindowHandles();
        List<String> allWindows = new ArrayList<String>(windowhandle);
        //通过taskTittletext查询
        webDriver.findElement(By.id("taskTitle$text")).sendKeys(taskTitletext);
        webDriver.findElement(By.linkText("查询")).click();

        Thread.sleep(1000);
        webDriver.findElement(By.id("taskDataGrid1")).findElement(By.xpath(".//div/div[2]/div[4]/div[2]/div/table/tbody/tr[3]/td[2]/div/a")).click();
        Thread.sleep(1000);
        windowhandle = webDriver.getWindowHandles();
        allWindows = new ArrayList<String>(windowhandle);
        webDriver.switchTo().window(allWindows.get(2));
    }


    public static void pressEnter() throws InterruptedException, AWTException {

        Robot r = new Robot();
        r.keyPress(KeyEvent.VK_ENTER);
        r.keyRelease(KeyEvent.VK_ENTER);
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
        Thread.sleep(250);
        Runtime.getRuntime().exec("D:\\autoltWork\\" + fileName + "文件选择程序.exe");

        //上传类型--查勘表
        webDriver.findElement(By.xpath("//html/body/div[2]/form/table/tbody/tr/td/span/span/input")).click();
        Thread.sleep(1000);
        int size = webDriver.findElement(By.xpath("//html/body/div[4]/div/div[1]/div[2]/div[1]/table/tbody")).findElements(By.tagName("tr")).size();
        webDriver.findElement(By.xpath("//html/body/div[2]/form/table/tbody/tr/td/span/span/input")).click();
        for(int i = 1;i<=size;i++) {
            String temp = fileName;

            webDriver.findElement(By.xpath("//html/body/div[2]/form/table/tbody/tr/td/span/span/input")).click();

            String contents = webDriver.findElement(By.xpath("//html/body/div[4]/div/div[1]/div[2]/div[1]/table/tbody/tr[" + i + "]/td[2]")).getAttribute("textContent");
            webDriver.findElement(By.xpath("//html/body/div[4]/div/div[1]/div[2]/div[1]/table/tbody/tr[" + i + "]/td[2]")).click();

            if(temp.equals("设计变更单") || temp.equals("说明文本") || temp.equals("预设计表"))
                temp = "设计文件（含设计方案会审纪要）";

            if(temp.equals("现场核查照片") || temp.equals("非模块化预算") || temp.equals("交付说明书"))
                temp = "检测报告";

            if (contents.equals(temp))
                break;
        }

        //打印是否为查勘表
//        System.out.println(webDriver.findElement(By.xpath("//html/body/div[4]/div/div[1]/div[2]/div[1]/table/tbody/tr[" + i + "]/td[2]")).getAttribute("textContent"));


        //上传
        webDriver.findElement(By.xpath("//html/body/div[2]/div/table/tbody/tr/td/a[1]/span")).click();
        //确定按钮
        webDriver.findElement(By.xpath("//html/body/div[3]/div/div[2]/div[2]/div/table/tbody/tr[3]/td/a[1]/span")).click();

        //关闭上传附件窗口
        Thread.sleep(3500);
//        webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[2]/div[2]/a/span")).click();
//        webDriver.switchTo().defaultContent();
        webDriver.switchTo().defaultContent();
        webDriver.findElement(By.xpath("//html/body/div[5]/div/div[1]/div/div[2]/span[4]")).click();

        //运行去除readonly属性
//        String js = "document.getElementById('fileupload$text').removeAttribute('readonly')";
//        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) webDriver;
//        javascriptExecutor.executeScript(js);
    }

    public static void setAndctrlVClipboardData(String string) {
        StringSelection stringSelection = new StringSelection(string);
        //使用Toolkit对象的setContents将字符串放到剪切板中
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e1) {
            e1.printStackTrace();
        }

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);

    }
    public void pressTabKey()
    {
        Robot robot = null;
        try{
            robot = new Robot();
        }catch(AWTException e1){
            e1.printStackTrace();
        }
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
    }

    public static void pressEnterKey()
    {
        Robot robot = null;
        try{
            robot = new Robot();
        }catch(AWTException e1){
            e1.printStackTrace();
        }
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);

    }

}

//        webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[4]/div[2]/div/table/tbody/tr[3]/td[2]/div/
//        a/span")).click();
//        webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[4]/div[2]/div/table/tbody")).findElement(By.tagName("tr")).findElement(By.xpath("//td[3]"));
//        webDriver.findElement(By.xpath("//html/body/div[1]/div/div[2]/div[4]/div[2]/div/table/tbody")).findElement(By.tagName("tr")).findElement(By.xpath("//td[2]")).click();
//         webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div
//         [4]/div[2]/div/table/tbody")).findElement(By.tagName("tr")).findElement(By.xpath("//td[2]/div")).click();
//        webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[4]/div[2]/div/table/tbody")).findElement(By.linkText("中国铁塔山西分公司太原市分公司2020年玉门河前进路口换电站主动规划能源类项目")).getText();
//        webDriver.findElement(By.xpath("//td[@id='1$cell$12']/div/a/span")).click();
//        webDriver.findElement(By.xpath("//td[4]/span")).click();


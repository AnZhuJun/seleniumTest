package Selenium;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.Thread.sleep;

public class TowerDemo {
    public static void main(String[] args) throws Exception {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        ExcelData excelData = new ExcelData("D:\\seleniumWork\\excel数据表格\\excel1.xlsx", "sheet1");

        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
        WebDriver webDriver = new ChromeDriver(options);

//        webDriver.get("http://10.202.246.70/?service=chinaccs.cn/#/portals");
        webDriver.get("http://4a.chinatowercom.cn:20000/uac/index");


        int excelRows = excelData.getRows();
        for (int i = 2;i<=excelRows;i++) {
            String Bi = excelData.getExcelDateByIndex(i-1,2-1);
            System.out.println(Bi);
            String Ai = excelData.getExcelDateByIndex(i-1,1-1);
            System.out.println(Ai);
            if (Bi.equals("0.0")) {
                doNYByTitle(webDriver);
                findTittletext(webDriver,Ai);

                Thread.sleep(2000);
                pressEnter();

                Thread.sleep(1000);
                uoloadFile(webDriver, "查勘表");

                Thread.sleep(1000);
                uoloadFile(webDriver, "现场布置图");

                closeNYDetail(webDriver);
            }else {
                continue;
            }
        }
    }

    public static void closeNYDetail(WebDriver webDriver) {
        Set<String> windowhandle = webDriver.getWindowHandles();
        List<String> allWindows = new ArrayList<String>(windowhandle);
        webDriver.switchTo().window(allWindows.get(3)).close();
        windowhandle = webDriver.getWindowHandles();
        allWindows = new ArrayList<String>(windowhandle);
        webDriver.switchTo().window(allWindows.get(2)).close();
    }

    public static void doNYByTitle(WebDriver webDriver) throws InterruptedException {
        Set<String> tempwh = webDriver.getWindowHandles();
        List<String> tempall = new ArrayList<String>(tempwh);
        webDriver.switchTo().window(tempall.get(0));
        webDriver.get("http://4a.chinatowercom.cn:20000/uac/index");
        webDriver.findElement(By.xpath("//span[contains(.,'设计监理施工人员')]")).click();

        Set<String> windowhandle = webDriver.getWindowHandles();
        List<String> allWindows = new ArrayList<String>(windowhandle);
        webDriver.switchTo().window(allWindows.get(2));
        webDriver.findElement(By.linkText("我的工作")).click();
        webDriver.findElement(By.linkText("我的待办")).click();
        webDriver.switchTo().window(allWindows.get(2));
        webDriver.switchTo().frame("pageSet");
        webDriver.switchTo().frame("mainframe");
        webDriver.findElement(By.id("mini-1$2")).click();
        webDriver.switchTo().frame("tab12");
    }

    public static void findTittletext(WebDriver webDriver,String taskTitletext) throws InterruptedException {
        Set<String> windowhandle = webDriver.getWindowHandles();
        List<String> allWindows = new ArrayList<String>(windowhandle);
        //通过taskTittletext查询
        webDriver.findElement(By.id("taskTitle$text")).sendKeys(taskTitletext);
        webDriver.findElement(By.linkText("查询")).click();

        Thread.sleep(2000);
        webDriver.findElement(By.id("taskDataGrid1")).findElement(By.xpath(".//div/div[2]/div[4]/div[2]/div/table/tbody")).findElement(By.xpath(".//*[@id=\"mini-30$row2$1\"]/td[2]/div/a")).click();
        Thread.sleep(2000);
        windowhandle = webDriver.getWindowHandles();
        allWindows = new ArrayList<String>(windowhandle);
        webDriver.switchTo().window(allWindows.get(3));
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
        Thread.sleep(1000);
        Runtime.getRuntime().exec("D:\\autoltWork\\" + fileName + "文件选择程序.exe");

        //上传类型--查勘表
        webDriver.findElement(By.xpath("//html/body/div[2]/form/table/tbody/tr/td/span/span/input")).click();
        int size = webDriver.findElement(By.xpath("//html/body/div[4]/div/div[1]/div[2]/div[1]/table/tbody")).findElements(By.tagName("tr")).size();
        webDriver.findElement(By.xpath("//html/body/div[2]/form/table/tbody/tr/td/span/span/input")).click();
        for(int i = 1;i<size;i++) {

            webDriver.findElement(By.xpath("//html/body/div[2]/form/table/tbody/tr/td/span/span/input")).click();
            String contents = webDriver.findElement(By.xpath("//html/body/div[4]/div/div[1]/div[2]/div[1]/table/tbody/tr[" + i + "]/td[2]")).getAttribute("textContent");
            webDriver.findElement(By.xpath("//html/body/div[4]/div/div[1]/div[2]/div[1]/table/tbody/tr[" + i + "]/td[2]")).click();
            if (contents.equals(fileName))
                break;
        }

        //打印是否为查勘表
//        System.out.println(webDriver.findElement(By.xpath("//html/body/div[4]/div/div[1]/div[2]/div[1]/table/tbody/tr[" + i + "]/td[2]")).getAttribute("textContent"));

        Thread.sleep(1300);
        //上传
        webDriver.findElement(By.xpath("//html/body/div[2]/div/table/tbody/tr/td/a[1]/span")).click();

        //取消按钮
        webDriver.findElement(By.xpath("//html/body/div[3]/div/div[2]/div[2]/div/table/tbody/tr[3]/td/a[1]/span")).click();
        Thread.sleep(1000);
        //关闭上传附件窗口
        Thread.sleep(1000);
        webDriver.switchTo().defaultContent();
        webDriver.findElement(By.xpath("//html/body/div[4]/div/div[1]/div/div[2]/span[4]")).click();

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

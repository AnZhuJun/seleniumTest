package Selenium;

//Equipment and materials

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EquipmentMaterialsItem {
    public static void main(String[] args) throws InterruptedException, AWTException {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        ExcelData excelData = new ExcelData("D:\\seleniumWork\\excel数据表格\\excel1.xlsx", "sheet1");

        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
        WebDriver webDriver = new ChromeDriver(options);

        webDriver.get("http://4a.chinatowercom.cn:20000/uac/index");

        int excelRows = excelData.getRows();
        for (int i = 2;i<=excelRows;i++) {
            String Ai = excelData.getExcelDateByIndex(i-1,1-1);
            String Bi = excelData.getExcelDateByIndex(i-1,2-1);
            String Ci = excelData.getExcelDateByIndex(i-1,3-1);
            String Di = excelData.getExcelDateByIndex(i-1,4-1);
            System.out.println("项目名称:" + Ai + "   ,是否已经录入(1为是,0为否):" + Bi + "   ,服务规程格式:" + Ci + "   ,数量:" + Di);

            String[] CiArr = Ci.split(",");
            String[] DiArr = Di.split(",");

            if (Bi.equals("0.0")) {
                doNYByTitle(webDriver);
                findTittletext(webDriver,Ai);

                Thread.sleep(3000);
                pressEnter();

                Thread.sleep(1000);
                clickSelectService(webDriver);


                Thread.sleep(1000);
                if (CiArr.length == 1 && DiArr.length == 1)
                    buyService(webDriver,Ci,Di);
                else if (CiArr.length > 1 && DiArr.length > 1){
                    int length1;

                    if (CiArr.length <= DiArr.length){
                        length1 = CiArr.length;
                    }else {
                        length1 = DiArr.length;
                    }

                    for(int j = 0 ; j < length1;j++){
                        buyService(webDriver,CiArr[j],DiArr[j]);
                    }

                    Thread.sleep(2000);
                }
                Thread.sleep(1000);

            }else {
                continue;
            }
        }


//        doNYByTitle(webDriver);
//        findTittletext(webDriver,"中国铁塔山西分公司太原市分公司2020年玉门河前进路口换电站主动规划能源类项目");

//        Thread.sleep(3000);
//        pressEnter();
//        Thread.sleep(500);


//        clickSelectService(webDriver);
//        buyService(webDriver,"900101060810","2");


        webDriver.quit();
    }

    public static void clickSelectService(WebDriver webDriver) throws InterruptedException, AWTException {
        webDriver.switchTo().frame("NewFileServ");
        System.out.println(webDriver.findElement(By.xpath("//html/body/div[2]/table/tbody/tr/td[7]/a[2]/span")).getText());
        //单击选择服务
        webDriver.findElement(By.xpath("//html/body/div[2]/table/tbody/tr/td[7]/a[2]/span")).click();
        Thread.sleep(500);
        pressEnter();

        webDriver.switchTo().defaultContent();
    }

    public static void buyService(WebDriver webDriver,String serviceId,String nums) throws InterruptedException,AWTException{
        webDriver.switchTo().frame(webDriver.findElement(By.xpath("//iframe[con" +
                "tains(@src,'/pms/module/design/service/Detailgrid')]")));

        //输入服务规程格式编码
        webDriver.findElement(By.xpath("//html/body/div[2]/div/div[1]/div/div/div[2]/div/div[1]/table/tbody/tr/td[2]/span/span/input")).clear();
        webDriver.findElement(By.xpath("//html/body/div[2]/div/div[1]/div/div/div[2]/div/div[1]/table/tbody/tr/td[2]/span/span/input")).sendKeys(serviceId);

        //单击查询
        webDriver.findElement(By.xpath("//html/body/div[2]/div/div[1]/div/div/div[2]/div/div[1]/table/tbody/tr/td[3]/a[1]/span")).click();

        Thread.sleep(1000);
        //单击，选择第一行（单击名称列）
        webDriver.findElement(By.xpath("//html/body/div[2]/div/div[1]/div/div/div[2]/div/div[2]/div/div[2]/div[4]/div[2]/div/table/tbody/tr[3]/td[4]")).click();

        //单击确定
        webDriver.findElement(By.xpath("//html/body/div[2]/div/div[1]/div/div/div[2]/div/div[1]/table/tbody/tr/td[3]/a[2]/span")).click();
        Thread.sleep(1000);

        //单击数量输入框
        webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[2]/div/div[2]/div[4]/div[2]/div/table/tbody/tr[3]/td[7]")).click();

        Thread.sleep(1000);
        //将数量改为2.0000
        webDriver.findElement(By.xpath("//html/body/div[3]/span/span/input")).sendKeys(nums);


        Thread.sleep(1000);
        pressEnter();
        //单击保存
        webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[1]/a/span")).click();
        webDriver.switchTo().defaultContent();
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

    public static void printString(String string){
        System.out.println(string);
    }
}

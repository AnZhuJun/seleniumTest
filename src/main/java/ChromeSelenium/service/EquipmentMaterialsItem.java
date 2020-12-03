package ChromeSelenium.service;

//Equipment and materials

import ChromeSelenium.tools.ExcelData;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EquipmentMaterialsItem {
    public static void main(String[] args) throws InterruptedException, AWTException, IOException {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        ExcelData excelData = new ExcelData("D:\\seleniumWork\\excel数据表格\\excel1.xlsx", "sheet1");
        String excelPath = "D:\\seleniumWork\\excel数据表格\\excel1.xlsx";
        String excelSheet = "sheet1";

        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
        WebDriver webDriver = new ChromeDriver(options);

        webDriver.get("http://4a.chinatowercom.cn:20000/uac/index");

        int excelRows = excelData.getRows();
        for (int i = 2;i<=excelRows;i++) {
            String Ai = excelData.getExcelDateByIndex(i-1,1-1);
            String Bi = excelData.getExcelDateByIndex(i-1,2-1);
            String Ci = excelData.getExcelDateByIndex(i-1,3-1);
            String Di = excelData.getExcelDateByIndex(i-1,4-1);
            String Ei = excelData.getExcelDateByIndex(i-1,5-1);
            String Fi = excelData.getExcelDateByIndex(i-1,6-1);
            String Gi = excelData.getExcelDateByIndex(i-1,7-1);
            String Hi = excelData.getExcelDateByIndex(i-1,8-1);
            String Ii = excelData.getExcelDateByIndex(i-1,9-1);
            String Ji = excelData.getExcelDateByIndex(i-1,10-1);
            System.out.println("项目名称:" + Ai + "   ,是否已经录入(1为是,0为否):" + Bi + "   ,服务规程格式:" + Ci + "   ,数量:" + Di + "   ,单位计量:" + Ei + "   ,物资规程格式:" + Fi + "   ,数量:" + Gi + "   ,单位计量:" + Hi +
                    "   ,服务规程规格(该单价):" + Ii + "   ,单价:" + Ji);

            String[] CiArr = Ci.split(",");
            String[] DiArr = Di.split(",");
            String[] EiArr = Ei.split(",");
            String[] FiArr = Fi.split(",");
            String[] GiArr = Gi.split(",");
            String[] HiArr = Hi.split(",");
            String[] IiArr = Ii.split(",");
            String[] JiArr = Ji.split(",");

            if (Bi.equals("0.0") || Bi.equals("0")) {
                doNYByTitle(webDriver);
                findTittletext(webDriver,Ai);

                Thread.sleep(3000);
                pressEnter();

                Thread.sleep(1000);

//                模块服务清单选择服务
                clickSelectService(webDriver);

                Thread.sleep(1000);
                //判断是否只有一个要采购
                if (CiArr.length == 1 && DiArr.length == 1)
                    buyService(webDriver,Ci,Di,Ei);
                else if (CiArr.length > 1 && DiArr.length > 1){
                    int length1;

                    if (CiArr.length <= DiArr.length){
                        length1 = CiArr.length;
                    }else {
                        length1 = DiArr.length;

                    }

                    for(int j = 0 ; j < length1;j++){
                        buyService(webDriver,CiArr[j],DiArr[j],EiArr[j]);
                    }

                    Thread.sleep(2000);
                }
                Thread.sleep(1000);

                //只改单价类型的采购
                if (Ii.length()!= 0){
                    if (IiArr.length == 1 && JiArr.length == 1)
                        buyServicePrice(webDriver,Ii,Ji);
                    else if (IiArr.length > 1 && JiArr.length > 1){
                        int length2;

                        if(IiArr.length <= JiArr.length){
                            length2 = IiArr.length;
                        }else {
                            length2 = JiArr.length;
                        }

                        for(int j = 0 ; j < length2 ; j++){
                            buyServicePrice(webDriver,IiArr[j],JiArr[j]);
                        }

                        Thread.sleep(2000);
                    }
                }

                webDriver.findElement(By.xpath("//html/body/div[4]/div/div[1]/div/div[2]/span[4]")).click();
                Thread.sleep(1000);



                //甲供设备材料选择物资

                clickJiaService(webDriver);
                Thread.sleep(1000);

                if(FiArr.length == 1 && GiArr.length == 1)
                    buyJiaService(webDriver,Fi,Gi,Hi);
                else if (FiArr.length > 1 && GiArr.length > 1){
                    int length1;

                    if(FiArr.length <= GiArr.length)
                        length1 = FiArr.length;
                    else
                        length1 = GiArr.length;

                    for (int j = 0; j < length1 ;j++){
                        buyJiaService(webDriver,FiArr[j],GiArr[j],HiArr[j]);
                    }
                    Thread.sleep(2000);

                }


                excelData.writeExcelDateByIndex(excelPath,excelSheet,i-1,2-1,"1");

                closeNYDetail(webDriver);
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

    public static void closeNYDetail(WebDriver webDriver) {
        Set<String> windowhandle = webDriver.getWindowHandles();
        List<String> allWindows = new ArrayList<String>(windowhandle);
        webDriver.switchTo().window(allWindows.get(3)).close();
        windowhandle = webDriver.getWindowHandles();
        allWindows = new ArrayList<String>(windowhandle);
        webDriver.switchTo().window(allWindows.get(2)).close();
    }

    //服务模块选择物资
    public static void clickSelectService(WebDriver webDriver) throws InterruptedException, AWTException {
        webDriver.switchTo().frame("NewFileServ");
        System.out.println(webDriver.findElement(By.xpath("//html/body/div[2]/table/tbody/tr/td[7]/a[2]/span")).getText());
        //单击选择服务
        webDriver.findElement(By.xpath("//html/body/div[2]/table/tbody/tr/td[7]/a[2]/span")).click();
        Thread.sleep(500);
        pressEnter();

        webDriver.switchTo().defaultContent();
    }

    //单击到甲供设备材料选择物资
    public static void clickJiaService(WebDriver webDriver) throws InterruptedException {
        //单击甲供设备材料清单
        webDriver.findElement(By.xpath("//html/body/div[2]/div[4]/div/div/div[2]/div[2]/div[4]/table/tbody/tr/td[2]/div[1]/div[3]/table/tbody/tr/td[6]/span")).click();

        //切换iframe
        webDriver.switchTo().frame(webDriver.findElement(By.xpath("//iframe[con" +
                "tains(@src,'/pms/module/design/product/Baseinfo')]")));

        //单击选择物资
        webDriver.findElement(By.xpath("//html/body/div[2]/table/tbody/tr/td[7]/a[3]/span")).click();

        //切换iframe
        webDriver.switchTo().defaultContent();
    }

    //购买甲供物资
    public static void buyJiaService(WebDriver webDriver,String serviceId,String nums,String nums2) throws InterruptedException, AWTException {
        webDriver.switchTo().frame(webDriver.findElement(By.xpath("//iframe[con" +
                "tains(@src,'/pms/module/design/product/Detailgrid')]")));

        //清空物资规格程式,并输入编码
        webDriver.findElement(By.xpath("//html/body/div[2]/div/div[1]/div/div/div[2]/div/div[1]/table/tbody/tr/td[2]/span/span/input")).clear();
        webDriver.findElement(By.xpath("//html/body/div[2]/div/div[1]/div/div/div[2]/div/div[1]/table/tbody/tr/td[2]/span/span/input")).sendKeys(serviceId);

        //查询
        webDriver.findElement(By.xpath("//html/body/div[2]/div/div[1]/div/div/div[2]/div/div[1]/table/tbody/tr/td[3]/a/span")).click();

        Thread.sleep(1000);
        //单击选择第一行（单击名称列）
        webDriver.findElement(By.xpath("//html/body/div[2]/div/div[1]/div/div/div[2]/div/div[2]/div/div[2]/div[4]/div[2]/div/table/tbody/tr[3]/td[4]")).click();

        //单击确定
        webDriver.findElement(By.xpath("//html/body/div[2]/div/div[1]/div/div/div[2]/div/div[1]/table/tbody/tr/td[3]/a[2]/span")).click();
        Thread.sleep(2000);

        //根据id找到对应的那一行,单击数量
        for(int i = 3;i<100;i++) {
            if (webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[2]/div/div[2]/div[4]/div[2]/div/table/tbody/tr[" + i + "]/td[3]")).getText().equals(serviceId)) {//单击数量输入框
                webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[2]/div/div[2]/div[4]/div[2]/div/table/tbody/tr[" + i + "]/td[9]")).click();
                Thread.sleep(1000);
                //将数量改为2.0000
                webDriver.findElement(By.xpath("//html/body/div[3]/span/span/input")).sendKeys(nums);
                Thread.sleep(1000);
                pressEnter();

                if(nums2.equals("1") || nums2.equals("1.0")) {
                    //输入单位计量
                    Thread.sleep(1000);
                    webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[2]/div/div[2]/div[4]/div[2]/div/table/tbody/tr[" + i + "]/td[13]")).click();
                    webDriver.findElement(By.xpath("//html/body/div[3]/span/span/input")).sendKeys(nums2);
                    Thread.sleep(1000);
                    pressEnter();
                }
                break;
            }
        }

        Thread.sleep(500);
        //单击保存
        webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[1]/a/span")).click();
        webDriver.switchTo().defaultContent();

    }

    //购买物资(改价格模式)
    public static void buyServicePrice(WebDriver webDriver,String serviceId,String price) throws InterruptedException,AWTException{
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
        Thread.sleep(2000);

        for(int i = 3;i<100;i++) {
            if (webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[2]/div/div[2]/div[4]/div[2]/div/table/tbody/tr[" + i + "]/td[3]")).getText().equals(serviceId)) {//单击数量输入框
                webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[2]/div/div[2]/div[4]/div[2]/div/table/tbody/tr[" + i + "]/td[8]")).click();
                break;
            }
        }
        Thread.sleep(1000);
        //将数量改为2.0000
        webDriver.findElement(By.xpath("//html/body/div[3]/span/span/input")).sendKeys(price);

        Thread.sleep(1000);
        pressEnter();
        //单击保存
        webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[1]/a/span")).click();
        webDriver.switchTo().defaultContent();
    }


    //购买物资(改数量模式)
    public static void buyService(WebDriver webDriver,String serviceId,String nums,String nums2) throws InterruptedException,AWTException{
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
        Thread.sleep(2000);

        for(int i = 3;i<100;i++) {
            if (webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[2]/div/div[2]/div[4]/div[2]/div/table/tbody/tr[" + i + "]/td[3]")).getText().equals(serviceId)) {//单击数量输入框
                webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[2]/div/div[2]/div[4]/div[2]/div/table/tbody/tr[" + i + "]/td[7]")).click();
                Thread.sleep(1000);
                //将数量改为2.0000
                webDriver.findElement(By.xpath("//html/body/div[3]/span/span/input")).sendKeys(nums);
                Thread.sleep(1000);
                pressEnter();
                //单击保存
                webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[1]/a/span")).click();

                if (nums2.length() != 0) {
                    webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[2]/div/div[2]/div[4]/div[2]/div/table/tbody/tr[" + i + "]/td[13]")).click();
                    webDriver.findElement(By.xpath("//html/body/div[3]/span/span/input")).sendKeys(nums2);
                    Thread.sleep(1000);
                    pressEnter();
                    //单击保存
                    webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[1]/a/span")).click();
                }
                break;
            }
        }

        Thread.sleep(1000);


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

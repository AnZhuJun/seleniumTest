package ChromeSelenium.ptService;

import ChromeSelenium.service.preDesignDataSer;
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

public class EquipmentMaterPreDesign {
    public static void main(String[] args) throws AWTException, InterruptedException, IOException {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();

        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
        WebDriver webDriver = new ChromeDriver(options);

        webDriver.get("http://4a.chinatowercom.cn:20000/uac/index");

        String excelPath = "D:\\seleniumWork\\excel数据表格\\excel1.xlsx";
        String excelSheet = "sheet1";
        ExcelData excelData = new ExcelData(excelPath, excelSheet);
        int excelRows = excelData.getRows();
        List<String> name = new ArrayList<String>();

        for(int i = 2;i<=excelRows;i++){
            name.add(excelData.getExcelDateByIndex(i-1,1-1));
        }

        List<String> record = preDesignDataSer.getRecord();


        for (int i = 0;i<name.size();i++) {
            if (record.get(i).equals("0.0") || record.get(i).equals("0")) {
                preDesignDataSer preDesignDataSer = new preDesignDataSer();

                //设计表路径
                String preDesignPath = "D:\\seleniumWork\\excel数据表格\\" + name.get(i)  +"\\" + name.get(i) + "-预设计表.xlsx";

                //编号表
                List<String> idList = preDesignDataSer.getIdList(preDesignPath);
                //价格表
                List<String> priceList = preDesignDataSer.getPriceList(preDesignPath);
                //数量表
                List<String> numList = preDesignDataSer.getNumsList(preDesignPath);
                //外电价格(特殊)
                String outPrice = preDesignDataSer.getOutEle(preDesignPath);

                System.out.println(idList + " 长度 " + idList.size());
                System.out.println(priceList + " 长度 " + priceList.size());
                System.out.println(numList + " 长度 " + numList.size());

                int preDesignRows = idList.size();
                System.out.println("一共要处理的物资/服务有 ：  " + preDesignRows + "  个!");

                doNYByTitle(webDriver);
                findTittletext(webDriver, name.get(i));

                //处理第一个alert
                Thread.sleep(7000);
                pressEnter();

                Thread.sleep(1000);

                theService(webDriver, idList, numList, outPrice);

                Thread.sleep(1000);
                closeWindow(webDriver);

                theJiaService(webDriver, idList, numList);

                excelData.writeExcelDateByIndex(excelPath,excelSheet,i+1,2-1,"1");

                closeNYDetail(webDriver);


            }else {
                continue;
            }
        }

    }

    public static void theService(WebDriver webDriver, List<String> idList, List<String> numList, String outPrice) throws InterruptedException, AWTException {
        //                模块服务清单选择服务
        clickSelectService(webDriver);
        for (int i = 0; i< idList.size(); i++){
            String pdID = idList.get(i);
            String pdNUM = numList.get(i);

            System.out.println(pdID + "  :   " + pdNUM);

            if (pdID.startsWith("9")) {

                Thread.sleep(1000);
                if (pdID.equals("90010106000000000004")){
                    buyService(webDriver,pdID, outPrice);
                }else {
                    buyService(webDriver, pdID, pdNUM);
                }

            }else{
                continue;
            }
        }
    }

    public static void theJiaService(WebDriver webDriver, List<String> idList, List<String> numList) throws InterruptedException, AWTException {
        //甲供设备材料选择物资
        clickJiaService(webDriver);
        Thread.sleep(1000);
        for (int i = 0; i< idList.size(); i++){
            String pdID = idList.get(i);
            String pdNUM = numList.get(i);

            System.out.println(pdID + "  :   " + pdNUM);

            if (pdID.startsWith("0")) {
                Thread.sleep(1000);
                buyJiaService(webDriver, pdID, pdNUM);
            }else{
                continue;
            }
        }
    }

    public static void closeWindow(WebDriver webDriver) throws InterruptedException {
        webDriver.findElement(By.xpath("//html/body/div[5]/div/div[1]/div/div[2]/span[4]")).click();
        Thread.sleep(1000);
    }

    //购买甲供物资
    public static void buyJiaService(WebDriver webDriver,String serviceId,String nums) throws InterruptedException, AWTException {
        webDriver.switchTo().frame(webDriver.findElement(By.xpath("//iframe[con" +
                "tains(@src,'/pms/module/design/product/Detailgrid')]")));

        //清空物资规格程式,并输入编码
        webDriver.findElement(By.xpath("//html/body/div[2]/div/div[1]/div/div/div[2]/div/div[1]/table/tbody/tr/td[2]/span/span/input")).clear();
        webDriver.findElement(By.xpath("//html/body/div[2]/div/div[1]/div/div/div[2]/div/div[1]/table/tbody/tr/td[2]/span/span/input")).sendKeys(serviceId);

        //查询
        webDriver.findElement(By.xpath("//html/body/div[2]/div/div[1]/div/div/div[2]/div/div[1]/table/tbody/tr/td[3]/a/span")).click();

        Thread.sleep(1500);
        //单击选择第一行（单击名称列）
        webDriver.findElement(By.xpath("//html/body/div[2]/div/div[1]/div/div/div[2]/div/div[2]/div/div[2]/div[4]/div[2]/div/table/tbody/tr[3]/td[4]")).click();

        //单击确定
        webDriver.findElement(By.xpath("//html/body/div[2]/div/div[1]/div/div/div[2]/div/div[1]/table/tbody/tr/td[3]/a[2]/span")).click();
        Thread.sleep(1500);

        //根据id找到对应的那一行,单击数量
        for(int i = 3;i<100;i++) {
        Thread.sleep(1000);
            if (webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[2]/div/div[2]/div[4]/div[2]/div/table/tbody/tr[" + i + "]/td[3]")).getText().equals(serviceId)) {//单击数量输入框

                if (!nums.equals("1.0")) {
                    webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[2]/div/div[2]/div[4]/div[2]/div/table/tbody/tr[" + i + "]/td[9]")).click();
                    Thread.sleep(1000);
                    //将数量改为2.0000
                    webDriver.findElement(By.xpath("//html/body/div[3]/span/span/input")).sendKeys(nums);
                    Thread.sleep(1000);
                    pressEnter();
                }

                Thread.sleep(1000);

                String temp = webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[2]/div/div[2]/div[4]/div[2]/div/table/tbody/tr[" + i + "]/td[13]")).getText();
                System.out.println(temp);
                Thread.sleep(1000);
                if (temp.equals("0.0000") || temp.equals("0.000") ||temp.equals("0.00") ||temp.equals("0.0") ||temp.equals("0"))
                {
                webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[2]/div/div[2]/div[4]/div[2]/div/table/tbody/tr[" + i + "]/td[13]")).click();
                webDriver.findElement(By.xpath("//html/body/div[3]/span/span/input")).sendKeys("1.0");
                Thread.sleep(1000);
                pressEnter();
                webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[1]/a/span")).click();
                }

                Thread.sleep(1000);
                webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[1]/a/span")).click();
                break;
            }
        }

        Thread.sleep(500);
        webDriver.switchTo().defaultContent();

    }


    //购买物资(改数量模式)
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
        Thread.sleep(3000);

        for(int i = 3;i<100;i++) {
            if (webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[2]/div/div[2]/div[4]/div[2]/div/table/tbody/tr[" + i + "]/td[3]")).getText().equals(serviceId)) {//单击数量输入框


                if(!serviceId.equals("90010106000000000004") && !nums.equals("1.0")){
                webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[2]/div/div[2]/div[4]/div[2]/div/table/tbody/tr[" + i + "]/td[7]")).click();
                Thread.sleep(1000);
                //将数量改为2.0000
                webDriver.findElement(By.xpath("//html/body/div[3]/span/span/input")).sendKeys(nums);
                Thread.sleep(1000);
                pressEnter();
                }else if (serviceId.equals("90010106000000000004")){
                String[] price2 = nums.split(",");
                //当为外电的时候
                webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[2]/div/div[2]/div[4]/div[2]/div/table/tbody/tr[" + i + "]/td[8]")).click();
                Thread.sleep(1000);
                webDriver.findElement(By.xpath("//html/body/div[3]/span/span/input")).sendKeys(price2[0]);
                Thread.sleep(1000);
                webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[2]/div/div[2]/div[4]/div[2]/div/table/tbody/tr[" + i + "]/td[14]")).click();
                Thread.sleep(1000);
                webDriver.findElement(By.xpath("//html/body/div[3]/span/span/input")).sendKeys(price2[1]);
                Thread.sleep(1000);
                pressEnter();
                }

                Thread.sleep(1000);
                //单击保存
                webDriver.findElement(By.xpath("//html/body/div[2]/div/div[2]/div[1]/a/span")).click();

                break;
            }
        }

        Thread.sleep(1000);


        webDriver.switchTo().defaultContent();
    }

    public static void init(){
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();

        options.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
        WebDriver webDriver = new ChromeDriver(options);

        webDriver.get("http://4a.chinatowercom.cn:20000/uac/index");
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
        webDriver.findElement(By.xpath("//html/body/div[2]/div[7]/div/div/div[2]/div[2]/div[4]/table/tbody/tr/td[2]/div[1]/div[3]/table/tbody/tr/td[6]/span")).click();

        //切换iframe
        webDriver.switchTo().frame(webDriver.findElement(By.xpath("//iframe[con" +
                "tains(@src,'/pms/module/design/product/Baseinfo')]")));

        //单击选择物资
        webDriver.findElement(By.xpath("//html/body/div[2]/table/tbody/tr/td[7]/a[3]/span")).click();

        //切换iframe
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
//        webDriver.findElement(By.id("mini-1$2")).click();

//        webDriver.findElement(By.id("mini-1$1")).click();
        webDriver.switchTo().frame("tab1");
    }

    public static void findTittletext(WebDriver webDriver,String taskTitletext) throws InterruptedException {
        Set<String> windowhandle = webDriver.getWindowHandles();
        List<String> allWindows = new ArrayList<String>(windowhandle);
        //通过taskTittletext查询
        webDriver.findElement(By.id("taskTitle$text")).sendKeys(taskTitletext);
        webDriver.findElement(By.linkText("查询")).click();

        Thread.sleep(2000);
        webDriver.findElement(By.id("taskDataGrid1")).findElement(By.xpath(".//div/div[2]/div[4]/div[2]/div/table/tbody")).findElement(By.xpath(".//*[@id=\"mini-29$row2$1\"]/td[2]/div/a")).click();
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

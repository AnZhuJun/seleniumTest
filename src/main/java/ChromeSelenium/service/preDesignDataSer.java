package ChromeSelenium.service;

import ChromeSelenium.tools.ExcelData;
import ChromeSelenium.interf.preDesignDataInter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class preDesignDataSer implements preDesignDataInter{

    public List<String> getIdList(String path){
        ExcelData preDesignData = new ExcelData(path, "预设计表");
        ExcelData equipmentData = new ExcelData(path, "模块库");
        ExcelData priceData = new ExcelData(path, "价格库");

        List<String> idList = new ArrayList<String>();
        int excelRows = preDesignData.getRows();

        for (int i = 4;i<=excelRows-1;i++) {
            String Di = preDesignData.getExcelDateByIndex(i - 1, 4 - 1);
            String Ii = preDesignData.getExcelDateByIndex(i - 1, 9 - 1);

            if(Di.startsWith("V")) {
                idList.add(equipmentData.getCellByCaseName(Ii, 0, 2));
            }else {
                idList.add(Di);
            }
        }

        return idList;
    }

    public List<String> getNumsList(String path){
        ExcelData preDesignData = new ExcelData(path, "预设计表");
        ExcelData equipmentData = new ExcelData(path, "模块库");
        ExcelData priceData = new ExcelData(path, "价格库");

        int excelRows = preDesignData.getRows();
        List<String> numList = new ArrayList<String>();

        for(int i = 4 ; i<=excelRows-1;i++){
            String Li = preDesignData.getExcelDateByIndex(i - 1, 12 - 1);

            numList.add(Li);
        }

        return numList;
    }

    public List<String> getPriceList(String path){
        ExcelData preDesignData = new ExcelData(path, "预设计表");
        ExcelData equipmentData = new ExcelData(path, "模块库");
        ExcelData priceData = new ExcelData(path, "价格库");

        int excelRows = preDesignData.getRows();
        List<String> priceList = new ArrayList<String>();

        for (int i = 4;i<=excelRows-1;i++) {
            String Ai = preDesignData.getExcelDateByIndex(i - 1, 1 - 1);
            String Di = preDesignData.getExcelDateByIndex(i - 1, 4 - 1);
            String Ii = preDesignData.getExcelDateByIndex(i - 1, 9 - 1);
            String Ji = preDesignData.getExcelDateByIndex(i - 1, 10 - 1);
            String Li = preDesignData.getExcelDateByIndex(i - 1, 12 - 1);
            List<String> idList = new ArrayList<String>();

            if(Di.startsWith("V")) {
                idList.add(equipmentData.getCellByCaseName(Ii, 0, 2));
            }else {
                idList.add(Di);
            }

            for (String a : idList
            ) {
                char firstNum = a.charAt(0);
                if (firstNum == '9' && !a.equals("90010106000000000004")) {
                    String Price = priceData.getMultiCellByCaseName(Ai, Ii, Ji, 0, 2, 3, 6);
                    Double d = Double.valueOf(Price) * Double.valueOf(Li);
                    BigDecimal bigDecimal = new BigDecimal(d);
                    double dou = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    String price = String.valueOf(dou);
                    priceList.add(price);
                }
            }
        }

        return priceList;
    }


    public String getOutEle(String path){
        ExcelData preDesignData = new ExcelData(path, "预设计表");
        ExcelData equipmentData = new ExcelData(path, "模块库");
        ExcelData priceData = new ExcelData(path, "价格库");

        int excelRows = preDesignData.getRows();
        String price = "";

        for (int i = 4;i<=excelRows-1;i++) {
            String Ai = preDesignData.getExcelDateByIndex(i - 1, 1 - 1);
            String Di = preDesignData.getExcelDateByIndex(i - 1, 4 - 1);
            String Ii = preDesignData.getExcelDateByIndex(i - 1, 9 - 1);
            String Ji = preDesignData.getExcelDateByIndex(i - 1, 10 - 1);
            String Li = preDesignData.getExcelDateByIndex(i - 1, 12 - 1);
            String Mi = preDesignData.getExcelDateByIndex(i - 1, 13 - 1);
            String Ni = preDesignData.getExcelDateByIndex(i - 1, 14 - 1);
            List<String> idList = new ArrayList<String>();

            Map<String,Double> TYcompanySale = new HashMap<String, Double>();
            TYcompanySale.put("海纳通讯技术有限公司",0.85);
            TYcompanySale.put("山西省邮电建设工程有限公司",0.84);
            TYcompanySale.put("山西咸宁科技有限公司",0.86);
            TYcompanySale.put("日海通信服务有限公司",0.83);

            Map<String,Double> DTcompanySale = new HashMap<String, Double>();
            DTcompanySale.put("山西晋通邮电实业有限公司",0.84);
            DTcompanySale.put("山西兴达通电力建设有限责任公司",0.83);

            Map<String,Double> SZcompanySale = new HashMap<String, Double>();
            SZcompanySale.put("君行天下建筑工程有限公司",0.84);
            SZcompanySale.put("海纳通讯技术有限公司",0.85);


            Map<String,Map<String,Double>> cityCompany = new HashMap<String, Map<String, Double>>();
            cityCompany.put("太原",TYcompanySale);
            cityCompany.put("大同",DTcompanySale);
            cityCompany.put("朔州",SZcompanySale);


            if(Di.startsWith("V")) {
                idList.add(equipmentData.getCellByCaseName(Ii, 0, 2));
            }else {
                idList.add(Di);
            }

            for (String a : idList
            ) {
                if (a.equals("90010106000000000004")) {
                    double temp = Double.valueOf(Mi)  / Double.valueOf(cityCompany.get(Ai).get(Ji))  + Double.valueOf(Ni);
                    BigDecimal bigDecimal = new BigDecimal(temp);
                    double dou = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                    price  =String.valueOf(dou) + "," + String.valueOf(Ni);
                }
            }
        }

        return price;

    }

    public static List<String> getRecord(){
        String excelPath = "D:\\seleniumWork\\excel数据表格\\excel1.xlsx";
        String excelSheet = "sheet1";
        ExcelData excelData = new ExcelData(excelPath,excelSheet);
        int excelRows = excelData.getRows();
        List<String> record = new ArrayList<String>();

        for(int i = 2;i<=excelRows;i++){
            record.add(excelData.getExcelDateByIndex(i-1,2-1));
        }

        return record;
    }

    public static List<String> getName(){
        String excelPath = "D:\\seleniumWork\\excel数据表格\\excel1.xlsx";
        String excelSheet = "sheet1";
        ExcelData excelData = new ExcelData(excelPath,excelSheet);
        int excelRows = excelData.getRows();
        List<String> name = new ArrayList<String>();

        for(int i = 2;i<=excelRows;i++){
            name.add(excelData.getExcelDateByIndex(i-1,1-1));
        }

        return name;
    }


    public static void main(String[] args) {

        List<String> list1 = getName();
        List<String> list11 = getRecord();

        System.out.println(list1);
        System.out.println(list11);


        String path = "D:\\seleniumWork\\excel数据表格\\" + list1.get(0) +"\\" + list1.get(0) + "-预设计表.xlsx";
        preDesignDataSer preDesignDataSer = new preDesignDataSer();
        List<String> list = preDesignDataSer.getIdList(path);
        System.out.println(list+ " 长度: " + list.size());
        List<String> list2 = preDesignDataSer.getPriceList(path);
        System.out.println(list2+ " 长度: " + list2.size());
        List<String> list3 = preDesignDataSer.getNumsList(path);
        System.out.println(list3 + " 长度: " + list3.size());
        String price = preDesignDataSer.getOutEle(path);
        System.out.println(price );
    }
}

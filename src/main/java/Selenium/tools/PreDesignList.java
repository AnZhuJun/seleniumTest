package Selenium.tools;

import Selenium.tools.ExcelData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PreDesignList {
    public static void main(String[] args) {
        ExcelData preDesignData = new ExcelData("D:\\seleniumWork\\excel数据表格\\杏花岭区东中环马道坡-预设计表.xlsx", "预设计表");
        ExcelData equipmentData = new ExcelData("D:\\seleniumWork\\excel数据表格\\杏花岭区东中环马道坡-预设计表.xlsx", "模块库");
        ExcelData priceData = new ExcelData("D:\\seleniumWork\\excel数据表格\\杏花岭区东中环马道坡-预设计表.xlsx", "价格库");
        int excelRows = preDesignData.getRows();


        for (int i = 4;i<=excelRows-1;i++) {
            String Ai = preDesignData.getExcelDateByIndex(i - 1, 1 - 1);
            String Bi = preDesignData.getExcelDateByIndex(i - 1, 2 - 1);
            String Ci = preDesignData.getExcelDateByIndex(i - 1, 3 - 1);
            String Di = preDesignData.getExcelDateByIndex(i - 1, 4 - 1);
            String Ei = preDesignData.getExcelDateByIndex(i - 1, 5 - 1);
            String Fi = preDesignData.getExcelDateByIndex(i - 1, 6 - 1);
            String Gi = preDesignData.getExcelDateByIndex(i - 1, 7 - 1);
            String Hi = preDesignData.getExcelDateByIndex(i - 1, 8 - 1);
            String Ii = preDesignData.getExcelDateByIndex(i - 1, 9 - 1);
            String Ji = preDesignData.getExcelDateByIndex(i - 1, 10 - 1);
            String Ki = preDesignData.getExcelDateByIndex(i - 1, 11 - 1);
            String Li = preDesignData.getExcelDateByIndex(i - 1, 12 - 1);
            String Mi = preDesignData.getExcelDateByIndex(i - 1, 13 - 1);
            String Ni = preDesignData.getExcelDateByIndex(i - 1, 14 - 1);

            List<String> idList = new ArrayList<String>();
            List<String> priceList = new ArrayList<String>();

            if(Di.startsWith("V")) {
                idList.add(equipmentData.getCellByCaseName(Ii, 0, 2));
            }else {
                idList.add(Di);
            }

//            System.out.println(idList.toString());

            //根据行列计算相应的价格
            for (String a:idList
                 ) {
                char firstNum = a.charAt(0);
                if (firstNum == '9' && !a.equals("90010106000000000004")){
                    String Price = priceData.getMultiCellByCaseName(Ai,Ii,Ji,0,2,3,6);
                    Double d  = Double.valueOf(Price) * Double.valueOf(Li);
                    BigDecimal bigDecimal = new BigDecimal(d);
                    double dou = bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
                    System.out.println(dou);
                }
//                else if(firstNum == '0'){
//                    String Price = Mi;
//                    System.out.println(Price);
//                }
            }

//            String temp = priceData.getExcelDateByIndex(i-1,5);
//            System.out.println(temp);

//            char DiArr = Id.charAt(0);
//            System.out.println(DiArr);

        }
    }
}

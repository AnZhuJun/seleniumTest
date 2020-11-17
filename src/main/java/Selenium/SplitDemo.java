package Selenium;

public class SplitDemo {
    public static void main(String[] args) {
        ExcelData excelData = new ExcelData("D:\\seleniumWork\\excel数据表格\\excel1.xlsx", "sheet1");
        int excelRows = excelData.getRows();

        for (int i = 2;i<=excelRows;i++) {
            String Ai = excelData.getExcelDateByIndex(i - 1, 1 - 1);
            String Bi = excelData.getExcelDateByIndex(i - 1, 2 - 1);
            String Ci = excelData.getExcelDateByIndex(i - 1, 3 - 1);
            String Di = excelData.getExcelDateByIndex(i - 1, 4 - 1);
            System.out.println("项目名称:" + Ai + "   ,是否已经录入(1为是,0为否):" + Bi + "   ,服务规程格式:" + Ci + "   ,数量:" + Di);

            String[] CiArr = Ci.split(",");
            for (int index = 0 ; index < CiArr.length;index++)
                System.out.println(CiArr[index]);

            String[] DiArr = Di.split(",");
            for (int index = 0 ; index < DiArr.length;index++)
                System.out.println(DiArr[index]);
        }

    }
}

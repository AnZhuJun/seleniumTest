package ChromeSelenium.tools;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExcelData {
    private XSSFSheet sheet;

    /**
     * 构造函数，初始化excel数据
     * @param filePath  excel路径
     * @param sheetName sheet表名
     */
    public ExcelData(String filePath, String sheetName){
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(filePath);
            XSSFWorkbook sheets = new XSSFWorkbook(fileInputStream);
            //获取sheet
            sheet = sheets.getSheet(sheetName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据行和列的索引获取单元格的数据
     * @param row
     * @param column
     * @return
     */
    public String getExcelDateByIndex(int row,int column){
        XSSFRow row1 = sheet.getRow(row);
        String cell = row1.getCell(column).toString();
        return cell;
    }

    public  void writeExcelDateByIndex(String filePath,String sheetName,int row,int column,String value) throws IOException {
        FileInputStream fileInputStream = null;
        fileInputStream = new FileInputStream(filePath);
        XSSFWorkbook sheets = new XSSFWorkbook(fileInputStream);
            //获取sheet
        sheet = sheets.getSheet(sheetName);

        XSSFRow row1 = sheet.getRow(row);
        row1.createCell(column).setCellValue(value);

        OutputStream op = new FileOutputStream(filePath);
        sheets.write(op);
        op.close();
    }


    public int getRows(){
        return sheet.getPhysicalNumberOfRows();
    }

    /**
     * 根据某一列值为“******”的这一行，来获取该行第x列的值
     * @param caseName
     * @param currentColumn 当前单元格列的索引
     * @param targetColumn 目标单元格列的索引
     * @return
     */
    public String getCellByCaseName(String caseName,int currentColumn,int targetColumn){
        String operateSteps="";
        //获取行数
        int rows = sheet.getPhysicalNumberOfRows();
        for(int i=0;i<rows;i++){
            XSSFRow row = sheet.getRow(i);
            String cell = row.getCell(currentColumn).toString();
            if(cell.equals(caseName)){
                operateSteps = row.getCell(targetColumn).toString();
                break;
            }
        }
        return operateSteps;
    }

    public String getMultiCellByCaseName(String caseName1,String caseName2,String caseName3,int currentColumn1,int currentColumn2,int currentColumn3,int targetColumn){
        String operateSteps="";
        //获取行数
        int rows = sheet.getPhysicalNumberOfRows();
        for(int i=4;i<rows;i++){
            XSSFRow row = sheet.getRow(i);
            XSSFRow row2 = sheet.getRow(i);
            XSSFRow row3 = sheet.getRow(i);
            String cell1 = row.getCell(currentColumn1).toString();
//            System.out.println(cell1);
            String cell2 = row2.getCell(currentColumn2).toString();
//            System.out.println(cell2);
            String cell3 = row3.getCell(currentColumn3).toString();
//            System.out.println(cell3);
            if(cell1.equals(caseName1) && cell2.equals(caseName2) && cell3.equals(caseName3)){
                operateSteps = row.getCell(targetColumn).toString();
                break;
            }
        }
        return operateSteps;
    }

    //打印excel数据
    public void readExcelData(){
        //获取行数
        int rows = sheet.getPhysicalNumberOfRows();
        for(int i=0;i<rows;i++){
            //获取列数
            XSSFRow row = sheet.getRow(i);
            int columns = row.getPhysicalNumberOfCells();
            for(int j=0;j<columns;j++){
                String cell = row.getCell(j).toString();
                System.out.println(cell);
            }
        }
    }

    //测试方法
    public static void main(String[] args) throws IOException {
        ExcelData sheet1 = new ExcelData("D:\\seleniumWork\\excel数据表格\\excel1.xlsx", "sheet1");
        //获取第二行第4列
        String cell2 = sheet1.getExcelDateByIndex(1, 0);
        //根据第3列值为“customer23”的这一行，来获取该行第2列的值
//        String cell3 = sheet1.getCellByCaseName("900101060810", 2,1);
        System.out.println(cell2);

        String excelPath = "D:\\seleniumWork\\excel数据表格\\excel1.xlsx";
        String excelSheet = "sheet1";

        ExcelData excelData = new ExcelData(excelPath, excelSheet);
        int excelRows = excelData.getRows();
        List<String> name = new ArrayList<String>();

        System.out.println(excelRows);

        for(int i = 2;i<=excelRows;i++){
            name.add(excelData.getExcelDateByIndex(i-1,1-1));
        }

        System.out.println(name);
//        System.out.println(cell3);
//        sheet1.readExcelData();
//        sheet1.writeExcelDateByIndex("D:\\seleniumWork\\excel数据表格\\excel1.xlsx", "sheet1",1,1,"1");
    }
}
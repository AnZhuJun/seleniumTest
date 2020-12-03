package ChromeSelenium.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 该类可以输出指定路径下所有的文件名（文件名和文件夹名）
 * 指定一个路径即可
 *
 */
public class TakeFilePathAndName {

    public static void main(String[] args) {
        //这是需要获取的文件夹路径
        String path = "南内环西街新晋祠路口桥下换电站主动规划能源类项目";
        System.out.println( getFile(path,0));
    }

    /*
     * 函数名：getFile
     * 作用：使用递归，输出指定文件夹内的所有文件
     * 参数：path：文件夹路径   deep：表示文件的层次深度，控制前置空格的个数
     * 前置空格缩进，显示文件层次结构
     */
    public static List<String> getFile(String path,int deep){
        String truePath = "D:\\seleniumWork\\excel数据表格\\" + path;
        // 获得指定文件对象
        File file = new File(truePath);
        // 获得该文件夹内的所有文件
        File[] array = file.listFiles();
        List<String> names = new ArrayList<String>();
        Map<String,String> nameMap = new HashMap<String, String>();
        nameMap.put("现场布置图","现场布置图");
        nameMap.put("勘察报告","查勘表");
        nameMap.put("外电资料","外电专业图纸");
        nameMap.put("设计变更单","设计变更单");
        nameMap.put("说明文本","说明文本");
        nameMap.put("会审纪要","设计方案会审纪要");
        nameMap.put("地勘报告","地勘报告");
        nameMap.put("无线配套图纸","动力配套专业图纸");
        nameMap.put("铁塔图纸","铁塔专业图纸");
        nameMap.put("塔基图纸","塔基专业图纸");
        nameMap.put("机房图纸","机房专业图纸");
        nameMap.put("防雷接地资料","其他专业图纸");
        nameMap.put("预设计表","预设计表");
        nameMap.put("现场核查照片","现场核查照片");
        nameMap.put("非模块化预算","非模块化预算");
        nameMap.put("交付说明书","交付说明书");

        for(int i=0;i<array.length;i++)
        {
            if(array[i].isFile())//如果是文件
            {
                for (int j = 0; j < deep; j++)//输出前置空格
                    System.out.print(" ");
                String temp = array[i].getName().split("\\.")[0];
                String temp2 = temp.split("-")[1];
                // 只输出文件名字

                names.add(nameMap.get(temp2));

                // 输出当前文件的完整路径
                // System.out.println("#####" + array[i]);
                // 同样输出当前文件的完整路径   大家可以去掉注释 测试一下
                // System.out.println(array[i].getPath());
            }
            else if(array[i].isDirectory())//如果是文件夹
            {
                for (int j = 0; j < deep; j++)//输出前置空格
                    System.out.print(" ");

                String temp = array[i].getName().split("\\.")[0];
                String temp2 = temp.split("-")[1];
                names.add(nameMap.get(temp2));
                //System.out.println(array[i].getPath());
                //文件夹需要调用递归 ，深度+1
                getFile(array[i].getPath(),deep+1);
            }
        }

        return names;
    }
}
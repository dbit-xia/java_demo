package dbit.excel;

import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: wang gang
 * @Date: 2019/5/23 14:27
 * @Description: Pointing to the breeze, the procedure is self-contained
 */
//@Component
public class StreamingReaderApp {

    public static String getCellValue(Cell cell){
        String cellValue = "";
        if (cell == null) {
            return "";
        }
        switch (cell.getCellTypeEnum()){
            case NUMERIC: //数字
                if (HSSFDateUtil.isCellDateFormatted(cell)){//日期
                    SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
                    Date d = cell.getDateCellValue();
                    cellValue = sd.format(d);
                }else{
                    //转换格式
                    DataFormatter dataFormatter = new DataFormatter();
                    dataFormatter.addFormat("###########", null);
                    cellValue = dataFormatter.formatCellValue(cell);
                }
                break;
            case STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case BLANK: //空值
                cellValue = "";
                break;
            case ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

    /**
     * 解决读取大文件excel出现内存溢出的情况
     * @param args
     * @throws InterruptedException 
     * @throws FileNotFoundException 
     */
    public static void main(String[] args) throws InterruptedException, FileNotFoundException {

        InputStream is = new FileInputStream(new File("/home/dbit/xxx.xlsx"));
        Workbook wk = StreamingReader.builder()
                .rowCacheSize(100)    // number of rows to keep in memory (defaults to 10)
                .bufferSize(4096)     // buffer size to use when reading InputStream to file (defaults to 1024)
                .open(is);            // InputStream or File for XLSX file (required)
        
        for (Sheet sheet : wk){
            System.out.println(sheet.getSheetName());
            int count = 0;//获取表头列数
            List<String> header = new ArrayList<>();//获取表头
            for (Row r : sheet) {
                if (r.getRowNum()==0){
                    count = r.getLastCellNum();
                    for (Cell c : r) {
                        header.add(getCellValue(c));
                    }
                }else{
                    break;
                }
            }
            List<Map<String,String>> dataMap = new ArrayList<>();
            for (Row r : sheet) {
                if (r.getRowNum()>0){
                    Map<String,String> dataMap1 = new HashMap<>();
                    for (int i = 0; i < header.size(); i++) {
                        dataMap1.put(header.get(i),getCellValue(r.getCell(i)));
                    }
                    dataMap.add(dataMap1);
                }
                if (r.getRowNum()==300){//测试时先拿出了299条
                    break;
                }
            }
            System.out.println(dataMap);
            System.out.println(dataMap.size());
        }
        Thread.sleep(30000);
    }
}

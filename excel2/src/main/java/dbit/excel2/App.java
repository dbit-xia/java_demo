package dbit.excel2;

import java.util.List;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import cn.hutool.poi.excel.sax.handler.RowHandler;

/**
 * Hello world!
 *
 */
public class App 
{
    @SuppressWarnings("unchecked")
	public static void main( String[] args )
    {
    	Console.log("[{}] [{}] {}");
//    	exportExcel();
    	readExcel();
    }
    
    public static void readExcel () {
    	RowHandler aaa=new RowHandler() {
            public void handle(int sheetIndex, int rowIndex, List<Object> rowlist) {
//                Console.log("[{}] [{}] {}", sheetIndex, rowIndex, rowlist);
            }
        };
        
//    	ExcelUtil.read07BySax("/home/dbit/xxx.xlsx",-1, aaa);
    	Excel07SaxReader reader = new Excel07SaxReader(aaa);
    	reader.read("/home/dbit/xxx.xlsx", -1);
    }
    
    @SuppressWarnings("unchecked")
	public static void exportExcel() {
//        System.out.println( "Hello World!" );
    	List<?> row1 = CollUtil.newArrayList("aa", "bb", "cc", "dd", DateUtil.date(), 3.22676575765);
    	List<?> row2 = CollUtil.newArrayList("aa1", "bb1", "cc1", "dd1", DateUtil.date(), 250.7676);
    	List<?> row3 = CollUtil.newArrayList("aa2", "bb2", "cc2", "dd2", DateUtil.date(), 0.111);
    	List<?> row4 = CollUtil.newArrayList("aa3", "bb3", "cc3", "dd3", DateUtil.date(), 35);
    	List<?> row5 = CollUtil.newArrayList("aa4", "bb4", "cc4", "dd4", DateUtil.date(), 28.00);

    	List<List<?>> rows = CollUtil.newArrayList(row1, row2, row3, row4, row5);

    	//执行前,需要先删除掉
    	BigExcelWriter writer= ExcelUtil.getBigWriter("/home/dbit/xxx.xlsx");
    	//writer.passCurrentRow();
    	// 一次性写出内容，使用默认样式
//    	writer.write(rows);
//    	writer.passRows(writer.getRowCount());
    	writer.setSheet(0);
    	writer.renameSheet("001");
    	for (int i=0;i<100000;i++) {
    		writer.writeRow(row1);
    	}
    	writer.setSheet(1);
    	writer.renameSheet("002");
    	for (int i=0;i<100000;i++) {
    		writer.writeRow(row2);
    	}
    	// 关闭writer，释放内存
    	writer.close();

    }
}

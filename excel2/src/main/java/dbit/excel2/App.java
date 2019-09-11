package dbit.excel2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.sax.Excel07SaxReader;
import cn.hutool.poi.excel.sax.handler.RowHandler;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws InterruptedException, FileNotFoundException
    {
    	Console.log("[{}] [{}] {}");
    	exportExcel();
//    	readExcel();
//    	int i=true?a():b();
    }
    
    public static int a() {
    	Console.log('1');
    	return 1;
    }
    public static int b() {
    	Console.log('2');
    	return 2;
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
	public static void exportExcel() throws InterruptedException, FileNotFoundException {
    	
//        System.out.println( "Hello World!" );
    	List<?> row1 = CollUtil.newArrayList("aa", "bb", "cc", "dd", DateUtil.date(), 3.22676575765,"aa", "bb", "cc", "dd", DateUtil.date(), 3.22676575765,"aa", "bb", "cc", "dd", DateUtil.date(), 3.22676575765,"aa", "bb", "cc", "dd", DateUtil.date(), 3.22676575765,"aa", "bb", "cc", "dd", DateUtil.date(), 3.22676575765,"aa", "bb", "cc", "dd", DateUtil.date(), 3.22676575765);
    	List<?> row2 = CollUtil.newArrayList("aa1", "bb1", "cc1", "dd1", DateUtil.date(), 250.7676);
//    	List<?> row3 = CollUtil.newArrayList("aa2", "bb2", "cc2", "dd2", DateUtil.date(), 0.111);
//    	List<?> row4 = CollUtil.newArrayList("aa3", "bb3", "cc3", "dd3", DateUtil.date(), 35);
//    	List<?> row5 = CollUtil.newArrayList("aa4", "bb4", "cc4", "dd4", DateUtil.date(), 28.00);

//    	List<List<?>> rows = CollUtil.newArrayList(row1, row2, row3, row4, row5);
    	
    	File aaa=new File("/home/dbit/xxx.xlsx");
    	if (aaa.exists()) aaa.delete();
    	
    	
//    	OutputStream out = FileUtil.getOutputStream("/home/dbit/xxx.xlsx");
    	
    	//执行前,需要先删除掉
    	ExcelWriter writer= ExcelUtil.getBigWriter("/home/dbit/xxx.xlsx");
//    	writer.flush(out);
//    	writer.setDestFile(new File("/home/dbit/xxx.xlsx"));
    	
    	//writer.passCurrentRow();
    	// 一次性写出内容，使用默认样式
//    	writer.write(rows);
//    	writer.passRows(writer.getRowCount());
    	
    	System.gc();
    	writer.setSheet(0);
    	writer.renameSheet("001");
    	
    	System.out.println("add sheet");
//    	Thread.interrupted();
    	
//    	Thread.sleep(1000);
    	for (int i=0;i<100000;i++) {
    		writer.writeRow(row1);
    		if ( i % 2000 == 0 ) {
    			System.out.println(i);
//    			Thread.sleep(50);
//    			System.runFinalization();
    			
//    			long time1=System.currentTimeMillis();	
////    			System.gc();
//    			Runtime.getRuntime().gc();
//    			long time2=System.currentTimeMillis();
//    			System.out.println("当前程序耗时："+(time2-time1)+"ms");
    		}
    	}
//    	Thread.sleep(1000);
    	System.out.println("add sheet");
    	writer.setSheet(1);
    	writer.renameSheet("002");
    	for (int i=0;i<4000;i++) {
    		writer.writeRow(row2);
//    		writer.flush();
    		if ( i % 2000 == 0 ) {
    			System.out.println(i);
    		}
    	}
    	
    	System.out.println("close");
    	// 关闭writer，释放内存
    	writer.close();
    	System.out.println("sleep");
    	Thread.sleep(100000);
    	
    }
}

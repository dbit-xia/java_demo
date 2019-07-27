package dbit.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import java.io.*;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;

/**
 * Hello world!
 *
 */
public class App 
{
	
	
	
    public static void main( String[] args ) throws FileNotFoundException, InterruptedException
    {
        //System.out.println( "Hello World!" );
//    	writeExcel();
    	readExcel(); //内存持续占用太大
    	Thread.sleep(30000);    //延时30秒
    }
    
    
    
    public static InputStream getInputStream(String fileName) {
        try {
            return new FileInputStream(new File(fileName));
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
 }  
    
    @Test
    public static void readExcel() {
    	
    	/* 解析监听器，
         * 每解析一行会回调invoke()方法。
         * 整个excel解析结束会执行doAfterAllAnalysed()方法
         *
         * 下面只是我写的一个样例而已，可以根据自己的逻辑修改该类。
         * @author jipengfei
         * @date 2017/03/14
         */
        class ExcelListener extends AnalysisEventListener {

            //自定义用于暂时存储data。
            //可以通过实例获取该值
            private List<Object> datas = new ArrayList<Object>();
            public void invoke(Object object, AnalysisContext context) {
                System.out.println("当前行："+context.getCurrentRowNum());
                System.out.println(object);
//                datas.add(object);//数据存储到list，供批量处理，或后续自己业务逻辑处理。
                doSomething(object);//根据自己业务做处理
            }
            private void doSomething(Object object) {
                //1、入库调用接口
            }
            public void doAfterAllAnalysed(AnalysisContext context) {
               // datas.clear();//解析结束销毁不用的资源
            }
            public List<Object> getDatas() {
                return datas;
            }
            public void setDatas(List<Object> datas) {
                this.datas = datas;
            }
        }
        
        InputStream inputStream = getInputStream("/home/dbit/xxx.xlsx");
        try {
            // 解析每行结果在listener中处理
            ExcelListener listener = new ExcelListener();

            ExcelReader excelReader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null, listener);
            excelReader.read(new Sheet(-1));
        } catch (Exception e) {

        } finally {
        	System.out.print("end");
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void writeExcel() throws FileNotFoundException {
    	class ExcelPropertyIndexModel2 extends BaseRowModel {

            @ExcelProperty(value = "姓名" ,index = 0)
            private String name;
            
            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

        }
    	
    	List<ExcelPropertyIndexModel2> aaa = new ArrayList<ExcelPropertyIndexModel2>();
    	ExcelPropertyIndexModel2 row = new ExcelPropertyIndexModel2();
    	row.setName("123");
    	
    	aaa.add(row);
    	OutputStream out = new FileOutputStream("/home/dbit/Code/demo.xlsx");
        try {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX,false);
            //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
            Sheet sheet1 = new Sheet(1, 0);
            sheet1.setSheetName("第一个sheet");
            writer.write(aaa, sheet1);
            writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

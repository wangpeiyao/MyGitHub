package com.qhyj.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.nstc.dtk.helper.DtkExportHelper;
import com.nstc.dtk.model.ByteResult;

public class ExcelUtils {
	

	public static void exportData(List dataList,String[] fields,String[] keys,String path,String fileName) throws Exception {
		
//		File newFile = FileUtil.getFile(path+File.separator+fileName);
//		if(!newFile.exists()) {
//			newFile.mkdirs();
//		}
//		FileUtil.copyFile(new ExcelUtils().getClass().getResource("").getPath()+"Excel.xls", path+File.separator+fileName);
		
//		createExcel(path+File.separator+fileName);
		ByteResult result = DtkExportHelper.export(dataList, keys, fields, fileName);
		
		
//		File newFile = new File(path, fileName);
		OutputStream outS = new FileOutputStream(new File(path,fileName));
		outS.write(result.getDataBytes());
	}
	public static void main(String[] args) throws Exception {
		List<Map<String, Object>> dataList = new ArrayList();
		for (int i=0;i<100000;i++) {
			Map<String, Object> map = new HashMap();
			map.put("num", i);
			map.put("name", "����"+i);
			map.put("count", 100+i);
			dataList.add(map);
		}
		String[] fields = new String[] {"num","name","count"};
		String[] keys = new String[] {"���","����","����"};
		String fileName = "MyTestExcel";
		long sl = System.currentTimeMillis();
		System.out.println(sl);
		ByteResult result = DtkExportHelper.export(dataList, keys, fields, fileName);
		long el = System.currentTimeMillis();
		System.out.println(el);
		System.out.println("��"+(sl-el)/1000);
		String fileName1="\"C:\\\\Users\\\\Administrator.USER-20150212XT\\\\Desktop\\\\�й�201801.xls\"";
//		OutputStream outS = new FileOutputStream(FileUtil.getFile(new String(fileName1.getBytes("GBK"),"UTF-8")));
		OutputStream outS = new FileOutputStream(FileUtil.getFile("C:\\Users\\Administrator.USER-20150212XT\\Desktop\\��Ʒ�����Ϣ201801.xls"));
		long el1 = System.currentTimeMillis();
		System.out.println(el);
		System.out.println("��"+(el-el1)/1000);
		outS.write(result.getDataBytes());
////		FileUtil.copyFile(new ExcelUtils().getClass().getResource("").getPath()+"Excel.xls", " C:\\Users\\Administrator.USER-20150212XT\\Desktop"+File.separator+"��Ʒ�����Ϣ201801.xls");
//		createExcel("C:\\Users\\Administrator.USER-20150212XT\\Desktop","��Ʒ�����Ϣ201801.xls");
////		download("D:\\app\\��Ʒ�����Ϣ201801.xls", "C:\\Users\\Administrator.USER-20150212XT\\Desktop");
	}

}

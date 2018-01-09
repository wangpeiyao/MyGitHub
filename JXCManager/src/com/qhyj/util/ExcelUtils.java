package com.qhyj.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nstc.dtk.helper.DtkExportHelper;
import com.nstc.dtk.model.ByteResult;

public class ExcelUtils {
	

	public static void exportData(List dataList,String[] fields,String[] keys,String path,String fileName) throws IOException {
		ByteResult result = DtkExportHelper.export(dataList, keys, fields, fileName);
		File newFile = FileUtil.getFile(path+File.separator+fileName);
		if(!newFile.exists()) {
			newFile.mkdirs();
		}
		OutputStream outS = new FileOutputStream(newFile);
		outS.write(result.getDataBytes());
	}
	public static void main(String[] args) throws IOException {
		List<Map<String, Object>> dataList = new ArrayList();
		for (int i=0;i<100000;i++) {
			Map<String, Object> map = new HashMap();
			map.put("num", i);
			map.put("name", "张三"+i);
			map.put("count", 100+i);
			dataList.add(map);
		}
		String[] fields = new String[] {"num","name","count"};
		String[] keys = new String[] {"序号","名称","数量"};
		String fileName = "MyTestExcel";
		long sl = System.currentTimeMillis();
		System.out.println(sl);
		ByteResult result = DtkExportHelper.export(dataList, keys, fields, fileName);
		long el = System.currentTimeMillis();
		System.out.println(el);
		System.out.println("共"+(sl-el)/1000);
		OutputStream outS = new FileOutputStream(FileUtil.getFile("d:\\text.xls"));
		long el1 = System.currentTimeMillis();
		System.out.println(el);
		System.out.println("共"+(el-el1)/1000);
		outS.write(result.getDataBytes());
	}
}

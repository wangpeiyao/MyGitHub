package com.qhyj.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class FileUtil {
	public static File getFile(String path){
		File file = new File(path);
		return file;
	}
	
	public static  boolean setProfileString(String file, String key, String value)
			throws IOException {
		File f = new File(file);
		boolean res = false;
		if (f.exists()) {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String outstr = "";
			String line = "";

			while ((line = br.readLine()) != null) {
				if (line == "") // 如果为空
				{
					outstr += "\n";
					continue;
				}
				if (line.startsWith("#")) // 如果为注释
				{
					outstr += line + "\n";
					continue;
				}
				if (line.trim().startsWith(key)) {
					String[] keyandvalue = line.split("=");

					outstr += keyandvalue[0].toString() + "="
							+ value.toString() + "\n";

					res = true;
					continue;
				}
				outstr += line + "\n";

			}
			if (res) {

				FileWriter fw = new FileWriter(file, false);
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(outstr);

				bw.close();
				fw.close();
				return true;
			}
		}
		return false;

	}
}

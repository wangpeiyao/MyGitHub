package com.wpy.base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

public class HttpUtil {  
    /** 
     * ��ָ��URL����GET���������� 
     *  
     * @param url 
     *            ���������URL 
     * @param param 
     *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ�� 
     * @return URL ������Զ����Դ����Ӧ��� 
     */  
    public static String sendGet(String url, String param) {  
        String result = "";  
        BufferedReader in = null;  
        try {  
            String urlNameString = url + "?" + param;  
            URL realUrl = new URL(urlNameString);  
            // �򿪺�URL֮�������  
            URLConnection connection = realUrl.openConnection();  
            // ����ͨ�õ���������  
            connection.setRequestProperty("accept", "*/*");  
            connection.setRequestProperty("connection", "Keep-Alive");  
            connection.setRequestProperty("user-agent",  
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
            // ����ʵ�ʵ�����  
            connection.connect();  
            // ��ȡ������Ӧͷ�ֶ�  
            Map<String, List<String>> map = connection.getHeaderFields();  
            // �������е���Ӧͷ�ֶ�  
            for (String key : map.keySet()) {  
                System.out.println(key + "--->" + map.get(key));  
            }  
            // ���� BufferedReader����������ȡURL����Ӧ  
            in = new BufferedReader(new InputStreamReader(  
                    connection.getInputStream()));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            System.out.println("����GET��������쳣��" + e);  
            e.printStackTrace();  
        }  
        // ʹ��finally�����ر�������  
        finally {  
            try {  
                if (in != null) {  
                    in.close();  
                }  
            } catch (Exception e2) {  
                e2.printStackTrace();  
            }  
        }  
        return result;  
    }  
  
    /**  
     * ��ָ�� URL ����POST����������  
     *   
     * @param url  
     *            ��������� URL  
     * @param param  
     *            ����������������Ӧ���� name1=value1&name2=value2 ����ʽ��  
     * @return ������Զ����Դ����Ӧ���  
     */  
    public static String sendPost(String url, String param) {  
        PrintWriter out = null;  
        BufferedReader in = null;  
        String result = "";  
        try {  
            URL realUrl = new URL(url);  
            // �򿪺�URL֮�������  
            URLConnection conn = realUrl.openConnection();  
            // ����ͨ�õ���������  
            conn.setRequestProperty("accept", "*/*");  
            conn.setRequestProperty("connection", "Keep-Alive");  
            conn.setRequestProperty("user-agent",  
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");  
            // ����POST�������������������  
            conn.setDoOutput(true);  
            conn.setDoInput(true);  
            // ��ȡURLConnection�����Ӧ�������  
            out = new PrintWriter(conn.getOutputStream());  
            // �����������  
            out.print(param);  
            // flush������Ļ���  
            out.flush();  
            // ����BufferedReader����������ȡURL����Ӧ  
            in = new BufferedReader(  
                    new InputStreamReader(conn.getInputStream()));  
            String line;  
            while ((line = in.readLine()) != null) {  
                result += line;  
            }  
        } catch (Exception e) {  
            System.out.println("���� POST ��������쳣��"+e);  
            e.printStackTrace();  
        }  
        //ʹ��finally�����ر��������������  
        finally{  
            try{  
                if(out!=null){  
                    out.close();  
                }  
                if(in!=null){  
                    in.close();  
                }  
            }  
            catch(IOException ex){  
                ex.printStackTrace();  
            }  
        }  
        return result;  
    }      
      
    public static void main(String[] args) {  
        JSONObject js = new JSONObject();  
        js.put("expire_seconds", 604800);  
        js.put("action_name", "QR_SCENE");  
        JSONObject j1 = new JSONObject();  
        j1.put("scene_id", 111111);  
        JSONObject j2 = new JSONObject();  
        j2.put("scene", j1);  
        js.put("action_info", j2);  
        String s = HttpUtil.sendPost("https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=uG17gXKUrhrAgw7wAfiUQolnlGylW_5sc8BqKNtNTmmSEjbsNpgEz-6vq6G_U-EEUyU-1_QJ1lAWcLyx9PvvGWRX9F3iq4CiFa0_nMcxseeJ1vqpjxjCK-c78kuwhrTxFEUaAJAIHX", js.toString());  
        System.out.println(s);  
    }  
}  
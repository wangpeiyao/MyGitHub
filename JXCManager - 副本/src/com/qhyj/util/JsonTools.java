package com.qhyj.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * ����json�Ĺ�����. <br>
 * ����Ϊ����json�Ĺ�����
 * 
 * @author slj
 */
public class JsonTools {

	/**
	 * 
	 * jsonת��list. <br>
	 * ��ϸ˵��
	 * 
	 * @param jsonStr
	 *            json�ַ���
	 * @return
	 * @return List<Map<String,Object>> list
	 * @throws @author
	 *             slj
	 * @date 2013��12��24�� ����1:08:03
	 */
	public static List<Map<String, Object>> parseJSON2List(String jsonStr) {
		JSONArray jsonArr = JSONArray.fromObject(jsonStr);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Iterator<JSONObject> it = jsonArr.iterator();
		while (it.hasNext()) {
			JSONObject json2 = it.next();
			list.add(parseJSON2Map(json2.toString()));
		}
		return list;
	}

	/**
	 * 
	 * jsonת��map. <br>
	 * ��ϸ˵��
	 * 
	 * @param jsonStr
	 *            json�ַ���
	 * @return
	 * @return Map<String,Object> ����
	 * @throws @author
	 *             slj
	 */
	public static Map<String, Object> parseJSON2Map(String jsonStr) {
		Map map = new HashMap();
		// ��������
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			// ����ڲ㻹������Ļ�����������
			if (v instanceof JSONArray) {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				Iterator<JSONObject> it = ((JSONArray) v).iterator();
				while (it.hasNext()) {
					JSONObject json2 = it.next();
					list.add(parseJSON2Map(json2.toString()));
				}
				map.put(k.toString(), list);
			} else {
				map.put(k.toString(), v);
			}
		}
		return map;
	}

	/**
	 * 
	 * ͨ��HTTP��ȡJSON����. <br>
	 * ͨ��HTTP��ȡJSON���ݷ���list
	 * 
	 * @param url
	 *            ����
	 * @return
	 * @return List<Map<String,Object>> list
	 * @throws @author
	 *             slj
	 */
	public static List<Map<String, Object>> getListByUrl(String url) {
		try {
			// ͨ��HTTP��ȡJSON����
			InputStream in = new URL(url).openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return parseJSON2List(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * ͨ��HTTP��ȡJSON����. <br>
	 * ͨ��HTTP��ȡJSON���ݷ���map
	 * 
	 * @param url
	 *            ����
	 * @return
	 * @return Map<String,Object> ����
	 * @throws @author
	 *             slj
	 */
	public static Map<String, Object> getMapByUrl(String url) {
		try {
			// ͨ��HTTP��ȡJSON����
			InputStream in = new URL(url).openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return parseJSON2Map(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * mapת��json. <br>
	 * ��ϸ˵��
	 * 
	 * @param map
	 *            ����
	 * @return
	 * @return String json�ַ���
	 * @throws @author
	 *             slj
	 */
	public static String mapToJson(Map<String, String> map) {
		Set<String> keys = map.keySet();
		String key = "";
		String value = "";
		StringBuffer jsonBuffer = new StringBuffer();
		jsonBuffer.append("{");
		for (Iterator<String> it = keys.iterator(); it.hasNext();) {
			key = (String) it.next();
			value = map.get(key);
			jsonBuffer.append(key + ":" + "\"" + value + "\"");
			if (it.hasNext()) {
				jsonBuffer.append(",");
			}
		}
		jsonBuffer.append("}");
		return jsonBuffer.toString();
	}

	// test
	public static void main(String[] args) {
		Map map = new HashMap();
		map.put("123","123");
		map.put("35", "2342");
		Map reaMap = JsonTools.parseJSON2Map(JsonTools.mapToJson(map));
		System.out.println(reaMap.get("35"));
	}
}
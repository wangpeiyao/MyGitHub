package com.qhyj.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

public class MapUtils {
	
	public static boolean existObj(Map map,String key) {
		if(null==map) {
			return false;
		}
		if(null!=map.get(key)) {
			return true;
		}
		return false;
	}
	
	public static String getStringValByKey(Map map, String key) {
		String value = null;
		if (key == null) {
			return value;
		}
		if (map != null && map.get(key) != null) {
			if (map.get(key) instanceof String
					&& !"".equals(((String) map.get(key)).trim())) {
				value = ((String) map.get(key)).trim();
			}
		}
		return value;
	}
	public static Integer getIntegerValByKey(Map map, String key) {
		Integer value = null;
		if (key == null) {
			return value;
		}
		if (map != null && map.get(key) != null) {
			Object obj = map.get(key);
			String strValue = null;
			if (obj instanceof Integer) {
				value = (Integer) obj;
			}else if(obj instanceof BigInteger){
				value = new Integer(((BigInteger)obj).intValue());
			}else if(obj instanceof BigDecimal){
				value = new Integer(((BigDecimal)obj).intValue());
			} else if ((strValue = getStringValByKey(map, key)) != null) {
				value = Integer.valueOf(strValue);
			}
		}
		return value;
	}
	public static Double getDoubleValByKey(Map map, String key) {
		Double value = null;
		if (key == null) {
			return value;
		}
		if (map != null && map.get(key) != null) {
			Object obj = map.get(key);
			String strValue = null;
			if (obj instanceof Double) {
				value = (Double) obj;
			}else if(obj instanceof BigInteger){
				value = new Double(((BigInteger)obj).doubleValue());
			}else if(obj instanceof BigDecimal){
				value = new Double(((BigDecimal)obj).doubleValue());
			} else if ((strValue = getStringValByKey(map, key)) != null) {
				value = Double.valueOf(strValue);
			}
		}
		return value;
	}

}

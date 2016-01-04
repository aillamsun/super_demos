package com.sung.base.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public class JsonUtil {
	/**
	 * 将Object类型的对象转化为JSON
	 * 
	 * @param object
	 */
	public static String objectToJson(Object object) {
        StringBuilder json = new StringBuilder();
        if (object == null) {
            json.append("");
        } else if (object instanceof String || object instanceof Integer
                || object instanceof Character || object instanceof Float
                || object instanceof Boolean || object instanceof Short
                || object instanceof Double || object instanceof Long
                || object instanceof Byte) {
            json.append("\"").append(stringToJson(object.toString()))
                    .append("\"");
        } else if (object instanceof Map) {
            json.append(mapToJson((Map<?, ?>) object));
        } else if (object instanceof Object[]) {
            json.append(arrayToJson((Object[]) object));
        } else if (object instanceof Collection<?>) {
            json.append(collectionToJson((Collection<?>) object));
        } else {
            json.append(beanToJson(object));
        }
        return json.toString();
    }

	/**
	 * 将集合类型的对象转化为JSON
	 * 
	 */
	public static String collectionToJson(Collection<?> collection) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (collection != null && collection.size() > 0) {
			for (Object object : collection) {
				json.append(objectToJson(object));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	/**
	 * 将一个Map类型的对象转化为JSON
	 * 
	 * @param map
	 *            
	 */
	public static String mapToJson(Map<?, ?> map) {
		StringBuilder json = new StringBuilder();
		Iterator it = map.keySet().iterator();
		json.append("{");
		if (map != null && map.size() > 0) {
			while (it.hasNext()) {
				Object object = it.next();
				json.append(objectToJson(object));
				json.append(":");
				json.append(objectToJson(map.get(object)));
				json.append(",");

			}
			json.setCharAt(json.length() - 1, '}');// 将最后一个逗号改为"}"
		} else {
			json.append("}");
		}
		return json.toString();
	}

	/**
	 * 将一个Bean类型的对象转化为JSON
	 * 
	 * @param bean
	 */
	@SuppressWarnings("rawtypes")
	public static String beanToJson(Object bean) {
		StringBuilder json = new StringBuilder();
		Class cl = bean.getClass();
		json.append("{");
		Field[] fields = cl.getDeclaredFields();
		for (Field f : fields) {
			try {
				StringBuilder fieldName = new StringBuilder(f.getName());
				fieldName.setCharAt(0,Character.toUpperCase(fieldName.charAt(0)));
				@SuppressWarnings("unchecked")
				Method method = cl.getDeclaredMethod("get" + fieldName.toString(), null);// 获取类中的get方法
				json.append(objectToJson(method.invoke(bean, null)));
				String value = objectToJson(method.invoke(bean, null));
				if (value != null && !value.equals("")) {
					json.append("\"");
					json.append(f.getName());
					json.append("\"");
					json.append(":");
					json.append("\"");
					json.append(value);
					json.append("\"");
					json.append(",");
				}
			} catch (SecurityException e) {
				LogUtils.logError("", e.getMessage());
			} catch (NoSuchMethodException e) {
				LogUtils.logError("", e.getMessage());
			} catch (IllegalArgumentException e) {
				LogUtils.logError("", e.getMessage());
			} catch (IllegalAccessException e) {
				LogUtils.logError("", e.getMessage());
			} catch (InvocationTargetException e) {
				LogUtils.logError("", e.getMessage());
			}
		}
		json.setCharAt(json.length() - 1, '}');// 将最后一个逗号改为"}"
		return json.toString();
	}

	/**
	 * 将数组转化为JSON
	 * 
	 * @param array
	 *           
	 */
	public static String arrayToJson(Object[] array) {
		StringBuilder json = new StringBuilder();
		json.append("[");
		if (array != null && array.length > 0) {
			for (Object object : array) {
				json.append(objectToJson(object));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	/**
	 * 将String类型的对象转化为JSON
	 */
	public static String stringToJson(String string) {
		StringBuilder json = new StringBuilder();
		if (string == null) {
			return "";
		}
		for (int i = 0; i < string.length(); i++) {
			char ch = string.charAt(i);
			switch (ch) {
			case '"':
				json.append("\\\"");
				break;
			case '\\':
				json.append("\\\\");
				break;
			case '\b':
				json.append("\\b");
				break;
			case '\f':
				json.append("\\f");
				break;
			case '\n':
				json.append("\\n");
				break;
			case '\r':
				json.append("\\r");
				break;
			case '\t':
				json.append("\\t");
				break;
			case '/':
				json.append("\\/");
				break;
			default:
				if (ch >= '\u0000' && ch <= '\u001F') {
					String ss = Integer.toHexString(ch);
					json.append("\\u");
					for (int k = 0; k < 4 - ss.length(); k++) {
						json.append('0');
					}
					json.append(ss.toUpperCase());
				} else {
					json.append(ch);
				}
			}
		}
		return json.toString();
	}
}

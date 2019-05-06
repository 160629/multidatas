package com.example.demo.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.BeanUtils;


public class BeanUtil {
	@SuppressWarnings("unchecked")
	public static <S, T> void objectExchange(S sourceObj, T targetObj) throws Exception {
		BeanUtils.copyProperties(sourceObj, targetObj);
	}

	public static <S, T> List<T> objectExchanges(List<S> sourceObj, Class<T> cla) throws Exception {
		List<T> list = new ArrayList<>();
		for (S s : sourceObj) {
			T t = cla.newInstance();
			BeanUtils.copyProperties(s, t);
			list.add(t);
		}
		return list;

	}

	@SuppressWarnings("unchecked")
	public static <T extends Comparable<? super T>> Comparator<T> byUp() {
		return new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String clazz = o1.getClass().getName();
				if (clazz.equals("java.lang.String")) {
					String s1 = o1.toString();
					String s2 = o2.toString();
					return Collator.getInstance(Locale.CHINESE).compare(s1, s2);
				} else if (clazz.equals("java.lang.Integer")) {
					Integer s1 = (Integer) o1;
					Integer s2 = (Integer) o2;
					return Double.compare(s1, s2);
				} else if (clazz.equals("java.lang.Double")) {
					Double s1 = (Double) o1;
					Double s2 = (Double) o2;
					return Double.compare(s1, s2);
				}
				return 0;
			}

		};
	}

	@SuppressWarnings("unchecked")
	public static <T extends Comparable<? super T>> Comparator<T> byDown() {
		return new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String clazz = o1.getClass().getName();
				if (clazz.equals("java.lang.String")) {
					String s1 = o1.toString();
					String s2 = o2.toString();
					return Collator.getInstance(Locale.CHINESE).compare(s2, s1);
				} else if (clazz.equals("java.lang.Integer")) {
					Integer s1 = (Integer) o1;
					Integer s2 = (Integer) o2;
					return Double.compare(s2, s1);
				} else if (clazz.equals("java.lang.Double")) {
					Double s1 = (Double) o1;
					Double s2 = (Double) o2;
					return Double.compare(s2, s1);
				}
				return 0;
			}

		};
	}
	

    public static Map<String,Object> objectToMap(Object obj){
        try{
            Class type = obj.getClass();
            Map<String,Object> returnMap = new HashMap<>();
            BeanInfo beanInfo = Introspector.getBeanInfo(type);

            PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
            for (int i = 0; i< propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (!propertyName.equals("class")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(obj, new Object[0]);
                    if(result == null){
                        continue;
                    }
                    //判断是否为 基础类型 String,Boolean,Byte,Short,Integer,Long,Float,Double
                    //判断是否集合类，COLLECTION,MAP              
                    if(result instanceof String 
                            || result instanceof Boolean 
                            || result instanceof Byte 
                            || result instanceof Short 
                            || result instanceof Integer 
                            || result instanceof Long 
                            || result instanceof Float 
                            || result instanceof Double 
                            || result instanceof Enum 
                            ){
                        if (result != null) {
                            returnMap.put(propertyName, result);
                        }
                    }else if(result instanceof Collection){                        
                        Collection<?> lstObj = arrayToMap((Collection<?>)result);
                        returnMap.put(propertyName, lstObj);
                                
                    }else if(result instanceof Map){
                        Map<Object,Object> lstObj = mapToMap((Map<Object,Object>)result);
                        returnMap.put(propertyName, lstObj);
                    } else {
                        Map mapResult = objectToMap(result);
                        returnMap.put(propertyName, mapResult);
                    }
                    
                }
            }
            return returnMap;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
        
    } 

    private static Map<Object, Object> mapToMap(Map<Object, Object> orignMap) {
        Map<Object,Object> resultMap = new HashMap<Object,Object>();
        for(Entry<Object, Object> entry:orignMap.entrySet()){
            Object key = entry.getKey();
            Object resultKey = null;
            if(key instanceof Collection){
                resultKey = arrayToMap((Collection)key);
            }else if(key instanceof Map){
                resultKey = mapToMap((Map)key);
            }
            else{
                if(key instanceof String 
                        || key instanceof Boolean 
                        || key instanceof Byte 
                        || key instanceof Short 
                        || key instanceof Integer 
                        || key instanceof Long 
                        || key instanceof Float 
                        || key instanceof Double 
                        || key instanceof Enum 
                        ){
                    if (key != null) {
                        resultKey = key;
                    }
                }else{
                    resultKey = objectToMap(key);
                }                
            }
            

            Object value = entry.getValue();
            Object resultValue = null;
            if(value instanceof Collection){
                resultValue = arrayToMap((Collection)value);
            }else if(value instanceof Map){
                resultValue = mapToMap((Map)value);
            }
            else{
                if(value instanceof String 
                        || value instanceof Boolean 
                        || value instanceof Byte 
                        || value instanceof Short 
                        || value instanceof Integer 
                        || value instanceof Long 
                        || value instanceof Float 
                        || value instanceof Double 
                        || value instanceof Enum 
                        ){
                    if (value != null) {
                        resultValue = value;
                    }
                }else{
                    resultValue = objectToMap(value);
                }                
            }
            
            resultMap.put(resultKey, resultValue);
        }        
        return resultMap;
    }


    private static Collection arrayToMap(Collection lstObj){
        ArrayList arrayList = new ArrayList();
        
        for (Object t : lstObj) {
            if(t instanceof Collection){
                Collection result = arrayToMap((Collection)t);
                arrayList.add(result);
            }else if(t instanceof Map){
                Map result = mapToMap((Map)t);
                arrayList.add(result);
            } else {
                if(t instanceof String 
                        || t instanceof Boolean 
                        || t instanceof Byte 
                        || t instanceof Short 
                        || t instanceof Integer 
                        || t instanceof Long 
                        || t instanceof Float 
                        || t instanceof Double 
                        || t instanceof Enum 
                        ){
                    if (t != null) {
                        arrayList.add(t);
                    }
                }else{
                    Object result = objectToMap(t);
                    arrayList.add(result);    
                }                
            }
        }
        return arrayList;
    }
}

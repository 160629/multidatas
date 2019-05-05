package com.example.demo.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
	

}

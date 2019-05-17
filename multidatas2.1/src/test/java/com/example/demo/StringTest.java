package com.example.demo;

import org.junit.Test;

public class StringTest {
	@Test
	public void name() {
		String n = "123343578";
		char[] array1 = n.toCharArray();
		char[] array2 = new char [array1.length];
		for (int i = array2.length-1; i > -1; i--) {
			array2[array1.length-1-i]=array1[i];
		}
		System.out.println(11);
		StringBuilder builder = new StringBuilder();
		
	}

}

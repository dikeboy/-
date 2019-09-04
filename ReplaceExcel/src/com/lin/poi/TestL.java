package com.lin.poi;

import java.io.UnsupportedEncodingException;

public class TestL {
	public static void main(String[] args) {
		String s ="Få {amount} enkelt genom att dela en gång!";
		try {
			System.out.println(new String(s.getBytes(),"utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

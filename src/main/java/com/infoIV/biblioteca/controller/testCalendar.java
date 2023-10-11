package com.infoIV.biblioteca.controller;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.infoIV.biblioteca.model.Config;

public class testCalendar {
	
	public static void main(){
		Config config= new Config();	
	

	
			Calendar c = GregorianCalendar.getInstance();
			int i = config.getDiapre();
			c.add(Calendar.DAY_OF_MONTH, i);
			@SuppressWarnings("deprecation")
			String s = c.getTime().toLocaleString();
			System.out.println(s);
			

	
	}

}

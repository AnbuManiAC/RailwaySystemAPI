package com.railway.util;

import java.time.LocalDate;
import java.util.regex.Pattern;

public class Validator {

	public static boolean isValidName(String name) {
		if(name.length()>2 && name.matches("[a-zA-z]+")) return true;
		return false;
	}
	public static boolean isValidUserName(String name) {
		if(name.length()>3 && name.matches("[a-zA-Z0-9_.]+")) return true;
		return false;
	}
 	public static boolean isValidDate(String date) {
		
		final Pattern DATE_PATTERN = Pattern.compile(
			      "^((2000|2400|2800|(2[0-9](0[48]|[2468][048]|[13579][26])))-02-29)$" 
			      + "|^((2[0-9]{3})-02-(0[1-9]|1[0-9]|2[0-8]))$"
			      + "|^((2[0-9]{3})-(0[13578]|10|12)-(0[1-9]|[12][0-9]|3[01]))$" 
			      + "|^((2[0-9]{3})-(0[469]|11)-(0[1-9]|[12][0-9]|30))$");

			    
		return DATE_PATTERN.matcher(date).matches();
	}
	public static boolean isLogicalDate(String date) {
		LocalDate _date = LocalDate.parse(date);
		LocalDate presentDate = LocalDate.now();
		if(_date.compareTo(presentDate)>=0) {
			return true;
		}
		return false;
	}
	public static int validateInt(String num) {
		int number;
		try {
			number = Integer.parseInt(num);
		} catch (NumberFormatException e) {
			System.out.println("\nNon numerical value found! Please enter valid number!\n");
			return -1;
		}
		return number;
	}
	
}

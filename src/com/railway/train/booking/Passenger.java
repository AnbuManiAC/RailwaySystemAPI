package com.railway.train.booking;

public class Passenger {
	
	private static int id = 1;
	private final String name;
	private final int age;
	private final String gender;
	private final String berthPreference;
	private final String  pnr;
	
	public Passenger(String name, int age, String gender, String berthPreference) {
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.berthPreference = berthPreference;
		this.pnr = String.valueOf(id++);
		
	}
	 
	public String getName() {
		return name;
	}
	public String getGender() {
		return gender;
	}
//	public void setGender(String gender) {
//		this.gender = gender;
//	}
	public int getAge() {
		return age;
	}
//	public void setAge(int age) {
//		this.age = age;
//	}
	public String getBerthPreference() {
		return berthPreference;
	}
//	public void setBerthPreference(String berthPreference) {
//		this.berthPreference = berthPreference;
//	}
	public String getPnr() {
		return pnr;
	}
//	public void setPnr() {
//		this.pnr = String.valueOf(id++);
//	}
	
	
	
	@Override
	public String toString() {
		return "Passenger [Name = " + name + ", Age = " + age + ", Gender = " + gender + ", PNR = " + pnr + "]";
	}
	
}

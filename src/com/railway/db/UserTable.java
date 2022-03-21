package com.railway.db;

import java.util.ArrayList;
import java.util.List;

public class UserTable {
	private List<User> users = new ArrayList<>();
	
	private UserTable() {
		
	}

	private static UserTable instance = null;
	
	public static UserTable getInstance() {
		if(instance == null) {
			instance = new UserTable();
		}
		return instance;
	}

	public List<User> getUsers(){
		return users;
	}
	public User createUser(String username, String password) {
		User user  = new User();
		user.setUsername(username);
		user.setPassword(password);
		return user;
	}
	public void insertUser(User user) {
		users.add(user);
	}
	
	
	
	
	
	public boolean isExistingUser(String name,String pwd) {
		for(User user : users) {
			if(user.getUsername().equals(name) && user.getPassword().equals(pwd))
				return true;
		}
		return false;
	}
	
	public boolean checkUser(String username) {
		for(User user : users) {
			if(user.getUsername().equals(username))
				return true;
		}
		return false;
	}
	
}

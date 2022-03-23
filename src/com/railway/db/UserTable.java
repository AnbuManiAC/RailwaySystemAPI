package com.railway.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.railway.train.booking.Ticket;

public class UserTable {
	
	private HashMap<User,Boolean> users = new HashMap<>();
	private HashMap<User, List<Ticket>> userAccessMapping = new HashMap<User, List<Ticket>>();
	
	private UserTable() {
		
	}

	private static UserTable instance = null;
	
	public static UserTable getInstance() {
		if(instance == null) {
			instance = new UserTable();
		}
		return instance;
	}

	public HashMap<User, Boolean> getUsers(){
		return users;
	}

	public User getCurrentUser() {
		for(User user: users.keySet()) {
			if(users.get(user).equals(true))
				return user;
		}
		return null;
		
	}
	public List<Ticket> getUserAccessMapping() {
		for(User user: users.keySet()) {
			if(users.get(user).equals(true))
				return userAccessMapping.get(user);
		}
		return null;
	}

	public User createUser(String username, String password) {
		User user  = new User();
		user.setUsername(username);
		user.setPassword(password);
		return user;
	}
	public void insertUser(User user) {
		users.put(user,false);
		
		userAccessMapping.put(user, new ArrayList<Ticket>());
	}

	
	public User searchUser(String name,String pwd) {
		for(User user : users.keySet()) {
			if(user.getUsername().equals(name) && user.getPassword().equals(pwd))
				return user;
		}
		return null;
	}
	
	public boolean checkUser(String username) {
		for(User user : users.keySet()) {
			if(user.getUsername().equals(username))
				return true;
		}
		return false;
	}
	
}

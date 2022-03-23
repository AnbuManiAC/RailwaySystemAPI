package com.railway.db;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.railway.model.Ticket;
import com.railway.model.User;

public class UserTable {
	
	private final Map<User,Boolean> users = new HashMap<>();
	private final Map<User, List<Ticket>> userAccessMapping = new HashMap<User, List<Ticket>>();
	
	private UserTable() {
		
	}

	private static UserTable instance = null;
	
	public static UserTable getInstance() {
		if(instance == null) {
			instance = new UserTable();
		}
		return instance;
	}

	public Map<User, Boolean> getUsers(){
		return Collections.unmodifiableMap(users);
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
				return Collections.unmodifiableList(userAccessMapping.get(user));
		}
		return null;
	}

	public User createUser(String username, String password) {
		User user  = new User(username,password);
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
	public void changeUserStatus(User currentUser) {
		boolean status = users.get(currentUser);
		users.replace(currentUser, !status);
	}

	public void addUserAccessMapping(Ticket t) {

		for(User user: users.keySet()) {
			if(users.get(user).equals(true))
				userAccessMapping.get(user).add(t);
		}
	}
	public void removeUserAccessMapping(Ticket t) {

		for(User user: users.keySet()) {
			if(users.get(user).equals(true))
				userAccessMapping.get(user).remove(t);
		}
	}
	
	
}

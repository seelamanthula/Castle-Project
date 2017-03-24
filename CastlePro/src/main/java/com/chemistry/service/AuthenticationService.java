package com.chemistry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.chemistry.dao.AuthenticationDAO;
import com.chemistry.model.User;

@Service
@Scope("session")
public class AuthenticationService {

	private AuthenticationDAO authenticate;

	@Autowired
	public void setAuthenticate(AuthenticationDAO authenticate) {
		this.authenticate = authenticate;
	}

	public boolean authenticateUser(User user) {
		return authenticate.authenticateUser(user);
	}
	
	public int insertNewUserDetails(User user) {
		int create = authenticate.createNewUserDetails(user);
		System.out.println("Account Creation : "+create);
		if(create > 0) {
			int k = authenticate.updateUserNetIdInUserAssignment(user);
			System.out.println("User Assignment : "+k);
			return k;		
		}
		
		return 0;
	}
	
	public void deleteUser(User user) {
		System.out.println("Delete USer : "+authenticate.deleteNewUser(user));
		
	}
}

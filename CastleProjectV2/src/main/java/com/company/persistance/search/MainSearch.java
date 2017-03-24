package com.company.persistance.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.company.dao.account.AuthenticationDAO;
import com.company.dao.gradecenter.GradeCenterDAO;

@Service
@Scope("session")
public class MainSearch {

protected AuthenticationDAO authenticate;
	
	@Autowired
	public void setAuthenticate(AuthenticationDAO authenticate) {
		this.authenticate = authenticate;
	}
	

	protected GradeCenterDAO gradeCenter;

	@Autowired
	public void setGradeCenterDAO(GradeCenterDAO gradeCenter) {
		this.gradeCenter = gradeCenter;
	}
}

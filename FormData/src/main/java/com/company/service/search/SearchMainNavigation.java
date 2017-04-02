package com.company.service.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.company.dao.gradecenter.GradeCenterDAO;
import com.company.persistance.search.SearchService1;
import com.company.persistance.search.SearchService2;

@Service
@Scope("session")
public class SearchMainNavigation {

	protected SearchService1 seachService1;

	@Autowired
	public void setSeachService1(SearchService1 seachService1) {
		this.seachService1 = seachService1;
	}
	
	protected SearchService2 seachService2;

	@Autowired
	public void setSeachService2(SearchService2 seachService2) {
		this.seachService2 = seachService2;
	}
	
}

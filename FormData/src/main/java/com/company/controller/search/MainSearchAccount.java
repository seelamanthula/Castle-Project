package com.company.controller.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.company.service.NavigateRegistration;
import com.company.service.search.SearchNavigation1;

@Controller
@Scope("session")
public class MainSearchAccount {

	protected SearchNavigation1 searchNavigation1;
	
	@Autowired
	private void setSearchNavigation1(SearchNavigation1 searchNavigation1) {
		this.searchNavigation1 = searchNavigation1;
	}
}

package com.company.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.company.dao.StoreExperimentDetails;
import com.company.dao.StoreQuestions;

@Service
@Scope("session")
public class MainService {

	protected StoreQuestions storeQuestions;
	
	@Autowired
	private void setStoreQuestions(StoreQuestions storeQuestions) {
		this.storeQuestions = storeQuestions;
	}


	protected StoreExperimentDetails storeExperimentDetails;
	
	@Autowired
	private void setStoreExperimentDetails(StoreExperimentDetails storeExperimentDetails) {
		this.storeExperimentDetails = storeExperimentDetails;
	}
}

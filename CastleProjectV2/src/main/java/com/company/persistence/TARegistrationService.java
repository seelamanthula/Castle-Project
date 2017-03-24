package com.company.persistence;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.company.dao.StoreQuestions;
import com.company.dao.StoreTARegistration;
import com.company.demo.Store1;
import com.company.model.registration.TAMainDetails;

@Service
@Scope("session")
public class TARegistrationService {

	private StoreTARegistration storeTARegistration;
	
	@Autowired
	private void setStoreTARegistration(StoreTARegistration storeTARegistration) {
		this.storeTARegistration = storeTARegistration;
	}
	
	public int storeModifiedAndNotModifiedJSON(String semester, String year, String modified, String notModified) {
		
		return storeTARegistration.storeModifiedAndNotModifiedJSON(semester, year, modified, notModified);
	}
	
	public int storeTARegistration(ArrayList<TAMainDetails> taDetails) {
		return storeTARegistration.storeTARegistration(taDetails);
	}
}

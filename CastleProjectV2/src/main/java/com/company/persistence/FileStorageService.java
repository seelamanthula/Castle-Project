package com.company.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.company.dao.StoreQuestions;
import com.company.demo.Store1;
import com.company.model.FileContent;
import com.company.model.QuestionContent;

@Service
@Scope("session")
public class FileStorageService {

	private Store1 store;
	private StoreQuestions storeQuestions;
	
	@Autowired
	private void setStore(Store1 store) {
		this.store = store;
	}
	
	@Autowired
	private void setStoreQuestions(StoreQuestions storeQuestions) {
		this.storeQuestions = storeQuestions;
	}
	
	public void storeQuestionContent(QuestionContent content) {
		
	}
	
	public void storeQuestionFiles(List<FileContent> files) {
		
	}
	
	public void storeFile(byte[] file) {
		int k  = store.storeFile(file);
		System.out.println("File Result : "+k);
	}
	
	public byte[] getFile(int id) {
		return store.retrieveFile(id);
	}
	
	public void storeFileWithString(String s) {
		int k  = store.storeFileWithString(s);
		System.out.println("File Result : "+k);
	}

	public String getFileWithString(int id) {
		return store.getFileWithString(id);
	//	System.out.println("File Result : "+k);
	}
}

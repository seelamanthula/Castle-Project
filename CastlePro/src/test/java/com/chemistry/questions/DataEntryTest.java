package com.chemistry.questions;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.chemistry.dao.StudentQuestionsDAO;

public class DataEntryTest {

	StudentQuestionsDAO dao = new StudentQuestionsDAO();
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		System.out.println("Size : "+dao.getDataEntryDetails(1,1).size());
		System.out.println("Size : "+dao.getTextAreaDetails(1,1).size());

	}

}

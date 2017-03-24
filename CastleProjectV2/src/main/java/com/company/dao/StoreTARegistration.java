package com.company.dao;

import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.company.dao.account.AuthenticationDAO;
import com.company.model.registration.TAMainDetails;

@Repository
@Scope("session")
public class StoreTARegistration extends StoreHead {

	public int storeModifiedAndNotModifiedJSON(String semester, String year, String modified, String notModified) {	
		String sql = "insert into registration_slot_json(semester, semesteryear, setPoint, modified, notmodified) "
				+ "values('"+semester+"','"+year+"',1,'"+modified+"','"+notModified+"')";	
		
		removeTAsModifiedAndNotModifiedWithSemesterAndYear(semester, year);
		updateSetPoint();
		
		return insertCommand(sql);
	}
	
	private int updateSetPoint() {
		String sql = "update registration_slot_json set setPoint = 0 where setPoint = 1";
		return updateCommand(sql);
	}
	
	public int storeTARegistration(ArrayList<TAMainDetails> taDetails) {
		
		Iterator<TAMainDetails> iterate = taDetails.iterator();
	
		if(iterate.hasNext()) {
			TAMainDetails details = iterate.next();
			removeTAsWithSemesterAndYear(details.getSemester(), details.getYear());
			
			int k  = storeSingleTARegistration(details);
			if(k == 0) return 0;				
		}
		while(iterate.hasNext()) {
			TAMainDetails details = iterate.next();
			int k  = storeSingleTARegistration(details);
			if(k == 0) return 0;				
		}
		
		return 1;
	}
	
	private int removeTAsModifiedAndNotModifiedWithSemesterAndYear(String semester, String year) {		
		String sql = "delete from registration_slot_json where semester='"+semester+"' and semesterYear='"+year+"'";
		return deleteCommand(sql);		
	}
	
	private int removeTAsWithSemesterAndYear(String semester, String year) {		
		String sql = "delete from registration_slot where semester='"+semester+"' and semesterYear='"+year+"'";
		return deleteCommand(sql);		
	}
	
	private int storeSingleTARegistration(TAMainDetails tadetails) {
		
		String sql = "insert into registration_slot(semester, semesterYear, weekday, "
				+ "daytime, classno, room, taname, tanetid) values('"+tadetails.getSemester()+"','"+tadetails.getYear()+"',"
					+ "'"+tadetails.getDay()+"','"+tadetails.getTime()+"','"
	  				+ ""+tadetails.getTaDetails().getClassNo()+"','"+tadetails.getTaDetails().getRoom()+"','"
					+ ""+tadetails.getTaDetails().getTaName()+"','"+tadetails.getTaDetails().getTaNetId()+"')";
		
		return insertCommand(sql);
	}
}

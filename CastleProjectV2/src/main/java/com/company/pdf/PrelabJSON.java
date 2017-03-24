package com.company.pdf;

import java.util.ArrayList;

import org.springframework.context.annotation.Scope;

import com.company.pdf.model.AssessmentHeader;
import com.company.pdf.model.AssessmentModelPDF;
import com.company.pdf.model.PrelabModel;
import com.company.pdf.model.QuestionReport;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Scope("session")
public class PrelabJSON {

	public PrelabModel parseResults(String json) {

		System.out.println("Given Prelab Json : "+json);
		JsonObject mainObject = new JsonParser().parse(json).getAsJsonObject();
		JsonObject basics = mainObject.get("basics").getAsJsonObject();	
		String lastUpdate = mainObject.get("lastupdate").getAsString();
		
		String netId = basics.get("unetid").getAsString();
		String expId = basics.get("uexpid").getAsString();
		String modId = basics.get("umodid").getAsString();
		String expName = basics.get("uexpname").getAsString();
		String modName = basics.get("umodname").getAsString();

		
		JsonObject prelab = mainObject.get("prelab").getAsJsonObject();	
		
		/*String objective = prelab.get("objective").getAsString();
		String hypothesis = prelab.get("hypothesis").getAsString();
		String variables = prelab.get("variables").getAsString();
		String experimentOutline = prelab.get("experimentOutline").getAsString();
		String chemicalHazards = prelab.get("chemicalHazards").getAsString();*/
		
		
		AssessmentHeader header = new AssessmentHeader();
		header.setExpId(expId);
		header.setModId(modId);
		header.setNetId(netId);
		header.setModName(modName);
		header.setExpName(expName);
		
		
		PrelabModel model = setPrelabAnswers(prelab);
		model.setHeader(header);

		model.setLastUpdate(lastUpdate);

		return model;
	}
	
	private PrelabModel setPrelabAnswers(JsonObject prelab) {

		String objective = prelab.get("objective").getAsString();
		String hypothesis = prelab.get("hypothesis").getAsString();
		String variables = prelab.get("variables").getAsString();
		String experimentOutline = prelab.get("experimentOutline").getAsString();
		String chemicalHazards = prelab.get("chemicalHazards").getAsString();
		
		
		PrelabModel model = new PrelabModel();
		model.setChemicalhazards(chemicalHazards);
		model.setExperimentOutline(experimentOutline);
		model.setHypothesis(hypothesis);
		model.setObjective(objective);
		model.setVariables(variables);

		return model;
	}
}

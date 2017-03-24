package com.chemistry.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.chemistry.model.Experiment;
import com.chemistry.model.PreLab;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PdfGeneration {
	
	private Experiment experiment;
	private String netId;
	
	private Experiment getExperiment() {
		return experiment;
	}
	private void setExperiment(Experiment experiment) {
		this.experiment = experiment;
	}
	private String getNetId() {
		return netId;
	}
	private void setNetId(String netId) {
		this.netId = netId;
	}

	public byte[] preLabPDFGeneration(List<PreLab> prelabList, String netId, Experiment experiment) {
		
		Document document = new Document(PageSize.A4);
		setExperiment(experiment);
		setNetId(netId);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		try {
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);
			document.open();
			createLists(document, prelabList);
			document.close();
			writer.close();
			
			return outputStream.toByteArray();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void createLists(Document document, List<PreLab> prelabList) throws DocumentException {

		Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD);
		Font moduleFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);
		Font pointsFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
		Font paraFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
		
		document.newPage();
		createBasics(document);
		
		document.add(createPara("PRELAB", chapterFont, 0, 10));
		Iterator<PreLab> iterate = prelabList.iterator();

		while(iterate.hasNext()) {
			
			PreLab prelab = iterate.next();
			System.out.println(prelab.toString());
			
			document.add(createPara(prelab.getConcept().getName(), moduleFont, 0, 10));
			document.add(createPara("Objective", pointsFont, 10, 10));
			
			document.add(createPara(prelab.getObjective(), paraFont, 15, 5));

			document.add(createPara("Hypothesis", pointsFont, 10, 10));			
			document.add(createPara(prelab.getHypothesis(), paraFont, 15, 5));

			document.add(createPara("Variables", pointsFont, 10, 10));
			document.add(createPara(prelab.getVariables(), paraFont, 15, 5));

			document.add(createPara("Experimental Outline", pointsFont, 10, 10));
			document.add(createPara(prelab.getExperimental(), paraFont, 15, 5));

			document.add(createPara("Chemical Hazards", pointsFont, 10, 10));
			document.add(createPara(prelab.getChemical(), paraFont, 15, 5));
		}
	}
	
	private void createBasics(Document document) throws DocumentException {
		
		Font experimentFont = FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD);
		Font netIdFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
		
		String title = "";
		if(experiment.getTitle() != null) {
			title = experiment.getTitle();
		}
		Paragraph para = createPara(title, experimentFont, 0, 10);
	//	Paragraph para = createPara("Experiment Title", experimentFont, 0, 10);	
		para.setAlignment(Element.ALIGN_CENTER);
		document.add(para);
		
		Paragraph netId = createPara(getNetId(), netIdFont, 0, 5);
		netId.setAlignment(Element.ALIGN_RIGHT);
		document.add(netId);
	}
		
	private Paragraph createPara(String content, Font font, float intendation, int space) {
		
		Paragraph para = new Paragraph(content, font);
		para.setIndentationLeft(intendation);
		para.setSpacingBefore(space);
		para.setSpacingBefore(space);

		return para;
	}
	
	
	public byte[] mergePDFDocs(List<InputStream> input) {
		
		Document document = new Document(PageSize.A4);
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		try {
			PdfWriter writer = PdfWriter.getInstance(document, output);
			document.open();
			
			PdfContentByte contentByte = writer.getDirectContent();
			
			Iterator<InputStream> iterate = input.iterator();
			while(iterate.hasNext()) {
				PdfReader reader = new PdfReader(iterate.next());
				for(int i =0; i < reader.getNumberOfPages(); i++) {
					document.newPage();
					PdfImportedPage importedPage = writer.getImportedPage(reader, i);
					contentByte.addTemplate(importedPage, 0, 0);
				}
			}
			return output.toByteArray();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}

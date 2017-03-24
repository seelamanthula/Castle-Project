package com.chemistry.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.annotation.Scope;
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
@Scope("session")
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

	private ByteArrayOutputStream outputStream;
	
	
	public ByteArrayOutputStream getOutputStream() {
		return outputStream;
	}
	public void setOutputStream(ByteArrayOutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public byte[] preLabPDFGeneration1(List<PreLab> prelabList, String netId, Experiment experiment) {
		return preLabPDFGeneration(prelabList,netId,experiment, "Volumetric Glassware");
	}

	public byte[] preLabPDFGeneration2(List<PreLab> prelabList, String netId, Experiment experiment) {
		return preLabPDFGeneration(prelabList,netId,experiment, "Acid Base Titration");
	}

	private byte[] preLabPDFGeneration(List<PreLab> prelabList, String netId, Experiment experiment, String moduleName) {
		
		System.out.println("In PDF Generation");
		
		Document document = new Document(PageSize.A4);
		setExperiment(experiment);
		setNetId(netId);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		try {
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);
			document.open();
			createLists(document, prelabList, moduleName);
			document.close();
			writer.close();
			
			setOutputStream(outputStream);
			return outputStream.toByteArray();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private void createLists(Document document, List<PreLab> prelabList, String moduleName) throws DocumentException {

		Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD);
		Font moduleFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);
		Font pointsFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
		Font paraFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
		
		document.newPage();
		createBasics(document);
		
		document.add(createPara("PRELAB : "+moduleName, chapterFont, 0, 10));
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
		
	public byte[] volumetricLabPDFGeneration1(String question1, String question2, String netId, Experiment experiment) {
		return labPDFGeneration(question1, question2, netId,experiment.getExperimentId(), "Volumetric Glassware");
	}

	public byte[] acidBaseLabPDFGeneration1(String question1, String netId, Experiment experiment) {
		return labAcidBasePDFGeneration(question1, netId,experiment.getExperimentId(), "Acid Base Titration");
	}
	
	private byte[] labAcidBasePDFGeneration(String question1, String netId, int id, String lab) {
		System.out.println("In  Lab PDF Generation");
		
		Document document = new Document(PageSize.A4);
		//setExperiment(experiment);
	//	setNetId(netId);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.out.println("BAOS");
		try {
			FileOutputStream fos = new FileOutputStream("D:/Dr.Chen/1. Stories/two.pdf");
			System.out.println("After FOS");
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);
			PdfWriter writer2 = PdfWriter.getInstance(document, fos);
		
			
			document.open();
			System.out.println("Its OPEN");
			createLabBasics(document, netId);
			createAcidBaseLab(document, question1, lab);
			document.close();
			writer.close();
			System.out.println("Written Document in FOS");
			System.out.println("Write PDF");
			
			setOutputStream(outputStream);
			//fos.write(b);
			writer2.close();
			System.out.println("Erotter2 closed");
			return outputStream.toByteArray();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void createAcidBaseLab(Document document, String q1, String lab) {
		Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD);
		Font moduleFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);
		Font pointsFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
		Font paraFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
		
		System.out.println("Inside Lab");
		//document.newPage();
		try {
			System.out.println("Before Basics");
		//	createLabBasics(document, netId);
			System.out.println("After Basics");
			document.add(createPara("LAB : "+lab, chapterFont, 0, 10));
				
				document.add(createPara("You found an RSD of RSD ppt. Is your data precise? Explain.", pointsFont, 10, 10));
				document.add(createPara(q1, paraFont, 15, 5));
	
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("End Of Lab");

	}
	
	private byte[] labPDFGeneration(String question1, String question2, String netId, int id, String lab) {
		System.out.println("In  Lab PDF Generation");
		
		Document document = new Document(PageSize.A4);
		//setExperiment(experiment);
	//	setNetId(netId);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.out.println("BAOS");
		try {
			FileOutputStream fos = new FileOutputStream("D:/Dr.Chen/1. Stories/one.pdf");
			System.out.println("After FOS");
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);
			PdfWriter writer2 = PdfWriter.getInstance(document, fos);
		
			
			document.open();
			System.out.println("Its OPEN");
			createLabBasics(document, netId);
			createLab(document, question1, question2, lab);
			document.close();
			writer.close();
			System.out.println("Written Document in FOS");
			System.out.println("Write PDF");
			
			setOutputStream(outputStream);
			//fos.write(b);
			writer2.close();
			System.out.println("Erotter2 closed");
			return outputStream.toByteArray();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

private void createLabBasics(Document document, String netId) throws DocumentException {
		
		Font experimentFont = FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD);
		Font netIdFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
		
		Paragraph para = createPara("Titrations Experiment", experimentFont, 0, 10);
	//	Paragraph para = createPara("Experiment Title", experimentFont, 0, 10);	
		para.setAlignment(Element.ALIGN_CENTER);
		document.add(para);
		
		Paragraph pnetId = createPara(netId, netIdFont, 0, 5);
		pnetId.setAlignment(Element.ALIGN_RIGHT);
		document.add(pnetId);
	}

	private void createLab(Document document, String q1, String q2, String lab) {
		Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD);
		Font moduleFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);
		Font pointsFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
		Font paraFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
		
		System.out.println("Inside Lab");
		//document.newPage();
		try {
			System.out.println("Before Basics");
		//	createLabBasics(document, netId);
			System.out.println("After Basics");
			document.add(createPara("LAB : "+lab, chapterFont, 0, 10));
				
				document.add(createPara("You found an RSD of RSD pptand a percent error of PE %.", pointsFont, 10, 10));
				document.add(createPara(q1, paraFont, 15, 5));
				document.add(createPara("You found an RSD of RSD pptand a percent error of PE %.", pointsFont, 10, 10));			
				document.add(createPara(q2, paraFont, 15, 5));

		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("End Of Lab");

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

	void fileCreation(byte[] pdf) {
		try {
			System.out.println("Generated PDF");
			FileOutputStream fos = new FileOutputStream("C:/Users/Harish/Desktop/Magnus/1.pdf");
			File file = new File("C:/Users/Harish/Desktop/Magnus/2.pdf");
	
			fos.write(pdf);
			fos.close();
			System.out.println("Done FOS");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	
}

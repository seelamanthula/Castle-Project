package com.company.pdf;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.context.annotation.Scope;

import com.company.pdf.model.AssessmentHeader;
import com.company.pdf.model.AssessmentModelPDF;
import com.company.pdf.model.PrelabModel;
import com.company.pdf.model.QuestionReport;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Scope("session")
public class PrelabPDF {
	
	Document document = null;
	
	public byte[] makePDF(PrelabModel modelPDf) {
		
		System.out.println("In Prelab PDF Generation");
		
		document = new Document(PageSize.A4);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.out.println("BAOS");
		try {
/*			String url = "C:/Users/Harish/Desktop/tryprelab.pdf";
			FileOutputStream fos = new FileOutputStream(url);*/
			System.out.println("After FOS");
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);
//			PdfWriter writer2 = PdfWriter.getInstance(document, fos);
		
			document.open();
			System.out.println("Its OPEN");

			if(modelPDf == null) {
				notAttended();
			} else {
				setUpHeader(modelPDf.getHeader());
				setPDFBody(modelPDf);
				setUpScore(modelPDf.getLastUpdate());
			}
			document.close();
			writer.close();
			System.out.println("Written Document in FOS");
			System.out.println("Write PDF");
			
	/*		writer2.close();
			System.out.println("Erotter2 closed");*/
			return outputStream.toByteArray();
		} catch (DocumentException e) {
			e.printStackTrace();
		} 
		return null;
	}

	private void setUpScore(String score) throws DocumentException {
		
		Font moduleFont = FontFactory.getFont(FontFactory.HELVETICA, 15);
			
		Paragraph pnetId = createPara("Last Updated : "+score, moduleFont, 0, 5);
		pnetId.setAlignment(Element.ALIGN_RIGHT);
		document.add(pnetId);
	}

	private void notAttended() throws DocumentException {
		Font experimentFont = FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD);
		
		Paragraph pnetId = createPara("Student Not Attended this Module yet ", experimentFont, 0, 5);
		pnetId.setAlignment(Element.ALIGN_CENTER);
		document.add(pnetId);
	}

	private void setUpHeader(AssessmentHeader header) throws DocumentException {
		
		Font experimentFont = FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD);
		Font moduleFont = FontFactory.getFont(FontFactory.HELVETICA, 15);
		Font netIdFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
		
		Paragraph para = createPara(header.getExpName(), experimentFont, 0, 10);
		para.setAlignment(Element.ALIGN_CENTER);
		document.add(para);

		Paragraph mPara = createPara(header.getModName(), moduleFont, 0, 10);
		mPara.setAlignment(Element.ALIGN_CENTER);
		document.add(mPara);
	
		Paragraph pnetId = createPara(header.getNetId(), netIdFont, 0, 5);
		pnetId.setAlignment(Element.ALIGN_RIGHT);
		document.add(pnetId);
	}

	private void setPDFBody(PrelabModel model) {
		Font questionFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);
		Font resultFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
		
	
		try {
			
				document.add(createPara("Objective : ", questionFont, 0, 10));			
				document.add(createPara(model.getObjective(), resultFont, 15, 5));

				document.add(createPara("Hypothesis : ", questionFont, 0, 10));			
				document.add(createPara(model.getHypothesis(), resultFont, 15, 5));

				document.add(createPara("Environmental Variables : ", questionFont, 0, 10));			
				document.add(createPara(model.getVariables(), resultFont, 15, 5));

				document.add(createPara("Experiment Outline : ", questionFont, 0, 10));			
				document.add(createPara(model.getExperimentOutline(), resultFont, 15, 5));

				document.add(createPara("Chemical Hazards : ", questionFont, 0, 10));			
				document.add(createPara(model.getChemicalhazards(), resultFont, 15, 5));
		
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private Paragraph createPara(String content, Font font, float intendation, int space) {
		
		Paragraph para = new Paragraph(content, font);
		para.setIndentationLeft(intendation);
		para.setSpacingBefore(space);
		para.setSpacingBefore(space);

		return para;
	}
}

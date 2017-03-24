package com.chemistry.util;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

@Service
@Scope("session")
public class PDFPostLabGenerate {

	public byte[] PostlabPDFGeneration(String[] questions, String netId) {
		System.out.println("In  Lab PDF Generation");
		
		Document document = new Document(PageSize.A4);
		//setExperiment(experiment);
	//	setNetId(netId);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		System.out.println("BAOS");
		try {
			FileOutputStream fos = new FileOutputStream("D:/Dr.Chen/1. Stories/postlab.pdf");
			System.out.println("After FOS");
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);
			PdfWriter writer2 = PdfWriter.getInstance(document, fos);
		
			
			document.open();
			System.out.println("Its OPEN");
			createPostLabBasics(document, netId);
			createPostLab(document, questions);
			document.close();
			writer.close();
			System.out.println("Written Document in FOS");
			System.out.println("Write PDF");
			
			
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

	private void createPostLabBasics(Document document, String netId) throws DocumentException {
		
		Font experimentFont = FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD);
		Font netIdFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
		
		Paragraph para = createPara("Titrations Experiment", experimentFont, 0, 10);
		para.setAlignment(Element.ALIGN_CENTER);
		document.add(para);
		
		Paragraph pnetId = createPara(netId, netIdFont, 0, 5);
		pnetId.setAlignment(Element.ALIGN_RIGHT);
		document.add(pnetId);
	}

	private void createPostLab(Document document, String[] question) {
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
			document.add(createPara("PostLAB : ", chapterFont, 0, 10));
				
				document.add(createPara("Reflect on two key concepts you learned in the lab."
							+ "Key Concepts: "
							+ "After completing this experiment, you are responsible for understanding: the use of volumetric glassware, indicators, endpoints, acid-base reactions and titrations..", pointsFont, 10, 10));
				document.add(createPara(question[0], paraFont, 15, 5));

				document.add(createPara("Reflect on any scientific errors or possible improvements associated with the experiment. What could be done to perform the experiment better?", pointsFont, 10, 10));
				document.add(createPara(question[1], paraFont, 15, 5));

				document.add(createPara("You used the equation below to calculate the amount of 5.00 M NaOHrequired to make 0.100 M NaOH. Solve for the equation in terms of V1. C1V1= C2V2", pointsFont, 10, 10));
				document.add(createPara(question[2], paraFont, 15, 5));
				
				document.add(createPara("A student got the following HClmolarities: 0.201, 0.205, 0.203, 0.199 M. If the actual molarity was 0.200 is the data accurate? Precise? Explain. "
						+ "Be sure to calculate the RSD and percent error and use the values in your explanation.", pointsFont, 10, 10));
				document.add(createPara(question[3], paraFont, 15, 5));
				
				document.add(createPara("The initial burette reading in a titration was X.00 mL and the final burette reading was Y.00 mL. If 10.00 mL of HClwas titrated and Z.000 M NaOHwas used in the titration, "
						+ "what is the concentration of the acid?", pointsFont, 10, 10));
				document.add(createPara(question[4], paraFont, 15, 5));
				
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
}

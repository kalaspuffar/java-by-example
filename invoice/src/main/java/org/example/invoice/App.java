package org.example.invoice;

import org.json.simple.JSONValue;
import org.json.simple.JSONObject;
import java.io.*;
import java.util.*;
import org.example.invoice.document.*;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class App 
{
    public static void main( String[] args )
    {
        PDDocument pdfDocument = new PDDocument();
    	try {
	    	JSONObject jsonDocument = 
	    		(JSONObject)JSONValue
	    			.parse(new FileReader(new File("single.json")));

	    	Invoice invoice = new Invoice(jsonDocument);

            System.out.println(invoice.getNotes());

            PDPage pdfPage = new PDPage();
            pdfDocument.addPage(pdfPage);

            PDFont font = PDType1Font.HELVETICA_BOLD;

            PDPageContentStream contents = new PDPageContentStream(pdfDocument, pdfPage);
            contents.beginText();
            contents.setFont(font, 16);
            contents.newLineAtOffset(120, 740);
            contents.showText("Example Inc.");
            contents.endText();
            contents.beginText();
            contents.setFont(font, 8);
            contents.newLineAtOffset(120, 720);
            contents.showText("Mainstreet bl 13 1st floor");
            contents.endText();
            contents.beginText();
            contents.setFont(font, 8);
            contents.newLineAtOffset(120, 710);
            contents.showText("New York, 3453, New York, USA");
            contents.endText();
            contents.beginText();
            contents.setFont(font, 8);
            contents.newLineAtOffset(120, 700);
            contents.showText("1-555-5757, www.example.com");
            contents.endText();

            PDImageXObject pdImage = PDImageXObject.createFromFile("logo.png", pdfDocument);
            final float width = 60f;
            final float scale = width / pdImage.getWidth();
            contents.drawImage(pdImage, 50, 720, width, pdImage.getHeight()*scale);

            contents.close();

            pdfDocument.save("single-invoice.pdf");
    	} catch (Exception e) {
    		e.printStackTrace();
        } finally {
            try {
                pdfDocument.close();    
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}

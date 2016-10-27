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
	    			.parse(new FileReader(new File("empty.json")));

	    	Invoice invoice = new Invoice(jsonDocument);

/*
            "first": "季",
            "last": "睿杰"
*/

            PDPage pdfPage = new PDPage();
            pdfDocument.addPage(pdfPage);
            PDPageContentStream contents = new PDPageContentStream(pdfDocument, pdfPage);

            invoice.printPDF(contents);

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

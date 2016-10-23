package org.example.invoice.document;

import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.example.invoice.PDFPrinter;
import java.awt.Color;
import java.io.IOException;

public class Invoice {
	private Header header = null;
	private Address shipTo = null;
	private Address billTo = null;
	private List<InvoiceRow> rows = new ArrayList<InvoiceRow>();
	private ShippingData shipData = null;
	private String notes;

	public Invoice(JSONObject doc) {
		this.header = new Header(getJsonObjectFromDocument(doc, "invoiceHeader"));
		this.billTo = new Address(getJsonObjectFromDocument(doc, "billTo"));

		if(doc.containsKey("shipTo")) {
			JSONObject shipToObj = (JSONObject)doc.get("shipTo");
			if(shipToObj.containsKey("sameAsBilling") && ((Boolean)shipToObj.get("sameAsBilling")) == true) {
				this.shipTo = this.billTo;
			}
		}

		if(this.shipTo == null) {
			this.shipTo = new Address(getJsonObjectFromDocument(doc, "shipTo"));	
		}		
		this.shipData = new ShippingData(getJsonObjectFromDocument(doc, "shippingData"));
		if(doc.containsKey("invoiceRows")) {
			Object simpleInvoiceRowsObject = doc.get("invoiceRows");
			if(simpleInvoiceRowsObject instanceof JSONArray) {
				for(Object simpleInvRowObj : ((JSONArray)simpleInvoiceRowsObject)) {
					if(simpleInvRowObj instanceof JSONObject) {
						addRow(new InvoiceRow((JSONObject)simpleInvRowObj));
					}
				}
			}
		}		
		if(doc.containsKey("notes")) {
			this.setNotes((String)doc.get("notes"));
		}
	}

	private JSONObject getJsonObjectFromDocument(JSONObject doc, String key) {
		if(doc.containsKey(key)) {
			Object simpleObject = doc.get(key);
			if(simpleObject instanceof JSONObject) {
				return (JSONObject)simpleObject;
			}
		}
		return null;
	}

	public void printPDF(PDPageContentStream contents) throws IOException {
		this.header.printPDF(contents);
		this.shipTo.printPDF(contents, false);
		this.billTo.printPDF(contents, true);
		this.shipData.printPDF(contents);

		printRowHeader(contents);

		int rowY = 499;
		printRowBackGround(contents, rowY, 16);

		for (InvoiceRow invoiceRow : rows) {			
			invoiceRow.printPDF(contents, rowY);
			rowY -= 20;
		}

		printFooter(contents);
	}

	public void printRowBackGround(PDPageContentStream contents, int rowY, int numRows) throws IOException {
        Color strokeColor = new Color(100, 100, 100);
        contents.setStrokingColor(strokeColor);
        Color fillColor = new Color(240, 240, 240);
        contents.setNonStrokingColor(fillColor);

		boolean odd = true;
        for(int i=0; i<numRows; i++) {
	        if(odd) {
		        contents.addRect(51, rowY, 518, 20);
		        contents.fill();
	        }

        	contents.moveTo(50, rowY);
        	contents.lineTo(50, rowY+20);
        	contents.moveTo(570, rowY);
        	contents.lineTo(570, rowY+20);
        	contents.stroke();
			rowY -= 20;
			odd = !odd;
        }

    	contents.moveTo(50, rowY+20);
    	contents.lineTo(570, rowY+20);
    	contents.stroke();
	}
	
	public void printRowHeader(PDPageContentStream contents) throws IOException {
        Color fillColor = new Color(230, 230, 230);
        Color strokeColor = new Color(100, 100, 100);
        contents.setStrokingColor(strokeColor);
        contents.setNonStrokingColor(fillColor);
        contents.addRect(50, 520, 520, 20);
        contents.fillAndStroke();

        final int headerY = 527;
        PDFont font = PDType1Font.HELVETICA;
        PDFPrinter headerPrinter = new PDFPrinter(contents, font, 12);
        headerPrinter.putText(60, headerY, "Product no.");
        headerPrinter.putText(160, headerY, "Description");
        headerPrinter.putText(380, headerY, "Quantity");
        headerPrinter.putText(440, headerY, "Unit price");
        headerPrinter.putText(510, headerY, "Total");
	}

	public void printFooter(PDPageContentStream contents) throws IOException {
        Color strokeColor = new Color(100, 100, 100);
        contents.setStrokingColor(strokeColor);
        contents.addRect(50, 35, 370, 135);
        contents.stroke();

        PDFPrinter footerLabelPrinter = new PDFPrinter(contents, PDType1Font.HELVETICA_BOLD, 8);
        PDFPrinter footerValuePrinter = new PDFPrinter(contents, PDType1Font.HELVETICA, 8);
        footerLabelPrinter.putText(50, 172, "Notes");
        int rowY = 160;
        StringBuilder sb = new StringBuilder();
        for(String s : this.getNotes().split(" ")) {
        	if(footerValuePrinter.widthOfText(sb.toString() + " " + s) > 365) {
	        	if(rowY < 50) {
	        		sb.append("...");
		        	footerValuePrinter.putText(55, rowY, sb.toString());
		        	sb = new StringBuilder();
		        	break;
	        	}
	        	footerValuePrinter.putText(55, rowY, sb.toString());        	
	        	rowY -= 10;
	        	sb = new StringBuilder();
        	}        	
        	sb.append(s);
        	sb.append(" ");
        }
    	footerValuePrinter.putText(55, rowY, sb.toString());        	
	}

	public List<InvoiceRow> getRows() {
		return this.rows;
	}
    public void addRow(InvoiceRow row) {
        this.rows.add(row);
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
    public String getNotes() {
        return this.notes;
    }
}
package org.example.invoice.document;

import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.example.invoice.PDFPrinter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.Color;
import java.math.RoundingMode;
import java.math.BigDecimal;
import java.io.IOException;

public class Invoice {
	private Header header = null;
	private Address shipTo = null;
	private Address billTo = null;
	private List<InvoiceRow> rows = new ArrayList<InvoiceRow>();
	private ShippingData shipData = null;
	private String notes;

	private int maxRowSize = 23;
	private int maxPageWithSummation = 16;
	private int breakPoint = 12;

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

	private JSONObject getElementToJson(Element e) {
		JSONObject jsonObject = new JSONObject();
		Elements tableRows = e.getElementsByTag("tr");
		Element header = tableRows.get(0);
		Element data = tableRows.get(1);

		Elements headings = header.getElementsByTag("td");
		Elements datas = data.getElementsByTag("td");
		for (int i = 0; i < headings.size(); i++) {
			jsonObject.put(headings.get(i).text(), datas.get(i).text());
		}
		return jsonObject;
	}

	private List<JSONObject> getElementToJsonMultiple(Element e) {
		List<JSONObject> rows = new ArrayList<>();
		Elements tableRows = e.getElementsByTag("tr");
		Element header = tableRows.get(0);
		Elements headings = header.getElementsByTag("td");
		for(int j = 1; j < tableRows.size(); j++) {
			JSONObject jsonObject = new JSONObject();
			Elements datas = tableRows.get(j).getElementsByTag("td");
			for (int i = 0; i < headings.size(); i++) {
				jsonObject.put(headings.get(i).text(), datas.get(i).text());
			}
			rows.add(jsonObject);
		}
		return rows;
	}

	public Invoice(Document doc) {
		Elements divs = doc.getElementsByTag("div");

		boolean sameAsBill = false;
		for (Element e : divs) {
			if (e.getElementsByTag("h1").get(0).text().equals("invoiceHeader")) {
				JSONObject header = getElementToJson(e);
				this.header = new Header(header);
				this.shipData = new ShippingData(header);
				this.notes = (String) header.get("notes");
				sameAsBill = header.get("sameAsBilling").equals("TRUE");
			}
			if (e.getElementsByTag("h1").get(0).text().equals("billTo")) {
				JSONObject address = getElementToJson(e);
				JSONObject name = new JSONObject();
				name.put("title", address.get("title"));
				name.put("first", address.get("first"));
				name.put("last", address.get("last"));
				address.put("name", name);
				this.billTo = new Address(address);
			}

			if (e.getElementsByTag("h1").get(0).text().equals("rowData")) {
				List<JSONObject> dataRows = getElementToJsonMultiple(e);
				for (JSONObject jsonObject : dataRows) {
					this.rows.add(new InvoiceRow(jsonObject));
				}
			}
		}

		if(sameAsBill) {
			this.shipTo = this.billTo;
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


	private PDPageContentStream newPage(PDDocument pdfDocument, PDPageContentStream contents, int rowY, int numRows) throws IOException {
		contents.close();
		PDPage pdfPage = new PDPage();
		pdfDocument.addPage(pdfPage);		
		contents = new PDPageContentStream(pdfDocument, pdfPage);
		this.header.printPDF(pdfDocument, contents);
		printRowHeader(contents, rowY);
		printRowBackGround(contents, rowY-21, numRows);		
		return contents;
	}


	private boolean newPageRequired(int numPrintedRows, int rowsLeft) {
		if(numPrintedRows >= this.maxRowSize) return true;
		if(this.maxPageWithSummation < rowsLeft && rowsLeft < this.maxRowSize) {
			if(numPrintedRows >= this.breakPoint) return true;
		}
		return false;
	}

	public void printPDF(PDDocument pdfDocument, PDPageContentStream contents) throws IOException {
		this.header.printPDF(pdfDocument, contents);
		this.shipTo.printPDF(contents, false);
		this.billTo.printPDF(contents, true);
		this.shipData.printPDF(contents);

		int rowY = 520;
		int numPrintedRows = 0;

		int rowsLeft = rows.size();

		printRowHeader(contents, rowY);
		printRowBackGround(contents, rowY-21, 
			rowsLeft < this.maxPageWithSummation ? this.maxPageWithSummation : this.maxRowSize
			);

		BigDecimal totalCost = BigDecimal.ZERO;
		for (InvoiceRow invoiceRow : rows) {	
			numPrintedRows++;
			rowY -= 20;
			invoiceRow.printPDF(contents, rowY);
			totalCost = invoiceRow.addTotal(totalCost);
			if(newPageRequired(numPrintedRows, rowsLeft)) {
				rowsLeft -= numPrintedRows;
				numPrintedRows = 0;
				maxRowSize = 30;
				maxPageWithSummation = 23;
				breakPoint = 18;
				rowY = 660;
				contents = newPage(pdfDocument, contents, rowY,
					rowsLeft < this.maxPageWithSummation ? this.maxPageWithSummation : this.maxRowSize
					);

			}
		}		

/*
		First page with summation 16 rows
		First page without summation 24 rows
		Next page with summation 23 rows
		Next page without summation 31 rows

		16-24 = 12		
		24-31 = 18
*/
		printSummery(contents, totalCost);
		printFooter(contents);
		contents.close();
	}

	public void printSummery(PDPageContentStream contents, BigDecimal totalCost) throws IOException {
        Color strokeColor = new Color(100, 100, 100);
        contents.setStrokingColor(strokeColor);
        Color fillColor = new Color(240, 240, 240);
        contents.setNonStrokingColor(fillColor);        

        PDFPrinter summeryLabelPrinter = new PDFPrinter(contents, PDType1Font.HELVETICA_BOLD, 8);
        PDFPrinter summeryValuePrinter = new PDFPrinter(contents, PDType1Font.HELVETICA, 12);

    	BigDecimal subTotal = totalCost.multiply(new BigDecimal(0.8f));
    	BigDecimal vatValue = totalCost.multiply(new BigDecimal(0.2f));
    	subTotal = subTotal.setScale(2, RoundingMode.HALF_EVEN);    	
    	vatValue = vatValue.setScale(2, RoundingMode.HALF_EVEN);
    	totalCost = totalCost.setScale(2, RoundingMode.HALF_EVEN);

    	int summeryStartY = 171;

		summeryLabelPrinter.putText(451, summeryStartY, "Sub total");
        contents.addRect(450, summeryStartY-17, 120, 16);
        contents.stroke();
        summeryValuePrinter.putTextToTheRight(566, summeryStartY-13, subTotal.toString() + " SEK");

		summeryLabelPrinter.putText(451, summeryStartY - 30, "Vat");
        contents.addRect(450, summeryStartY - 30 - 17, 120, 16);
        contents.stroke();
        summeryValuePrinter.putTextToTheRight(566, summeryStartY - 30 - 13, vatValue.toString() + " SEK");

		summeryLabelPrinter.putText(451, summeryStartY - 60, "Total price");
        contents.addRect(450, summeryStartY - 60 - 17, 120, 16);
        contents.stroke();
        summeryValuePrinter.putTextToTheRight(566, summeryStartY - 60 - 13, totalCost.toString() + " SEK");        
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
	
	public void printRowHeader(PDPageContentStream contents, int headerY) throws IOException {
        Color fillColor = new Color(230, 230, 230);
        Color strokeColor = new Color(100, 100, 100);
        contents.setStrokingColor(strokeColor);
        contents.setNonStrokingColor(fillColor);
        contents.addRect(50, headerY, 520, 20);
        contents.fillAndStroke();

        PDFont font = PDType1Font.HELVETICA;
        PDFPrinter headerPrinter = new PDFPrinter(contents, font, 12);
        headerPrinter.putText(60, headerY+7, "Product no.");
        headerPrinter.putText(160, headerY+7, "Description");
        headerPrinter.putText(380, headerY+7, "Quantity");
        headerPrinter.putText(440, headerY+7, "Unit price");
        headerPrinter.putText(510, headerY+7, "Total");
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
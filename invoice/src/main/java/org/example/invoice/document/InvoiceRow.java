package org.example.invoice.document;

import java.math.BigDecimal;
import org.json.simple.JSONObject;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.example.invoice.PDFPrinter;

public class InvoiceRow {
	private String productNumber;
	private String productDescription;
	private double quantity;
	private BigDecimal price;

    public InvoiceRow(JSONObject jsonInvoiceRow) {
        if(jsonInvoiceRow.containsKey("productNumber")) {
            this.setProductNumber((String)jsonInvoiceRow.get("productNumber"));
        }
        if(jsonInvoiceRow.containsKey("productDescription")) {
            this.setProductDescription((String)jsonInvoiceRow.get("productDescription"));
        }
        if(jsonInvoiceRow.containsKey("quantity")) {
            this.setQuantity((String)jsonInvoiceRow.get("quantity"));
        }
        if(jsonInvoiceRow.containsKey("price")) {
            this.setPrice((String)jsonInvoiceRow.get("price"));
        }
    }


	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}
	public String getProductNumber() {
		return this.productNumber;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public String getProductDescription() {
		return this.productDescription;
	}
	public void setQuantity(String quantity) {		
		this.quantity = Double.parseDouble(quantity);
	}	
	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}
	public double getQuantity() {
		return this.quantity;
	}
	public void setPrice(double price) {
		this.price = new BigDecimal(price);
	}
	public void setPrice(String price) {		
		this.price = new BigDecimal(price);
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getPrice() {
		return this.price;
	}
}
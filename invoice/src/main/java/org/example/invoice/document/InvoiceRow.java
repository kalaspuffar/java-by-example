package org.example.invoice.document;

import java.math.BigDecimal;
import org.json.simple.JSONObject;

public class InvoiceRow {
	private String productNumber;
	private String productDescription;
	private double quantity;
	private BigDecimal price;

    public InvoiceRow(JSONObject jsonInvoiceRow) {

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
package org.example.invoice.document;

import java.util.Date;

public class Header {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private Date invoiceDate;
	private String invoiceNumber;

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = sdf.parse(invoiceDate);
    }
    public String getInvoiceDate() {
        return sdf.format(this.invoiceDate);
    }
    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
    public Date getInvoiceDate() {
        return this.invoiceDate;
    }
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }
}
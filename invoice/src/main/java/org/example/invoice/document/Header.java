package org.example.invoice.document;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import org.json.simple.JSONObject;

public class Header {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private Date invoiceDate;
	private String invoiceNumber;

    public Header(JSONObject jsonHeader) {

    }


    public void setInvoiceDate(String invoiceDate) {
    	try {
    		this.invoiceDate = sdf.parse(invoiceDate);
    	} catch(ParseException pe) {
    		pe.printStackTrace();
    		this.invoiceDate = null;
    	}        
    }
    public String getInvoiceDateString() {
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
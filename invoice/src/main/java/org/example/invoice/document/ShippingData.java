package org.example.invoice.document;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import org.json.simple.JSONObject;

public class ShippingData {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private String shipNumber;
	private String salesRep;
	private Date shipDate;
	private String shipVia;
	private String terms;
	private Date dueDate;

    public ShippingData(JSONObject jsonShippingData) {
        if(jsonShippingData.containsKey("shipNumber")) {
            this.setShipNumber((String)jsonShippingData.get("shipNumber"));
        }
        if(jsonShippingData.containsKey("salesRep")) {
            this.setSalesRep((String)jsonShippingData.get("salesRep"));
        }
        if(jsonShippingData.containsKey("shipDate")) {
            this.setShipDate((String)jsonShippingData.get("shipDate"));
        }
        if(jsonShippingData.containsKey("shipVia")) {
            this.setShipVia((String)jsonShippingData.get("shipVia"));
        }
        if(jsonShippingData.containsKey("terms")) {
            this.setTerms((String)jsonShippingData.get("terms"));
        }
        if(jsonShippingData.containsKey("dueDate")) {
            this.setDueDate((String)jsonShippingData.get("dueDate"));
        }
    }

    public void setShipNumber(String shipNumber) {
        this.shipNumber = shipNumber;
    }
    public String getShipNumber() {
        return this.shipNumber;
    }
    public void setSalesRep(String salesRep) {
        this.salesRep = salesRep;
    }
    public String getSalesRep() {
        return this.salesRep;
    }
    public void setShipDate(String shipDate) {
    	try {
        	this.shipDate = sdf.parse(shipDate);
    	} catch(ParseException pe) {
    		pe.printStackTrace();
    		this.shipDate = null;
    	}
    }
    public String getShipDateString() {
        return sdf.format(this.shipDate);
    }
    public void setShipDate(Date shipDate) {
        this.shipDate = shipDate;
    }
    public Date getShipDate() {
        return this.shipDate;
    }
    public void setShipVia(String shipVia) {
        this.shipVia = shipVia;
    }
    public String getShipVia() {
        return this.shipVia;
    }
    public void setTerms(String terms) {
        this.terms = terms;
    }
    public String getTerms() {
        return this.terms;
    }
    public void setDueDate(String dueDate) {
    	try {
        	this.dueDate = sdf.parse(dueDate);
    	} catch(ParseException pe) {
    		pe.printStackTrace();
    		this.dueDate = null;
    	}
    }
    public String getDueDateString() {
        return sdf.format(this.dueDate);
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    public Date getDueDate() {
        return this.dueDate;
    }
}
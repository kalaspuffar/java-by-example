package org.example.invoice.document;

import java.util.Date;

public class ShippingData {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	private String shipNumber;
	private String salesRep;
	private Date shipDate;
	private String shipVia;
	private String terms;
	private Date dueDate;

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
        this.shipDate = sdf.parse(shipDate);
    }
    public String getShipDate() {
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
        this.dueDate = sdf.parse(dueDate);
    }
    public String getDueDate() {
        return sdf.format(this.dueDate);
    }
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }
    public Date getDueDate() {
        return this.dueDate;
    }
}
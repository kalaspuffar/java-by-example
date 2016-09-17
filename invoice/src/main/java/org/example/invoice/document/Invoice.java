package org.example.invoice.document;

import java.util.*;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

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
		this.shipTo = new Address(getJsonObjectFromDocument(doc, "shipTo"));
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
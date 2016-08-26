package org.example.invoice.document;

import java.util.*;

public class Invoice {
	private Address shipTo = null;
	private Address billTo = null;
	private List<InvoiceRow> rows = new ArrayList<InvoiceRow>();
	private ShippingData shipData = null;
	private String notes;
}
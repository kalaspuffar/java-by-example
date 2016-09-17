package org.example.invoice.document;

import org.json.simple.JSONObject;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.example.invoice.PDFPrinter;

public class Address {
	private String title;
	private String first;
	private String last;
	private String address1;
	private String address2;
	private String address3;
	private String city;
	private String state;
	private String zipCode;
	private String country;

	public Address(JSONObject jsonAddress) {
        if(jsonAddress.containsKey("name")) {
        	if(jsonAddress.get("name") instanceof JSONObject) {
        		JSONObject jsonName = (JSONObject) jsonAddress.get("name");
		        if(jsonName.containsKey("title")) {
		            this.setTitle((String)jsonName.get("title"));
		        }
		        if(jsonName.containsKey("first")) {
		            this.setFirst((String)jsonName.get("first"));
		        }
		        if(jsonName.containsKey("last")) {
		            this.setLast((String)jsonName.get("last"));
		        }        		
        	}
	    }
        if(jsonAddress.containsKey("address1")) {
            this.setAddress1((String)jsonAddress.get("address1"));
        }
        if(jsonAddress.containsKey("address2")) {
            this.setAddress2((String)jsonAddress.get("address2"));
        }
        if(jsonAddress.containsKey("address3")) {
            this.setAddress3((String)jsonAddress.get("address3"));
        }
        if(jsonAddress.containsKey("city")) {
            this.setCity((String)jsonAddress.get("city"));
        }
        if(jsonAddress.containsKey("state")) {
            this.setState((String)jsonAddress.get("state"));
        }
        if(jsonAddress.containsKey("zipCode")) {
            this.setZipCode((String)jsonAddress.get("zipCode"));
        }
        if(jsonAddress.containsKey("country")) {
            this.setCountry((String)jsonAddress.get("country"));
        }
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return this.title;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getFirst() {
		return this.first;
	}
	public void setLast(String last) {
		this.last = last;
	}
	public String getLast() {
		return this.last;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress1() {
		return this.address1;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress2() {
		return this.address2;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getAddress3() {
		return this.address3;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCity() {
		return this.city;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getState() {
		return this.state;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getZipCode() {
		return this.zipCode;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCountry() {
		return this.country;
	}
}
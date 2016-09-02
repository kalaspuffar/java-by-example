package org.example.invoice;

import org.json.simple.JSONValue;
import org.json.simple.JSONObject;
import java.io.*;
import java.util.*;
import org.example.invoice.document.*;

public class App 
{
    public static void main( String[] args )
    {
    	try {
	    	JSONObject doc = 
	    		(JSONObject)JSONValue
	    			.parse(new FileReader(new File("single.json")));

	    	Invoice invoice = new Invoice(doc);

            System.out.println(invoice.getNotes());

    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}

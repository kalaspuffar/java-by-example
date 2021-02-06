package org.example.invoice;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BasicContentHandlerFactory;
import org.json.simple.JSONValue;
import org.json.simple.JSONObject;
import java.io.*;
import org.example.invoice.document.*;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.ContentHandler;

public class App 
{
    public static void main( String[] args )
    {
        PDDocument pdfDocument = new PDDocument();
    	try {
            String xlsHtml = parseXLS("invoicedata.xlsx");

            Document doc = Jsoup.parse(xlsHtml);
            Invoice invoice = new Invoice(doc);

/*
	    	JSONObject jsonDocument =
	    		(JSONObject)JSONValue
	    			.parse(new FileReader(new File("afew.json")));

	    	Invoice invoice = new Invoice(jsonDocument);
*/
            PDPage pdfPage = new PDPage();
            pdfDocument.addPage(pdfPage);
            PDPageContentStream contents = new PDPageContentStream(pdfDocument, pdfPage);

            invoice.printPDF(pdfDocument, contents);            

            pdfDocument.save("single-invoice.pdf");
    	} catch (Exception e) {
    		e.printStackTrace();
        } finally {
            try {
                pdfDocument.close();    
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

    public static String parseXLS(String filename) {
        try {
            File docFile = new File(filename);
            BasicContentHandlerFactory basicHandlerFactory =
                    new BasicContentHandlerFactory(BasicContentHandlerFactory.HANDLER_TYPE.XML, -1);
            AutoDetectParser parser = new AutoDetectParser();
            ContentHandler handler = basicHandlerFactory.getNewContentHandler();
            Metadata metadata = new Metadata();
            ParseContext context = new ParseContext();
            context.set(Parser.class, parser);
            InputStream stream = new FileInputStream(docFile);
            parser.parse(stream, handler, metadata, context);
            return handler.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

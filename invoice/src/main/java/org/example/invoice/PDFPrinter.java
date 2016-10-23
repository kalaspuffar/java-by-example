package org.example.invoice;

import java.awt.Color;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import java.io.IOException;

public class PDFPrinter {
	private PDPageContentStream contents;
	private PDFont font;
	private int fontSize;
	private Color color;

	public PDFPrinter(PDPageContentStream contents, PDFont font, int fontSize) {		
		this(contents, font, fontSize, Color.BLACK);
	}	

	public PDFPrinter(PDPageContentStream contents, PDFont font, int fontSize, Color color) {		
		this.contents = contents;
		this.font = font;
		this.fontSize = fontSize;
		this.color = color;
	}	

	public void putText(int x, int y, String text) throws IOException {
		contents.setNonStrokingColor(color);
        contents.beginText();
        contents.setFont(font, fontSize);
        contents.newLineAtOffset(x, y);
        contents.showText(text);
        contents.endText();		
	}

	public int widthOfText(String text) throws IOException {
		return (int)Math.round((font.getStringWidth(text) / 1000f) * this.fontSize);
	}

	public void putTextToTheRight(int x, int y, String text) throws IOException {
		this.putText(x - widthOfText(text), y, text);
	}
}
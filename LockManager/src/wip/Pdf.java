package wip;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Pdf {
	public static void createLockPDF(String path, String date, String parameters, Lock[] data) {
		Document document = new Document();

		try	{
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
			document.open();
			
			//Set attributes here
			document.addAuthor("Lock Manager");
			document.addCreationDate();
			document.addCreator("Lock Manager");
			
			Paragraph dateP = new Paragraph("Created: " + date);
			dateP.setAlignment(4);
			document.add(dateP);
			
			document.add(new Paragraph("Lock Info"));

			document.add(new Paragraph(parameters));
			
			PdfPTable table = new PdfPTable(6); // 6 columns.
			table.setWidthPercentage(100); //Width 100%
			table.setSpacingBefore(10f); //Space before table
			table.setSpacingAfter(10f); //Space after table


			//Set Column widths
			float[] columnWidths = {3f, 2f, 3f, 3f, 3f, 3f};
			table.setWidths(columnWidths);

			PdfPCell headSerial = new PdfPCell(new Paragraph("Serial"));
			headSerial.setPaddingLeft(10);
			headSerial.setHorizontalAlignment(Element.ALIGN_CENTER);
			headSerial.setVerticalAlignment(Element.ALIGN_MIDDLE);

			PdfPCell headCombo = new PdfPCell(new Paragraph("Combo"));
			headCombo.setHorizontalAlignment(Element.ALIGN_CENTER);
			headCombo.setVerticalAlignment(Element.ALIGN_MIDDLE);

			PdfPCell headBarcode = new PdfPCell(new Paragraph("Barcode"));
			headBarcode.setPaddingLeft(10);
			headBarcode.setHorizontalAlignment(Element.ALIGN_CENTER);
			headBarcode.setVerticalAlignment(Element.ALIGN_MIDDLE);

			PdfPCell headYearAdded = new PdfPCell(new Paragraph("YearAdded"));
			headYearAdded.setPaddingLeft(10);
			headYearAdded.setHorizontalAlignment(Element.ALIGN_CENTER);
			headYearAdded.setVerticalAlignment(Element.ALIGN_MIDDLE);

			PdfPCell headYearLastUsed = new PdfPCell(new Paragraph("YearLastUsed"));
			headYearLastUsed.setPaddingLeft(10);
			headYearLastUsed.setHorizontalAlignment(Element.ALIGN_CENTER);
			headYearLastUsed.setVerticalAlignment(Element.ALIGN_MIDDLE);

			PdfPCell headTotalUses = new PdfPCell(new Paragraph("TotalUses"));
			headTotalUses.setPaddingLeft(10);
			headTotalUses.setHorizontalAlignment(Element.ALIGN_CENTER);
			headTotalUses.setVerticalAlignment(Element.ALIGN_MIDDLE);

			table.addCell(headSerial);
			table.addCell(headCombo);
			table.addCell(headBarcode);
			table.addCell(headYearAdded);
			table.addCell(headYearLastUsed);
			table.addCell(headTotalUses);

			for(int i = 0; i < data.length; i++) {
				PdfPCell dataBox = new PdfPCell(new Paragraph("TotalUses"));
				dataBox.setPaddingLeft(10);
				dataBox.setHorizontalAlignment(Element.ALIGN_CENTER);
				dataBox.setVerticalAlignment(Element.ALIGN_MIDDLE);


				dataBox.setPhrase(new Paragraph("" + data[i].getSerial()));
				table.addCell(dataBox);
				dataBox.setPhrase(new Paragraph(data[i].getCombo()));
				table.addCell(dataBox);
				dataBox.setPhrase(new Paragraph("" + data[i].getBarcode()));
				table.addCell(dataBox);
				dataBox.setPhrase(new Paragraph("" + data[i].getYearAdded()));
				table.addCell(dataBox);
				dataBox.setPhrase(new Paragraph("" + data[i].getYearLastUsed()));
				table.addCell(dataBox);
				dataBox.setPhrase(new Paragraph("" + data[i].getTotalUses()));
				table.addCell(dataBox);
			}

			document.add(table);

			document.close();
			writer.close();
		} catch (DocumentException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}


	public static void createLockerPDF(String path, String date, String parameters, String[][] data) {
		Document document = new Document();

		try	{
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));
			document.open();
			document.add(new Paragraph("Locker Info"));

			document.add(new Paragraph(parameters));
			
			//Set attributes here
			document.addAuthor("Lock Manager");
			document.addCreationDate();
			document.addCreator("Lock Manager");

			Paragraph dateP = new Paragraph("Created: " + date);
			dateP.setAlignment(4);
			document.add(dateP);
			
			PdfPTable table = new PdfPTable(2); // 2 columns.
			table.setWidthPercentage(100); //Width 100%
			table.setSpacingBefore(10f); //Space before table
			table.setSpacingAfter(10f); //Space after table

			//Set Column widths
			float[] columnWidths = {1f, 1f};
			table.setWidths(columnWidths);

			PdfPCell headLocker = new PdfPCell(new Paragraph("Locker"));
			headLocker.setPaddingLeft(10);
			headLocker.setHorizontalAlignment(Element.ALIGN_CENTER);
			headLocker.setVerticalAlignment(Element.ALIGN_MIDDLE);

			PdfPCell headSerial = new PdfPCell(new Paragraph("Serial"));
			headSerial.setPaddingLeft(10);
			headSerial.setHorizontalAlignment(Element.ALIGN_CENTER);
			headSerial.setVerticalAlignment(Element.ALIGN_MIDDLE);

			table.addCell(headLocker);
			table.addCell(headSerial);

			for(int i = 0; i < data.length; i++) {
				table.addCell(data[i][0]);
				table.addCell(data[i][1]);
			}

			document.add(table);

			document.close();
			writer.close();
		} catch (DocumentException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}

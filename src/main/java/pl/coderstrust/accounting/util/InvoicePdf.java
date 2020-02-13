package pl.coderstrust.accounting.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.coderstrust.accounting.model.Company;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;
import pl.coderstrust.accounting.model.Vat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.List;

public class InvoicePdf {

    private static final Logger logger = LoggerFactory.getLogger(InvoicePdf.class);

    public static ByteArrayInputStream invoicesReport(Invoice invoice) {

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        headFont.setSize(14);
        Paragraph paragraph = new Paragraph(" ");
        paragraph.setSpacingAfter(50);

        try {
            PdfPTable headTable = headTable(invoice, headFont);
            PdfPTable titleTable = titleTable(invoice, headFont);
            PdfPTable table = bodyTable(invoice, headFont);

            PdfWriter.getInstance(document, out);
            document.open();
            document.add(headTable);
            document.add(paragraph);
            document.add(titleTable);
            document.add(paragraph);
            document.add(table);
            document.close();

        } catch (DocumentException ex) {
            logger.error("Error occurred: {0}", ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }

    public static PdfPTable bodyTable(Invoice invoice, Font headFont) throws DocumentException {
        logger.info("Create body table of Invoice");
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setWidths(new int[] {2, 7, 3, 3, 3, 3});

        PdfPCell hcell;
        hcell = new PdfPCell(new Phrase("Id", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Description", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Vat rate", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Net price", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Vat value", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        hcell = new PdfPCell(new Phrase("Price inc. VAT", headFont));
        hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(hcell);

        List<InvoiceEntry> entries = invoice.getEntries();
        String description = entries.get(0).getDescription();
        BigDecimal price = entries.get(0).getPrice();
        Vat vatRate = entries.get(0).getVatRate();
        int vatValue = entries.get(0).getVatValue();
        BigDecimal vatValueBig = BigDecimal.valueOf(vatValue);
        BigDecimal netValue = price.subtract(vatValueBig);

        PdfPCell cell;
        cell = new PdfPCell(new Phrase(invoice.getId().toString()));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(description));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(vatRate.toString()));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(String.valueOf(netValue)));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(String.valueOf(vatValue)));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase(price.toString()));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
        return table;
    }

    public static PdfPTable titleTable(Invoice invoice, Font headFont) throws DocumentException {
        logger.info("Create title table of Invoice");
        PdfPTable titleTable = new PdfPTable(1);
        titleTable.setWidthPercentage(100);
        titleTable.setWidths(new int[] {10});
        titleTable.getSpacingAfter();

        PdfPCell titleCell;
        titleCell = new PdfPCell(new Phrase("Invoice No " + invoice.getId().toString(), headFont));
        titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        titleCell.setBorder(Rectangle.NO_BORDER);
        titleTable.addCell(titleCell);
        return titleTable;
    }

    public static PdfPTable headTable(Invoice invoice, Font headFont) throws DocumentException {
        logger.info("Create head table of Invoice");
        PdfPTable headTable = new PdfPTable(3);
        headTable.setWidthPercentage(100);
        headTable.setWidths(new int[] {6, 6, 3});

        PdfPCell headHcell;
        headHcell = new PdfPCell(new Phrase("Seller", headFont));
        headHcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headHcell.setBorder(Rectangle.NO_BORDER);
        headTable.addCell(headHcell);

        headHcell = new PdfPCell(new Phrase("Buyer", headFont));
        headHcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headHcell.setBorder(Rectangle.NO_BORDER);
        headTable.addCell(headHcell);

        headHcell = new PdfPCell(new Phrase("Date", headFont));
        headHcell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headHcell.setBorder(Rectangle.NO_BORDER);
        headTable.addCell(headHcell);

        Company seller = invoice.getSeller();
        Company buyer = invoice.getSeller();

        PdfPCell headCell;
        String sellerCell = seller.getName() + "\r\n" + seller.getAddress() + "\r\n" + seller.getTin();
        headCell = new PdfPCell(new Phrase(sellerCell));
        headCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        headCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headCell.setBorder(Rectangle.NO_BORDER);
        headTable.addCell(headCell);

        String buyerCell = buyer.getName() + "\r\n" + buyer.getAddress() + "\r\n" + buyer.getTin();
        headCell = new PdfPCell(new Phrase(buyerCell));
        headCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        headCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headCell.setBorder(Rectangle.NO_BORDER);
        headTable.addCell(headCell);

        headCell = new PdfPCell(new Phrase(invoice.getDate().toString()));
        headCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        headCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headCell.setBorder(Rectangle.NO_BORDER);
        headTable.addCell(headCell);
        return headTable;
    }

}

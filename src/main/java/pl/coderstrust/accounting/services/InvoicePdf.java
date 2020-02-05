package pl.coderstrust.accounting.services;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfOutputIntent;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.zugferd.InvoiceDOM;
import com.itextpdf.zugferd.ZugferdConformanceLevel;
import com.itextpdf.zugferd.ZugferdDocument;
import com.itextpdf.zugferd.profiles.IBasicProfile;
import org.apache.tomcat.util.json.ParseException;
import org.dom4j.Text;
import org.xml.sax.SAXException;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;

import javax.lang.model.element.Element;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class InvoicePdf {

    public static final String DEST = "results/zugferd/pdf/basic%05d.pdf";
    public static final String ICC = "resources/color/sRGB_CS_profile.icm";
    public static final String REGULAR = "resources/fonts/OpenSans-Regular.ttf";
    public static final String BOLD = "resources/fonts/OpenSans-Bold.ttf";
    public static final String NEWLINE = "\n";

    public void createPdf(Invoice invoice)
        throws ParserConfigurationException, SAXException, TransformerException,
        IOException, ParseException {

        String dest = String.format(DEST, invoice.getId());

        // Create the XML
        Invoice invoiceData = new Invoice();
        IBasicProfile basic = invoiceData.createBasicProfileData(invoice);
        InvoiceDOM dom = new InvoiceDOM(basic);

        // Create the ZUGFeRD document
        ZugferdDocument pdfDocument = new ZugferdDocument(
            new PdfWriter(dest), ZugferdConformanceLevel.ZUGFeRDBasic,
            new PdfOutputIntent("Custom", "", "https://www.color.org",
                "sRGB IEC61966-2.1", new FileInputStream(ICC)));
        pdfDocument.addFileAttachment(
            "ZUGFeRD invoice", dom.toXML(), "ZUGFeRD-invoice.xml",
            PdfName.ApplicationXml, new PdfDictionary(), PdfName.Alternative);

        // Create the document
        Document document = new Document(pdfDocument);
        document.setFont(PdfFontFactory.createFont(REGULAR, true))
            .setFontSize(12);
        PdfFont bold = PdfFontFactory.createFont(BOLD, true);

        // Add the header
        document.add(
            new Paragraph()
                .setTextAlignment(TextAlignment.RIGHT)
                .setMultipliedLeading(1)
                .add(new Text(String.format("%s %s\n", basic.getName(), basic.getId()))
                    .setFont(bold).setFontSize(14))
                .add(convertDate(basic.getDateTime(), "MMM dd, yyyy")));
        // Add the seller and buyer address
        document.add(getAddressTable(basic, bold));
        // Add the line items
        document.add(getLineItemTable(invoice, bold));
        // Add the grand totals
        document.add(getTotalsTable(
            basic.getTaxBasisTotalAmount(), basic.getTaxTotalAmount(),
            basic.getGrandTotalAmount(), basic.getGrandTotalAmountCurrencyID(),
            basic.getTaxTypeCode(), basic.getTaxApplicablePercent(),
            basic.getTaxBasisAmount(), basic.getTaxCalculatedAmount(),
            basic.getTaxCalculatedAmountCurrencyID(), bold));
        // Add the payment info
        document.add(getPaymentInfo(basic.getPaymentReference(),
            basic.getPaymentMeansPayeeFinancialInstitutionBIC(),
            basic.getPaymentMeansPayeeAccountIBAN()));

        document.close();
    }
}

package pl.coderstrust.accounting.mapper;

import pl.coderstrust.accounting.model.Company;
import pl.coderstrust.accounting.model.InvoiceEntry;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class InvoiceBookMapper implements Serializable {

    private static Long id;
    private static LocalDate date;
    private static Company buyer;
    private static Company seller;
    private static List<InvoiceEntry> entries;
    io.spring.guides.gs_producing_web_service.Invoice toSoapInvoice;

    public InvoiceBookMapper() {
    }

    public static io.spring.guides.gs_producing_web_service.Invoice toSoapInvoice
        (pl.coderstrust.accounting.model.Invoice invoice) {

        io.spring.guides.gs_producing_web_service.Invoice invoiceSoap =
            new io.spring.guides.gs_producing_web_service.Invoice();

        id = invoice.getId();
        date = invoice.getDate();
        buyer = invoice.getBuyer();
        seller = invoice.getSeller();
        entries = invoice.getEntries();

        invoiceSoap.setId(id);
//        invoiceSoap.setDate(date);
//        invoiceSoap.setSeller(seller);
//        invoiceSoap.setBuyer(buyer);
//        invoiceSoap.setEntries(entries);

        return invoiceSoap;
    }
}

package pl.coderstrust.accounting.mapper;

import io.spring.guides.gs_producing_web_service.Invoice;

public class InvoiceBookMapper {

    public static io.spring.guides.gs_producing_web_service.Invoice toSoapInvoice (pl.coderstrust.accounting.model.Invoice invoice) {

        Invoice invoice1 = new Invoice();
        
        // new Invoice web service nowy obiekt
        // invoice set wszystkie pola
        return toSoapInvoice(invoice);
    }
}

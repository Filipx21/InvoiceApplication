package pl.coderstrust.accounting.mapper;

import io.spring.guides.gs_producing_web_service.Company;
import io.spring.guides.gs_producing_web_service.Entries;
import org.mapstruct.Mapper;

import javax.xml.datatype.XMLGregorianCalendar;


@Mapper
public class InvoiceBookMapper {

    private Long id;
    private XMLGregorianCalendar date;
    private Company buyer;
    private io.spring.guides.gs_producing_web_service.Company seller;
    private Entries entries;

    public io.spring.guides.gs_producing_web_service.Invoice toSoapInvoice
        (pl.coderstrust.accounting.model.Invoice invoice) {

        io.spring.guides.gs_producing_web_service.Invoice invoiceSoap =
            new io.spring.guides.gs_producing_web_service.Invoice();

        invoiceSoap.setId(invoice.getId());
        invoiceSoap.setId(id);
        invoiceSoap.setDate(date);
        invoiceSoap.setSeller(seller);
        invoiceSoap.setBuyer(buyer);
        invoiceSoap.setEntries(entries);
        
        return invoiceSoap;
    }
}

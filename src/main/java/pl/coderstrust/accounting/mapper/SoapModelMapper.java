package pl.coderstrust.accounting.mapper;

import io.spring.guides.gs_producing_web_service.Entries;
import io.spring.guides.gs_producing_web_service.Entry;
import io.spring.guides.gs_producing_web_service.Vat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.coderstrust.accounting.model.Company;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SoapModelMapper {

    private static io.spring.guides.gs_producing_web_service.Vat Vat;
    private final static Logger log = LoggerFactory.getLogger(SoapModelMapper.class);

    public static io.spring.guides.gs_producing_web_service.Invoice toSoapInvoice
        (pl.coderstrust.accounting.model.Invoice invoice) {

        io.spring.guides.gs_producing_web_service.Invoice invoiceSoap =
            new io.spring.guides.gs_producing_web_service.Invoice();

        if(invoice == null){
            return null;
        }

        Long id = invoice.getId();
        LocalDate date = invoice.getDate();
        Company buyer = invoice.getBuyer();
        Company seller = invoice.getSeller();
        List<InvoiceEntry> entries = invoice.getEntries();

        XMLGregorianCalendar xmlDate = null;
        if(date != null) {
            try {
                xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(date.toString());
            } catch (DatatypeConfigurationException e) {
                e.printStackTrace();
            }
        }


            invoiceSoap.setId(id);
            invoiceSoap.setDate(xmlDate);
            invoiceSoap.setSeller(toXmlCompany(seller));
            invoiceSoap.setBuyer(toXmlCompany(buyer));
            invoiceSoap.setEntries(toEntriesList(entries));

        log.info("Invoice SOAP serialized");
        return invoiceSoap;
    }

    public static Invoice toInvoice(io.spring.guides.gs_producing_web_service.Invoice invoiceSoap)
        throws DatatypeConfigurationException {

        Invoice invoiceModel = new Invoice();
        io.spring.guides.gs_producing_web_service.Invoice invoice
            = new io.spring.guides.gs_producing_web_service.Invoice();

        Long id = invoice.getId();
        XMLGregorianCalendar date = invoice.getDate();
        io.spring.guides.gs_producing_web_service.Company buyer = invoice.getBuyer();
        io.spring.guides.gs_producing_web_service.Company seller = invoice.getSeller();
        Entries entries = invoice.getEntries();

        XMLGregorianCalendar dateModel = DatatypeFactory.newInstance().newXMLGregorianCalendar();
        LocalDate localDate = LocalDate.of(
            dateModel.getYear(),
            dateModel.getMonth(),
            dateModel.getDay());

        if (id != null) {
            invoiceModel.setId(id);
        }
        else
            log.error("ID is null");
            invoiceModel.setId(null);
        if (localDate != null) {
            invoiceModel.setDate(localDate);
        } else
            log.debug("Date is null");
            invoiceModel.setDate(null);
        if (seller != null) {
            invoiceModel.setSeller(toCompanyModel(seller));
        } else
            log.debug("Seller is null");
            invoiceModel.setSeller(null);
        if (buyer != null) {
            invoiceModel.setBuyer(toCompanyModel(buyer));
        } else
            log.debug("Buyer is null");
        invoiceModel.setBuyer(null);
        if(entries != null) {
            invoiceModel.setEntries(toEntriesListModel(entries));
        } else
            log.debug("Entries are null");
            invoiceModel.setEntries(null);

        return invoiceModel;
    }

    private static io.spring.guides.gs_producing_web_service.Company toXmlCompany(Company company) {
        io.spring.guides.gs_producing_web_service.Company soapCompany =
            new io.spring.guides.gs_producing_web_service.Company();

        if(company == null) {
        return null;
        }
        soapCompany.setId(company.getId());
        soapCompany.setTin(company.getTin());
        soapCompany.setAddress(company.getAddress());
        soapCompany.setName(company.getName());
        return soapCompany;
    }

    private static Company toCompanyModel(io.spring.guides.gs_producing_web_service.Company company) {
        Company companyModel = new Company();

        companyModel.setId(company.getId());
        companyModel.setTin(company.getTin());
        companyModel.setAddress(company.getAddress());
        companyModel.setName(company.getName());
        return companyModel;
    }

    private static Entries toEntriesList(List<InvoiceEntry> invoiceEntries) {
        Entries entries = new Entries();
        List<Entry> entryList = invoiceEntries.stream().map(invoiceEntry ->
            toEntry(invoiceEntry)).collect(Collectors.toList());
        entries.getEntriesList().addAll(entryList);
        return entries;
    }

    private static List<InvoiceEntry> toEntriesListModel(Entries invoiceEntries) {
        List<InvoiceEntry> entries = new ArrayList<>();
        Entries entries1 = (Entries) invoiceEntries.getEntriesList();
        entries.stream().map(invoiceEntry -> toEntry(invoiceEntry)).collect(Collectors.toList());
        return entries;
    }

    private static Entry toEntry(InvoiceEntry invoiceEntry) {
        Entry entry = new Entry();
        entry.setDescription(invoiceEntry.getDescription());
        entry.setId(invoiceEntry.getId());
        entry.setPrice(invoiceEntry.getPrice());
        entry.setVatRate(toXmlVat(invoiceEntry.getVatRate()));
        entry.setVatValue(invoiceEntry.getVatValue());
        return entry;
    }

    private static Vat toXmlVat(pl.coderstrust.accounting.model.Vat vat) {
        Vat xmlVat = Vat;
        return xmlVat;
    }
}

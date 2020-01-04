package pl.coderstrust.accounting.mapper;

import io.spring.guides.gs_producing_web_service.Entries;
import io.spring.guides.gs_producing_web_service.Entry;
import io.spring.guides.gs_producing_web_service.GetFindInvoiceByIdResponse;
import io.spring.guides.gs_producing_web_service.Vat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.model.Company;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceBookMapper {

    private static io.spring.guides.gs_producing_web_service.Vat Vat;
    private final InvoiceDatabase invoiceDatabase;
    private final static Logger log = LoggerFactory.getLogger(InvoiceBookMapper.class);

    public InvoiceBookMapper(InvoiceDatabase invoiceDatabase) {
        this.invoiceDatabase = invoiceDatabase;
    }

    public static io.spring.guides.gs_producing_web_service.Invoice toSoapInvoice
        (pl.coderstrust.accounting.model.Invoice invoice) {

        io.spring.guides.gs_producing_web_service.Invoice invoiceSoap =
            new io.spring.guides.gs_producing_web_service.Invoice();

        Long id = invoice.getId();
        LocalDate date = invoice.getDate();
        Company buyer = invoice.getBuyer();
        Company seller = invoice.getSeller();
        List<InvoiceEntry> entries = invoice.getEntries();

        XMLGregorianCalendar xmlDate = null;
        try {
            xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(date.toString());
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }

        invoiceSoap.setId(id);
        invoiceSoap.setDate(xmlDate);
        invoiceSoap.setSeller(toXmlCompany(seller));
        invoiceSoap.setBuyer(toXmlCompany(buyer));
        invoiceSoap.setEntries(toEntriesList(entries));

        log.info("Invoice SOAP serialized");
        return invoiceSoap;
    }

    public static Invoice toInvoice (GetFindInvoiceByIdResponse invoiceSoap)
        throws DatatypeConfigurationException {

        Invoice invoiceModel = new Invoice();

        Long id = invoiceSoap.getId();
        XMLGregorianCalendar date = invoiceSoap.getDate();
        io.spring.guides.gs_producing_web_service.Company buyer = invoiceSoap.getBuyer();
        io.spring.guides.gs_producing_web_service.Company seller = invoiceSoap.getSeller();
        Entries entries = invoiceSoap.getEntries();

        XMLGregorianCalendar dateModel = DatatypeFactory.newInstance().newXMLGregorianCalendar();
            LocalDate localDate = LocalDate.of(
                dateModel.getYear(),
                dateModel.getMonth(),
                dateModel.getDay());

        invoiceModel.setId(id);
        invoiceModel.setDate(localDate);
        invoiceModel.setSeller(toCompanyModel(seller));
        invoiceModel.setBuyer(toCompanyModel(buyer));
        invoiceModel.setEntries(toEntriesListModel(entries));

        return invoiceModel;
    }

    private static io.spring.guides.gs_producing_web_service.Company toXmlCompany (Company company){

        io.spring.guides.gs_producing_web_service.Company soapCompany =
            new io.spring.guides.gs_producing_web_service.Company();

        soapCompany.setId(company.getId());
        soapCompany.setTin(company.getTin());
        soapCompany.setAddress(company.getAddress());
        soapCompany.setName(company.getName());
        return soapCompany;
    }

    private static Company toCompanyModel (io.spring.guides.gs_producing_web_service.Company company){

        Company companyModel = new Company();

        companyModel.setId(company.getId());
        companyModel.setTin(company.getTin());
        companyModel.setAddress(company.getAddress());
        companyModel.setName(company.getName());
        return companyModel;
    }

    private static Entries toEntriesList(List<InvoiceEntry> invoiceEntries){
        Entries entries = new Entries();
        List<Entry> entryList = invoiceEntries.stream().map(invoiceEntry ->
            toEntry(invoiceEntry)).collect(Collectors.toList());
        entries.getEntriesList().addAll(entryList);
        return entries;
    }

    private static List<InvoiceEntry> toEntriesListModel(Entries invoiceEntries){
        List<InvoiceEntry> entries = new ArrayList<>();
        Entries entries1 = (Entries) invoiceEntries.getEntriesList();
        entries.stream().map(invoiceEntry -> toEntry(invoiceEntry)).collect(Collectors.toList());
        return entries;
    }

    private static Entry toEntry (InvoiceEntry invoiceEntry){
        Entry entry = new Entry();
        entry.setDescription(invoiceEntry.getDescription());
        entry.setId(invoiceEntry.getId());
        entry.setPrice(invoiceEntry.getPrice());
        entry.setVatRate(toXmlVat(invoiceEntry.getVatRate()));
        entry.setVatValue(invoiceEntry.getVatValue());
        return entry;
    }

    @XmlElement(name = "vat", required = true)
    private static Vat toXmlVat (pl.coderstrust.accounting.model.Vat vat){
        Vat xmlVat = Vat;

        return xmlVat;
    }
}

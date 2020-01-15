package pl.coderstrust.accounting.mapper;

import ct_invoice_soap.Entries;
import ct_invoice_soap.Entry;
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

    private static ct_invoice_soap.Vat Vat;
    private final static Logger log = LoggerFactory.getLogger(SoapModelMapper.class);

    public static ct_invoice_soap.Invoice toSoapInvoice(Invoice invoice) {

        ct_invoice_soap.Invoice invoiceSoap = new ct_invoice_soap.Invoice();

        if (invoice == null) {
            return null;
        }

        Long id = invoice.getId();
        LocalDate date = invoice.getDate();
        Company buyer = invoice.getBuyer();
        Company seller = invoice.getSeller();
        List<InvoiceEntry> entries = invoice.getEntries();

        XMLGregorianCalendar xmlDate = null;
        if (date != null) {
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

    public static Invoice toInvoice(ct_invoice_soap.Invoice invoiceSoap) {

        Invoice invoiceModel = new Invoice();
        ct_invoice_soap.Invoice invoice = new ct_invoice_soap.Invoice();

        Long id = invoice.getId();
        XMLGregorianCalendar date = invoice.getDate();
        ct_invoice_soap.Company buyer = invoice.getBuyer();
        ct_invoice_soap.Company seller = invoice.getSeller();
        Entries entries = invoice.getEntries();

        XMLGregorianCalendar dateModel = null;
        LocalDate localDate = null;
        if (date != null) {
            try {
                dateModel = DatatypeFactory.newInstance().newXMLGregorianCalendar();
                localDate = LocalDate.of(
                    dateModel.getYear(),
                    dateModel.getMonth(),
                    dateModel.getDay());
            } catch (DatatypeConfigurationException e) {
                e.printStackTrace();
            }
        }

        invoiceModel.setId(id);
        invoiceModel.setDate(localDate);
        invoiceModel.setSeller(toCompanyModel(seller));
        invoiceModel.setBuyer(toCompanyModel(buyer));
        invoiceModel.setEntries(toEntriesListModel(entries));

        return invoiceModel;
    }

    private static ct_invoice_soap.Company toXmlCompany(Company company) {
        ct_invoice_soap.Company soapCompany = new ct_invoice_soap.Company();

        if (company == null) {
            return null;
        }
        soapCompany.setId(company.getId());
        soapCompany.setTin(company.getTin());
        soapCompany.setAddress(company.getAddress());
        soapCompany.setName(company.getName());
        return soapCompany;
    }

    private static Company toCompanyModel(ct_invoice_soap.Company company)
        throws NullPointerException{

        if (company == null){
            return null;
        }

        Company companyModel = new Company();

        companyModel.setId(company.getId());
        companyModel.setTin(company.getTin());
        companyModel.setAddress(company.getAddress());
        companyModel.setName(company.getName());
        return companyModel;
    }

    private static Entries toEntriesList(List<InvoiceEntry> invoiceEntries) {
        if (invoiceEntries == null) {
            return null;
        }
        Entries entries = new Entries();
        List<Entry> entryList = invoiceEntries.stream().map(SoapModelMapper::toEntry).
            collect(Collectors.toList());
        entries.getEntriesList().addAll(entryList);
        return entries;
    }

    private static List<InvoiceEntry> toEntriesListModel(Entries invoiceEntries) {
        if (invoiceEntries == null){
            return null;
        }
        List<InvoiceEntry> entries = new ArrayList<>();
        Entries entries1 = (Entries) invoiceEntries.getEntriesList();
        entries.stream().map(invoiceEntry -> toEntry(invoiceEntry)).collect(Collectors.toList());
        return entries;
    }

    private static Entry toEntry(InvoiceEntry invoiceEntry) {
        if (invoiceEntry == null){
            return null;
        }
        Entry entry = new Entry();
        entry.setDescription(invoiceEntry.getDescription());
        entry.setId(invoiceEntry.getId());
        entry.setPrice(invoiceEntry.getPrice());
        entry.setVatRate(toXmlVat(invoiceEntry.getVatRate()));
        entry.setVatValue(invoiceEntry.getVatValue());
        return entry;
    }

    private static ct_invoice_soap.Vat toXmlVat(pl.coderstrust.accounting.model.Vat vat) {

        ct_invoice_soap.Vat vatSoap = Vat;

        return vatSoap;
    }
}

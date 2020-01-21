package pl.coderstrust.accounting.mapper;

import ct_invoice_soap.Entries;
import ct_invoice_soap.Entry;
import ct_invoice_soap.Vat;
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
    private static pl.coderstrust.accounting.model.Vat vatModel;
    private final static Logger log = LoggerFactory.getLogger(SoapModelMapper.class);
    private Invoice invoice;
    private ct_invoice_soap.Invoice invoiceSoap;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SoapModelMapper)) {
            return false;
        }

        SoapModelMapper that = (SoapModelMapper) o;

        if (!invoice.equals(that.invoice)) {
            return false;
        }
        return invoiceSoap.equals(that.invoiceSoap);
    }

    @Override
    public int hashCode() {
        int result = invoice.hashCode();
        result = 31 * result + invoiceSoap.hashCode();
        return result;
    }

    public static ct_invoice_soap.Invoice toSoapInvoice(Invoice invoice) {
        if (invoice == null) {
            return null;
        } else {
            ct_invoice_soap.Invoice invoiceSoap = new ct_invoice_soap.Invoice();
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
    }

    public static Invoice toInvoice(ct_invoice_soap.Invoice invoiceSoap) {
        if (invoiceSoap ==null){
            return null;
        } else {
            Invoice invoiceModel = new Invoice();
            ct_invoice_soap.Invoice invoice = new ct_invoice_soap.Invoice();

            Long id = invoiceSoap.getId();
            XMLGregorianCalendar date = invoiceSoap.getDate();
            ct_invoice_soap.Company buyer = invoiceSoap.getBuyer();
            ct_invoice_soap.Company seller = invoiceSoap.getSeller();
            Entries entries = invoiceSoap.getEntries();

            XMLGregorianCalendar dateModel;
            LocalDate localDate = null;
            if (date != null) {
                //dateModel = DatatypeFactory.newInstance().newXMLGregorianCalendar();
                localDate = LocalDate.of(
                    date.getYear(),
                    date.getMonth(),
                    date.getDay());
            }

            invoiceModel.setId(id);
            invoiceModel.setDate(localDate);
            invoiceModel.setSeller(toCompanyModel(seller));
            invoiceModel.setBuyer(toCompanyModel(buyer));
            invoiceModel.setEntries(toEntriesListModel(entries));

            return invoiceModel;
        }
    }

    private static ct_invoice_soap.Company toXmlCompany(Company company) {
        if (company == null) {
            return null;
        } else {
            ct_invoice_soap.Company soapCompany = new ct_invoice_soap.Company();
            soapCompany.setId(company.getId());
            soapCompany.setTin(company.getTin());
            soapCompany.setAddress(company.getAddress());
            soapCompany.setName(company.getName());
            return soapCompany;
        }
    }

    private static Company toCompanyModel(ct_invoice_soap.Company company)
        throws NullPointerException {
        if (company == null) {
            return null;
        } else {
            Company companyModel = new Company();
            companyModel.setId(company.getId());
            companyModel.setTin(company.getTin());
            companyModel.setAddress(company.getAddress());
            companyModel.setName(company.getName());
            return companyModel;
        }
    }

    private static Entries toEntriesList(List<InvoiceEntry> invoiceEntries) {
        if (invoiceEntries == null) {
            return null;
        } else {
            Entries entries = new Entries();
            List<Entry> entryList = invoiceEntries.stream().map(SoapModelMapper::toEntry).
                collect(Collectors.toList());
            entries.getEntriesList().addAll(entryList);
            return entries;
        }
    }

    private static List<InvoiceEntry> toEntriesListModel(Entries invoiceEntries) {
        if (invoiceEntries == null) {
            return null;
        } else {
            List<InvoiceEntry> entries = new ArrayList<>();
            List<ct_invoice_soap.Entry> entries1 = invoiceEntries.getEntriesList();
            entries.stream().map(invoiceEntry -> toEntry(invoiceEntry)).collect(Collectors.toList());
            return entries;
        }
    }

    private static Entry toEntry(InvoiceEntry invoiceEntry) {
        if (invoiceEntry == null) {
            return null;
        } else {
            Entry entry = new Entry();
            entry.setDescription(invoiceEntry.getDescription());
            entry.setId(invoiceEntry.getId());
            entry.setPrice(invoiceEntry.getPrice());
            entry.setVatRate(toXmlVat(invoiceEntry.getVatRate()));
            entry.setVatValue(invoiceEntry.getVatValue());
            return entry;
        }
    }

    private static InvoiceEntry toInvoiceEntry (Entry entry){
        if (entry == null) {
            return null;
        } else {
            InvoiceEntry invoiceEntry = new InvoiceEntry();
            invoiceEntry.setDescription(entry.getDescription());
            invoiceEntry.setId(entry.getId());
            invoiceEntry.setPrice(entry.getPrice());
            invoiceEntry.setVatRate(toVat(entry.getVatRate()));
            invoiceEntry.setVatValue(entry.getVatValue());
            return invoiceEntry;
        }
    }


    private static ct_invoice_soap.Vat toXmlVat(pl.coderstrust.accounting.model.Vat vat) {
        if (vat == null) {
            return null;
        } else {
            ct_invoice_soap.Vat vatSoap = Vat;
            return vatSoap;
        }
    }

    private static pl.coderstrust.accounting.model.Vat toVat(Vat vat){
        if (vat == null){
            return null;
        } else {
            pl.coderstrust.accounting.model.Vat vatConvertedModel = vatModel;
            return vatConvertedModel;
        }
    }
}

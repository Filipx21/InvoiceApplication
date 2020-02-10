package pl.coderstrust.accounting.mapper;

import ct_invoice_soap.Entries;
import ct_invoice_soap.Entry;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pl.coderstrust.accounting.model.Company;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class SoapModelMapperTest {

    SoapModelMapper soapModelMapper = new SoapModelMapper();

    @Test
    void shouldConvertIdInvoiceToSoapIdInvoice() {
        //given
        Invoice invoice = new Invoice();
        ct_invoice_soap.Invoice invoiceSoap;
        invoice.setId(1L);

        //when
        invoiceSoap = SoapModelMapper.toSoapInvoice(invoice);
        Long idInvoice = invoice.getId();
        Long idActual = invoiceSoap.getId();

        //then
        Assert.assertEquals(idInvoice, idActual);
    }

    @Test
    void shouldConvertDateInvoiceToSoapDateInvoice() {
        //given
        Invoice invoice = new Invoice();
        ct_invoice_soap.Invoice invoiceSoap;
        ct_invoice_soap.Invoice invoiceExpected = new ct_invoice_soap.Invoice();
        invoice.setId(0L);
        invoice.setDate(LocalDate.of(2020,1,8));
        invoiceExpected.setDate(null);

        //when
        invoiceSoap = SoapModelMapper.toSoapInvoice(invoice);
        String dateExpected = invoice.getDate().toString();
        String dateActual = invoiceSoap.getDate().toString();

        //then
        Assert.assertEquals(dateExpected, dateActual);
    }

    @Test
    void shouldConvertCompanyInvoiceToSoapCompanyInvoice() {
        //given
        Invoice invoice = new Invoice();
        ct_invoice_soap.Invoice invoiceSoap;
        ct_invoice_soap.Invoice invoiceExpected = new ct_invoice_soap.Invoice();
        pl.coderstrust.accounting.model.Company seller = new pl.coderstrust.accounting.model.Company();
        ct_invoice_soap.Company sellerExpected = new ct_invoice_soap.Company();
        ct_invoice_soap.Company sellerResult;

        seller.setId(1L);
        seller.setTin("Tin example number");
        seller.setName("Seller example name");
        seller.setAddress("Example seller Address");

        sellerExpected.setId(1L);
        sellerExpected.setTin("Tin example number");
        sellerExpected.setName("Seller example name");
        sellerExpected.setAddress("Example seller Address");

        invoice.setId(0L);
        invoice.setSeller(seller);
        invoiceExpected.setId(0L);
        invoiceExpected.setSeller(sellerExpected);

        //when
        invoiceSoap = SoapModelMapper.toSoapInvoice(invoice);
        sellerResult = invoiceSoap.getSeller();

        Long idExpected = sellerExpected.getId();
        String tinExpected = sellerExpected.getTin();
        String nameExpected = sellerExpected.getName();
        String addressExpected = sellerExpected.getAddress();

        Long idResult = sellerResult.getId();
        String tinResult = sellerResult.getTin();
        String nameResult = sellerResult.getName();
        String addressResult = sellerResult.getAddress();

        //then
        Assert.assertEquals(idExpected, idResult);
        Assert.assertEquals(tinExpected, tinResult);
        Assert.assertEquals(nameExpected, nameResult);
        Assert.assertEquals(addressExpected, addressResult);
    }

    @Test
    void shouldConvertSoapInvoiceToInvoice() throws DatatypeConfigurationException {
        //given
        Invoice invoiceExpected = new Invoice();
        Invoice invoiceResult;
        ct_invoice_soap.Invoice invoiceSoap = new ct_invoice_soap.Invoice();
        XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().
            newXMLGregorianCalendarDate(2000, 3, 4, 0);
        ct_invoice_soap.Company buyer = new ct_invoice_soap.Company();
        buyer.setId(1L);
        buyer.setAddress("Buyer address");
        buyer.setName("Buyer name");
        buyer.setTin("123");
        ct_invoice_soap.Company seller = new ct_invoice_soap.Company();
        seller.setId(1L);
        seller.setAddress("Seller address");
        seller.setName("Seller name");
        seller.setTin("456");
        Entries entries = new Entries();
        List<Entry> listEntrySoap = new ArrayList<>();
        entries.getEntriesList();

        Company buyerModel = new Company();
        buyerModel.setId(1L);
        buyerModel.setAddress("Buyer address");
        buyerModel.setName("Buyer name");
        buyerModel.setTin("123");
        Company sellerModel = new Company();
        sellerModel.setId(1L);
        sellerModel.setAddress("Seller address");
        sellerModel.setName("Seller name");
        sellerModel.setTin("456");
        List<InvoiceEntry> entriesModel = new ArrayList<>();

        invoiceSoap.setId(1L);
        invoiceSoap.setDate(xmlGregorianCalendar);
        invoiceSoap.setBuyer(buyer);
        invoiceSoap.setSeller(seller);
        invoiceSoap.setEntries(entries);

        invoiceExpected.setId(1L);
        invoiceExpected.setDate(LocalDate.of(2000, 3,4));
        invoiceExpected.setBuyer(buyerModel);
        invoiceExpected.setSeller(sellerModel);
        invoiceExpected.setEntries(entriesModel);

        //when
        invoiceResult = SoapModelMapper.toInvoice(invoiceSoap);
        Long idResultSoap = invoiceResult.getId();
        LocalDate dateResultSoap = invoiceResult.getDate();
        Company buyerResultSoap = invoiceResult.getBuyer();
        Company sellerResultSoap = invoiceResult.getSeller();
        List<InvoiceEntry> entriesResultSoap = invoiceResult.getEntries();

        Long idExpected = invoiceExpected.getId();
        LocalDate dateExpected = invoiceExpected.getDate();
        Company buyerExpected = invoiceExpected.getBuyer();
        Company sellerExpected = invoiceExpected.getSeller();
        List<InvoiceEntry> entriesExpected = invoiceExpected.getEntries();

        //then
        Assert.assertEquals(idExpected, idResultSoap);
        Assert.assertEquals(dateExpected, dateResultSoap);
        Assert.assertEquals(buyerExpected, buyerResultSoap);
        Assert.assertEquals(sellerExpected, sellerResultSoap);
        Assert.assertEquals(entriesExpected, entriesResultSoap);
    }

    @Test
    void shouldConvertInvoiceToSoapInvoice() throws DatatypeConfigurationException {
        //given
        Invoice invoiceModel = new Invoice();
        ct_invoice_soap.Invoice invoiceResult;
        ct_invoice_soap.Invoice invoiceExpected = new ct_invoice_soap.Invoice();
        XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().
            newXMLGregorianCalendarDate(2020, 01, 21, 0);

        invoiceExpected.setId(0L);
        invoiceModel.setId(0L);

        //when
        invoiceResult = SoapModelMapper.toSoapInvoice(invoiceModel);


        Long idResultSoap = invoiceResult.getId();
        XMLGregorianCalendar dateResultSoap = invoiceResult.getDate();
        ct_invoice_soap.Company buyerResultSoap = invoiceResult.getBuyer();
        ct_invoice_soap.Company sellerResultSoap = invoiceResult.getSeller();
        List<InvoiceEntry> entriesResultSoap = (List<InvoiceEntry>) invoiceResult.getEntries();

        Long idExpected = invoiceExpected.getId();
        XMLGregorianCalendar dateExpected = invoiceExpected.getDate();
        ct_invoice_soap.Company buyerExpected = invoiceExpected.getBuyer();
        ct_invoice_soap.Company sellerExpected = invoiceExpected.getSeller();
        List<InvoiceEntry> entriesExpected = (List<InvoiceEntry>) invoiceExpected.getEntries();

        //then
        Assert.assertEquals(idExpected, idResultSoap);
        Assert.assertEquals(dateExpected, dateResultSoap);
        Assert.assertEquals(buyerExpected, buyerResultSoap);
        Assert.assertEquals(sellerExpected, sellerResultSoap);
        Assert.assertEquals(entriesExpected, entriesResultSoap);
    }

    private ct_invoice_soap.Invoice prepareInvoice() throws DatatypeConfigurationException {
        Random random = new Random();
        Entries invoiceEntries = new Entries();
        ct_invoice_soap.Company buyer = prepareCompany("Wrocław 66-666", "TurboMarek Sp. z.o.o");
        ct_invoice_soap.Company seller = prepareCompany("Gdynia 66-666", "Szczupak Sp. z.o.o");
        ct_invoice_soap.Invoice invoice = new ct_invoice_soap.Invoice();
        invoice.setId(random.nextLong());
        XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().
            newXMLGregorianCalendarDate(random.nextInt(120) + 1900,
                random.nextInt(12) + 1 ,
                random.nextInt(30) + 1,
                0);
        invoice.setBuyer(buyer);
        invoice.setSeller(seller);
        invoice.setEntries(invoiceEntries);
        return invoice;
    }

    private ct_invoice_soap.Company prepareCompany(String city, String company) {
        Random random = new Random();
        return new ct_invoice_soap.Company();
    }

    private Invoice prepareModelInvoice() {
        Random random = new Random();
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        Company buyer = prepareModelCompany("Wrocław 66-666", "TurboMarek Sp. z.o.o");
        Company seller = prepareModelCompany("Gdynia 66-666", "Szczupak Sp. z.o.o");
        Invoice invoice = new Invoice();
        invoice.setDate(LocalDate.of(
            random.nextInt(120) + 1900,
            random.nextInt(12) + 1,
            random.nextInt(25) + 1));
        invoice.setBuyer(buyer);
        invoice.setSeller(seller);
        invoice.setEntries(invoiceEntries);
        return invoice;
    }

    private Company prepareModelCompany(String city, String company) {
        Random random = new Random();
        return new Company(
            (long) (random.nextInt(10000) + 1),
            (random.nextInt(999999999) + 9999999) + "",
            city,
            company);
    }

    private List<Invoice> prepareModelInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        Invoice invoice1 = prepareModelInvoice();
        invoices.add(invoice1);
        Invoice invoice2 = prepareModelInvoice();
        invoices.add(invoice2);
        Invoice invoice3 = prepareModelInvoice();
        invoices.add(invoice3);
        return invoices;
    }

}

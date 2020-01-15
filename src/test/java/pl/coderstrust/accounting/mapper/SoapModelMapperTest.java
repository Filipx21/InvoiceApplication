package pl.coderstrust.accounting.mapper;

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
        ct-invoice-soap.Invoice invoiceSoap;
        ct-invoice-soap.Invoice invoiceExpected = new ct-invoice-soap.Invoice();
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
        ct-invoice-soap.Invoice invoiceSoap;
        ct-invoice-soap.Invoice invoiceExpected = new ct-invoice-soap.Invoice();
        pl.coderstrust.accounting.model.Company seller = new pl.coderstrust.accounting.model.Company();
        Company sellerExpected = new Company();
        Company sellerResult;

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

        //then
        Assert.assertEquals(sellerExpected, sellerResult);
    }

    @Test
    void shouldConvertSoapInvoiceToInvoice() throws DatatypeConfigurationException {
        //given
        Invoice invoiceExpected = new Invoice();
        Invoice invoiceResult;
        coders_trust.Invoice invoiceSoap = new coders_trust.Invoice();
        XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar("2000-03-04T20:00:00Z");

        invoiceSoap.setId(0L);
        invoiceSoap.setDate(xmlGregorianCalendar);
        invoiceExpected.setId(0L);
        invoiceExpected.setDate(null);

        //when
        invoiceResult = SoapModelMapper.toInvoice(invoiceSoap);

        //then
        Assert.assertEquals(invoiceExpected, invoiceResult);
    }

    @Test
    void shouldConvertInvoiceToSoapInvoice() throws DatatypeConfigurationException {
        //given
        Invoice invoiceModel = new Invoice();
        coders_trust.Invoice invoiceResult;
        coders_trust.Invoice invoiceExpected = new coders_trust.Invoice();

        invoiceExpected.setId(0L);
        invoiceModel.setId(0L);

        //when
        invoiceResult = SoapModelMapper.toSoapInvoice(invoiceModel);

        //then
        Assert.assertEquals(invoiceExpected, invoiceResult);
    }

    private pl.coderstrust.accounting.model.Invoice prepareInvoice() {
        Random random = new Random();
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        pl.coderstrust.accounting.model.Company buyer = prepareCompany("Wrocław 66-666", "TurboMarek z.o.o");
        pl.coderstrust.accounting.model.Company seller = prepareCompany("Gdynia 66-666", "Szczupak z.o.o");
        pl.coderstrust.accounting.model.Invoice invoice = new pl.coderstrust.accounting.model.Invoice();
        invoice.setId(random.nextLong());
        invoice.setDate(LocalDate.of(
            random.nextInt(120) + 1900,
            random.nextInt(12) + 1,
            random.nextInt(25) + 1));
        invoice.setBuyer(buyer);
        invoice.setSeller(seller);
        invoice.setEntries(invoiceEntries);
        return invoice;
    }

    private pl.coderstrust.accounting.model.Company prepareCompany(String city, String company) {
        Random random = new Random();
        return new pl.coderstrust.accounting.model.Company(
            (long) (random.nextInt(10000) + 1),
            (random.nextInt(999999999) + 9999999) + "",
            city,
            company);
    }

}
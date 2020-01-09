package pl.coderstrust.accounting.mapper;

import io.spring.guides.gs_producing_web_service.Company;
import io.spring.guides.gs_producing_web_service.Entries;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;

import javax.xml.datatype.DatatypeConfigurationException;
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
        io.spring.guides.gs_producing_web_service.Invoice invoiceSoap;
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
        io.spring.guides.gs_producing_web_service.Invoice invoiceSoap;
        io.spring.guides.gs_producing_web_service.Invoice invoiceExpected
            = new io.spring.guides.gs_producing_web_service.Invoice();
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
        io.spring.guides.gs_producing_web_service.Invoice invoiceSoap;
        io.spring.guides.gs_producing_web_service.Invoice invoiceExpected
            = new io.spring.guides.gs_producing_web_service.Invoice();
        pl.coderstrust.accounting.model.Company seller = new pl.coderstrust.accounting.model.Company();
        Company sellerSoap = new Company();
        Company sellerExpected;

        seller.setId(1L);
        seller.setTin("Tin example number");
        seller.setName("Seller example name");
        seller.setAddress("Example seller Address");

        sellerSoap.setId(1L);
        sellerSoap.setTin("Tin example number");
        sellerSoap.setName("Seller example name");
        sellerSoap.setAddress("Example seller Address");

        invoice.setId(0L);
        invoice.setSeller(seller);
        invoiceExpected.setId(0L);
        invoiceExpected.setSeller(sellerSoap);

        //when
        invoiceSoap = SoapModelMapper.toSoapInvoice(invoice);
        sellerExpected = invoiceSoap.getSeller();

        //then
        Assert.assertEquals(sellerExpected, sellerSoap);
    }

    @Test
    void shouldConvertSoapInvoiceToInvoice() throws DatatypeConfigurationException {
        Invoice invoiceModel = new Invoice();
        Invoice invoiceResult = new Invoice();
        io.spring.guides.gs_producing_web_service.Invoice invoiceSoap
            = new io.spring.guides.gs_producing_web_service.Invoice();

        invoiceSoap.setId(1L);
        invoiceSoap.setDate(null);
        invoiceModel.setId(1L);
        invoiceModel.setDate(null);

        invoiceResult = SoapModelMapper.toInvoice(invoiceSoap);

        Assert.assertEquals(invoiceModel, invoiceResult);


    }

    private Invoice prepareInvoice() {
        Random random = new Random();
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        pl.coderstrust.accounting.model.Company buyer = prepareCompany("Wrocław 66-666", "TurboMarek z.o.o");
        pl.coderstrust.accounting.model.Company seller = prepareCompany("Gdynia 66-666", "Szczupak z.o.o");
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

    private pl.coderstrust.accounting.model.Company prepareCompany(String city, String company) {
        Random random = new Random();
        return new pl.coderstrust.accounting.model.Company(
            (long) (random.nextInt(10000) + 1),
            (random.nextInt(999999999) + 9999999) + "",
            city,
            company);
    }

    private List<Invoice> prepareInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        Invoice invoice1 = prepareInvoice();
        invoices.add(invoice1);
        Invoice invoice2 = prepareInvoice();
        invoices.add(invoice2);
        Invoice invoice3 = prepareInvoice();
        invoices.add(invoice3);
        return invoices;
    }

    private io.spring.guides.gs_producing_web_service.Invoice prepareSoapInvoice() {
        Entries invoiceEntries = new Entries();
        io.spring.guides.gs_producing_web_service.Company buyer
            = prepareSoapCompany("Wrocław 66-666", "TurboMarek z.o.o");
        io.spring.guides.gs_producing_web_service.Company seller
            = prepareSoapCompany("Gdynia 66-666", "Szczupak z.o.o");
        io.spring.guides.gs_producing_web_service.Invoice invoice
            = new io.spring.guides.gs_producing_web_service.Invoice();
        invoice.setDate(null);
        invoice.setBuyer(buyer);
        invoice.setSeller(seller);
        invoice.setEntries(invoiceEntries);
        return invoice;
    }

    private io.spring.guides.gs_producing_web_service.Company
    prepareSoapCompany(String city, String company) {
        return new Company();
    }

    private List<io.spring.guides.gs_producing_web_service.Invoice> prepareSoapInvoices() {
        List<io.spring.guides.gs_producing_web_service.Invoice> invoices = new ArrayList<>();
        io.spring.guides.gs_producing_web_service.Invoice invoice1 = prepareSoapInvoice();
        invoices.add(invoice1);
        io.spring.guides.gs_producing_web_service.Invoice invoice2 = prepareSoapInvoice();
        invoices.add(invoice2);
        io.spring.guides.gs_producing_web_service.Invoice invoice3 = prepareSoapInvoice();
        invoices.add(invoice3);
        return invoices;
    }
}
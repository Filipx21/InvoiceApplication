package pl.coderstrust.accounting.controllers;

import coders_trust.FindAllInvoicesRequest;
import coders_trust.FindAllInvoicesResponse;
import coders_trust.FindInvoiceByIdRequest;
import coders_trust.FindInvoiceByIdResponse;
import coders_trust.Invoice;
import coders_trust.SaveInvoiceRequest;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import pl.coderstrust.accounting.config.AppConfiguration;
import pl.coderstrust.accounting.config.WebServiceConfig;
import pl.coderstrust.accounting.mapper.SoapModelMapper;
import pl.coderstrust.accounting.model.Company;
import pl.coderstrust.accounting.model.InvoiceEntry;

import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest(classes = AppConfiguration.class)
@ContextConfiguration(classes= WebServiceConfig.class)
public class InvoicesEndpointTest {

    @Autowired
    private InvoicesEndpoint invoicesEndpoint;

    @Test
    public void shouldThrowsExceptionForEmptyMemoryDatabase() throws IOException {
        // given, when, then
        FindAllInvoicesRequest findAllInvoicesRequest = new FindAllInvoicesRequest();
        assertThrows(NullPointerException.class, () -> {
            invoicesEndpoint.findAllInvoices(findAllInvoicesRequest);
        });
    }

    @Test
    public void shouldSaveInvoiceInMemoryDatabaseAndFindItInDatabaseForReturn() {
        // given
        pl.coderstrust.accounting.model.Invoice invoiceModel = prepareInvoice();;
        Invoice invoiceExpected = SoapModelMapper.toSoapInvoice(invoiceModel);
        SaveInvoiceRequest saveInvoiceRequest = new SaveInvoiceRequest();
        saveInvoiceRequest.setInvoice(invoiceExpected);

        // when
        coders_trust.Invoice invoiceResult = saveInvoiceRequest.getInvoice();
        SoapModelMapper.toInvoice(invoiceResult);

        //then
        Assert.assertEquals(invoiceExpected, invoiceResult);
    }

    @Test
    public void getAllInvoices() throws IOException {
        // given
        SaveInvoiceRequest saveInvoiceRequest = new SaveInvoiceRequest();
        FindAllInvoicesRequest findAllInvoicesRequest = new FindAllInvoicesRequest();
        FindAllInvoicesResponse findAllInvoicesResponse;
        Invoice invoice = new Invoice();
        invoice.setId(1L);

        // when
        saveInvoiceRequest.setInvoice(invoice);
        findAllInvoicesResponse = invoicesEndpoint.findAllInvoices(findAllInvoicesRequest);

        // then
        assertNotNull(findAllInvoicesResponse);
    }

    @Test
    public void getInvoiceById() throws IOException, DatatypeConfigurationException {
        // given
        SaveInvoiceRequest saveInvoiceRequest = new SaveInvoiceRequest();
        Invoice invoiceSoap = new Invoice();
        FindInvoiceByIdRequest findInvoiceByIdRequest = new FindInvoiceByIdRequest();
        FindInvoiceByIdResponse findInvoiceByIdResponse = new FindInvoiceByIdResponse();
        pl.coderstrust.accounting.model.Invoice invoice = new pl.coderstrust.accounting.model.Invoice();
        invoice.setId(1L);
        invoiceSoap = SoapModelMapper.toSoapInvoice(invoice);

        // when
        saveInvoiceRequest.setInvoice(invoiceSoap);

        findInvoiceByIdResponse.setInvoice(invoiceSoap);
        invoicesEndpoint.findInvoiceById(findInvoiceByIdRequest);

        // then
        assertNotNull(findInvoiceByIdResponse);
    }

    private pl.coderstrust.accounting.model.Invoice prepareInvoice() {
        Random random = new Random();
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        pl.coderstrust.accounting.model.Company buyer = prepareCompany("Wrocław 66-666", "TurboMarek z.o.o");
        pl.coderstrust.accounting.model.Company seller = prepareCompany("Gdynia 66-666", "Szczupak z.o.o");
        pl.coderstrust.accounting.model.Invoice invoice = new pl.coderstrust.accounting.model.Invoice();
        invoice.setId(1L);
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
        return new Company(
            (long) (random.nextInt(10000) + 1),
            (random.nextInt(999999999) + 9999999) + "",
            city,
            company);
    }

    private List<pl.coderstrust.accounting.model.Invoice> prepareInvoices() {
        List<pl.coderstrust.accounting.model.Invoice> invoices = new ArrayList<>();
        pl.coderstrust.accounting.model.Invoice invoice1 = prepareInvoice();
        invoices.add(invoice1);
        pl.coderstrust.accounting.model.Invoice invoice2 = prepareInvoice();
        invoices.add(invoice2);
        pl.coderstrust.accounting.model.Invoice invoice3 = prepareInvoice();
        invoices.add(invoice3);
        return invoices;
    }

}

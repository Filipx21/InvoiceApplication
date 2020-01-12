package pl.coderstrust.accounting.controllers;

import coders_trust.Company;
import coders_trust.Entries;
import coders_trust.FindAllInvoicesRequest;
import coders_trust.FindAllInvoicesResponse;
import coders_trust.FindInvoiceByIdRequest;
import coders_trust.FindInvoiceByIdResponse;
import coders_trust.Invoice;
import coders_trust.SaveInvoiceRequest;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import pl.coderstrust.accounting.config.AppConfiguration;
import pl.coderstrust.accounting.config.WebServiceConfig;

import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = AppConfiguration.class)
@ContextConfiguration(classes= WebServiceConfig.class)
public class InvoicesEndpointTest {

    @Autowired
    private InvoicesEndpoint invoicesEndpoint;

    @Test
    public void shouldThrowsExceptionForEmptyMemoryDatabase() throws IOException {
        // Given, When, Then
        FindAllInvoicesRequest findAllInvoicesRequest = new FindAllInvoicesRequest();
        assertThrows(NullPointerException.class, () -> {
            invoicesEndpoint.findAllInvoices(findAllInvoicesRequest);
        });
    }

    @Test
    public void getAllInvoices() throws IOException {
        // Given
        SaveInvoiceRequest saveInvoiceRequest = new SaveInvoiceRequest();
        FindAllInvoicesRequest findAllInvoicesRequest = new FindAllInvoicesRequest();
        FindAllInvoicesResponse findAllInvoicesResponse;
        coders_trust.Invoice invoice = prepareInvoice();
        invoice.setId(1L);

        // When
        saveInvoiceRequest.setInvoice(invoice);
        findAllInvoicesResponse = invoicesEndpoint.findAllInvoices(findAllInvoicesRequest);

        // Then
        assertNotNull(findAllInvoicesResponse);
    }

    @Test
    public void getInvoiceById() throws IOException, DatatypeConfigurationException {
        // Given
        SaveInvoiceRequest saveInvoiceRequest = new SaveInvoiceRequest();
        FindInvoiceByIdRequest findInvoiceByIdRequest = new FindInvoiceByIdRequest();
        FindInvoiceByIdResponse findInvoiceByIdResponse = new FindInvoiceByIdResponse();
        Invoice invoice = prepareInvoice();
        invoice.setId(1L);

        // When
        saveInvoiceRequest.setInvoice(invoice);
        invoicesEndpoint.findInvoiceById(findInvoiceByIdRequest);

        // Then
        assertNotNull(findInvoiceByIdResponse);
    }

    private Invoice prepareInvoice() {
        Entries invoiceEntries = new Entries();
        coders_trust.Company buyer = prepareCompany("Wrocław 66-666", "TurboMarek z.o.o");
        coders_trust.Company seller = prepareCompany("Gdynia 66-666", "Szczupak z.o.o");
        coders_trust.Invoice invoice = new coders_trust.Invoice();
        invoice.setDate(null);
        invoice.setBuyer(buyer);
        invoice.setSeller(seller);
        invoice.setEntries(invoiceEntries);
        return invoice;
    }

    private Company prepareCompany(String city, String company) {
        return new Company();
    }

    private List<coders_trust.Invoice> prepareInvoices() {
        List<coders_trust.Invoice> invoices = new ArrayList<>();
        coders_trust.Invoice invoice1 = prepareInvoice();
        invoices.add(invoice1);
        coders_trust.Invoice invoice2 = prepareInvoice();
        invoices.add(invoice2);
        coders_trust.Invoice invoice3 = prepareInvoice();
        invoices.add(invoice3);
        return invoices;
    }
}

package pl.coderstrust.accounting.controllers;

import io.spring.guides.gs_producing_web_service.Company;
import io.spring.guides.gs_producing_web_service.Entries;
import io.spring.guides.gs_producing_web_service.FindAllInvoicesRequest;
import io.spring.guides.gs_producing_web_service.FindAllInvoicesResponse;
import io.spring.guides.gs_producing_web_service.FindInvoiceByIdRequest;
import io.spring.guides.gs_producing_web_service.FindInvoiceByIdResponse;
import io.spring.guides.gs_producing_web_service.Invoice;
import io.spring.guides.gs_producing_web_service.SaveInvoiceRequest;
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
        FindAllInvoicesResponse findAllInvoicesResponse = new FindAllInvoicesResponse();
        Invoice invoice = prepareInvoice();
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

    private io.spring.guides.gs_producing_web_service.Invoice prepareInvoice() {
        Entries invoiceEntries = new Entries();
        io.spring.guides.gs_producing_web_service.Company buyer = prepareCompany("Wrocław 66-666", "TurboMarek z.o.o");
        io.spring.guides.gs_producing_web_service.Company seller = prepareCompany("Gdynia 66-666", "Szczupak z.o.o");
        io.spring.guides.gs_producing_web_service.Invoice invoice = new io.spring.guides.gs_producing_web_service.Invoice();
        invoice.setDate(null);
        invoice.setBuyer(buyer);
        invoice.setSeller(seller);
        invoice.setEntries(invoiceEntries);
        return invoice;
    }

    private io.spring.guides.gs_producing_web_service.Company prepareCompany(String city, String company) {
        return new Company();
    }

    private List<io.spring.guides.gs_producing_web_service.Invoice> prepareInvoices() {
        List<io.spring.guides.gs_producing_web_service.Invoice> invoices = new ArrayList<>();
        io.spring.guides.gs_producing_web_service.Invoice invoice1 = prepareInvoice();
        invoices.add(invoice1);
        io.spring.guides.gs_producing_web_service.Invoice invoice2 = prepareInvoice();
        invoices.add(invoice2);
        io.spring.guides.gs_producing_web_service.Invoice invoice3 = prepareInvoice();
        invoices.add(invoice3);
        return invoices;
    }
}

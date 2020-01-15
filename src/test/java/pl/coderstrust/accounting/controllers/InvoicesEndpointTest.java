package pl.coderstrust.accounting.controllers;

import coders_trust.FindAllInvoicesRequest;
import coders_trust.FindAllInvoicesResponse;
import coders_trust.FindInvoiceByIdRequest;
import coders_trust.FindInvoiceByIdResponse;
import coders_trust.Invoice;
import coders_trust.Invoices;
import coders_trust.SaveInvoiceRequest;
import coders_trust.SaveInvoiceResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.Assert;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doReturn;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderstrust.accounting.mapper.SoapModelMapper;
import pl.coderstrust.accounting.model.Company;
import pl.coderstrust.accounting.model.InvoiceEntry;
import pl.coderstrust.accounting.services.InvoiceBook;

import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ExtendWith(MockitoExtension.class)
public class InvoicesEndpointTest {

    @Mock
    private InvoiceBook invoiceBook;

    private SoapModelMapper soapModelMapper = new SoapModelMapper();

    @InjectMocks
    private InvoicesEndpoint invoicesEndpoint;

    @Test
    public void shouldThrowsExceptionForEmptyMemoryDatabase() {
        // given, when, then
        FindAllInvoicesRequest findAllInvoicesRequest = new FindAllInvoicesRequest();
        assertThrows(NullPointerException.class, () -> {
            invoicesEndpoint.findAllInvoices(findAllInvoicesRequest);
        });
    }

    @Test
    public void shouldSaveInvoiceFromInvoiceBook() throws IOException, DatatypeConfigurationException {
        // given

        SaveInvoiceRequest saveInvoiceRequest = new SaveInvoiceRequest();
        pl.coderstrust.accounting.model.Invoice invoiceModel = prepareInvoice();
        Invoice invoiceTest = SoapModelMapper.toSoapInvoice(invoiceModel);
        saveInvoiceRequest.setInvoice(invoiceTest);
        pl.coderstrust.accounting.model.Invoice invoiceExpected = prepareInvoice();
        doReturn(invoiceExpected).when(invoiceBook).saveInvoice(invoiceModel);

        // when
        SaveInvoiceResponse saveInvoiceResponse = invoicesEndpoint.saveInvoice(saveInvoiceRequest);
        assertThat(SoapModelMapper.toInvoice(saveInvoiceResponse.getInvoice())).
            isEqualToComparingFieldByField(invoiceExpected);
    }

    @Test
    public void getInvoiceById() throws IOException {
        // given
        pl.coderstrust.accounting.model.Invoice invoiceModel = prepareInvoice();;
        Invoice invoiceExpected = SoapModelMapper.toSoapInvoice(invoiceModel);
        SaveInvoiceRequest saveInvoiceRequest = new SaveInvoiceRequest();
        saveInvoiceRequest.setInvoice(invoiceExpected);

        // when
        coders_trust.Invoice invoiceTemp = saveInvoiceRequest.getInvoice();
        SoapModelMapper.toInvoice(invoiceTemp);
        FindInvoiceByIdRequest findInvoiceByIdRequest = new FindInvoiceByIdRequest();
        findInvoiceByIdRequest.setId(invoiceTemp.getId());
        FindInvoiceByIdResponse findInvoiceByIdResponse = new FindInvoiceByIdResponse();
        findInvoiceByIdResponse.setInvoice(invoiceTemp);
        Invoice invoiceResult = findInvoiceByIdResponse.getInvoice();

        //then
        Assert.assertEquals(invoiceExpected, invoiceResult);
    }

    @Test
    public void getAllInvoices() throws IOException, DatatypeConfigurationException {
        // given
        pl.coderstrust.accounting.model.Invoice invoiceModel = prepareInvoice();;
        Invoice invoiceExpected = SoapModelMapper.toSoapInvoice(invoiceModel);

        SaveInvoiceRequest saveInvoiceRequest = new SaveInvoiceRequest();
        saveInvoiceRequest.setInvoice(invoiceExpected);

        Invoices invoicesExpected = new Invoices();
        List<Invoices> invoicesList = new ArrayList<>();

        // when
        coders_trust.Invoice invoiceTemp = saveInvoiceRequest.getInvoice();
        SoapModelMapper.toInvoice(invoiceTemp);
        invoicesList.add(invoicesExpected);
        invoicesExpected.getInvoiceList();

        FindAllInvoicesResponse findAllInvoicesResponse = new FindAllInvoicesResponse();
        findAllInvoicesResponse.setInvoices(invoicesExpected);
        Invoices invoiceResult = findAllInvoicesResponse.getInvoices();

        //then
        Assert.assertEquals(invoicesExpected, invoiceResult);
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
        return new Company(
            (long) (random.nextInt(10000) + 1),
            (random.nextInt(999999999) + 9999999) + "",
            city,
            company);
    }

}

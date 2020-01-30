package pl.coderstrust.accounting.controllers;

import ct_invoice_soap.DeleteInvoiceByIdRequest;
import ct_invoice_soap.DeleteInvoiceByIdResponse;
import ct_invoice_soap.Entries;
import ct_invoice_soap.Entry;
import ct_invoice_soap.FindAllInvoicesRequest;
import ct_invoice_soap.FindAllInvoicesResponse;
import ct_invoice_soap.Invoice;
import ct_invoice_soap.Invoices;
import ct_invoice_soap.SaveInvoiceRequest;
import ct_invoice_soap.SaveInvoiceResponse;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.hamcrest.MatcherAssert;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
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

        //then
        assertThat(SoapModelMapper.toInvoice(saveInvoiceResponse.getInvoice())).
            isEqualToComparingFieldByField(invoiceExpected);
    }

    @Test
    public void shouldFindAllInvoicesFromInvoiceBook() throws IOException {
        // given
        pl.coderstrust.accounting.model.Invoice invoice = prepareInvoice();
        SaveInvoiceRequest saveInvoiceRequest = new SaveInvoiceRequest();
        pl.coderstrust.accounting.model.Invoice invoiceModel = invoice;
        Invoice invoiceTest = SoapModelMapper.toSoapInvoice(invoiceModel);
        saveInvoiceRequest.setInvoice(invoiceTest);
        Invoices invoices = new Invoices();
        invoices.getInvoiceList().add(0, invoiceTest);

        // when
        List<pl.coderstrust.accounting.model.Invoice> invoicesExpected = invoiceBook.findAllInvoices();
        invoicesExpected.add(0, invoiceModel);
        pl.coderstrust.accounting.model.Invoice invoiceExpected = invoicesExpected.get(0);

        FindAllInvoicesResponse findAllInvoicesResponse = new FindAllInvoicesResponse();
        findAllInvoicesResponse.setInvoices(invoices);
        List<Invoice> invoicesResult = findAllInvoicesResponse.getInvoices().getInvoiceList();
        pl.coderstrust.accounting.model.Invoice invoiceResult = SoapModelMapper.toInvoice(findAllInvoicesResponse.getInvoices().getInvoiceList().get(0));

        // then
        assertEquals(invoiceExpected, invoiceResult);
    }

    @Test
    public void shouldUpdateInvoiceFromInvoiceBook() throws IOException, DatatypeConfigurationException {

        // given
        SaveInvoiceRequest saveInvoiceRequest = new SaveInvoiceRequest();
        pl.coderstrust.accounting.model.Invoice invoiceModel = prepareInvoice();
        Invoice invoiceTest = SoapModelMapper.toSoapInvoice(invoiceModel);
        saveInvoiceRequest.setInvoice(invoiceTest);

        pl.coderstrust.accounting.model.Invoice invoiceExpected = prepareInvoice();
        doReturn(invoiceExpected).when(invoiceBook).saveInvoice(invoiceModel);

        SaveInvoiceResponse saveInvoiceResponse = invoicesEndpoint.saveInvoice(saveInvoiceRequest);

        SaveInvoiceRequest saveInvoiceRequestUpdate = new SaveInvoiceRequest();
        pl.coderstrust.accounting.model.Invoice newInvoiceModel = prepareInvoice();
        Invoice invoiceTestUpdate = SoapModelMapper.toSoapInvoice(newInvoiceModel);
        saveInvoiceRequest.setInvoice(invoiceTestUpdate);

        pl.coderstrust.accounting.model.Invoice invoiceExpectedUpdate = prepareInvoice();
        doReturn(invoiceExpectedUpdate).when(invoiceBook).saveInvoice(newInvoiceModel);

        SaveInvoiceResponse saveInvoiceResponseUpdate = invoicesEndpoint.saveInvoice(saveInvoiceRequestUpdate);

        newInvoiceModel.setId(invoiceExpected.getId());
        pl.coderstrust.accounting.model.Invoice result = invoiceBook.saveInvoice(newInvoiceModel);
        List<pl.coderstrust.accounting.model.Invoice> results = invoiceBook.findAllInvoices();

        MatcherAssert.assertThat(results.size(), is(1));
        assertEquals(invoiceExpected.getId(), result.getId());
    }

    @Test
    public void shouldDeteleInvoiceUsingInvoiceBook() throws IOException, DatatypeConfigurationException {

        // given
        pl.coderstrust.accounting.model.Invoice invoice = prepareInvoice();
        SaveInvoiceRequest saveInvoiceRequest = new SaveInvoiceRequest();
        pl.coderstrust.accounting.model.Invoice invoiceModel = invoice;
        Invoice invoiceTest = SoapModelMapper.toSoapInvoice(invoiceModel);
        saveInvoiceRequest.setInvoice(invoiceTest);

        // when
        pl.coderstrust.accounting.model.Invoice invoiceExpected = invoice;
        doReturn(invoiceExpected).when(invoiceBook).saveInvoice(invoiceModel);

        SaveInvoiceResponse saveInvoiceResponse = invoicesEndpoint.saveInvoice(saveInvoiceRequest);

        DeleteInvoiceByIdRequest deleteInvoiceByIdRequest = new DeleteInvoiceByIdRequest();
        pl.coderstrust.accounting.model.Invoice invoiceResult = invoice;
        deleteInvoiceByIdRequest.setId(invoiceModel.getId());
        doReturn(invoiceResult).when(invoiceBook).deleteInvoiceById(invoiceModel.getId());

        DeleteInvoiceByIdResponse deleteInvoiceByIdResponse =
            invoicesEndpoint.deleteInvoiceById(deleteInvoiceByIdRequest);

        Long idExpected = saveInvoiceRequest.getInvoice().getId();
        XMLGregorianCalendar dateExpected = saveInvoiceRequest.getInvoice().getDate();
        Long idSellerExpected = saveInvoiceRequest.getInvoice().getSeller().getId();
        String nameSellerExpected = saveInvoiceRequest.getInvoice().getSeller().getName();
        String addressSellerExpected = saveInvoiceRequest.getInvoice().getSeller().getAddress();
        String tinSellerExpected = saveInvoiceRequest.getInvoice().getSeller().getTin();
        Long idBuyerExpected = saveInvoiceRequest.getInvoice().getBuyer().getId();
        String nameBuyerExpected = saveInvoiceRequest.getInvoice().getBuyer().getName();
        String addressBuyerExpected = saveInvoiceRequest.getInvoice().getBuyer().getAddress();
        String tinBuyerExpected = saveInvoiceRequest.getInvoice().getBuyer().getTin();
        List<Entry> entriesExpected = saveInvoiceRequest.getInvoice().getEntries().getEntriesList();

        Long idResult = deleteInvoiceByIdResponse.getInvoice().getId();
        XMLGregorianCalendar dateResult = deleteInvoiceByIdResponse.getInvoice().getDate();
        Long idSellerResult = deleteInvoiceByIdResponse.getInvoice().getSeller().getId();
        String nameSellerResult = deleteInvoiceByIdResponse.getInvoice().getSeller().getName();
        String addressSellerResult = deleteInvoiceByIdResponse.getInvoice().getSeller().getAddress();
        String tinSellerResult = deleteInvoiceByIdResponse.getInvoice().getSeller().getTin();
        Long idBuyerResult = deleteInvoiceByIdResponse.getInvoice().getBuyer().getId();
        String nameBuyerResult = deleteInvoiceByIdResponse.getInvoice().getBuyer().getName();
        String addressBuyerResult = deleteInvoiceByIdResponse.getInvoice().getBuyer().getAddress();
        String tinBuyerResult = deleteInvoiceByIdResponse.getInvoice().getBuyer().getTin();
        List<Entry> entriesResult = deleteInvoiceByIdResponse.getInvoice().getEntries().getEntriesList();

        //then
        assertEquals(idExpected, idResult);
        assertEquals(dateExpected, dateResult);
        assertEquals(idSellerExpected, idSellerResult);
        assertEquals(nameSellerExpected, nameSellerResult);
        assertEquals(addressSellerExpected, addressSellerResult);
        assertEquals(tinSellerExpected, tinSellerResult);
        assertEquals(idBuyerExpected, idBuyerResult);
        assertEquals(nameBuyerExpected, nameBuyerResult);
        assertEquals(addressBuyerExpected, addressBuyerResult);
        assertEquals(tinBuyerExpected, tinBuyerResult);
        assertEquals(entriesExpected, entriesResult);
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

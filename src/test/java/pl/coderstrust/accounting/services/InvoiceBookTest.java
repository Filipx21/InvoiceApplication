package pl.coderstrust.accounting.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderstrust.accounting.model.Company;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.model.InvoiceEntry;
import pl.coderstrust.accounting.repositories.InMemoryDatabase;

@ExtendWith(MockitoExtension.class)
class InvoiceBookTest {

    @Mock
    private InMemoryDatabase inMemoryDatabase;

    @InjectMocks
    private InvoiceBook invoiceBook;

    private Invoice prepareInoice(){
        Random random = new Random();
        List<InvoiceEntry> invoiceEntries = new ArrayList<>();
        Company buyer = new Company(1L, "123123123", "Wrocław 66-666", "Firmex z.o.o");
        Company saller = new Company(2L, "987567888", "Gdynia 66-666", "Szczupak z.o.o");
        Invoice invoice = new Invoice();
        invoice.setId(9L);
        invoice.setDate(LocalDateTime.of(
            random.nextInt(120) + 1900,
            random.nextInt(12) + 1,
            random.nextInt(25) + 1,
            random.nextInt(12),
            random.nextInt(59) + 1,
            random.nextInt(59) + 1));
        invoice.setBuyer(buyer);
        invoice.setSeller(saller);
        invoice.setEntries(invoiceEntries);
        return invoice;
    }

    private Company prepareSaller(){
        return new Company(2L, "42342312", "Warszawa 66-666", "Zippo z.o.o");
    }

    private Map<Long, Invoice> prepareInvoiceData() {
        Map<Long, Invoice> invoices = new ConcurrentHashMap<>();
        Invoice invoice1 = new Invoice();
        invoice1.setId(12L);
        invoices.put(1L, invoice1);
        Invoice invoice2 = new Invoice();
        invoice2.setId(13L);
        invoices.put(2L, invoice2);
        Invoice invoice3 = new Invoice();
        invoice3.setId(14L);
        invoices.put(3L, invoice3);
        return invoices;
    }

    @Test
    @DisplayName("Save invoice test")
    void shouldSaveInvoice() throws NullPointerException {
        Invoice invoice = prepareInoice();
        Invoice expected = prepareInoice();

        when(inMemoryDatabase.saveInvoice(invoice)).thenReturn(expected);

        Invoice invoiceFound = invoiceBook.saveInvoice(invoice);

        assertEquals(expected, invoiceFound);
    }

    @Test
    @DisplayName("Save invoice with null id")
    void shouldSaveInvoiceWithNullId() throws NullPointerException {
        Invoice invoice = prepareInoice();
        Invoice expected = prepareInoice();
        invoice.setId(null);
        expected.setId(null);

        when(inMemoryDatabase.saveInvoice(invoice)).thenReturn(expected);

        Invoice invoiceSave = invoiceBook.saveInvoice(invoice);

        assertEquals(expected, invoiceSave);
    }
///////////////////////////////////////////////////////
    @Test
    @DisplayName("Save invoice with id where invoice with this same id exists")
    void shouldUpdateInvoiceWithIdWhereInvoiceWithThisSameIdExists() throws NullPointerException {
        Invoice existed = prepareInoice();
        Invoice invoice = prepareInoice();
        Invoice expected = prepareInoice();
        existed.setSeller(prepareSaller());

        when(inMemoryDatabase.saveInvoice(existed)).thenReturn(existed);

        inMemoryDatabase.saveInvoice(existed);

        Invoice invoice2 = inMemoryDatabase.saveInvoice(invoice);



    }
/////////////////////////////////////////////////////////////
    @Test
    @DisplayName("save invoice with id but not exist in db")
    void shouldThrowExceptionWhenSavedInvoiceDoesntExistInDatabase() throws NullPointerException {
        Invoice invoice = prepareInoice();

        when(inMemoryDatabase.saveInvoice(invoice)).thenReturn(invoice);
        when(inMemoryDatabase.findInvoiceById(invoice.getId())).thenThrow(NullPointerException.class);

        Invoice invoiceSave = invoiceBook.saveInvoice(invoice);

        assertThrows(NullPointerException.class, () ->{
            invoiceBook.findInvoiceById(invoiceSave.getId());
        });
    }

    @Test
    @DisplayName("Save invoice exception test")
    void shouldSaveInvoiceAndReturnNull() throws NullPointerException {
        Invoice invoice = prepareInoice();

        when(inMemoryDatabase.saveInvoice(invoice)).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () -> {
            invoiceBook.saveInvoice(invoice);
        });
    }

    @Test
    @DisplayName("Find invoice by id")
    void shouldFindInvoiceById() throws NullPointerException {
        Invoice expected = prepareInoice();
        Invoice invoice = prepareInoice();

        when(inMemoryDatabase.findInvoiceById(invoice.getId())).thenReturn(expected);

        Invoice invoiceFound = invoiceBook.findInvoiceById(invoice.getId());

        assertEquals(expected, invoiceFound);
    }

    @Test
    @DisplayName("Not Find invoice by id")
    void shouldNotFindInvoiceById() throws NullPointerException {
        Invoice invoice = prepareInoice();

        when(inMemoryDatabase.findInvoiceById(invoice.getId())).thenReturn(null);

        Invoice invoiceFound = invoiceBook.findInvoiceById(invoice.getId());

        assertNull(invoiceFound);
    }

    @Test
    @DisplayName("Throw exception when invoice doesnt exist")
    void shouldThrowExceptionWhenInvoiceDoesntExist() throws NullPointerException {
        when(inMemoryDatabase.findInvoiceById(1L)).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () -> {
            invoiceBook.findInvoiceById(1L);
        });
    }

    @Test
    @DisplayName("Find id invoice")
    void shouldFindInvoiceId() throws NullPointerException {
        Invoice invoice = prepareInoice();
        Invoice expected = prepareInoice();

        when(inMemoryDatabase.findInvoiceById(invoice.getId())).thenReturn(expected);

        Invoice invoiceFound = invoiceBook.findInvoiceById(invoice.getId());

        assertEquals(expected.getId(), invoiceFound.getId());
    }

    @Test
    @DisplayName("Find 3 invoices")
    void shouldFindAllnvoicesListSize3() throws NullPointerException {
        Map<Long, Invoice> invoices = prepareInvoiceData();

        when(inMemoryDatabase.findAllnvoices()).thenReturn(invoices);

        List<Invoice> allInvoices = invoiceBook.findAllnvoices();

        assertThat(allInvoices, hasSize(3));
    }

    @Test
    @DisplayName("Find all invoices")
    void shouldFindAllInvoiceInRepository() throws NullPointerException {
        Map<Long, Invoice> invoices = prepareInvoiceData();
        List<Invoice> invoicesExpected = new ArrayList<>(invoices.values());

        when(inMemoryDatabase.findAllnvoices()).thenReturn(invoices);

        List<Invoice> allInvoices = invoiceBook.findAllnvoices();

        assertEquals(invoicesExpected, allInvoices);
    }

    @Test
    @DisplayName("Delete by id")
    void shouldDeleteById() throws NullPointerException {
        Invoice invoice = prepareInoice();
        Invoice expected = prepareInoice();

        when(inMemoryDatabase.findInvoiceById(invoice.getId())).thenReturn(expected);
        when(inMemoryDatabase.deleteInvoiceById(expected.getId())).thenReturn(
            expected);

        Invoice deletedInvoice = invoiceBook.deleteInvoiceById(invoice.getId());

        assertEquals(expected, deletedInvoice);
    }

    @Test
    @DisplayName("Delete by id not find invoice")
    void shouldNotFindObjectForDeleteById() throws NullPointerException {
        Invoice invoice = prepareInoice();

        when(inMemoryDatabase.findInvoiceById(invoice.getId())).thenReturn(null);

        Invoice deletedInvoice = invoiceBook.deleteInvoiceById(invoice.getId());

        assertNull(deletedInvoice);
    }

    @Test
    @DisplayName("Delete by id throw exception")
    void shouldThrowExceptionWhenDeleteInvoiceNotExist() throws NullPointerException {
        Invoice invoice = prepareInoice();

        when(inMemoryDatabase.findInvoiceById(invoice.getId())).thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () -> {
            invoiceBook.deleteInvoiceById(invoice.getId());
        });
    }

    @Test
    @DisplayName("Edit invoice")
    void shouldEditInvoice() throws NullPointerException {
        Invoice invoice = prepareInoice();
        Invoice toEdit = prepareInoice();
        toEdit.setSeller(prepareSaller());


        Invoice expected = prepareInoice();

        when(inMemoryDatabase.findInvoiceById(toEdit.getId()))
            .thenReturn(invoice);

        when(inMemoryDatabase.saveInvoice(toEdit)).thenReturn(toEdit);

        //Invoice dfgdfg = invoiceBook.editInvoice(toEdit);

        //Invoice invoiceAfterEdit = invoiceBook.editInvoice(expected);

        //assertEquals(expected, invoiceAfterEdit);
    }

    @Test
    @DisplayName("Edit invoice throw exception when not find invoice")
    void shouldThrowExcetionWhenNotFindInvoiceForEditInvoice() throws NullPointerException {
        Invoice toEdit = prepareInoice();

        when(inMemoryDatabase.findInvoiceById(toEdit.getId()))
            .thenThrow(new NullPointerException("Invoice doesn't exist"));

        assertThrows(NullPointerException.class, () -> {
            invoiceBook.editInvoice(toEdit);
        });
    }

}
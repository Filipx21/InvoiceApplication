package pl.coderstrust.accounting.infrastructure;

import pl.coderstrust.accounting.model.Invoice;

import java.io.IOException;
import java.util.List;

public interface InvoiceDatabase {

    Invoice saveInvoice(Invoice invoice) throws IOException;

    Invoice findInvoiceById(Long id) throws IOException;

    List<Invoice> findAllnvoices() throws IOException;

    Invoice deleteByInvoice(Long id) throws IOException;

}

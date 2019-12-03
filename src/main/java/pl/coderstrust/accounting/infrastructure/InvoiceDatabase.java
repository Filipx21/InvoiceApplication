package pl.coderstrust.accounting.infrastructure;

import java.util.List;
import pl.coderstrust.accounting.model.Invoice;
import pl.coderstrust.accounting.repositories.hibernate.HibernateRepository;

public interface InvoiceDatabase {

    Invoice saveInvoice(Invoice invoice);

    Invoice findInvoiceById(Long id);

    List<Invoice> findAllInvoices();

    Invoice deleteInvoiceById(Long id);

}

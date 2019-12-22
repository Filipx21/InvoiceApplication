package pl.coderstrust.accounting.mapper;

import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.services.InvoiceBook;

public class InvoiceBookMapper extends InvoiceBook{

    public InvoiceBook invoiceBook;

    public InvoiceBookMapper(InvoiceDatabase invoiceDatabase) {
        super(invoiceDatabase);
    }

    public InvoiceBook getInvoiceBook() {
        return invoiceBook;
    }

    public void setInvoiceBook(InvoiceBook invoiceBook) {
        this.invoiceBook = invoiceBook;
    }

}

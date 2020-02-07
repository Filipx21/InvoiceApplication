package pl.coderstrust.accounting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.coderstrust.accounting.services.InvoiceBook;
import pl.coderstrust.accounting.util.InvoicePdf;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Controller
public class MyController {

    @Autowired
    private InvoiceBook invoiceBook;

    @RequestMapping(value = "/pdfreport/{id}", method = RequestMethod.GET,
        produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> citiesReport(@PathVariable("id") Long id) throws IOException {

        var invoicePdf = invoiceBook.findInvoiceById(id);

        ByteArrayInputStream bis = InvoicePdf.invoicesReport(invoicePdf);

        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=invoiceReport.pdf");

        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(bis));
    }
}
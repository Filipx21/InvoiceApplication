package pl.coderstrust.accounting.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

    @RequestMapping(value = "/pdfreport", method = RequestMethod.GET,
        produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> citiesReport() throws IOException {

        var invoices = invoiceBook.findAllInvoices();

        ByteArrayInputStream bis = InvoicePdf.invoicesReport(invoices);

        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");

        return ResponseEntity
            .ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_PDF)
            .body(new InputStreamResource(bis));
    }
}
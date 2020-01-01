package pl.coderstrust.accounting.controllers;

import io.spring.guides.gs_producing_web_service.GetDeleteInvoiceByIdResponse;
import io.spring.guides.gs_producing_web_service.GetFindAllInvoiceByDateRangeResponse;
import io.spring.guides.gs_producing_web_service.GetFindAllInvoicesResponse;
import io.spring.guides.gs_producing_web_service.GetFindInvoiceByIdRequest;
import io.spring.guides.gs_producing_web_service.GetFindInvoiceByIdResponse;
import io.spring.guides.gs_producing_web_service.GetSaveInvoiceRequest;
import io.spring.guides.gs_producing_web_service.GetSaveInvoiceResponse;
import io.spring.guides.gs_producing_web_service.Invoice;
import io.spring.guides.gs_producing_web_service.Invoices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.coderstrust.accounting.infrastructure.InvoiceDatabase;
import pl.coderstrust.accounting.mapper.InvoiceBookMapper;
import pl.coderstrust.accounting.services.InvoiceBook;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class InvoicesEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private InvoiceBook invoiceBook;
    private InvoiceBookMapper invoiceBookMapper;
    private final InvoiceDatabase invoiceDatabase;
    private final static Logger log = LoggerFactory.getLogger(InvoiceBookMapper.class);

    @Autowired
    public InvoicesEndpoint(InvoiceBook invoiceBook, InvoiceBookMapper invoiceBookMapper,
                            InvoiceDatabase invoiceDatabase) {
        this.invoiceBook = invoiceBook;
        this.invoiceBookMapper = invoiceBookMapper;
        this.invoiceDatabase = invoiceDatabase;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveInvoice")
    @ResponsePayload
    public GetSaveInvoiceResponse saveInvoice(@RequestPayload GetSaveInvoiceRequest getSaveInvoiceRequest) {
        log.info("Save invoice SOAP endpoint services");
        GetSaveInvoiceResponse responseSaveInvoice = new GetSaveInvoiceResponse();
        pl.coderstrust.accounting.model.Invoice invoice = new pl.coderstrust.accounting.model.Invoice();
        if (invoice != null) {
            log.info("Save invoice in InvoiceBook services");
            invoiceDatabase.saveInvoice(invoice);
            return responseSaveInvoice;
        }
        log.info("Null save invoice SOAP endpoint services");
        return null;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findInvoiceById")
    @ResponsePayload
    public Object findInvoiceById
        (@RequestPayload GetFindInvoiceByIdRequest getFindInvoiceByIdRequest) {
        log.info("Find Invoice by ID SOAP endpoint services");
        GetFindInvoiceByIdResponse responseFindInvoiceById = new GetFindInvoiceByIdResponse();
       // responseFindInvoiceById.setInvoice(invoiceBookMapper.findInvoiceById(id));
        //return responseFindInvoiceById;
        invoiceBookMapper.

        pl.coderstrust.accounting.model.Invoice invoiceFound = invoiceDatabase.findInvoiceById();
        if (invoiceFound != null) {
            log.info("Find Invoice by ID SOAP endpoint services");
            return responseFindInvoiceById;
        }
        log.info("Find Invoice by ID is null in InvoiceBook services");
        return null;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findAllInvoices")
    @ResponsePayload
    public GetFindAllInvoicesResponse findAllInvoices() {
        log.info("Find all invoices SOAP endpoint services");
        GetFindAllInvoicesResponse responseFindAllInvoices = new GetFindAllInvoicesResponse();
        List<pl.coderstrust.accounting.model.Invoice> allInvoices = invoiceBook.findAllInvoices();
        List<Invoice> soapInvoices = allInvoices.stream().map(InvoiceBookMapper::toSoapInvoice).collect(Collectors.toList());
        Invoices invoices = new Invoices();
        invoices.getInvoiceList().addAll(soapInvoices);
        responseFindAllInvoices.setInvoices(invoices);
        return responseFindAllInvoices;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findAllInvoiceByDateRange")
    @ResponsePayload
    public GetFindAllInvoiceByDateRangeResponse findAllInvoiceByDateRange
        (@RequestPayload LocalDate localDatefrom, @RequestPayload LocalDate localDateTo) {
        log.info("Find all invoices by data range SOAP endpoint services");
        GetFindAllInvoiceByDateRangeResponse responseFindAllInvoiceByDateRange =
            new GetFindAllInvoiceByDateRangeResponse();
        //responseFindAllInvoiceByDateRange.setInvoice((Invoice) invoiceBookMapper.
            findAllInvoiceByDateRange(localDatefrom, localDateTo);

        return responseFindAllInvoiceByDateRange;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteInvoiceById")
    @ResponsePayload
    public GetDeleteInvoiceByIdResponse deleteInvoiceById(@RequestPayload Long id) {
        log.info("Delete invoice by ID SOAP endpoint services");
        GetDeleteInvoiceByIdResponse responseDeleteInvoiceById = new GetDeleteInvoiceByIdResponse();
        //responseDeleteInvoiceById.setInvoice(invoiceBookMapper.deleteInvoiceById(id));

        return responseDeleteInvoiceById;
    }
}

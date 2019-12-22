package pl.coderstrust.accounting.controllers;

import io.spring.guides.gs_producing_web_service.GetInvoicesResponse;
import io.spring.guides.gs_producing_web_service.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.coderstrust.accounting.mapper.InvoiceBookMapper;
import pl.coderstrust.accounting.services.InvoiceBook;

import java.time.LocalDate;

@Endpoint
public class InvoicesEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private InvoiceBook invoiceBook;
    private InvoiceBookMapper invoiceBookMapper;

    @Autowired
    public InvoicesEndpoint(InvoiceBook invoiceBook) {
        this.invoiceBook = invoiceBook;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoicesResponse")
    @ResponsePayload
    public GetInvoicesResponse findInvoiceById(@RequestPayload Long id) {
        GetInvoicesResponse response = new GetInvoicesResponse();
        //response.setInvoice(invoiceBookMapper.findInvoiceById(id));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoicesResponse")
    @ResponsePayload
    public GetInvoicesResponse findAllInvoices() {
        GetInvoicesResponse response = new GetInvoicesResponse();
        response.setInvoice((Invoice) invoiceBookMapper.findAllInvoices());

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoicesResponse")
    @ResponsePayload
    public GetInvoicesResponse findAllInvoiceByDateRange(@RequestPayload LocalDate localDatefrom,
                                                         LocalDate localDateTo) {
        GetInvoicesResponse response = new GetInvoicesResponse();
        response.setInvoice((Invoice) invoiceBookMapper.findAllInvoiceByDateRange(localDatefrom,
            localDateTo));

        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoicesResponse")
    @ResponsePayload
    public GetInvoicesResponse deleteInvoiceById(@RequestPayload Long id) {
        GetInvoicesResponse response = new GetInvoicesResponse();
        //response.setInvoice(invoiceBookMapper.deleteInvoiceById(id));

        return response;
    }
}

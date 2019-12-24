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
import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class InvoicesEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private InvoiceBook invoiceBook;
    private InvoiceBookMapper invoiceBookMapper;

    @Autowired
    public InvoicesEndpoint(InvoiceBook invoiceBook) {
        this.invoiceBook = invoiceBook;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findInvoiceById")
    @ResponsePayload
    public GetInvoicesResponse findInvoiceById(@RequestPayload Long id) {
        GetInvoicesResponse responseFindInvoiceById = new GetInvoicesResponse();
        //responseFindInvoiceById.setInvoice(invoiceBookMapper.findInvoiceById(id));

        return responseFindInvoiceById;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findAllInvoices")
    @ResponsePayload
    public GetInvoicesResponse findAllInvoices() {
        GetInvoicesResponse responseFindAllInvoices = new GetInvoicesResponse();
        List<pl.coderstrust.accounting.model.Invoice> allInvoices = invoiceBook.findAllInvoices();
        List<Invoice> soapInvoices = allInvoices.stream().map(InvoiceBookMapper::toSoapInvoice).collect(Collectors.toList());
        // responseFindAllInvoices.setInvoices(soapInvoices)
        responseFindAllInvoices.setInvoice(invoiceBookMapper.findAllInvoices());

        return responseFindAllInvoices;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findAllInvoiceByDateRange")
    @ResponsePayload
    public GetInvoicesResponse findAllInvoiceByDateRange(@RequestPayload LocalDate localDatefrom,
                                                         @RequestPayload LocalDate localDateTo) {
        GetInvoicesResponse responseFindAllInvoiceByDateRange = new GetInvoicesResponse();
        responseFindAllInvoiceByDateRange.setInvoice((Invoice) invoiceBookMapper.
            findAllInvoiceByDateRange(localDatefrom, localDateTo));

        return responseFindAllInvoiceByDateRange;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteInvoiceById")
    @ResponsePayload
    public GetInvoicesResponse deleteInvoiceById(@RequestPayload Long id) {
        GetInvoicesResponse responseDeleteInvoiceById = new GetInvoicesResponse();
        //responseDeleteInvoiceById.setInvoice(invoiceBookMapper.deleteInvoiceById(id));

        return responseDeleteInvoiceById;
    }
}

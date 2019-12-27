package pl.coderstrust.accounting.controllers;

import io.spring.guides.gs_producing_web_service.GetDeleteInvoiceByIdResponse;
import io.spring.guides.gs_producing_web_service.GetFindAllInvoiceByDateRangeResponse;
import io.spring.guides.gs_producing_web_service.GetFindAllInvoicesResponse;
import io.spring.guides.gs_producing_web_service.GetFindInvoiceByIdRequest;
import io.spring.guides.gs_producing_web_service.GetFindInvoiceByIdResponse;
import io.spring.guides.gs_producing_web_service.Invoice;
import io.spring.guides.gs_producing_web_service.Invoices;
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
    public GetFindInvoiceByIdResponse findInvoiceById(@RequestPayload GetFindInvoiceByIdRequest getFindInvoiceByIdRequest) {
        GetFindInvoiceByIdResponse responseFindInvoiceById = new GetFindInvoiceByIdResponse();
        //responseFindInvoiceById.setInvoice(invoiceBookMapper.findInvoiceById(id));

        return responseFindInvoiceById;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findAllInvoices")
    @ResponsePayload
    public GetFindAllInvoicesResponse findAllInvoices() {
        GetFindAllInvoicesResponse responseFindAllInvoices = new GetFindAllInvoicesResponse();
        List<pl.coderstrust.accounting.model.Invoice> allInvoices = invoiceBook.findAllInvoices();
        List<Invoice> soapInvoices = allInvoices.stream().map(InvoiceBookMapper::toSoapInvoice).collect(Collectors.toList());
        Invoices invoices = new Invoices();
        // invoices.setInvoiceList;
        invoices.getInvoiceList().addAll(soapInvoices);
        responseFindAllInvoices.setInvoices(invoices);

        // responseFindAllInvoices.setInvoice(invoiceBookMapper.findAllInvoices());

        return responseFindAllInvoices;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findAllInvoiceByDateRange")
    @ResponsePayload
    public GetFindAllInvoiceByDateRangeResponse findAllInvoiceByDateRange(@RequestPayload LocalDate localDatefrom,
                                                         @RequestPayload LocalDate localDateTo) {
        GetFindAllInvoiceByDateRangeResponse responseFindAllInvoiceByDateRange = new GetFindAllInvoiceByDateRangeResponse();
        //responseFindAllInvoiceByDateRange.setInvoice((Invoice) invoiceBookMapper.
            findAllInvoiceByDateRange(localDatefrom, localDateTo);

        return responseFindAllInvoiceByDateRange;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteInvoiceById")
    @ResponsePayload
    public GetDeleteInvoiceByIdResponse deleteInvoiceById(@RequestPayload Long id) {
        GetDeleteInvoiceByIdResponse responseDeleteInvoiceById = new GetDeleteInvoiceByIdResponse();
        //responseDeleteInvoiceById.setInvoice(invoiceBookMapper.deleteInvoiceById(id));

        return responseDeleteInvoiceById;
    }
}

package pl.coderstrust.accounting.controllers;

import io.spring.guides.gs_producing_web_service.GetInvoicesRequest;
import io.spring.guides.gs_producing_web_service.GetInvoicesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.coderstrust.accounting.services.InvoiceBook;

@Endpoint
public class InvoicesEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private InvoiceBook invoiceBook;

    @Autowired
    public InvoicesEndpoint(InvoiceBook invoiceBook) {
        this.invoiceBook = invoiceBook;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getInvoicesResponse")
    @ResponsePayload
    public GetInvoicesResponse getInvoices(@RequestPayload GetInvoicesRequest request) {
        GetInvoicesResponse response = new GetInvoicesResponse();
        Long id = 1L;
        response.setInvoice(invoiceBook.findInvoiceById(id));

        return response;
    }
}

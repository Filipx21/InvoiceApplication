package pl.coderstrust.accounting.controllers;

import coders_trust.DeleteInvoiceByIdRequest;
import coders_trust.DeleteInvoiceByIdResponse;
import coders_trust.FindAllInvoiceByDateRangeRequest;
import coders_trust.FindAllInvoiceByDateRangeResponse;
import coders_trust.FindAllInvoicesRequest;
import coders_trust.FindAllInvoicesResponse;
import coders_trust.FindInvoiceByIdRequest;
import coders_trust.FindInvoiceByIdResponse;
import coders_trust.Invoice;
import coders_trust.Invoices;
import coders_trust.SaveInvoiceRequest;
import coders_trust.SaveInvoiceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import pl.coderstrust.accounting.mapper.SoapModelMapper;
import pl.coderstrust.accounting.services.InvoiceBook;

import javax.xml.datatype.DatatypeConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class InvoicesEndpoint {
    private static final String NAMESPACE_URI = "coders-trust";

    private final InvoiceBook invoiceBook;
    private final SoapModelMapper soapModelMapper;
    private static final Logger log = LoggerFactory.getLogger(SoapModelMapper.class);

    @Autowired
    public InvoicesEndpoint(InvoiceBook invoiceBook, SoapModelMapper soapModelMapper) {
        this.invoiceBook = invoiceBook;
        this.soapModelMapper = soapModelMapper;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveInvoiceRequest")
    @ResponsePayload
    public SaveInvoiceResponse saveInvoice(
        @RequestPayload SaveInvoiceRequest saveInvoiceRequest)
        throws DatatypeConfigurationException, IOException {
        log.info("Save invoice SOAP endpoint services");
        Invoice saveInvoice = SoapModelMapper.toSoapInvoice(
            invoiceBook.saveInvoice(SoapModelMapper.toInvoice(saveInvoiceRequest.getInvoice())));
        SaveInvoiceResponse responseSaveInvoice = new SaveInvoiceResponse();
        responseSaveInvoice.setInvoice(saveInvoice);
        return responseSaveInvoice;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findInvoiceByIdRequest")
    @ResponsePayload
    public FindInvoiceByIdResponse findInvoiceById(
        @RequestPayload FindInvoiceByIdRequest findInvoiceByIdRequest)
        throws DatatypeConfigurationException {

        pl.coderstrust.accounting.model.Invoice invoice
            = new pl.coderstrust.accounting.model.Invoice();
        Long id = findInvoiceByIdRequest.getId();
        invoice.setId(id);
        Invoice invoiceSoap = SoapModelMapper.toSoapInvoice(invoice);
        log.info("Find Invoice by ID SOAP endpoint services");
        FindInvoiceByIdResponse responseFindInvoiceById = new FindInvoiceByIdResponse();
        responseFindInvoiceById.setInvoice(invoiceSoap);

        return responseFindInvoiceById;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findAllInvoicesRequest")
    @ResponsePayload
    public FindAllInvoicesResponse findAllInvoices(
        @RequestPayload FindAllInvoicesRequest findAllInvoicesRequest) throws IOException {
        log.info("Find all invoices SOAP endpoint services");

        List<Invoice> soapInvoices = invoiceBook.findAllInvoices().stream()
            .map(SoapModelMapper::toSoapInvoice).collect(Collectors.toList());

        Invoices invoices = new Invoices();
        invoices.getInvoiceList().addAll(soapInvoices);
        FindAllInvoicesResponse responseFindAllInvoices = new FindAllInvoicesResponse();
        responseFindAllInvoices.setInvoices(invoices);
        return responseFindAllInvoices;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findAllInvoiceByDateRangeRequest")
    @ResponsePayload
    public FindAllInvoiceByDateRangeResponse findAllInvoiceByDateRange(
        @RequestPayload FindAllInvoiceByDateRangeRequest findAllInvoiceByDateRangeRequest) {
        log.info("Find all invoices by data range SOAP endpoint services");
        FindAllInvoiceByDateRangeResponse responseFindAllInvoiceByDateRange =
            new FindAllInvoiceByDateRangeResponse();

        return responseFindAllInvoiceByDateRange;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteInvoiceByIdRequest")
    @ResponsePayload
    public DeleteInvoiceByIdResponse deleteInvoiceById(
        @RequestPayload DeleteInvoiceByIdRequest deleteInvoiceByIdRequest) throws IOException {
        log.info("Delete invoice by ID SOAP endpoint services");
        DeleteInvoiceByIdResponse responseDeleteInvoiceById = new DeleteInvoiceByIdResponse();
        Long id = deleteInvoiceByIdRequest.getId();

        SoapModelMapper.toSoapInvoice(invoiceBook.deleteInvoiceById(id));
        return responseDeleteInvoiceById;
    }
}

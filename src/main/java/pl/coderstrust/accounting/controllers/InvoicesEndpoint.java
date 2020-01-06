package pl.coderstrust.accounting.controllers;

import io.spring.guides.gs_producing_web_service.DeleteInvoiceByIdRequest;
import io.spring.guides.gs_producing_web_service.DeleteInvoiceByIdResponse;
import io.spring.guides.gs_producing_web_service.FindAllInvoiceByDateRangeRequest;
import io.spring.guides.gs_producing_web_service.FindAllInvoiceByDateRangeResponse;
import io.spring.guides.gs_producing_web_service.FindAllInvoicesRequest;
import io.spring.guides.gs_producing_web_service.FindAllInvoicesResponse;
import io.spring.guides.gs_producing_web_service.FindInvoiceByIdRequest;
import io.spring.guides.gs_producing_web_service.FindInvoiceByIdResponse;
import io.spring.guides.gs_producing_web_service.Invoice;
import io.spring.guides.gs_producing_web_service.Invoices;
import io.spring.guides.gs_producing_web_service.SaveInvoiceRequest;
import io.spring.guides.gs_producing_web_service.SaveInvoiceResponse;
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
import java.util.List;
import java.util.stream.Collectors;

@Endpoint
public class InvoicesEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private final InvoiceBook invoiceBook;
    private final SoapModelMapper soapModelMapper;
    private final static Logger log = LoggerFactory.getLogger(SoapModelMapper.class);

    @Autowired
    public InvoicesEndpoint(InvoiceBook invoiceBook, SoapModelMapper soapModelMapper) {
        this.invoiceBook = invoiceBook;
        this.soapModelMapper = soapModelMapper;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "saveInvoiceRequest")
    @ResponsePayload
    public SaveInvoiceResponse saveInvoice(
        @RequestPayload SaveInvoiceRequest saveInvoiceRequest) {
        log.info("Save invoice SOAP endpoint services");
        SaveInvoiceResponse responseSaveInvoice = new SaveInvoiceResponse();
        pl.coderstrust.accounting.model.Invoice invoice =
            new pl.coderstrust.accounting.model.Invoice();

        Invoice invoiceConverted;
        invoiceBook.saveInvoice(invoice);
        invoiceConverted = SoapModelMapper.toSoapInvoice(invoice);

        responseSaveInvoice.setInvoice(invoiceConverted);
        saveInvoiceRequest.setInvoice(invoiceConverted);
        return responseSaveInvoice;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findInvoiceByIdRequest")
    @ResponsePayload
    public FindInvoiceByIdResponse findInvoiceById
        (@RequestPayload FindInvoiceByIdRequest findInvoiceByIdRequest)
        throws DatatypeConfigurationException {

        pl.coderstrust.accounting.model.Invoice invoice = new pl.coderstrust.accounting.model.Invoice();
        invoice.setId(1L);
        Invoice invoice1 = SoapModelMapper.toSoapInvoice(invoice);
        log.info("Find Invoice by ID SOAP endpoint services");
        FindInvoiceByIdResponse responseFindInvoiceById = new FindInvoiceByIdResponse();
        responseFindInvoiceById.setInvoice(invoice1);
        //Long id = findInvoiceByIdRequest.getId();
        //pl.coderstrust.accounting.model.Invoice invoice = invoiceBook.findInvoiceById(id);

        //SoapModelMapper.toSoapInvoice(invoice);
        return responseFindInvoiceById;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findAllInvoicesRequest")
    @ResponsePayload
    public FindAllInvoicesResponse findAllInvoices
        (@RequestPayload FindAllInvoicesRequest findAllInvoicesRequest) {
        log.info("Find all invoices SOAP endpoint services");
        FindAllInvoicesResponse responseFindAllInvoices = new FindAllInvoicesResponse();

        List<pl.coderstrust.accounting.model.Invoice> allInvoices = invoiceBook.findAllInvoices();
        List<Invoice> soapInvoices = allInvoices.stream()
            .map(SoapModelMapper::toSoapInvoice).collect(Collectors.toList());

        Invoices invoices = new Invoices();
        invoices.getInvoiceList().addAll(soapInvoices);
        responseFindAllInvoices.setInvoices(invoices);
        return responseFindAllInvoices;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findAllInvoiceByDateRangeRequest")
    @ResponsePayload
    public FindAllInvoiceByDateRangeResponse findAllInvoiceByDateRange
        (@RequestPayload FindAllInvoiceByDateRangeRequest findAllInvoiceByDateRangeRequest) {
        log.info("Find all invoices by data range SOAP endpoint services");
        FindAllInvoiceByDateRangeResponse responseFindAllInvoiceByDateRange =
            new FindAllInvoiceByDateRangeResponse();


        return responseFindAllInvoiceByDateRange;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteInvoiceByIdRequest")
    @ResponsePayload
    public DeleteInvoiceByIdResponse deleteInvoiceById
        (@RequestPayload DeleteInvoiceByIdRequest deleteInvoiceByIdRequest) {
        log.info("Delete invoice by ID SOAP endpoint services");
        DeleteInvoiceByIdResponse responseDeleteInvoiceById = new DeleteInvoiceByIdResponse();
        Long id = deleteInvoiceByIdRequest.getId();
        pl.coderstrust.accounting.model.Invoice invoice = invoiceBook.deleteInvoiceById(id);

        SoapModelMapper.toSoapInvoice(invoice);
        return responseDeleteInvoiceById;
    }
}

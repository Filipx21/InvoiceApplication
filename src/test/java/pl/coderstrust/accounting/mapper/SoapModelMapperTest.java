package pl.coderstrust.accounting.mapper;

import coders_trust.Company;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import pl.coderstrust.accounting.model.Invoice;

import javax.xml.datatype.DatatypeConfigurationException;
import java.time.LocalDate;

class SoapModelMapperTest {

    SoapModelMapper soapModelMapper = new SoapModelMapper();

    @Test
    void shouldConvertIdInvoiceToSoapIdInvoice() {
        //given
        Invoice invoice = new Invoice();
        coders_trust.Invoice invoiceSoap;
        invoice.setId(1L);

        //when
        invoiceSoap = SoapModelMapper.toSoapInvoice(invoice);
        Long idInvoice = invoice.getId();
        Long idActual = invoiceSoap.getId();

        //then
        Assert.assertEquals(idInvoice, idActual);
    }

    @Test
    void shouldConvertDateInvoiceToSoapDateInvoice() {
        //given
        Invoice invoice = new Invoice();
        coders_trust.Invoice invoiceSoap;
        coders_trust.Invoice invoiceExpected = new coders_trust.Invoice();
        invoice.setId(0L);
        invoice.setDate(LocalDate.of(2020,1,8));
        invoiceExpected.setDate(null);

        //when
        invoiceSoap = SoapModelMapper.toSoapInvoice(invoice);
        String dateExpected = invoice.getDate().toString();
        String dateActual = invoiceSoap.getDate().toString();

        //then
        Assert.assertEquals(dateExpected, dateActual);
    }

    @Test
    void shouldConvertCompanyInvoiceToSoapCompanyInvoice() {
        //given
        Invoice invoice = new Invoice();
        coders_trust.Invoice invoiceSoap;
        coders_trust.Invoice invoiceExpected = new coders_trust.Invoice();
        pl.coderstrust.accounting.model.Company seller = new pl.coderstrust.accounting.model.Company();
        Company sellerExpected = new Company();
        Company sellerResult;

        seller.setId(1L);
        seller.setTin("Tin example number");
        seller.setName("Seller example name");
        seller.setAddress("Example seller Address");

        sellerExpected.setId(1L);
        sellerExpected.setTin("Tin example number");
        sellerExpected.setName("Seller example name");
        sellerExpected.setAddress("Example seller Address");

        invoice.setId(0L);
        invoice.setSeller(seller);
        invoiceExpected.setId(0L);
        invoiceExpected.setSeller(sellerExpected);

        //when
        invoiceSoap = SoapModelMapper.toSoapInvoice(invoice);
        sellerResult = invoiceSoap.getSeller();

        //then
        Assert.assertEquals(sellerExpected, sellerResult);
    }

    @Test
    void shouldConvertSoapInvoiceToInvoice() throws DatatypeConfigurationException {
        //given
        Invoice invoiceModel = new Invoice();
        Invoice invoiceResult;
        coders_trust.Invoice invoiceSoap = new coders_trust.Invoice();

        invoiceSoap.setId(0L);
        invoiceSoap.setDate(null);
        invoiceModel.setId(0L);
        invoiceModel.setDate(null);

        //when
        invoiceResult = SoapModelMapper.toInvoice(invoiceSoap);

        //then
        Assert.assertEquals(invoiceModel, invoiceResult);
    }

    @Test
    void shouldConvertInvoiceToSoapInvoice() throws DatatypeConfigurationException {
        //given
        Invoice invoiceModel = new Invoice();
        coders_trust.Invoice invoiceResult;
        coders_trust.Invoice invoiceExpected = new coders_trust.Invoice();

        invoiceExpected.setId(0L);
        invoiceModel.setId(0L);

        //when
        invoiceResult = SoapModelMapper.toSoapInvoice(invoiceModel);

        //then
        Assert.assertEquals(invoiceExpected, invoiceResult);
    }

}
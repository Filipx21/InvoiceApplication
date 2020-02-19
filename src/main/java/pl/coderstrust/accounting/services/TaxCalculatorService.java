package pl.coderstrust.accounting.services;

import org.hibernate.boot.model.relational.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderstrust.accounting.model.InvoiceEntry;

import java.math.BigDecimal;

@Service
public class TaxCalculatorService {

    InvoiceEntry invoiceEntry;
    private final Logger log = LoggerFactory.getLogger(TaxCalculatorService.class);

    BigDecimal calculateVatValue() {
        log.info("Calculate Vat value");
        int vatValue = invoiceEntry.getVatValue();
        BigDecimal vatInvoiceValue = null;
        BigDecimal vatBigDecimal = multiply(vatValue, vatInvoiceValue);
        BigDecimal priceGross = invoiceEntry.getPrice();
        return vatBigDecimal.multiply(priceGross).multiply(BigDecimal.valueOf(0.01));
    }

    BigDecimal calculateNetPrice() {
        log.info("Calculate net price");
        return invoiceEntry.getPrice().subtract(calculateVatValue());
    }

    private static BigDecimal multiply ( int a, BigDecimal b ) {
        return BigDecimal.valueOf(a).multiply(b);
    }

}

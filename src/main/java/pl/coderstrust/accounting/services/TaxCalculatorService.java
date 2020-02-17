package pl.coderstrust.accounting.services;

import org.springframework.stereotype.Service;
import pl.coderstrust.accounting.model.InvoiceEntry;

import java.math.BigDecimal;

@Service
public class TaxCalculatorService {

    BigDecimal calculateVatValue(InvoiceEntry invoiceEntry) {
        int vatValue = invoiceEntry.getVatValue();
        BigDecimal vatInvoiceValue = null;
        BigDecimal vatBigDecimal = multiply(vatValue, vatInvoiceValue);
        BigDecimal priceGross = invoiceEntry.getPrice();
        BigDecimal vatCalculated = vatBigDecimal.multiply(priceGross).multiply(BigDecimal.valueOf(0.01));
        return vatCalculated;
    }

    BigDecimal calculateNetPrice(InvoiceEntry invoiceEntry) {
        return null;
    }

    private static BigDecimal multiply ( int a, BigDecimal b ) {
        return BigDecimal.valueOf(a).multiply(b);
    }

}

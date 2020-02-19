package pl.coderstrust.accounting.services;

import org.springframework.stereotype.Service;
import pl.coderstrust.accounting.model.InvoiceEntry;

import java.math.BigDecimal;

@Service
public class TaxCalculatorService {

    InvoiceEntry invoiceEntry;

    BigDecimal calculateVatValue() {
        int vatValue = invoiceEntry.getVatValue();
        BigDecimal vatInvoiceValue = null;
        BigDecimal vatBigDecimal = multiply(vatValue, vatInvoiceValue);
        BigDecimal priceGross = invoiceEntry.getPrice();
        return vatBigDecimal.multiply(priceGross).multiply(BigDecimal.valueOf(0.01));
    }

    BigDecimal calculateNetPrice() {
        return invoiceEntry.getPrice().subtract(calculateVatValue());
    }

    private static BigDecimal multiply ( int a, BigDecimal b ) {
        return BigDecimal.valueOf(a).multiply(b);
    }

}

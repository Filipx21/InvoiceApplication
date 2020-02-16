package pl.coderstrust.accounting.services;

import org.springframework.stereotype.Service;
import pl.coderstrust.accounting.model.Invoice;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Service
public class TaxCalculatorService {

    BigDecimal calculateIncome() {
        return null;
    }

    BigDecimal calculateCost() {
        return null;
    }

    BigDecimal calculateIncomeVat() {
        return null;
    }

    BigDecimal calculateOutcomeVat() {
        return null;
    }

    private BigDecimal calculateTaxBase() {
        return null;
    }

    public Map<String, BigDecimal> taxSummary() {
        return null;
    }

    private Predicate<Invoice> getCompanyIdSeller() {
        return null;
    }

    private Predicate<Invoice> getCompanyIdBuyer() {
        return null;
    }

    private BigDecimal getCostValue() {
        return null;
    }

    private BigDecimal getNetValue() {
        return null;
    }

    private BigDecimal getVatValue() {
        return null;
    }

    private BigDecimal getIncomeVatValue() {
        return null;
    }

    private List<Invoice> getInvoiceByDate() {
        return null;
    }

}

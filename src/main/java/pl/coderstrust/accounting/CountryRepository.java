package pl.coderstrust.accounting;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import pl.coderstrust.accounting.model.Invoice;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class CountryRepository {
    private static final Map<String, Country> countries = new HashMap<>();
    private static final Map<Long, Invoice> invoices = new HashMap<>();

    @PostConstruct
    public void initData() {
        Country spain = new Country();
        spain.setName("Spain");

        Invoice invoice = new Invoice();
        invoices.put(invoice.getId(), invoice);

        countries.put(spain.getName(), spain);
    }

    public Country findCountry(String name) {
        Assert.notNull(name, "The country's name must not be null");
        return countries.get(name);
    }
}

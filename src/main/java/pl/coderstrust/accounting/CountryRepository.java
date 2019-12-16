package pl.coderstrust.accounting;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
public class CountryRepository {
    private static final Map<String, Country> countries = new HashMap<>();

    @PostConstruct
    public void initData() {
        Country spain = new Country();
        spain.setName("Spain");

        countries.put(spain.getName(), spain);

        Country poland = new Country();
        poland.setName("Poland");

        countries.put(poland.getName(), poland);

        Country uk = new Country();
        uk.setName("United Kingdom");

        countries.put(uk.getName(), uk);
    }

    public Country findCountry(String name) {
        Assert.notNull(name, "The country's name must not be null");
        return countries.get(name);
    }
}

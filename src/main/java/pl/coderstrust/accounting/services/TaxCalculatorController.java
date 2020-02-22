package pl.coderstrust.accounting.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Configuration
public class TaxCalculatorController {

    private TaxCalculatorService taxService;

    @Autowired
    public TaxCalculatorController(TaxCalculatorService taxService) {
        this.taxService = taxService;
    }

    @GetMapping("/invoices")

}

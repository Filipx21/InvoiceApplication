package pl.coderstrust.accounting;

import io.spring.guides.gs_producing_web_service.GetCountryResponse;
import io.spring.guides.gs_producing_web_service.GetInvoicesRequest;
import io.spring.guides.gs_producing_web_service.GetInvoicesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CountryEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    private CountryRepository countryRepository;

    @Autowired
    public CountryEndpoint(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCountryRequest")
    @ResponsePayload
    public GetInvoicesResponse getCountry(@RequestPayload GetInvoicesRequest request) {
        GetInvoicesResponse response = new GetInvoicesResponse();
        response.setInvoice(countryRepository.findCountry(request.getName()));

        return response;
    }
}

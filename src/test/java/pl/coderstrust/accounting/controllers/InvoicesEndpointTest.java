package pl.coderstrust.accounting.controllers;

import static org.hamcrest.core.*;
import org.jboss.logging.Messages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;


import javax.xml.transform.Source;
import java.io.IOException;

@SpringBootTest
@AutoConfigureMockMvc
class InvoicesEndpointTest {

    private static final int INVOICES_COUNT = 100;
    private static final String RESOURCES_PATH = "src/test/resources/SoapXmlRequests/";

    @Autowired
    private ApplicationContext applicationContext;
    private MockWebServiceClient mockClient;
    private Resource xsdSchema = new ClassPathResource("invoice.xsd");

    @Before
    public void init(){
    mockClient = MockWebServiceClient.createClient(applicationContext);
    }

    @Test
    public void shouldAddInvoice() throws IOException {
        Source requestPayload = getRequest("invoiceAddRequest.xml");
        mockClient
            .sendRequest(withPayload((requestPayload)))
            .andExpect(noFault())
            .andExpect(validPayload(xsdSchema))
            .andExpect(new ContainsStringMatcher(Messages.NO_ERRORS));
    }
}
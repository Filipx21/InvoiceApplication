package pl.coderstrust.accounting.mapper;

import oracle.webservices.databinding.SOAPElementSerializer;
import pl.coderstrust.accounting.model.Invoice;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.Text;
import java.util.Iterator;
import java.util.Map;

public class SoapSerializer extends SOAPElementSerializer {

    private Map initParams;
    protected SOAPFactory soapFactory;
    protected Invoice invoice;

    public SoapSerializer() {
    }

    public void init(Map prop) {
        initParams = prop;
        try {
            soapFactory = SOAPFactory.newInstance();
        } catch (SOAPException e) {
            e.printStackTrace();
        }
    }

    public Object deserialize(SOAPElement ele) {
        String str = "";
        for (Iterator i = ele.getChildElements(); i.hasNext(); ) {
            Object obj = i.next();
            if (obj instanceof Text) {
                str += (((Text) obj).getValue());
            }
        }
        return new invoice(str);
    }

    public SOAPElement serialize(QName qname, Object obj) {
        SOAPElement xml = null;
        try {
            xml = soapFactory.createElement(
                qname.getLocalPart(),
                qname.getPrefix(),
                qname.getNamespaceURI());
            xml.addTextNode(((invoice) obj).getString());
        } catch (SOAPException e) {
            e.printStackTrace();
        }
        return xml;
    }
}

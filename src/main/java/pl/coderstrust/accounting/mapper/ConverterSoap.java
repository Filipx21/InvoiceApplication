//package pl.coderstrust.accounting.mapper;
//
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Mappings;
//import pl.coderstrust.accounting.model.Invoice;
//import pl.coderstrust.accounting.model.soap.InvoiceSoap;
//
//@Mapper
//public interface ConverterSoap  {
//
//    @Mappings({
//        @Mapping(target = "id", source = "invoiceSoap.id"),
//        @Mapping(target = "date", source = "invoiceSoap.date",
//            dateFormat = "yyyy-MM-dd"),
//        @Mapping(target = "buyer", source = "invoiceSoap.buyer"),
//        @Mapping(target = "seller", source = "invoiceSoap.seller"),
//        @Mapping(target = "entries", source = "invoiceSoap.entries")})
//    Invoice toInvoice(InvoiceSoap invoiceHib);
//
//    @Mappings({
//        @Mapping(target = "id", source = "invoice.id"),
//        @Mapping(target = "date", source = "invoice.date",
//            dateFormat = "yyyy-MM-dd"),
//        @Mapping(target = "buyer", source = "invoice.buyer"),
//        @Mapping(target = "seller", source = "invoice.seller"),
//        @Mapping(target = "entries", source = "invoice.entries")})
//    InvoiceSoap toInvoiceSoap(Invoice invoice);
//
//}

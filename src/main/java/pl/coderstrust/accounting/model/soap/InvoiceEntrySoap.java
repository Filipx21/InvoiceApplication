//package pl.coderstrust.accounting.model.soap;
//
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import java.math.BigDecimal;
//import java.util.Objects;
//
//@Entity
//public class InvoiceEntrySoap {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private String description;
//    private BigDecimal price;
//    private int vatValue;
//    @Enumerated(EnumType.ORDINAL)
//    private VatSoap vatRate;
//
//    public InvoiceEntrySoap() {
//    }
//
//    public InvoiceEntrySoap(Long id, String description, BigDecimal price, int vatValue,
//                            VatSoap vatRate) {
//        this.id = id;
//        this.description = description;
//        this.price = price;
//        this.vatValue = vatValue;
//        this.vatRate = vatRate;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public BigDecimal getPrice() {
//        return price;
//    }
//
//    public void setPrice(BigDecimal price) {
//        this.price = price;
//    }
//
//    public int getVatValue() {
//        return vatValue;
//    }
//
//    public void setVatValue(int vatValue) {
//        this.vatValue = vatValue;
//    }
//
//    public VatSoap getVatRate() {
//        return vatRate;
//    }
//
//    public void setVatRate(VatSoap vatRate) {
//        this.vatRate = vatRate;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null || getClass() != obj.getClass()) {
//            return false;
//        }
//        InvoiceEntrySoap that = (InvoiceEntrySoap) obj;
//        return vatValue == that.vatValue
//            && Objects.equals(id, that.id)
//            && Objects.equals(description, that.description)
//            && Objects.equals(price, that.price)
//            && vatRate == that.vatRate;
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, description, price, vatValue, vatRate);
//    }
//
//    @Override
//    public String toString() {
//        return "InvoiceEntry{"
//            + "id=" + id
//            + ", description='" + description + '\''
//            + ", price=" + price
//            + ", vatValue=" + vatValue
//            + ", vatRate=" + vatRate
//            + '}';
//    }
//}

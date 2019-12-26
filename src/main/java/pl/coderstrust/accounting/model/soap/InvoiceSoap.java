package pl.coderstrust.accounting.model.soap;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
public class InvoiceSoap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CompanySoap buyer;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private CompanySoap seller;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<InvoiceEntrySoap> entries;

    public InvoiceSoap() {
    }

    public InvoiceSoap(InvoiceSoap invoiceSoap) {
        setId(invoiceSoap.getId());
        setDate(invoiceSoap.getDate());
        setBuyer(invoiceSoap.getBuyer());
        setSeller(invoiceSoap.getSeller());
        setEntries(invoiceSoap.getEntries());
    }

    public InvoiceSoap(Long id, LocalDate date, CompanySoap buyer,
                       CompanySoap seller, List<InvoiceEntrySoap> entries) {
        this.id = id;
        this.date = date;
        this.buyer = buyer;
        this.seller = seller;
        this.entries = entries;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public CompanySoap getBuyer() {
        return buyer;
    }

    public void setBuyer(CompanySoap buyer) {
        this.buyer = buyer;
    }

    public CompanySoap getSeller() {
        return seller;
    }

    public void setSeller(CompanySoap seller) {
        this.seller = seller;
    }

    public List<InvoiceEntrySoap> getEntries() {
        return entries;
    }

    public void setEntries(List<InvoiceEntrySoap> entries) {
        this.entries = entries;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        InvoiceSoap invoice = (InvoiceSoap) obj;
        return Objects.equals(id, invoice.id)
            && Objects.equals(date, invoice.date)
            && Objects.equals(buyer, invoice.buyer)
            && Objects.equals(seller, invoice.seller)
            && Objects.equals(entries, invoice.entries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, buyer, seller, entries);
    }

    @Override
    public String toString() {
        return "InvoiceSoap{"
            + "id=" + id
            + ", date=" + date
            + ", buyer=" + buyer
            + ", seller=" + seller
            + ", entries=" + entries
            + '}';
    }
}

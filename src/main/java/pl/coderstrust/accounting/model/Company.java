package pl.coderstrust.accounting.model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tin;
    private String address;
    private String name;

    public Company() {
    }

    public Company(Long id, String tin, String address, String name) {
        this.id = id;
        this.tin = tin;
        this.address = address;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTin() {
        return tin;
    }

    public void setTin(String tin) {
        this.tin = tin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Company company = (Company) obj;
        return Objects.equals(id, company.id)
            && Objects.equals(tin, company.tin)
            && Objects.equals(address, company.address)
            && Objects.equals(name, company.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tin, address, name);
    }

    @Override
    public String toString() {
        return "Company{"
            + "id=" + id
            + ", tin='" + tin + '\''
            + ", address='" + address + '\''
            + ", name='" + name + '\''
            + '}';
    }
}

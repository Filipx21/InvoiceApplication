package pl.coderstrust.accounting.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "vat")
@XmlEnum
public enum Vat {
    STANDARD_23(23),
    REDUCED_8(8),
    REDUCED_7(7),
    REDUCED_5(5),
    REDUCED_4(4),
    REDUCED_0(0),
    TAX_FREE(0);

    private final int value;

    Vat(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value + "";
    }

    public String value() {
        return name();
    }

    public static Vat fromValue(String v) {
        return valueOf(v);
    }
}

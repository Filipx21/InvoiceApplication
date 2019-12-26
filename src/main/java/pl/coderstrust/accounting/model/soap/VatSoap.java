package pl.coderstrust.accounting.model.soap;

public enum VatSoap {
    STANDARD_23(23),
    REDUCED_8(8),
    REDUCED_7(7),
    REDUCED_5(5),
    REDUCED_4(4),
    REDUCED_0(0),
    TAX_FREE(0);

    private final int value;

    VatSoap(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value + "";
    }
}

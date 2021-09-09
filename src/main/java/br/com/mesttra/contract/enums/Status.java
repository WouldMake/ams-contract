package br.com.mesttra.contract.enums;

public enum Status {

    STARTED("STARTED"), SENT_TO_ROSTER("SENT_TO_ROSTER"), SENT_TO_FINANCIAL("SENT_TO_FINANCIAL"), DONE("DONE");

    private String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

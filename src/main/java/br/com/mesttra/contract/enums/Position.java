package br.com.mesttra.contract.enums;

public enum Position {

    KEEPER("KEEPER"), DEFENDER("DEFENDER"), MIDFIELDER("MIDFIELDER"), FORWARD("FORWARD");

    private String value;

    private Position(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

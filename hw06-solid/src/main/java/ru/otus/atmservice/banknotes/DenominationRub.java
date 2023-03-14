package ru.otus.atmservice.banknotes;

public enum DenominationRub implements Denomination {
    СТО(100),
    ПЯТЬСОТ(500),
    ТЫСЯЧА(1000),
    ДВЕ_ТЫСЯЧИ(2000),
    ПЯТЬ_ТЫСЯЧ(5000);

    private int value;

    DenominationRub(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}

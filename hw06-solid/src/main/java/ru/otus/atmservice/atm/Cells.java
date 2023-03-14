package ru.otus.atmservice.atm;

import ru.otus.atmservice.banknotes.Banknote;

import java.util.List;

public interface Cells {
    void add(List<Banknote> addBanknotes);

    List<Banknote> getBanknotes(int sum);

    List<Banknote> getBanknotes();

    int getSumOfBanknotes();
}

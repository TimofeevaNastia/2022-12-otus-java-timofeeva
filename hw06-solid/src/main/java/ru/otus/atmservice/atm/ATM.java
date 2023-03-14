package ru.otus.atmservice.atm;

import ru.otus.atmservice.banknotes.Banknote;

import java.util.List;

public interface ATM extends TakeMoneyATM, PutMoneyATM {
    int getTotalSum();

    List<Banknote> getBanknotes();
}

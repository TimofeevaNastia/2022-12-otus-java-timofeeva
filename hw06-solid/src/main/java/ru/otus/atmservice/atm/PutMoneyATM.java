package ru.otus.atmservice.atm;

import ru.otus.atmservice.account.Account;
import ru.otus.atmservice.banknotes.Banknote;

public interface PutMoneyATM {
    void addMoneyToAccount(Account account, Banknote... banknotes);

    void addMoney(Banknote... banknotes);
}

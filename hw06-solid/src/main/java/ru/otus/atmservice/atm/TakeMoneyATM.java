package ru.otus.atmservice.atm;

import ru.otus.atmservice.account.Account;
import ru.otus.atmservice.banknotes.Banknote;

import java.util.List;

public interface TakeMoneyATM {
    List<Banknote> getMoneyFromAccount(Account account, int sum);
}

package ru.otus.atmservice.atm;

import ru.otus.atmservice.PrintService;
import ru.otus.atmservice.account.Account;
import ru.otus.atmservice.banknotes.Banknote;

import java.util.List;

public class ATMImpl implements ATM {
    private final PrintService printService = new PrintService();
    private final ATMService atm;

    public ATMImpl(List<Banknote> banknote) {
        atm = new ATMService(banknote);
    }

    public List<Banknote> getMoneyFromAccount(Account account, int sum) {
        var money = atm.getMoneyFromAccount(account, sum);
        printService.printAccountSum(account.getSumAccount());
        return money;
    }

    public void addMoneyToAccount(Account account, Banknote... banknotes) {
        var sum = atm.addMoneyToAccount(account, List.of(banknotes));
        printService.printAddedSum(sum);
        printService.printAccountSum(account.getSumAccount());
    }

    public void addMoney(Banknote... banknotes) {
        var sum = atm.addMoney(List.of(banknotes));
        printService.printAddedSum(sum);
    }

    public int getTotalSum() {
        return atm.getTotalSum();
    }

    public List<Banknote> getBanknotes() {
        return atm.getCellsRub().getBanknotes();
    }
}

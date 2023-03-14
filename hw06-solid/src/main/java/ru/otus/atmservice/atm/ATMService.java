package ru.otus.atmservice.atm;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.otus.atmservice.CalculationsATM;
import ru.otus.atmservice.account.Account;
import ru.otus.atmservice.banknotes.Banknote;
import ru.otus.atmservice.exceptions.MoneyNotEnoughException;

import java.util.List;

@Slf4j
public class ATMService {
    @Getter
    private final Cells cellsRub;
    @Getter
    private int totalSum;

    public ATMService() {
        cellsRub = new CellsRub();
    }

    public ATMService(List<Banknote> banknote) {
        this();
        cellsRub.add(banknote);
        totalSum = cellsRub.getSumOfBanknotes();
    }


    public int addMoneyToAccount(Account account, List<Banknote> addBanknotes) {
        cellsRub.add(addBanknotes);
        int addedSum = CalculationsATM.calcSumOfBanknotes(addBanknotes);
        account.plusSum(addedSum);
        addTotalSum(addedSum);
        return addedSum;
    }

    public int addMoney(List<Banknote> addBanknotes) {
        cellsRub.add(addBanknotes);
        int addedSum = CalculationsATM.calcSumOfBanknotes(addBanknotes);
        addTotalSum(addedSum);
        return addedSum;
    }

    public List<Banknote> getMoneyFromAccount(Account account, int sumAmount) {
        if (!account.enoughMoney(sumAmount))
            throw new MoneyNotEnoughException("У клиента недостаточно средств для выдачи");
        if (sumAmount > totalSum)
            throw new MoneyNotEnoughException("В банкомате недостаточно средств для выдачи");
        var result = cellsRub.getBanknotes(sumAmount);
        account.minusSum(sumAmount);
        minusTotalSum(sumAmount);
        return result;
    }

    private void addTotalSum(int sum) {
        this.totalSum += sum;
    }

    private void minusTotalSum(int sum) {
        this.totalSum -= sum;
    }
}

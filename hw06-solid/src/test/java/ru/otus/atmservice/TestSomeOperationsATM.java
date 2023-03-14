package ru.otus.atmservice;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.otus.atmservice.account.Account;
import ru.otus.atmservice.account.AccountImpl;
import ru.otus.atmservice.atm.ATM;
import ru.otus.atmservice.atm.ATMImpl;
import ru.otus.atmservice.banknotes.Banknote;
import ru.otus.atmservice.banknotes.DenominationRub;
import ru.otus.atmservice.exceptions.MoneyNotEnoughException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class TestSomeOperationsATM {
    List<Banknote> banknoteList = List.of(new Banknote(DenominationRub.ТЫСЯЧА, 4),
            new Banknote(DenominationRub.ПЯТЬ_ТЫСЯЧ, 1),
            new Banknote(DenominationRub.ПЯТЬСОТ, 9),
            new Banknote(DenominationRub.СТО, 1));
    ATM atm = new ATMImpl(banknoteList);
    int expectedTotalSumATM = calcSumBanknotes(banknoteList);
    double accountSum = 100_000;
    Account account = new AccountImpl(accountSum);

    @Test
    public void checkSomeOperations() throws MoneyNotEnoughException {
        log.info("Текущая сумма в банкомате: {}", expectedTotalSumATM);
        checkAddSumToATM(new Banknote(DenominationRub.ТЫСЯЧА, 40),
                new Banknote(DenominationRub.ПЯТЬ_ТЫСЯЧ, 10),
                new Banknote(DenominationRub.ПЯТЬСОТ, 10),
                new Banknote(DenominationRub.СТО, 50));
        checkAddSum(new Banknote(DenominationRub.ТЫСЯЧА, 4),
                new Banknote(DenominationRub.ПЯТЬ_ТЫСЯЧ, 1),
                new Banknote(DenominationRub.ПЯТЬСОТ, 0),
                new Banknote(DenominationRub.ДВЕ_ТЫСЯЧИ, 1),
                new Banknote(DenominationRub.СТО, 1));
        checkGetSum(20_000);
        checkGetSum(34_600);
    }

    private void checkAddSumToATM(Banknote... banknotes) {
        var addSumToATM = calcSumBanknotes(List.of(banknotes));
        log.info("Добавить в банкомат: {}", addSumToATM);
        atm.addMoney(banknotes);
        expectedTotalSumATM += addSumToATM;
        assertThat(atm.getTotalSum()).as("Сумма в банкомате не равна ожидаемой").isEqualTo(expectedTotalSumATM);
        log.info("----------------------------------------------");
    }

    private void checkAddSum(Banknote... banknotes) {
        var addSumToAccount = calcSumBanknotes(List.of(banknotes));
        log.info("Текущая сумма в банкомате: {}", atm.getTotalSum());
        log.info("Добавить в банкомат на счёт: {}", addSumToAccount);
        atm.addMoneyToAccount(account, banknotes);
        expectedTotalSumATM += addSumToAccount;
        var actualSumBankomat = atm.getTotalSum();
        accountSum = accountSum + addSumToAccount;
        log.info("Текущая сумма в банкомате: {}", actualSumBankomat);
        log.info("Остаток средств на счете: {}", account.getSumAccount());
        assertThat(actualSumBankomat).as("Сумма в банкомате не равна ожидаемой").isEqualTo(expectedTotalSumATM);
        assertThat(account.getSumAccount()).as("Сумма на счёте не равна ожидаемой").isEqualTo(accountSum);
        log.info("----------------------------------------------");
    }

    private void checkGetSum(int getSum) {
        log.info("Получить из банкомата: {}", getSum);
        var getMoney1 = atm.getMoneyFromAccount(account, getSum);
        accountSum = accountSum - getSum;
        expectedTotalSumATM -= getSum;
        var actualSumBankomat = atm.getTotalSum();
        log.info("Текущая сумма в банкомате: {}", actualSumBankomat);
        log.info("Выданы купюры: {}", getMoney1);
        assertThat(actualSumBankomat).as("Сумма в банкомате не равна ожидаемой").isEqualTo(expectedTotalSumATM);
        assertThat(account.getSumAccount()).as("Сумма на счёте не равна ожидаемой").isEqualTo(accountSum);
        log.info("----------------------------------------------");
    }

    private int calcSumBanknotes(List<Banknote> banknotes) {
        return banknotes.stream().mapToInt(x -> x.getDenomination().getValue() * x.getCount()).sum();
    }
}

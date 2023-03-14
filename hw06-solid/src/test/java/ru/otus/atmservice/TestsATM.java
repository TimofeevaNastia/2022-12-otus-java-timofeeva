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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class TestsATM {
    List<Banknote> banknoteList = List.of(new Banknote(DenominationRub.ТЫСЯЧА, 4),
            new Banknote(DenominationRub.ПЯТЬ_ТЫСЯЧ, 1),
            new Banknote(DenominationRub.ПЯТЬСОТ, 9),
            new Banknote(DenominationRub.СТО, 1));
    ATM atm = new ATMImpl(banknoteList);
    int expectedTotalSumATM = calcSumBanknotes(banknoteList);

    @Test
    public void checkSuccessGetSum() {
        log.info("---------- Тест: Получение суммы из банкомата --------------");
        var getSum = 10600;
        var accountSum = 150_000.350;
        double expectedRestSumAccount = accountSum - getSum;
        var expectedBanknotes = new ArrayList<Banknote>();
        expectedBanknotes.add(new Banknote(DenominationRub.ПЯТЬ_ТЫСЯЧ, 1));
        expectedBanknotes.add(new Banknote(DenominationRub.ТЫСЯЧА, 4));
        expectedBanknotes.add(new Banknote(DenominationRub.ПЯТЬСОТ, 3));
        expectedBanknotes.add(new Banknote(DenominationRub.СТО, 1));
        Account account = new AccountImpl(accountSum);

        log.info("Текущая сумма в банкомате: {}", atm.getTotalSum());
        log.info("Текущая сумма на счете: {}", accountSum);
        log.info("Получить из банкомата: {}", getSum);
        var actualBanknotes = atm.getMoneyFromAccount(account, getSum);
        log.info("Выданы купюры:");
        actualBanknotes.forEach(x -> log.info("Номинал: {}, Количество: {}", x.getDenomination(), x.getCount()));
        log.info("Текущая сумма в банкомате: {}", atm.getTotalSum());

        assertThat(expectedBanknotes.containsAll(actualBanknotes)).isTrue();
        assertThat(account.getSumAccount()).isEqualTo(expectedRestSumAccount);
        log.info("------------------------------------------------------------");
    }


    @Test
    public void checkSuccessAddSum() {
        log.info("---------- Тест: Добавление суммы в банкомат --------------");
        var totalSumATM = atm.getTotalSum();
        var accountSum = 100_000;
        var addBanknotes = new Banknote[]{new Banknote(DenominationRub.ТЫСЯЧА, 4),
                new Banknote(DenominationRub.ПЯТЬ_ТЫСЯЧ, 1),
                new Banknote(DenominationRub.ПЯТЬСОТ, 0),
                new Banknote(DenominationRub.ДВЕ_ТЫСЯЧИ, 1),
                new Banknote(DenominationRub.СТО, 1)};
        var addSum = calcSumBanknotes(List.of(addBanknotes));
        double restSumAccount = accountSum + addSum;
        Account account = new AccountImpl(accountSum);

        log.info("Текущая сумма в банкомате: {}", totalSumATM);
        log.info("Текущая сумма на счете: {}", accountSum);
        log.info("Добавить в банкомат: {}", addSum);
        atm.addMoneyToAccount(account, addBanknotes);
        assertThat(account.getSumAccount()).isEqualTo(restSumAccount);
        assertThat(atm.getTotalSum()).isEqualTo(totalSumATM + addSum);
        log.info("------------------------------------------------------------");
    }

    @Test
    public void notAccessedBanknote() {
        log.info("---------- Тест: Нет подходящих банкнот в банкомате --------------");
        var accountSum = 100_000;
        var getSum = 200;
        Account account = new AccountImpl(accountSum);
        boolean error = false;
        log.info("Текущие купюры в банкомате: {}", atm.getBanknotes());
        log.info("Получить из банкомата: {}", getSum);
        try {
            atm.getMoneyFromAccount(account, getSum);
        } catch (MoneyNotEnoughException e) {
            e.printStackTrace();
            assertThat(e.getMessage()).isEqualTo("Нет подходящих банкнот для выдачи");
            error = true;
        }
        assertThat(error).isEqualTo(true);
        assertThat(account.getSumAccount()).isEqualTo(accountSum);
        log.info("------------------------------------------------------------");
    }

    @Test
    public void notEnoughATMSum() {
        log.info("---------- Тест: Недостаточно средств в банкомате --------------");
        var accountSum = expectedTotalSumATM + 10_000;
        var getSum = expectedTotalSumATM + 5000;
        Account account = new AccountImpl(accountSum);
        boolean error = false;
        try {
            atm.getMoneyFromAccount(account, getSum);
        } catch (MoneyNotEnoughException e) {
            e.printStackTrace();
            assertThat(e.getMessage()).isEqualTo("В банкомате недостаточно средств для выдачи");
            error = true;
        }
        assertThat(error).isEqualTo(true);
        log.info("------------------------------------------------------------");
    }

    @Test
    public void notEnoughAccountSum() {
        log.info("---------- Тест: Недостаточно средств у клиента --------------");
        var accountSum = 1000;
        var getSum = 5000;
        Account account = new AccountImpl(accountSum);
        boolean error = false;
        try {
            atm.getMoneyFromAccount(account, getSum);
        } catch (MoneyNotEnoughException e) {
            e.printStackTrace();
            assertThat(e.getMessage()).isEqualTo("У клиента недостаточно средств для выдачи");
            error = true;
        }
        assertThat(error).isEqualTo(true);
        log.info("------------------------------------------------------------");
    }

    private int calcSumBanknotes(List<Banknote> banknotes) {
        return banknotes.stream().mapToInt(x -> x.getDenomination().getValue() * x.getCount()).sum();
    }
}

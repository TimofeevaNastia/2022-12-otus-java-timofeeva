package ru.otus.atmservice;

import ru.otus.atmservice.banknotes.Banknote;
import ru.otus.atmservice.banknotes.Denomination;
import ru.otus.atmservice.exceptions.MoneyNotEnoughException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CalculationsATM {

    public CalculationsATM() {
    }

    public static Map<Denomination, Integer> calcBanknotesBySum(int sumAmount, List<Banknote> banknotesFrom) {
        Map<Denomination, Integer> giveDenominations = new HashMap<>();
        Iterator<Banknote> iterator = banknotesFrom.iterator();
        while (sumAmount != 0) {
            Banknote banknote = iterator.next();
            if (banknote.getDenomination().getValue() <= sumAmount && banknote.getCount() > 0) {
                int newCountBanknotes = sumAmount / banknote.getDenomination().getValue();
                if (banknote.getCount() - newCountBanknotes < 0) {
                    newCountBanknotes = banknote.getCount();
                }
                sumAmount = sumAmount - banknote.getDenomination().getValue() * newCountBanknotes;
                int newCountDenomination = banknote.getCount() - newCountBanknotes;
                giveDenominations.put(banknote.getDenomination(), newCountBanknotes);
                banknote.setCount(newCountDenomination);
            }
            if (sumAmount < 0)
                throw new ArithmeticException("Ошибка - отрицательная сумма");
            if (!iterator.hasNext() && sumAmount > 0)
                throw new MoneyNotEnoughException("Нет подходящих банкнот для выдачи");
        }
        return giveDenominations;
    }

    public static int calcSumOfBanknotes(List<Banknote> banknotes) {
        return banknotes.stream().mapToInt(x -> x.getDenomination().getValue() * x.getCount()).sum();
    }
}

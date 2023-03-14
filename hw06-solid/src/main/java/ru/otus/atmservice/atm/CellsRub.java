package ru.otus.atmservice.atm;

import lombok.Data;
import ru.otus.atmservice.CalculationsATM;
import ru.otus.atmservice.banknotes.Banknote;
import ru.otus.atmservice.banknotes.Denomination;
import ru.otus.atmservice.banknotes.DenominationRub;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import static ru.otus.atmservice.CalculationsATM.calcBanknotesBySum;
import static ru.otus.atmservice.CalculationsATM.calcSumOfBanknotes;

@Data
public class CellsRub implements Cells {
    private Map<Denomination, Integer> cells;
    private CalculationsATM calculations;

    public CellsRub() {
        cells = new TreeMap<>((o1, o2) -> o2.getValue() - o1.getValue());
        Arrays.stream(DenominationRub.values()).forEach(x -> cells.put(x, 0));

    }

    public void add(List<Banknote> addBanknotes) {
        addBanknotes.forEach(addBanknote -> {
            cells.put(addBanknote.getDenomination(), cells.get(addBanknote.getDenomination()) + addBanknote.getCount());
        });
    }

    public List<Banknote> getBanknotes(int sum) {
        var res = calcBanknotesBySum(sum, getBanknotes());
        return res.entrySet().stream().map(x -> new Banknote(x.getKey(), x.getValue())).collect(Collectors.toList());
    }


    public int getSumOfBanknotes() {
        return calcSumOfBanknotes(getBanknotes());
    }

    public List<Banknote> getBanknotes() {
        return cells.entrySet().stream()
                .map(x -> new Banknote(x.getKey(), x.getValue()))
                .collect(Collectors.toList());
    }
}

package ru.otus.atmservice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PrintService {

    public void printAddedSum(int sum) {
        log.info("Внесенная сумма: {}", sum);
    }

    public void printAccountSum(double sum) {
        log.info("Остаток средств на счете: {}", sum);
    }

    public void printATMTotalSum(int sum) {
        log.info("Средств в банкомате: {}", sum);
    }
}

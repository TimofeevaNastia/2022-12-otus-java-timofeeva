package ru.otus.atmservice.account;

public interface Account {
    double getSumAccount();

    void setSumAccount(double sum);

    void plusSum(double sum);

    void minusSum(double sum);

    boolean enoughMoney(double sumAmount);
}

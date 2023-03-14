package ru.otus.atmservice.account;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountImpl implements Account {
    private double sumAccount;

    public void plusSum(double sum) {
        setSumAccount(sumAccount + sum);
    }

    public void minusSum(double sum) {
        setSumAccount(sumAccount - sum);
    }

    public boolean enoughMoney(double sum) {
        return sumAccount >= sum;
    }
}

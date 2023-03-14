package ru.otus.atmservice.banknotes;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Banknote  {
    private Denomination denomination;
    private int count;
}

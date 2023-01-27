package homework;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {
    private final TreeMap<Customer, String> map;

    public CustomerService() {
        map = new TreeMap<>(Comparator.comparingLong(Customer::getScores));
    }
    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    public Map.Entry<Customer, String> getSmallest() {
        Map.Entry<Customer, String> entry = map.firstEntry();
        return modifyEntry(entry);
        //Возможно, чтобы реализовать этот метод, потребуется посмотреть как Map.Entry сделан в jdk // это "заглушка, чтобы скомилировать"
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        Map.Entry<Customer, String> entry = map.higherEntry(customer);
        return modifyEntry(entry);
    }

    public void add(Customer customer, String data) {
        map.put(customer, data);
    }

    private Map.Entry<Customer, String> modifyEntry(Map.Entry<Customer, String> entry) {
        if (entry != null) {
            Customer oldCustomer = entry.getKey();
            Customer newCustomer = new Customer(oldCustomer.getId(), oldCustomer.getName(), oldCustomer.getScores());
            entry = Map.entry(newCustomer, entry.getValue());
        }
        return entry;
    }
}
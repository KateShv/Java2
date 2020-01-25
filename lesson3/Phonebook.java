package lesson3;

import java.util.*;

public class Phonebook {

    private HashMap<String, ArrayList<Person>> map = new HashMap<>();

    void addToMap(String name, String phone, String mail) {
        if (map.containsKey(name)) {
            map.get(name).add(new Person(phone, mail));
        } else {
            ArrayList<Person> temp = new ArrayList<>();
            temp.add(new Person(phone, mail));
            map.put(name, temp);
        }
    }

    ArrayList<String> getPhones(String name) {
        if (!map.containsKey(name)) return null;
        ArrayList<String> temp = new ArrayList<>();
        for (Person person : map.get(name)) {
            temp.add(person.getPhone());
        }
        return temp;
    }

    ArrayList<String> getMails(String name) {
        if (!map.containsKey(name)) return null;
        ArrayList<String> temp = new ArrayList<>();
        for (Person person : map.get(name)) {
            temp.add(person.getEmail());
        }
        return temp;
    }

}

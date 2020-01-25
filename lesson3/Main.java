package lesson3;

import java.util.*;

public class Main {

    private static TreeSet<String> words(String[] arr) {
        return new TreeSet<>(Arrays.asList(arr));
    }

    private static HashMap<String, Integer> counterWords(String[] arr) {
        HashMap<String, Integer> map = new HashMap<>();
        for (String s : arr) {
            if (map.containsKey(s)) {
                map.put(s, map.get(s) + 1);
            } else {
                map.put(s, 1);
            }
        }
        return map;
    }

    public static void main(String[] args) {

        //Забавный кусок из ГП =)
        String[] arr = {"Один", "волшебник", "в", "больших", "летах", "был", "одет", "в",
                "длинную", "ночную", "рубашку", "в", "цветочек", ".", "Второй", "явно",
                "служащий", "Министерства", "протягивал", "первому", "брюки", "в", "полоску", ".",
                "Надень", "их", "Арчи", "-", "кричал", "он", "в", "отчаянии", "-",
                "не", "валяй", "дурака", "!", "Нельзя", "разгуливать", "в", "таком", "виде",
                "магл-привратник", "наверняка", "что-то", "заподозрил", "...", "Я", "купил", "это", "в",
                "магловском", "магазине", "-", "упрямо", "возражал", "старик", "-", "маглы", "такую",
                "одежду", "носят", ".", "Магловские", "женщины", "их", "носят", "а", "не", "мужчины", ".",
                "мужчины", "носят", "вот", "это", "-", "настаивал", "волшебник", "из", "Министерства",
                "потрясая", "полосатыми", "брюками", ".", "не", "надену", "!", "-", "негодовал",
                "Арчи", "-", "Я", "люблю", "свежий", "ветерок", "вокруг", "интимных", "частей", "тела",
                "так", "что", "спасибо", "."};

        System.out.println(words(arr));
        System.out.println(counterWords(arr));

        System.out.println();

        Phonebook book = new Phonebook();

        book.addToMap("Loki", "89563325674", "loki_pretty_boy@gmail.com");
        book.addToMap("Tor", "89320654786", "i_am_a_god@gmail.com");
        book.addToMap("Spiderman", "89206458712", "peter_parker@gmail.com");
        book.addToMap("Ironman", "89804236513", "toni_stark@gmail.com");
        book.addToMap("DrStrange", "89056548561", "stiven_vinsent_strange@gmail.com");
        book.addToMap("Blackpanther", "89263245163", "tchalla@gmail.com");
        book.addToMap("Tor", "89106325647", "tor@gmail.com");
        book.addToMap("DrStrange", "89296988672", "time_lord@gmail.com");
        book.addToMap("Ironman", "89387138995", "iron_man@gmail.com");
        book.addToMap("Tor", "89271846583", "thunderer@gmail.com");

        System.out.println("DrStrange: " + book.getPhones("DrStrange") + " - " + book.getMails("DrStrange"));
        System.out.println("Tor: " + book.getPhones("Tor") + " - "  + book.getMails("Tor"));
        System.out.println("Ironman: " + book.getPhones("Ironman") + " - "  + book.getMails("Ironman"));

    }
}

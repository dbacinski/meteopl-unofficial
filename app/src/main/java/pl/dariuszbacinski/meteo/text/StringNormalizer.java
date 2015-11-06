package pl.dariuszbacinski.meteo.text;

import java.util.HashMap;
import java.util.Map;

public class StringNormalizer {
    private static Map<Character, Character> normalizedPlMap = new HashMap<Character, Character>() {
        {
            put('ą', 'a');
            put('ć', 'c');
            put('ę', 'e');
            put('ł', 'l');
            put('ń', 'n');
            put('ó', 'o');
            put('ś', 's');
            put('ż', 'z');
            put('ź', 'z');
        }
    };

    public static String normalizePlLang(String plString) {
        if (plString==null || plString.isEmpty()) return "";

        char[] charArray = plString.toCharArray();

        for (int i = 0; i < plString.length(); i++) {
            Character normalized = normalizedPlMap.get(charArray[i]);

            if (normalized != null) {
                charArray[i] = normalized;
            }
        }

        return String.valueOf(charArray);
    }
}

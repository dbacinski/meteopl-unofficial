package pl.dariuszbacinski.meteo.location;

import java.util.HashMap;
import java.util.Map;

import pl.dariuszbacinski.meteo.rx.Indexed;

public class IndexedLocation extends Indexed<Location> {

    public IndexedLocation(int index, int originalIndex, Location value) {
        super(index, originalIndex, value);
    }

    public String getLowerCaseName() {
        return getValue().getName().toLowerCase();
    }

    public String getNormalizedName() {
        return StringNormalizer.normalizePlLang(getLowerCaseName());
    }

    @Override
    public IndexedLocation setIndex(int index) {
        super.setIndex(index);
        return this;
    }

    public static class StringNormalizer {
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
}

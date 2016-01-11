package pl.dariuszbacinski.meteo.component.text;

import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

import static com.eccyan.optional.Optional.ofNullable;
import static rx.Observable.from;

public class StringJoiner {
    private static final String EMPTY_STRING = "";
    private NotEmptyStringsFunc notEmptyStrings;
    private ConcatWithSeparatorFunc2 concatenateWithSeparator;

    public StringJoiner(String separator) {
        notEmptyStrings = new NotEmptyStringsFunc();
        concatenateWithSeparator = new ConcatWithSeparatorFunc2(ofNullable(separator).orElse(EMPTY_STRING));
    }

    public String join(String... strings) {
        return from(strings).filter(notEmptyStrings).reduce(concatenateWithSeparator).toBlocking().single();
    }

    static class NotEmptyStringsFunc implements Func1<String, Boolean> {
        @Override
        public Boolean call(String s) {
            return s != null && !s.isEmpty();
        }
    }

    static class ConcatWithSeparatorFunc2 implements Func2<String, String, String> {
        private String separator;

        public ConcatWithSeparatorFunc2(String separator) {
            this.separator = separator;
        }

        @Override
        public String call(String s, String s2) {
            return s + separator + s2;
        }
    }
}

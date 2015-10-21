package pl.dariuszbacinski.meteo.rx;

import java.util.Iterator;

public class NaturalNumbers implements Iterable<Integer> {

    private static class Holder {
        static final NaturalNumbers INSTANCE = new NaturalNumbers();
    }

    public static NaturalNumbers instance() {
        return Holder.INSTANCE;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {

            private int n = 0;

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public Integer next() {
                return n++;
            }

            @Override
            public void remove() {
                throw new RuntimeException("not supported");
            }
        };
    }

}
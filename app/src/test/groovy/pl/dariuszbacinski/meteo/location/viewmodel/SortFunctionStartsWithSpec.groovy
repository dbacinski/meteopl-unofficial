package pl.dariuszbacinski.meteo.location.viewmodel
import pl.dariuszbacinski.meteo.location.model.IndexedLocation
import pl.dariuszbacinski.meteo.location.model.Location
import pl.dariuszbacinski.meteo.shadow.ShadowRoboSpecification
import spock.lang.Unroll

class SortFunctionStartsWithSpec extends ShadowRoboSpecification {

    public static final int SAME_RANK = 0
    public static final int LEFT_RANK_HIGHER = -1
    public static final int RIGHT_RANK_HIGHER = 1

    @Unroll
    def "do not move names that does not start from \"prefix\" (#firstName, #secondName)"() {
        given:
            SortFunctionStartsWith sortFunction = new SortFunctionStartsWith("prefix")
        when:
            def sortIndex = sortFunction.call(new IndexedLocation(0, 0, new Location(firstName, 0, 0)), new IndexedLocation(0, 0, new Location(secondName, 0, 0)))
        then:
            sortIndex == expectedSortIndex
        where:
            firstName | secondName || expectedSortIndex
            ""        | ""         || SAME_RANK
            "aa"      | ""         || SAME_RANK
            ""        | "bb"       || SAME_RANK
            "aa"      | "bb"       || SAME_RANK
    }

    @Unroll
    def "do not move names with same prefix (#firstName, #secondName)"() {
        given:
            SortFunctionStartsWith sortFunction = new SortFunctionStartsWith("prefix")
        when:
            def sortIndex = sortFunction.call(new IndexedLocation(0, 0, new Location(firstName, 0, 0)), new IndexedLocation(0, 0, new Location(secondName, 0, 0)))
        then:
            sortIndex == expectedSortIndex
        where:
            firstName  | secondName || expectedSortIndex
            "prefix1"  | "prefix2"  || SAME_RANK
            "prefixAa" | "prefixBb" || SAME_RANK
            "prefixBb" | "PrefixAa" || SAME_RANK
            "prefix"   | "prefix"   || SAME_RANK
    }

    @Unroll
    def "move higher names with prefix (#firstName, #secondName)"() {
        given:
            SortFunctionStartsWith sortFunction = new SortFunctionStartsWith("prefix")
        when:
            def sortIndex = sortFunction.call(new IndexedLocation(0, 0, new Location(firstName, 0, 0)), new IndexedLocation(0, 0, new Location(secondName, 0, 0)))
        then:
            sortIndex == expectedSortIndex
        where:
            firstName | secondName || expectedSortIndex
            "prefix"  | "aaa"      || LEFT_RANK_HIGHER
            "aaa"     | "prefix"   || RIGHT_RANK_HIGHER

    }
}

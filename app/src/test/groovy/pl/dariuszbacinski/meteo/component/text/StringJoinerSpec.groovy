package pl.dariuszbacinski.meteo.component.text

import spock.lang.Specification

class StringJoinerSpec extends Specification {

    def "joins strings with separator"() {
        given:
            StringJoiner objectUnderTest = new StringJoiner(":")
        when:
            String joined = objectUnderTest.join("aaa", "bbb")
        then:
            joined == "aaa:bbb"
    }

    def "joins strings with empty separator"() {
        given:
            StringJoiner objectUnderTest = new StringJoiner("")
        when:
            String joined = objectUnderTest.join("aaa", "bbb")
        then:
            joined == "aaabbb"
    }

    def "joins strings with null separator"() {
        given:
            StringJoiner objectUnderTest = new StringJoiner(null)
        when:
            String joined = objectUnderTest.join("aaa", "bbb")
        then:
            joined == "aaabbb"
    }

    def "skips null string"() {
        given:
            StringJoiner objectUnderTest = new StringJoiner(":")
        when:
            String joined = objectUnderTest.join("aaa", null, "ccc")
        then:
            joined == "aaa:ccc"
    }

    def "skips empty string"() {
        given:
            StringJoiner objectUnderTest = new StringJoiner(":")
        when:
            String joined = objectUnderTest.join("aaa", "", "ccc")
        then:
            joined == "aaa:ccc"
    }

    def "skips null string at the end"() {
        given:
            StringJoiner objectUnderTest = new StringJoiner(":")
        when:
            String joined = objectUnderTest.join("aaa", null)
        then:
            joined == "aaa"
    }
}

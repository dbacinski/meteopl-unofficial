package pl.dariuszbacinski.meteo.component.text

import spock.lang.Specification
import spock.lang.Unroll


class StringNormalizerSpec extends Specification {

    @Unroll
    def "converts small polish letters to normalized form #inputString -> #expectedString"() {
        given:

        when:
            String normalizedString = StringNormalizer.normalizePlLang(inputString)
        then:
            normalizedString == expectedString
        where:
            inputString    || expectedString
            "ląd"          || "lad"
            "łódź"         || "lodz"
            "ąćęłńóśżź"    || "acelnoszz"
            "środa śląska" || "sroda slaska"
    }

    @Unroll
    def "handles empty input string: #inputString"() {
        when:
            String normalizedString = StringNormalizer.normalizePlLang(inputString)
        then:
            normalizedString == expectedString
        where:
            inputString || expectedString
            null        || ""
            ""          || ""
    }
}

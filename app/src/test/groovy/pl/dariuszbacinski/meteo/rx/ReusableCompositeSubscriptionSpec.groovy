package pl.dariuszbacinski.meteo.rx
import rx.Subscription
import rx.subscriptions.Subscriptions
import spock.lang.Specification

class ReusableCompositeSubscriptionSpec extends Specification {

    def "unsubscribes added subscriptions"() {
        given:
            ReusableCompositeSubscription objectUnderTest = new ReusableCompositeSubscription();
            Subscription subscriptionFirst = Subscriptions.empty();
            Subscription subscriptionSecond = Subscriptions.empty();
            objectUnderTest.add(subscriptionFirst)
            objectUnderTest.add(subscriptionSecond)
        when:
            objectUnderTest.unsubscribe()
        then:
            subscriptionFirst.isUnsubscribed()
            subscriptionSecond.isUnsubscribed()
    }

    def "adds subscriptions after unsuscribe"() {
        given:
            ReusableCompositeSubscription objectUnderTest = new ReusableCompositeSubscription();
            Subscription subscriptionFirst = Subscriptions.empty();
            objectUnderTest.add(subscriptionFirst)
            objectUnderTest.unsubscribe()
            Subscription subscriptionSecond = Subscriptions.empty();
        when:
            objectUnderTest.add(subscriptionSecond)
        then:
            subscriptionSecond.isUnsubscribed() == false
    }

    def "unsubscribes added subscriptions for a second time"() {
        given:
            ReusableCompositeSubscription objectUnderTest = new ReusableCompositeSubscription();
            Subscription subscriptionFirst = Subscriptions.empty();
            objectUnderTest.add(subscriptionFirst)
            objectUnderTest.unsubscribe()
            Subscription subscriptionSecond = Subscriptions.empty();
        when:
            objectUnderTest.add(subscriptionSecond)
            objectUnderTest.unsubscribe()
        then:
            subscriptionSecond.isUnsubscribed()
    }
}

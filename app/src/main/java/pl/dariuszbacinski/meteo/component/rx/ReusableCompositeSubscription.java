package pl.dariuszbacinski.meteo.component.rx;

import android.support.annotation.NonNull;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class ReusableCompositeSubscription implements Subscription {

    private CompositeSubscription compositeSubscription = new CompositeSubscription();

    public void add(@NonNull Subscription subscription) {
        if (isUnsubscribed()) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(subscription);
    }

    @Override
    public void unsubscribe() {
        compositeSubscription.unsubscribe();
    }

    @Override
    public boolean isUnsubscribed() {
        return compositeSubscription.isUnsubscribed();
    }

    public boolean hasSubscriptions() {
        return compositeSubscription.hasSubscriptions();
    }
}

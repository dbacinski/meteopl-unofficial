package rx.plugins;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import rx.Scheduler;
import rx.android.plugins.RxAndroidPlugins;
import rx.android.plugins.RxAndroidSchedulersHook;
import rx.schedulers.Schedulers;

public class RxImmediateSchedulersRule implements TestRule {


    @Override public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                reset();
                RxJavaPlugins.getInstance().registerSchedulersHook(new SchedulerHook());
                RxAndroidPlugins.getInstance().registerSchedulersHook(new AndroidSchedulerHook());
                base.evaluate();
                reset();
            }
        };
    }

    private void reset() {
        RxJavaPlugins.getInstance().reset();
        RxAndroidPlugins.getInstance().reset();
    }

    private static class SchedulerHook extends RxJavaSchedulersHook {
        @Override
        public Scheduler getIOScheduler() {
            return Schedulers.immediate();
        }

        @Override
        public Scheduler getNewThreadScheduler() {
            return Schedulers.immediate();
        }

        @Override
        public Scheduler getComputationScheduler() {
            return Schedulers.immediate();
        }
    }

    private static class AndroidSchedulerHook extends RxAndroidSchedulersHook {
        @Override public Scheduler getMainThreadScheduler() {
            return Schedulers.immediate();
        }
    }
}

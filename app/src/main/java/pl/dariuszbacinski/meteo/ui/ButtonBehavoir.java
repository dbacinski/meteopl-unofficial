package pl.dariuszbacinski.meteo.ui;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

public class ButtonBehavoir extends CoordinatorLayout.Behavior<Button> {

        public ButtonBehavoir(Context context, AttributeSet attrs) {
        }

        @Override
        public boolean layoutDependsOn(CoordinatorLayout parent, Button child, View dependency) {
            return dependency instanceof Snackbar.SnackbarLayout;
        }

        @Override
        public boolean onDependentViewChanged(CoordinatorLayout parent, Button child, View dependency) {
            float translationY = Math.min(0, dependency.getTranslationY() - dependency.getHeight());
            child.setTranslationY(translationY);
            return true;
        }

}

package pl.dariuszbacinski.meteo.component.view;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

public class RecyclerViewResizeBehavior extends CoordinatorLayout.Behavior<RecyclerView> {

    public RecyclerViewResizeBehavior(Context context, AttributeSet attrs) {
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, RecyclerView child, View dependency) {
        return dependency instanceof Button;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, RecyclerView child, View dependency) {
        int translationY = Math.min(0, (int) dependency.getTranslationY());
        child.getLayoutParams().height = getHeight((View) child.getParent()) + translationY - getHeight(dependency) - parent.getPaddingBottom();
        child.requestLayout();
        return true;
    }

    private int getHeight(View child) {
        return child.getMeasuredHeight();
    }
}

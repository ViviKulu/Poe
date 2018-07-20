package com.example.vivianbabiryekulumba.poe.utils;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;

public class AnimationBehavior extends CoordinatorLayout.Behavior<View>{

    public AnimationBehavior(){
        super();
    }

    public AnimationBehavior(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof Snackbar.SnackbarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float translationZ = Math.min(0, ViewCompat.getTranslationZ(dependency) - dependency.getHeight());
        ViewCompat.setTranslationZ(child, translationZ);
        return true;
    }

    public void onDependentViewRemoved(CoordinatorLayout parent, View child, View dependency){
        ViewCompat.animate(child).translationZ(0).start();
    }
}

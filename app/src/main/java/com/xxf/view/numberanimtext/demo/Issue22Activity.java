package com.xxf.view.numberanimtext.demo;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;


import com.xxf.view.numberanimtext.CharOrder;
import com.xxf.view.numberanimtext.NumberAnimTextView;

import java.util.Random;


public class Issue22Activity extends Activity {

    private /*lateinit*/ NumberAnimTextView tv;
    private /*lateinit*/ ViewGroup container;
    private ValueAnimator mTranslationYAnimator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_22);

        tv = findViewById(R.id.textView);
        container = findViewById(R.id.container);
    }

    public void performIssue22(View view) {
        Keyframe k0 = Keyframe.ofFloat(0.0f, 0.0f);
        Keyframe k1 = Keyframe.ofFloat(0.5f, 100f);
        Keyframe k2 = Keyframe.ofFloat(1.0f, 0.0f);
        PropertyValuesHolder translationY = PropertyValuesHolder.ofKeyframe("translationY", k0, k1, k2);

        if (mTranslationYAnimator != null) {
            mTranslationYAnimator.cancel();
        }
        mTranslationYAnimator = ObjectAnimator.ofPropertyValuesHolder(container, translationY);
        mTranslationYAnimator.setInterpolator(new LinearInterpolator());
        mTranslationYAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mTranslationYAnimator.setDuration(1000);
        mTranslationYAnimator.start();

        tv.addCharOrder(CharOrder.Number);
        tv.setText("1000000");
        tv.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animator) {
                long next = Long.parseLong(tv.getText().toString()) +
                    new Random().nextInt(100) - 50;
                tv.setText(String.valueOf(next));
            }
        });
    }
}

package com.xxf.view.numberanimtext;

import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description: 计数动画效果的textView 建议 最开始设置最小宽度
 * @Author: XGod
 * @CreateDate: 2020/11/10 17:34
 */
public class NumberCountAnimTextView extends TextView {
    private static final String TAG = NumberCountAnimTextView.class.getName();

    public NumberCountAnimTextView(Context context) {
        super(context);
    }

    public NumberCountAnimTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NumberCountAnimTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    AnimatorSet animator;

    @Override
    public void setText(CharSequence text, final TextView.BufferType type) {

        /**
         * 其他span不管
         * 第一步找到第一个完整的数字,比如 "10.20%"   "10.20$" 计数部分应该是其中的数字
         */
        try {
            if (text instanceof String) {
                final String textStr = text.toString();
                Pattern p = Pattern.compile("([1-9]\\d*\\.?\\d*)|(0\\.\\d*[1-9])");
                Matcher m = p.matcher(textStr);
                while (m.find()) {
                    final String group = m.group();
                    BigDecimal bigDecimal = new BigDecimal(group);
                    if (bigDecimal.subtract(new BigDecimal(0)).doubleValue() > 0) {
                        play(bigDecimal, textStr.replace(group, TAG));
                    } else {
                        super.setText(text, type);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            super.setText(text, type);
        }
    }

    private void play(BigDecimal target, final String formatStr) {
        if (animator != null && (animator.isRunning() || animator.isStarted())) {
            animator.cancel();
        }
        final int targetScale = target.scale();

        BigDecimal slowCountNumber;
        /**
         * 10
         * 10.0
         * 10.00
         */
        if (targetScale == 0) {
            /**
             * 整数
             */
            int v = (int) (target.intValue() * 0.1f);
            while (v >= 100) {
                v = v / 10;
            }
            /**
             * 超过Int最大值边界是负数
             */
            if (v < 0 || v > 15) {
                v = 15;
            }
            slowCountNumber = new BigDecimal(v);
        } else {
            /**
             * 小数
             */
            double doubleValue = target.doubleValue() - target.intValue();
            if (doubleValue == 0) {
                slowCountNumber = new BigDecimal(1 * 0.1f);
            } else {
                slowCountNumber = new BigDecimal((target.doubleValue() - target.intValue()) * 0.1f);
            }
        }
        BigDecimal animTarget = target.subtract(slowCountNumber);
        if (animTarget.subtract(new BigDecimal(0)).doubleValue() <= 0) {
            throw new RuntimeException("数字太小不能滚动");
        }
        ValueAnimator firstAnimator = ValueAnimator.ofObject(new BigDecimalEvaluator(), new BigDecimal(0), animTarget);
        ValueAnimator.AnimatorUpdateListener bigDecimalUpdateListener = new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                BigDecimal currentNum = (BigDecimal) animation.getAnimatedValue();
                NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.CHINA);
                numberFormat.setMaximumFractionDigits(targetScale);
                numberFormat.setMinimumFractionDigits(targetScale);
                numberFormat.setGroupingUsed(false);
                NumberCountAnimTextView.super.setText(formatStr.replace(TAG, numberFormat.format(currentNum)), TextView.BufferType.NORMAL);
            }
        };
        firstAnimator.setDuration(500L);
        firstAnimator.addUpdateListener(bigDecimalUpdateListener);
        firstAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        ValueAnimator linearValueAnimator = ValueAnimator.ofObject(new BigDecimalEvaluator(), animTarget, target);
        linearValueAnimator.setDuration(800L);
        linearValueAnimator.setInterpolator(new DecelerateInterpolator());
        linearValueAnimator.addUpdateListener(bigDecimalUpdateListener);

        animator = new AnimatorSet();
        animator.playSequentially(firstAnimator, linearValueAnimator);
        animator.start();
    }

    class BigDecimalEvaluator implements TypeEvaluator {
        @Override
        public Object evaluate(float fraction, Object startValue, Object endValue) {
            BigDecimal start = (BigDecimal) startValue;
            BigDecimal end = (BigDecimal) endValue;
            BigDecimal result = end.subtract(start);
            return result.multiply(new BigDecimal("" + fraction)).add(start);
        }
    }
}

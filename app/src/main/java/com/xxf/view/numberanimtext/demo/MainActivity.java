package com.xxf.view.numberanimtext.demo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateDecelerateInterpolator;


import com.xxf.view.numberanimtext.CharOrder;
import com.xxf.view.numberanimtext.NumberAnimTextView;
import com.xxf.view.numberanimtext.strategy.AlignAnimationStrategy;
import com.xxf.view.numberanimtext.strategy.Direction;
import com.xxf.view.numberanimtext.strategy.NormalAnimationStrategy;
import com.xxf.view.numberanimtext.strategy.Strategy;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import kotlin.Pair;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    private List<String> list = Arrays.asList("1", "21339", "12", "123319", "24", "6", "247",
            "5226", "63", "378", "234389", "12395", "2", "1289", "32212", "400");
    private int idx = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final NumberAnimTextView normal = findViewById(R.id.rollingTextView);
        normal.addCharOrder(CharOrder.Number);
        normal.setAnimationDuration(2000L);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                normal.setText(list.get(idx % list.size()));
                handler.postDelayed(this, 3000L);
            }
        }, 2000L);

        final NumberAnimTextView sameDirection = findViewById(R.id.rollingTextView2);
        sameDirection.addCharOrder(CharOrder.Number);
        sameDirection.setAnimationDuration(2000L);
        sameDirection.setCharStrategy(Strategy.SameDirectionAnimation(Direction.SCROLL_DOWN));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                sameDirection.setText(list.get(idx % list.size()));
                handler.postDelayed(this, 3000L);
            }
        }, 2000L);

        final NumberAnimTextView carryBit = findViewById(R.id.rollingTextView3);
        carryBit.addCharOrder(CharOrder.Number);
        carryBit.setAnimationDuration(2000L);
        carryBit.setCharStrategy(Strategy.CarryBitAnimation(Direction.SCROLL_UP));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                carryBit.setText(list.get(idx % list.size()));
                handler.postDelayed(this, 3000L);
            }
        }, 2000L);

        final NumberAnimTextView alignLeft = findViewById(R.id.rollingTextView4);
        alignLeft.addCharOrder(CharOrder.Number);
        alignLeft.setAnimationDuration(2000L);
        alignLeft.setCharStrategy(new AlignAnimationStrategy(AlignAnimationStrategy.TextAlignment.Left));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                alignLeft.setText(list.get(idx++ % list.size()));
                handler.postDelayed(this, 3000L);
            }
        }, 2000L);

        final NumberAnimTextView stickyText = findViewById(R.id.stickyText);
        stickyText.setAnimationDuration(3000L);
        stickyText.addCharOrder("0123456789abcdef");
        stickyText.setCharStrategy(Strategy.StickyAnimation(0.9f));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stickyText.setText("eeee");
            }
        }, 2000L);

        final NumberAnimTextView stickyText2 = findViewById(R.id.stickyText2);
        stickyText2.setAnimationDuration(3000L);
        stickyText2.addCharOrder("0123456789abcdef");
        stickyText2.setCharStrategy(Strategy.StickyAnimation(0.2f));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                stickyText2.setText("eeee\naaaaa");
            }
        }, 2000L);


        final NumberAnimTextView alphaBetView = findViewById(R.id.alphaBetView);
        alphaBetView.setAnimationDuration(2000L);
        alphaBetView.setCharStrategy(Strategy.NormalAnimation());
        alphaBetView.addCharOrder(CharOrder.Alphabet);
        alphaBetView.addCharOrder(CharOrder.UpperAlphabet);
        alphaBetView.addCharOrder(CharOrder.Number);
        alphaBetView.addCharOrder(CharOrder.Hex);
        alphaBetView.addCharOrder(CharOrder.Binary);
        alphaBetView.setAnimationInterpolator(new AccelerateDecelerateInterpolator());
        alphaBetView.addAnimatorListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //finsih
            }
        });
        alphaBetView.setText("i am a text");


        final NumberAnimTextView timeView = findViewById(R.id.timeView);
        timeView.setAnimationDuration(300);
        timeView.setLetterSpacingExtra(10);
        @SuppressLint("SimpleDateFormat") final DateFormat format = new SimpleDateFormat("HH:mm:ss");
        handler.post(new Runnable() {
            @Override
            public void run() {
                timeView.setText(format.format(new Date()));
                handler.postDelayed(this, 1000L);
            }
        });

        final NumberAnimTextView carryView = findViewById(R.id.carryTextView);
        carryView.setAnimationDuration(13000L);
        carryView.addCharOrder(CharOrder.Number);
        carryView.setCharStrategy(Strategy.CarryBitAnimation(Direction.SCROLL_DOWN));
        carryView.setText("0");
        carryView.setText("1290");

        final NumberAnimTextView charOrder1 = findViewById(R.id.charOrderExample1);
        charOrder1.setAnimationDuration(4000L);
        charOrder1.addCharOrder("abcdefg");
        charOrder1.setText("a");

        final NumberAnimTextView charOrder2 = findViewById(R.id.charOrderExample2);
        charOrder2.setAnimationDuration(4000L);
        charOrder2.addCharOrder("adg");
        charOrder2.setText("a");

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                charOrder1.setText("g"); //move from a to g

                charOrder2.setText("g"); //just like charOrder1 but with different charOder
            }
        }, 2000L);

        final NumberAnimTextView diffDirection = findViewById(R.id.directionExample);
        diffDirection.setAnimationDuration(500L);
        diffDirection.addCharOrder(CharOrder.UpperAlphabet);
        diffDirection.setLetterSpacingExtra(10);
        diffDirection.setCharStrategy(new NormalAnimationStrategy() {
            @NotNull
            @Override
            public Pair<List<Character>, Direction> findCharOrder(
                    char sourceChar,
                    char targetChar,
                    int index,
                    @Nullable Iterable<Character> order) {
                Pair<List<Character>, Direction> origin =
                        super.findCharOrder(sourceChar, targetChar, index, order);
                return new Pair<>(origin.getFirst(), diffDirection(index));
            }

            private Direction diffDirection(int index) {
                if (index == 0) {
                    return Direction.SCROLL_LEFT;
                } else if (index == 1) {
                    return Direction.SCROLL_UP;
                } else if (index == 2) {
                    return Direction.SCROLL_DOWN;
                } else {
                    return Direction.SCROLL_RIGHT;
                }
            }
        });
        new Runnable() {
            int idx = 0;

            @Override
            public void run() {
                diffDirection.setText(String.valueOf(1111 * (idx++ % 9 + 1)));
                handler.postDelayed(this, 1000L);
            }
        }.run();
    }
}

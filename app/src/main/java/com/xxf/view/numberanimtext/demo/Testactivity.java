package com.xxf.view.numberanimtext.demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.xxf.view.numberanimtext.NumberCountAnimTextView;

/**
 * @Author: XGod
 * @CreateDate: 2020/11/10 18:06
 */
public class Testactivity extends Activity {
    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        final NumberCountAnimTextView textView = findViewById(R.id.text);
        final EditText inputText = findViewById(R.id.input);
        findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(inputText.getText().toString());
            }
        });

    }
}

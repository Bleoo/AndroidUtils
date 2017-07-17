package io.github.bleoo.androidutils;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import io.github.bleoo.components.widget.FlowLayout;

/**
 * Created by bleoo on 2017/7/17.
 */

public class FlowLayoutActivity extends AppCompatActivity {

    private FlowLayout flow_layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_layout);

        findViewById(R.id.btn_reset).setOnClickListener(view -> resetContent());
        flow_layout = (FlowLayout) findViewById(R.id.flow_layout);
        resetContent();
    }

    private void resetContent() {
        flow_layout.removeAllViews();
        for (int i = 0; i < 18; i++) {
            View view = new View(this);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(getRandomInt(180) + 180, 80);
            view.setLayoutParams(lp);
            view.setBackgroundColor(Color.rgb(getRandomInt(255), getRandomInt(255), getRandomInt(255)));
            flow_layout.addView(view);
        }
    }

    private int getRandomInt(int range) {
        return (int) (Math.random() * range);
    }
}

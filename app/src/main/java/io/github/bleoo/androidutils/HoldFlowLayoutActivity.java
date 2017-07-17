package io.github.bleoo.androidutils;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.github.bleoo.components.widget.FlowLayout;
import io.github.bleoo.components.widget.HoldFlowLayout;

/**
 * Created by bleoo on 2017/7/17.
 */

public class HoldFlowLayoutActivity extends AppCompatActivity {

    private HoldFlowLayout flow_layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hold_flow_layout);

        findViewById(R.id.btn_reset).setOnClickListener(view -> resetContent());
        findViewById(R.id.btn_clear).setOnClickListener(view -> flow_layout.removeAllViews());
        flow_layout = (HoldFlowLayout) findViewById(R.id.hold_flow_layout);
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

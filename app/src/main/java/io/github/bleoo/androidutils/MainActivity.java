package io.github.bleoo.androidutils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_gravity_animation).setOnClickListener(this);
        findViewById(R.id.btn_flow_layout).setOnClickListener(this);
        findViewById(R.id.btn_hold_flow_layout).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_gravity_animation:
                startActivity(new Intent(this, GravityAnimationActivity.class));
                break;
            case R.id.btn_flow_layout:
                startActivity(new Intent(this, FlowLayoutActivity.class));
                break;
            case R.id.btn_hold_flow_layout:
                startActivity(new Intent(this, HoldFlowLayoutActivity.class));
                break;
        }
    }
}

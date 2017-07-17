package io.github.bleoo.androidutils;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import io.github.bleoo.components.anim.GravityAnimation;

/**
 * Created by bleoo on 2017/7/17.
 */

public class GravityAnimationActivity extends AppCompatActivity {

    private RelativeLayout rl_parent;
    private TextView tv_child;
    private RadioGroup rg_anim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gravity_animation);

        rl_parent = (RelativeLayout) findViewById(R.id.rl_parent);
        tv_child = (TextView) findViewById(R.id.tv_child);
        rg_anim = (RadioGroup) findViewById(R.id.rg_anim);

        GravityAnimation gravityAnimation = new GravityAnimation(rl_parent);
        gravityAnimation.bind(tv_child);
        gravityAnimation.setAnimationListener(new GravityAnimation.AnimationListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationEnd() {

            }
        });

        rg_anim.setOnCheckedChangeListener((radioGroup, i) -> {
            gravityAnimation.prep();
            switch (i){
                case R.id.rb_center:
                    rl_parent.setGravity(Gravity.CENTER);
                    break;
                case R.id.rb_left_top:
                    rl_parent.setGravity(Gravity.START | Gravity.TOP);
                    break;
                case R.id.rb_center_top:
                    rl_parent.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
                    break;
                case R.id.rb_right_top:
                    rl_parent.setGravity(Gravity.END | Gravity.TOP);
                    break;
                case R.id.rb_right_center:
                    rl_parent.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
                    break;
                case R.id.rb_right_bottom:
                    rl_parent.setGravity(Gravity.END | Gravity.BOTTOM);
                    break;
                case R.id.rb_center_bottom:
                    rl_parent.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
                    break;
                case R.id.rb_left_bottom:
                    rl_parent.setGravity(Gravity.START | Gravity.BOTTOM);
                    break;
                case R.id.rb_left_center:
                    rl_parent.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
                    break;
            }
        });


    }
}

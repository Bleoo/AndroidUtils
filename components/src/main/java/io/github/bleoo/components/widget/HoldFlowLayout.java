package io.github.bleoo.components.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

import io.github.bleoo.components.R;

/**
 * Created by bleoo on 2017/7/12.
 * <p>
 * 无内容时显示Hold
 */

public class HoldFlowLayout extends FlowLayout {

    private View mHoldView;

    public HoldFlowLayout(Context context) {
        super(context);
    }

    public HoldFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
    }

    public HoldFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public HoldFlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttr(context, attrs);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HoldFlowLayout);
        if (typedArray != null) {
            int layoutId = typedArray.getResourceId(R.styleable.HoldFlowLayout_holdView, 0);
            if (layoutId != 0) setHoldView(layoutId);
            typedArray.recycle();
        }
    }

    @Override
    public void removeAllViews() {
        super.removeAllViews();
        if (mHoldView != null) {
            super.addView(mHoldView);
        }
    }

    @Override
    public void addView(View child) {
        if (getChildCount() == 1 && getChildAt(0).equals(mHoldView)) {
            removeView(getChildAt(0));
        }
        super.addView(child);
    }

    public void setHoldView(View view) {
        if (view == null) {
            return;
        }
        mHoldView = view;
        if (getChildCount() == 0) {
            super.addView(mHoldView);
        }
    }

    public void setHoldView(@LayoutRes int layoutId) {
        View view = inflate(getContext(), layoutId, null);
        setHoldView(view);
    }
}

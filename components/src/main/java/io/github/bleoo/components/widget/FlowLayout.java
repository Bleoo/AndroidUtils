package io.github.bleoo.components.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.github.bleoo.components.R;

/**
 * Created by bleoo on 2017/7/10.
 * <p>
 * 流式布局
 */

public class FlowLayout extends ViewGroup {

    private List<TagLine> tagLineList = new ArrayList<>();
    private int mSpacing = 0;

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttr(context, attrs);
    }

    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        if (typedArray != null) {
            mSpacing = (int) typedArray.getDimension(R.styleable.FlowLayout_spacing, 0);
            typedArray.recycle();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left = getPaddingLeft();// 获取最初的左上点
        int top = getPaddingTop();

        for (int i = 0; i < tagLineList.size(); i++) {
            TagLine line = tagLineList.get(i);
            line.layoutView(left, top);// 设置每一行所在的位置
            top += mSpacing + line.height;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int totalWidth = MeasureSpec.getSize(widthMeasureSpec);
        int availableWidth = totalWidth - getPaddingRight() - getPaddingLeft();

        // onMeasure可能会多次执行，需要初始化置空
        tagLineList.clear();
        TagLine currentLine = null;

        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);

            // 测量子View
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            if (currentLine == null) {
                currentLine = new TagLine();
            }
            int childWidth = child.getMeasuredWidth();
            int futureWidth = currentLine.futureWidth(childWidth);
            if (futureWidth <= availableWidth) {
                currentLine.addView(child);
            } else {
                // 如果当前行有view，先保存该行，再开启新行
                if (currentLine.getViewCount() > 0) {
                    tagLineList.add(currentLine);
                    currentLine = new TagLine();
                }
                currentLine.addView(child);
            }
        }

        // 无论如何保存最后一行
        if (currentLine != null && currentLine.getViewCount() > 0 && !tagLineList.contains(currentLine)) {
            tagLineList.add(currentLine);
        }

        // 加上所有行的高度
        int totalHeight = 0;
        for (TagLine line : tagLineList) {
            totalHeight += line.height;
        }
        // 加上间距
        if (tagLineList.size() > 1) {
            totalHeight += (tagLineList.size() - 1) * mSpacing;
        }
        totalHeight += getPaddingTop() + getPaddingBottom();// 加上padding
        // 给出最终计算好的高宽
        setMeasuredDimension(totalWidth, resolveSize(totalHeight, heightMeasureSpec));
    }

    private class TagLine {
        int width = 0;// 该行中所有的子控件加起来的宽度
        int height = 0;// 子控件的高度
        List<View> viewList = new ArrayList<>();

        public void addView(View view) {// 添加子控件
            if (getViewCount() != 0) {
                width += mSpacing;
            }
            width += view.getMeasuredWidth();
            height = Math.max(height, view.getMeasuredHeight());// 行的高度当然是有子控件的高度决定了
            viewList.add(view);
        }

        public int futureWidth(int width) {
            if (getViewCount() == 0) {
                return width;
            }
            return this.width + mSpacing + width;
        }

        public int getViewCount() {
            return viewList.size();
        }

        public void layoutView(int left, int top) {
            for (int i = 0; i < viewList.size(); i++) {
                View view = viewList.get(i);
                int childWidth = view.getMeasuredWidth();
                int childHeight = view.getMeasuredHeight();
                // 判断不是第一个，加间距
                if (i != 0) {
                    left += mSpacing;
                }
                view.layout(left, top, left + childWidth, top + childHeight);
                left += childWidth; // 获取到的left值是下一个子控件的左边所在的位置
            }
        }
    }
}

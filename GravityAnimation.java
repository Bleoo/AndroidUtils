package cc.sachsen.util.anim;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import java.util.ArrayList;

/**
 * Created by bleoo on 2017/6/19.
 *
 * 根据Gravity属性做出动画
 *
 * 目前只支持平移动画
 */

public class GravityAnimation {

    private ViewGroup mParentView;
    private ArrayList<ViewHolder> mChildViewList = new ArrayList<>();
    private Animation animation;
    private AnimationListener listener;
    private int duration = 300;

    public GravityAnimation(ViewGroup parentView) {
        mParentView = parentView;
    }

    /**
     * 自由选择需要动画的View
     * @param targetView
     */
    public void bind(View... targetView) {
        for (View view : targetView) {
            ViewGroup viewGroup = (ViewGroup)view.getParent();
            if(viewGroup.equals(mParentView)){
                mChildViewList.add(new ViewHolder(view));
            } else {
                throw new RuntimeException("The View does not belong to the parentView -> " + view.toString());
            }
        }
    }

    /**
     * 改变父View的Gravity属性前必须先调用该方法
     * This method must be called before changing the parent View's Gravity property
     */
    public void prep() {
        if (mChildViewList.size() == 0) {
            throw new RuntimeException("GravityAnimation -> 'ChildViews' is empty. Maybe 'bind' method not called");
        }
        for (ViewHolder holder : mChildViewList) {
            holder.view.getLocationOnScreen(holder.oldLocation);
        }
        mParentView.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);
    }

    public void cancel() {
        stopAnimation();
        mParentView.getViewTreeObserver().removeOnGlobalLayoutListener(mOnGlobalLayoutListener);
    }

    private Animation.AnimationListener animationListener = new Animation.AnimationListener() {
        @Override
        public void onAnimationStart(Animation animation) {
            mParentView.getViewTreeObserver().removeOnGlobalLayoutListener(mOnGlobalLayoutListener);
            if (listener != null) listener.onAnimationStart();
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            mParentView.getViewTreeObserver().removeOnGlobalLayoutListener(mOnGlobalLayoutListener);
            if (listener != null) listener.onAnimationEnd();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    };

    /**
     * 目前只支持平移动画
     */
    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener = () -> {
        stopAnimation();
        for (ViewHolder holder : mChildViewList) {
            holder.view.getLocationOnScreen(holder.newLocation);
            Log.d(this.getClass().getSimpleName(), "leftOld=" + holder.oldLocation[0] + "，" + "leftNew=" + holder.newLocation[0]);
            Log.d(this.getClass().getSimpleName(), "topOld=" + holder.oldLocation[1] + "，" + "topNew=" + holder.newLocation[1]);
            animation = new TranslateAnimation(holder.oldLocation[0] - holder.newLocation[0], 0.0f, holder.oldLocation[1] - holder.newLocation[1], 0.0f);
            animation.setDuration(duration);
            animation.setAnimationListener(animationListener);
        }
        startAnimation();
    };

    public void stopAnimation() {
        if (animation != null) {
            animation.cancel();
        }
        for (ViewHolder holder : mChildViewList) {
            holder.view.clearAnimation();
        }
    }

    public void startAnimation() {
        for (ViewHolder holder : mChildViewList) {
            holder.view.startAnimation(animation);
        }
    }

    public void setDuration(int duration) {
        if (duration <= 0) {
            Log.w(this.getClass().getSimpleName(), "duration less than 0 is invalid");
            return;
        }
        this.duration = duration;
    }

    public void setAnimationListener(AnimationListener listener) {
        this.listener = listener;
    }

    public interface AnimationListener {
        void onAnimationStart();

        void onAnimationEnd();
    }

    private class ViewHolder {
        View view;
        int[] oldLocation = new int[2];
        int[] newLocation = new int[2];

        ViewHolder(View view) {
            this.view = view;
        }
    }
}

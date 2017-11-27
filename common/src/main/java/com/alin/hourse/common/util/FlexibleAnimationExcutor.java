package com.alin.hourse.common.util;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * @创建者 hailin
 * @创建时间 2017/8/31 11:50
 * @描述 ${布局的伸缩动画}.
 */

public class FlexibleAnimationExcutor {

    private  OnAnimationStateChangerListener mOnAnimationStateChangerListener;
    private  View[] mViews;
    private int[] mHeights;
    private int mVisibility;
    private int[] mStartHeight;
    private int[] mEndHeight;
    private ObjectAnimator mAnimator;
    private boolean mExpand;

    public FlexibleAnimationExcutor(View[] views, OnAnimationStateChangerListener onAnimationStateChangerListener){
        mViews = views;
        mOnAnimationStateChangerListener = onAnimationStateChangerListener;
    }

    public void excute(boolean expand,boolean smooth) {
        if (mViews != null && mViews.length > 0) {
            mExpand = expand;
            mVisibility = expand ? View.VISIBLE : View.GONE;
            mHeights = new int[mViews.length];
            //        展开
            if (expand){
                for (int i = 0; i < mViews.length; i++) {
                    String tag = (String) mViews[i].getTag();
                    if (!TextUtils.isEmpty(tag)) {
                        mHeights[i] = Integer.parseInt(tag);
                    }
                }
            }else  if(mViews[0].getTag() == null){       //首次展开的时候，view的tag还未绑定
                for (int i = 0; i < mViews.length; i++) {
                    mViews[i].setTag(mViews[i].getHeight());
                }
            }
        }

        if (smooth) {       //有展开、收缩动画效果
            mStartHeight = new int[mViews.length];
            for (int i = 0; i < mViews.length; i++) {
                mStartHeight[i] = mViews[i].getHeight();
            }

            mEndHeight = mHeights;
            excuteAnimation();

        }else {          //不使用动画效果直接展开、收起，一般用于代码自动执行，如第一次展开的时候
            if (mHeights[0] > 0) {
                for (int i = 0; i < mViews.length; i++) {
                    mViews[i].getLayoutParams().height = mHeights[i];
                }
            }

            for (View view : mViews) {
                view.requestLayout();
                view.setVisibility(mVisibility);
            }


            if (mOnAnimationStateChangerListener != null) {
                mOnAnimationStateChangerListener.onEnd();
            }
        }

    }

    private void excuteAnimation() {
        final int visibility = mExpand ? View.VISIBLE : View.GONE;
        if (mExpand) {
            for (View view : mViews) {
                view.setVisibility(visibility);
            }
        }

        if (mAnimator == null) {
            mAnimator = ObjectAnimator.ofFloat(this, "progress", 0, 1);
            mAnimator.setDuration(160);
            mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float  animatedValue = (float) valueAnimator.getAnimatedValue();
                    if (animatedValue == 1) {
                        if (!mExpand) {
                            for (View view : mViews) {
                                view.setVisibility(visibility);
                            }
                        }

                        if (mOnAnimationStateChangerListener != null) {
                            mOnAnimationStateChangerListener.onEnd();
                            mAnimator = null;
                        }

                    }
                }
            });
        }

        if (mOnAnimationStateChangerListener != null) {
            mOnAnimationStateChangerListener.onStart();
        }

        if (mAnimator != null && (mAnimator.isStarted() || mAnimator.isRunning())) {
            mAnimator.cancel();
        }

        mAnimator.start();

    }


    public void setProgress(float progress){
        for (int i = 0; i < mViews.length; i++) {
            mViews[i].getLayoutParams().height = (int) (mStartHeight[i] + (mEndHeight[i] - mStartHeight[i]) * progress);
            mViews[i].requestLayout();
        }
    }

    public interface OnAnimationStateChangerListener{
        void onStart();
        void onEnd();
    }
}

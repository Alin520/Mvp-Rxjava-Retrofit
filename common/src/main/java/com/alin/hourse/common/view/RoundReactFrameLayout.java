package com.alin.hourse.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.alin.hourse.common.R;

import java.util.ArrayList;
import java.util.List;


/**
 * @创建者 hailin
 * @创建时间 2017/8/25 22:24
 * @描述 ${}.
 */

public class RoundReactFrameLayout extends CommonFrameLayout{
    private static final int NO_ID = -1;
    private static final String LOADING_TAG = "ProgressLayout.loading_tag";
    private static final String NONE_TAG = "ProgressLayout.none_tag";
    private static final String ERROR_TAG = "ProgressLayout.error_tag";
    private int mEmptyViewId;
    private int mErrorViewId;
    private int mLoadingViewId;
    private View mEmptyView;
    private View mErrorView;
    private View mLoadingView;
    private float mCornerRadius;
    private RectF mRectF;
    private Path mPath;
    private Paint mPaint;
    private int mSaveLayerCount;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<View> mContentViews = new ArrayList<>();
    private boolean mIsShowContent = false;         //初始化时，是否显示contentView

    public RoundReactFrameLayout(@NonNull Context context) {
        super(context,null);
        initialize(context,null);
    }

    public RoundReactFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
        initialize(context,attrs);
    }

    public RoundReactFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context,attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundReactFrameLayout);
        if (ta != null) {
            try {
                mIsShowContent = ta.getBoolean(R.styleable.RoundReactFrameLayout_isShowContentView,false);
                mEmptyViewId = ta.getResourceId(R.styleable.RoundReactFrameLayout_emptyView,NO_ID);
                mErrorViewId = ta.getResourceId(R.styleable.RoundReactFrameLayout_errorView,NO_ID);
                mLoadingViewId = ta.getResourceId(R.styleable.RoundReactFrameLayout_loadingView,NO_ID);
            }catch (Throwable t){
                t.printStackTrace();
            }finally {
                ta.recycle();
            }
        }
    }



    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (child.getTag() == null || (child.getTag() != LOADING_TAG
                && child.getTag() != NONE_TAG && child.getTag() != ERROR_TAG)) {
            mContentViews.add(child);
            showContentViewVisibility();
        }
    }

    private void showContentViewVisibility(){
        if (!this.isInEditMode()) {
            if (!mIsShowContent) {
                setContentVisibillty(false);
            }else {
                setContentVisibillty(true);
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (mLoadingViewId != NO_ID) {
            setLoadingView();
        }

        if (mErrorViewId != NO_ID){
            setErrorView();
        }

        if (mEmptyViewId != NO_ID){
            setEmptyView();
        }
    }

    public boolean isShowContent() {
        return mIsShowContent;
    }

    public void setShowContent(boolean showContent) {
        mIsShowContent = showContent;
        showContentViewVisibility();
    }

    public void showLoadingyView(){
        if (mEmptyView != null && mLoadingView != null && mErrorView != null && mContentViews != null) {
            this.mErrorView.setVisibility(GONE);
            this.setContentVisibillty(false);
            this.mEmptyView.setVisibility(GONE);
            this.mLoadingView.setVisibility(VISIBLE);
        }
    }

    public void showEmtpyView(){
        if (mEmptyView != null && mLoadingView != null && mErrorView != null && mContentViews != null) {
            this.mErrorView.setVisibility(GONE);
            this.mLoadingView.setVisibility(GONE);
            this.setContentVisibillty(false);
            this.mEmptyView.setVisibility(VISIBLE);
        }
    }

    public void showErrorView(){
        if (mEmptyView != null && mLoadingView != null && mErrorView != null && mContentViews != null) {
            this.mLoadingView.setVisibility(GONE);
            this.setContentVisibillty(false);
            this.mEmptyView.setVisibility(GONE);
            this.mErrorView.setVisibility(VISIBLE);
        }
    }

    public void showContentView(){
        if (mEmptyView != null && mLoadingView != null && mErrorView != null && mContentViews != null) {
            this.mLoadingView.setVisibility(GONE);
            this.mEmptyView.setVisibility(GONE);
            this.mErrorView.setVisibility(GONE);
            this.setContentVisibillty(true);
        }
    }

    private void setContentVisibillty(boolean visible) {
        if (mContentViews != null && !mContentViews.isEmpty()) {
            for (View contentView : mContentViews) {
                contentView.setVisibility(visible ? VISIBLE : GONE);
            }
        }
    }

    private void setEmptyView() {
        if (mEmptyView == null) {
            if (mEmptyViewId == NO_ID) {
                throw new IllegalStateException(
                        "cannot call showLoadingView() when loadingId was NO_SET which value is -1");
            }

            mEmptyView = mLayoutInflater.inflate(mEmptyViewId, RoundReactFrameLayout.this, false);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mEmptyView.getLayoutParams();
            layoutParams.gravity = Gravity.CENTER;
            this.mEmptyView.setTag(NONE_TAG);
            RoundReactFrameLayout.this.addView(mEmptyView,layoutParams);
            this.mEmptyView.setVisibility(GONE);
        }
    }


    private void setErrorView() {
        if (mErrorView == null) {
            if (mErrorViewId == NO_ID) {
                throw new IllegalStateException(
                        "cannot call showNetErrorView() when networkErrorId was NO_SET which value is -1");
            }
            mErrorView = mLayoutInflater.inflate(mErrorViewId,RoundReactFrameLayout.this,false);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mErrorView.getLayoutParams();
            layoutParams.gravity = Gravity.CENTER;
            this.mErrorView.setTag(ERROR_TAG);
            RoundReactFrameLayout.this.addView(mErrorView,layoutParams);
            this.mErrorView.setVisibility(GONE);
        }

    }

    private void setLoadingView() {
        if (mLoadingView == null) {
            if (mLoadingViewId == NO_ID) {
                throw new IllegalStateException(
                        "cannot call showLoadingView() when loadingId was NO_SET which value is -1");
            }

            mLoadingView = mLayoutInflater.inflate(mLoadingViewId,RoundReactFrameLayout.this,false);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mLoadingView.getLayoutParams();
            layoutParams.gravity = Gravity.CENTER;
            mLoadingView.setTag(LOADING_TAG);
            RoundReactFrameLayout.this.addView(mLoadingView,layoutParams);
            mLoadingView.setVisibility(GONE);
        }
    }

}

package com.alin.hourse.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alin.hourse.common.R;

import java.util.ArrayList;


/**
 * @创建者 hailin
 * @创建时间 2017/8/25 16:13
 * @描述 ${}.
 */

public class RoundRectLinearLayout extends CommonLinearlayout {
    public static final String TAG = RoundRectLinearLayout.class.getSimpleName();
    private static final int NO_ID = -1;
    private static final String LOADING_TAG = "ProgressLayout.loading_tag";
    private static final String NONE_TAG = "ProgressLayout.none_tag";
    private static final String ERROR_TAG = "ProgressLayout.error_tag";
    private ArrayList<View> mContentViews = new ArrayList<>();
    private int mEmptyViewId;
    private int mErrorViewId;
    private int mLoadingViewId;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private View mLoadingView;
    private View mErrorView;
    private View mEmptyView;
    private boolean mIsShowContent = false;         //初始化时，是否显示contentView


    public RoundRectLinearLayout(Context context) {
        super(context);
        initialize(context,null);
    }

    public RoundRectLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initialize(context,attrs);
    }

    public RoundRectLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context,attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RoundReactLinearLayout);
        if (ta != null) {
            try {
                mIsShowContent = ta.getBoolean(R.styleable.RoundReactLinearLayout_isShowContentView,false);
                mEmptyViewId = ta.getResourceId(R.styleable.RoundReactLinearLayout_emptyView, NO_ID);
                mErrorViewId = ta.getResourceId(R.styleable.RoundReactLinearLayout_errorView, NO_ID);
                mLoadingViewId = ta.getResourceId(R.styleable.RoundReactLinearLayout_loadingView, NO_ID);
            }catch (Throwable t){
                t.printStackTrace();
            }finally {
                ta.recycle();
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (mLoadingViewId != NO_ID) {
            setLoadingView();
        }

        if (mEmptyViewId != NO_ID){
            setEmptyView();
        }

        if (mErrorViewId != NO_ID){
            setErrorView();
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
                setContentViewVisibility(false);
            }else {
                setContentViewVisibility(true);
            }
        }
    }

    public boolean isShowContent() {
        return mIsShowContent;
    }

    public void setShowContent(boolean showContent) {
        mIsShowContent = showContent;
        showContentViewVisibility();
    }


    public void showLoadingView(){
        this.mEmptyView.setVisibility(GONE);
        this.mErrorView.setVisibility(GONE);
        this.setContentViewVisibility(false);
        this.mLoadingView.setVisibility(VISIBLE);
    }

    public void showEmptyView(){
        this.mLoadingView.setVisibility(GONE);
        this.mErrorView.setVisibility(GONE);
        this.setContentViewVisibility(false);
        this.mEmptyView.setVisibility(VISIBLE);
    }

    public void showErrorView(){
        this.mLoadingView.setVisibility(GONE);
        this.mEmptyView.setVisibility(GONE);
        this.setContentViewVisibility(false);
        this.mErrorView.setVisibility(VISIBLE);
    }

    public void showContentView() {
        this.mLoadingView.setVisibility(GONE);
        this.mEmptyView.setVisibility(GONE);
        this.mErrorView.setVisibility(GONE);
        this.setContentViewVisibility(true);
    }

    private void setContentViewVisibility(boolean visible) {
        if (mContentViews != null && !mContentViews.isEmpty()) {
            for (View contentView : mContentViews) {
                contentView.setVisibility(visible ? VISIBLE : GONE);
            }
        }
    }

    private void setErrorView() {
        if (mErrorView == null) {
            if (mErrorViewId == NO_ID) {
                throw new IllegalStateException(
                        "cannot call showNetErrorView() when networkErrorId was NO_SET which value is -1");
            }
            mErrorView = mLayoutInflater.inflate(mErrorViewId, RoundRectLinearLayout.this, false);
            LayoutParams layoutParams = (LayoutParams) mErrorView.getLayoutParams();
            layoutParams.gravity = Gravity.CENTER;
            mErrorView.setTag(ERROR_TAG);
            RoundRectLinearLayout.this.addView(mErrorView,layoutParams);
            mErrorView.setVisibility(GONE);
        }
    }

    private void setEmptyView() {
        if (mEmptyView == null) {
            if (mEmptyViewId == NO_ID) {
                throw new IllegalStateException(
                        "cannot call showLoadingView() when loadingId was NO_SET which value is -1");
            }
            mEmptyView = mLayoutInflater.inflate(mEmptyViewId, RoundRectLinearLayout.this, false);
            LayoutParams layoutParams = (LayoutParams) mEmptyView.getLayoutParams();
            layoutParams.gravity = Gravity.CENTER;
            mEmptyView.setTag(ERROR_TAG);
            RoundRectLinearLayout.this.addView(mEmptyView,layoutParams);
            mEmptyView.setVisibility(GONE);
        }
    }

    private void setLoadingView() {
        if (mLoadingView == null) {
            if (mLoadingViewId == NO_ID) {
                throw new IllegalStateException(
                        "cannot call showLoadingView() when loadingId was NO_SET which value is -1");
            }
            mLoadingView = mLayoutInflater.inflate(mLoadingViewId, RoundRectLinearLayout.this, false);
//            LayoutParams layoutParams = (LayoutParams) mLoadingView.getLayoutParams();
//            layoutParams.gravity = Gravity.CENTER;
            mLoadingView.setTag(LOADING_TAG);
            RoundRectLinearLayout.this.addView(mLoadingView);
            mLoadingView.setVisibility(GONE);
        }
    }

}

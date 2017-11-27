package com.alin.hourse.common.ui.twinklingRefreshLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.alin.hourse.common.R;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;


/**
 * @创建者 hailin
 * @创建时间 2017/8/29 13:35
 * @描述 ${}.
 */

public class RefreshRecyclerView extends TwinklingRefreshLayout {

    private Orientation  mOrientation;
    private int          mType;
    private int          mCount;
    private RecyclerView mRecyclerView;
    private float        mHeaderHeight;
    private float        mBottomHeight;
    private float        mMaxHeadHeight;
    private float        mMaxBottomHeight;
//    伸缩
    public RefreshRecyclerView(Context context) {
        this(context,null);
    }

    public RefreshRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RefreshRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context,attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RefreshRecyclerView);
        if (ta != null) {
            try {
                mHeaderHeight = ta.getDimension(R.styleable.RefreshRecyclerView_headerHeight, 0);
                mBottomHeight = ta.getDimension(R.styleable.RefreshRecyclerView_bottomHeight, 36);
                mMaxHeadHeight = ta.getDimension(R.styleable.RefreshRecyclerView_maxHeadHeight, 0);
                mMaxBottomHeight = ta.getDimension(R.styleable.RefreshRecyclerView_maxBottomHeight, 0);
                mCount = ta.getInteger(R.styleable.RefreshRecyclerView_horizontal_count,0);
                mType = ta.getInteger(R.styleable.RefreshRecyclerView_orientation, 1);
                mOrientation = getOrientationType(mType);
            }catch (Throwable t){
                t.printStackTrace();
            }finally {
                ta.recycle();
            }
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initRecyclerView(getContext());
    }

    private void initRecyclerView(Context context) {
//        初始化RecyclerView
        mRecyclerView = new RecyclerView(context);
//        mRecyclerView.setLayoutAnimation();
        mRecyclerView.setOverScrollMode(OVER_SCROLL_NEVER);
        mRecyclerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
        mRecyclerView.setItemAnimator(null);
        if (mOrientation != null) {
            if (mOrientation == Orientation.HORIZONTAL) {       //水平方向
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));
            }else if (mOrientation == Orientation.VERTICAL){       //竖直方向,即listview
                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            }else if (mOrientation == Orientation.VERTICAL){        //grideView
                if (mCount > 0) {
                    mRecyclerView.setLayoutManager(new GridLayoutManager(context,mCount));
                }
            }
        }

//        对RecyclerView绑定下拉刷新
        if (mMaxHeadHeight > 0) {
            this.setMaxHeadHeight(mMaxHeadHeight);
        }

        if (mMaxBottomHeight > 0) {
            this.setMaxBottomHeight(mMaxBottomHeight);
        }
        if (mHeaderHeight > 0) {
            this.setHeaderHeight(mHeaderHeight);
        }
        if (mBottomHeight > 0) {
            this.setBottomHeight(mBottomHeight);
        }
        this.addView(mRecyclerView);
        this.setHeaderView(new RefreshHeaderView(context));
        this.setBottomView(new RefreshBottomView(context));
        this.setAutoLoadMore(true);
    }

    public float getHeaderHeight() {
        return mHeaderHeight;
    }

    @Override
    public void setHeaderHeight(float headerHeight) {
        mHeaderHeight = headerHeight;
    }

    public float getBottomHeight() {
        return mBottomHeight;
    }

    @Override
    public void setBottomHeight(float bottomHeight) {
        mBottomHeight = bottomHeight;
    }

    public float getMaxBottomHeight() {
        return mMaxBottomHeight;
    }

    @Override
    public void setMaxBottomHeight(float maxBottomHeight) {
        mMaxBottomHeight = maxBottomHeight;
    }

    public float getMaxHeadHeight(){
       return this.mMaxHeadHeight;
    }

    public void setMaxHeadHeight(float maxHeadHeight){
        this.mMaxHeadHeight = maxHeadHeight;
    }

    public int getCount(){
        return mCount;
    }

    public void setCount(int count){
        this.mCount = count;
    }

    public void setOrientation(Orientation orientation){
        this.mOrientation = orientation;
    }

    public Orientation setOrientation(){
        return this.mOrientation;

    }

    public void setOrientationType(int type){
        this.mType = type;
    }

    public Orientation getOrientationType(int type) {
        switch (type){
            case 1:
                return Orientation.HORIZONTAL;
            case 2:
                return Orientation.GRIDEVIEW;
            case 3:
                return Orientation.VERTICAL;
        }
        return null;
    }

    enum Orientation{
        HORIZONTAL,GRIDEVIEW,VERTICAL
    }
}

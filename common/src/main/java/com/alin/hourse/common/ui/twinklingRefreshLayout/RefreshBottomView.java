package com.alin.hourse.common.ui.twinklingRefreshLayout;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alin.hourse.common.R;
import com.lcodecore.tkrefreshlayout.IBottomView;


/**
 * @创建者 hailin
 * @创建时间 2017/8/29 14:38
 * @描述 ${}.
 */

public class RefreshBottomView extends FrameLayout implements IBottomView {


    private ImageView mLoading;

    public RefreshBottomView(@NonNull Context context) {
        this(context,null);
    }

    public RefreshBottomView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs,0);
    }

    public RefreshBottomView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    private void initialize(AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.refresh_bottom_view, this, true);
        mLoading = (ImageView) findViewById(R.id.loading_iv);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onPullingUp(float fraction, float maxBottomHeight, float bottomHeight) {
        mLoading.startAnimation(AnimationUtils.loadAnimation(getContext(),R.anim.rotate_always));
    }

    @Override
    public void startAnim(float maxBottomHeight, float bottomHeight) {

    }

    @Override
    public void onPullReleasing(float fraction, float maxBottomHeight, float bottomHeight) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void reset() {

    }
}

package com.alin.hourse.common.ui.twinklingRefreshLayout;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alin.hourse.common.R;
import com.alin.hourse.common.util.CommonUtil;
import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.OnAnimEndListener;


/**
 * @创建者 hailin
 * @创建时间 2017/8/29 14:38
 * @描述 ${}.
 */

public class RefreshHeaderView extends FrameLayout implements IHeaderView {
    private ImageView mImgBackground;
    private ImageView mImgBike;
    private float mFraction;
    private int mMaxBikeTranslationX;

    public RefreshHeaderView(@NonNull Context context) {
        this(context,null);
    }

    public RefreshHeaderView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RefreshHeaderView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        intialize(context);
    }

    private void intialize(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.refresh_header_view, this, true);
        mImgBackground = (ImageView) view.findViewById(R.id.imgBackground);
        mImgBike = (ImageView) view.findViewById(R.id.imgBike);
        mMaxBikeTranslationX = CommonUtil.dp2px(context, 48);
    }


    @Override
    public View getView() {
        return this;
    }

//    手动下拉
    @Override
    public void onPullingDown(float fraction, float maxHeadHeight, float headHeight) {
        updateRefreshAnim(fraction);
    }


//    释放
    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
        updateRefreshAnim(fraction);
    }

    private void updateRefreshAnim(float fraction) {
        mFraction = Math.min(fraction, 1);
        mImgBike.setTranslationX(-(mMaxBikeTranslationX - mMaxBikeTranslationX * mFraction));
        mImgBike.setImageAlpha((int) (255 * fraction));
    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        Drawable drawable = mImgBackground.getDrawable();
        if (drawable != null && drawable instanceof AnimationDrawable) {
            ((AnimationDrawable) drawable).start();
        }

        mImgBike.setImageDrawable(null);
    }

    @Override
    public void onFinish(OnAnimEndListener animEndListener) {
        Drawable drawable = mImgBackground.getDrawable();
        if (drawable != null && drawable instanceof  AnimationDrawable) {
            ((AnimationDrawable) drawable).stop();
        }

        mImgBackground.setImageResource(R.mipmap.refresh_end_bg);
        mImgBike.setImageResource(R.mipmap.icon_refresh_bike);
        mImgBike.setTranslationX(0);
        mImgBike.animate().translationX(mMaxBikeTranslationX).alpha(0).setDuration(100).start();
        animEndListener.onAnimEnd();
    }

    @Override
    public void reset() {
        Drawable drawable = mImgBackground.getDrawable();
        if (drawable != null && drawable instanceof  AnimationDrawable) {
            ((AnimationDrawable) drawable).stop();
        }

        mImgBackground.setImageResource(R.mipmap.refresh_end_bg);
        mImgBike.setImageResource(R.mipmap.icon_refresh_bike);
        mImgBike.setScaleX(1);
        mImgBike.setScaleY(1);
        mImgBike.setImageAlpha(255);
        mImgBike.setTranslationX(0);

    }
}

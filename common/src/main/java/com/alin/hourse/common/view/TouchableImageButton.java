package com.alin.hourse.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.ImageButton;

import com.alin.hourse.common.R;


/**
 * @创建者 hailin
 * @创建时间 2017/8/30 14:00
 * @描述 ${}.
 */

public class TouchableImageButton extends ImageButton {

    private int         mColor;
    private boolean     mIsCorverEnable;
    private boolean     mTouching;
    private ColorFilter mDefaultColorFilter;

    public TouchableImageButton(Context context) {
        this(context,null);
    }

    public TouchableImageButton(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TouchableImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    private void initialize(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.TouchableImageButton);
        try {
            mColor = ta.getColor(R.styleable.TouchableImageButton_cover_color, Color.LTGRAY);

        }catch (Throwable t){
            t.printStackTrace();
        }finally {
            ta.recycle();
        }
    }


    @Override
    public void setPressed(boolean pressed) {
        super.setPressed(pressed);

        if ((mIsCorverEnable && this.isClickable()) || pressed != mTouching) {
            mTouching = pressed;
            onPressedChange();
        }
    }

    private void onPressedChange() {
        if (mTouching) {
            this.setColorFilter(mColor, PorterDuff.Mode.MULTIPLY);
        }else {
            this.setColorFilter(mDefaultColorFilter);
        }
    }


    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public boolean isCorverEnable() {
        return mIsCorverEnable;
    }

    public void setCorverEnable(boolean corverEnable) {
        mIsCorverEnable = corverEnable;
    }

    public boolean isTouching() {
        return mTouching;
    }

    public void setTouching(boolean touching) {
        mTouching = touching;
    }

    public ColorFilter getDefaultColorFilter() {
        return mDefaultColorFilter;
    }

    public void setDefaultColorFilter(ColorFilter defaultColorFilter) {
        mDefaultColorFilter = defaultColorFilter;
    }

    @Override
    public void setClickable(boolean clickable) {
        super.setClickable(clickable);
        this.setColorFilter(mDefaultColorFilter);
    }
}

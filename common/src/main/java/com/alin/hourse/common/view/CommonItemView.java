package com.alin.hourse.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alin.hourse.common.R;
import com.alin.hourse.common.util.CommonUtil;


/**
 * @创建者 hailin
 * @创建时间 2017/8/25 22:26
 * @描述 ${}.
 */

public class CommonItemView extends RelativeLayout {
    private ImageView mIconLeft;
    private ImageView mIconRight;
    private TextView  mTextLeft;
    private TextView  mTextRight;

    public CommonItemView(Context context) {
        super(context);
    }

    public CommonItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommonItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context,attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        inflate(context, R.layout.commom_item_view_layout,this);
        mIconLeft = getView(R.id.icon_left);
        mIconRight = getView(R.id.icon_right);
        mTextLeft = getView(R.id.menu_text_left);
        mTextRight = getView(R.id.menu_text_right);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.CommonItemView);
        if (ta != null) {
            try {
                int leftColor = ta.getColor(R.styleable.CommonItemView_leftTextColor, Color.GRAY);
                int rightColor = ta.getColor(R.styleable.CommonItemView_rigthTextColor, Color.GREEN);
                float leftTextSize = ta.getDimension(R.styleable.CommonItemView_leftTextSize, CommonUtil.dp2px(context,12));
                float rightTextSize = ta.getDimension(R.styleable.CommonItemView_rightTextSize, CommonUtil.dp2px(context,12));
                int leftImgId = ta.getResourceId(R.styleable.CommonItemView_leftIcon, R.mipmap.icon_my_trip);
                int rightImgId = ta.getResourceId(R.styleable.CommonItemView_rightIcon, R.mipmap.icon_my_trip);
                String rightText = ta.getString(R.styleable.CommonItemView_rightText);
                String leftText = ta.getString(R.styleable.CommonItemView_leftText);
                mIconLeft.setImageResource(leftImgId);
                mIconRight.setImageResource(rightImgId);
                mTextLeft.setTextColor(leftColor);
                mTextRight.setTextColor(rightColor);
                mTextLeft.setTextSize(leftTextSize);
                mTextRight.setTextSize(rightTextSize);
                if (!TextUtils.isEmpty(rightText)) {
                    mTextRight.setText(rightText);
                }
                if (!TextUtils.isEmpty(leftText)) {
                    mTextLeft.setText(leftText);
                }

            }catch (Throwable t){
                t.printStackTrace();
            }finally {
                ta.recycle();
            }
        }
    }

    public void setRightText(String text){
        if (!TextUtils.isEmpty(text)) {
            mTextRight.setText(text);
        }
    }

    public CharSequence getRightText(){
        return mTextRight.getText();
    }

    public void setLeftText(String text){
        if (!TextUtils.isEmpty(text)) {
            mTextLeft.setText(text);
        }
    }

    public CharSequence getLeftText(){
        return mTextLeft.getText();
    }

    public void setLeftTextColor(int textColor){
       try {
           if (mTextLeft != null) {
               mTextLeft.setTextColor(textColor);
           }
       }catch (Throwable t){
           t.printStackTrace();
       }
    }

    public int getLeftTextColor(){
        return mTextLeft.getCurrentTextColor();
    }

    public void setRightTextColor(int textColor){
        try {
            if (mTextRight != null) {
                mTextRight.setTextColor(textColor);
            }
        }catch (Throwable t){
            t.printStackTrace();
        }
    }

    public int getRightTextColor(){
        return mTextRight.getCurrentTextColor();
    }

    public float getRightTextSize(){
        return mTextRight.getTextSize();
    }

    public void setRightTextSize(float textSize){
        try {
            if (mTextRight != null) {
                mTextRight.setTextSize(textSize);
            }
        }catch (Throwable t){
            t.printStackTrace();
        }
    }

    public float getLeftTextSize(){
        return mTextLeft.getTextSize();
    }

    public void setLeftTextSize(float textSize){
        try {
            if (mTextLeft != null) {
                mTextLeft.setTextSize(textSize);
            }
        }catch (Throwable t){
            t.printStackTrace();
        }
    }

    private <T extends View>T getView(int viewId){
        return (T)findViewById(viewId);
    }
}

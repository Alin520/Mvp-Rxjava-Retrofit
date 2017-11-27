package com.alin.hourse.common.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alin.hourse.common.R;


/**
 * @创建者 hailin
 * @创建时间 2017/8/30 11:43
 * @描述 ${toolbar}.
 */

public class AlinToolbar extends RelativeLayout {

    private int mBarColor;
    private String mBarTitleText;
    private float mBarTitleTextSize;
    private ColorStateList mBarTitleTextColor;
    private String mBarRightButtonText;
    private float mBarRightButtonTextSize;
    private ColorStateList mBarRightButtonTextColor;
    private Drawable mBarLeftImg;
    private Drawable mBarRightImg;
    private ImageView mImgBtnLeft;
    private ImageView mImgBtnRight;
    private TextView mTxtTitle;
    private TextView mBtnRight;
    private TextView mTxtLeft;
    private TextView mTxtRight;
    private String mBarLeftButtonText;
    private float mBarLeftButtonTextSize;
    private ColorStateList mBarLeftButtonTextColor;
    private Drawable mBarCenterImg;
    private TouchableImageButton mBtnCenter;
    private LinearLayout mBarCenterContent;
    private OnLeftClickListener mOnLeftClickListener;
    private OnCenterClickListener mOnCenterClickListener;
    private OnRightClickListener mOnRightClickListener;
    private View mRootView;

    public AlinToolbar(Context context) {
        this(context,null);
    }

    public AlinToolbar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AlinToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(attrs);
    }

    private void initialize(AttributeSet attrs) {
        initAttrs(attrs);
        initView();
    }

    @Override
    public void draw(Canvas canvas) {
        setView();
        super.draw(canvas);
    }



    private void initAttrs(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.AlinToolbar);
        if (ta != null) {
            try {
//               左边
                mBarLeftImg = ta.getDrawable(R.styleable.AlinToolbar_barLeftImg);       //左边图片
                mBarLeftButtonText = ta.getString(R.styleable.AlinToolbar_barLeftText); //         左边文字
                mBarLeftButtonTextSize = ta.getDimension(R.styleable.AlinToolbar_barLeftTextSize, 14);   //左边文字大小
                mBarLeftButtonTextColor = ta.getColorStateList(R.styleable.AlinToolbar_barLeftTextColor);//左边文字颜色
//                中间
                mBarCenterImg = ta.getDrawable(R.styleable.AlinToolbar_barRightImg);
                mBarColor = ta.getColor(R.styleable.AlinToolbar_barColor, getResources().getColor(R.color.white));      //toolBar背景色
                mBarTitleText = ta.getString(R.styleable.AlinToolbar_barTitleText);                         //标题（中间）
                mBarTitleTextSize = ta.getDimension(R.styleable.AlinToolbar_barTitleTextSize, 16);       //标题大小
                mBarTitleTextColor = ta.getColorStateList(R.styleable.AlinToolbar_barTitleTextColor);     //标题颜色
//              右边
                mBarRightImg = ta.getDrawable(R.styleable.AlinToolbar_barRightImg);     //右边图片
                mBarRightButtonText = ta.getString(R.styleable.AlinToolbar_barLeftText);         //         右边文字
                mBarRightButtonTextSize = ta.getDimension(R.styleable.AlinToolbar_barLeftTextSize, 14);     //右边文字大小
                mBarRightButtonTextColor = ta.getColorStateList(R.styleable.AlinToolbar_barLeftTextColor);     //右边文字颜色
            }catch (Throwable t){
                t.printStackTrace();
            }finally {
                ta.recycle();
            }
        }else {
            mBarColor = getResources().getColor(R.color.white);
            this.mBarTitleTextSize = 16;
            this.mBarRightButtonTextSize = 14;
        }
    }

    private void initView() {
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRootView = layoutInflater.inflate(R.layout.alin_toolbar, this);
        mBarCenterContent = (LinearLayout) mRootView.findViewById(R.id.bar_center_llyt); //中间部分
        mImgBtnLeft = (TouchableImageButton) mRootView.findViewById(R.id.imgBtnBack);      //左边图片
        mBtnCenter = (TouchableImageButton) mRootView.findViewById(R.id.imgBtnCenter);    //中间图片
        mImgBtnRight = (TouchableImageButton) mRootView.findViewById(R.id.imgBtnRight);  //右边图片
        //左边文字
        mTxtLeft = (TextView) mRootView.findViewById(R.id.txtLeft);
        //标题
        mTxtTitle = (TextView) mRootView.findViewById(R.id.txtTitle);
        //右边文字
        mTxtRight = (TextView) mRootView.findViewById(R.id.txtRight);

    }

    private void setView() {
        mRootView.setBackgroundColor(mBarColor);
        //        左边
        mTxtLeft.setTextSize(mBarLeftButtonTextSize);
        if (mBarLeftButtonTextColor != null) {
            mTxtLeft.setTextColor(mBarLeftButtonTextColor);
        }
        if (mBarLeftImg != null) {
            mImgBtnLeft.setImageDrawable(mBarLeftImg);
            mImgBtnLeft.setVisibility(VISIBLE);
        }
        if (!TextUtils.isEmpty(mBarLeftButtonText)) {
            mTxtLeft.setText(mBarLeftButtonText);
            mTxtLeft.setVisibility(VISIBLE);
        }
        mTxtLeft.setText(mBarLeftButtonText);

        //        中间
        mTxtTitle.setTextSize(mBarTitleTextSize);
        if (mBarTitleTextColor != null) {
            mTxtTitle.setTextColor(mBarTitleTextColor);
        }
        if (mBarCenterImg != null) {
            mBtnCenter.setImageDrawable(mBarCenterImg);
            mBtnCenter.setVisibility(VISIBLE);
        }
        if (!TextUtils.isEmpty(mBarTitleText)) {
            mTxtTitle.setText(mBarTitleText);
            mTxtTitle.setVisibility(VISIBLE);
        }

        //        右边
        mTxtRight.setTextSize(mBarRightButtonTextSize);
        if (mBarRightButtonTextColor != null) {
            mTxtRight.setTextColor(mBarRightButtonTextColor);
        }
        if (mBarRightImg != null) {
            mImgBtnRight.setImageDrawable(mBarRightImg);
            mImgBtnRight.setVisibility(VISIBLE);
        }
        if (!TextUtils.isEmpty(mBarRightButtonText)) {
            mTxtRight.setText(mBarRightButtonText);
            mTxtRight.setVisibility(VISIBLE);
        }

        /**
         *
         * 点击事件处理
         *
         * */

        //    左边
        mTxtLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnLeftClickListener != null) {
                    mOnLeftClickListener.onClick(view);
                }else if (getContext() instanceof Activity){
                    Activity activity = (Activity) getContext();
                    if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
                        activity.onBackPressed();
                    }
                }
            }
        });

        mImgBtnLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //        中间
        mBarCenterContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnCenterClickListener != null) {
                    mOnCenterClickListener.onClick(view);
                }
            }
        });

        //        右边
        mTxtRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnRightClickListener != null) {
                    mOnRightClickListener.onClick(view);
                }
            }
        });

        mImgBtnRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnRightClickListener != null) {
                    mOnRightClickListener.onClick(view);
                }
            }
        });
    }


    public int getBarColor() {
        return mBarColor;
    }

    public void setBarColor(int barColor) {
        mBarColor = barColor;
    }

    public String getBarTitleText() {
        return mBarTitleText;
    }

    public void setBarTitleText(String barTitleText) {
        mBarTitleText = barTitleText;
    }

    public float getBarTitleTextSize() {
        return mBarTitleTextSize;
    }

    public void setBarTitleTextSize(float barTitleTextSize) {
        mBarTitleTextSize = barTitleTextSize;
    }

    public ColorStateList getBarTitleTextColor() {
        return mBarTitleTextColor;
    }

    public void setBarTitleTextColor(ColorStateList barTitleTextColor) {
        mBarTitleTextColor = barTitleTextColor;
    }

    public String getBarRightButtonText() {
        return mBarRightButtonText;
    }

    public void setBarRightButtonText(String barRightButtonText) {
        mBarRightButtonText = barRightButtonText;
    }

    public float getBarRightButtonTextSize() {
        return mBarRightButtonTextSize;
    }

    public void setBarRightButtonTextSize(float barRightButtonTextSize) {
        mBarRightButtonTextSize = barRightButtonTextSize;
    }

    public ColorStateList getBarRightButtonTextColor() {
        return mBarRightButtonTextColor;
    }

    public void setBarRightButtonTextColor(ColorStateList barRightButtonTextColor) {
        mBarRightButtonTextColor = barRightButtonTextColor;
    }

    public Drawable getBarLeftImg() {
        return mBarLeftImg;
    }

    public void setBarLeftImg(Drawable barLeftImg) {
        mBarLeftImg = barLeftImg;
    }

    public Drawable getBarRightImg() {
        return mBarRightImg;
    }

    public void setBarRightImg(Drawable barRightImg) {
        mBarRightImg = barRightImg;
    }

    public ImageView getImgBtnLeft() {
        return mImgBtnLeft;
    }

    public void setImgBtnLeft(ImageView imgBtnLeft) {
        mImgBtnLeft = imgBtnLeft;
    }

    public ImageView getImgBtnRight() {
        return mImgBtnRight;
    }

    public void setImgBtnRight(ImageView imgBtnRight) {
        mImgBtnRight = imgBtnRight;
    }

    public TextView getTxtTitle() {
        return mTxtTitle;
    }

    public void setTxtTitle(TextView txtTitle) {
        mTxtTitle = txtTitle;
    }

    public TextView getBtnRight() {
        return mBtnRight;
    }

    public void setBtnRight(TextView btnRight) {
        mBtnRight = btnRight;
    }

    public TextView getTxtLeft() {
        return mTxtLeft;
    }

    public void setTxtLeft(TextView txtLeft) {
        mTxtLeft = txtLeft;
    }

    public TextView getTxtRight() {
        return mTxtRight;
    }

    public void setTxtRight(TextView txtRight) {
        mTxtRight = txtRight;
    }

    public String getBarLeftButtonText() {
        return mBarLeftButtonText;
    }

    public void setBarLeftButtonText(String barLeftButtonText) {
        mBarLeftButtonText = barLeftButtonText;
    }

    public float getBarLeftButtonTextSize() {
        return mBarLeftButtonTextSize;
    }

    public void setBarLeftButtonTextSize(float barLeftButtonTextSize) {
        mBarLeftButtonTextSize = barLeftButtonTextSize;
    }

    public ColorStateList getBarLeftButtonTextColor() {
        return mBarLeftButtonTextColor;
    }

    public void setBarLeftButtonTextColor(ColorStateList barLeftButtonTextColor) {
        mBarLeftButtonTextColor = barLeftButtonTextColor;
    }

    public Drawable getBarCenterImg() {
        return mBarCenterImg;
    }

    public void setBarCenterImg(Drawable barCenterImg) {
        mBarCenterImg = barCenterImg;
    }

    public TouchableImageButton getBtnCenter() {
        return mBtnCenter;
    }

    public void setBtnCenter(TouchableImageButton btnCenter) {
        mBtnCenter = btnCenter;
    }

    public LinearLayout getBarCenterContent() {
        return mBarCenterContent;
    }

    public void setBarCenterContent(LinearLayout barCenterContent) {
        mBarCenterContent = barCenterContent;
    }

    public OnLeftClickListener getOnLeftClickListener() {
        return mOnLeftClickListener;
    }

    public void setOnLeftClickListener(OnLeftClickListener onLeftClickListener) {
        mOnLeftClickListener = onLeftClickListener;
    }

    public OnCenterClickListener getOnCenterClickListener() {
        return mOnCenterClickListener;
    }

    public void setOnCenterClickListener(OnCenterClickListener onCenterClickListener) {
        mOnCenterClickListener = onCenterClickListener;
    }

    public OnRightClickListener getOnRightClickListener() {
        return mOnRightClickListener;
    }

    public void setOnRightClickListener(OnRightClickListener onRightClickListener) {
        mOnRightClickListener = onRightClickListener;
    }

    public interface OnLeftClickListener{
        void onClick(View view);
    }

    public interface OnCenterClickListener{
        void onClick(View view);
    }

    public interface OnRightClickListener{
        void onClick(View view);
    }
}

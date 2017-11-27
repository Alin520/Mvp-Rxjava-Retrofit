package com.alin.hourse.common.view.dialig;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alin.hourse.common.R;
import com.alin.hourse.common.base.CommonDialog;

/**
 * @创建者 hailin
 * @创建时间 2017/9/13 11:25
 * @描述 ${数据加载进度loading}.
 */

public abstract class LoadingProgressDialog extends CommonDialog {

    ImageView mImgLoading;

    TextView mLoadingInfo;

    private String mInfo;
    private boolean mCancelable = false;
    private final ProgressCancleListener mProgressCancleListener;

    public LoadingProgressDialog(AppCompatActivity activity, int layoutId, boolean cancelable, ProgressCancleListener cancleListener) {
        super(activity, R.layout.loading_dialog, true,null);
        this.settCallback(new LoadingProgressCallback());
        mCancelable = cancelable;
        mProgressCancleListener = cancleListener;
    }



    public class LoadingProgressCallback implements DialogCallback{
        @Override
        public void initChildren(CommonDialog commonDialog, Dialog dialog, View contentView) {
            dialog.setCanceledOnTouchOutside(mCancelable);
            dialog.setCancelable(mCancelable);
//            ButterKnife.bind(LoadingProgressDialog.this,dialog);
            mImgLoading = contentView.findViewById(R.id.imgLoading);
            mLoadingInfo = contentView.findViewById(R.id.txtLoadingInfo);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    mProgressCancleListener.progressCancle(dialogInterface);
                }
            });

        }

        @Override
        public void afterCreated(AppCompatDialogFragment fragment, Dialog dialog) {
            if (!TextUtils.isEmpty(mInfo)) {
                mLoadingInfo.setText(mInfo);
                mLoadingInfo.setVisibility(View.VISIBLE);
            }else {
                mLoadingInfo.setVisibility(View.GONE);
            }

            if (mImgLoading != null) {
                ((AnimationDrawable)mImgLoading.getDrawable()).start();
            }
        }

        @Override
        public void onDismiss() {
            ((AnimationDrawable)mImgLoading.getDrawable()).stop();
        }
    }


    public void show(String info){
        this.setInfo(info);
        super.show();
    }

    public String getInfo(){
        return this.mInfo;
    }

    public void setInfo(String info){
        this.mInfo = info;
    }

    public void setInfo(int infoId){
        this.setInfo(getResources().getString(infoId));
    }

}

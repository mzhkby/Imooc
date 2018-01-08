package mzh.it4pl.com.httpsdk.core.video;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import mzh.it4pl.com.httpsdk.activity.AdBrowserActivity;
import mzh.it4pl.com.httpsdk.adutil.Utils;
import mzh.it4pl.com.httpsdk.constant.SDKConstant;
import mzh.it4pl.com.httpsdk.core.AdParameters;
import mzh.it4pl.com.httpsdk.module.AdValue;
import mzh.it4pl.com.httpsdk.report.ReportManager;
import mzh.it4pl.com.httpsdk.widget.CustomVideoView;
import mzh.it4pl.com.httpsdk.widget.VideoFullDialog;

/**
 * @author: qndroid
 * @function: 广告业务逻辑层
 * @date: 16/6/20
 */
public class VideoAdSlot implements CustomVideoView.ADVideoPlayerListener {

    private Context mContext;
    /**
     * UI
     */
    private CustomVideoView mVideoView;
    private ViewGroup mParentView;//要添加到的父容器中
    /**
     * Data
     */
    private AdValue mXAdInstance;
    private AdSDKSlotListener mSlotListener;
    private boolean canPause = false; //是否可自动暂停标志位
    private int lastArea = 0; //防止将要滑入滑出时播放器的状态改变

    public VideoAdSlot(AdValue adInstance, AdSDKSlotListener slotLitener, CustomVideoView.ADFrameImageLoadListener frameLoadListener) {
        mXAdInstance = adInstance;
        mSlotListener = slotLitener;
        mParentView = slotLitener.getAdParent();
        mContext = mParentView.getContext();
        initVideoView(frameLoadListener);
    }

    private void initVideoView(CustomVideoView.ADFrameImageLoadListener frameImageLoadListener) {
        mVideoView = new CustomVideoView(mContext, mParentView);
        if (mXAdInstance != null) {
            mVideoView.setDataSource(mXAdInstance.resource);
            mVideoView.setFrameURI(mXAdInstance.thumb);
            mVideoView.setFrameLoadListener(frameImageLoadListener);
            mVideoView.setListener(this);
        }
        RelativeLayout paddingView = new RelativeLayout(mContext);
        paddingView.setBackgroundColor(mContext.getResources().getColor(android.R.color.black));
        paddingView.setLayoutParams(mVideoView.getLayoutParams());
        mParentView.addView(paddingView);
        mParentView.addView(mVideoView);
    }

    private boolean isPlaying() {
        if (mVideoView != null) {
            return mVideoView.isPlaying();
        }
        return false;
    }

    private boolean isRealPause() {
        if (mVideoView != null) {
            return mVideoView.isRealPause();
        }
        return false;
    }

    private boolean isComplete() {
        if (mVideoView != null) {
            return mVideoView.isComplete();
        }
        return false;
    }

    //pause the  video
    private void pauseVideo(boolean isAuto) {
        if (mVideoView != null) {
            if (isAuto) {
                //发自动暂停监测
                if (!isRealPause() && isPlaying()) {
                    try {
                        ReportManager.pauseVideoReport(mXAdInstance.event.pause.content, getPosition());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            mVideoView.seekAndPause(0);
        }
    }

    //resume the video
    private void resumeVideo() {
        if (mVideoView != null) {
            mVideoView.resume();
            if (isPlaying()) {
                sendSUSReport(true); //发自动播放监测
            }
        }
    }

    /**
     * 实现滑入播放，滑出暂停功能
     */
    public void updateAdInScrollView() {
        int currentArea = Utils.getVisiblePercent(mParentView);
        //小于0表示未出现在屏幕上，不做任何处理
        if (currentArea <= 0) {
            return;
        }
        //刚要滑入和滑出时，异常状态的处理
        if (Math.abs(currentArea - lastArea) >= 100) {
            return;
        }
        if (currentArea < SDKConstant.VIDEO_SCREEN_PERCENT) {
            //进入自动暂停状态
            if (canPause) {
                pauseVideo(true);
                //滑动事件过滤
                canPause = false;
            }
            lastArea = 0;
            mVideoView.setIsComplete(false); // 滑动出50%后标记为从头开始播
            mVideoView.setIsRealPause(false); //以前叫setPauseButtonClick()
            return;
        }

        //当视频进入真正的暂停状态时走此case
        if (isRealPause() || isComplete()) {
            //进入手动暂停或者播放结束，播放结束和不满足自动播放条件都作为手动暂停
            pauseVideo(false);
            canPause = false;
            return;
        }

        //满足自动播放条件或者用户主动点击播放，开始播放
        if (Utils.canAutoPlay(mContext, AdParameters.getCurrentSetting())
                || isPlaying()) {
            lastArea = currentArea;
            resumeVideo();
            canPause = true;
            mVideoView.setIsRealPause(false);
        } else {
            pauseVideo(false);
            mVideoView.setIsRealPause(true); //不能自动播放则设置为手动暂停效果
        }
    }

    public void destroy() {
        mVideoView.destroy();
        mVideoView = null;
        mContext = null;
        mXAdInstance = null;
    }

    /**
     * 实现play层接口
     */
    @Override
    public void onClickFullScreenBtn() {
        try {
            ReportManager.fullScreenReport(mXAdInstance.event.full.content, getPosition());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取videoview在当前界面的属性
        Bundle bundle = Utils.getViewProperty(mParentView);
        mParentView.removeView(mVideoView);
        //创建全屏dialog
        VideoFullDialog dialog = new VideoFullDialog(mContext, mVideoView, mXAdInstance,
                mVideoView.getCurrentPosition());
        dialog.setListener(new VideoFullDialog.FullToSmallListener() {
            @Override
            public void getCurrentPlayPosition(int position) {
                //全屏视频播放的时候点击了返回
                backToSmallMode(position);
            }

            @Override
            public void playComplete() {
                //全屏播放完成以后的事件回调
                bigPlayComplete();
            }
        });
        dialog.setViewBundle(bundle); //为Dialog设置播放器数据Bundle对象
        dialog.setSlotListener(mSlotListener);
        dialog.show();
    }

    /**
     * 返回小屏的时候调用
     * @param position
     */
    private void backToSmallMode(int position) {
        if (mVideoView.getParent() == null) {
            mParentView.addView(mVideoView);
        }
        //防止动画导致偏离父容器
        mVideoView.setTranslationY(0);
        //显示我们的全屏按钮
        mVideoView.isShowFullBtn(true);
        //小屏静音播放
        mVideoView.mute(true);
        //重新设置监听为我们的业务逻辑层
        mVideoView.setListener(this);
        //使播放器跳到指定位置并且播放
        mVideoView.seekAndResume(position);
        // 标为可自动暂停
        canPause = true;
    }

    /**
     * 全屏播放结束时的事件回调
     */
    private void bigPlayComplete() {
        if (mVideoView.getParent() == null) {
            mParentView.addView(mVideoView);
        }
        //防止动画导致偏离父容器
        mVideoView.setTranslationY(0);
        mVideoView.isShowFullBtn(true);
        mVideoView.mute(true);
        mVideoView.setListener(this);
        mVideoView.seekAndPause(0);
        canPause = false;
    }

    @Override
    public void onClickVideo() {
        String desationUrl = mXAdInstance.clickUrl;
        if (mSlotListener != null) {
            if (mVideoView.isFrameHidden() && !TextUtils.isEmpty(desationUrl)) {
                mSlotListener.onClickVideo(desationUrl);
                try {
                    ReportManager.pauseVideoReport(mXAdInstance.clickMonitor, mVideoView.getCurrentPosition()
                            / SDKConstant.MILLION_UNIT);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            //走默认样式
            if (mVideoView.isFrameHidden() && !TextUtils.isEmpty(desationUrl)) {
                Intent intent = new Intent(mContext, AdBrowserActivity.class);
                intent.putExtra(AdBrowserActivity.KEY_URL, mXAdInstance.clickUrl);
                mContext.startActivity(intent);
                try {
                    ReportManager.pauseVideoReport(mXAdInstance.clickMonitor, mVideoView.getCurrentPosition()
                            / SDKConstant.MILLION_UNIT);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onClickBackBtn() {
    }

    @Override
    public void onClickPlay() {
        sendSUSReport(false);
    }

    @Override
    public void onAdVideoLoadSuccess() {
        if (mSlotListener != null) {
            mSlotListener.onAdVideoLoadSuccess();
        }
    }

    @Override
    public void onAdVideoLoadFailed() {
        if (mSlotListener != null) {
            mSlotListener.onAdVideoLoadFailed();
        }
        //加载失败全部回到初始状态
        canPause = false;
    }

    @Override
    public void onAdVideoLoadComplete() {
        try {
            ReportManager.sueReport(mXAdInstance.endMonitor, false, getDuration());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mSlotListener != null) {
            mSlotListener.onAdVideoLoadComplete();
        }
        mVideoView.setIsRealPause(true);
    }

    @Override
    public void onBufferUpdate(int time) {
        try {
            ReportManager.suReport(mXAdInstance.middleMonitor, time / SDKConstant.MILLION_UNIT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getPosition() {
        return mVideoView.getCurrentPosition() / SDKConstant.MILLION_UNIT;
    }

    private int getDuration() {
        return mVideoView.getDuration() / SDKConstant.MILLION_UNIT;
    }

    /**
     * 发送视频开始播放监测
     *
     * @param isAuto
     */
    private void sendSUSReport(boolean isAuto) {
        try {
            ReportManager.susReport(mXAdInstance.startMonitor, isAuto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //传递消息到appcontext层
    public interface AdSDKSlotListener {

        public ViewGroup getAdParent();

        public void onAdVideoLoadSuccess();

        public void onAdVideoLoadFailed();

        public void onAdVideoLoadComplete();

        public void onClickVideo(String url);
    }
}

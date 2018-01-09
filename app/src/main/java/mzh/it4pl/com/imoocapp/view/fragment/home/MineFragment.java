package mzh.it4pl.com.imoocapp.view.fragment.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.sharesdk.framework.Platform;
import de.hdodenhof.circleimageview.CircleImageView;
import mzh.it4pl.com.httpsdk.adutil.ImageLoaderUtil;
import mzh.it4pl.com.imoocapp.R;
import mzh.it4pl.com.imoocapp.activity.LoginActivity;
import mzh.it4pl.com.imoocapp.activity.VideoSettingActivity;
import mzh.it4pl.com.imoocapp.manager.UserManager;
import mzh.it4pl.com.imoocapp.module.update.UpdateInfo;
import mzh.it4pl.com.imoocapp.service.update.UpdateService;
import mzh.it4pl.com.imoocapp.share.ShareDialog;
import mzh.it4pl.com.imoocapp.util.Util;
import mzh.it4pl.com.imoocapp.view.CommonDialog;
import mzh.it4pl.com.imoocapp.view.MyQrCodeDialog;
import mzh.it4pl.com.imoocapp.view.fragment.BaseFragment;

/**
 * @author: 马中辉
 * Email:A908391541@163.com
 * @date: 2018/1/4 10:06
 * 类描述:
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    /**
     * UI
     */
    private View mContentView;
    private RelativeLayout mLoginLayout;
    private CircleImageView mPhotoView, mUserPhotoView;
    private TextView mLoginInfoView;
    private TextView mLoginView;
    private RelativeLayout mLoginedLayout;
    private TextView mUserNameView;
    private TextView mTickView;
    private TextView mVideoPlayerView;
    private TextView mShareView;
    private TextView mQrCodeView;
    private TextView mUpdateView;

    /**
     * 广播接收器
     */
    private LoginBroadcastReceiver mReceiver = new LoginBroadcastReceiver();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        registerLoginBroadcast();
    }

    private void registerLoginBroadcast() {
        IntentFilter filter = new IntentFilter(LoginActivity.LOGIN_ACTION);
        LocalBroadcastManager.getInstance(mContext).registerReceiver(mReceiver, filter);
    }

    private void unregisterLoginBroadcast() {
        LocalBroadcastManager.getInstance(mContext).unregisterReceiver(mReceiver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_mine, null, false);
        initView();
        return mContentView;
    }

    private void initView() {
        mLoginLayout = mContentView.findViewById(R.id.login_layout);
        mLoginLayout.setOnClickListener(this);
        mLoginedLayout = mContentView.findViewById(R.id.logined_layout);
        mLoginedLayout.setOnClickListener(this);

        mPhotoView = mContentView.findViewById(R.id.photo_view);
        mPhotoView.setOnClickListener(this);
        mUserPhotoView = mContentView.findViewById(R.id.user_photo_view);
        mLoginView = mContentView.findViewById(R.id.login_view);
        mLoginView.setOnClickListener(this);
        mVideoPlayerView = mContentView.findViewById(R.id.video_setting_view);
        mVideoPlayerView.setOnClickListener(this);
        mShareView = mContentView.findViewById(R.id.share_imooc_view);
        mShareView.setOnClickListener(this);
        mQrCodeView = mContentView.findViewById(R.id.my_qrcode_view);
        mQrCodeView.setOnClickListener(this);
        mLoginInfoView = mContentView.findViewById(R.id.login_info_view);
        mUserNameView = mContentView.findViewById(R.id.username_view);
        mTickView = mContentView.findViewById(R.id.tick_view);

        mUpdateView = mContentView.findViewById(R.id.update_view);
        mUpdateView.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        //根据用户信息更新我们的fragment
        if (UserManager.getUserManager().hasLogin()) {
            if (mLoginedLayout.getVisibility() == View.GONE) {
                mLoginLayout.setVisibility(View.GONE);
                mLoginedLayout.setVisibility(View.VISIBLE);
                mUserNameView.setText(UserManager.getUserManager().getUser().data.name);
                mTickView.setText(UserManager.getUserManager().getUser().data.tick);
                ImageLoaderUtil.getInstance(mContext).displayImage(mUserPhotoView, UserManager.getUserManager().getUser().data.photoUrl);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterLoginBroadcast();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_setting_view:
                mContext.startActivity(new Intent(mContext, VideoSettingActivity.class));
                break;
            case R.id.update_view:
                checkVersion();
                break;
            case R.id.login_layout:
            case R.id.login_view:
                if (!UserManager.getUserManager().hasLogin()) {
                    toLogin();
                }
                break;
            case R.id.my_qrcode_view:
                if (!UserManager.getUserManager().hasLogin()) {
                    //未登陆，去登陆。
                    toLogin();
                } else {
                    //已登陆根据用户ID生成二维码显示
                    MyQrCodeDialog dialog = new MyQrCodeDialog(mContext);
                    dialog.show();
                }
                break;
            case R.id.share_imooc_view:
                shareFriend();
                break;
            default:
                break;
        }
    }

    /**
     * 去登录页面
     */
    private void toLogin() {
        Intent intent = new Intent(mContext, LoginActivity.class);
        mContext.startActivity(intent);
    }

    /**
     * 分享慕课网给好友
     */
    private void shareFriend() {
        ShareDialog dialog = new ShareDialog(mContext, false);
        dialog.setShareType(Platform.SHARE_TEXT);
        dialog.setShareTitle("慕课网");
        dialog.setShareTitleUrl("http://www.imooc.com");
        dialog.setShareText("慕课网");
        dialog.setShareSite("imooc");
        dialog.setShareSiteUrl("http://www.imooc.com");
        dialog.show();
    }

    /**
     * 发送版本检查请求  由于没有服务器，所以使用本地修改值的方式进行测试
     */
    private void checkVersion() {
        /*RequestCenter.checkVersion(new DisposeDataListener() {
            //请求成功回调
            @Override
            public void onSuccess(Object responseObj) {
                final UpdateModel updateModel = (UpdateModel) responseObj;
                //判断本地程序版本号与服务器返回版本号大小
                if (Util.getVersionCode(mContext) < updateModel.data.currentVersion) {
                    //说明有新版本
                    CommonDialog dialog = new CommonDialog(mContext, getString(R.string.update_new_version),
                            getString(R.string.update_title), getString(R.string.update_install),
                            getString(R.string.cancel), new CommonDialog.DialogClickListener() {
                        @Override
                        public void onDialogClick() {
                            //安装事件回调   启动更新服务
                            Intent intent = new Intent(mContext, UpdateService.class);
                            mContext.startService(intent);
                        }
                    });
                    dialog.show();
                } else {
                    //说明没有新版本
                    Toast.makeText(mContext,"当前版本已是最新版本@",Toast.LENGTH_LONG).show();
                }
            }

            //请求失败回调
            @Override
            public void onFailure(Object reasonObj) {

            }
        });*/
        UpdateInfo updateInfo = new UpdateInfo();
        updateInfo.currentVersion = 1;
        //判断本地程序版本号与服务器返回版本号大小
        if (Util.getVersionCode(mContext) < updateInfo.currentVersion) {
            //说明有新版本
            CommonDialog dialog = new CommonDialog(mContext, getString(R.string.update_new_version),
                    getString(R.string.update_title), getString(R.string.update_install),
                    getString(R.string.cancel), new CommonDialog.DialogClickListener() {
                @Override
                public void onDialogClick() {
                    //安装事件回调   启动更新服务
                    Intent intent = new Intent(mContext, UpdateService.class);
                    mContext.startService(intent);
                }
            });
            dialog.show();
        } else {
            //说明没有新版本
            Toast.makeText(mContext, "当前版本已是最新版本", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 自定义广播接收器，用来处理我们的登录广播
     */
    private class LoginBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //更新我们Fragment的UI
            mLoginLayout.setVisibility(View.GONE);
            mLoginedLayout.setVisibility(View.VISIBLE);
            mUserNameView.setText(UserManager.getUserManager().getUser().data.name);
            mTickView.setText(UserManager.getUserManager().getUser().data.tick);
            ImageLoaderUtil.getInstance(mContext).displayImage(mUserPhotoView, UserManager.getUserManager().getUser().data.photoUrl);
        }
    }
}

package mzh.it4pl.com.imoocapp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import mzh.it4pl.com.httpsdk.constant.SDKConstant;
import mzh.it4pl.com.httpsdk.core.AdParameters;
import mzh.it4pl.com.imoocapp.R;
import mzh.it4pl.com.imoocapp.activity.base.BaseActivity;
import mzh.it4pl.com.imoocapp.db.SharedPreferenceManager;

public class VideoSettingActivity extends BaseActivity implements View.OnClickListener {

    /**
     * UI
     */
    private RelativeLayout mWifiLayout;
    private RelativeLayout mAlwayLayout;
    private RelativeLayout mNeverLayout;
    private CheckBox mWifiBox, mAlwayBox, mNeverBox;
    private ImageView mBackView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_setting);

        initView();
    }

    /**
     * 初始化用到的所有组件
     */
    private void initView() {
        mBackView = findViewById(R.id.back_view);
        mWifiLayout = findViewById(R.id.wifi_layout);
        mWifiBox = findViewById(R.id.wifi_check_box);
        mAlwayLayout = findViewById(R.id.alway_layout);
        mAlwayBox = findViewById(R.id.alway_check_box);
        mNeverLayout = findViewById(R.id.close_layout);
        mNeverBox = findViewById(R.id.close_check_box);

        mBackView.setOnClickListener(this);
        mWifiLayout.setOnClickListener(this);
        mAlwayLayout.setOnClickListener(this);
        mNeverLayout.setOnClickListener(this);

        int currentSetting = SharedPreferenceManager.getInstance().getInt(
                SharedPreferenceManager.VIDEO_PLAY_SETTING, 1);
        switch (currentSetting) {
            case 0:
                mAlwayBox.setBackgroundResource(R.drawable.setting_selected);
                mWifiBox.setBackgroundResource(0);
                mNeverLayout.setBackgroundResource(0);
                break;
            case 1:
                mAlwayBox.setBackgroundResource(0);
                mWifiBox.setBackgroundResource(R.drawable.setting_selected);
                mNeverLayout.setBackgroundResource(0);
                break;
            case 2:
                mAlwayBox.setBackgroundResource(0);
                mWifiBox.setBackgroundResource(0);
                mNeverLayout.setBackgroundResource(R.drawable.setting_selected);
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.alway_layout:
                SharedPreferenceManager.getInstance().putInt(SharedPreferenceManager.VIDEO_PLAY_SETTING, 0);

                //通知当前设置到视频播放SDK
                AdParameters.setCurrentSetting(SDKConstant.AutoPlaySetting.AUTO_PLAY_3G_4G_WIFI);

                //设置对应的CheckBox为选中状态
                mAlwayBox.setBackgroundResource(R.drawable.setting_selected);
                mWifiBox.setBackgroundResource(0);
                mNeverLayout.setBackgroundResource(0);
                break;
            case R.id.close_layout:
                SharedPreferenceManager.getInstance().putInt(SharedPreferenceManager.VIDEO_PLAY_SETTING, 2);

                //通知当前设置到视频播放SDK
                AdParameters.setCurrentSetting(SDKConstant.AutoPlaySetting.AUTO_PLAY_NEVER);

                //设置对应的CheckBox为选中状态
                mAlwayBox.setBackgroundResource(0);
                mWifiBox.setBackgroundResource(0);
                mNeverLayout.setBackgroundResource(R.drawable.setting_selected);
                break;
            case R.id.wifi_layout:
                SharedPreferenceManager.getInstance().putInt(SharedPreferenceManager.VIDEO_PLAY_SETTING, 1);

                //通知当前设置到视频播放SDK
                AdParameters.setCurrentSetting(SDKConstant.AutoPlaySetting.AUTO_PLAY_ONLY_WIFI);

                //设置对应的CheckBox为选中状态
                mAlwayBox.setBackgroundResource(0);
                mWifiBox.setBackgroundResource(R.drawable.setting_selected);
                mNeverLayout.setBackgroundResource(0);
                break;
            case R.id.back_view:
                finish();
                break;
            default:
                break;
        }
    }
}

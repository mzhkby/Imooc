package mzh.it4pl.com.imoocapp.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.sharesdk.framework.Platform;
import mzh.it4pl.com.httpsdk.adutil.Utils;
import mzh.it4pl.com.imoocapp.R;
import mzh.it4pl.com.imoocapp.activity.base.BaseActivity;
import mzh.it4pl.com.imoocapp.adpater.PhotoPagerAdapter;
import mzh.it4pl.com.imoocapp.share.ShareDialog;
import mzh.it4pl.com.imoocapp.util.Util;

/**
 * @author: 马中辉
 * Email:A908391541@163.com
 * @date: 2018/1/9 9:50
 * 类描述:显示产品大图
 */
public class PhotoViewActivity extends BaseActivity implements View.OnClickListener {
    public static final String PHOTO_LIST = "photo_list";
    /**
     * UI
     */
    private ViewPager mPager;
    private TextView mIndictorView;
    private ImageView mShareView;

    /**
     * Data
     */
    private PhotoPagerAdapter mAdapter;
    private ArrayList<String> mPhotoLists;
    private int mLength;
    private int currentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        initData();
        initView();
    }

    /**
     * 初始化要显示的图片地址列表
     */
    private void initData() {
        mPhotoLists = getIntent().getStringArrayListExtra(PHOTO_LIST);
        mLength = mPhotoLists.size();
    }

    private void initView() {
        mPager = findViewById(R.id.photo_pager);
        mIndictorView = findViewById(R.id.indictor_view);
        mIndictorView.setText("1/" + mLength);
        mShareView = findViewById(R.id.share_view);
        mShareView.setOnClickListener(this);
        mPager.setPageMargin(Utils.dip2px(this, 30));

        mAdapter = new PhotoPagerAdapter(this, mPhotoLists);
        mPager.setAdapter(mAdapter);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mIndictorView.setText(String.valueOf((position + 1)).concat("/").
                        concat(String.valueOf(mLength)));
                currentPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        Util.hideSoftInputMethod(this, mIndictorView);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_view:
                ShareDialog dialog = new ShareDialog(this, false);
                dialog.setShareType(Platform.SHARE_IMAGE);
                dialog.setShareTitle("慕课网");
                dialog.setShareTitleUrl("http://www.imooc.com");
                dialog.setShareText("慕课网");
                dialog.setShareSite("imooc");
                dialog.setShareSiteUrl("http://www.imooc.com");
                dialog.setImagePhoto("http://e.hiphotos.baidu.com/image/pic/item/f7246b600c338744e7762fb6580fd9f9d62aa04c.jpg");
                dialog.show();
                break;
            default:
                break;
        }

    }
}

package mzh.it4pl.com.imoocapp.view.fragment.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mzh.it4pl.com.httpsdk.okHttp.listener.DisposeDataListener;
import mzh.it4pl.com.imoocapp.R;
import mzh.it4pl.com.imoocapp.adpater.CourseAdapter;
import mzh.it4pl.com.imoocapp.constant.Constant;
import mzh.it4pl.com.imoocapp.network.http.RequestCenter;
import mzh.it4pl.com.imoocapp.view.fragment.BaseFragment;
import mzh.it4pl.com.imoocapp.zxing.app.CaptureActivity;

import static android.content.ContentValues.TAG;

/**
 * @author: 马中辉
 * Email:A908391541@163.com
 * @date: 2017/12/27 11:25
 * 类描述:
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private static final int REQUEST_QRCODE = 0x01;
    /**
     * UI
     */
    private View mContentView;
    private ListView mListView;
    private TextView mCategoryView;
    private TextView mSearchView;
    private ImageView mLoadingView;
    private TextView mQRCodeView;
    /**
     * data
     */
    private CourseAdapter mAdapter;
    private List<Integer> list = new ArrayList<>();
    private ArrayList<String> urls = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestRecommandData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = getActivity();
        mContentView = inflater.inflate(R.layout.fragment_home, container, false);
        initView();
        initData();
        return mContentView;
    }

    private void initView() {
        mQRCodeView = mContentView.findViewById(R.id.qrcode_view);
        mQRCodeView.setOnClickListener(this);
        mCategoryView = mContentView.findViewById(R.id.category_view);
        mCategoryView.setOnClickListener(this);
        mSearchView = mContentView.findViewById(R.id.search_view);
        mSearchView.setOnClickListener(this);
        mListView = mContentView.findViewById(R.id.list_view);
        mListView.setOnItemClickListener(this);
        mLoadingView = mContentView.findViewById(R.id.loading_view);
        //启动我们的loading动画
        AnimationDrawable anim = (AnimationDrawable) mLoadingView.getDrawable();
        anim.start();
    }

    private void initData() {
        mLoadingView.setVisibility(View.GONE);
        mListView.setVisibility(View.VISIBLE);

        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        mAdapter = new CourseAdapter(mContext, list);
        mListView.setAdapter(mAdapter);

        /**
         * 为ListView添加滑动事件监听
         */
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                mAdapter.updateAdInScrollView();
            }
        });
    }

    /**
     * 发送首页列表数据请求
     */
    private void requestRecommandData() {
        RequestCenter.requestRecommandData(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                //完成我们真正的功能逻辑
                Log.e(TAG, "请求成功:" + responseObj.toString());
            }

            @Override
            public void onFailure(Object reasonObj) {
                //提示用户网络有问题
                Log.e(TAG, "请求失败:" + reasonObj.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.qrcode_view:
                Log.v(TAG, "二维码:");
                if (hasPermission(Constant.HARDWEAR_CAMERA_PERMISSION)) {
                    Log.v(TAG, "二维码:123");
                    doOpenCamera();
                } else {
                    Log.v(TAG, "二维码:123456");
                    requestPermission(Constant.HARDWEAR_CAMERA_CODE, Constant.HARDWEAR_CAMERA_PERMISSION);
                }
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void doOpenCamera() {
        Intent intent = new Intent(mContext, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_QRCODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_QRCODE:
                if (resultCode == Activity.RESULT_OK) {
                    String code = data.getStringExtra("SCAN_RESULT");
                    getCode(code);
                }
                if (resultCode == 300) {
                    String code = data.getStringExtra("result");
                    getCode(code);
                }
                break;
        }
    }

    private void getCode(String code) {
        if (code.contains("http") || code.contains("https")) {
            Toast.makeText(mContext, "二维码:" + code, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent();
//Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(code);
            intent.setData(content_url);
            //是否需要指定浏览器
            //intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
            startActivity(intent);

                        /*Intent intent = new Intent(mContext, AdBrowserActivity.class);
                        intent.putExtra(AdBrowserActivity.KEY_URL, code);
                        startActivity(intent);*/
        } else {
            Toast.makeText(mContext, code, Toast.LENGTH_SHORT).show();
        }
    }
}

package mzh.it4pl.com.imoocapp.adpater;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;
import mzh.it4pl.com.httpsdk.activity.AdBrowserActivity;
import mzh.it4pl.com.httpsdk.adutil.Utils;
import mzh.it4pl.com.httpsdk.core.AdContextInterface;
import mzh.it4pl.com.httpsdk.core.video.VideoAdContext;
import mzh.it4pl.com.httpsdk.imageloader.ImageLoaderManager;
import mzh.it4pl.com.imoocapp.R;

/**
 * @author: 马中辉
 * Email:A908391541@163.com
 * @date: 2018/1/4 10:33
 * 类描述:
 */

public class CourseAdapter extends BaseAdapter {

    private Context context;
    private List<Integer> list;
    private LayoutInflater layoutInflater;
    private ViewHolder mViewHolder;
    private ImageLoaderManager mImagerLoader;
    private VideoAdContext mAdsdkContext;
    private List<String> urls = new ArrayList<>();
    public CourseAdapter(Context context, List<Integer> list){
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
        mImagerLoader = ImageLoaderManager.getInstance(context);

    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            if(position%3==0){
                //显示video卡片
                mViewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.item_video_layout, parent, false);
                mViewHolder.mVieoContentLayout = (RelativeLayout)
                        convertView.findViewById(R.id.video_ad_layout);
                mViewHolder.mLogoView = (CircleImageView) convertView.findViewById(R.id.item_logo_view);
                mViewHolder.mTitleView = (TextView) convertView.findViewById(R.id.item_title_view);
                mViewHolder.mInfoView = (TextView) convertView.findViewById(R.id.item_info_view);
                mViewHolder.mFooterView = (TextView) convertView.findViewById(R.id.item_footer_view);
                mViewHolder.mShareView = (ImageView) convertView.findViewById(R.id.item_share_view);
                //为对应布局创建播放器
                mAdsdkContext = new VideoAdContext(mViewHolder.mVieoContentLayout,
                        new Gson().toJson("传入视频数据json进行解析"), null);
                mAdsdkContext.setAdResultListener(new AdContextInterface() {
                    @Override
                    public void onAdSuccess() {
                    }

                    @Override
                    public void onAdFailed() {
                    }

                    @Override
                    public void onClickVideo(String url) {
                        Intent intent = new Intent(context, AdBrowserActivity.class);
                        intent.putExtra(AdBrowserActivity.KEY_URL, url);
                        context.startActivity(intent);
                    }
                });
            }else{
                mViewHolder = new ViewHolder();
                convertView = layoutInflater.inflate(R.layout.item_product_card_one_layout,parent,false);
                mViewHolder.mLogoView =  convertView.findViewById(R.id.item_logo_view);
                mViewHolder.mTitleView = convertView.findViewById(R.id.item_title_view);
                mViewHolder.mInfoView =  convertView.findViewById(R.id.item_info_view);
                mViewHolder.mFooterView = convertView.findViewById(R.id.item_footer_view);
                mViewHolder.mPriceView = convertView.findViewById(R.id.item_price_view);
                mViewHolder.mFromView = convertView.findViewById(R.id.item_from_view);
                mViewHolder.mZanView = convertView.findViewById(R.id.item_zan_view);
                mViewHolder.mProductLayout = convertView.findViewById(R.id.product_photo_layout);
            }

            convertView.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        if(position%3==0){
        }else{
            urls.clear();
            Random rand = new Random();
            int next = rand.nextInt(15)+1;
            for (int i=0;i<next;i++){
                urls.add("http://e.hiphotos.baidu.com/image/pic/item/f7246b600c338744e7762fb6580fd9f9d62aa04c.jpg");
            }

            mViewHolder.mProductLayout.removeAllViews();

            //动态添加多个imageview
            for (String url : urls) {
                mViewHolder.mProductLayout.addView(createImageView(url));
            }
        }

        return convertView;
    }

    /**
     * 第二步：自动播放方法
     */
    public void updateAdInScrollView() {
        if (mAdsdkContext != null) {
            mAdsdkContext.updateAdInScrollView();
        }
    }

    //动态添加ImageView
    private ImageView createImageView(String url) {
        ImageView photoView = new ImageView(context);
        LinearLayout.LayoutParams params = new LinearLayout.
                LayoutParams(Utils.dip2px(context, 100),
                LinearLayout.LayoutParams.MATCH_PARENT);
        params.leftMargin = Utils.dip2px(context, 5);
        photoView.setLayoutParams(params);
        mImagerLoader.displayImage(photoView, url);
        return photoView;
    }

    private static class ViewHolder {
        //所有Card共有属性
        private CircleImageView mLogoView;
        private TextView mTitleView;
        private TextView mInfoView;
        private TextView mFooterView;
        //Video Card特有属性
        private RelativeLayout mVieoContentLayout;
        private ImageView mShareView;

        //Video Card外所有Card具有属性
        private TextView mPriceView;
        private TextView mFromView;
        private TextView mZanView;
        //Card One特有属性
        private LinearLayout mProductLayout;
        //Card Two特有属性
        private ImageView mProductView;
        //Card Three特有属性
        private ViewPager mViewPager;
    }

}

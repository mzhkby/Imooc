package mzh.it4pl.com.imoocapp.adpater;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

import mzh.it4pl.com.httpsdk.imageloader.ImageLoaderManager;

/**
 * @author: 马中辉
 * Email:A908391541@163.com
 * @date: 2018/1/9 10:01
 * 类描述:
 */

public class PhotoPagerAdapter extends PagerAdapter {
    private Context mContext;
    private ArrayList<String> mPhotoLists;
    private ImageLoaderManager mLoader;
    public PhotoPagerAdapter(Context context, ArrayList<String> list){
        mContext = context;
        mPhotoLists = list;
        mLoader = ImageLoaderManager.getInstance(mContext);
    }
    @Override
    public int getCount() {
        return mPhotoLists.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView photoView = new PhotoView(mContext);
        //使用图片加载组件为我们的PhotoView显示图片
        mLoader.displayImage(photoView,mPhotoLists.get(position));
        container.addView(photoView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        return photoView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }
}

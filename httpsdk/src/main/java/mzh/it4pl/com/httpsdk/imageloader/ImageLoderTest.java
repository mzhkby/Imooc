package mzh.it4pl.com.httpsdk.imageloader;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * @author: 马中辉
 * Email:A908391541@163.com
 * @date: 2017/12/29 14:19
 * 类描述:ImageLoader网络图片测试
 */

public class ImageLoderTest {
    private void testApi(){

        /**
         * 为我们的ImageLoader去配置参数
         */
        //ImageLoaderConfiguration configuration = ImageLoaderConfiguration.Builder(context).build();
        /**
         * 我们先来获取到ImageLoader的一个实例
         */
        ImageLoader imageLoader = ImageLoader.getInstance();
        /**
         * 为我们显示图片的时候去进行一个配置
         */
        DisplayImageOptions options = new DisplayImageOptions.Builder().build();
        /**
         * 使用displayImage去加载图片
         */
        /*imageLoader.displayImage("url",imageView,options,new SimpleImageLoadingListener(){
            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                super.onLoadingCancelled(imageUri, view);
            }

            @Override
            public void onLoadingStarted(String imageUri, View view) {
                super.onLoadingStarted(imageUri, view);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                super.onLoadingComplete(imageUri, view, loadedImage);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                super.onLoadingFailed(imageUri, view, failReason);
            }
        });

        ImageLoaderManager.getInstance(context).displayImage();*/
    }
}

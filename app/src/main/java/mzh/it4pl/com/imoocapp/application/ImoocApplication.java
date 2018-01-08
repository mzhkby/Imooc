package mzh.it4pl.com.imoocapp.application;

import android.app.Application;

/**
 * @author :马中辉
 * Email:A908391541@163.com
 * @date: 2017/12/27 10:31
 * 类描述:1.整个程序的入口 2.初始化工作
 * 3.为整个应用的其他模块提供上下文
 */

public class ImoocApplication extends Application {
    private static ImoocApplication mApplication = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    public static ImoocApplication getInstance(){
        return mApplication;
    }
}

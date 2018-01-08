package mzh.it4pl.com.imoocapp.db;

import android.content.Context;
import android.content.SharedPreferences;

import mzh.it4pl.com.imoocapp.application.ImoocApplication;

/**
 * @author: 马中辉
 * Email:A908391541@163.com
 * @date: 2018/1/8 15:14
 * 类描述:配置文件工具类
 */

public class SharedPreferenceManager {
    //当前类的实例
    private static SharedPreferenceManager mInstance;
    private static SharedPreferences sp;
    private static SharedPreferences.Editor editorl;

    //文件名
    private static final String SHARE_PREFERENCE_NAME = "imooc.pre";
    //视频播放设置
    public static final String VIDEO_PLAY_SETTING = "video_setting";

    public static SharedPreferenceManager getInstance(){
        if(mInstance==null){
            synchronized (SharedPreferenceManager.class){
                if(mInstance==null){
                    mInstance = new SharedPreferenceManager();
                }
            }
        }
        return mInstance;
    }

    private SharedPreferenceManager(){
        sp = ImoocApplication.getInstance().getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);
        editorl = sp.edit();
    }

    /**
     * 对INt类型的写入
     * @param key
     * @param value
     */
    public void putInt(String key,int value){
        editorl.putInt(key,value);
        editorl.commit();
    }

    /**
     * 对Int类型的读取
     * @param key
     * @param defulValue
     * @return
     */
    public int getInt(String key,int defulValue){
        return sp.getInt(key,defulValue);
    }
}

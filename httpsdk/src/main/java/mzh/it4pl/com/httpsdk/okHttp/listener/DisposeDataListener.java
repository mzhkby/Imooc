package mzh.it4pl.com.httpsdk.okHttp.listener;

/**
 * @author: 马中辉
 * Email:A908391541@163.com
 * @date: 2017/12/27 15:41
 * 类描述:自定义事件监听
 */

public interface DisposeDataListener {
    /**
     * 请求成功回调事件处理
     */
    public void onSuccess(Object responseObj);

    /**
     * 请求失败回调事件处理
     */
    public void onFailure(Object reasonObj);
}

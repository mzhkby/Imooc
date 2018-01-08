package mzh.it4pl.com.httpsdk.okHttp;

import mzh.it4pl.com.httpsdk.module.AdInstance;
import mzh.it4pl.com.httpsdk.okHttp.listener.DisposeDataHandle;
import mzh.it4pl.com.httpsdk.okHttp.listener.DisposeDataListener;
import mzh.it4pl.com.httpsdk.okHttp.request.CommonRequest;

/**
 * Created by renzhiqiang on 16/10/27.
 *
 * @function sdk请求发送中心
 */
public class RequestCenter {

    /**
     * 发送广告请求
     */
    public static void sendImageAdRequest(String url, DisposeDataListener listener) {

        CommonOkHttpClient.post(CommonRequest.createPostRequest(url, null),
                new DisposeDataHandle(listener, AdInstance.class));
    }
}

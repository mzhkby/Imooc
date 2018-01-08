package mzh.it4pl.com.imoocapp.network.http;

import mzh.it4pl.com.httpsdk.okHttp.CommonOkHttpClient;
import mzh.it4pl.com.httpsdk.okHttp.listener.DisposeDataHandle;
import mzh.it4pl.com.httpsdk.okHttp.listener.DisposeDataListener;
import mzh.it4pl.com.httpsdk.okHttp.request.CommonRequest;
import mzh.it4pl.com.httpsdk.okHttp.request.RequestParams;
import mzh.it4pl.com.imoocapp.module.recommand.BaseRecommandModel;

/**
 * @author: 马中辉
 * Email:A908391541@163.com
 * @date: 2018/1/4 10:44
 * 类描述:存放应用中所有的请求
 */

public class RequestCenter {
    //根据参数发送所有的post请求
    private static void postRequest(String url, RequestParams params, DisposeDataListener listener, Class<?> clazz){
        CommonOkHttpClient.get(CommonRequest.createGetRequest(url,params),new DisposeDataHandle(listener,clazz));
    }

    /**
     * 真正的发送我们的首页请求
     * @param listener
     */
    public static void requestRecommandData(DisposeDataListener listener){
        RequestCenter.postRequest(HttpConstants.HOME_RECOMMAND,null,listener, BaseRecommandModel.class);
    }
}

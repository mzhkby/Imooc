package mzh.it4pl.com.httpsdk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @author: 马中辉
 * Email:A908391541@163.com
 * @date: 2017/12/27 17:25
 * 类描述:测试
 */
public class test extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void test(){
        /*CommonOkHttpClient.sendRequest(CommonRequest.createGetRequest("http://www.imooc.com",null),
                new CommonJsonCallback(new DisposeDataHandle(new DisposeDataListener() {
                    @Override
                    public void onSuccess(Object responseObj) {

                    }

                    @Override
                    public void onFailure(Object reasonObj) {

                    }
                })));*/
    }
}

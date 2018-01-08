package mzh.it4pl.com.httpsdk.okHttp.exception;

/**
 * @author: 马中辉
 * Email:A908391541@163.com
 * @date: 2017/12/27 15:57
 * 类描述:自定义异常类
 */
public class OkHttpException extends Exception{
    private static final long serialVersionUID = 1L;
    /**
     * the server return code
     */
    private int ecode;
    /**
     * the server return error message
     */
    private Object emsg;

    public OkHttpException(int ecode,Object emsg){
        this.ecode = ecode;
        this.emsg = emsg;
    }
    public int getEcode(){return ecode;}

    public Object getEmsg(){return emsg;}
}

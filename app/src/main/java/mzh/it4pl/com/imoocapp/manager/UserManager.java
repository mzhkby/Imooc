package mzh.it4pl.com.imoocapp.manager;

import mzh.it4pl.com.imoocapp.module.user.User;

/**
 * @author: 马中辉
 * Email:A908391541@163.com
 * @date: 2018/1/8 17:19
 * 类描述:单例管理登陆用户信息
 */

public class UserManager {

    private static UserManager userManager = null;

    private User mUser;

    public static UserManager getUserManager(){
        if(userManager==null){
            synchronized (UserManager.class){
                if(userManager==null){
                    userManager = new UserManager();
                }
            }
        }
        return userManager;
    }

    //保存方法
    public void setUser(User user){
        mUser = user;
    }
    //获取方法
    public User getUser(){
        return mUser;
    }

    //判断用户是否登录
    public boolean hasLogin(){
        return mUser==null ?false:true;
    }
}

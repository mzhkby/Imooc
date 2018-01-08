package mzh.it4pl.com.imoocapp.view.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mzh.it4pl.com.imoocapp.R;
import mzh.it4pl.com.imoocapp.view.fragment.BaseFragment;

/**
 * @author: 马中辉
 * Email:A908391541@163.com
 * @date: 2018/1/4 10:06
 * 类描述:
 */
public class MineFragment extends BaseFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }
}

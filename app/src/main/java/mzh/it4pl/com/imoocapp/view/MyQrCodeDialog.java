package mzh.it4pl.com.imoocapp.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import mzh.it4pl.com.httpsdk.adutil.Utils;
import mzh.it4pl.com.imoocapp.R;
import mzh.it4pl.com.imoocapp.manager.UserManager;
import mzh.it4pl.com.imoocapp.util.Util;

/**
 * Created by renzhiqiang on 16/8/19.
 */
public class MyQrCodeDialog extends Dialog {

    private Context mContext;

    /**
     * UI
     */
    private ImageView mQrCodeView;
    private TextView mTickView;
    private TextView mCloseView;

    public MyQrCodeDialog(Context context) {
        super(context, 0);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_mycode_layout);
        initView();
    }

    private void initView() {
        mQrCodeView = findViewById(R.id.qrcode_view);
        mTickView = findViewById(R.id.tick_view);
        mCloseView = findViewById(R.id.close_view);
        mCloseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        String name = UserManager.getUserManager().getUser().data.name;
        mQrCodeView.setImageBitmap(Util.createQRCode(
            Utils.dip2px(mContext, 200),
            Utils.dip2px(mContext, 200),
            name));
        mTickView.setText(name + mContext.getString(R.string.personal_info));
    }
}

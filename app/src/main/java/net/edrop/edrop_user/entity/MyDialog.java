package net.edrop.edrop_user.entity;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by 李诗凡.
 * User: sifannnn
 * Date: 2019/12/10
 * Time: 20:27
 * TODO：自定义Dialog实现类
 */
public class MyDialog extends Dialog {
    //    style引用style样式
    public MyDialog(Context context, int width, int height, View layout, int style) {
        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }
}


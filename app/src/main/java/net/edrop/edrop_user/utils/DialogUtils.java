package net.edrop.edrop_user.utils;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.edrop.edrop_user.R;

public class DialogUtils {
    /**
     * 分享
     * @param activity activity
     * @param content 分享的内容
     * @param listener 监听
     */
    public static void showSharedDialog(Activity activity, final String content, final SharedListener listener){
        final Dialog bottomDialog = new Dialog(activity, R.style.BottomDialog);
        View contentView = LayoutInflater.from(activity).inflate(R.layout.dlg_layout_shared, null,false);
        LinearLayout llQQ = contentView.findViewById(R.id.dlg_shared_qq_friend);
        LinearLayout llQQZone = contentView.findViewById(R.id.dlg_shared_qq_friend_zone);
        TextView cancel = contentView.findViewById(R.id.dlg_shared_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.dismiss();
            }
        });
        llQQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.sharedToQQFriend(content);
                bottomDialog.dismiss();
            }
        });
        llQQZone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.sharedToQQZone(content);
                bottomDialog.dismiss();
            }
        });
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = activity.getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();

    }

    public interface SharedListener{

        void sharedToQQFriend(String content);

        void sharedToQQZone(String content);

    }


}

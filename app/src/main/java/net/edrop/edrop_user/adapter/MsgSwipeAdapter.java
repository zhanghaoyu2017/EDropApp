package net.edrop.edrop_user.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import net.edrop.edrop_user.R;
import net.edrop.edrop_user.activity.ChatViewActivity;
import net.edrop.edrop_user.entity.MsgItemBean;

import java.util.List;

/**
 * Created by mysterious
 * User: mysterious
 * Date: 2019/11/28
 * Time: 8:43
 */
public class MsgSwipeAdapter extends BaseSwipeAdapter implements View.OnClickListener  {
    private Context context;
    private List<MsgItemBean> list;
    private int item_swipe_msg;

    public MsgSwipeAdapter(Context context, int item_swipe_msg ,List<MsgItemBean> list) {
        this.item_swipe_msg=item_swipe_msg;
        this.context = context;
        this.list = list;
    }

    /**
     * 返回SwipeLayout的ID
     *
     * @param position
     * @return
     */
    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    /**
     * 绑定布局
     *
     * @param position
     * @param parent
     * @return
     */
    @Override
    public View generateView(final int position, ViewGroup parent) {
        View itemView = View.inflate(context, item_swipe_msg, null);
        SwipeLayout swipeLayout = (SwipeLayout) itemView.findViewById(getSwipeLayoutResourceId(position));

        // 设置滑动是否可用,默认为true
        swipeLayout.setSwipeEnabled(true);

        /**
         * 打开时调用：循环调用onStartOpen,onUpdate,onHandRelease,onUpdate,onOpen,
         * 关闭时调用：onStartClose,onUpdate,onHandRelease,onHandRelease,onUpdate,onClose
         */
        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {
//                Log.e(TAG, "onStartOpen: ");
            }

            @Override
            public void onOpen(SwipeLayout layout) {
//                Log.e(TAG, "onOpen: ");
            }

            @Override
            public void onStartClose(SwipeLayout layout) {
//                Log.e(TAG, "onStartClose: ");
            }

            @Override
            public void onClose(SwipeLayout layout) {
//                Log.e(TAG, "onClose: ");
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
//                Log.e(TAG, "onUpdate: ");
            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
//                Log.e(TAG, "onHandRelease: ");
            }
        });

        // 设置为true,在当前一条item(除侧滑以外部分)点击时,可收回侧滑出来部分,默认为false
        swipeLayout.setClickToClose(true);

        // SwipeLayout单击事件,可替代ListView的OnItemClickListener事件.
        swipeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatViewActivity.class);
                intent.putExtra("userId","zs");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(intent);
            }
        });

        return itemView;
    }

    /**
     * 绑定数据
     *
     * @param position
     * @param convertView
     */
    @Override
    public void fillValues(int position, View convertView) {
        ViewHolder viewHolder=null;
        if (viewHolder==null){
            viewHolder=new ViewHolder();
            viewHolder.nickName=(TextView) convertView.findViewById(R.id.tv_nickname);
            viewHolder.msg=(TextView) convertView.findViewById(R.id.tv_msg);
            viewHolder.talkDate=(TextView) convertView.findViewById(R.id.tv_talk_date);
            viewHolder.swipeTop=(TextView) convertView.findViewById(R.id.swipe_top);
            viewHolder.swipeDelete=(TextView) convertView.findViewById(R.id.swipe_delete);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.nickName.setText(list.get(position).getNickName());
        viewHolder.msg.setText(list.get(position).getMsg());
        viewHolder.talkDate.setText(list.get(position).getDate());

        viewHolder.swipeDelete.setOnClickListener(this);
        viewHolder.swipeTop.setOnClickListener(this);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public MsgItemBean getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.swipe_top:
                // 关闭所有打开的Swipe的item
                this.closeAllItems();
                Toast.makeText(context, "Swipe--Top", Toast.LENGTH_SHORT).show();
                break;
            case R.id.swipe_delete:
                this.closeAllItems();
                Toast.makeText(context, "Swipe--Delete", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private class ViewHolder{
        private TextView nickName;
        private TextView msg;
        private TextView talkDate;
        private TextView swipeDelete;
        private TextView swipeTop;
    }

}

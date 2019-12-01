package net.edrop.edrop_user;

import net.edrop.edrop_user.entity.ChatModel;
import net.edrop.edrop_user.entity.ItemModel;

import java.util.ArrayList;

public class TestData {
    public static ArrayList<ItemModel> getTestAdData() {
        ArrayList<ItemModel> models = new ArrayList<>();
        ChatModel model = new ChatModel();
        model.setContent("土豪？我们交个朋友吧！");
        model.setIcon("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575200975280&di=013453a7f9804cfa5ca5966f3d4ec05e&imgtype=0&src=http%3A%2F%2Fwww.uimaker.com%2Fuploads%2Fallimg%2F20140731%2F1406774235170879.png");
        models.add(new ItemModel(ItemModel.CHAT_A, model));
        ChatModel model2 = new ChatModel();
        model2.setContent("我是隔壁小王，你是谁？");
        model2.setIcon("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1575190893&di=186b0e34b0f1e51535794dedcbe0465e&src=http://b-ssl.duitang.com/uploads/item/201106/04/20110604152619_AaY5P.thumb.700_0.jpg");
        models.add(new ItemModel(ItemModel.CHAT_B, model2));
        ChatModel model3 = new ChatModel();
        model3.setContent("what？你真不知道我是谁吗？哭~");
        model3.setIcon("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575200975280&di=013453a7f9804cfa5ca5966f3d4ec05e&imgtype=0&src=http%3A%2F%2Fwww.uimaker.com%2Fuploads%2Fallimg%2F20140731%2F1406774235170879.png");
        models.add(new ItemModel(ItemModel.CHAT_A, model3));
        ChatModel model4 = new ChatModel();
        model4.setContent("大哥，别哭，我真不知道");
        model4.setIcon("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1575190893&di=186b0e34b0f1e51535794dedcbe0465e&src=http://b-ssl.duitang.com/uploads/item/201106/04/20110604152619_AaY5P.thumb.700_0.jpg");
        models.add(new ItemModel(ItemModel.CHAT_B, model4));
        ChatModel model5 = new ChatModel();
        model5.setContent("卧槽，你不知道你来撩妹？");
        model5.setIcon("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1575200975280&di=013453a7f9804cfa5ca5966f3d4ec05e&imgtype=0&src=http%3A%2F%2Fwww.uimaker.com%2Fuploads%2Fallimg%2F20140731%2F1406774235170879.png");
        models.add(new ItemModel(ItemModel.CHAT_A, model5));
        ChatModel model6 = new ChatModel();
        model6.setContent("你是妹子，卧槽，我怎么没看出来？");
        model6.setIcon("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1575190893&di=186b0e34b0f1e51535794dedcbe0465e&src=http://b-ssl.duitang.com/uploads/item/201106/04/20110604152619_AaY5P.thumb.700_0.jpg");
        models.add(new ItemModel(ItemModel.CHAT_B, model6));
        return models;
    }

}

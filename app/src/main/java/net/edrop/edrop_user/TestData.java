package net.edrop.edrop_user;

import net.edrop.edrop_user.entity.ChatModel;
import net.edrop.edrop_user.entity.ItemModel;

import java.util.ArrayList;

public class TestData {
    public static ArrayList<ItemModel> getTestAdData(String employeeHeadImg,String userHeadImg) {
        ArrayList<ItemModel> models = new ArrayList<>();
        ChatModel model = new ChatModel();
        model.setContent("土豪？我们交个朋友吧！");
        model.setIcon(userHeadImg);
        models.add(new ItemModel(ItemModel.CHAT_A, model));
        ChatModel model2 = new ChatModel();
        model2.setContent("我是隔壁小王，你是谁？");
        model2.setIcon(employeeHeadImg);
        models.add(new ItemModel(ItemModel.CHAT_B, model2));
        ChatModel model3 = new ChatModel();
        model3.setContent("what？你真不知道我是谁吗？哭~");
        model3.setIcon(userHeadImg);
        models.add(new ItemModel(ItemModel.CHAT_A, model3));
        ChatModel model4 = new ChatModel();
        model4.setContent("大哥，别哭，我真不知道");
        model4.setIcon(employeeHeadImg);
        models.add(new ItemModel(ItemModel.CHAT_B, model4));
        ChatModel model5 = new ChatModel();
        model5.setContent("卧槽，你不知道你来撩妹？");
        model5.setIcon(userHeadImg);
        models.add(new ItemModel(ItemModel.CHAT_A, model5));
        ChatModel model6 = new ChatModel();
        model6.setContent("你是妹子，卧槽，我怎么没看出来？");
        model6.setIcon(employeeHeadImg);
        models.add(new ItemModel(ItemModel.CHAT_B, model6));
        return models;
    }

}

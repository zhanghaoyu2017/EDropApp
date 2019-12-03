package net.edrop.edrop_user.entity;

import java.io.Serializable;

public class ItemModel implements Serializable {
    public static final int CHAT_A = 1001;
    public static final int CHAT_B = 1002;
    public int type;
    public Object object;

    public ItemModel(int type, Object object) {
        this.type = type;
        this.object = object;
    }

    @Override
    public String toString() {
        return "ItemModel{" +
                "type=" + type +
                ", object=" + object +
                '}';
    }
}

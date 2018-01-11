package net.bj.talker.factory.model.api.message;

import net.bj.talker.factory.model.card.MessageCard;
import net.bj.talker.factory.model.db.Message;
import net.bj.talker.factory.persistence.Account;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Neko-T4 on 2018/1/10.
 */

public class MsgCreateModel {

    //ID从客户端，UUID
    private String id;

    //内容
    private String content;

    //附件
    private String attach;

    //消息类型
    private int type = Message.TYPE_STR;

    //接收者，可为空
    private String receiverId;

    //接收者类型，群，人
    private int receiverType = Message.RECEIVER_TYPE_NONE;

    private MsgCreateModel() {
        //随机生成一个UUID
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAttach() {
        return attach;
    }

    public int getType() {
        return type;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public int getReceiverType() {
        return receiverType;
    }

    //TODO 当我们需要发送一个文件的时候，content刷新的问题


    private MessageCard card;
    /**
     * 构建Card
     * @return 一个Card
     */
    public MessageCard buildCard(){
        if (card == null){
            MessageCard card = new MessageCard();
            card.setId(id);
            card.setContent(content);
            card.setAttach(attach);
            card.setType(type);
            card.setSenderId(Account.getUserId());

            //如果是群
            if (receiverType == Message.RECEIVER_TYPE_GROUP){
                card.setGroupId(receiverId);
            }else {
                //给人发的
                card.setReceiverId(receiverId);
            }

            //通过当前model建立的Card就是一个初步状态的Card
            card.setStatus(Message.STATUS_CREATED);
            card.setCreateAt(new Date());
            this.card = card;
        }
        return this.card;
    }

    /**
     * 建造者模式，快速建立一个发送Model
     */
    public static class Builder{
        private MsgCreateModel model;

        public Builder(){
            this.model = new MsgCreateModel();
        }

        //设置接收者
        public Builder receiver(String receiverId, int receiverType){
            this.model.receiverId = receiverId;
            this.model.receiverType = receiverType;
            return this;
        }

        //设置内容
        public Builder content(String content, int type){
            this.model.content = content;
            this.model.type = type;
            return this;
        }

        //设置附件
        public Builder attach(String attach){
            this.model.attach = attach;
            return this;
        }

        public MsgCreateModel build(){
            return this.model;
        }
    }

    /**
     * 把一个Message消息，转换为一个创建状态的CreateModel
     * @param message Message
     * @return MsgCreateModel
     */
    public static MsgCreateModel buildWithMessage(Message message){
        MsgCreateModel model = new MsgCreateModel();
        model.id = message.getId();
        model.content = message.getContent();
        model.type = message.getType();
        model.attach = message.getAttach();

        if (message.getReceiver() != null){
            model.receiverId = message.getReceiver().getId();
            model.receiverType = Message.RECEIVER_TYPE_NONE;
        } else {
            model.receiverId = message.getGroup().getId();
            model.receiverType = Message.RECEIVER_TYPE_GROUP;
        }
        return model;
    }
}

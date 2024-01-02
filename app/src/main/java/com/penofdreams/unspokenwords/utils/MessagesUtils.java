package com.penofdreams.unspokenwords.utils;

import com.penofdreams.unspokenwords.model.MessageModel;
import com.penofdreams.unspokenwords.model.MessagesListModel;

import java.util.ArrayList;
import java.util.List;

public class MessagesUtils  {

    private final List<MessageModel> messagesListModelList;
    private final List<MessageModel> readMessageModelList;
    private final List<MessageModel> unreadMessageModelList;
    private MessageModel newMessage;
    public MessagesUtils(List<MessageModel> messagesListModelList) {
        this.messagesListModelList = messagesListModelList;
        this.readMessageModelList = new ArrayList<>();
        this.unreadMessageModelList = new ArrayList<>();
        setReadMessageList();
        setUnreadMessageList();

    }

    public void setReadMessageList(){
        for (MessageModel messageModel : messagesListModelList) {
            if (!messageModel.getIsPending()) {
                readMessageModelList.add(messageModel);
            }
        }
    }

    public void setUnreadMessageList(){
        for (MessageModel messageModel : messagesListModelList) {
            if (messageModel.getIsPending()) {
                unreadMessageModelList.add(messageModel);
            }
        }
    }


    public MessagesListModel getMessagesListModel() {
        return new MessagesListModel(unreadMessageModelList, readMessageModelList);
    }

}

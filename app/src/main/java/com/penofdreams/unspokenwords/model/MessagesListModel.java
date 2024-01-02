package com.penofdreams.unspokenwords.model;

import java.util.List;

public class MessagesListModel {

    private final List<MessageModel> unreadMessageModelList;
    private final List<MessageModel> readMessageModelList;

    public MessagesListModel(List<MessageModel> unreadMessageModelList, List<MessageModel> readMessageModelList) {
        this.unreadMessageModelList = unreadMessageModelList;
        this.readMessageModelList = readMessageModelList;
    }

    public List<MessageModel> getUnreadMessageModelList() {
        return unreadMessageModelList;
    }


    public List<MessageModel> getReadMessageModelList() {
        return readMessageModelList;
    }

}

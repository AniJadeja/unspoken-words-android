package com.penofdreams.unspokenwords.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DatabaseError;
import com.penofdreams.unspokenwords.R;
import com.penofdreams.unspokenwords.adapter.RecyclerViewAdapter;
import com.penofdreams.unspokenwords.databinding.ActivityMainBinding;
import com.penofdreams.unspokenwords.firebase.Database;
import com.penofdreams.unspokenwords.model.MessageModel;
import com.penofdreams.unspokenwords.model.MessagesListModel;
import com.penofdreams.unspokenwords.utils.MessagesUtils;
import com.penofdreams.unspokenwords.utils.PostNotification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Database.ValueEventListenerCallback {

    private ActivityMainBinding binding;
    private TextView category1, category2, newMessagesCount;
    private RecyclerView recyclerView;
    private Database database;
    private final List<MessageModel> messageModelList = new ArrayList<>();
    private List<MessageModel> readMessageModelList ;
    private List<MessageModel> unreadMessageModelList ;
    private int currentCategory = 1;
    private RecyclerViewAdapter adapter;
    private static final String TAG = "flow -> MainActivity";
    private PostNotification notification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
    }

    private void init() {
        recyclerView = binding.postsRecyclerView;
        category1 = binding.subject1View;
        category2 = binding.subject2View;
        newMessagesCount = binding.newMessagesCount;
        unreadMessageModelList = new ArrayList<>();
        readMessageModelList = new ArrayList<>();
        database = new Database();
        notification = new PostNotification(this,this);
        setupViews();

    }

    private void setupViews() {

        category1.setOnClickListener(this);
        category2.setOnClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter(messageModelList, this);
        recyclerView.setAdapter(adapter);
        database.setupMessageListener(this);

    }

    private void updateUI() {
        if (currentCategory == 1) {
            messageModelList.clear();
            messageModelList.addAll(unreadMessageModelList);
            adapter.notifyDataSetChanged();
        } else if (currentCategory == 2) {
            messageModelList.clear();
            messageModelList.addAll(readMessageModelList);
            adapter.notifyDataSetChanged();
        }
    }

    private void updateChangesInUI(MessageModel messageModel) {
        Log.d(TAG, "updateChangesInUI: unread list size : "+ unreadMessageModelList.size());
        unreadMessageModelList.add(messageModel);
        Log.d(TAG, "updateChangesInUI: unread list size : "+ unreadMessageModelList.size());
        messageModelList.clear();
        messageModelList.addAll(unreadMessageModelList);
        Log.d(TAG, "updateChangesInUI: messageModelList size : "+ messageModelList.size());
        adapter.notifyDataSetChanged();
    }
    @Override
    public void onClick(View v) {
        if (v == category1) {
            category1.setBackgroundResource(R.drawable.rounded_rect);
            category2.setBackgroundResource(0);
            currentCategory = 1;
            updateUI();
        } else if (v == category2) {
            category2.setBackgroundResource(R.drawable.rounded_rect);
            category1.setBackgroundResource(0);
            currentCategory = 2;
            updateUI();
        }
    }

    @Override
    public void onMessagesReceived(List<MessageModel> messageModelList) {
        MessagesUtils utils = new MessagesUtils(messageModelList);
        MessagesListModel messagesListModel = utils.getMessagesListModel();
        readMessageModelList.clear();
        unreadMessageModelList.clear();
        readMessageModelList = messagesListModel.getReadMessageModelList();
        unreadMessageModelList = messagesListModel.getUnreadMessageModelList();
        String newMessagesText = unreadMessageModelList.size() +" " + getString(R.string.slogan);
        newMessagesCount.setText(newMessagesText);
    }

    @Override
    public void onNewMessageReceived(MessageModel messageModel) {
        if (!messageModelList.contains(messageModel)) {
            Log.d(TAG, "onNewMessageReceived: NewMessageReceived ");
            updateChangesInUI(messageModel);
            notification.postNotification(messageModel.getName(), messageModel.getMessage());
        }

    }


    @Override
    public void onCancelled(DatabaseError error) {

    }
}
package com.penofdreams.unspokenwords.firebase;

import static com.penofdreams.unspokenwords.firebase.DatabaseReferences.MessageRef;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.penofdreams.unspokenwords.model.MessageModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Database  {

    private static final String TAG = "flow -> Database";
    private final List<MessageModel> messageModelList;
    public Database() {
        this.messageModelList = new ArrayList<>();}

    // Define a callback interface
    public interface ValueEventListenerCallback {
        void onMessagesReceived(List<MessageModel> messageModelList);
        void onNewMessageReceived(MessageModel messageModel);

        void onCancelled(DatabaseError error);
    }




    public void setupMessageListener(final ValueEventListenerCallback callback) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //create a list of messageModels from the dataSnapshot

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MessageModel messageModel = new MessageModel(
                            Objects.requireNonNull(snapshot.child("name").getValue()).toString(),
                            Objects.requireNonNull(snapshot.child("message").getValue()).toString(),
                            Objects.requireNonNull(snapshot.child("email").getValue()).toString(),
                            Boolean.parseBoolean(Objects.requireNonNull(snapshot.child("pending").getValue()).toString())
                    );

                    messageModelList.add(messageModel);
                }

                    Log.d(TAG, "onDataChange: passing to onMessagesReceived - MessageList size :" + messageModelList.size());

                  callback.onMessagesReceived(messageModelList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onCancelled(error);
            }
        };

        MessageRef.addValueEventListener(valueEventListener);

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                // New child added
                MessageModel messageModel = new MessageModel(
                        Objects.requireNonNull(snapshot.child("name").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("message").getValue()).toString(),
                        Objects.requireNonNull(snapshot.child("email").getValue()).toString(),
                        Boolean.parseBoolean(Objects.requireNonNull(snapshot.child("pending").getValue()).toString())
                );

                Log.d(TAG, "onChildAdded: passing to onNewMessageReceived");
                callback.onNewMessageReceived(messageModel);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        };

        MessageRef.addChildEventListener(childEventListener);



    }




}

package com.penofdreams.unspokenwords.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseReferences {
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static final DatabaseReference MessageRef = database.getReference("contact/messages");



}

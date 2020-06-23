package com.weterings.ikpmd;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class FireBase {

    private static FirebaseDatabase database;
    public static void connect(){
        database = FirebaseDatabase.getInstance();
    }

    public void readFromFireBase() {
            FirebaseDatabase database;
            DatabaseReference myRef;

            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("message");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String value = snapshot.getValue(String.class);
                    Log.d(TAG, "Value is:" + value);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }

    }

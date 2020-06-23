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

public class fireBase {
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("server/saving-data/fireblog");

    public static class Unit {

        public String unit_name;
        public int unit_atk;
        public int unit_hp;

        public Unit(String unit_name, int unit_hp) {

        }

        public Unit(String unit_Name, int unit_atk, int unit_hp) {

        }
    }
    public void addToFireBase() {
        DatabaseReference unitRef = ref.child("units");
        Map<String, Unit> units = new HashMap<>();
        units.put("smortis", new Unit("smortis", 3000));
    }
    public static class Post {
        public String unit_name;

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

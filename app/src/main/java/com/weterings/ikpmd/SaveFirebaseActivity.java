package com.weterings.ikpmd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.weterings.ikpmd.database.LocalDbHelper;
import com.weterings.ikpmd.database.LocalDbValues;
import com.weterings.ikpmd.models.User;

public class SaveFirebaseActivity extends AppCompatActivity {
    private EditText editSave;
    private EditText editPass;
    private LocalDbHelper localDbHelper = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);
    }
    public void backButton(View view) {
        finish();
    }

    public void postUserSave(View view) {
        editSave = findViewById(R.id.editGameIdSave);
        editPass = findViewById(R.id.editPassword);

        String path = "users/" + editSave.getText();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    User user = dataSnapshot.getValue(User.class);
                    if (user == null) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
                        String userId = String.valueOf(editSave.getText());
                        Cursor cursor1 = localDbHelper.query(LocalDbValues.ScoreTables.SCORETABLE, new String[]{"*"});
                        Cursor cursor2 = localDbHelper.query(LocalDbValues.ScoreMultiplierTable.MULTIPLIERTABLE, new String[]{"*"});
                        cursor1.moveToFirst();
                        cursor2.moveToFirst();
                        User newUser = new User(
                                userId,
                                String.valueOf(editPass.getText()),
                                Double.valueOf(cursor1.getString(cursor1.getColumnIndex("score"))),
                                Double.valueOf(cursor1.getString(cursor1.getColumnIndex("shekels"))),
                                Integer.valueOf(cursor2.getString(cursor2.getColumnIndex("multiplier1"))),
                                Integer.valueOf(cursor2.getString(cursor2.getColumnIndex("multiplier2"))),
                                Integer.valueOf(cursor2.getString(cursor2.getColumnIndex("multiplier3"))),
                                Integer.valueOf(cursor2.getString(cursor2.getColumnIndex("multiplier4"))));
                        ref.child(userId).setValue(newUser);
                        String confirmation = "Your shekels are safe!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(getApplicationContext(),confirmation,duration);
                        toast.show();
                        finish();
                    } else {
                        String rejection = "Somebody already hid their shekels there!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(getApplicationContext(),rejection,duration);
                        toast.show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    String notfound = "Couldn't safeguard your shekels";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getApplicationContext(),notfound,duration);
                    toast.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                String txt = "ERROR SaveActivity l82";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getApplicationContext(),txt,duration);
                toast.show();
            }
        });
    }

    public void goBack(View view) {
        finish();
    }
}

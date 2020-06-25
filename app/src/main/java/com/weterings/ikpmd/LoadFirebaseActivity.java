package com.weterings.ikpmd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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
public class LoadFirebaseActivity extends AppCompatActivity {
    private LocalDbHelper localDbHelper;
    EditText editPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        localDbHelper.getDbHelper(this);
    }
    public void backButton(View view) {
        finish();
    }

    public void getUserData(View view){
        EditText saveId =findViewById(R.id.editGameIdSave);
        editPass =findViewById(R.id.editPassword);

        String path = "users/" + saveId.getText();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    User user = snapshot.getValue(User.class);
                    if (user.getKey().equals(String.valueOf(editPass.getText()))) {
                        updateUserScore(user);
                        updateUserMultipliers(user);
                        String confirmation = "The shekels you saved are back!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(getApplicationContext(), confirmation, duration);
                        toast.show();
                        finish();
                    } else {
                        String rejection = "Seems like that password has no shekels!";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(getApplicationContext(), rejection, duration);
                        toast.show();
                    }

                } catch (Exception e) {
                    String notFound = "Seems like there are no shekels to be found";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getApplicationContext(), notFound, duration);
                    toast.show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                String txt = "LoadFirebase onCancelled";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getApplicationContext(), txt, duration);
                toast.show();
            }
            });
        }
            private void updateUserMultipliers(User user) {
                String selection = "_id = ?";
                String[] selectionArgs = {"1"};
                ContentValues values = new ContentValues();
                values.put(LocalDbValues.ScoreMultiplierColumn.MULTIPLIER1, user.getMultiplier1());
                values.put(LocalDbValues.ScoreMultiplierColumn.MULTIPLIER2, user.getMultiplier2());
                values.put(LocalDbValues.ScoreMultiplierColumn.MULTIPLIER3, user.getMultiplier3());
                values.put(LocalDbValues.ScoreMultiplierColumn.MULTIPLIER4, user.getMultiplier4());
                try {
                    localDbHelper.updateTable(LocalDbValues.ScoreMultiplierTable.MULTIPLIERTABLE, values, selection, selectionArgs);
                } catch (Exception e) {
                    String txt = "Load updateMultiplier";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getApplicationContext(), txt, duration);
                    toast.show();
                }
            }


            public void updateUserScore(User user) {
                String selection = "_id = ?";
                String[] selectionArgs = {"1"};
                ContentValues values = new ContentValues();
                values.put(LocalDbValues.TotalScoreColumn.SCORE, String.valueOf(user.getScore()));
                values.put(LocalDbValues.TotalScoreColumn.SHEKELS, String.valueOf(user.getShekels()));
                try {
                    localDbHelper.updateTable(LocalDbValues.TotalScoreTables.SCORETABLE, values, selection, selectionArgs);
                } catch (Exception e) {
                    String txt = "Load updateScore";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(getApplicationContext(), txt, duration);
                    toast.show();
                }
            }
        }

package com.weterings.ikpmd;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.weterings.ikpmd.database.LocalDbHelper;
import com.weterings.ikpmd.database.LocalDbValues;

public class MainActivity extends AppCompatActivity {
    private double shekels;
    private double score;
    private int shekelMultiplier1;
    private int shekelMultiplier2;
    private int shekelMultiplier3;
    private int shekelMultiplier4;
    private boolean run = true;
    Activity localActivity;
    TextView ScoreView;
    TextView ShekelsView;
    TextView ShekelClickView;
    TextView ShekelSecondView;
    private LocalDbHelper localDbHelper = null;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        localActivity = this;
        try{
            localDbHelper = LocalDbHelper.getDbHelper(this);
            Cursor cursor1 = localDbHelper.query(LocalDbValues.ScoreTables.SCORETABLE, new String[]{"*"});
            Cursor cursor2 = localDbHelper.query(LocalDbValues.ScoreMultiplierTable.MULTIPLIERTABLE, new String[]{"*"});
            if(!(cursor1.getCount() > 0)) {
                ContentValues scoreValues = new ContentValues();
                scoreValues.put(LocalDbValues.ScoreColumn.SHEKELS, 0);
                scoreValues.put(LocalDbValues.ScoreColumn.SCORE, 0);
                localDbHelper.insertIntoTable(LocalDbValues.ScoreTables.SCORETABLE,    null, scoreValues);
                cursor1 =localDbHelper.query(LocalDbValues.ScoreTables.SCORETABLE, new String[]{"*"});
                Log.e("whore", String.valueOf(cursor1.getCount()));
            }
            if(!(cursor2.getCount() > 0)) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(LocalDbValues.ScoreMultiplierColumn.MULTIPLIER1, 0);
                contentValues.put(LocalDbValues.ScoreMultiplierColumn.MULTIPLIER2, 0);
                contentValues.put(LocalDbValues.ScoreMultiplierColumn.MULTIPLIER3, 0);
                contentValues.put(LocalDbValues.ScoreMultiplierColumn.MULTIPLIER4, 0);

                localDbHelper.insertIntoTable(LocalDbValues.ScoreMultiplierTable.MULTIPLIERTABLE, null, contentValues);
                cursor2 =localDbHelper.query(LocalDbValues.ScoreMultiplierTable.MULTIPLIERTABLE, new String[]{"*"});
            }
            if (cursor1.getCount() > 0 || cursor2.getCount() > 0) {
                cursor1.moveToFirst();
                score = Double.valueOf(cursor1.getString(cursor1.getColumnIndex("score")));
                ScoreView = findViewById(R.id.score);
                ScoreView.setText("Total score: " + score);
                ShekelsView = findViewById(R.id.shekel);
                ShekelClickView = findViewById(R.id.shekelClick);
                ShekelSecondView = findViewById(R.id.shekelSecond);
                Runnable runnable1 = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (run = true) {
                                try {
                                    Cursor cursor1 = localDbHelper.query(LocalDbValues.ScoreTables.SCORETABLE, new String[]{"*"});
                                    Cursor cursor2 = localDbHelper.query(LocalDbValues.ScoreMultiplierTable.MULTIPLIERTABLE, new String[]{"*"});
                                    cursor1.moveToFirst();
                                    cursor2.moveToFirst();
                                    score = Double.valueOf(cursor1.getString(cursor1.getColumnIndex("score")));
                                    shekels = Double.valueOf(cursor1.getString(cursor1.getColumnIndex("shekels")));
                                    shekelMultiplier1 = Integer.valueOf(cursor2.getString(cursor2.getColumnIndex("multiplier1")));
                                    shekelMultiplier2 = Integer.valueOf(cursor2.getString(cursor2.getColumnIndex("multiplier2")));
                                    shekelMultiplier3 = Integer.valueOf(cursor2.getString(cursor2.getColumnIndex("multiplier3")));
                                    shekelMultiplier4 = Integer.valueOf(cursor2.getString(cursor2.getColumnIndex("multiplier4")));
                                    localActivity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ScoreView.setText("Total score: " + (double) Math.round(score * 100) / 100);
                                            ShekelsView.setText("Shekels: " + (double) Math.round(shekels * 100) / 100);
                                            ShekelSecondView.setText("Shekels per Second: " + (double) Math.round((shekelMultiplier1 * .05) * (1 + shekelMultiplier2 * .15) * (1 + shekelMultiplier3 * .2) * (1 + shekelMultiplier4 * .3) * 100) / 100);
                                            ShekelClickView.setText("Shekels per Click: " + (double) Math.round((1 + shekelMultiplier1 * .05) * (1 + shekelMultiplier2 * .15) * (1 + shekelMultiplier3 * .2) * (1 + shekelMultiplier4 * .3) * 100) / 100);
                                        }
                                    });
                                    Thread.sleep(250);
                                } catch (Exception e) {
                                    String txt = "Main regel 94";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(getApplicationContext(), txt, duration);
                                    toast.show();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                Runnable runnable2 = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (run = true) {
                                try {
                                    double addition = .5 * (shekelMultiplier1 * .05) * (1 + shekelMultiplier2 * .15) * (1 + shekelMultiplier3 * .2) * (1 + shekelMultiplier4 * .3);
                                    score += addition;
                                    shekels += addition;
                                    String selection = "_id = ?";
                                    String[] selectionArgs = {"1"};
                                    ContentValues values = new ContentValues();
                                    values.put(LocalDbValues.ScoreColumn.SCORE, String.valueOf(score));
                                    values.put(LocalDbValues.ScoreColumn.SHEKELS, String.valueOf(shekels));
                                    try {
                                        localDbHelper.updateTable(LocalDbValues.ScoreTables.SCORETABLE, values, selection, selectionArgs);
                                    } catch (Exception e) {
                                        String txt = "ERROR MainActivity l125";
                                        int duration = Toast.LENGTH_SHORT;
                                        Toast toast = Toast.makeText(getApplicationContext(), txt, duration);
                                        toast.show();
                                    }
                                    Thread.sleep(500);
                                } catch (Exception e) {
                                    String txt = "ERROR MainActivity l132";
                                    int duration = Toast.LENGTH_SHORT;
                                    Toast toast = Toast.makeText(getApplicationContext(), txt, duration);
                                    toast.show();
                                }
                            }
                        } catch (Exception e) {
                            String txt = "ERROR MainActivity l139";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(getApplicationContext(), txt, duration);
                            toast.show();
                        }
                    }
                };
                Thread thread1 = new Thread(runnable1);
                Thread thread2 = new Thread(runnable2);
                thread1.start();
                thread2.start();
            } else {
                String txt = "Main Regel 152";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getApplicationContext(),txt,duration);
                toast.show();
            }
        } catch (Exception e) {
            Log.d("stacktrace 158:", e.toString());
            String txt = "Main Regel 158";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(),txt,duration);
            toast.show();
        }
    }
    @SuppressLint("SetTextI18n")
    public void ClickerHandler(View view) {
        ContentValues values = new ContentValues();
        String selection = "_id = ?";
        String[] selectionArgs = {"1"};
        double addition = (1 + (shekelMultiplier1 * .05)) * (1 + shekelMultiplier2 * .15) * (1 + shekelMultiplier3 * .2) * (1 + shekelMultiplier4 * .3);
        score += addition;
        shekels += addition;
        values.put(LocalDbValues.ScoreColumn.SCORE, String.valueOf(score));
        values.put(LocalDbValues.ScoreColumn.SHEKELS, String.valueOf(shekels));
        try {
            localDbHelper.updateTable(LocalDbValues.ScoreTables.SCORETABLE, values, selection, selectionArgs);
        } catch (Exception e) {
            Log.d("stacktrace:", e.toString());
            String txt = "ERROR MainActivity l177";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(),txt,duration);
            toast.show();
        }
        TextView scoreView = findViewById(R.id.score);
        scoreView.setText("Total score: " + (double) Math.round(score * 100) / 100);
        TextView shekelsView = findViewById(R.id.shekel);
        shekelsView.setText("Shekels: " + (double) Math.round(shekels * 100) / 100);
    }

    public void Load(View view) {
        Intent intent = new Intent(this, LoadFirebaseActivity.class);
        startActivity(intent);
    }
    public void Shop(View view) {
        Intent intent = new Intent(MainActivity.this, ShopActivity.class);
        startActivity(intent);

        }
    public void Save(View view) {
        Intent intent = new Intent(this, SaveFirebaseActivity.class);
        startActivity(intent);
    }



}

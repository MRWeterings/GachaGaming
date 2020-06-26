package com.weterings.ikpmd;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.weterings.ikpmd.database.LocalDbHelper;
import com.weterings.ikpmd.database.LocalDbValues;

public class ShopActivity extends AppCompatActivity {
    private double shekel;
    private int multiplier1;
    private int multiplier2;
    private int multiplier3;
    private int multiplier4;
    private boolean run = true;
    Activity localActivity;
    TextView ShekelView;

    private LocalDbHelper localDbHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        localActivity = this;
        try {
            localDbHelper = LocalDbHelper.getDbHelper(this);
            Cursor cursor1 = localDbHelper.query(LocalDbValues.ScoreTables.SCORETABLE, new String[]{"*"});
            Cursor cursor2 = localDbHelper.query(LocalDbValues.ScoreMultiplierTable.MULTIPLIERTABLE, new String[]{"*"});
            cursor1.moveToFirst();
            cursor2.moveToFirst();
            if (cursor1.getCount() > 0 || cursor2.getCount() > 0) {

                shekel = Double.valueOf(cursor1.getString(cursor1.getColumnIndex("shekels")));
                multiplier1 = Integer.valueOf(cursor2.getString(cursor2.getColumnIndex("multiplier1")));
                multiplier2 = Integer.valueOf(cursor2.getString(cursor2.getColumnIndex("multiplier2")));
                multiplier3 = Integer.valueOf(cursor2.getString(cursor2.getColumnIndex("multiplier3")));
                multiplier4 = Integer.valueOf(cursor2.getString(cursor2.getColumnIndex("multiplier4")));

                ShekelView = findViewById(R.id.shekel);
                ShekelView.setText("Shekels: " + (double) Math.round(shekel * 100) / 100);

                TextView textmultiplier1 = findViewById(R.id.multiplier1counter);
                textmultiplier1.setText(String.valueOf(multiplier1));
                Button btnmultiplier1 = findViewById(R.id.multiplier1);
                btnmultiplier1.setText("Cost: " + (double) Math.round(25 * ((1 + multiplier1) * .4) * 100) / 100);

                TextView textmultiplier2 = findViewById(R.id.multiplier2counter);
                textmultiplier2.setText(String.valueOf(multiplier2));
                Button btnmultiplier2 = findViewById(R.id.multiplier2);
                btnmultiplier2.setText("Cost: " + (double) Math.round(50 * ((1 + multiplier2) * .4) * 100) / 100);

                TextView textmultiplier3 = findViewById(R.id.multiplier3counter);
                textmultiplier3.setText(String.valueOf(multiplier3));
                Button btnmultiplier3 = findViewById(R.id.multiplier3);
                btnmultiplier3.setText("Cost: " + (double) Math.round(100 * ((1 + multiplier3) * .4) * 100) / 100);

                TextView textmultiplier4 = findViewById(R.id.multiplier4counter);
                textmultiplier4.setText(String.valueOf(multiplier4));
                Button btnmultiplier4 = findViewById(R.id.multiplier4);
                btnmultiplier4.setText("Cost: " + (double) Math.round(200 * ((1 + multiplier4) * .4) * 100) / 100);
                Runnable runnable1 = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (run = true) {
                                Cursor cursor1 = localDbHelper.query(LocalDbValues.ScoreTables.SCORETABLE, new String[]{"*"});
                                cursor1.moveToFirst();
                                shekel = Double.valueOf(cursor1.getString(cursor1.getColumnIndex("shekels")));
                                localActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ShekelView.setText("Shekels: " + (double) Math.round(shekel * 100) / 100);
                                    }
                                });
                                Thread.sleep(250);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                Thread thread1 = new Thread(runnable1);
                thread1.start();
            } else {
                String txt = "Shop regel 98";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getApplicationContext(),txt,duration);
                toast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            String txt = "Shop regel 104";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(),txt,duration);
            toast.show();
        }
    }

    public void goBack(View view) {
        updatemultipliers();
        finish();
    }

    public void multiplier1(View view) {
        if (shekel >= ((1 + multiplier1) * .4) * 25) {
            removeShekels(((1 + multiplier1) * .4) * 25);
            multiplier1 += 1;
            ContentValues values = new ContentValues();
            String selection = "_id = ?";
            String[] selectionArgs = {"1"};
            values.put(LocalDbValues.ScoreMultiplierColumn.MULTIPLIER1, String.valueOf(multiplier1));
            try {
                localDbHelper.updateTable(LocalDbValues.ScoreMultiplierTable.MULTIPLIERTABLE, values, selection, selectionArgs);
            } catch (Exception e) {
                String txt = "ERROR StoreActivity l123";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getApplicationContext(),txt,duration);
                toast.show();
            }
            TextView tvmultiplier = findViewById(R.id.multiplier1counter);
            tvmultiplier.setText(String.valueOf(multiplier1));
            Button btnmultiplier = findViewById(R.id.multiplier1);
            btnmultiplier.setText("Cost: " + (double) Math.round(25 * ((1 + multiplier1) * .4) * 100) / 100);
        } else {
            String txt = "Not enough shekels!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(),txt,duration);
            toast.show();
        }
    }

    public void multiplier2(View view) {
        if (shekel >= ((1 + multiplier2) * .4) * 50) {
            removeShekels(((1 + multiplier2) * .4) * 50);
            multiplier2 += 1;
            ContentValues values = new ContentValues();
            String selection = "_id = ?";
            String[] selectionArgs = {"1"};
            values.put(LocalDbValues.ScoreMultiplierColumn.MULTIPLIER2, String.valueOf(multiplier2));
            try {
                localDbHelper.updateTable(LocalDbValues.ScoreMultiplierTable.MULTIPLIERTABLE, values, selection, selectionArgs);
            } catch (Exception e) {
                String txt = "ERROR StoreActivity l158";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getApplicationContext(),txt,duration);
                toast.show();
            }
            TextView tvmultiplier = findViewById(R.id.multiplier2counter);
            tvmultiplier.setText(String.valueOf(multiplier2));
            Button btnmultiplier = findViewById(R.id.multiplier2);
            btnmultiplier.setText("Cost: " + (double) Math.round(50 * ((1 + multiplier2) * .4) * 100) / 100);
        } else {
            String txt = "Not enough shekels!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(),txt,duration);
            toast.show();
        }
    }

    public void multiplier3(View view) {
        if (shekel >= ((1 + multiplier3) * .4) * 100) {
            removeShekels(((1 + multiplier3) * .4) * 100);
            multiplier3 += 1;
            ContentValues values = new ContentValues();
            String selection = "_id = ?";
            String[] selectionArgs = {"1"};
            values.put(LocalDbValues.ScoreMultiplierColumn.MULTIPLIER3, String.valueOf(multiplier3));
            try {
                localDbHelper.updateTable(LocalDbValues.ScoreMultiplierTable.MULTIPLIERTABLE, values, selection, selectionArgs);
            } catch (Exception e) {
                String txt = "ERROR StoreActivity l187";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getApplicationContext(),txt,duration);
                toast.show();
            }
            TextView tvmultiplier = findViewById(R.id.multiplier3counter);
            tvmultiplier.setText(String.valueOf(multiplier3));
            Button btnmultiplier = findViewById(R.id.multiplier3);
            btnmultiplier.setText("Cost: " + (double) Math.round(100 * ((1 + multiplier3) * .4) * 100) / 100);
        } else {
            String txt = "Not enough shekels!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(),txt,duration);
            toast.show();
        }
    }

    public void multiplier4(View view) {
        if (shekel >= ((1 + multiplier4) * .4) * 200) {
            removeShekels(((1 + multiplier4) * .4) * 200);
            multiplier4 += 1;
            ContentValues values = new ContentValues();
            String selection = "_id = ?";
            String[] selectionArgs = {"1"};
            values.put(LocalDbValues.ScoreMultiplierColumn.MULTIPLIER4, String.valueOf(multiplier4));
            try {
                localDbHelper.updateTable(LocalDbValues.ScoreMultiplierTable.MULTIPLIERTABLE, values, selection, selectionArgs);
            } catch (Exception e) {
                String txt = "ERROR StoreActivity l216";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(getApplicationContext(),txt,duration);
                toast.show();
            }
            TextView tvmultiplier = findViewById(R.id.multiplier4counter);
            tvmultiplier.setText(String.valueOf(multiplier4));
            Button btnmultiplier = findViewById(R.id.multiplier4);
            btnmultiplier.setText("Cost: " + (double) Math.round(200 * ((1 + multiplier4) * .4) * 100) / 100);
        } else {
            String txt = "Not enough shekels!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(),txt,duration);
            toast.show();
        }
    }

    public void updatemultipliers() {
        ContentValues values = new ContentValues();
        String selection = "_id = ?";
        String[] selectionArgs = {"1"};
        values.put(LocalDbValues.ScoreMultiplierColumn.MULTIPLIER1, String.valueOf(multiplier1));
        values.put(LocalDbValues.ScoreMultiplierColumn.MULTIPLIER2, String.valueOf(multiplier2));
        values.put(LocalDbValues.ScoreMultiplierColumn.MULTIPLIER3, String.valueOf(multiplier3));
        values.put(LocalDbValues.ScoreMultiplierColumn.MULTIPLIER4, String.valueOf(multiplier4));
        try {
            localDbHelper.updateTable(LocalDbValues.ScoreMultiplierTable.MULTIPLIERTABLE, values, selection, selectionArgs);
        } catch (Exception e) {
            String txt = "ERROR StoreActivity l259";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(),txt,duration);
            toast.show();
        }
    }

    public void removeShekels(double amount) {
        TextView shekelText = findViewById(R.id.shekel);
        shekelText.setText("Shekels: " + (double) Math.round(shekel * 100) / 100);
        ContentValues values = new ContentValues();
        String selection = "_id = ?";
        String[] selectionArgs = {"1"};
        values.put(LocalDbValues.ScoreColumn.SHEKELS, String.valueOf(shekel - amount));
        try {
            localDbHelper.updateTable(LocalDbValues.ScoreTables.SCORETABLE, values, selection, selectionArgs);
        } catch (Exception e) {
            String txt = "ERROR StoreActivity l253";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(getApplicationContext(),txt,duration);
            toast.show();
        }
    }
}

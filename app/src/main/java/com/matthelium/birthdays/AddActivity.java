package com.matthelium.birthdays;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.matthelium.birthdays.db.MyDbManager;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {

    private MyDbManager myDbManager;
    private EditText name;
    private EditText presents;

    private Calendar c;

    private int mYear;
    private int mMonth;
    private int mDay;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        name = findViewById(R.id.editNameText);
        presents = findViewById(R.id.editPresents);
        myDbManager = new MyDbManager(this);
    }

    @Override
    protected void onResume(){
        super.onResume();
        myDbManager.openDb();
    }

    public void onClickDate(View v) {
        c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        openDateDialog();
    }

    private void openDateDialog(){
        DatePickerDialog dateDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                Log.d("My tag", c.getTime().toString());
            }
        }, mYear, mMonth, mDay);
        dateDialog.show();
    }

    public void onClickOK(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Log.d("GGGGGGGG", name.getText().toString());
        Log.d("GFWIF", name.toString());
        if (name.getText() != null && presents.getText() != null && c !=null) {
            myDbManager.insertToDb(name.getText().toString(), c, String.valueOf(presents.getText()));
        }
    }

}
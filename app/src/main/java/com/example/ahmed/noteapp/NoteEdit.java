package com.example.ahmed.noteapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class NoteEdit extends AppCompatActivity {


    EditText title, body;
    Button addButton;
    Date calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_edit);

        title = (EditText) findViewById(R.id.note_title_edit);
        body = (EditText) findViewById(R.id.note_body_edit);
        addButton = (Button) findViewById(R.id.add_button);

        calendar = Calendar.getInstance().getTime();

        SharedPreferences sharedPreferences = getSharedPreferences("edit", MODE_PRIVATE);

        title.setText(sharedPreferences.getString("title", ""));
        body.setText(sharedPreferences.getString("body", ""));

        if (!title.getText().toString().equals("") || !body.getText().toString().equals("")) {
            addButton.setText("Save");
        }

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataBase db = new DataBase(NoteEdit.this);
                SQLiteDatabase sql = db.getWritableDatabase();

                ContentValues cv = new ContentValues();
                cv.put(DataBase.COLUMN_TITLE, title.getText().toString());
                cv.put(DataBase.COLUMN_BODY, body.getText().toString());
                cv.put(DataBase.COLUMN_DATE, calendar.toString());
                sql.insert(DataBase.TABLE_NAME, null, cv);

                sql.close();

                if(addButton.getText().toString() == "Save")
                    Toast.makeText(NoteEdit.this, "Note Successfully Saved", Toast.LENGTH_SHORT).show();

                else
                    Toast.makeText(NoteEdit.this, "Note Successfully Created", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(NoteEdit.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

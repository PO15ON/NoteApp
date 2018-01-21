package com.example.ahmed.noteapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Note extends AppCompatActivity {

    private static final String TAG = "Ahmed";
    TextView title, date, body;
    Button editButton, deleteButton, shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        title = (TextView) findViewById(R.id.note_title);
        date = (TextView) findViewById(R.id.date);
        body = (TextView) findViewById(R.id.note_body);

        editButton = (Button) findViewById(R.id.edit_button);
        deleteButton = (Button) findViewById(R.id.delete_button);
        shareButton = (Button) findViewById(R.id.share_button);

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("edit", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("title", title.getText().toString());
                editor.putString("body", body.getText().toString());
                editor.putString("date", date.getText().toString());
                editor.commit();

                Intent intent = new Intent(Note.this, NoteEdit.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBase db = new DataBase(Note.this);
                SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
                sqLiteDatabase.delete(DataBase.TABLE_NAME, DataBase.COLUMN_TITLE + "=?", new String[]{title.getText().toString()});

                Intent intent = new Intent(Note.this, MainActivity.class);
                startActivity(intent);
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = body.getText().toString();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("note", MODE_PRIVATE);

        String titleSP = sharedPreferences.getString("title", "");
        DataBase db = new DataBase(this);
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(DataBase.TABLE_NAME, null, DataBase.COLUMN_TITLE + "=?", new String[]{titleSP}, null, null, null);
        if(cursor != null && cursor.moveToFirst()){
            String bodySP = cursor.getString(cursor.getColumnIndex(DataBase.COLUMN_BODY));
            String dateSP = cursor.getString(cursor.getColumnIndex(DataBase.COLUMN_DATE));
            title.setText(titleSP);
            body.setText(bodySP);
            date.setText(dateSP);

        }


    }
}

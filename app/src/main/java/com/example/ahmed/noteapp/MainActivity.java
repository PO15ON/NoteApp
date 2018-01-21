package com.example.ahmed.noteapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements NoteAdapter.NoteAdapterAdapterOnClickHandler{

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    public NoteAdapter noteAdapter;
    TextView errorText;
    String[] titles, dates;
    public static final String TAG = "Ahmed";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        errorText = (TextView) findViewById(R.id.error_message);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        noteAdapter = new NoteAdapter(this);
        recyclerView.setAdapter(noteAdapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("edit", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("title", "");
                editor.putString("body", "");
                editor.commit();

                Intent intent = new Intent(MainActivity.this, NoteEdit.class);
                startActivity(intent);

            }
        });


        getSql();

        loadData();


    }


    public void loadData(){

            noteAdapter.setTitles(titles);
            noteAdapter.setDates(dates);
    }

    public Cursor getCursor() {
        DataBase db = new DataBase(this);
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        return sqLiteDatabase.query(DataBase.TABLE_NAME, null, null, null, null, null, null);
    }

    public void getSql(){
        Cursor cursor = getCursor();
        titles = new String[cursor.getCount()];
        dates = new String[cursor.getCount()];

        int i = 0, j = 0;
        if(cursor.moveToFirst()){
            do {
                String title = cursor.getString(cursor.getColumnIndex(DataBase.COLUMN_TITLE));
                String date = cursor.getString(cursor.getColumnIndex(DataBase.COLUMN_DATE));

                titles[i++] = title;
                dates[j++] = date;

            } while (cursor.moveToNext());

        }
    }

    @Override
    public void onClick(String title) {

        SharedPreferences sharedPreferences = getSharedPreferences("note", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("title", title);

        editor.commit();

        Intent intent = new Intent(MainActivity.this, Note.class);
        startActivity(intent);
    }
}

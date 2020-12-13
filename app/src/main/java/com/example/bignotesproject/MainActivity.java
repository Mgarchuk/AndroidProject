package com.example.bignotesproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ArrayList<String> notes = new ArrayList<>();

    public static int selectedItem;
    public static boolean is_addition = true;

    EditText editText;
    DBHelper dbHelper;

    Button button_add;
    Button button_show_tasks;


    public static String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        if(EnterText.firstly && Safety.firstly && TasksActivity.firstly){
            Cursor cursor_password = database.query(DBHelper.TABLE_PASSWORD, null, null,
                    null, null, null, null);

            if(cursor_password.moveToFirst()) {
                int passwordIndex = cursor_password.getColumnIndex(DBHelper.KEY_PASSWORD);
                password = (cursor_password.getString(passwordIndex));
                Intent intent = new Intent(this, EnterPassword.class);
                startActivity(intent);
            }

            cursor_password.close();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_add = (Button) findViewById(R.id.addition);
        button_show_tasks = (Button) findViewById(R.id.button_tasks);

        button_add.setOnClickListener(this);
        button_show_tasks.setOnClickListener(this);

        notes.clear();

        editText = (EditText) findViewById(R.id.search_edit);

        dbHelper = new DBHelper(this);
        Cursor cursor = database.query(DBHelper.TABLE_NOTES, null, null,
                null, null, null, null);

        if(cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int textIndex = cursor.getColumnIndex(DBHelper.KEY_TEXT);
            int picIndex = cursor.getColumnIndex(DBHelper.KEY_PIC);
            do {
                notes.add(cursor.getString(textIndex));
            }while (cursor.moveToNext());
        }

        cursor.close();
        dbHelper.close();

        create_adapter(notes);
    }

    public void showNote() {
        Intent intent = new Intent(MainActivity.this, EnterText.class);
        startActivityForResult(intent, 1);
    }

    public void create_adapter(ArrayList<String> array) {

        GridView notesList = (GridView) findViewById(R.id.grid_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.set_notes, array);
        notesList.setAdapter(adapter);

        notesList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                selectedItem = position;
                is_addition = false;
                showNote();
            }
        });

        notesList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, final int position, long id)
            {
                LayoutInflater li = LayoutInflater.from(MainActivity.this);
                View promptsView = li.inflate(R.layout.delete_dialog, null);

                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                mDialogBuilder.setView(promptsView);
                // final EditText userInput = promptsView.findViewById(R.id.input_text);

                mDialogBuilder.setCancelable(false)
                        .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteNote(position);
                            }
                        });

                mDialogBuilder.create().show();
                return true;
            }
        });
    }

    public void deleteNote(int pos) {
        DBHelper dataBaseHelper = new DBHelper(MainActivity.this);

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_NOTES, null, null,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int textIndex = cursor.getColumnIndex(DBHelper.KEY_TEXT);
            int num = 0;
            do {
                int id_index = cursor.getInt(idIndex);
                String text_index = cursor.getString(textIndex);
                if (num == (pos)) {
                    database.delete(DBHelper.TABLE_NOTES, DBHelper.KEY_ID + "= ?",
                            new String[] {Integer.toString(id_index)});
                    notes.remove(num);
                    create_adapter(notes);
                    break;
                }
                num++;
            } while (cursor.moveToNext());
        }

        cursor.close();
        dataBaseHelper.close();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_clear:
                create_adapter(notes);
                editText.setText("");
                break;
            case R.id.button_search:
                String text = editText.getText().toString();

                dbHelper = new DBHelper(this);
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                Cursor cursor = database.query(DBHelper.TABLE_NOTES, null, null,
                        null, null, null, null);

                ArrayList<String> new_array = new ArrayList<>();

                if(cursor.moveToFirst()) {
                    int textIndex = cursor.getColumnIndex(DBHelper.KEY_TEXT);
                    do {
                        String str = cursor.getString(textIndex);
                        if(str.contains(text))
                        {
                            new_array.add(str);
                        }

                    }while (cursor.moveToNext());
                }

                cursor.close();
                dbHelper.close();

                create_adapter(new_array);
                break;
            case R.id.addition:
                Intent intent_5 = new Intent(MainActivity.this, EnterText.class);
                startActivityForResult(intent_5, 1);
                is_addition = true;
                break;
            case R.id.button_tasks:
                Intent intent_4 = new Intent(this, TasksActivity.class);
                startActivity(intent_4);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.safety :
                Intent intent_1 = new Intent(this, Safety.class);
                startActivity(intent_1);
                return true;
            case R.id.internet:
                WebView browser=(WebView)findViewById(R.id.webBrowser);
                browser.getSettings().setJavaScriptEnabled(true);
                browser.loadUrl("http://google.com");
                return true;
            case R.id.delete_all:
                dbHelper = new DBHelper(this);
                SQLiteDatabase database = dbHelper.getWritableDatabase();
                database.delete(DBHelper.TABLE_NOTES, null, null);
                dbHelper.close();
                notes.clear();
                create_adapter(notes);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                dbHelper = new DBHelper(this);
                SQLiteDatabase database_ = dbHelper.getWritableDatabase();
                Cursor cursor = database_.query(DBHelper.TABLE_NOTES, null, null,
                        null, null, null, null);

                notes.clear();
                if(cursor.moveToFirst()) {
                    int textIndex = cursor.getColumnIndex(DBHelper.KEY_TEXT);
                    do {
                        notes.add(cursor.getString(textIndex));
                    }while (cursor.moveToNext());
                }

                cursor.close();
                dbHelper.close();

                create_adapter(notes);
            }
        }
    }


}
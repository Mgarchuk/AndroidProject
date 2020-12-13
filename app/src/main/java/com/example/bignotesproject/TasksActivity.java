package com.example.bignotesproject;

import android.annotation.SuppressLint;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class TasksActivity extends AppCompatActivity  implements View.OnClickListener {

    Button button_ok;
    Button show_notes;
    Button search_task;
    EditText edit_search;
    EditText edit_task;

    DBHelper dbHelper;
    boolean click;

    public static boolean firstly = true;
    ArrayList<String> tasks = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tasks_activity);
        firstly = false;

        button_ok = (Button) findViewById(R.id.button_ok);
        search_task = (Button) findViewById(R.id.button_search_task);
        show_notes = (Button) findViewById(R.id.show_notes);
        edit_search = (EditText) findViewById(R.id.search_edit);
        edit_task = (EditText) findViewById(R.id.edit_text);

        button_ok.setOnClickListener(this);
        search_task.setOnClickListener(this);
        show_notes.setOnClickListener(this);

        click = false;
        show_added();

    }

    @Override
    public void onClick(View v) {

        dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        String text = edit_task.getText().toString();

        switch (v.getId()) {
            case R.id.button_clear:
                click = false;
                show_added();
                edit_search.setText("");
                break;
            case R.id.button_ok:
                if(!text.equals(""))
                {
                    contentValues.put(DBHelper.KEY_TEXT, text);
                    database.insert(DBHelper.TABLE_TASKS, null, contentValues);
                    show_added();
                    edit_task.setText("");
                }
                break;
            case R.id.button_search_task:
                click = true;
                show_added();
                break;
            case R.id.show_notes:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
        dbHelper.close();
    }

    public void show_added()
    {
        dbHelper = new DBHelper(this);
        tasks.clear();
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_TASKS, null, null,
                null, null, null, null);

        String text_search = edit_search.getText().toString();

        if(cursor.moveToFirst()) {
            int textIndex = cursor.getColumnIndex(DBHelper.KEY_TEXT);

            if(click){
                do {
                    String str = cursor.getString(textIndex);
                    if (str.contains(text_search)) {
                        tasks.add(str);
                    }
                }while (cursor.moveToNext());

            }
            else{
                do {
                    String str = cursor.getString(textIndex);
                    tasks.add(str);
                }while (cursor.moveToNext());
            }

            click = false;

        }

        cursor.close();
        dbHelper.close();

        final GridView tasksList = (GridView) findViewById(R.id.grid_view_checker);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                R.layout.set_tasks, tasks);
        tasksList.setAdapter(adapter);

        tasksList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View v, final int position, long id)
            {

                LayoutInflater li = LayoutInflater.from(TasksActivity.this);
                View promptsView = li.inflate(R.layout.delete_dialog, null);

                AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(TasksActivity.this);
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
                                deleteTask(position);
                            }
                        });

                mDialogBuilder.create().show();


                return true;
            }
        });

    }

    public void deleteTask(int pos) {
        DBHelper dataBaseHelper = new DBHelper(TasksActivity.this);

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_TASKS, null, null,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
            int textIndex = cursor.getColumnIndex(DBHelper.KEY_TEXT);
            int num = 0;
            do {
                int id_index = cursor.getInt(idIndex);
                String text_index = cursor.getString(textIndex);
                if (num == (pos)) {
                    database.delete(DBHelper.TABLE_TASKS, DBHelper.KEY_ID + "= ?",
                            new String[] {Integer.toString(id_index)});
                    show_added();
                    break;
                }
                num++;
            } while (cursor.moveToNext());
        }


        cursor.close();
        dataBaseHelper.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.tasks_menu, menu);
        return true;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete_all){
            dbHelper = new DBHelper(this);
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            database.delete(DBHelper.TABLE_TASKS, null, null);
            dbHelper.close();
            show_added();
        }
        return super.onOptionsItemSelected(item);
    }
}

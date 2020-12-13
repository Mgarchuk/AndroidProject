package com.example.bignotesproject;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.os.Bundle;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.net.*;
import java.io.*;
import java.util.Random;

public class EnterText extends AppCompatActivity implements View.OnClickListener{

    RelativeLayout relativeLayout;
    EditText editText;
    Button button_attach;
    Button button_save;
    Button button_delete;
    @SuppressLint("StaticFieldLeak")
    public static Button button_delete_photo;

    @SuppressLint("StaticFieldLeak")
    public static ImageView imageView;

    int idIndex;
    int textIndex;
    int picIndex;

    int pic_index;
    int id_index;

    public static int pic;

    public static boolean firstly = true;

    boolean flag = MainActivity.is_addition;

    DBHelper dbHelper;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_text);

        firstly = false;
        imageView = (ImageView) findViewById(R.id.image_view);
        relativeLayout = (RelativeLayout) findViewById(R.id.relative_layout);
        editText = (EditText) findViewById(R.id.edit);
        button_attach = (Button) findViewById(R.id.button_attach);
        button_save = (Button) findViewById(R.id.button_save);
        button_delete = (Button) findViewById(R.id.button_delete);
        button_delete_photo = (Button) findViewById(R.id.delete_photo);

        button_attach.setOnClickListener(this);
        button_save.setOnClickListener(this);
        button_delete.setOnClickListener(this);
        button_delete_photo.setOnClickListener(this);

        dbHelper = new DBHelper(this);

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_NOTES, null, null,
                null, null, null, null);


        if(!flag) {
            if (cursor.moveToFirst()) {
                idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                textIndex = cursor.getColumnIndex(DBHelper.KEY_TEXT);
                picIndex = cursor.getColumnIndex(DBHelper.KEY_PIC);
                int num = 0;
                do {
                    id_index = cursor.getInt(idIndex);
                    String text_index = cursor.getString(textIndex);
                    pic_index = cursor.getInt(picIndex);

                    if (num == (MainActivity.selectedItem)) {
                        editText.setText(text_index);
                        if(pic_index == 1)
                            imageView.setImageResource(R.drawable.pic_1);
                        else if(pic_index == 2)
                            imageView.setImageResource(R.drawable.pic_2);
                        else if(pic_index == 3)
                            imageView.setImageResource(R.drawable.pic_3);
                        else if(pic_index == 4)
                            imageView.setImageResource(R.drawable.pic_4);
                        else if(pic_index == 5)
                            imageView.setImageResource(R.drawable.pic_5);
                        else if(pic_index == 6)
                            imageView.setImageResource(R.drawable.pic_6);
                        else if(pic_index == 7)
                            imageView.setImageResource(R.drawable.pic_7);
                        else if(pic_index == 8)
                            imageView.setImageResource(R.drawable.pic_8);
                        else if(pic_index == 9)
                            imageView.setImageResource(R.drawable.pic_9);

                        MainActivity.is_addition = true;
                        if(pic_index != 0)
                            button_delete_photo.setVisibility(View.VISIBLE);
                        else
                            button_delete_photo.setVisibility(View.INVISIBLE);

                        pic_index = 0;
                        break;
                    }
                    num++;
                } while (cursor.moveToNext());
            }

            cursor.close();
        }
        dbHelper.close();


    }

    @Override
    public void onClick(View v) {
        String text = editText.getText().toString();

        dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        boolean save_or_delete = true;
        switch (v.getId()) {
            case R.id.button_save:
                if(flag) {
                    if(!text.equals(""))
                    {
                        contentValues.put(DBHelper.KEY_TEXT, text);
                        contentValues.put(DBHelper.KEY_PIC, pic);
                        database.insert(DBHelper.TABLE_NOTES, null, contentValues);
                    }
                }
                else {
                    if(!text.equals(""))
                    {
                        contentValues.put(DBHelper.KEY_TEXT, text);
                        contentValues.put(DBHelper.KEY_PIC, pic);
                        database.update(DBHelper.TABLE_NOTES, contentValues,
                                DBHelper.KEY_ID + "= ?",
                                new String[] {Integer.toString(id_index)});
                    }
                    else
                    {
                        database.delete(DBHelper.TABLE_NOTES, DBHelper.KEY_ID + "= ?",
                                new String[] {Integer.toString(id_index)});
                    }

                }
                save_or_delete = true;
                break;
            case  R.id.button_delete:
                if(Integer.toString(id_index).equalsIgnoreCase("")){
                    break;
                }
                database.delete(DBHelper.TABLE_NOTES, DBHelper.KEY_ID + "= ?",
                        new String[] {Integer.toString(id_index)});

                save_or_delete = true;
                break;
            case R.id.button_attach:
                pic = 0;
                Intent intent_pic = new Intent(this, GalleryActivity.class);
                startActivity(intent_pic);
                save_or_delete = false;

                break;
            case R.id.delete_photo:
                button_delete_photo.setVisibility(View.INVISIBLE);
                contentValues.put(DBHelper.KEY_PIC, 0);
                pic = 0;
                imageView.setImageDrawable(null);
                save_or_delete = false;
                break;
        }


        if(save_or_delete)
        {
            Intent answerIntent = new Intent();
            answerIntent.putExtra("info", "Данные");
            setResult(RESULT_OK, answerIntent);
            finish();
        }
        dbHelper.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.git_menu, menu);
        return true;
    }

    @SuppressLint({"SetJavaScriptEnabled", "SetTextI18n"})
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.git){

            try {
                RequestQueue queue = Volley.newRequestQueue(this);
                Random random = new Random();
                int blog = random.nextInt(3) + 1;

                String url ="https://codeforces.com/api/blogEntry.view?blogEntryId=" + Integer.toString(blog);

// Request a string response from the provided URL.
                JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Display the first 500 characters of the response string.\\
                                String[] data = response.toString().split("[^\\w']+");
                                String title = "";
                                String author = "";
                                for (int i = 0; i < data.length; ++i) {
                                    if (data[i].equals("title")) {
                                        int j = i + 1;
                                        while(!data[j].equals("locale")) {
                                            title = title.concat(data[j] + " ");
                                            j++;
                                        }
                                    }
                                    if (data[i].equals("authorHandle")) {
                                        int j = i + 1;
                                        while(!data[j].equals("modificationTimeSeconds")) {
                                            author = author.concat(data[j] + " ");
                                            j++;
                                        }
                                    }
                                }
                                editText.setText("Title: " + title + "\n" + "Author: " + author);

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        editText.setText("Blog not found!");
                    }
                });

                queue.add(stringRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
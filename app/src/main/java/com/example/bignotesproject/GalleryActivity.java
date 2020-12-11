package com.example.bignotesproject;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class GalleryActivity extends AppCompatActivity implements View.OnClickListener {

    DBHelper dbHelper;

    int pic = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_activity);

        dbHelper = new DBHelper(this);
    }


    @Override
    public void onClick(View v) {
        dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        switch (v.getId()) {
            case R.id.pic_1:
                pic = 1;
                EnterText.imageView.setImageResource(R.drawable.pic_1);
                break;
            case R.id.pic_2:
                pic = 2;
                EnterText.imageView.setImageResource(R.drawable.pic_2);
                break;
            case R.id.pic_3:
                pic = 3;
                EnterText.imageView.setImageResource(R.drawable.pic_3);
                break;
            case R.id.pic_4:
                pic = 4;
                EnterText.imageView.setImageResource(R.drawable.pic_4);
                break;
            case R.id.pic_5:
                pic = 5;
                EnterText.imageView.setImageResource(R.drawable.pic_5);
                break;
            case R.id.pic_6:
                pic = 6;
                EnterText.imageView.setImageResource(R.drawable.pic_6);
                break;
            case R.id.pic_7:
                pic = 7;
                EnterText.imageView.setImageResource(R.drawable.pic_7);
                break;
            case R.id.pic_8:
                pic = 8;
                EnterText.imageView.setImageResource(R.drawable.pic_8);
                break;
            case R.id.pic_9:
                pic = 9;
                EnterText.imageView.setImageResource(R.drawable.pic_9);
                break;
        }
        if(pic != 0)
            EnterText.button_delete_photo.setVisibility(View.VISIBLE);
        else
            EnterText.button_delete_photo.setVisibility(View.INVISIBLE);
        EnterText.pic = pic;
        dbHelper.close();
        finish();
    }
}
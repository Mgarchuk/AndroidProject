package com.example.bignotesproject;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.security.PublicKey;


public class Safety extends AppCompatActivity implements View.OnClickListener {

    public static boolean firstly = true;
    public static int number;

    public static String cur_password;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.safety);
        firstly = false;


    }

    @Override
    public void onClick(View v) {
        DBHelper dbHelper;
        dbHelper = new DBHelper(this);

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor_password = database.query(DBHelper.TABLE_PASSWORD, null, null,
                null, null, null, null);

        if(cursor_password.moveToFirst()) {
            int passwordIndex = cursor_password.getColumnIndex(DBHelper.KEY_PASSWORD);
            cur_password = (cursor_password.getString(passwordIndex));
        }
        cursor_password.close();
        dbHelper.close();

        switch (v.getId()) {
            case R.id.add_password:
                number = 1;
                break;
            case R.id.change_password:
                number = 2;
                break;
            case R.id.delete_password:
                number = 3;
                break;
        }
        if(number == 1 && cur_password == null) {
            Intent intent = new Intent(this, Password.class);
            startActivity(intent);
            finish();

        }
        else if(number == 2 && cur_password != null)
        {
            Intent intent = new Intent(this, Password.class);
            startActivity(intent);
            finish();
        }
        else if(number == 3 && cur_password != null)
        {
            Intent intent = new Intent(this, Password.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Toast toast = Toast.makeText(this, "Действие не может быть прозведено", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0,160);
            toast.show();
        }

    }
}

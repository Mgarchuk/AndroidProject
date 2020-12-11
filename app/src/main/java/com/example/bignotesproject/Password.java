package com.example.bignotesproject;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Password extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Character> password = new ArrayList<>();

    TextView textView;
    String text;
    boolean permission = false;
    Button button;



    DBHelper dbHelper;

    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, del;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password);

        b0 = (Button) findViewById(R.id.button_0);
        b1 = (Button) findViewById(R.id.button_1);
        b2 = (Button) findViewById(R.id.button_2);
        b3 = (Button) findViewById(R.id.button_3);
        b4 = (Button) findViewById(R.id.button_4);
        b5 = (Button) findViewById(R.id.button_5);
        b6 = (Button) findViewById(R.id.button_6);
        b7 = (Button) findViewById(R.id.button_7);
        b8 = (Button) findViewById(R.id.button_8);
        b9 = (Button) findViewById(R.id.button_9);
        del = (Button) findViewById(R.id.delete_digit);
        button = (Button) findViewById(R.id.save_password);

        b0.setOnClickListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);
        del.setOnClickListener(this);
        button.setOnClickListener(this);

        if(Safety.number == 1) {
            Toast toast = Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0,160);
            toast.show();
        }
        else if (Safety.number == 2 || Safety.number == 3) {
            Toast toast = Toast.makeText(this, "Введите пароль для подтверждения",Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0,160);
            toast.show();

            if (Safety.number == 3)
                button.setText("Удалить");
            if (Safety.number == 2)
                button.setText("Готово");
        }

        textView = (TextView) findViewById(R.id.edit_password);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View v) {
        if(password.size() < 4) {
            switch (v.getId()) {
                case R.id.button_0:
                    password.add('0');
                    break;
                case R.id.button_1:
                    password.add('1');
                    break;
                case R.id.button_2:
                    password.add('2');
                    break;
                case R.id.button_3:
                    password.add('3');
                    break;
                case R.id.button_4:
                    password.add('4');
                    break;
                case R.id.button_5:
                    password.add('5');
                    break;
                case R.id.button_6:
                    password.add('6');
                    break;
                case R.id.button_7:
                    password.add('7');
                    break;
                case R.id.button_8:
                    password.add('8');
                    break;
                case R.id.button_9:
                    password.add('9');
                    break;
                case R.id.delete_digit:
                    if (password.size() != 0)
                        password.remove(password.size() - 1);
                    break;
            }
        }
        else
        {
            switch (v.getId()) {
                case R.id.delete_digit:
                    if (password.size() != 0)
                        password.remove(password.size() - 1);
                    break;
                case R.id.save_password:
                    dbHelper = new DBHelper(this);
                    SQLiteDatabase database = dbHelper.getWritableDatabase();

                    if(Safety.number == 1)
                    {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DBHelper.KEY_PASSWORD, text);
                        database.insert(DBHelper.TABLE_PASSWORD, null, contentValues);
                        finish();
                    }
                    else if (Safety.number == 2 || Safety.number == 3){

                        ///

                        if(Safety.number == 2) {
                            if (permission) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put(DBHelper.KEY_PASSWORD, text);
                                database.delete(DBHelper.TABLE_PASSWORD, null, null);
                                database.insert(DBHelper.TABLE_PASSWORD, null, contentValues);
                                finish();

                            } else if (text.equals(Safety.cur_password)) {
                                Toast toast = Toast.makeText(this, "Введите новый пароль",Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP, 0,160);
                                toast.show();
                                permission = true;
                                password.clear();
                                button.setText("Сохранить");
                                textView.setText("");
                            } else {
                                Toast toast = Toast.makeText(this, "Пароль введен неверно",Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP, 0,160);
                                toast.show();
                            }
                        }
                        else if(text.equals(Safety.cur_password)){
                            database.delete(DBHelper.TABLE_PASSWORD, null, null);
                            Safety.cur_password = null;
                            finish();
                        }
                        else {
                            textView.setText("");
                            password.clear();
                            Toast toast = Toast.makeText(this, "Пароль введен неверно",Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0,160);
                            toast.show();

                        }
                    }
                    dbHelper.close();
                    break;
            }


        }

        char[] digits = new char[4];
        
        for(int i = 0; i < password.size(); i++) {
            digits[i] = password.get(i);
        }

        text = new String(digits);

        textView.setText(text);

    }
}

package com.example.bignotesproject;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EnterPassword extends AppCompatActivity implements View.OnClickListener {

    ArrayList<Character> password = new ArrayList<>();

    TextView textView;
    String text;
    Button b1, b2, b3, b4, b5, b6, b7, b8, b9, b0, del;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_password);
        textView = (TextView) findViewById(R.id.text_password_add);

        b0 = (Button) findViewById(R.id.button0);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        b5 = (Button) findViewById(R.id.button5);
        b6 = (Button) findViewById(R.id.button6);
        b7 = (Button) findViewById(R.id.button7);
        b8 = (Button) findViewById(R.id.button8);
        b9 = (Button) findViewById(R.id.button9);
        del = (Button) findViewById(R.id.deletedigit);

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button0:
                password.add('0');
                break;
            case R.id.button1:
                password.add('1');
                break;
            case R.id.button2:
                password.add('2');
                break;
            case R.id.button3:
                password.add('3');
                break;
            case R.id.button4:
                password.add('4');
                break;
            case R.id.button5:
                password.add('5');
                break;
            case R.id.button6:
                password.add('6');
                break;
            case R.id.button7:
                password.add('7');
                break;
            case R.id.button8:
                password.add('8');
                break;
            case R.id.button9:
                password.add('9');
                break;
            case R.id.deletedigit:
                if (password.size() != 0)
                    password.remove(password.size() - 1);
                break;
        }


        char[] text_arr = new char[4];

        for(int i = 0; i < password.size(); i++) {
            text_arr[i] = password.get(i);
        }

        text = new String(text_arr);


        char[] dots_arr = new char[4];

        for(int i = 0; i < password.size(); i++) {
           dots_arr[i] = '.';
        }

        String dots = new String(dots_arr);
        textView.setText(dots);


        if(password.size() == 4) {
            if (text.equals(MainActivity.password))
                finish();
            else
                password.clear();
                textView.setText("");
        }
    }
}

package com.example.dialer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "idk";
    private EditText number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        number = findViewById(R.id.numberToDial);
    }

    public void Dial(View view) {
        String num =  number.getText().toString();
        switch (view.getId()) {
            case R.id.number10:
                Log.d("test0", "0");
                number.setText(num + "0");
                break;
            case R.id.number1:
                number.setText(num + "1");
                break;
            case R.id.number:
                number.setText(num + "2");
                break;
            case R.id.number2:
                number.setText(num + "3");
                break;
            case R.id.number3:
                number.setText(num + "4");
                break;
            case R.id.number4:
                number.setText(num + "5");
                break;
            case R.id.number5:
                number.setText(num + "6");
                break;
            case R.id.number6:
                number.setText(num + "7");
                break;
            case R.id.number7:
                number.setText(num + "8");
                break;
            case R.id.number11:
                number.setText(num + "9");
                break;
            case R.id.number9:
                number.setText(num + "*");
                break;
            case R.id.number8:
                number.setText(num + "#");
                break;

        }
    }

    public void startCall(View view) {

        Log.d("oio", "Coioo");
        //get the string value of the EditText
        String num = number.getText().toString();

        //Encode and parse that string into a Uri object
        Uri add = Uri.parse(num);

        //Create a new Intent with Intent.ACTION_CALL as the action and the URI as the data:
        Intent intent = new Intent(Intent.ACTION_CALL, add);

        // Find an activity to hand the intent and start that activity.
        // if condition to make sure there is at least one Activity that can handle your requests
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("ImplicitIntents", "Can't handle this intent!");
        }
    }
}


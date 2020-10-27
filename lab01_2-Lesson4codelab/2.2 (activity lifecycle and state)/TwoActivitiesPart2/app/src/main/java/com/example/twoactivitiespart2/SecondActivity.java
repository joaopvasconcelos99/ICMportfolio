package com.example.twoactivitiespart2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    //public constant to define the key for the Intent extra
    public static final String EXTRA_REPLY =
            "com.example.android.twoactivitiespart2.extra.REPLY";

    private EditText mReply;

    private static final String LOG_TAG = SecondActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //findViewByID() to get a reference to the EditText and assign it to that private variable
        mReply = findViewById(R.id.editText_second);

        Intent intent = getIntent();

        //Get the string containing the message from the Intent extras using the MainActivity.EXTRA_MESSAGE static variable as the key
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        //Use findViewByID() to get a reference to the TextView for the message from the layout
        TextView textView = findViewById(R.id.text_message);

        //Set text of the TextView to the string from the Intent extra
        textView.setText(message);
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(LOG_TAG, "onStart");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(LOG_TAG, "onPause");
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.d(LOG_TAG, "onRestart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(LOG_TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    public void returnReply(View view) {

        //get the text of the EditText as a string
        String reply = mReply.getText().toString();

        //create a new intent for the response
        Intent replyIntent = new Intent();

        //Add reply string from the EditText to the new intent as an Intent extra
        replyIntent.putExtra(EXTRA_REPLY, reply);

        //indicate the response was successful, the activity class defines the result codes
        setResult(RESULT_OK,replyIntent);

        Log.d(LOG_TAG, "End SecondActivity");

        //close activity and return to MainActivity
        finish();
    }
}
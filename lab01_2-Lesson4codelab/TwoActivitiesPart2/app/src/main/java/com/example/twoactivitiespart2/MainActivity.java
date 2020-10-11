package com.example.twoactivitiespart2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //define the key for a particular type of response you're interested in
    public static final int TEXT_REQUEST = 1;

    private TextView mReplyHeadTextView;
    private TextView mReplyTextView;

    // define the key for the Intent extra
    public static final String EXTRA_MESSAGE =
            "com.example.android.twoactivitiespart2.extra.MESSAGE";

    private EditText mMessageEditText;

    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(LOG_TAG, "-------");
        Log.d(LOG_TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get a reference to the EditText and assign it to that private variable
        mMessageEditText = findViewById(R.id.editText_main);

        mReplyHeadTextView = findViewById(R.id.text_header_reply);
        mReplyTextView = findViewById(R.id.text_message_reply);

        // Restore the saved state
        // See onSaveInstanceState() for what gets saved
        if (savedInstanceState != null) {

            //get the current visibility (true or false) out of the Bundle with the key "reply_visible"
            boolean isVisible = savedInstanceState.getBoolean("reply_visible");

            // Show both the header and the message views. If isVisible is
            if (isVisible) {

                //make the header visible
                mReplyHeadTextView.setVisibility(View.VISIBLE);

                //Get the text reply message from the Bundle with the key "reply_text", and set the reply TextView to show that string
                mReplyTextView.setText(savedInstanceState.getString("reply_text"));

                //make the TextView visible
                mReplyTextView.setVisibility(View.VISIBLE);
            }
        }
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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //check to see if header is currently visible
        if (mReplyHeadTextView.getVisibility() == View.VISIBLE) {
            outState.putBoolean("reply_visible", true);
        }

        outState.putString("reply_text",mReplyTextView.getText().toString());
    }

    public void launchSecondActivity(View view) {
        Log.d(LOG_TAG, "Button clicked!");
        Intent intent = new Intent(this, SecondActivity.class);

        //get text from EditText as string
        String message = mMessageEditText.getText().toString();

        //Add above string to the Intent as an extra with the EXTRA_MESSAGE constant as the key and the string as the value
        intent.putExtra(EXTRA_MESSAGE, message);

        startActivityForResult(intent, TEXT_REQUEST);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        //requestCode set when the activity is launched with startActivityResult() /code for the SecondActivity request)
        //resultCode set in the launched Activity (code that comes back from SecondActivity)
        //Intent data contains data returned from the launch Activity (sent back form SecondActivity)
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                String reply =
                        data.getStringExtra(SecondActivity.EXTRA_REPLY);

                // Make reply head visible
                mReplyHeadTextView.setVisibility(View.VISIBLE);

                // Set reply and make it visible.
                mReplyTextView.setText(reply);
                mReplyTextView.setVisibility(View.VISIBLE);
            }
        }
    }


}
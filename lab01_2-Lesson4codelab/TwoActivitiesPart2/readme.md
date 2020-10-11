# Android fundamentals 02.2: Activity lifecycle and state

## Lifecycles

The lifecycle methods are onCreate(), onStart(), onPause(), onRestart(), onResume(), onStop(), onDestroy()

In the onCreate() method, add the following log statements:

```java
// Log the start of the onCreate() method.
Log.d(LOG_TAG, "-------");
Log.d(LOG_TAG, "onCreate");
```
The other lifecycle methods: (added to the MainActivity.java as well as SecondActivity.java)

```java
@Override
public void onStart(){
    super.onStart();
    Log.d(LOG_TAG, "onStart");
}
```
Use the onStart() method as a template to implement the onPause(), onRestart(), onResume(), onStop(), and onDestroy() lifecycle callbacks.



## Rotating device

Rotating the device does not affect the state of the second Activity at all. This is because the second Activity layout and state are generated from the layout and the Intent that activated it. Even if the Activity is recreated, the Intent is still there and the data in that Intent is still used each time the onCreate() method in the second Activity is called

In each Activity, any text you typed into message or reply EditText elements is retained even when the device is rotated. This is because the state information of some of the View elements in your layout are automatically saved across configuration changes, and the current value of an EditText is one of those cases.

So the only Activity state you're interested in are the TextView elements for the reply header and the reply text in the main Activity. Both TextView elements are invisible by default; they only appear once you send a message back to the main Activity from the second Activity.

## Saving data

If the header is visible, then there is reply data that needs to be saved

```java
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //check to see if header is currently visible
        if (mReplyHeadTextView.getVisibility() == View.VISIBLE) {
            outState.putBoolean("reply_visible", true);
        }

        outState.putString("reply_text",mReplyTextView.getText().toString());
    }
```

## Restore the Activity instance state in onCreate()

Most of the time the better place to restore the Activity state is in onCreate(), to ensure that the UI, including the state, is available as soon as possible

In the onCreate() method:

```java
   // Initialize all the view variables.
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
```
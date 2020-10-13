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

# Android fundamentals 02.3: Implicit Intents

In this section you learn more about implicit intents and how to use them to carry out activities

With an **implicit intent**, you initiate an activity without knowing which app or activity will handle the task. For example, if you want your app to take a photo, send email, or display a location on a map, you typically don't care which app or activity performs the task.

An activity can declare one or more intent filters in the **AndroidManifest.xml** file to advertise that the activity can accept implicit intents, and to define the types of intents that the activity will accept.

The Android system matches your **implicit intent** with an activity whose intent filters indicate that they can perform the action.

## Open a Website example

### strings.xml
In **strings.xml** we add:

```xml
<string name="edittext_uri">http://developer.android.com</string>
<string name="button_uri">Open Website</string>

```
### activity_main.xml

In **activity_main.xml** we add
```xml
<EditText
        android:id="@+id/website_edittext"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
         android:text="@string/edittext_uri"/>

    <Button
        android:id="@+id/open_website_button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
         android:layout_marginBottom="24dp"
        android:text="@string/button_uri"
        android:onClick="openWebsite"/>

```

### MainActivity.java 

In **MainActivity**, add a private variable at the top of the class to hold the EditText object for the web site URI.

```java
private EditText mWebsiteEditText;
```

In the **onCreate()** method for **MainActivity**, use **findViewById()** to get a reference to the **EditText** instance and assign it to that private variable:

```java
mWebsiteEditText = findViewById(R.id.website_edittext);
```

### openWebsite(View) inside MainActivity.java

This **Intent** constructor is different from the one  used to create an **explicit Intent**. 

In the previous constructor, we specify the **current context** and a specific component (**Activity class**) to send the **Intent**. In this constructor you specify an **action** and the **data** for that action. 

**Actions** are defined by the **Intent** class and can include:
- **ACTION_VIEW** (to view the given data)
- **ACTION_EDIT** (to edit the given data)
- **ACTION_DIAL** (to dial a phone number)

In this case the action is **ACTION_VIEW** because you want to display the web page specified by the URI in the webpage variable.

```java
public void openWebsite(View view) {

        //get the string value of the EditText
        String url = mWebsiteEditText.getText().toString();

        //Encode and parse that string into a Uri object
        Uri webpage = Uri.parse(url);

        //Create a new Intent with Intent.ACTION_VIEW as the action and the URI as the data:
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        // Find an activity to hand the intent and start that activity.
        // You use if condition to make sure there is at least one Activity that can handle your requests
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("ImplicitIntents", "Can't handle this intent!");
        }
    }
```

# Android fundamentals 02.3.1: Implicit Intents

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

## Other Examples

### openLocation(View) example to compare:

```java
public void openLocation(View view) {

        String loc = mLocationEditText.getText().toString();

        Uri addressUri = Uri.parse("geo:0,0?q=" + loc);

        Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("ImplicitIntents", "Can't handle this intent!");
        }
    }
```

### shareText(View) example to compare:

```java
public void shareText(View view) {
        String txt = mShareTextEditText.getText().toString();
        String mimeType = "text/plain";
        ShareCompat.IntentBuilder
                .from(this) //The Activity that launches this share Intent (this)
                .setType(mimeType) //The MIME type of the item to be shared
                .setChooserTitle("Share this text with: ") //The title that appears on the system app chooser
                .setText(txt) //The actual text to be shared
                .startChooser(); //Show the system app chooser and send the Intent
    }
```

# Android fundamentals 02.3.2: Implicit Intents Receiver

## Receive Implicit Intents Example

### activity_main.xml

Instead of the default **Hello_World** **TextView** we use:

```xml
<TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        android:id="@+id/text_uri_message"
        android:textSize="18sp"
        android:textStyle="bold"
        app:layout_constraintTop_toTopOf="parent" />
```

### AndroidManifest.xml

The **application** section of **AndroidManifest.xml** should look as follows:

```xml
<application
    android:allowBackup="true"
    android:icon="@mipmap/ic_launcher"
    android:label="@string/app_name"
    android:roundIcon="@mipmap/ic_launcher_round"
    android:supportsRtl="true"
    android:theme="@style/AppTheme">
    <activity android:name=".MainActivity">
        <intent-filter>
            <action android:name="android.intent.action.MAIN" />
            <category android:name="android.intent.category.LAUNCHER" />
        </intent-filter>
        
        <!--intent-filter added-->
        <intent-filter>
            <action android:name="android.intent.action.VIEW" />
            <category android:name="android.intent.category.DEFAULT" />
            <category android:name="android.intent.category.BROWSABLE" />
            <data android:scheme="http" 
                                android:host="developer.android.com" />
        </intent-filter>
    </activity>
</application>
```

The **intent-filter** added declares these elements:

| Filter type        | Value         | Matches |
| ------------- |:-------------:| -----:|
| action     | "android.intent.action.VIEW"| Any **Intent** with view actions. |
| category      | "android.intent.category.DEFAULT"    |  Any **implicit Intent**. This category must be included for your **Activity** to receive **any implicit Intent**. |
| category | "android.intent.category.BROWSABLE"      |    Requests for browsable links from web pages, email, or other sources. |
| data | android:scheme="http" android:host="developer.android.com"      |    URIs that contain a scheme of http and a host name of developer.android.com |

### MainActivity.java

The **onCreate** method for **MainActivity** should look as follows:

```java
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the incoming Intent that was used to activate the Activity
        Intent intent = getIntent();

        // Get the Intent data. Intent data is always a URI object:
        Uri uri = intent.getData();

        //Check to make sure that the uri variable is not null. If that check passes, create a string from that URI object:
        if (uri != null) {
            String uri_string = "URI: "  + uri.toString();

            // get the TextView for the message:
            TextView textView = findViewById(R.id.text_uri_message);

            //set the text of that TextView to the URI:
            textView.setText(uri_string);
        }
    }
```

### How to Test

1. Run the Implicit Intents Receiver app.

2. Run the Implicit Intents app, and click Open Website with the default URI. **(Select Implicit Intents Receiver)**

3. Tap the Back button and enter a different URI. Click Open Website. **(Any other URI opens in the default web browser)**

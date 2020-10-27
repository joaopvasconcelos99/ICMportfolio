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
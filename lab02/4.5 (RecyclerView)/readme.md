# Android fundamentals 04.5: Recycler View

## Creating a Recycler View

**1.** Add a **RecyclerView** element to the **MainActivity XML** content layout (**content_main.xml**) for the RecyclerView app

```xml
<androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerview"
        android:layout_width="match_parent"
        android:layout_height="match_parent">
</androidx.recyclerview.widget.RecyclerView>
```

**2.** Create an XML layout file (**wordlist_item.xml**) for one list item, which is **WordListItem**

- Right-click the **app > res > layout** folder and choose **New > Layout resource file**.

- Change the new xml file to somehting like the following:

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android" android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="vertical"
    android:padding="6dp">

    <TextView
        android:id="@+id/word"
        android:text="TextView"
        style="@style/world_title" />
</LinearLayout>
```

**3.** Create a **style** from the TextView attributes

- **Right-click the TextView** just created, and choose **Refactor > Style**

- Name. Then select the **Launch ‘Use Style Where Possible' option**. Then click OK.

- This style is now located in **values > styles.xml.**

**4.** Create an **adapter**

- Right-click **java/com.android.example.recyclerview** and select **New > Java Class**

```java
public class WordListAdapter extends
    RecyclerView.Adapter<WordListAdapter.WordViewHolder>  {}
```

**5.** Create the **viewholder** for the adapter

```java
public class WordListAdapter extends
        RecyclerView.Adapter<WordListAdapter.WordViewHolder>  {

    class WordViewHolder extends RecyclerView.ViewHolder {
        public final TextView wordItemView;
        final WordListAdapter mAdapter;

        public WordViewHolder(View itemView, WordListAdapter adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
        }
    }

    @NonNull
    @Override
    public WordListAdapter.WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull WordListAdapter.WordViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}

```

**6.** Storing your **data** in the **adapter**

```java
public class WordListAdapter extends
        RecyclerView.Adapter<WordListAdapter.WordViewHolder>  {

    private final LinkedList<String> mWordList;

    // WordListAdapter needs a constructor that initializes the word list from the data.
    // To create a View for a list item, the WordListAdapter needs to inflate the XML for a list item
    // LayoutInflator reads a layout XML description and converts it into the corresponding View items
    private LayoutInflater mInflater;

    public WordListAdapter(LinkedList<String> mWordList) {
        this.mWordList = mWordList;
    }

    class WordViewHolder extends RecyclerView.ViewHolder {
        public final TextView wordItemView;
        final WordListAdapter mAdapter;

        public WordViewHolder(View itemView, WordListAdapter adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.word);
            this.mAdapter = adapter;
        }
    }

    public WordListAdapter(Context context,
                           LinkedList<String> wordList) {
        mInflater = LayoutInflater.from(context);
        this.mWordList = wordList;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        View mItemView = mInflater.inflate(R.layout.wordlist_item,
                parent, false);
        return new WordViewHolder(mItemView, this);
    }


    //connects your data to the view holder.
    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        String mCurrent = mWordList.get(position);
        holder.wordItemView.setText(mCurrent);
    }

    @Override
    public int getItemCount() {
        return mWordList.size();
    }


}

```

**6.** Create the **RecyclerView** in the **Activity**

- Now that you have an **adapter** with a **ViewHolder**, you can finally create a **RecyclerView** and connect all the pieces to display your **data**.

```java
private RecyclerView mRecyclerView;
private WordListAdapter mAdapter;


//inside onCreate()
// Get a handle to the RecyclerView.
mRecyclerView = findViewById(R.id.recyclerview);
// Create an adapter and supply the data to be displayed.
mAdapter = new WordListAdapter(this, mWordList);
// Connect the adapter with the RecyclerView.
mRecyclerView.setAdapter(mAdapter);
// Give the RecyclerView a default layout manager.
mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

```
# Android fundamentals 04.1: Clickable Images

### Adding images to a project

1. close project
2. Add images to *project_name* > **app > src > main > res > drawable**
3. open project
4. drag an **image view** to design and select the image

### Adding an icon

1. right click on **drawable** folder
2. **New > Image Asset**
3. Choose **Action Bar and Tab Items** in the drop-down menu at the top of the dialog

Use the package manager [pip](https://pip.pypa.io/en/stable/) to install foobar.

### OnClick (on any of the images) to open OrderActivity

```java
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        
        //new
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,
                        OrderActivity.class);
                startActivity(intent);
            }
        });
    }
```
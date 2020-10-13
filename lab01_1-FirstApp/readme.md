# MyFirstApp

## Intent

Um **Intent** é um objeto que fornece vínculos de tempo de execução entre componentes separados, como duas atividades. O **Intent** representa uma “intenção de fazer algo” do aplicativo. Você pode usar intents para uma ampla variedade de tarefas, mas, nesta lição, a intent iniciará outra atividade.

## Usar o intent

Na janela **Attributes**, localize a propriedade **onClick** e selecione **sendMessage** [MainActivity] na lista suspensa.
```java
public class MainActivity extends AppCompatActivity {
        public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
        }

        /** Called when the user taps the Send button */
        public void sendMessage(View view) {
            Intent intent = new Intent(this, DisplayMessageActivity.class);
            EditText editText = (EditText) findViewById(R.id.editText);
            String message = editText.getText().toString();
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }
    }
```
### sendMessage()

- O criador do **Intent** usa dois parâmetros, um **Context** e um **Class**.

- O parâmetro **Context** é usado primeiro porque a classe **Activity** é uma subclasse de **Context**.

- Nesse caso, o parâmetro **Class** do componente do app, ao qual o sistema entrega o **Intent**, é a atividade a ser iniciada.

- O método **putExtra()** adiciona o valor de **EditText** à intent. Uma **Intent** pode carregar tipos de dados como pares de chave-valor chamados de extras.

- Sua chave é uma **EXTRA_MESSAGE** pública constante porque a próxima atividade usa a chave para recuperar o valor de texto. É recomendável definir chaves para intents extras com o nome do pacote do app como prefixo. Isso garante que as chaves sejam únicas caso seu app interaja com outros.

- O método **startActivity()** inicia uma instância do **DisplayMessageActivity** especificada pela **Intent**. Em seguida, você precisa criar essa classe.

### DisplayMessageActivity Class
De forma a exibir a mensagem que foi transmitida pela primeira atividade:
```java
public class DisplayMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the string as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
    }


}
```

## Adicionar navegação para cima (back button)
No AndroidManifest.xml:
```xml
<activity android:name=".DisplayMessageActivity"
              android:parentActivityName=".MainActivity">
        <!-- The meta-data tag is required if you support API level 15 and lower -->
        <meta-data
            android:name="android.support.PARENT_ACTIVITY"
            android:value=".MainActivity" />
</activity>
```
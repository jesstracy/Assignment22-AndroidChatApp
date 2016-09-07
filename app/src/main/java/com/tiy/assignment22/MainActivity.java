package com.tiy.assignment22;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {

    ListView list;
    EditText text;
    Button sendButton;
    ArrayAdapter<String> items;
    ChatClient myChatClient;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        list = (ListView)findViewById(R.id.listView);
        text = (EditText) findViewById(R.id.editText);
        sendButton = (Button) findViewById(R.id.button);

        items = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        list.setAdapter(items);

        sendButton.setOnClickListener(this);
        list.setOnItemLongClickListener(this);
//        text.setOnEditorActionListener();

        myChatClient = new ChatClient();
        myChatClient.startClientSocket();
    }

    @Override
    public void onClick(View v) {
        String item = text.getText().toString();
        items.add(item);
        text.setText("");
        myChatClient.sendMessage(item);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String item = items.getItem(position);
        items.remove(item);
        return true;
    }

//    public boolean onKey(KeyEvent e) {
//        if (e.getKeyCode() )
//    }
}

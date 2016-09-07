package com.tiy.assignment22;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {

    ListView list;
    EditText text;
    Button sendButton;
    Button historyButton;
    Button exitButton;
    ArrayAdapter<String> items;
    ChatClient myChatClient;
    int sendCount = 0;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        list = (ListView)findViewById(R.id.listView);
        text = (EditText) findViewById(R.id.editText);
        sendButton = (Button) findViewById(R.id.button);
        historyButton = (Button) findViewById(R.id.button2);
        exitButton = (Button) findViewById(R.id.button3);

        text.setHint("Enter your name");

        items = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        list.setAdapter(items);

        sendButton.setOnClickListener(this);


        list.setOnItemLongClickListener(this);
//        text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                boolean handled = false;
//                if (actionId == EditorInfo.IME_ACTION_RETURN) {
//                    enterAMessage();
//                    handled = true;
//                }
//                return handled;
//            }
//        });

        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sendCount != 0 && sendCount != 1) {
                    items.add("history");
                    ArrayList<String> historyStrings = myChatClient.sendHistoryMessage();
                    for (String response : historyStrings) {
                        items.add("\t\t*" + userName + "'s History* " + response);
                    }
                } else {
                    items.add("No history to show!");
                }
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                items.add("exit");
                String serverResponse = myChatClient.sendMessage("exit", sendCount);
                items.add("\t\t*Server Response* " + serverResponse);
            }
        });


        myChatClient = new ChatClient();
        myChatClient.startClientSocket();
    }

    // onClickListener for first button is doing this one bc we said the listener was "this" for first button (??)
    @Override
    public void onClick(View v) {
        enterAMessage();
    }

    public void enterAMessage() {
        String item = text.getText().toString();
        if (sendCount == 0) {
            userName = item;
        }
        items.add(item);
        text.setText("");
        text.setHint("Write a message");
//        if (!item.equals("history")) {
            String serverResponse = myChatClient.sendMessage(item, sendCount);
            items.add("\t\t*Server Response* " + serverResponse);
//        } else {
//            ArrayList<String> historyStrings = myChatClient.sendHistoryMessage();
//            for (String response : historyStrings) {
//                items.add("\t\t*" + userName + "'s History* " + response);
//            }
//        }

        sendCount++;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        String item = items.getItem(position);
        items.remove(item);
        return true;
    }

}

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

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {

    ListView list;
    EditText text;
    Button sendButton;
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

        text.setHint("Enter your name");

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
        if (sendCount == 0) {
            userName = item;
        }
        items.add(item);
        text.setText("");
        text.setHint("Write a message!");
//        ArrayList<String> serverResponses = myChatClient.sendMessage(item, sendCount);
        if (!item.equals("history")) {
            String serverResponse = myChatClient.sendMessage(item, sendCount);
            items.add("\t\t*Server Response* " + serverResponse);
        } else {
            ArrayList<String> historyStrings = myChatClient.sendHistoryMessage();
            for (String response : historyStrings) {
                items.add("\t\t*" + userName + "'s History* " + response);
            }
        }

//        for (String response : serverResponses) {
//            items.add("*Server Response*" + response);
//        }

        sendCount++;

//        if (item.equals("history")) {
//            try {
//                String responseFromServer;
//                while ((responseFromServer = myChatClient.getIn().readLine()) != null) {
//                    myChatClient.addResponse(responseFromServer);
//                }
//
//                // *** Now printing responses from server ***
//                if (responseFromServer != null) {
//                    for (String string : myChatClient.getServerResponses()) {
//                        items.add(string);
//                    }
//                }
//            } catch (IOException ex) {
//                System.out.println("Exception caught when reading in from server.");
//                ex.printStackTrace();
//            }
//        }
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

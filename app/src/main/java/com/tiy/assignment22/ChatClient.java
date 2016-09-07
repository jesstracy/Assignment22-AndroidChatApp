package com.tiy.assignment22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by jessicatracy on 9/7/16.
 */
public class ChatClient {
    private PrintWriter out;
    private BufferedReader in;
//    private ArrayList<String> serverResponses = new ArrayList<String>();

    public void startClientSocket() {
        try {
            Socket clientSocket = new Socket("172.16.100.5", 8005);

            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        } catch (IOException ex) {
            System.out.println("Exception caught when creating client socket");
            ex.printStackTrace();
        }
    }


    public String sendMessage(String message, int sendCount) {
        // if it's the first message, the user is sending his/her name
        if (sendCount == 0) {
            String nameMessage = "name=" + message;
            out.println(nameMessage);
        } else {
            out.println(message);
        }
        String serverResponse = getServerResponse();
        return serverResponse;
    }

    public ArrayList<String> sendHistoryMessage() {
        ArrayList<String> historyStrings = new ArrayList<>();
        try {
            out.println("history");
            String currentLine;
            while (!(currentLine = in.readLine()).equals("TX::HISTORY::END")) {
                historyStrings.add(currentLine);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return historyStrings;
    }

    public String getServerResponse() {
        String serverResponse = "";
        try {
        serverResponse = in.readLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return serverResponse;
    }

    //getters and setters
//    public ArrayList<String> getServerResponses() {
//        return serverResponses;
//    }
//
//    public void setServerResponses(ArrayList<String> serverResponses) {
//        this.serverResponses = serverResponses;
//    }
//
//    public void addResponse(String response) {
//        serverResponses.add(response);
//    }

    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }
}

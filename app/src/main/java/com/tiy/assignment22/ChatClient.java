package com.tiy.assignment22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by jessicatracy on 9/7/16.
 */
public class ChatClient {
    private PrintWriter out;
    private BufferedReader in;

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
    public void sendMessage(String message) {
        // send message here!
        out.println(message);
    }

}

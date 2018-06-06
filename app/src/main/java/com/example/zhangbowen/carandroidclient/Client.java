package com.example.zhangbowen.carandroidclient;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    private static final int PORT = 8888;
    private static Socket socket;

    public static int startClient(String IP_ADDR,MainActivity activity) {
        socket = null;
        try {
            socket = new Socket(IP_ADDR, PORT);
        } catch (Exception e) {
            return -1;
        }
        return 1;
    }

    public static void sendCommand(String x) {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            x += '\r';
            x += '\n';
            out.writeUTF(x);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (x.charAt(0) == 27) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
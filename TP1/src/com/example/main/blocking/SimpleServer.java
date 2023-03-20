package com.example.main.blocking;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(1234);
        System.out.println("I'm waiting new connection");
        Socket s = ss.accept();
        InputStream is = s.getInputStream();
        OutputStream os = s.getOutputStream();
        System.out.println("I'm waiting data");
        int nb = is.read();
        int res = nb*2;
        System.out.println("I'm sending response");
        os.write(res);
        s.close();
    }
}

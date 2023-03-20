package com.example.main.blocking;

import com.sun.security.ntlm.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadBlockingServer extends Thread{

    private int clientsCount;

    public static void main(String[] args) {
        new MultiThreadBlockingServer().start();
    }

    @Override
    public void run() {
        System.out.println("The server is startes using port:1234");
        try {
            ServerSocket ss = new ServerSocket(1234);
            while(true){
                Socket socket = ss.accept();
                clientsCount++;
                new Conversation(socket, clientsCount).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Conversation extends Thread{
        private Socket socket;
        private int clientId;
        public Conversation(Socket socket, int clientId) {
            this.socket=socket;
            this.clientId=clientId;
        }

        @Override
        public void run() {
            try {
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);
                String ip = socket.getRemoteSocketAddress().toString();
                System.out.println("New client connexion : " + clientId + " IP : "+ ip);
                pw.println("Welcome, you are client " + clientId);
                String request;
                while((request=br.readLine())!=null){
                    System.out.println("New client connexion : "+ clientId +", request : "+ request);
                    String response = "Size : "+ request.length();
                    pw.println(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

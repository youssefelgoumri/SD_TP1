package com.example.main.blocking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class MultiThreadChatServer extends Thread{
    private int nbrClient=0;
    private List<Conversation> Clients = new ArrayList<Conversation>();



    public static void main(String[] args)
    {
        new MultiThreadChatServer().start();

    }

    public void run() {
        try {
            ServerSocket ss = new ServerSocket(4321);
            System.out.println("Server is started...");
            while(true)
            {
                Socket socket = ss.accept();
                nbrClient++;
                Conversation conversation = new Conversation(socket ,  nbrClient);
                Clients.add(conversation);
                conversation.start();
            }
        }
        catch (IOException e)
        {

            e.printStackTrace();
        }
    }

    class Conversation extends Thread
    {
        protected Socket socket ;
        protected int nbrClient ;

        public Conversation(Socket socket , int num)
        {
            super();
            this.socket = socket;
            this.nbrClient = num;
        }
        //*****************brodcastMessage function
        public void brodcastMessage(String msg , Socket s , int num )
        {
            try {
                for (Conversation client:Clients )
                {
                    if(client.socket != s  )
                    {
                        if(client.nbrClient == num || num ==-1 )
                        {
                            PrintWriter pp = new PrintWriter(client.socket.getOutputStream() ,true);
                            pp.println(msg);
                        }
                    }


                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        public void run()
        {
            try {
                BufferedReader bf = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter    pw = new PrintWriter   (socket.getOutputStream(),true);
                String IP = socket.getRemoteSocketAddress().toString();

                System.out.println("connexion client number "+nbrClient+"ip = "+IP);
                pw.println("welcome you are the client number "+nbrClient);
                while(true)
                {
                    String req= bf.readLine();
                    if(req.contains("=>"))
                    {
                        String[] requestParams = req.split("=>");
                        if(requestParams.length==2);
                        String msg = requestParams[1];
                        int num_client = Integer.parseInt(requestParams[0]);
                        brodcastMessage(msg,socket,num_client);

                    }else
                    {
                        brodcastMessage(req,socket,-1);
                    }

                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}

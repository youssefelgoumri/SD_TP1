package com.example.main.blocking;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class SimpleClient {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost",1234);
        OutputStream os = s.getOutputStream();
        InputStream is = s.getInputStream();
        Scanner sc = new Scanner(System.in);
        System.out.println("Give a number : ");
        int nb = sc.nextInt();
        os.write(nb);
        int res = is.read();
        System.out.println("The number is : "+ res);
        s.close();
    }
}

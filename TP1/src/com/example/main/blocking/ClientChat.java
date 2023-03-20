package com.example.main.blocking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class ClientChat extends Application{

    PrintWriter pw ;
    public static void main(String[] arg) {
        launch(arg);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Client chat");
        BorderPane B1 = new BorderPane();

        Label     labebHost = new Label("Host : ");
        TextField text1     = new TextField("localhost");

        Label     labebPort = new Label("Port : ");
        TextField text2     = new TextField("4444");

        Button    buttonConnecter     = new Button("Connexion");

        HBox   hbox = new HBox(); hbox.setSpacing(10); hbox.setPadding(new Insets(10));
        hbox.setBackground(new Background(new BackgroundFill(Color.GREEN,null,null)));
        hbox.getChildren().addAll(labebHost,text1,labebPort,text2,buttonConnecter);



        VBox   vbox = new VBox(); vbox.setSpacing(10); vbox.setPadding(new Insets(10));
        ObservableList<String> listModel = FXCollections.observableArrayList();
        ListView<String> listView = new ListView<String>(listModel);
        vbox.getChildren().add(listView);

        Label labelMessage = new Label("Message");
        TextField textFieldMessage = new TextField();
        textFieldMessage.setPrefSize(300, 30);
        Button buttonEnvoyer = new Button("SEND");

        HBox hBox2 = new HBox();
        hBox2.setSpacing(10);
        hBox2.setPadding(new Insets(10));
        hBox2.getChildren().addAll(labelMessage, textFieldMessage, buttonEnvoyer);

        B1.setTop(hbox);
        B1.setCenter(vbox);
        B1.setBottom(hBox2);

        Scene scene = new Scene(B1,500,400);
        primaryStage.setScene(scene);
        primaryStage.show();

        buttonConnecter.setOnAction((evt)->{
            String host = text1.getText();
            int port = Integer.parseInt(text2.getText());
            try {
                Socket socket = new Socket(host, port);
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                pw = new PrintWriter(socket.getOutputStream(), true);
                new Thread(()->{
                    while(true) {
                        try {
                            String response = br.readLine();
                            Platform.runLater(()->{
                                listModel.add(response);
                            });
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }catch(IOException e) {
                e.printStackTrace();
            }

        });

        buttonEnvoyer.setOnAction((evt)->{
            String msg = textFieldMessage.getText();
            pw.println(msg);
        });
    }
}
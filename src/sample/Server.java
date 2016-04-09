package sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {
    private ServerSocket server;
    private int port;
    private writeOnInterface gui;

    public Server(writeOnInterface gui, int port) {
        this.port = port;
        this.gui = gui;

        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Socket cSocket;
        try {
            while((cSocket = server.accept()) != null) {
                InputStream inputStream = cSocket.getInputStream();
                InputStreamReader isReader = new InputStreamReader(inputStream);
                BufferedReader bReader = new BufferedReader(isReader);

                String line = bReader.readLine();

                if(line != null) {
                    gui.write(line);
                } else {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package sample;

import java.io.IOException;
import java.net.Socket;

public class Client extends Thread {
    private String user;
    private String message;
    private String host;
    private int port;


    public Client(String user, String message, String host, int port) {
        this.user = user;
        this.message = message;
        this.host = host;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            String wholeMsg = user + ": " + message;
            Socket socket = new Socket(host, port);
            //Gets output stream message in bytes
            socket.getOutputStream().write(wholeMsg.getBytes());
            socket.close();
        } catch (IOException e) {
            System.err.println("No connection found. Try to reconnect.");
        }
    }

}

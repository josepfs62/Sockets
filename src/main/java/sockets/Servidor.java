package sockets;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {
    private ServerSocket serverSocket;
    private Socket socket;
    private int port;
    private ArrayList<Connection> bufferConnection;
    private EventsListener eventsListener;

    public Servidor ( ArrayList<Connection> bufferConnection, EventsListener eventsListener) {
        this.bufferConnection=bufferConnection;
        this.eventsListener=eventsListener;
    }

    public void runServer(int port) {
        Thread serverThread = new Thread (new Runnable () {
            @Override
            public void run() {
                try {
                    serverSocket = new ServerSocket(port);
                    while(true) {
                        socket=serverSocket.accept();
                        Connection connection = new Connection(socket);
                        connection.addListener(eventsListener);
                        connection.triggerConnectionEvent();
                        bufferConnection.add(connection);
                    }
                }catch(Exception e) {
                    System.out.println("Error al runServer: "+e.getMessage());
                }
            }
        });
        serverThread.start();
    }
}
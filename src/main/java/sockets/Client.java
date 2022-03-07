package sockets;

import java.net.Socket;
import java.util.ArrayList;

public class Client {
    private ArrayList<Connection> bufferConnection;
    private EventsListener eventsListener;

    public Client (ArrayList<Connection> bufferConnection,EventsListener eventsListener) {
        this.bufferConnection=bufferConnection;
        this.eventsListener=eventsListener;
    }

    public String Connectar(String ip, int port) {
        String msgState;
        try {
            Socket socket = new Socket(ip,port);
            Connection connection = new Connection(socket);
            connection.addListener(eventsListener);
            connection.triggerConnectionEvent();
            bufferConnection.add(connection);
            msgState = "Connexió establerta amb: "+ip+":"+port;
        }catch (Exception e) {
            System.out.println("Error al connectar: "+ e.getMessage());
            msgState = "Error al connectar: "+ e.getMessage();
        }
        return msgState;
    }
}
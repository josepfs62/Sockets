package sockets;

import java.util.ArrayList;

public class Model {
    private Client client;
    private Servidor servidor;
    private ArrayList<Connection> bufferConnection = new ArrayList<Connection>();
    private EventsListener eventsListener;

    public Model (EventsListener eventsListener) {
        this.eventsListener=eventsListener;
    }

    public void iniciarServidor (int port) {
        servidor = new Servidor(bufferConnection, eventsListener);
        servidor.runServer(port);
    }

    public String establirConnexio (String ip, int port) {
        this.client = new Client(bufferConnection, eventsListener);
        String msgState = this.client.Connectar(ip, port);
        return msgState;
    }

    public Servidor getServidor() {
        return this.servidor;
    }

    public ArrayList<Connection> getBufferConnection (){
        return this.bufferConnection;
    }
}
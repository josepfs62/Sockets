package sockets;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import javax.swing.JButton;


public class Controlador implements ActionListener{
    //El controlador gestiona el pas d'informació de la vista gràfica al model de dades
    Vista vista;
    Model model;

    //Definir els mètodes que s'han d'executar quan s'activi un event
    public EventsListener eventsListener = new EventsListener() {
        @Override
        //Aquest event s'executarà quan es crei una nova Connexió, ja sigui
        //per part de la classe client com de la classe servidor.
        //Afegeix una nova pestanya i afegeix un listener al nou boto creat.
        public void onNewConnection(EventsConnection event) {
            vista.novaPestanya(event.getConnection().getIP());
            addListener(vista.llistaButtons.get(vista.llistaButtons.size()-1));
        }

        @Override
        //Quan enviam un missatge actualitzam les areas de text.
        public void onNewEnviarConnection(EventsConnection event, Message msg) {
            vista.llistaTextArea.get(model.getBufferConnection().indexOf(event.getConnection())).setText(vista.llistaTextArea.get(model.getBufferConnection().indexOf(event.getConnection())).getText()+"\n"+"Jo: "+msg.getMsg());
        }

        @Override
        //Quan rebem un missatge actualitzam les areas de text.
        public void onNewRebreConnection(EventsConnection event, Message msg) {
            vista.llistaTextArea.get(model.getBufferConnection().indexOf(event.getConnection())).setText(vista.llistaTextArea.get(model.getBufferConnection().indexOf(event.getConnection())).getText()+"\n"+event.getConnection().getIP()+": "+msg.getMsg());
        }

        @Override
        public void onNewErrorConnection(EventsConnection event) {
            Thread healthThread = new Thread (new Runnable () {
                @Override
                public void run () {
                    while (true) {
                        try {
                            Socket socket = new Socket (event.getConnection().getIP(),event.getConnection().getPort());
                            event.getConnection().setSocket(socket);
                            break;
                        } catch (IOException e) {}
                        try {
                            Thread.sleep(1 * 1000);
                        } catch (InterruptedException e) {}
                    }
                }
            });
            healthThread.start();
        }
    };

    public Controlador() {
        vista = new Vista();
        model = new Model(eventsListener);

        ////////////////////
        Scanner teclat = new Scanner (System.in);
        System.out.println("Port del servidor: ");
        int port = Integer.parseInt(teclat.nextLine());
        ////////////////////

        this.model.iniciarServidor(port);

        this.vista.connectarButton.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String msgState;
        //Quan és pitja el boto connectar de la pestanya servidor intentam crear connexió
        //Sí es un altre boto és perque volem enviar un missatge.
        if (e.getSource().equals(this.vista.connectarButton)) {
            //Intentam connectar amb la IP i el port que s'ha indicat a la pestanya servidor
            try {
                msgState=this.model.establirConnexio(this.vista.ip.getText(), Integer.parseInt(this.vista.port.getText()));
            }catch(Exception error) {
                msgState="Error amb la IP i el port: "+error.getMessage();
            }

            this.vista.comLog.setText(this.vista.comLog.getText()+"\n"+msgState);
        }else {
            enviarMsg(e);
        }
    }

    public void addListener(JButton button) {
        button.addActionListener(this);
    }

    public void enviarMsg(ActionEvent e) {
        Message message = new Message();
        message.setMsg(this.vista.llistaMsgs.get(this.vista.llistaButtons.indexOf(e.getSource())).getText());
        this.model.getBufferConnection().get(this.vista.llistaButtons.indexOf(e.getSource())).enviar(message);
        this.vista.llistaMsgs.get(this.vista.llistaButtons.indexOf(e.getSource())).setText("");
    }

}
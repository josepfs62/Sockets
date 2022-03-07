package sockets;

import java.util.ArrayList;

import javax.swing.*;

public class Vista extends JFrame{
    public JTabbedPane pestanyes;
    public JTextArea comLog;
    public JButton connectarButton;
    public JTextField ip;
    public JTextField port;

    public ArrayList<JTextArea> llistaTextArea = new ArrayList<JTextArea>();
    public ArrayList<JTextField> llistaMsgs = new ArrayList<JTextField>();
    public ArrayList<JButton> llistaButtons = new ArrayList<JButton>();
    public ArrayList<JPanel> llistaPanels = new ArrayList<JPanel>();

    public Vista() {
        //Instanciam atributs de classe
        pestanyes = new JTabbedPane();

        //Parametritzar el JFrame (finestra)
        setBounds(0,0,500,550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Cream la primera pestanya
        JPanel ServPane = new JPanel();
        ServPane.setLayout(null);

        comLog = new JTextArea(450,300);
        comLog.setBounds(10,50,450,300);
        comLog.setEditable(false);

        JScrollPane logScroll = new JScrollPane(comLog);
        logScroll.setBounds(10,50,450,300);
        ServPane.add(logScroll);

        connectarButton = new JButton("Connectar");
        connectarButton.setBounds(10,400,100,30);
        ServPane.add(connectarButton);

        ip = new JTextField();
        ip.setBounds(130,400,100,30);
        ServPane.add(ip);

        port = new JTextField();
        port.setBounds(250,400,50,30);
        ServPane.add(port);

        pestanyes.add("Servidor",ServPane);

        //Afegir pestanyes al contenidor de la finestra
        getContentPane().add(pestanyes);
        setVisible(true);
    }

    public void novaPestanya(String ip) {
        JPanel panel;
        JScrollPane scroll;
        JTextArea textArea;
        JTextField msg;
        JButton button;

        panel = new JPanel();
        panel.setLayout(null);

        textArea = new JTextArea(450,300);
        textArea.setBounds(10,50,450,300);
        textArea.setEditable(false);
        scroll = new JScrollPane(textArea);
        scroll.setBounds(10,50,450,300);
        panel.add(scroll);
        llistaTextArea.add(textArea);

        msg = new JTextField();
        msg.setBounds(10,400,370,30);
        panel.add(msg);
        llistaMsgs.add(msg);

        button = new JButton(">");
        button.setBounds(390,400,70,30);
        panel.add(button);
        llistaButtons.add(button);

        pestanyes.addTab(ip, panel);
        llistaPanels.add(panel);

    }
}
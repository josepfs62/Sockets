package sockets;

import java.util.EventListener;

//A la interficie definim quins metodes és podrán executar quan és disparin els events
interface EventsListener extends EventListener{
    public abstract void onNewConnection (EventsConnection event);
    public abstract void onNewEnviarConnection (EventsConnection event, Message msg);
    public abstract void onNewRebreConnection (EventsConnection event, Message msg);
    public abstract void onNewErrorConnection (EventsConnection event);
}
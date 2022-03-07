package sockets;

import java.io.Serializable;

public class Message implements Serializable {
    private String msg = new String();

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
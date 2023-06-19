package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class MensajeReceiver {
    private DatagramSocket socket;

    public MensajeReceiver(int puerto) throws SocketException {
        this.socket = new DatagramSocket(puerto);
    }

    public String recibirMensaje() throws IOException {
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        return new String(packet.getData(), 0, packet.getLength());
    }

    public DatagramSocket getSocket() {
        return socket;
    }
    
    public void cerrar() {
        if (socket != null) {
            socket.close();
        }
    }
}
package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;


public class MensajeSender {
    private DatagramSocket socket;

    public MensajeSender(DatagramSocket socket) {
        this.socket = socket;
    }

    public void enviarMensaje(String mensaje, InetAddress direccionCliente, int puertoCliente) throws IOException {
        byte[] mensajeBuffer = mensaje.getBytes();
        DatagramPacket mensajePacket = new DatagramPacket(mensajeBuffer, mensajeBuffer.length,
                direccionCliente, puertoCliente);
        socket.send(mensajePacket);
    }
    
    public void cerrar() {
        if (socket != null) {
            socket.close();
        }
    }
}
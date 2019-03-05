package com.demianchuk.chat;

import com.demianchuk.chat.util.ConsoleHelper;
import com.demianchuk.chat.util.Constants;

import java.net.*;
import java.io.*;

public class Chat {

    public static void main(String[] args) throws IOException {
        InetAddress group = InetAddress.getByName(Constants.MULTICAST_ADDRESS);
        MulticastSocket socket = new MulticastSocket(Constants.MULTICAST_PORT);

        ConsoleHelper.write("Enter your name");
        String userName = ConsoleHelper.read();

        socket.joinGroup(group);

        Thread thread = new Thread(() -> {
            while (!socket.isClosed()) {
                byte[] buffer = new byte[Constants.BUFFER_SIZE];
                DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, group, Constants.MULTICAST_PORT);
                try {
                    socket.receive(datagram);
                    String message = new String(buffer, 0, datagram.getLength(), "UTF-8");
                    if (!message.startsWith(userName)) {
                        ConsoleHelper.write(message);
                    }
                } catch (IOException e) {
                    ConsoleHelper.write("Socket is closed. Bye!");
                }
            }
        });

        thread.start();

        ConsoleHelper.write("Enter your message");
        while (true) {
            String message = ConsoleHelper.read();
            if (message.equalsIgnoreCase(Constants.TERMINATE)) {
                socket.leaveGroup(group);
                socket.close();
                return;
            }
            message = userName + ": " + message;
            byte[] buffer = message.getBytes();
            DatagramPacket datagram = new DatagramPacket(buffer, buffer.length, group, Constants.MULTICAST_PORT);
            socket.send(datagram);
        }
    }
}
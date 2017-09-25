package com.payjoy.integration.ip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient implements Runnable {

    public static final Logger logger = LoggerFactory.getLogger(TCPClient.class);

    public static int port;
    public static String host;
    public byte[] message;
    private Socket socket;
    private InputStream in;
    private OutputStream out;

    public static void main(String[] args) throws UnknownHostException,
            IOException {
        TCPClient client = new TCPClient();
        TCPClient.host = "192.168.0.101";
        TCPClient.port = 12345;
        client.socket = new Socket(host, port);

        byte[] msg = {'a', 'b', 'c', 'd'};
        client.message = msg;
        new Thread(client).start();

    }

    @Override
    public void run() {
        try {
            out = socket.getOutputStream();
            out.write(message);
            out.flush();
            in = socket.getInputStream();
            Thread.sleep(500);
            // 接收服务器的反馈
            BufferedReader bufferReader = new BufferedReader(
                    new InputStreamReader(in));
            String msg = "";
            for (int i = 0; i < 10; ++i) {
                msg = bufferReader.readLine();
                logger.debug(msg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
            }
        }

    }
}

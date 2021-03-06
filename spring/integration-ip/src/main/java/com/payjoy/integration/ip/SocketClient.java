/**
 *
 */
package com.payjoy.integration.ip;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class SocketClient {
    private static final Logger logger = LoggerFactory.getLogger(SocketClient.class);

    public static void main(String[] args) {
        Socket socket = null;
        BufferedReader bufferedReader = null;
        PrintWriter pw = null;
        try {
            // 客户端socket指定服务器的地址和端口号
            socket = new Socket("192.168.0.108", 12345);
            logger.debug("Socket=" + socket);
            bufferedReader = new BufferedReader(new InputStreamReader(
                    socket.getInputStream()));
            pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    socket.getOutputStream())));
            for (int i = 0; i < 10; i++) {
                byte[] msg = {'a', 'b', 'c'};
                pw.println(new String(msg) + i);
                pw.flush();
                String str = bufferedReader.readLine();
                logger.debug(str);
            }
            pw.println("\n");
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
//		finally {
//			try {
//				logger.debug("close......");
//				bufferedReader.close();
//				pw.close();
//				socket.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
    }

}

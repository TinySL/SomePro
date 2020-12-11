package com.midtest.midtes;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import static java.lang.Thread.sleep;

public class ClentTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        //System.exit(0);
        String mes = "303437323837373432ff8072e5766a80eaed2f94ec6ca0d2e02cbc6a3362";
        String info = "";
        Socket socket1 = ConnectToServerByTcp("127.0.0.1",9805,5,3);

        SockSend(socket1,mes,info);
        System.out.println("END");
      //  System.exit(0);
/*        //byte[] res = null;


        Socket socket  = new Socket("127.0.0.1",9806);
        //socket.connect(socketAddress, 5000); // 连接超时限制在5秒
        socket.setSoTimeout(1000 * 5);//设置读操作超时时间5到180秒

        InputStream is = null;
        OutputStream os = null;
        try {

            //socket = new Socket(url, port);
            os = socket.getOutputStream();
            //os.write(DataAsys.str2bytearray(mes));
            os.write(DataAsys.str2bytearray(mes));
           // os.flush();
            //socket.close();
            //sleep(1000);
            is = socket.getInputStream();
            //ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int count = 0;
            System.out.println("see"+is.available());
            while (true)
            {
                if(is.available() ==0)
                    break;
                count = is.read(buffer);
                //bos.write(buffer, 0, count);
                String sibuf = new String(buffer, "utf-8");
                info += sibuf;

            }

            //res = bos.toByteArray();

            os.close();
            is.close();
            socket.close();

        } catch (Exception ex) {
            try {

                if (is != null) {
                    is.close();
                }

            } catch (Exception e) {

            }
        }

        System.out.println("HIS: "+info);*/
    }


    public static Socket ConnectToServerByTcp(String serverIp, int serverPort, int timeOutSecond,
                                              int connectCS) {
        // 建立通讯连接
        Socket otherSocket = null;
        boolean connectOk = false;
        try {
            // 创建一个流套接字并将其连接到指定主机上的指定端口号
            otherSocket = new Socket();
            //System.out.println("serverIp " + serverIp + ", serverPort" + serverPort);
            SocketAddress socketAddress = new InetSocketAddress(serverIp, serverPort);
            // 接收超时限制在5到180秒，连接超时限制在5秒
            timeOutSecond = (timeOutSecond < 1 || timeOutSecond > 180) ? 1 : timeOutSecond;
            for (int i = 0; i < connectCS; i++) {
                try {
                    otherSocket.connect(socketAddress, 5000); // 连接超时限制在5秒
                    otherSocket.setSoTimeout(1000 * timeOutSecond);//设置读操作超时时间5到180秒
                    connectOk = true;
                    break;
                } catch (Exception e1) {
                    //log.error(e1);
                    //System.out.println(e1);
                }
            }
            if (!connectOk) {
                if (otherSocket != null)
                    otherSocket.close();
                otherSocket = null;
            }
            return otherSocket;
        } catch (Exception e) {
            //System.out.println(e);
            e.printStackTrace();
        }
        return otherSocket;

    }

    public static String SockSend(Socket socketS, String bufS,String bufR) throws InterruptedException {
        // 发送网络数据
        //boolean oK = false;
        //String sErr = "";
        sleep(300);
        DataOutputStream out = null;
        try {
            if (bufS.length() < 1)
                System.out.println("error1");
               // sErr = "数据长度[" + bufS.get_dataLen() + "]无效";
            // 向服务器端发送数据
            out = new DataOutputStream(socketS.getOutputStream());
            out.write(DataAsys.str2bytearray(bufS));
            //sleep(5000);
            //Thread.sleep(200);
            out.flush();
            //oK = true;
            //关闭客户端的输出流。相当于给流中加入一个结束标记-1.这个样子服务器的输入流的reaLine方法就会读到一个-1，然后结束readLIne方法
           // socketS.shutdownOutput();
        } catch (Exception e) {
            System.out.println(e);
            //oK = false;
            //sErr = e.toString();
            e.printStackTrace();
        }

        sleep(300);

        if (socketS.isClosed())
        {
            System.out.println("socket closed");
        }
        if (socketS.isInputShutdown())
        {
            System.out.println("input closed");
        }

        if (socketS.isOutputShutdown())
        {
            System.out.println("output closed");
        }



        //int getLen = 0;
        InputStream inputStream = null;
        DataInputStream bs = null;
        try {
            inputStream = socketS.getInputStream();
            bs = new DataInputStream(inputStream);
            //bufR.set_dataLen(0);
            byte[] data = new byte[4096];
            int totalBytesRcvd = 0;
            //getLen = bs.read(data);
            byte bytesRcvd;

            int len = -1;
            String sibuf = "";
            while (true) {
                if(inputStream.available() != 0){
                    len = bs.read(data);
                   // sibuf = new String(data);
                    sibuf = DataAsys.byteArrayToHexStr(data);
                }else
                    break;
                //totalBytesRcvd = bs.read(data,0,len);0
            }

/*            while((bytesRcvd = (byte) bs.read())!= -1){
                data[totalBytesRcvd] = (byte)bytesRcvd;
                totalBytesRcvd++;
            }*/
            bufR = sibuf.substring(0,len*2);
/*            if (sibuf.length() > 2) {
                System.out.println(sibuf);
            } else {
                //System.out.println("error2");
            }*/
            bs.close();
            inputStream.close();
            socketS.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return bufR;
        }

    }

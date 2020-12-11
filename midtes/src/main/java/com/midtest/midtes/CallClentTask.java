package com.midtest.midtes;

import java.io.*;
import java.net.*;
import java.util.concurrent.Callable;

import static java.lang.Thread.sleep;

public class CallClentTask implements Callable {
    public  String message;

    public CallClentTask(String inputmes){
        this.message =inputmes;
    }
    @Override

    /*
   public String call() throws Exception {
        String info = "";
        byte[] buff = new byte[4096];
        //1.创建客户端Socket，指定服务器地址和端口
        Socket socket = new Socket("192.168.1.134",9805);
       // Socket socket = new Socket("127.0.0.1",9805);
       // Socket socket = new Socket(InetAddress.getLocalHost(),9991);
        //2.获取输出流，向服务器发送消息
        //OutputStream os = socket.getOutputStream();  //字节输出流
        //PrintWriter pw = new PrintWriter(os);  //将输出流包装为打印流
        //pw.write(message);
        //pw.flush();
        //socket.shutdownOutput();
        socket.setOOBInline(true);
        //建立连接后获取输出流
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());

        // String uuid = UUID.randomUUID().toString();
        //String usermes ="push_event\r\n\r\n";
        //log.info("push", usermes);
        //System.out.println(message);
        //buff = str2bytearray(message);

        outputStream.write(DataAsys.str2bytearray(message));
        //outputStream.write(message.getBytes());
       // outputStream.flush();

        System.out.println("您的2客户端发送完成");
//        //3.获取输入流，并读取服务器端的响应信息
        //DataInputStream inputStream1 = new DataInputStream(socket.getInputStream());
        //String content = "";
        sleep(500);
        System.out.println("read seate"+inputStream.available());

        int i = 0;
        while (true)
        {

            System.out.println("read2");
            if(inputStream.available() != 0)
            {
                System.out.println("tes");
                inputStream.read(buff);
                String buffer = new String(buff, "utf-8");
                info += buffer;
                break;
            }
            i++;
            if(i>10)
                break;
        }

        //outputStream.close();


        //System.out.println("您的2"+ret);
        //info = buff.toString();
        //System.out.println(buff);
        //String buffer = ASCIIToConvert(buff);
        //String buffer = new String(buff,"ascii");
        //System.out.println(buff.toString());
        //inputStream1.close();
        //info += buffer;
        //InputStreamReader isr = new InputStreamReader(socket.getInputStream());
        //BufferedReader br = new BufferedReader(isr);

        //while((info=br.readLine())!=null){
            //System.out.println("我是客户端，服务器说："+info);
        //}
        //System.out.println("");



        //log.info("info: {}", content);
       // System.out.println(info);
        //pw.flush();
        //socket.shutdownOutput();
//        //3.获取输入流，并读取服务器端的响应信息
        //InputStream is = socket.getInputStream();
        //InputStreamReader isr = new InputStreamReader(is);
        //BufferedReader br = new BufferedReader(isr);
        //System.out.println("读取数据");
//
       //while((info=br.readLine())!=null){
        //    System.out.println("我是客户端，服务器说："+buff.toString());
      // }
        //4.关闭资源
        //br.close();
        //isr.close();
        //is.close();
        //pw.close();
        //os.close();
        //socket.close();
        return  info;
    }*/

    public String call() throws Exception {
        String info = "";
        Socket socket1 = ConnectToServerByTcp("127.0.0.1",9805,5,3);

        info = SockSend(socket1,message,info);

        return info;
    }
    public static Socket ConnectToServerByTcp(String serverIp, int serverPort, int timeOutSecond,
                                              int connectCS) {
        // 建立通讯连接
        Socket otherSocket = null;
        boolean connectOk = false;
        try {
            // 创建一个流套接字并将其连接到指定主机上的指定端口号
            otherSocket = new Socket();
            System.out.println("serverIp " + serverIp + ", serverPort" + serverPort);
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
                    System.out.println(e1);
                }
            }
            if (!connectOk) {
                if (otherSocket != null)
                    otherSocket.close();
                otherSocket = null;
            }
            return otherSocket;
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        return otherSocket;

    }

    public static  String SockSend(Socket socketS, String bufS,String bufR) throws InterruptedException {
        // 发送网络数据
        //boolean oK = false;
        //String sErr = "";
        sleep(500);
        DataOutputStream out = null;
        try {
            if (bufS.length() < 1)
                System.out.println("error1");
            // sErr = "数据长度[" + bufS.get_dataLen() + "]无效";
            // 向服务器端发送数据
            out = new DataOutputStream(socketS.getOutputStream());
            out.write(DataAsys.str2bytearray(bufS));
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

        sleep(1000);
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
                    sibuf = DataAsys.ASCIIToConvert(data);
                }else
                    break;
                //totalBytesRcvd = bs.read(data,0,len);0
            }

/*            while((bytesRcvd = (byte) bs.read())!= -1){
                data[totalBytesRcvd] = (byte)bytesRcvd;
                totalBytesRcvd++;
            }*/
            bufR += sibuf;
            if (sibuf.length() > 2) {
                System.out.println(sibuf);
            } else {
                System.out.println("error2");
            }
            bs.close();
            inputStream.close();
            socketS.close();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return bufR;
    }
/*    public Object call() throws Exception {
        byte[] res = null;
        String info = "";
        Socket socket ;
        InputStream is = null;
        OutputStream os = null;
        try {
             socket = new Socket("127.0.0.1",9805);
            //socket = new Socket(url, port);
            os = socket.getOutputStream();
            os.write(str2bytearray(message));
            //os.flush();
            sleep(500);
            is = socket.getInputStream();
            //ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            int count = 0;
            System.out.println("see"+is.available());
            while (true)
            {
                count = is.read(buffer);
                //bos.write(buffer, 0, count);
                if(is.available() ==0)
                    break;
            }
            String sibuf = new String(buffer, "utf-8");
            info += buffer;
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
        return info;
    }*/

/*    public Object call() throws Exception {
        String info = "";
        byte[] buff = new byte[4096];
        //1.创建客户端Socket，指定服务器地址和端口
        //Socket socket = new Socket("192.168.1.134",9805);
        //Socket socket = new Socket("127.0.0.1",9805);

        try {
            //1.建立客户端socket连接，指定服务器位置及端口
            //Socket socket = new Socket("192.168.1.134",9805);
            Socket socket =new Socket("localhost",9805);
            //2.得到socket读写流
            OutputStream os=socket.getOutputStream();
            PrintWriter pw=new PrintWriter(os);
            //输入流
            InputStream is=socket.getInputStream();
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            //3.利用流按照一定的操作，对socket进行读写操作
            //String info="用户名：Tom,用户密码：123456";
            pw.write(message);
            pw.flush();
            socket.shutdownOutput();
            //接收服务器的相应
            String reply=null;
            while(!((reply=br.readLine())==null)){
                System.out.println("接收服务器的信息："+reply);
            }
            //4.关闭资源
            br.close();
            is.close();
            pw.close();
            os.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  info;

    }*/

    public static void charclient(String outmse,Socket socket) throws IOException {

        String status=null;
        //与服务端建立连接

        //Socket socket = new Socket(host, port);
        socket.setOOBInline(true);
        //建立连接后获取输出流
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        // String uuid = UUID.randomUUID().toString();
        //String usermes ="push_event\r\n\r\n";
        //log.info("push", usermes);
        System.out.print(outmse);
        outputStream.write(outmse.getBytes());
        outputStream.flush();

        //DataInputStream inputStream1 = new DataInputStream(socket.getInputStream());
        String content = "";

        // while (true){
        byte[] buff = new byte[4096];
        inputStream.read(buff);
        String buffer = new String(buff, "utf-8");
        content += buffer;
        //log.info("info: {}", content);
        System.out.print(content);
        //File file = new File("json.json");
        //FileWriter fileWriter = new FileWriter(file);
        //fileWriter.write(content);
        //fileWriter.flush();


    }


}

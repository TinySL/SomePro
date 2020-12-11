package com.midtest.midtes;



import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Map;

import static com.midtest.midtes.XmlAys.getxml;
import static java.lang.Thread.sleep;

@Component
public class NIOServer {


    public void NIOServerR(){
        //OPout outstr = new OPout();
        ClentTest  clenca = new ClentTest();
        XmlAys masp = new XmlAys();
        Map mas = getxml();
        int serverport = Integer.parseInt((String)mas.get("serport"));
        String clientipadd = (String) mas.get("ipaddress");
        int clientport = Integer.parseInt((String)mas.get("clientport"));
        try {
            //创建ServerSocketChannel通道，绑定监听端口为8080
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(serverport));//8076
            //设置为非阻塞模式
            serverSocketChannel.configureBlocking(false);
            //注册选择器,设置选择器选择的操作类型
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            //创建处理器
            Handler handler = new Handler(4096);
            int iistime = 0;
            while (true) {
                if (iistime <1) {
                    System.out.println("服务器已启动等待连接请求......");
                    iistime++;
                }
                //等待请求，每次等待阻塞3s，超过时间则向下执行，若传入0或不传值，则在接收到请求前一直阻塞
                if (selector.select(1000) == 0) {
                    //System.out.println("等待请求超时......");
                    continue;
                }
                //System.out.println("-----处理请求-----");
                //获取待处理的选择键集合
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey selectionKey = keyIterator.next();
                    try {
                        //如果是连接请求，调用处理器的连接处理方法
                        if (selectionKey.isAcceptable()) {
                            handler.handleAccept(selectionKey);
                        }
                        //如果是读请求，调用对应的读方法
                        if (selectionKey.isReadable()) {
                            String readstr;
                            //byte[] sonoutstr = new byte[4096];
                            String sonoutstr ="";
                            Boolean isclose = false;
                            readstr = handler.handleRead(selectionKey);
                            //handler. handleRead(selectionKey).
                            //System.out.println(readstr);
                            if (readstr != null){
                                sleep(1000);
       /*                         if (readstr.equals("exit"))
                                {
                                    isclose =true;
                                }*/
                                //String sds = "303437323837373432ff8072e5766a80eaed2f94ec6ca0d2e02cbc6a3362d1d929629bb403d8fd3b582580c6da2e52b0813290ca13971d3fc763b01b0f285ea0385105850048dfc47663b01b0f285ea038ea4d5a8632e701760f1132b15461d322d9e4bf9c582116c2d586841c228947ac08c0eab51be4d501a7934ce33b9c979757a3df0c13a9436097766ad36129456e47f15e907fdf2bdcd67b6691fa238b516864e55122335572731175e706c139a85e4eddefab1dc429cfd02f9f106ac9fe1afeb7d764e5971c50292a52b380459121b8d21c834f1eb6957498667aab65e23c20638db99ce848d57389b6cf4f3632ae1a15c81103ca47613fdc7a12b8836eeba0edee607f5295fd00bb9e7aa32bd9914de642816c05ecc215970df27660ce54463f7c0ce671a6a33b5afc306b1c8bf676466d99f79aa7bff705d18785a23eee02066e7ef8028e8dcbe0ea784d87f29153bb789cf069dc698f5b35e02aba3c99dfaeebae35ee6aa433c498b26d262ddf5dff3323ac5f78d53ae051500a4a4aa4185763fd02ea398498bbe6b29557e89e4be8d400616696c317c9c9a5ad7714cbe8c8a25720b3d8f67993b1304449ed001870d8ddd23d3b1edcee249be7aef1c49dc71512653eda17fbaf615344bee0165b2c0d5c843fea454545";
                                //Socket socket1 = clenca.ConnectToServerByTcp("127.0.0.1",9805,5,3);
                                //Socket socket1 = clenca.ConnectToServerByTcp("192.168.1.134",9805,5,3);
                                Socket socket1 = clenca.ConnectToServerByTcp(clientipadd,clientport,5,3);

                                if(socket1 ==null)
                                {
                                    System.out.println("远程服务器未启动！");
                                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                                    socketChannel.close();
                                }else {
                                    sonoutstr = clenca.SockSend(socket1, readstr, sonoutstr);
                                    //sonoutstr = outstr.newsome(readstr);
                                    // sonoutstr = clenca.getoutstr(readstr);
                                    //System.out.println("输出数据：" + sonoutstr);
                                    handler.handleWrite(selectionKey, DataAsys.str2bytearray(sonoutstr), isclose);
                                }
                            }
                        }
                    } catch (IOException e) {
                        keyIterator.remove();
                        continue;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //处理完毕从待处理集合移除该选择键
                keyIterator.remove();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}

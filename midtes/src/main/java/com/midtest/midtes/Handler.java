package com.midtest.midtes;

import org.apache.tomcat.util.buf.HexUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


public class Handler {
    private int bufferSize = 4096; //缓冲器容量
    private String localCharset = "UTF-8"; //编码格式

    public Handler(){}
    public Handler(int bufferSize){
        this(bufferSize,null);
    }
    public Handler(String localCharset){
        this(-1,localCharset);
    }
    public Handler(int bufferSize,String localCharset){
        if(bufferSize > 0){
            this.bufferSize = bufferSize;
        }
        if(localCharset != null){
            this.localCharset = localCharset;
        }
    }
    /*
    连接请求处理方法
    */
    public void handleAccept(SelectionKey selectionKey) throws IOException {
        //通过选择器键获取服务器套接字通道，通过accept()方法获取套接字通道连接
        SocketChannel socketChannel = ((ServerSocketChannel)selectionKey.channel()).accept();
        //设置套接字通道为非阻塞模式
        socketChannel.configureBlocking(false);
        //为套接字通道注册选择器，该选择器为服务器套接字通道的选择器，即选择到该SocketChannel的选择器
        //设置选择器关心请求为读操作，设置数据读取的缓冲器容量为处理器初始化时候的缓冲器容量
        socketChannel.register(selectionKey.selector(),SelectionKey.OP_READ, ByteBuffer.allocate(bufferSize));
    }

    public String handleRead(SelectionKey selectionKey) throws IOException {
        String receivedRequestData = null;
        //获取套接字通道
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        //获取缓冲器并进行重置,selectionKey.attachment()为获取选择器键的附加对象
        ByteBuffer byteBuffer = (ByteBuffer)selectionKey.attachment();
        //CharBuffer charBuffer = byteBuffer.asCharBuffer();
        byteBuffer.clear();
        //charBuffer.clear();
        //没有内容则关闭通道
        if(socketChannel.isConnected()){
            if (socketChannel.read(byteBuffer) == -1) {
                //if (socketChannel.read(charBuffer) == -1) {
                socketChannel.close();
            } else {
                //将缓冲器转换为读状态
                byteBuffer.flip();
                //charBuffer.flip();
                //将缓冲器中接收到的值按localCharset格式编码保存
                byte[] bufse = new byte[4096];
                int length = 0;
    /*            int i =0;
               while (socketChannel.read()!=-1){
                    int lens = socketChannel.read(bufse);
                    i++;
                }*/
                //charBuffer = byteBuffer.asCharBuffer();
                //receivedRequestData = charBuffer.toString();
                int lens = socketChannel.read(byteBuffer);
                // byteBuffer.
                length = byteBuffer.limit();
                receivedRequestData = HexUtils.toHexString(byteBuffer.array());

                //length = receivedRequestData.indexOf("00");
                receivedRequestData = receivedRequestData.substring(0, length * 2);

                //  receivedRequestData = Charset.forName(localCharset).newDecoder().decode(byteBuffer).toString();
               // System.out.println("接收到客户端的请求数据：" + receivedRequestData);

                //返回响应数据给客户端
                // responseData = "已接收到你的请求数据，响应数据为：(响应数据)";
                // byteBuffer = ByteBuffer.wrap(responseData.getBytes(localCharset));
                // socketChannel.write(byteBuffer);
                //关闭通道
                // socketChannel.close();
            }
        }else{
            socketChannel.close();
            return "";
        }
        byteBuffer.flip();
        return  receivedRequestData;
    }

    //public void handleWrite(SelectionKey selectionKey,String message,Boolean isclose) throws IOException {
    public void handleWrite(SelectionKey selectionKey,byte[] message,Boolean isclose) throws IOException {
        //System.out.println("开始返回数据");
        //String responseData = null;
        //获取套接字通道
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        //获取缓冲器并进行重置,selectionKey.attachment()为获取选择器键的附加对象
        ByteBuffer byteBuffer = (ByteBuffer)selectionKey.attachment();
        CharBuffer charBuffer = byteBuffer.asCharBuffer();
        byteBuffer.clear();
        charBuffer.clear();
        //System.out.println("开始返回数据:"+ message);


        //没有内容则关闭通道
        if (socketChannel.read(byteBuffer) == -1) {
            socketChannel.close();
        } else {
            //将缓冲器转换为写状态
            //byteBuffer.flip();

            //将缓冲器中接收到的值按localCharset格式编码保存
            //String receivedRequestData = Charset.forName(localCharset).newDecoder().decode(byteBuffer).toString();
            //System.out.println("接收到客户端的请求数据："+receivedRequestData);

            //返回响应数据给客户端
            //responseData = "已接收到你的请求数据，响应数据为：(响应数据)";
            //responseData = responseData + message;
            //byteBuffer = ByteBuffer.wrap(message.getBytes(localCharset));
            byteBuffer = ByteBuffer.wrap(message);
            charBuffer =byteBuffer.asCharBuffer();
            socketChannel.write(byteBuffer);
            //socketChannel.write(charBuffer);
            if (isclose){
                //关闭通道
                socketChannel.close();
            }

        }

        //return  responseData;
    }

}

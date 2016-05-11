package test;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sun.misc.Cleaner;

public class Server {
	public static final int PORT = 8081;//监听的端口号   
	public static int i=0;
	
/*
 * 定义socket和ip的map，存于服务端，用户配对与查询
 * 当生成二维码和扫描二维码时，需要将两个map中的旧数据清除，保证pPair中的key和value应该都是不重复的
 * 当互动结束时，需要清除这两个map中的数据，以免内存泄露
 * 异常结束时，如何清除内存中的数据
 * */
	public static Map<String,Socket> socketMap=new HashMap<String,Socket>();//存放ip与socket的map
	public static Map<String,String> ipPair=new HashMap<String,String>();//存放配对的两组ip。
	
	
    public static void main(String[] args) {  
        System.out.println("服务器启动...\n");  
        Server server = new Server();  
        server.init();  
    }  
  
    public void init() {  
        try {  
            ServerSocket serverSocket = new ServerSocket(PORT);  
            int i=0;
            while (true) {  
            	i++;
//            	System.out.println(i);
//            	System.out.println("当前线程数"+Thread.activeCount());
                // 一旦有堵塞, 则表示服务器与客户端获得了连接  
                Socket client = serverSocket.accept();  
                String ip= client.getInetAddress().getHostAddress().toString();
                if(socketMap.get(ip)==null){ 
                	socketMap.put(ip, client);
                }
                // 处理这次连接  
                new HandlerThread(client);  
            }  
        } catch (Exception e) {  
            System.out.println("服务器异常: " + e.getMessage());  
        }  
    }  
  
    private class HandlerThread implements Runnable {
        private Socket socket;  
        public HandlerThread(Socket client) {  
            socket = client;  
            new Thread(this).start();  
        }  
  
        public void run() {  
        	System.out.println("start");
            try {  
                // 读取客户端数据  
                DataInputStream input =new DataInputStream(socket.getInputStream());
                String clientInfo = input.readUTF();//这里要注意和客户端输出流的写方法对应,否则会抛 EOFException
                // 处理客户端数据  
                System.out.println("客户端发过来的内容:" + clientInfo); 
                //生成二维码请求
                if(clientInfo.startsWith("code:")){
                	String ip=clientInfo.split(":")[1];
                	DataOutputStream out=new DataOutputStream(socket.getOutputStream());
                	System.out.println("返回二维码给客户端");
                	out.writeUTF("二维码为:www.xxxx.com/"+ip);
                	out.close();
                	
                	//对方扫描了二维码
                }else if(clientInfo.startsWith("bind:")){
                	String oneIp=clientInfo.split(":")[1].split("\\|")[0];
                	String otherIp=clientInfo.split(":")[1].split("\\|")[1];
                	//将两者ip进行绑定
                	ipPair.remove(oneIp);
                	ipPair.put(oneIp, otherIp);
                	//往两个客户端发送消息
//                	socketMap.get(oneIp).connect(new InetSocketAddress("192.168.5.222", 8081));
                	 DataOutputStream out1 = new DataOutputStream(socketMap.get(oneIp).getOutputStream());  //被扫二维码的客户端
                	  out1.writeUTF("游戏开始");
                	  out1.close();
                	  DataOutputStream out2 = new DataOutputStream(socket.getOutputStream());  //扫二维码的客户端
                	  out2.writeUTF("游戏开始");
                	  out2.close();
                	
                }
                input.close();
  
            } catch (Exception e) {  
            	e.printStackTrace();
                System.out.println("服务器 run 异常: " + e.getMessage());  
            } finally {  
                if (socket != null) {  
                    try {  
                        socket.close();  
                    } catch (Exception e) {  
                        socket = null;  
                        System.out.println("服务端 finally 异常:" + e.getMessage());  
                    }  
                }  
            } 
        }  
    }  
}  
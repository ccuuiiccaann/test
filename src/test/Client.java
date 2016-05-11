package test;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	
	public static final String IP_ADDR = "192.168.5.105";//服务器地址 
	public static final int PORT = 8081;//服务器端口号  
	
    public static void main(String[] args) {
		
        System.out.println("客户端启动...");  
        System.out.println("当接收到服务器端字符为 \"OK\" 的时候, 客户端将终止\n"); 
        while (true) {  
        	Socket socket = null;
        	try {
        		InetAddress addr = InetAddress.getLocalHost();
        		String ip=	addr.getHostAddress().toString();
//        		System.out.println(ip);
        		//创建一个流套接字并将其连接到指定主机上的指定端口号
	        	socket = new Socket(ip, PORT);  
	        	
	          
	              
	            //读取服务器端数据  
	            DataInputStream input = new DataInputStream(socket.getInputStream());  
	            String ret = input.readUTF();   
	            System.out.println("服务器端返回过来的是: " + ret);  
	            
	          //向服务器端发送数据  
	            DataOutputStream out = new DataOutputStream(socket.getOutputStream()); 
//	            System.out.print("请输入: \n");  
//	            String str = new BufferedReader(new InputStreamReader(System.in)).readLine();  
//	            out.writeUTF(str);  
	            out.writeUTF("client send");
	            
	           
	              
	            
	            // 如接收到 "OK" 则断开连接  
//	            if ("OK".equals(ret)) {  
//	                System.out.println("客户端将关闭连接");  
//	                Thread.sleep(500);  
//	                break;  
//	            }  
	            
	            out.close();
	            input.close();
        	} catch (Exception e) {
        		System.out.println("客户端异常:" + e.getMessage()); 
        	} finally {
        		if (socket != null) {
        			try {
						socket.close();
					} catch (IOException e) {
						socket = null; 
						System.out.println("客户端 finally 异常:" + e.getMessage()); 
					}
        		}
        	}
        }  
    }  
}  
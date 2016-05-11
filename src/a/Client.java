package a;

import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;

public class Client {
	public static final String IP_ADDR = "192.168.5.222";//服务器地址 
	public static final int PORT = 8081;//服务器端口号  
	private JFrame frame;
	private static Socket socket;
	public static void main(String[] args) {
		
					Client window = new Client();
					window.frame.setVisible(true);
					try {
						socket = new Socket(IP_ADDR, PORT);
					} catch (Exception e) {
						e.printStackTrace();
					} 
					while(true){
			        		//创建一个流套接字并将其连接到指定主机上的指定端口号
				        	try {
					            //读取服务器端数据  
					            DataInputStream input = new DataInputStream(socket.getInputStream());  
					            String ret = input.readUTF()+"";   
					            System.out.println("服务器端返回过来的是: " + ret);  
					            input.close();
							} catch (Exception e) {
								e.printStackTrace();
							} 
					}
				
	}

	public Client() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JTextArea textArea = new JTextArea();
		textArea.setBounds(194, 27, 197, 39);
		frame.getContentPane().add(textArea);
		
		JButton button = new JButton("生成二维码");
		
		try {
			socket = new Socket(IP_ADDR, PORT);
		} catch (Exception e1) {
			e1.printStackTrace();
		} 
		button.addMouseListener(new MouseAdapter() {
			

			/* 
			 * 生成二维码
			 */
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String code="";
				try {
					InetAddress addr;
					addr = InetAddress.getLocalHost();
					String ip=	addr.getHostAddress().toString();
					code="code:"+ip;
					System.out.println("发送生成二维码的请求是:"+code);
					socket = new Socket(IP_ADDR, PORT);
					 DataOutputStream out = new DataOutputStream(socket.getOutputStream()); 
					 out.writeUTF(code);
					  DataInputStream input = new DataInputStream(socket.getInputStream());  
			            String ret = input.readUTF()+"";   
			            System.out.println("服务器端返回过来的是: " + ret);  
			            out.close();
			            input.close();
			            socket.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		button.setBounds(72, 27, 93, 23);
		frame.getContentPane().add(button);
		
		JButton btnPlay = new JButton("play");
		btnPlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnPlay.setBounds(112, 115, 93, 23);
		frame.getContentPane().add(btnPlay);
		
		JButton endGameButton = new JButton("结束游戏");
		endGameButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println("结束游戏");
			}
		});
		endGameButton.setBounds(250, 115, 93, 23);
		frame.getContentPane().add(endGameButton);
		
		
	}
}

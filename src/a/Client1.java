package a;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client1 {

	private JFrame frame;
	public static final String IP_ADDR = "192.168.5.222";//服务器地址 
	public static final int PORT = 8081;//服务器端口号  
	private static Socket socket;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
					Client1 window = new Client1();
					window.frame.setVisible(true);
					while(true){
		        		//创建一个流套接字并将其连接到指定主机上的指定端口号
							try {
								socket = new Socket(IP_ADDR, PORT);
								//读取服务器端数据  
								DataInputStream input = new DataInputStream(socket.getInputStream());  
								String ret = input.readUTF();   
								input.close();
								System.out.println("服务器端返回过来的是: " + ret);  
							} catch ( Exception e) {
								e.printStackTrace();
							}
					}
				
	}

	/**
	 * Create the application.
	 */
	public Client1() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		try {
			socket=new Socket(IP_ADDR, PORT);
		} catch ( Exception e2) {
			e2.printStackTrace();
		} 
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(38, 10, 252, 31);
		frame.getContentPane().add(textArea);
		
		JButton button = new JButton("扫描二维码");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					//扫描二维码的时候，二维码中应包含对方的ip。此处固定为一个ip，用来模拟获取对方ip的操作
					String otherIp="192.168.5.222";//对方ip
					InetAddress addr = InetAddress.getLocalHost();
					String ip=	addr.getHostAddress().toString();//本机ip。
					socket=new Socket(IP_ADDR, PORT);
					DataOutputStream out=new DataOutputStream(socket.getOutputStream());
					String bind="bind:"+otherIp+"|"+ip;
					System.out.println("扫描二维码，发送"+bind);
					out.writeUTF(bind);
					out.close();
					socket.close();
				} catch ( Exception e1) {
					e1.printStackTrace();
				}
				
			}
		});
		button.setBounds(308, 10, 93, 23);
		frame.getContentPane().add(button);
		
		JButton btnPlay = new JButton("play");
		btnPlay.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		btnPlay.setBounds(116, 92, 93, 23);
		frame.getContentPane().add(btnPlay);
		
		JButton button_1 = new JButton("结束游戏");
		button_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
		button_1.setBounds(278, 92, 93, 23);
		frame.getContentPane().add(button_1);
	}
}

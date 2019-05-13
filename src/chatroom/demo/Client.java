package chatroom.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 该类实现客户端的一个socket：ip+端口
 * 
 * @author goodwell
 *
 */
public class Client {
	private static String IP = "127.0.0.1";// 服务器的IP地址
	private int PORT = 9999;// 服务器的处理客户端socket的端口号
	private Socket socket;// 客户端的socket

	public Client() {
		try {
			socket = new Socket(IP, PORT);// 新建一个客户端的套接字，即本地的端口，和服务器进行交互
			System.out.println("客户端已启动并连接上" + socket.getInetAddress() + "-" + socket.getPort());
		} catch (UnknownHostException e) {
			System.out.println("服务器的IP地址有误");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 该方法实现给客户端和服务器进行交互，并可以对服务器发送信息，进而实现向其他客户端发送信息，即聊天群 其中发送和接受信息利用线程处理
	 * 
	 * @return
	 */
	public void clientchat() {
		InputStream in = null;
		InputStreamReader reader = null;
		BufferedReader br = null;
		OutputStream out = null;
		OutputStreamWriter writer = null;
		PrintWriter pw = null;
		try {
			in = socket.getInputStream();// 客户端的输入流，对于服务器来说，是输出，接收信息
			reader = new InputStreamReader(in); // 把字节流转化为字符流
			br = new BufferedReader(reader);// 将字符输入流包装一下，方便读取信息
			out = socket.getOutputStream();// 客户端的输出流，对于服务器来说，是输入流,发送信息
			writer = new OutputStreamWriter(out, "utf-8");
			pw = new PrintWriter(writer, true);// 把字符输出流包装一下，方便发送信息
			new Thread(new SendMeg(pw)).start();// 创建并实现发送信息的对象和方法
			new Thread(new ReciMeg(br)).start();// 创建并实现接受信息的对象和方法
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static String getIP() {
		return IP;
	}

	public static void setIP(String iP) {
		IP = iP;
	}

	public int getPORT() {
		return PORT;
	}

	public void setPORT(int pORT) {
		PORT = pORT;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public static void main(String[] args) {
		new Client().clientchat();// 新开启一个客户端
	}
}

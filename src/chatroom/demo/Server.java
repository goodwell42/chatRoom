package chatroom.demo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用于接受客户端请求的服务器
 * 
 * @author goodwell
 *
 */
public class Server {
	private static int PORT = 9999;// 服务器端处理客户端的监听端口
	private ServerSocket server;// 监听器对象，当监听到一个客户端请求的时候，返回一个本地的socket进行处理
	// return a IP+PORT:Socket
	public static List<Socket> clientsockets;

	public Server() {
		try {
			this.server = new ServerSocket(PORT);// 实例化一个监听器对象，实现对来自客户端请求的处理
			System.out.println("服务器已启动...." + PORT);
			clientsockets = new ArrayList<>();// 实例化一个顺序表，用于存储与客户端进行交互的套接字，可以理解为存储用户
		} catch (IOException e) {
			System.out.println("端口号被占用无法实例化");
			e.printStackTrace();
		}
	}

	/**
	 * 用于处理客户端的请求
	 */
	public void acceptsocket() {
		Socket soc = null;
		ExecutorService threadpool = Executors.newFixedThreadPool(100);// 创建一个处理与每一个客户端进行交互的线程池
		while (true) {
			try {
				soc = server.accept();// 死循环实时监听，当监听到客户端的请求的时候，返回一个服务器的socket对象并和客户端进行交互
				System.out.println(soc.getInetAddress() + "-" + soc.getPort() + "已进入聊天室...");
				clientsockets.add(soc);
				threadpool.submit(new cilentthread(soc));// 创建一个处理每个客户端任务的线程，并抛进线程池和其他线程同步运行
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static int getPORT() {
		return PORT;
	}

	public static void setPORT(int pORT) {
		PORT = pORT;
	}

	public ServerSocket getServer() {
		return server;
	}

	public void setServer(ServerSocket server) {
		this.server = server;
	}

	public List<Socket> getClientsockets() {
		return clientsockets;
	}

	public static void main(String[] args) {
		new Server().acceptsocket();// 新建一个服务器
	}

}

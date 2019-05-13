package chatroom.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 * 服务器端用于读取某个客户端发送来的信息并群发，基于线程机制
 * 
 * @author goodwell
 *
 */
public class cilentthread implements Runnable {
	List<Socket> clientsockets = Server.clientsockets;// 实时获取与每个客户端进行交互的socket集合
	private Socket soc;// 服务器中的一个与客户端进行交互的套接字

	public cilentthread(Socket soc) {
		this.soc = soc;
	}

	/**
	 * 多线程机制实现实时地接收一个客户端发来的信息并实时的发送给其他客户端
	 */
	@Override
	public void run() {
		InputStreamReader in = null;// 字符输入流，接收来自客户端的信息
		BufferedReader br = null;// 包装类
		OutputStreamWriter out = null;
		PrintWriter pw = null;
		String recimeg = null;
		try {
			in = new InputStreamReader(soc.getInputStream(), "utf-8");// 接收输入流并转化为字符输入流
			System.out.println("已启动2");
			br = new BufferedReader(in);// 包装
			while (true) {
				if ((recimeg = br.readLine()) != null) {
					for (Socket clientsoc : clientsockets) {
						out = new OutputStreamWriter(clientsoc.getOutputStream(), "utf-8");// 遍历，对每一个客户端发送这个客户端发送的信息
						pw = new PrintWriter(out, true);
						pw.println(soc.getInetAddress() + "-" + soc.getPort() + "说:" + recimeg);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Socket getSoc() {
		return soc;
	}

	public void setSoc(Socket soc) {
		this.soc = soc;
	}

}

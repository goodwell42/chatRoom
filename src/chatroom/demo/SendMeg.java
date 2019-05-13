package chatroom.demo;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 该类实例化一个输出流对象，实现socket的发送信息的功能，基于线程机制，可以达到同时收发信息，底层为抢占式资源
 * 
 * @author goodwell
 *
 */
public class SendMeg implements Runnable {
	private PrintWriter pw;// Socket的输出流对象

	public SendMeg(PrintWriter pw) {
		this.pw = pw;
	}

	/**
	 * 基于线程的发送信息run方法
	 */
	@Override
	public void run() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		System.out.println("已启动");
		String meg = null;
		while (true) {
			if ((meg = sc.nextLine()) != null) {
				pw.println(meg);// 发送信息
				meg = null;
			}
		}
	}

	public PrintWriter getPw() {
		return pw;
	}

	public void setPw(PrintWriter pw) {
		this.pw = pw;
	}

}

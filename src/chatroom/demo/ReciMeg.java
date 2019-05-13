package chatroom.demo;

import java.io.BufferedReader;
import java.io.IOException;

public class ReciMeg implements Runnable {
	private BufferedReader br;// 客户端用于接收信息的输入流

	public ReciMeg(BufferedReader br) {
		super();
		this.br = br;
	}

	/**
	 * 基于线程实现接收其他客户信息的功能，基于线程实现和发送信息的同步，底层是抢占式资源
	 */
	@Override
	public void run() {
		String recimeg = null;
		while (true) {
			try {
				if ((recimeg = br.readLine()) != null) {
					System.out.println(recimeg);// 当接收到的信息不为空的时候，将信息呈现给用户
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public BufferedReader getBr() {
		return br;
	}

	public void setBr(BufferedReader br) {
		this.br = br;
	}
}

package com.kentsong.socket;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {

	public static final int SOCKET_PORT = 8765;

	public void listenRequest() {
		ServerSocket serverSocket = null;
		try {
			serverSocket = new ServerSocket(SOCKET_PORT);
			System.out.println("伺服器已啟動 ! Lintening Port:" + SOCKET_PORT);

			while (true) {
				Socket socket = serverSocket.accept();// 等待連線進來才會往下執行
				new RequestThread(socket).run();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


	/**
	 * 處理Client端的Request執行續。
	 */
	class RequestThread implements Runnable {
		private Socket clientSocket;

		public RequestThread(Socket clientSocket) {
			this.clientSocket = clientSocket;
		}

		@Override
		public void run() {
			System.out.printf("有%s連線進來!\n", clientSocket.getRemoteSocketAddress());
			BufferedInputStream input = null;
			DataOutputStream output = null;

			try {
				input = new BufferedInputStream(clientSocket.getInputStream());
				byte[] b = new byte[1024];
				String data = "";
				int length;
				while ((length = input.read(b)) > 0) {// <=0的話就是結束了
					data += new String(b, 0, length);
				}

				System.out.println("取得的值:" + data);
				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (input != null)
						input.close();
					if (output != null)
						output.close();
					if (this.clientSocket != null && !this.clientSocket.isClosed())
						this.clientSocket.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SocketServer server = new SocketServer();
		server.listenRequest();
	}

}

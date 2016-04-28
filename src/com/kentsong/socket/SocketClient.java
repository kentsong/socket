package com.kentsong.socket;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketClient {

	private static String host = "127.0.0.1"; // �s�uIP
	private static int port = 8765;
	private static Socket socket = null;

	public static void main(String[] args) {

		try {
			socket = new Socket(host, port);
			System.out.println("�إ߳s�u " + host + ":" + port);
			BufferedOutputStream out = null;
			try {
				out = new BufferedOutputStream(socket.getOutputStream());
				out.write("I'm kent Test".getBytes());
				out.flush();
				System.out.println("�ǰe��Ƨ���");

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
					System.out.println("�����s�u");
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}
}

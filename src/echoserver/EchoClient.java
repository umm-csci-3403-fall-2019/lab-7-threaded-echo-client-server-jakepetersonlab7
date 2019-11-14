package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClient {
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException {
		EchoClient client = new EchoClient();
		client.start();
	}

	private void start() throws IOException {
		Socket socket = new Socket("localhost", PORT_NUMBER);
		InputStream socketInputStream = socket.getInputStream();
		OutputStream socketOutputStream = socket.getOutputStream();

		// Put your code here.
		Thread input = new Thread(){

			public void run(){
				try{
					int read;
					while ((read = System.in.read())!=-1){
						socketOutputStream.write(read);
					}
					socketOutputStream.flush();
					socket.shutdownOutput();
				}catch (IOException e){
					e.printStackTrace();
				}
			}


		};
	input.start();

	Thread output = new Thread(){
		public void run(){
			try{
				int write;
				while ((write = socketInputStream.read()) !=-1){
					System.out.write(write);
				}
				System.out.flush();
				socket.close();
			}catch (IOException e){
				e.printStackTrace();
			}
		}
	};
		output.start();
	}
}

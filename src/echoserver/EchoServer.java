package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	
	// REPLACE WITH PORT PROVIDED BY THE INSTRUCTOR
	public static final int PORT_NUMBER = 6013;

	public static void main(String[] args) throws IOException, InterruptedException {
		EchoServer server = new EchoServer();
		server.start();
	}

	private void start() throws IOException, InterruptedException {
		ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);
		while (true) {
			Socket socket = serverSocket.accept();

			// Put your code here.
			// This should do very little, essentially:
			//   * Construct an instance of your runnable class
			//   * Construct a Thread with your runnable
			//      * Or use a thread pool
			//   * Start that thread
			InputStream input = socket.getInputStream();
			OutputStream output = socket.getOutputStream();

			Thread t = new Thread (new ServerThreader(input, output, socket));
			t.start();
		}
	}
}

class ServerThreader implements Runnable{
	private InputStream input;
	private OutputStream output;
	private  Socket socket;

	public ServerThreader (InputStream input, OutputStream output, Socket socket){
		this.input =  input;
		this.output = output;
		this.socket = socket;
	}

	public void run(){
		try{
			int i;
			while((i = input.read()) != -1){
				output.write(i);

			}
			output.flush();
			socket.shutdownOutput();
		}catch(IOException e){
			e.printStackTrace();
		}

	}



}
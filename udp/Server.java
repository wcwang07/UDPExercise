package udp;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class Server extends Thread {
	
	/* Server information */
	private int port;
	private DatagramSocket serverSocket;
	private Thread runningThread;
	private boolean running;
	private String filePath;
	private String IP;
	
	public Server(String recvPort, String filePath) {
		this.port = Integer.parseInt(recvPort);
		
	}
	public void run() {
		 synchronized(this){
			 this.runningThread = Thread.currentThread();
		 }
		 try {
			this.serverSocket = new DatagramSocket(this.port);
			this.serverSocket.setReceiveBufferSize(1000);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("Cannot open port", e);
		}
		while(running) {
			DatagramSocket clientSocket = null;
			byte[] buffer = new byte[1024];
			DatagramPacket dgpacket = new DatagramPacket(buffer, buffer.length);
			
			try{
				serverSocket.receive(dgpacket);
			}
			catch(IOException e) {
				if(!this.running) {
					System.out.println("Server Stopped.") ;
                    return;
				}
				e.printStackTrace();
			}
			new Thread(
	                new WorkerRunnable(clientSocket, filePath, port, IP)).start();
		
		}
		
		 
	}
	public static void main(String[] args) {
		
		String recvPort;
		
		String filePath;

		// Get the parameters
		if (args.length < 2) {
			System.err.println("Arguments required: server name/IP, recv port, message count");
			System.exit(-1);
		}

		recvPort = args[0];
		filePath = args[1];
		
		// TO-DO: Construct UDP Server class and try to send messages
		System.out.println("Constructing udp server");
		new Server(recvPort, filePath).start();
		System.out.println("Server started...");
		
	}
	
}

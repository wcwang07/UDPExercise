import java.io.File;
import java.io.FileInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class WorkerRunnable implements Runnable{
	private DatagramSocket clientSocket = null;
	private String filePath;
	private int IP;
	private int port;
	
	 public WorkerRunnable(DatagramSocket clientSocket, String filePath, int port, int iP) {
	        this.clientSocket = clientSocket;
	        this.filePath = filePath;
	        this.port=port;
	        this.IP = iP;
	        		
	 }
	 
	 public void run() {
		 try {
			 String filePath = null;
			File initialFile = new File(filePath);
			 FileInputStream targetStream = new FileInputStream(initialFile);
			 int filesize=targetStream.available();
			 InetAddress IPAddress = InetAddress.getByName(""+IP);
	            byte [] data  = new byte[(int)initialFile.length()];
	            DatagramPacket sendPacket = new DatagramPacket(data, data.length, IPAddress, IP);
	            clientSocket.send(sendPacket);
	            clientSocket.close();
		 }
		 catch(Exception e) {
			 e.printStackTrace();
		 }
		 
	 }
	 
	
	
}

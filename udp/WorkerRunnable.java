package udp;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;

import udp.ThrottledOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class WorkerRunnable implements Runnable{
	private DatagramSocket clientSocket = null;
	private String filePath;
	private String IP;
	private int port;
	
	 public WorkerRunnable(DatagramSocket clientSocket, String filePath, int port, String iP) {
	        this.clientSocket = clientSocket;
	        this.filePath = filePath;
	        this.port=port;
	        this.IP = iP;
	 }
	 
	 public void run() {
		 try {
			 File file =new File(filePath);
			
             Long sendDataSize = file.length();
             String fileName = "FileName";
             String out = String.format("Name of file %s, file size %s is", sendDataSize, fileName);
             InetAddress ip = InetAddress.getByName(IP);
             byte[] data = out.getBytes();
             DatagramPacket sendPacket = new DatagramPacket(data, data.length , ip, port);
             clientSocket.send(sendPacket);
             
			//8000 is the block size for BufferedInputStream and 1000 is 1 Mbps
			final OutputStream slowOut = new ThrottledOutputStream(new BufferedOutputStream(new FileOutputStream(filePath),8000),1000);
			InetAddress IPAddress = InetAddress.getByName(""+IP);
			
	        data = new byte[(int)file.length()];
	        slowOut.write(data);
	        sendPacket = new DatagramPacket(data, data.length, ip, port);
	        clientSocket.send(sendPacket);
	        clientSocket.close();
		 }
		 catch(Exception e) {
			 e.printStackTrace();
		 }	 
	 }
}

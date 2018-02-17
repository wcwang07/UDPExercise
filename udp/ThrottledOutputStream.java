package udp;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author weicwang
 * ThrottledOutputStream writes a file as a stream of bytes
 * by controlling the output rate through a short intervals of sleep 
 *
 */
public class ThrottledOutputStream extends OutputStream {

	private final OutputStream outStream;
	private long totalBytesWrite;
	private long startTimeMillis;
	private final ConcurrentLinkedQueue<Byte> buffer = new ConcurrentLinkedQueue<Byte>();
	
	
	private static final int BYTES_PER_KILOBYTE = 1024;
	private static final int MILLIS_PER_SECOND = 1000;
	private final int ratePerMillis;
	
	public ThrottledOutputStream(OutputStream rawStream, int kBytesPersecond) {
		 this.outStream = rawStream;
		 ratePerMillis = kBytesPersecond * BYTES_PER_KILOBYTE / MILLIS_PER_SECOND;
	 }

	@Override
	public synchronized void write(int b) throws IOException {
		
		// TODO Auto-generated method stub
		if (startTimeMillis == 0)  {
			startTimeMillis = System.currentTimeMillis();
		}
		long now = System.currentTimeMillis();
		long interval = now - startTimeMillis;
		 //sleep if designated rate * time below total byte each time
		if (interval * ratePerMillis < totalBytesWrite + 1) {
			//we are reading one byte at a time
			try {
				// will most likely only be relevant on the first few passes
				final long sleepTime = ratePerMillis / (totalBytesWrite + 1) - interval; 
			}
			catch(Exception e) {
				//so thread can immediate respond to interrupt request
				e.printStackTrace();
			}
		}
		totalBytesWrite += 1;
		 this.buffer.add((byte) b);
	}
	
	
}

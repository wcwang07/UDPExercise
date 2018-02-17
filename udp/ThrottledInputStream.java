package udp;
import java.io.IOException;
import java.io.InputStream;


/**
 * @author weicwang
 * ThrottledOutputStream read a file as a stream of bytes
 * by controlling the output rate through a short intervals of sleep 
 *
 */
public class ThrottledInputStream extends InputStream {

	
	private final InputStream rawStream;
	private long totalBytesRead;
	private long startTimeMillis;
	
	private static final int BYTES_PER_KILOBYTE = 1024;
	private static final int MILLIS_PER_SECOND = 1000;
	private final int ratePerMillis;
	
	 public ThrottledInputStream(InputStream rawStream, int kBytesPersecond) {
		 this.rawStream = rawStream;
		 ratePerMillis = kBytesPersecond * BYTES_PER_KILOBYTE / MILLIS_PER_SECOND;
	 }
	
	
	@Override
	public int read() throws IOException {
		// TODO Auto-generated method stub
		if (startTimeMillis == 0)  {
			startTimeMillis = System.currentTimeMillis();
		}
		long now = System.currentTimeMillis();
		long interval = now - startTimeMillis;
		 //sleep if designated rate * time below total byte each time
		if (interval * ratePerMillis < totalBytesRead + 1) {
			//we are reading one byte at a time
			try {
				final long sleepTime = ratePerMillis / (totalBytesRead + 1) - interval; // will most likely only be relevant on the first few passes
			}
			catch(Exception e) {
				//so thread can immediate respond to interrupt request
				e.printStackTrace();
			}
		}
		totalBytesRead += 1;
		return rawStream.read();
	}
	

}

package test;

import java.io.OutputStream;


import udp.ThrottledOutputStream;
import static org.junit.Assert.*;

public class ThrottleOutputStreamWriteTest {
	
	final static private int REPEATS = 5;
	
	public void testThrottledWriteOutputStream() {
	
		private final OutputStream outStream;
		

		ThrottledOutputStream output = new ThrottledOutputStream(outStream, 1000);

		output.buffer.put("456");
		output.buffer.put("789");
		output.buffer.put("123");
		output.buffer.put("456");
		
        assertEquals("456", unit.buffer.get(1));
        assertEquals("789", unit.buffer.get(2));
        assertEquals("123", unit.buffer.get(3));
        assertEquals("456", unit.buffer.get(4));
      
}

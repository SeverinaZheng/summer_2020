package servlets;
import java.net.InetSocketAddress;
import java.util.Arrays;

import java.util.List;
import net.spy.memcached.MemcachedClient; 


public class MemcachedSource {
	private MemcachedClient memClient;
	private static MemcachedSource memSource; 
	
	public static String memcachedIP;
	
	private MemcachedSource(){
		try {
			List<InetSocketAddress> addresses = Arrays.asList(new InetSocketAddress(memcachedIP,11211));
		
			memClient = new MemcachedClient(addresses);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static MemcachedSource getInstance(){
		if (memSource == null) {

			memSource = new MemcachedSource();
			return memSource;
			
		} else return memSource;
	}
	
	public MemcachedClient getClient() {
		
		return this.memClient;
	}
}

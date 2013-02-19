package networkdcq.discovery;

import javax.microedition.io.Datagram; //import java.net.DatagramPacket;
import javax.microedition.io.DatagramConnection; //import java.net.MulticastSocket;
//import java.net.InetAddress;

import networkdcq.Host;
import networkdcq.NetworkDCQ;
import networkdcq.util.Logger;
import networkdcq.discovery.HostDiscovery;

class UDPDiscovery extends HostDiscovery implements Runnable {

	/** UDP Port */
	public static final int UDP_PORT = 9998;
	/** UDP Group */
	protected static final String UDP_GROUP = "230.0.0.1";
	/** Field Separator */
	protected static final String DATAGRAM_FIELD_SPLIT = ":..:";
	/** Buffer size */
	protected static final int BUFFER_SIZE = 64;
	/** Discovery is running */
	protected static boolean running = false;
	/** Status data */
	protected Datagram packet = null; //DatagramPacket packet = null;
	/** Status to be sent/received */
	protected byte[] buf = new byte[BUFFER_SIZE];
	/** UDP Host group */
//	protected InetAddress group = null;
	/** Broadcast to other hosts */ 
	protected DatagramConnection socket = null;//MulticastSocket socket = null;
	
	/**
	 * Starts UDP host discovery, creating two threads: one for the server and one for the client
	 */
	public boolean startDiscovery() {
		running = true;
		
		// Listener
        UDPListener listener = new UDPListener();
        Thread threadA = new Thread(listener);
        threadA.start();
        
        // Client
        UDPClient client = new UDPClient();
        Thread threadB = new Thread(client);
        threadB.start();
        
        return true;
	}
	
	/**
	 * Stops UDP host discovery.  The thread created in <code>startDiscovery()</code> end normally.
	 */
	public void stopDiscovery() {
		running = false;
	}

	/**
	 * In charge of verifying hosts timeOuts ---> nuevo
	 */
	public synchronized void run() {
		while (running) { 			
			//for (Host host : otherHosts.getValueList()) {
			Host host;
			for (int i=0; i < otherHosts.size(); i++) {
				host = (Host) otherHosts.getValueAt(i);
				if (System.currentTimeMillis() - host.getLastPing() > DISCOVERY_TIMEOUT_LIMIT_MS) {
					HostDiscovery.removeHost(host.getHostIP());
					NetworkDCQ.getCommunication().getConsumer().byeHost(host);
				}
			}
        	try {
        		Thread.sleep(DISCOVERY_TIMEOUT_CHECK_INTERVAL_MS);
        	}
        	catch (Exception e) { 
        		Logger.w(e.getMessage()); 
        	}

		}
	}
	
	
}

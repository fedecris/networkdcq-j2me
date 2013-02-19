package networkdcq.discovery;


import javax.microedition.io.Connector;
import javax.microedition.io.DatagramConnection;

//import java.net.InetAddress;

import networkdcq.util.Logger;



class UDPClient extends UDPDiscovery implements Runnable {
	
	/**
	 * Discovery client main loop.  Send a status broadcast message periodically.  
	 */
	public void run() {
		
        try {
        	// Get UDP group and socket 
//        	group = InetAddress.getByName(UDP_GROUP);
            //socket = new MulticastSocket(UDP_PORT);
        	socket = (DatagramConnection) Connector.open("datagram://" + UDP_GROUP + ":" + UDP_PORT);
        	
        	while (running) {

        		// Send current status
	    		sendPing();

	            // Sleep for a while and resend status
                Thread.sleep(DISCOVERY_INTERVAL_MS);
	    	}
        	
        	// Close socket
			if (socket != null)
				socket.close();

	    }
	    catch (Exception e) { 
	    	Logger.e(e.getMessage()); 
	    }
	}
	
	/**
	 * Sends datagram with host information (IP-Active(Y/N))
	 * @throws Exception
	 */
	protected void sendPing() throws Exception {
		// Send current status
        buf = (thisHost.getHostIP() + DATAGRAM_FIELD_SPLIT + (thisHost.isOnLine()?"Y":"N") + DATAGRAM_FIELD_SPLIT).getBytes();
        if (packet==null)
        	//packet = new DatagramPacket(buf, buf.length, group, UDP_PORT);
        	packet = socket.newDatagram(buf, buf.length); //, UDP_GROUP);

        //packet.setData(buf);
        packet.setData(buf, 0, buf.length); // setData(byte[] buffer, int offset, int len)   
        socket.send(packet);
	}
	
}


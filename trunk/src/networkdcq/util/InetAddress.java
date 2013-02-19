package networkdcq.util;

import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.ServerSocketConnection;

public class InetAddress {

	String localAddress;
	
	public InetAddress() throws IOException
	{
		ServerSocketConnection ssc = (ServerSocketConnection) Connector.open("socket://:1234");
		localAddress = ssc.getLocalAddress();
		ssc.close();
	}
	
	public String getLocalAddress()
	{
		return localAddress;
	}
	
}

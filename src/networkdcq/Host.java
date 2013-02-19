package networkdcq;

//import java.util.Enumeration;
//import java.net.NetworkInterface;
//import java.net.Inet6Address;

import networkdcq.util.InetAddress;
import networkdcq.util.StringSplitter;


public class Host implements NetworkSerializable {

	/** Serial Version UID */
	private static final long serialVersionUID = 2807206367538864478L;
	/** Host IP */
	private String hostIP;
	/** Current status (online or not) */
	private boolean onLine;
	/** Last ping (for timeOut validation) */
	private long lastPing = System.currentTimeMillis();
	
	public String toString() {
	    return " (" + hostIP + ") - " + (onLine?"Online":"Offline");
	}

    public String getHostIP() {
        return hostIP;
    }

    public void setHostIP(String hostIP) {
        this.hostIP = hostIP;
    }

    public boolean isOnLine() {
        return onLine;
    }

    public void setOnLine(boolean onLine) {
        this.onLine = onLine;
    }
    
    public Host(String hostIP, boolean onLine) {
        this.hostIP = hostIP;
        this.onLine = onLine;
    }

    public long getLastPing() {
        return lastPing;
    }

    public void setLastPing(long lastPing) {
        this.lastPing = lastPing;
    }  

    /** 
     * Especificación de equals
     */
    public boolean equals(Object anObject) {
    	if (anObject == null)
    		return false;
    	
    	Host host = (Host)anObject;
    	if (getHostIP() == null && host.getHostIP() != null ||
    		getHostIP() != null && host.getHostIP() == null)
    	return false;
    	
    	return getHostIP().equals(((Host)host).getHostIP());
    }

    
    /**
     * Obtains this host IP
     * @return IPv4 or null otherwise
     */
    public static Host getLocalHostAddresAndIP() {
        try {
/*
        	//for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
        	for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {        	
                //NetworkInterface intf = en.nextElement();
                NetworkInterface intf = (NetworkInterface) en.nextElement();                                
                
                //for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {                
                	//InetAddress inetAddress = enumIpAddr.nextElement();
                	InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();                        

                	// IPv4 only
                	if (inetAddress instanceof Inet6Address)
                    	continue;
                    if (!inetAddress.isLoopbackAddress()) {
                        Host thisHost = new Host(inetAddress.getHostAddress().toString(), true);
                        return thisHost;
                    }
                }
            }
*/                                    
		    InetAddress inetAddress = new InetAddress();
		    Host thisHost = new Host(inetAddress.getLocalAddress(), true);
		    return thisHost;
		    
        } catch (Exception ex) { //} catch (SocketException ex) {            
            ex.printStackTrace();
        }
        return null;
    }

	public String networkSerialize() {
		// TODO Auto-generated method stub	
		return ( hostIP + NetworkSerializable.VARIABLE_MEMBER_SEPARATOR +
				 onLine + NetworkSerializable.VARIABLE_MEMBER_SEPARATOR +		
		 		 lastPing + NetworkSerializable.VARIABLE_END_OF_VARIABLES);		
		
	}

	public Object networkDeserialize(String data) {
		// TODO Auto-generated method stub
		String cadenas[] = StringSplitter.split(data, NetworkSerializable.VARIABLE_MEMBER_SEPARATOR + 
													  NetworkSerializable.VARIABLE_MEMBER_SEPARATOR +
													  NetworkSerializable.VARIABLE_END_OF_VARIABLES);		

		// creo la instancia Host y la devuelvo
		Host host = new Host(cadenas[0], cadenas[1]=="true");
		host.setLastPing(Long.parseLong(cadenas[2]));
		return host;		
	}
    
    public void updateHostStatus(boolean onLine) {
    	setOnLine(onLine);
    	setLastPing(System.currentTimeMillis());
    }
    
}

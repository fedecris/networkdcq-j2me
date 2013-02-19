package networkdcq.communication;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.SocketConnection;


import networkdcq.Host;
import networkdcq.NetworkDCQ;
import networkdcq.discovery.HostDiscovery;
import networkdcq.util.Logger;

public class TCPServer extends TCPListener implements Runnable {

	/**
	 * Constructor
	 */
	//public TCPServer(Socket socket, ObjectInputStream fromBuffer, ObjectOutputStream toBuffer) {
	public TCPServer(SocketConnection socket, InputStream fromBuffer, OutputStream toBuffer, 
					 DataInputStream dataInputStream, DataOutputStream dataOutputStream) {	
		//Logger.i("Creating connection to: " + socket.getInetAddress());
		try {
			Logger.i("Creating connection to: " + socket.getLocalAddress());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		this.socket = socket;
		this.fromBuffer = fromBuffer;
		this.toBuffer = toBuffer;
		this.dataInputStream = dataInputStream;
		this.dataOutputStream = dataOutputStream;		
	}

	
	/**
	 * Receives and notifies incoming messages
	 */
	public synchronized void run() {
		boolean ok = true;
        while (listenerRunning && ok) {
            try {
                // Wait for incoming messages
            	data = receive();
                if (data == null)
                    continue;

                // Update data to be consumed
                NetworkDCQ.getCommunication().getConsumer().newData(data);
                data = null;
            }
            catch (IOException ex) {
                // Tell the app that the connection with the host is lost, or has too many errors
            	//String ip = socket.getInetAddress().toString().substring(1);
            	String ip = "";
				try {
					ip = socket.getLocalAddress().toString().substring(1);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	NetworkDCQ.getCommunication().getConsumer().byeHost(new Host(ip, false));
            	HostDiscovery.removeHost(ip);
            	ok = false;
            }
            catch (Exception e) { 
            	Logger.e(e.getMessage());
            }
        }
        try {
        	socket.close();
        }
        catch (Exception e) {
        	Logger.e(e.getMessage());
        }
    }
}

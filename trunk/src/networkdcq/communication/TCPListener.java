package networkdcq.communication;

import javax.microedition.io.Connector;
import javax.microedition.io.ServerSocketConnection;
import javax.microedition.io.SocketConnection;

import networkdcq.util.Logger;


public class TCPListener extends TCPNetwork implements Runnable {
            
    /**
     * Creates the TCP ServerSocket
     */
    public TCPListener() {
        try {
            port = TCP_PORT;
            // Subclasses should not create a new ServerSocket
            if (this.getClass().equals(TCPListener.class))
            	//serverConn = new ServerSocket(port);
            	serverConn = (ServerSocketConnection) Connector.open("socket://:" + port);            	
        }
        catch (Exception ex) { 
        	Logger.e(ex.getMessage()); 
        }
    }

    /**
     * Main loop, listens for new connection requests
     */
    public synchronized void run() {
    	while (listenerRunning) {
    		listen();
    	}
    }
    
    /**
     * Restarts sever socket
     */
    public void restartServer() {
        try {
                closeServer();
				serverConn = (ServerSocketConnection) Connector.open("socket://:" + port);                
        }
        catch (Exception e) { 
        	Logger.e(e.getMessage()); 
        }
    }
    
    /**
     * Waits for new connections and spawns a new thread each time
     */
    public boolean listen() {
        try {   
        	Logger.i("Esperando client connections...");
        	socket = (SocketConnection) serverConn.acceptAndOpen();        	
            toBuffer = socket.openOutputStream();
            fromBuffer = socket.openInputStream();
            new Thread(new TCPServer(socket, fromBuffer, toBuffer)).start();
            return true;
        }
        catch (Exception ex) { 
        	Logger.e(ex.getMessage());
            return false;
        }
    }  
    

}
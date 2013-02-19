package networkdcq.communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.ServerSocketConnection;
import javax.microedition.io.SocketConnection;

import networkdcq.NetworkApplicationData;
import networkdcq.NetworkDCQ;
import networkdcq.util.Logger;


public class TCPNetwork extends TCPCommunication {

	/** TCP Port */
	public static final int TCP_PORT = 9999;
	
    /** Configured Host IP */
    protected String host;
    /** Configured Host port */
    protected int port;     

    /** Client socket */
    protected SocketConnection socket; //Socket socket;
    /** Server socket */
    protected ServerSocketConnection serverConn; //ServerSocket serverConn;
    
    /** Buffer for writing objects */
    protected OutputStream toBuffer = null; //ObjectOutputStream toBuffer = null;    
    protected DataOutputStream dataOutputStream;
    /** Buffer for reading objects */
    protected InputStream fromBuffer = null; //ObjectInputStream fromBuffer = null;
    protected DataInputStream dataInputStream;

	/**
     * Writes an object to the stream
     * @param data object to be written
     * @throws IOException in case of socket error 
     */
    public void write(NetworkApplicationData data) throws IOException {
        try {
        	
        	dataOutputStream.write((data.networkSerialize()).getBytes()); //toBuffer.writeObject(data);
        	dataOutputStream.flush(); //toBuffer.flush();
        	//toBuffer.reset();
        }
        catch (Exception ex) { 
        	Logger.w("Exception writing object:" + ex.getMessage());
       		throw new IOException("Socket error");
        }
    }   
    

    /**
     * Reads the next object from the stream
     * @return received data
     * @throws IOException in case of socket error
     */
    public NetworkApplicationData receive() throws IOException {
        try {
        	//return (NetworkApplicationData)fromBuffer.readObject();
			int inputChar;
			int corte = NetworkApplicationData.VARIABLE_END_OF_VARIABLES.charAt(0);
			StringBuffer sb = new StringBuffer();
		    // se lee hasta el caracter de fin (es un String pero por ahora asumo que es de tama�o 1) <-------
			while ((inputChar = dataInputStream.read()) != corte)
		      sb.append((char)inputChar);

			// agrego el ultimo caracter (corte) 
			sb.append((char)inputChar);
			
			// armo el NetworkApplicationData
			String datos = sb.toString();									
			
			return (NetworkApplicationData)NetworkDCQ.getCommunication().getData().networkDeserialize(datos);
			//return this.getData().networkDeserialize(datos);			
        }   
        catch (Exception ex) {
        	Logger.w("Exception reading object:" + ex.getMessage());
       		throw new IOException("Socket error");
        }
    }  
    

    /**
     * Closes the server socket 
     */
    public void closeServer() {
        try {       
            socket.close();
            serverConn.close();
        }
        catch (Exception ex) {
            Logger.w(ex.getMessage()); 
        }      
    }
    


    
}

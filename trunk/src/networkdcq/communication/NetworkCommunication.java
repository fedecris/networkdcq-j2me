package networkdcq.communication;

import networkdcq.Host;
import networkdcq.NetworkApplicationData;
import networkdcq.NetworkApplicationDataConsumer;
import networkdcq.NetworkApplicationDataProducer;

public abstract class NetworkCommunication {

	/** Interval between status updates to the other hosts */
	public static int BROADCAST_LOCAL_STATUS_INTERVAL_MS = 30;
	
	/** Local data producer instance */
	protected NetworkApplicationDataProducer producer = null;
	/** Remote data consumer instance */
	protected NetworkApplicationDataConsumer consumer = null;
    /** Message data to send/received */
    protected NetworkApplicationData data = null;
    
    /**
     * Default producer getter
     * @return the local data producer
     */
	public NetworkApplicationDataProducer getProducer() {
		return producer;
	}

	/**
	 * Default producer setter
	 * @param producer the local data producer
	 */
	public void setProducer(NetworkApplicationDataProducer producer) {
		this.producer = producer;
	}

	/**
	 * Default consumer getter
	 * @return the remote data consumer
	 */
	public NetworkApplicationDataConsumer getConsumer() {
		return consumer;
	}

	/**
	 * Default consumer setter
	 * @param consumer the remote data consumer
	 */
	public void setConsumer(NetworkApplicationDataConsumer consumer) {
		this.consumer = consumer;
	}

    /**
     * Default networkApplicationData getter
     * @return the local networkApplicationData
     */
	public NetworkApplicationData getData() {
		return data;
	}

	/**
	 * Default networkApplicationData setter
	 * @param data the local networkApplicationData
	 */
	public void setData(NetworkApplicationData data) {
		this.data = data;
	}	
	
	
	/* 
	 * ================================================================================================= 
	 */
	
	/**
	 * Starts the service for receiving messages from other hosts
	 * Should be started in a separate thread
	 * @return true if action was successful, false otherwise
	 */
	public abstract boolean startService();
	
	/**
	 * Starts the service in charge of broadcasting local status to other hosts
	 * Should be started in a separate thread
	 * @return true if action was successful, false otherwise
	 */
	public abstract boolean startBroadcast();

	/**
	 * Stops the service for receiving messages from other hosts
	 * @return true if action was successful, false otherwise
	 */
	public abstract boolean stopService();
	
	/**
	 * Stops the service in charge of broadcasting local status to other hosts
	 * @return true if action was successful, false otherwise
	 */
	public abstract boolean stopBroadcast();
	
	/**
	 * Connects to a server
	 * @param target target host
	 * @return true if a connection is achieved, or false otherwise
	 */
	public abstract  boolean connectToServerHost(Host target);
	
	/**      
	 * Sends a single data message to a target.  In case of a communication error,
	 * the <code>NetworkApplicationDataConsumer.byeHost()</code> method should 
	 * be invoked, so that the application can behave accordingly
	 * @param targetIP destination host IP
	 * @param data message content
	 */
	public abstract void sendMessage(Host targetHost, NetworkApplicationData data);
	
	/**
	 * Sends a single data message to all known hosts
	 * @param data message content
	 */
	public abstract void sendMessageToAllHosts(NetworkApplicationData data);
	
}

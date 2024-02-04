/**
 * 
 */
package br.processkey.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 */
public class ServerCommunicator implements IServerCommunicator { 
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(ServerCommunicator.class);

	/**
	 * 
	 */
	private String serverHost;
	
    /**
     * 
     */
    private int serverPort;

    /**
     * @param serverHost
     * @param serverPort
     */
    public ServerCommunicator(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    /**
     *
     */
    @Override
    public void sendData(final Map<String, String> data) {
        try (Socket socket = new Socket(serverHost, serverPort);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {
            oos.writeObject(data);
//            oos.flush();
            
            LOGGER.info("Data sent to server: " + data);
        } catch (IOException e) {
        	LOGGER.error("Error sending data to server", e);
        }
    }
}
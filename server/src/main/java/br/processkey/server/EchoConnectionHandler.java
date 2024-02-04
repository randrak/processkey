/**
 * 
 */
package br.processkey.server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.AccessLevel;
import lombok.Getter;

/**
 * 
 */
public class EchoConnectionHandler implements ConnectionHandler {
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(EchoConnectionHandler.class);
	
	/**
	 * 
	 */
	@Getter(value = AccessLevel.PROTECTED)
	private DataProcessor processor;

    /**
     * @param processor
     */
    public EchoConnectionHandler(final DataProcessor processor) {
    	this.processor = processor;	
	}

	/**
     *
     */
    @Override
    public void handleConnection(final Socket clientSocket) {
            
    	try {
        	if (getProcessor() == null) {
        		try (ObjectInputStream readerObj = new ObjectInputStream(clientSocket.getInputStream());
        				PrintWriter writer = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true)) {
	        		Map<String, String> obj;
		            while ((obj = (Map<String, String>) readerObj.readObject()) != null) {
		            	LOGGER.info("Received: " + obj);
		            		writer.print(obj);
		            }
        		} catch (EOFException ex) {
        			LOGGER.info("Socket was closed");
        		} catch (ClassNotFoundException ex) {
        			LOGGER.error("Cast error. ", ex);
        		}
        	} else {
        		getProcessor().processData(clientSocket.getInputStream());
        	}
		} catch (IOException ex) {
			LOGGER.error("Error in echo handle content", ex);
    	}
    }
}

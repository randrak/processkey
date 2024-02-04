/**
 * 
 */
package br.processkey.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Server {
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(Server.class);

	/**
     * 
     */
    private static final int DEFAULT_SERVER_PORT = 12345;

    /**
     * 
     */
    private int port;
    
    /**
     * 
     */
    private ConnectionHandler connectionHandler;

    /**
     * @param port
     * @param handler
     */
    public Server(int port, ConnectionHandler handler) {
        this.port = port;
        this.connectionHandler = handler;
    }

    /**
     * 
     */
    public void start() {
        LOGGER.info("Server started. Listening on port " + port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (!Thread.currentThread().isInterrupted()) {
                Socket clientSocket = serverSocket.accept();
                connectionHandler.handleConnection(clientSocket);
            }
        } catch (IOException ex) {
            LOGGER.error("Error in handling connection...", ex);
        }
    }
    
    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
    	final SimpleDataProcessor processor = new SimpleDataProcessor();
    	final ConnectionHandler conn = new EchoConnectionHandler(processor);
    	final Server server = new Server(DEFAULT_SERVER_PORT, conn);
    	
        server.start();
    }
}
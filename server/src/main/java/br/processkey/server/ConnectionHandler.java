package br.processkey.server;

import java.net.Socket;

/**
 * 
 */
public interface ConnectionHandler {
    /**
     * @param clientSocket
     */
    void handleConnection(Socket clientSocket);
}

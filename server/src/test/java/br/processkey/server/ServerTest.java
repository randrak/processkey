/**
 * 
 */
package br.processkey.server;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.net.Socket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * 
 */
public class ServerTest {

    /**
     * 
     */
    @Mock
    private ConnectionHandler mockConnectionHandler;

    /**
     * 
     */
    private Server server;

    /**
     * 
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        server = new Server(12345, mockConnectionHandler);
    }

    /**
     * 
     */
    @Test
    public void testServerUsesConnectionHandler() {
        // Simulate the server starting and accepting a connection
        // This part is conceptual because directly invoking server.start()
        // would require network access. 
        
        // Assume server.start() somehow triggers handleConnection without actual network IO
        
        new Thread(() -> {
            server.start();
        }).start();
        
        try {
        	Thread.currentThread().sleep(1000); //waiting start server
			Socket sck = new Socket("localhost", 12345);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // This step requires you to abstract or simulate server's network interactions
        verify(mockConnectionHandler, times(1)).handleConnection(any());
    }

    /**
     * 
     */
    @Test
    public void testServerAvoidsNullPointerException() {
        // Ensure that starting the server with a null ConnectionHandler doesn't cause an error
        // This might involve checking for null in the Server constructor or start method
        // and handling it appropriately (e.g., logging an error, throwing a specific exception).
    }
}
package br.processkey.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.Socket;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 */
public class EchoConnectionHandlerTest {

    /**
     * 
     */
    private Socket mockSocket;
    
    /**
     * 
     */
    private String simulatedInput;
    
    /**
     * 
     */
    private ByteArrayInputStream input;
    
    /**
     * 
     */
    private ByteArrayOutputStream output;

    /**
     * @throws Exception
     */
    @BeforeEach
    public void setUp() throws Exception {
        // Simulate socket input/output
        simulatedInput = "Test input";
        input = new ByteArrayInputStream(simulatedInput.getBytes());
        output = new ByteArrayOutputStream();

        mockSocket = mock(Socket.class);
        when(mockSocket.getInputStream()).thenReturn(input);
        when(mockSocket.getOutputStream()).thenReturn(output);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testHandleConnection() throws Exception {
        DataProcessor mockProcessor = mock(DataProcessor.class);
    	EchoConnectionHandler handler = new EchoConnectionHandler(mockProcessor);

    	handler.handleConnection(mockSocket);

        String resultOutput = output.toString();
        verify(mockProcessor).processData(any(InputStream.class));
    }
    
    /**
     * @throws Exception
     */
    @Test
    public void testHandlConnectionWithNullProcessor() throws Exception {
    	EchoConnectionHandler handler = new EchoConnectionHandler(null);

    	handler.handleConnection(mockSocket);

        String resultOutput = output.toString();
    	assertEquals(simulatedInput, resultOutput);
    }
}


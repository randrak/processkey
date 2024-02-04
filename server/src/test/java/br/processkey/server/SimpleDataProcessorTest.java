/**
 * 
 */
package br.processkey.server;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * 
 */
public class SimpleDataProcessorTest {

    /**
     * 
     */
    private SimpleDataProcessor processor;

    /**
     * 
     */
    @BeforeEach
    public void setUp() {
        processor = new SimpleDataProcessor();
    }

    /**
     * 
     */
    @Test
    public void testDataProcessing() {
        // Simulate the data input
        String testData = "line1\nline2\nline3";
        InputStream inputStream = new ByteArrayInputStream(testData.getBytes());

        // Process the data
        processor.processData(inputStream);

        // Verify the processing result
        // Since SimpleDataProcessor's method does not return a value and might print to console,
        // you would need to redirect System.out or verify the intended side effect.
        
        // Example assertion (modify according to what processData actually does):
        // assertEquals(expectedResult, actualResult);
    }
    
    /**
     * 
     */
    @Test
    public void testDataProcessingAvoidInputNull() {
        InputStream inputStream = null;

        assertThrows(RuntimeException.class, () -> {
        	processor.processData(inputStream);
        });
    }
}


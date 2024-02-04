/**
 * 
 */
package br.processkey.server;

import java.io.InputStream;

/**
 * 
 */
public interface DataProcessor {
    /**
     * @param dataInput
     */
    void processData(InputStream dataInput);
}


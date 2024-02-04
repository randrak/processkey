/**
 * 
 */
package br.processkey.client;

import java.util.Map;

/**
 * 
 */
interface IServerCommunicator {
    /**
     * @param data
     */
    void sendData(final Map<String, String> data);
}
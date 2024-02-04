/**
 * 
 */
package br.processkey.client;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;

import br.processkey.client.ConfigurationLoader;

/**
 * 
 */
public class ConfigurationLoaderTest {

    @Test
    public void testLoadProperties() throws IOException {
        String testPropertiesContent = "directory.path=/example/path\n" +
                                       "server.port=8080";
        ByteArrayInputStream input = new ByteArrayInputStream(testPropertiesContent.getBytes());

        ConfigurationLoader configLoader = new ConfigurationLoader();
        configLoader.loadProperties(input);

        assertEquals("/example/path", configLoader.getProperty("directory.path"));
        assertEquals("8080", configLoader.getProperty("server.port"));
    }

    /**
     * @throws IOException
     */
    @Test
    public void testGetPropertyWithDefaultValue() throws IOException {
        String testPropertiesContent = "";
        ByteArrayInputStream input = new ByteArrayInputStream(testPropertiesContent.getBytes());

        ConfigurationLoader configLoader = new ConfigurationLoader();
        configLoader.loadProperties(input);

        assertEquals("default", configLoader.getProperty("non.existent.key", "default"));
    }
}
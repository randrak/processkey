/**
 * 
 */
package br.processkey.client;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationLoader {
    /**
     * 
     */
    private Properties properties;

    /**
     * 
     */
    public ConfigurationLoader() {
        this.properties = new Properties();
    }

    /**
     * @param inputStream
     * @throws IOException
     */
    public void loadProperties(final InputStream inputStream) throws IOException {
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            throw new IOException("Unable to load configuration", e);
        }
    }

    /**
     * @param key
     * @return
     */
    public String getProperty(final String key) {
        return properties.getProperty(key);
    }

    /**
     * @param key
     * @param defaultValue
     * @return
     */
    public String getProperty(final String key, final String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}

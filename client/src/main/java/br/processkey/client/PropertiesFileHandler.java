/**
 * 
 */
package br.processkey.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 */
public class PropertiesFileHandler implements IDirectoryEventHandler {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(PropertiesFileHandler.class);

    /**
     * 
     */
    private String regexPattern;
    
    /**
     * 
     */
    private IServerCommunicator serverCommunicator;

    /**
     * @param regexPattern
     * @param serverCommunicator
     */
    public PropertiesFileHandler(String regexPattern, IServerCommunicator serverCommunicator) {
        this.regexPattern = regexPattern;
        this.serverCommunicator = serverCommunicator;
    }

    /**
     *
     */
    @Override
    public void handleEvent(Path filePath) {
        File file = filePath.toFile();
        if (file.getName().endsWith(".properties")) {
            processPropertiesFile(file);
            boolean deleted = file.delete();
            if (deleted) {
            	LOGGER.info("File deleted: " + file.getName());
            } else {
            	LOGGER.warn("Failed to delete file: " + file.getName());
            }
        }
    }

    /**
     * @param file
     */
    private void processPropertiesFile(File file) {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream(Paths.get(file.getAbsolutePath()).toFile()));

            Map<String, String> filteredMap = filterProperties(props);
            serverCommunicator.sendData(filteredMap);
        } catch (IOException e) {
        	LOGGER.error("Error processing properties file", e);
        }
    }

    /**
     * @param properties
     * @return
     */
    private Map<String, String> filterProperties(Properties properties) {
        Map<String, String> filteredMap = new HashMap<>();
        Pattern pattern = Pattern.compile(regexPattern);
        for (String key : properties.stringPropertyNames()) {
            if (pattern.matcher(key).matches() || 
            		pattern.matcher(String.valueOf(properties.get(key))).matches()) {
                filteredMap.put(key, properties.getProperty(key));
            }
        }
        
        return filteredMap;
    }
}
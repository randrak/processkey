/**
 * 
 */
package br.processkey.server;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 */
public class SimpleDataProcessor implements DataProcessor {

	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(SimpleDataProcessor.class);
	
	/**
	 * 
	 */
	private static final String DATA_DIR = "data";
	
	/**
	 * 
	 */
	private static DateTimeFormatter formatter = 
			DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");

    /**
     *
     */
    @Override
    public void processData(InputStream dataInput) {
    	if (dataInput == null) {
    		throw new RuntimeException("data input for SimpleDateProcessor is null");
    	}
    	
    	Properties propFromClient = new Properties();
    	final String fileName = getFilename();
    	Map<String, String> mapObj = null;
        try (ObjectInputStream readerObj = new ObjectInputStream(dataInput)) {
            while ((mapObj = (Map<String, String>) readerObj.readObject()) != null) {
            	propFromClient.putAll(mapObj);
            	LOGGER.info("Processing: " + mapObj);
            }
        } catch (EOFException ex) {
        	LOGGER.info("Socket was closed");
        } catch (Exception ex) {
        	LOGGER.error("Error in data processing.", ex);
        }
 

    	try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATA_DIR + "/" + fileName))) {
				propFromClient.store(writer, "Properties from client");
				LOGGER.info("Properties file saved at: " + Paths.get(fileName).toAbsolutePath().normalize().toString());
        } catch (IOException ex) {
        	LOGGER.error("Save error properties.", ex);
        }
    }

	/**
	 * @return
	 */
	private String getFilename() {
		return LocalDateTime.now().format(formatter)+".properties";
	}
}
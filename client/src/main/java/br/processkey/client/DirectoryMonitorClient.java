/**
 * 
 */
package br.processkey.client;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 */
public class DirectoryMonitorClient {
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(DirectoryMonitorClient.class);
	 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
        try {
            final ConfigurationLoader config = new ConfigurationLoader();
            LOGGER.info("Load configuration...");
            config.loadProperties(
            		DirectoryMonitorClient.class.getResourceAsStream("/config.properties"));
            
            String directoryPath = config.getProperty("directory.path");
            String regexPattern = config.getProperty("regex.pattern");
            String serverHost = config.getProperty("server.host");
            int serverPort = Integer.parseInt(config.getProperty("server.port"));

            IFileWatcher fileWatcher = new RealFileWatcher(directoryPath);
            IServerCommunicator serverCommunicator = new ServerCommunicator(serverHost, serverPort);
            IDirectoryEventHandler fileHandler = new PropertiesFileHandler(regexPattern, serverCommunicator);
            DirectoryMonitor monitor = new DirectoryMonitor(fileWatcher, fileHandler);

            LOGGER.info("Start monitori...");
            monitor.startMonitoring();
        } catch (IOException ex) {
        	LOGGER.error("Error loading configuration", ex);
        } catch (InterruptedException ex) {
        	LOGGER.error("Error when monitoring the folder.", ex);
		}
    }
}

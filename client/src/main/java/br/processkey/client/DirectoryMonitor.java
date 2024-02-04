/**
 * 
 */
package br.processkey.client;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 */
public class DirectoryMonitor {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(DirectoryMonitor.class);

	
	/**
	 * Time default to read in each interation over folder
	 */
	private static final int DEFAULT_TIME_INTERVAL_READ = 1000;
	
    /**
     * 
     */
    private final IFileWatcher fileWatcher;
    /**
     * 
     */
    private IDirectoryEventHandler eventHandler;
    
    /**
     * 
     */
    @Getter 
    @Setter 
    private long timeIntervalRead;

    /**
     * @param fileWatcher
     * @param eventHandler
     */
    public DirectoryMonitor(IFileWatcher fileWatcher, IDirectoryEventHandler eventHandler) {
        this.fileWatcher = fileWatcher;
        this.eventHandler = eventHandler;
        
        this.timeIntervalRead = DEFAULT_TIME_INTERVAL_READ;
    }

    /**
     * @throws IOException
     * @throws InterruptedException
     */
    public void startMonitoring() throws IOException, InterruptedException {
        fileWatcher.startWatching();

        try {
        	boolean exitFlag = false;
        	
            while (true) { 
                List<WatchEvent<?>> events = fileWatcher.pollEvents();
                
                for (WatchEvent<?> event : events) {
                    Path filePath = (Path) event.context();
                    
                    if (filePath.toString().equalsIgnoreCase("exit")) {
                        LOGGER.info("Finishing reading the files...");
                        exitFlag = true;
                        break;
                    }
                    
                    eventHandler.handleEvent(fileWatcher.getPathToMonitor().resolve(filePath));
                }

                if (exitFlag) {
                    break;
                }
                
                LOGGER.trace("Waiting for the next reading in "+getTimeIntervalRead()+"ms...");
                Thread.sleep(getTimeIntervalRead());
            }
        } finally {
        	fileWatcher.stopWatching();
        }
    }
    
    
}

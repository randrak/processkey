/**
 * 
 */
package br.processkey.client;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.List;

/**
 * 
 */
public interface IFileWatcher {
    /**
     * @throws IOException
     */
    void startWatching() throws IOException;
    
    /**
     * @return
     */
    List<WatchEvent<?>> pollEvents();
    
    /**
     * @throws IOException
     */
    void stopWatching() throws IOException;
    
    /**
     * @return
     */
    Path getPathToMonitor();
}
/**
 * 
 */
package br.processkey.client;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

/**
 * 
 */
public class RealFileWatcher implements IFileWatcher {
    /**
     * 
     */
	@Getter
    private final Path pathToMonitor;
    
    /**
     * 
     */
    private WatchService watchService;
    
    /**
     * 
     */
    private WatchKey watchKey;

    /**
     * @param directoryPath
     */
    public RealFileWatcher(String directoryPath) {
        this.pathToMonitor = Paths.get(directoryPath);
    }

    /**
     * @param directoryPath
     */
    public RealFileWatcher(Path directoryPath) {
        this.pathToMonitor = directoryPath;
    }

    /**
     *
     */
    @Override
    public void startWatching() throws IOException {
        this.watchService = FileSystems.getDefault().newWatchService();
        this.watchKey = pathToMonitor.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);
    }

    /**
     *
     */
    @Override
    public List<WatchEvent<?>> pollEvents() {
        List<WatchEvent<?>> events = new ArrayList<>();
        
        WatchKey key;
        if ((key = watchService.poll()) != null) {
            events.addAll(key.pollEvents());
            key.reset();
        }
        
        return events;
    }

    /**
     *
     */
    @Override
    public void stopWatching() throws IOException {
        if (watchKey != null) {
            watchKey.cancel();
        }
        if (watchService != null) {
            watchService.close();
        }
    }
}

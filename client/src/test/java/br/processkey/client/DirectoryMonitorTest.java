/**
 * 
 */
package br.processkey.client;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
/**
 * 
 */
import org.junit.jupiter.api.BeforeEach;

public class DirectoryMonitorTest {
	
	/**
	 * 
	 */
	private static final Logger LOGGER = LogManager.getLogger(DirectoryMonitorTest.class);


    private DirectoryMonitor directoryMonitor;
    private IFileWatcher mockFileWatcher;
    private IDirectoryEventHandler mockEventHandler;

    @BeforeEach
    public void setUp() {
        mockFileWatcher = mock(IFileWatcher.class);
        mockEventHandler = mock(IDirectoryEventHandler.class);
        directoryMonitor = new DirectoryMonitor(mockFileWatcher, mockEventHandler);
    }

    /**
     * @throws Exception
     */
    @Test
    public void testHandleFileCreationEvent() throws Exception {
        // Arrange
        WatchEvent<Path> fakeEvent = mock(WatchEvent.class);
        when(fakeEvent.kind()).thenReturn(StandardWatchEventKinds.ENTRY_CREATE);
        Path fakePath = mock(Path.class);
        when(fakeEvent.context()).thenReturn(fakePath);

        List<WatchEvent<?>> events = Arrays.asList(fakeEvent);
        when(mockFileWatcher.pollEvents()).thenReturn(events).thenReturn(Arrays.asList()); // Returns events first, then an empty list

        // Act
        Thread monitorThread = new Thread(() -> {
            try {
                directoryMonitor.startMonitoring();
            } catch (IOException ex) {
            	LOGGER.error("Error loading configuration", ex);
            } catch (InterruptedException ex) {
            	LOGGER.error("Error when monitoring the folder.", ex);
    		}
        });
        monitorThread.start();
        
        // Small delay to let the monitoring process the event
        Thread.sleep(100);

        // Stop the monitor
        monitorThread.interrupt();
        monitorThread.join();

        // Assert
        verify(mockEventHandler, times(1)).handleEvent(fakePath);
    }
}
/**
 * 
 */
package br.processkey.client;

import org.junit.jupiter.api.*;

import br.processkey.client.RealFileWatcher;

import java.nio.file.*;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 
 */
public class RealFileWatcherIntegrationTest {

    /**
     * 
     */
    private static final String TEST_DIR = "testDir";
    
    /**
     * 
     */
    private RealFileWatcher fileWatcher;

    /**
     * @throws IOException
     */
    @BeforeAll
    public static void setupTestClass() throws IOException {
        Files.createDirectory(Paths.get(TEST_DIR));
    }

    /**
     * @throws IOException
     */
    @BeforeEach
    public void setUp() throws IOException {
        fileWatcher = new RealFileWatcher(TEST_DIR);
        fileWatcher.startWatching();
    }

    /**
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void testDetectsFileCreation() throws IOException, InterruptedException {
        // Arrange
        Path testFile = Paths.get(TEST_DIR, "testFile.txt");
        Files.createFile(testFile);

        // Act
        List<WatchEvent<?>> events = fileWatcher.pollEvents();

        // Assert
        assertTrue(events.stream().anyMatch(event -> event.kind() == StandardWatchEventKinds.ENTRY_CREATE && testFile.endsWith(event.context().toString())), "File creation event should be detected");

        // Clean up
        Files.delete(testFile);
    }

    /**
     * @throws IOException
     */
    @AfterEach
    public void tearDown() throws IOException {
        fileWatcher.stopWatching();
    }

    /**
     * @throws IOException
     */
    @AfterAll
    public static void tearDownTestClass() throws IOException {
        Files.delete(Paths.get(TEST_DIR));
    }
}

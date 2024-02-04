package br.processkey.client;

import java.nio.file.Path;

/**
 * 
 */
interface IDirectoryEventHandler {
    /**
     * @param file
     */
    void handleEvent(final Path file);
}
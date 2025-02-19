package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Queue;

public class FileManager {

    public static Queue<String> readFileLines(final String fileName) {
        final String path = "src/main/resources/";
        try {
            return new ArrayDeque<>(Files.readAllLines(Path.of(path + fileName)));
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        }
    }
}

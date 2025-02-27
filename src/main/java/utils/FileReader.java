package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileReader {

    private FileReader(){
    }

    public static List<String> read(final String path){
        try {
            return Files.readAllLines(Paths.get(path));
        } catch (final IOException e) {
            throw new IllegalStateException(e);
        }
    }
}

package attendance.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileReader {

    public static List<String> readFromSecondLine(String fileName) throws IOException {
        List<String> inputs = Files.readAllLines(Paths.get(fileName));
        inputs.removeFirst();
        return inputs;
    }
}

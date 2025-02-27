package attendance.utility;

import attendance.exception.ExceptionMessage;
import attendance.exception.FileException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtility {

    private static final String RESOURCE_PATH = "./src/main/resources/";

    public static List<String> readFile(String fileName) {
        BufferedReader reader = loadFile(fileName);
        List<String> lines = readLine(reader);
        lines.removeFirst();
        return lines;
    }

    private static BufferedReader loadFile(String fileName) {
        try {
            FileReader fileReader = new FileReader(RESOURCE_PATH + fileName);
            return new BufferedReader(fileReader);
        } catch (FileNotFoundException exception) {
            throw new FileException(ExceptionMessage.NOT_FOUND_FILE.getMessage());
        }
    }

    private static List<String> readLine(BufferedReader reader) {
        try {
            ArrayList<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (IOException exception) {
            throw new FileException(ExceptionMessage.FILE_IO_ERROR.getMessage());
        }
    }
}

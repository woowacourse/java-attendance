package attendance;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AttendancesFileReader {
    private static final String PATH = "src/main/resources/attendances.txt";

    private AttendancesFileReader() {
    }

    public static BufferedReader readFile() throws IOException {
        return new BufferedReader(new FileReader(PATH));
    }
}

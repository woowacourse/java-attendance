import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AttendancesFileReader {

    public void read() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/attendances.csv"))) {
            String line;
            reader.readLine(); //첫줄
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

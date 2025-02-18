import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class File {

    public static List<String> readFile(String fileName) {
        List<String> studentList = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                studentList.add(line);
            }
        } catch (IOException e) {
        }
        return studentList;
    }
}

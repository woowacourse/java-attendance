package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileInput {

    private final String filePath = "src/main/resources/attendances.csv";
    private final BufferedReader fileBr;

    public FileInput() throws IOException {
        fileBr = new BufferedReader(new FileReader(filePath));
    }

    public ArrayList<String> readAttendanceFile() throws IOException{
        ArrayList<String> attendanceFile = new ArrayList<>();
        fileBr.readLine();
        while(true) {
            String information = fileBr.readLine();
            if (information == null) {
                break;
            }
            attendanceFile.add(information);
        }
        return attendanceFile;
    }

}

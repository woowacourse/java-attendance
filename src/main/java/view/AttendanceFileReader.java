package view;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceFileReader {
    private static final int NAME_INDEX = 0;
    private static final int ATTENDANCE_DATE_INDEX = 1;
    private static final String FILE_NAME = "attendances.csv";

    public Map<String, List<String>> getInfo() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(FILE_NAME);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        Map<String, List<String>> attendanceDateGroupByCrew = new HashMap<>();

        reader.lines().skip(1).forEach(fileData -> addInitDate(attendanceDateGroupByCrew, fileData));
        return attendanceDateGroupByCrew;
    }

    private void addInitDate(Map<String, List<String>> dateGroupByCrew, String fileData) {
        String[] parsedData = fileData.split(",");

        List<String> mapInside = dateGroupByCrew.getOrDefault(parsedData[NAME_INDEX], new ArrayList<>());
        mapInside.add(parsedData[ATTENDANCE_DATE_INDEX]);
        dateGroupByCrew.put(parsedData[NAME_INDEX], mapInside);
    }
}

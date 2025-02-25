package presentation.view;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileInputView {
    private static final int NAME_INDEX = 0;
    private static final int ATTENDANCE_DATE_INDEX = 1;
    private static final String FILE_NAME = "src/main/resources/attendances.csv";

    private static void addInitDate(Map<String, List<String>> dateGroupByCrew, String fileData) {
        String[] parsedData = fileData.split(",");

        List<String> mapInside = dateGroupByCrew.getOrDefault(parsedData[NAME_INDEX], new ArrayList<>());
        mapInside.add(parsedData[ATTENDANCE_DATE_INDEX]);
        dateGroupByCrew.put(parsedData[NAME_INDEX], mapInside);
    }

    public static Map<String, List<String>> getFileInput() {
        Map<String, List<String>> attendanceDateGroupByCrew = new HashMap<>();
        try{
            List<String> lines = Files.readAllLines(Paths.get(FILE_NAME));
            lines.stream().skip(1).forEach(fileDate -> addInitDate(attendanceDateGroupByCrew,fileDate));
        }catch (IOException e){

        }
        return attendanceDateGroupByCrew;
    }
}

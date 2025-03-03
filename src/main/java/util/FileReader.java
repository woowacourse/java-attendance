package util;


import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FileReader {

    private final static String FILE_PATH = "src/main/resources/attendances.csv";
    private final static String DELIMITER = ",";
    private final static int USERNAME_INDEX = 0;
    private final static int TIME_INDEX = 1;

    public static Map<String, List<LocalDateTime>> getCrewsAttendanceHistories() {
        Map<String, List<LocalDateTime>> allAttendanceResult = new HashMap<>();
        try (Scanner scanner = new Scanner(new File(FILE_PATH))) {
            readLines(scanner, allAttendanceResult);
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("[ERROR] 파일 경로가 잘못 되었습니다.");
        }
        return allAttendanceResult;
    }

    private static void readLines(Scanner scanner, Map<String, List<LocalDateTime>> allAttendanceResult) {
        scanner.nextLine();
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            processLine(input, allAttendanceResult);
        }
    }

    private static void processLine(String input, Map<String, List<LocalDateTime>> allAttendanceResult) {
        String[] splitResult = input.split(DELIMITER);
        String username = splitResult[USERNAME_INDEX];
        LocalDateTime attendanceTime = DateFormatter.changeInputToDateTime(splitResult[TIME_INDEX]);
        addHistories(allAttendanceResult, username, attendanceTime);
    }

    private static void addHistories(Map<String, List<LocalDateTime>> allAttendanceResult, String username,
                                     LocalDateTime attendanceTime) {
        if (allAttendanceResult.containsKey(username)) {
            List<LocalDateTime> histories = allAttendanceResult.get(username);
            histories.add(attendanceTime);
            return;
        }
        List<LocalDateTime> histories = new ArrayList<>(List.of(attendanceTime));
        allAttendanceResult.put(username, histories);
    }
}

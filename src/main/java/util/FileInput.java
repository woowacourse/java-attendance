package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.AttendanceDateTime;
import model.StudentAttendanceHistory;

public class FileInput {
    private final static String FILE_INFORMATION_REGEX = "[가-힣]+,\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
    private static final String FILE_PATH = "src/main/resources/attendances.csv";
    private static final int NAME_INDEX = 0;
    private static final int ATTENDANCE_DATE_TIME_INDEX = 1;
    private static final DateTimeFormatter ATTENDANCE_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final String ERROR_INVALID_FILE_FORMAT = "[ERROR] 학생 출석 정보 형식과 맞지 않습니다. 파일을 다시 확인해 주세요.";

    private FileInput() {}

    public static Map<String, StudentAttendanceHistory> readFileAndMakeStudentInformation() {
        List<String> fileInformation = readFile();
        Map<String, StudentAttendanceHistory> studentInformationInFile = new HashMap<>();
        try{
            for (String studentInformation : fileInformation) {
                if (!studentInformation.matches(FILE_INFORMATION_REGEX)) {
                    throw new IllegalArgumentException(ERROR_INVALID_FILE_FORMAT);
                }

                String[] studentNameAndAttendanceDateTimeInformation = studentInformation.split(",");
                String studentName = studentNameAndAttendanceDateTimeInformation[NAME_INDEX];
                String timeInformation = studentNameAndAttendanceDateTimeInformation[ATTENDANCE_DATE_TIME_INDEX];

                LocalDateTime studentDateTime = LocalDateTime.parse(timeInformation, ATTENDANCE_DATE_TIME_FORMATTER);
                AttendanceDateTime attendanceDateTime = new AttendanceDateTime(studentDateTime);

                studentInformationInFile.computeIfAbsent(studentName, k -> new StudentAttendanceHistory(new ArrayList<>())).addAttendanceDateTime(attendanceDateTime);
            }
            return studentInformationInFile;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new UncheckedIOException(new IOException());
        }

}

    private static List<String> readFile() {
        List<String> fileInformation = new ArrayList<>();
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE_PATH))) {
            bufferedReader.readLine();
            String studentInformation;
            while ((studentInformation = bufferedReader.readLine()) != null) {
                fileInformation.add(studentInformation);
            }
            return fileInformation;

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new UncheckedIOException(e);
        }
    }
}

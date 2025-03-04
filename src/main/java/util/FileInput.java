package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.AttendanceDate;
import model.AttendanceTime;
import model.Student;
import model.StudentAttendanceHistory;
import model.Students;

public class FileInput {
    private final static String FILE_INFORMATION_REGEX = "[가-힣]+,\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}";
    private static final String FILE_PATH = "src/main/resources/attendances.csv";
    private static final int NAME_INDEX = 0;
    private static final int ATTENDANCE_DATE_TIME_INDEX = 1;
    private static final int ATTENDANCE_DATE_INDEX = 0;
    private static final int ATTENDANCE_TIME_INDEX = 1;
    private static final DateTimeFormatter ATTENDANCE_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter ATTENDANCE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final String ERROR_INVALID_FILE_FORMAT = "[ERROR] 학생 출석 정보 형식과 맞지 않습니다. 파일을 다시 확인해 주세요.";

    private FileInput() {}

    public static Students readFileAndMakeStudents() {
        List<Student> students = new ArrayList<>();
        Map<String, StudentAttendanceHistory> studentInformationInFile = FileInput.readFileAndMakeStudentInformation();

        for (String studentName : studentInformationInFile.keySet()) {
            students.add(new Student(studentName, studentInformationInFile.get(studentName)));
        }

        return new Students(students);
    }

    private static Map<String, StudentAttendanceHistory> readFileAndMakeStudentInformation() {
        List<String> fileInformation = readFile();
        Map<String, StudentAttendanceHistory> studentInformationInFile = new HashMap<>();
        try{
            for (String studentInformation : fileInformation) {
                if (!studentInformation.matches(FILE_INFORMATION_REGEX)) {
                    throw new IllegalArgumentException(ERROR_INVALID_FILE_FORMAT);
                }

                String[] studentNameAndAttendanceDateTimeInformation = studentInformation.split(",");
                String studentName = studentNameAndAttendanceDateTimeInformation[NAME_INDEX];
                studentInformationInFile.putIfAbsent(studentName, new StudentAttendanceHistory(new HashMap<>()));

                String timeInformation = studentNameAndAttendanceDateTimeInformation[ATTENDANCE_DATE_TIME_INDEX];
                String[] attendanceDateAndAttendanceTime = timeInformation.split(" ");
                AttendanceDate attendanceDate = new AttendanceDate(
                        LocalDate.parse(attendanceDateAndAttendanceTime[ATTENDANCE_DATE_INDEX], ATTENDANCE_DATE_FORMATTER));
                AttendanceTime attendanceTime = new AttendanceTime(
                        LocalTime.parse(attendanceDateAndAttendanceTime[ATTENDANCE_TIME_INDEX], ATTENDANCE_TIME_FORMATTER));
                StudentAttendanceHistory history = studentInformationInFile.get(studentName);
                history.getAttendanceHistory().put(attendanceDate, attendanceTime);
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

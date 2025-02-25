package util;

import constant.DateFormatInformation;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import model.AttendanceRecord;
import model.AttendanceRecords;
import model.AttendanceRuleByDay;
import model.AttendanceStatus;
import model.Student;

public class FileInput {

    private final String filePath = "src/main/resources/attendances.csv";
    private final BufferedReader fileBr;

    public FileInput() throws IOException {
        fileBr = new BufferedReader(new FileReader(filePath));
    }

    public List<Student> createStudents() throws IOException {
        List<String> readAttendanceFile = readAttendanceFile();
        List<Student> students = new ArrayList<>();

        for (String information : readAttendanceFile) {
            String[] studentNameAndAttendanceTime = information.split(",");
            createStudentByFileInfo(students, studentNameAndAttendanceTime);
        }
        return students;
    }

    private List<String> readAttendanceFile() throws IOException {

        try (BufferedReader br = fileBr) {
            return br.lines()
                    .skip(1)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new IOException("[ERROR] 파일을 읽는 중 오류가 발생했습니다.", e);
        }
    }

    private void createStudentByFileInfo(List<Student> students, String[] studentNameAndAttendanceTime) {
        String name = studentNameAndAttendanceTime[0];
        LocalDateTime timeInformation = makeLocalDateTimeFromString(studentNameAndAttendanceTime[1]);
        if (findStudentByName(students, name) == null) {
            Student student = new Student(name, createAttendanceRecords(timeInformation));
            students.add(student);
            makeDateTimeFormatAndUpdateStudentState(student, timeInformation);
            return;
        }
        Student student = findStudentByName(students, name);
        makeDateTimeFormatAndUpdateStudentState(student, timeInformation);
    }

    private Student findStudentByName(List<Student> students, String name) {
        return students.stream()
                .filter(s -> s.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    private static LocalDateTime makeLocalDateTimeFromString(String timeInformation) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(
                DateFormatInformation.LOCAL_DATE_TIME_FORMATTER);
        return LocalDateTime.parse(timeInformation, dateTimeFormatter);
    }

    private static void makeDateTimeFormatAndUpdateStudentState(Student student, LocalDateTime localDateTime) {
        student.createAttendanceRecords(localDateTime);
    }

    private static AttendanceRecords createAttendanceRecords(LocalDateTime localDateTime) {
        Map<LocalDate, AttendanceRecord> attendanceRecords = new HashMap<>();
        LocalDate localDate = LocalDate.from(localDateTime);
        LocalTime localTime = LocalTime.from(localDateTime);
        AttendanceStatus attendanceStatus = AttendanceRuleByDay.calculateAttendance(localDateTime);
        AttendanceRecord attendanceRecord = new AttendanceRecord(localTime, attendanceStatus);
        attendanceRecords.put(localDate, attendanceRecord);
        return new AttendanceRecords(attendanceRecords);
    }

}

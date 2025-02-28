import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceBook {
    List<Student> attendanceBook;

    public AttendanceBook(Map<String, List<LocalDateTime>> fileAttendanceRecord){
        attendanceBook = fileAttendanceRecord.entrySet().stream()
                .map(map -> new Student(map.getKey(), map.getValue()))
                .collect(Collectors.toList());
    }

    public Map<LocalDate, LocalTime> findStudentAttendanceTimeRecord(String name) {
        Student student = attendanceBook.stream()
                .filter(stu -> stu.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 찾는 학생이 존재하지 않습니다."));
        return student.findAttendanceTimeRecordMap();
    }
}

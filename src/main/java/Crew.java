import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Crew {

    private final String nickname;
    private final List<LocalDateTime> attendanceHistory = new ArrayList<>();

    public Crew(String name) {
        this.nickname = name;
    }

    public void attendance(LocalDate date, LocalTime time) {
        validateAlreadyAttendanceDate(date);
        attendanceHistory.add(LocalDateTime.of(date, time));
    }

    private void validateAlreadyAttendanceDate(LocalDate date) {
        if (attendanceHistory.stream()
            .anyMatch(localDateTime -> localDateTime.toLocalDate().equals(date))) {
            throw new AlreadyAttendanceException("이미 출석 처리되어 있습니다. 수정 기능을 이용해주세요.");
        }
    }

    public String getName() {
        return nickname;
    }
}

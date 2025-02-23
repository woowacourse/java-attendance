package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Attendances {

    private final List<Attendance> attendances = new ArrayList<>();

    public Attendance find(LocalDate date) {
        return attendances.stream()
                .filter(attendance -> attendance.isSameDate(date))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 수정하려는 날짜는 출석할 수 없습니다."));
    }

    public Attendance addAttendance(LocalDateTime dateTime) {
        Attendance attendance = new Attendance(dateTime);
        attendances.add(attendance);

        return attendance;
    }

    public Attendance deleteAttendance(LocalDate date) {
        Attendance attendance = find(date);
        attendances.remove(attendance);

        return attendance;
    }

    public List<Attendance> getAttendancesUntilYesterday() {
        return attendances.stream()
                .sorted(Attendance::compareTo)
                .toList()
                .subList(0, attendances.size() - 1);
    }

    public void validateAlreadyAttendance(LocalDate date) {
        if (find(date).isAlreadyChecked()) {
            throw new IllegalArgumentException("[ERROR] 이미 출석을 완료하셨습니다. 수정 기능을 이용해주세요.");
        }
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }
}

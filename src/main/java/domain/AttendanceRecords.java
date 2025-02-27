package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AttendanceRecords {

    private final List<AttendanceDateTime> records = new ArrayList<>();

    public void add(AttendanceDateTime attendanceDateTime) {
        if (existsSameDate(attendanceDateTime)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석하셨습니다. 수정 기능을 이용해 주세요.");
        }
        records.add(attendanceDateTime);
    }

    private boolean existsSameDate(AttendanceDateTime attendanceDateTime) {
        LocalDate date = attendanceDateTime.getDate();
        return records.stream()
            .anyMatch(record -> record.isSameDate(date));
    }

    public List<AttendanceDateTime> getRecords() {
        return new ArrayList<>(records);
    }
}

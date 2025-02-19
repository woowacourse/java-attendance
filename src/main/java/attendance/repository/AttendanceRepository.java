package attendance.repository;

import attendance.domain.Attendance;
import attendance.domain.Time;
import java.time.LocalDate;
import java.util.List;

public class AttendanceRepository {
    private final List<Attendance> attendances;

    public AttendanceRepository(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void add(Attendance currentAttendance) {
        for (Attendance attendance : attendances) {
            if (attendance.isAlreadyAttendance(currentAttendance)) {
                throw new IllegalArgumentException("[ERROR] 오늘은 이미 출석하셨습니다. 수정 기능을 이용해 주세요.");
            }
        }
        attendances.add(currentAttendance);
    }

    public List<Attendance> findAllAttendanceByName(String name) {
        return attendances.stream()
                .filter(attendance -> attendance.getCrewName().equals(name))
                .toList();
    }

    public Attendance findAttendanceByNameAndDateTime(String name, int day) {

        for (Attendance attendance : attendances) {
            if (attendance.isSameByNameAndDay(name, day)) {
                return attendance;
            }
        }

        throw new IllegalArgumentException("[ERROR] 존재하지 않는 출석 기록입니다.");
    }

    public void initAbsent(String name) {

        for (int day = 1; day < LocalDate.now().getDayOfMonth(); day++) {
            try {
                Attendance attendance = findAttendanceByNameAndDateTime(name, day);

            } catch (IllegalArgumentException e) {
                int year = LocalDate.now().getYear();
                int month = LocalDate.now().getMonthValue();
                attendances.add(new Attendance(name, new Time(LocalDate.of(year, month, day), "--", "--", true)));

            }
        }
    }
}

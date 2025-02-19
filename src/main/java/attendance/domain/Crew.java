package attendance.domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Crew {

    private String nickname;
    private List<Attendance> attendances;
    private Map<AttendanceStatus, Integer> statusCount;

    public Crew(String nickname, List<Attendance> attendances) {
        this.nickname = nickname;
        this.attendances = attendances;

        statusCount = new HashMap<>();
        attendances.forEach((attendance) -> updateStatusCount(attendance, 1));
    }

    public boolean isEqualToNickname(String nickname) {
        return this.nickname.equals(nickname);
    }

    public void existInAttendances(LocalDate date) {
        for (Attendance attendance : attendances) {
            if (attendance.isEqualToDate(date)) {
                throw new IllegalArgumentException("[ERROR] 이미 오늘 출석을 하셨습니다. 출석 수정을 이용해주세요.");
            }
        }
    }

    public void updateStatusCount(Attendance attendance, int amount) {
        statusCount.put(attendance.getStatus(), statusCount.getOrDefault(attendance.getStatus(), 0) + amount);
    }

    public void addAttendance(Attendance attendance) {
        attendances.add(attendance);
    }

    public String getNickname() {
        return nickname;
    }

    public List<Attendance> getAttendances() {
        return attendances;
    }

    public Map<AttendanceStatus, Integer> getStatusCount() {
        return statusCount;
    }

    public Warning checkWarning() {
        int absenceCount = statusCount.getOrDefault(AttendanceStatus.ABSENCE, 0) + statusCount.getOrDefault(AttendanceStatus.LATE_ABSENCE, 0);
        absenceCount += statusCount.getOrDefault(AttendanceStatus.LATE, 0) / 3;
        return Warning.check(absenceCount);
    }
}

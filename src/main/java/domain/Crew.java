package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Crew {
    private final String userName;
    private final AttendanceHistories attendanceHistories;

    public Crew(String userName, List<LocalDateTime> histories, LocalDate standard) {
        validateName(userName);
        this.userName = userName;
        this.attendanceHistories = new AttendanceHistories(histories, standard);
    }

    public void validateName(String userName) {
        if (userName.length() >= 5) {
            throw new IllegalArgumentException("[ERROR ]이름은 네 글자 이하여야 합니다. 네 글자 이하로 입력해 주세요.");
        }
    }

    public void addAttendance(LocalDateTime history) {
        attendanceHistories.addHistory(history);
    }

    public void editHistory(LocalDateTime attendanceTime) {
        attendanceHistories.editHistory(attendanceTime);
    }

    public LocalDateTime getHistoryDate(LocalDateTime attendance) {
        return attendanceHistories.getHistory(attendance);
    }

    public AbsenceLevel getClassifyAbsenceLevel(LocalDateTime attendance) {
        return attendanceHistories.classifyAbsenceLevel(attendance);
    }

    public Map<AttendanceResult, Integer> getAttendanceAllResult(LocalDateTime attendance) {
        return attendanceHistories.getAttendanceResultCount(attendance);
    }

    public List<AttendanceHistory> getBeforeHistories(LocalDateTime standard) {
        return attendanceHistories.getSortedHistories(standard);
    }

    public String getHistoryResult(LocalDateTime attendanceTime) {
        return attendanceHistories.getHistoryResult(attendanceTime);
    }

    public String getUserName() {
        return userName;
    }
}
